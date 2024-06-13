package com.jaroapps.server.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jaroapps.bhtestismaev.R
import com.jaroapps.server.domain.model.ServerLog
import com.jaroapps.server.presentation.state.LogScreenState
import com.jaroapps.server.presentation.viewmodel.LogViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun ClientLogScreen(
    navController: NavHostController,
    logViewModel: LogViewModel = koinViewModel()
) {
    val uiState by logViewModel.screenState.observeAsState(initial = LogScreenState.Loading)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.server_log)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("main_screen") }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        SwipeEventList(uiState = uiState, paddingValues)
    }
}

@Composable
internal fun SwipeEventList(uiState: LogScreenState, paddingValues: PaddingValues) {
    when (uiState) {
        is LogScreenState.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(144.dp),
                color = Color.Blue,
            )
        }

        is LogScreenState.Content -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(uiState.serverLog) { item ->
                    ListItem(swipeEvent = item)
                }
            }
        }
    }
}

@Composable
internal fun ListItem(swipeEvent: ServerLog) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Client ID: ${swipeEvent.clientId}")
            Text(text = "Swipe Direction: ${swipeEvent.swipeDirection}")
            Text(text = "Swipe Value: ${swipeEvent.swipeValue}")
            Text(text = "Add Time: ${swipeEvent.addTime}")
        }
    }
}
