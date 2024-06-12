package com.jaroapps.server.presentation.ui

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
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.jaroapps.bhtestismaev.R
import com.jaroapps.server.presentation.state.ConfigState
import com.jaroapps.server.presentation.state.MainScreenState
import com.jaroapps.server.presentation.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel = koinViewModel()
) {
    val uiState by mainViewModel.screenState.observeAsState(initial = MainScreenState.Loading)

    if (uiState is MainScreenState.Loading) {
        LoadingIndicator()
    }

    ServerScreenContent(
        startStopBtnClick = {
            mainViewModel.startStopBtnClicked()
        },
        logClick = { navController.navigate(route = "client_log_screen") },
        uiState = uiState,
        viewModel = mainViewModel,
    )
}

@Composable
internal fun ServerScreenContent(
    startStopBtnClick: () -> Unit,
    logClick: () -> Unit,
    uiState: MainScreenState,
    viewModel: MainViewModel,
) {
    var showDialog by remember { mutableStateOf(false) }

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
                viewModel.getConfig()
                showDialog = true
            }) {
            Text(
                text = stringResource(id = R.string.config),
                modifier = Modifier.padding(horizontal = 26.dp, vertical = 18.dp),
                fontSize = 36.sp,
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(modifier = Modifier
            .height(100.dp)
            .width(250.dp),
            onClick = {
                startStopBtnClick()
            }) {
            when (uiState) {
                is MainScreenState.Value -> {
                    if (uiState.isStarted) {
                        Text(
                            text = stringResource(id = R.string.stop),
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
        Spacer(modifier = Modifier.height(24.dp))
        Button(modifier = Modifier
            .height(100.dp)
            .width(250.dp),
            onClick = {
                logClick()
            }) {
            Text(
                text = stringResource(id = R.string.logs),
                modifier = Modifier.padding(horizontal = 26.dp, vertical = 18.dp),
                fontSize = 36.sp,
            )
        }
        if (showDialog) {
            CustomDialog(
                onDismiss = { showDialog = false },
                viewModel = viewModel,
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
                .padding(32.dp)
        ) {
            Column(
                modifier = Modifier.wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                var text by remember {
                    mutableStateOf(
                        (configState as? ConfigState.Content)?.config?.port ?: ""
                    )
                }

                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text(text = stringResource(id = R.string.enter_port)) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(modifier = Modifier
                    .wrapContentSize(),
                    onClick = {
                        viewModel.saveConfig(text)
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
            color = Blue,
        )
    }
}