package com.smartmemoryassistant.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.KeyboardVoice
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartmemoryassistant.data.model.MemoryItem
import com.smartmemoryassistant.ui.theme.AccentBlue
import com.smartmemoryassistant.ui.theme.AccentRed
import com.smartmemoryassistant.ui.theme.AppBackground
import com.smartmemoryassistant.ui.theme.PanelDark
import com.smartmemoryassistant.ui.theme.PanelLight

@Composable
fun HomeScreen() {
    val recentItems = listOf(
        MemoryItem("Keys", "Dining table tray", "Saved 08:45 AM", "Most searched"),
        MemoryItem("Wallet", "Laptop bag front pocket", "Saved yesterday", "Habit match"),
        MemoryItem("Charger", "Study desk drawer", "Saved 2 days ago", "Photo attached")
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppBackground
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                HeroSection()
            }
            item {
                ActionStrip()
            }
            item {
                InsightCard()
            }
            item {
                Text(
                    text = "Recent memories",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            items(recentItems) { item ->
                MemoryCard(item)
            }
        }
    }
}

@Composable
private fun HeroSection() {
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

@Composable
private fun ActionStrip() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        QuickAction("Add", Icons.Outlined.AddCircle, AccentRed, Modifier.weight(1f))
        QuickAction("Voice", Icons.Outlined.KeyboardVoice, AccentBlue, Modifier.weight(1f))
        QuickAction("Photo", Icons.Outlined.PhotoCamera, Color(0xFF4A67FF), Modifier.weight(1f))
    }
}

@Composable
private fun QuickAction(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
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
                    .background(tint.copy(alpha = 0.16f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = label, tint = tint)
            }
            Text(
                text = label,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun InsightCard() {
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

@Composable
private fun MemoryCard(item: MemoryItem) {
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

