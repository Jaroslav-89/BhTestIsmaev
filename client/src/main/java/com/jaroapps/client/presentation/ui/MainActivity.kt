package com.jaroapps.client.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.jaroapps.client.presentation.ui.theme.BhTestIsmaevTheme
import com.jaroapps.client.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    private val accessibilitySettingsLauncher =
        registerForActivityResult(AccessibilitySettingsContract()) {
            if (isAccessibilityServiceEnabled(this)) {
                mainViewModel.startPauseBtnClicked()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BhTestIsmaevTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NewMainScreen(
                        mainViewModel = mainViewModel,
                        context = this,
                        accessibilitySettingsLauncher = accessibilitySettingsLauncher
                    )
                }
            }
        }
    }
}

fun isAccessibilityServiceEnabled(context: Context): Boolean {
    val am = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
    return if (am.isEnabled) true else false
}

class AccessibilitySettingsContract : ActivityResultContract<Void?, Boolean>() {
    override fun createIntent(context: Context, input: Void?): Intent {
        return Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return false
    }
}