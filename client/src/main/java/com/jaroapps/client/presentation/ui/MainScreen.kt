package com.jaroapps.client.presentation.ui

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.jaroapps.client.R
import com.jaroapps.client.presentation.state.ConfigState
import com.jaroapps.client.presentation.state.MainScreenState
import com.jaroapps.client.presentation.viewmodel.MainViewModel

@Composable
internal fun NewMainScreen(
    mainViewModel: MainViewModel,
    context: Context,
    accessibilitySettingsLauncher: ActivityResultLauncher<Void?>,
) {
    val uiState by mainViewModel.screenState.observeAsState(initial = MainScreenState.Loading)
    var showDialog by remember { mutableStateOf(false) }

    if (uiState is MainScreenState.Loading) {
        LoadingIndicator()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(modifier = Modifier
            .height(100.dp)
            .width(250.dp),
            onClick = {
                mainViewModel.getConfig()
                showDialog = true
            }) {
            Text(
                text = stringResource(id = R.string.config),
                modifier = Modifier.padding(horizontal = 26.dp, vertical = 18.dp),
                fontSize = 36.sp,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(modifier = Modifier
            .height(100.dp)
            .width(250.dp),
            onClick = {
                if (isAccessibilityServiceEnabled(context)) {
                    mainViewModel.startPauseBtnClicked()
                } else {
                    accessibilitySettingsLauncher.launch()
                }
            }) {
            when (uiState) {
                is MainScreenState.Value -> {
                    if ((uiState as MainScreenState.Value).isStarted) {
                        Text(
                            text = stringResource(id = R.string.pause),
                            modifier = Modifier.padding(horizontal = 26.dp, vertical = 18.dp),
                            fontSize = 36.sp,
                        )
                    } else {
                        Text(
                            text = stringResource(id = R.string.start),
                            modifier = Modifier.padding(horizontal = 26.dp, vertical = 18.dp),
                            fontSize = 36.sp,
                        )
                    }
                }

                else -> {}
            }
        }
        if (showDialog) {
            CustomDialog(
                onDismiss = { showDialog = false },
                viewModel = mainViewModel,
            )
        }
    }
}

@Composable
internal fun CustomDialog(
    onDismiss: () -> Unit,
    viewModel: MainViewModel,
) {
    val configState by viewModel.configState.observeAsState(initial = ConfigState.Default)

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                var ip by remember {
                    mutableStateOf(
                        (configState as? ConfigState.Content)?.config?.ip ?: ""
                    )
                }

                var port by remember {
                    mutableStateOf(
                        (configState as? ConfigState.Content)?.config?.port ?: ""
                    )
                }

                TextField(
                    value = ip,
                    onValueChange = { ip = it },
                    label = { Text(text = stringResource(id = R.string.enter_ip)) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = port,
                    onValueChange = { port = it },
                    label = { Text(text = stringResource(id = R.string.enter_port)) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(modifier = Modifier
                    .wrapContentSize(),
                    onClick = {
                        viewModel.saveConfig(ip, port)
                        onDismiss()
                    }) {
                    Text(
                        text = stringResource(id = R.string.save),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
internal fun LoadingIndicator() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 66.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(84.dp),
            color = Color.Blue,
        )
    }
}