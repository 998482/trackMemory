package com.smartmemoryassistant.presentation.camera



import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.core.content.FileProvider
import java.io.File

@Composable
fun rememberCameraLauncher(
    context: Context,
    onPhotoCaptured: (Uri) -> Unit
): Pair<Uri, () -> Unit> {

    val photoFile = remember {
        File(context.cacheDir, "memory_photo_${System.currentTimeMillis()}.jpg")
    }

    val photoUri = remember {
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            photoFile
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) onPhotoCaptured(photoUri)
    }

    return Pair(photoUri) { launcher.launch(photoUri) }
}