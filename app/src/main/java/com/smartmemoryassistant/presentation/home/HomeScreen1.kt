package com.smartmemoryassistant.presentation.home

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.KeyboardVoice
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smartmemoryassistant.data.model.MemoryItem
import com.smartmemoryassistant.data.model.RuleResult
import com.smartmemoryassistant.presentation.camera.rememberCameraLauncher
import com.smartmemoryassistant.presentation.viewmodel.MemoryViewModel
import com.smartmemoryassistant.presentation.voice.VoiceInputHandler
import com.smartmemoryassistant.ui.theme.AccentBlue
import com.smartmemoryassistant.ui.theme.AccentRed
import com.smartmemoryassistant.ui.theme.AppBackground
import com.smartmemoryassistant.ui.theme.PanelDark
import com.smartmemoryassistant.ui.theme.PanelLight

@Composable
fun HomeScreen1(viewModel: MemoryViewModel = viewModel()) {
    val context = LocalContext.current
    val memories by viewModel.memories.collectAsState()
    val ruleResult by viewModel.ruleResult.collectAsState()
    val voiceText by viewModel.voiceText.collectAsState()

    var isListening by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }
    var capturedPhotoUri by remember { mutableStateOf<android.net.Uri?>(null) }

    // Voice handler
    val voiceHandler = remember {
        VoiceInputHandler(
            context = context,
            onResult = { text ->
                isListening = false
                viewModel.onVoiceResult(text)
            },
            onError = { isListening = false }
        )
    }

    // Camera
    val (photoUri, launchCamera) = rememberCameraLauncher(context) { uri ->
        capturedPhotoUri = uri
        showAddDialog = true
    }

    // Permissions launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        if (perms[Manifest.permission.RECORD_AUDIO] == true) {
            isListening = true
            voiceHandler.startListening()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppBackground
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            // ── Hero Card ──────────────────────────────────────────
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(32.dp))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    AccentRed,
                                    Color(0xFF8C1D40),
                                    AccentBlue
                                )
                            )
                        )
                        .padding(24.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.16f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "SM",
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 18.sp
                            )
                        }
                        Text(
                            text = "Never forget where you kept your things.",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = "Fast memory capture for keys, wallet, charger, books, and everyday essentials.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }
                }
            }

            // ── Action Strip ───────────────────────────────────────
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Add button
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { showAddDialog = true },
                        colors = CardDefaults.cardColors(containerColor = PanelDark),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(AccentRed.copy(alpha = 0.16f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Outlined.AddCircle,
                                    contentDescription = "Add",
                                    tint = AccentRed
                                )
                            }
                            Text(
                                text = "Add",
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    // Voice button
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                permissionLauncher.launch(
                                    arrayOf(Manifest.permission.RECORD_AUDIO)
                                )
                            },
                        colors = CardDefaults.cardColors(containerColor = PanelDark),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isListening) Color.Green.copy(alpha = 0.16f)
                                        else AccentBlue.copy(alpha = 0.16f)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Outlined.KeyboardVoice,
                                    contentDescription = "Voice",
                                    tint = if (isListening) Color.Green else AccentBlue
                                )
                            }
                            Text(
                                text = if (isListening) "Listening…" else "Voice",
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    // Camera button
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                permissionLauncher.launch(
                                    arrayOf(Manifest.permission.CAMERA)
                                )
                                launchCamera()
                            },
                        colors = CardDefaults.cardColors(containerColor = PanelDark),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF4A67FF).copy(alpha = 0.16f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Outlined.PhotoCamera,
                                    contentDescription = "Photo",
                                    tint = Color(0xFF4A67FF)
                                )
                            }
                            Text(
                                text = "Photo",
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // ── Voice result card ──────────────────────────────────
            if (voiceText.isNotEmpty()) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = PanelLight),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(AccentBlue.copy(alpha = 0.16f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "🎙", fontSize = 18.sp)
                            }
                            Column {
                                Text(
                                    text = "Voice captured",
                                    color = AccentBlue,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.labelLarge
                                )
                                Text(
                                    text = "\"$voiceText\"",
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }

            // ── Rule engine result card ────────────────────────────
            ruleResult?.let { result ->
                item {
                    val color = when (result.ruleType) {
                        "HABIT" -> AccentBlue
                        "TIME"  -> Color(0xFF4A67FF)
                        else    -> AccentRed
                    }
                    Card(
                        colors = CardDefaults.cardColors(containerColor = PanelLight),
                        shape = RoundedCornerShape(28.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalArrangement = Arrangement.spacedBy(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(color.copy(alpha = 0.18f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = when (result.ruleType) {
                                        "HABIT" -> "🔁"
                                        "TIME"  -> "⏰"
                                        else    -> "📍"
                                    },
                                    fontSize = 20.sp
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${result.ruleType} RULE  •  ${result.confidence}% confidence",
                                    color = color,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.labelLarge
                                )
                                Text(
                                    text = result.suggestion,
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }

            // ── Smart suggestion card (always visible) ─────────────
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = PanelLight),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(AccentBlue.copy(alpha = 0.14f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Search,
                                    contentDescription = null,
                                    tint = AccentBlue
                                )
                            }
                            Column {
                                Text(
                                    text = "Smart suggestion",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "You usually keep your keys near the dining table.",
                                    color = Color.White.copy(alpha = 0.76f),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }

            // ── Section title ──────────────────────────────────────
            item {
                Text(
                    text = "Recent memories",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            // ── Memory cards list ──────────────────────────────────
            items(memories) { item ->
                Card(
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(containerColor = PanelDark)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item.title,
                                color = Color.White,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(AccentRed.copy(alpha = 0.16f))
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = item.accentLabel,
                                    color = AccentRed,
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = item.location,
                            color = Color.White.copy(alpha = 0.86f),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.savedAt,
                            color = Color.White.copy(alpha = 0.58f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }

    // ── Add Memory Dialog ──────────────────────────────────────────
    if (showAddDialog) {
        var titleInput by remember { mutableStateOf("") }
        var locationInput by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = {
                showAddDialog = false
                capturedPhotoUri = null
            },
            containerColor = PanelDark,
            title = {
                Text(
                    text = if (capturedPhotoUri != null) "Save Memory with Photo" else "Save Memory",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    if (capturedPhotoUri != null) {
                        Text(
                            text = "📷 Photo attached",
                            color = AccentBlue,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    OutlinedTextField(
                        value = titleInput,
                        onValueChange = { titleInput = it },
                        label = {
                            Text(
                                "Item (e.g. Keys)",
                                color = Color.White.copy(alpha = 0.6f)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = AccentRed,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.3f)
                        )
                    )
                    OutlinedTextField(
                        value = locationInput,
                        onValueChange = { locationInput = it },
                        label = {
                            Text(
                                "Location",
                                color = Color.White.copy(alpha = 0.6f)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = AccentRed,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.3f)
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (titleInput.isNotBlank() && locationInput.isNotBlank()) {
                            if (capturedPhotoUri != null) {
                                viewModel.saveMemoryWithPhoto(
                                    titleInput,
                                    locationInput,
                                    capturedPhotoUri.toString()
                                )
                            } else {
                                viewModel.saveMemory(titleInput, locationInput)
                            }
                            showAddDialog = false
                            capturedPhotoUri = null
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AccentRed)
                ) {
                    Text("Save", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showAddDialog = false
                        capturedPhotoUri = null
                    }
                ) {
                    Text("Cancel", color = Color.White.copy(alpha = 0.6f))
                }
            }
        )
    }
}