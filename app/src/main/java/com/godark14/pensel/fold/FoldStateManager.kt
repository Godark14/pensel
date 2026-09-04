package com.godark14.pensel.fold

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import kotlinx.coroutines.flow.map

@Composable
fun rememberFoldPosture(): FoldPosture {
    val context = LocalContext.current
    val activity = context as? Activity ?: return FoldPosture.CLOSED

    var posture by remember { mutableStateOf(FoldPosture.CLOSED) }

    val windowLayoutInfoFlow = remember(activity) {
        WindowInfoTracker.getOrCreate(activity)
            .windowLayoutInfo(activity)
            .map { layoutInfo: WindowLayoutInfo ->
                val foldingFeature = layoutInfo.displayFeatures
                    .filterIsInstance<FoldingFeature>()
                    .firstOrNull()

                when {
                    foldingFeature == null -> FoldPosture.CLOSED
                    foldingFeature.state == FoldingFeature.State.FLAT -> FoldPosture.OPENED
                    else -> FoldPosture.CLOSED
                }
            }
    }

    val state by windowLayoutInfoFlow.collectAsStateWithLifecycle(initialValue = FoldPosture.CLOSED)
    posture = state

    return posture
}