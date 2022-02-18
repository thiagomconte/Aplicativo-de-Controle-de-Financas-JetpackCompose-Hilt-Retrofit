package com.example.financeapp.features.finance.dateReferenceList.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.financeapp.R
import com.example.financeapp.features.finance.dateReferenceList.DateReferenceEvent
import com.example.financeapp.features.finance.dateReferenceList.DateReferenceViewModel
import com.example.financeapp.utils.ResponseState
import com.example.financeapp.utils.UiEvent
import com.example.financeapp.utils.components.LoadingComponent
import com.example.financeapp.utils.components.TopAppBarComponent
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.collect

@Composable
fun DateReferenceListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: DateReferenceViewModel = hiltViewModel()
) {

    val state = viewModel.dateReferencesState.collectAsState(ResponseState.Loading).value
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getDateReferences()
        viewModel.channel.collect { event ->
            if (event is UiEvent.Navigate) {
                onNavigate(UiEvent.Navigate(event.route))
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBarComponent()
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(DateReferenceEvent.OnNewFinance) },
                contentColor = Color.White,
                backgroundColor = Color.Black
            ) {
                Icon(imageVector = Icons.Default.Add, "")
            }
        }
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { viewModel.refresh() },
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                when (state) {
                    is ResponseState.Success -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            Text(
                                stringResource(R.string.resumes),
                                style = MaterialTheme.typography.h3,
                                modifier = Modifier.align(CenterHorizontally)
                            )
                            LazyColumn() {
                                items(items = state.data) { item ->
                                    DateReferenceItem(onItemClick = {
                                        viewModel.onEvent(
                                            DateReferenceEvent.OnItemClick(
                                                it.id,
                                                it.title
                                            )
                                        )
                                    }, item)
                                }
                            }
                        }
                    }
                    is ResponseState.Empty -> {
                        Column(modifier = Modifier.align(Center)) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_list_black_24dp),
                                contentDescription = "Empty list",
                                modifier = Modifier
                                    .align(CenterHorizontally)
                                    .size(36.dp),
                                tint = Color.LightGray,
                            )
                            Text(
                                text = stringResource(R.string.you_havent_added_resumes_yet),
                                color = Color.LightGray,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.padding(top = 12.dp)
                            )
                        }
                    }
                    is ResponseState.Error -> {
                        Column(modifier = Modifier.align(Center)) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_error_black_36dp),
                                contentDescription = "Error list",
                                modifier = Modifier.align(CenterHorizontally),
                                tint = Color.LightGray
                            )
                            Text(
                                text = stringResource(R.string.data_error),
                                color = Color.LightGray,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.padding(top = 12.dp)
                            )
                        }
                    }
                    else -> LoadingComponent()
                }
            }
        }
    }
}
