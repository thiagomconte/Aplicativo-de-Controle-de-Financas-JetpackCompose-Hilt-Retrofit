package com.example.financeapp.features.finance.financeDetails.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.financeapp.R
import com.example.financeapp.features.finance.financeDetails.FinanceViewModel
import com.example.financeapp.models.Finance
import com.example.financeapp.ui.theme.DebtRed
import com.example.financeapp.ui.theme.ProfitGreen
import com.example.financeapp.ui.theme.RobotoSerifRegular
import com.example.financeapp.utils.Common
import com.example.financeapp.utils.ResponseState
import com.example.financeapp.utils.components.LoadingComponent
import com.example.financeapp.utils.components.TopAppBarComponent
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun FinanceScreen(title: String, viewModel: FinanceViewModel = hiltViewModel()) {

    val state = viewModel.financeState.collectAsState(ResponseState.Loading).value
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    Scaffold(
        topBar = {
            TopAppBarComponent()
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
                                title,
                                style = MaterialTheme.typography.h3,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                            LazyColumn() {
                                items(items = state.data) { item ->
                                    FinanceItem(item)
                                }
                                item {
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp)
                                    ) {
                                        Text(
                                            stringResource(R.string.total),
                                            fontFamily = RobotoSerifRegular,
                                            color = Color.Gray,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            Common.formatNumber(getTotal(state.data)),
                                            fontFamily = RobotoSerifRegular,
                                            color = if (getTotal(state.data) > 0) ProfitGreen else DebtRed
                                        )
                                    }
                                }
                            }
                        }
                    }
                    is ResponseState.Empty -> {
                        Column(modifier = Modifier.align(Alignment.Center)) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_list_black_24dp),
                                contentDescription = "Empty list",
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                tint = Color.LightGray
                            )
                            Text(
                                text = stringResource(R.string.you_havent_added_resumes_yet),
                                color = Color.LightGray,
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.padding(top = 12.dp)
                            )
                        }
                    }
                    is ResponseState.Error -> {
                        Column(modifier = Modifier.align(Alignment.Center)) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_error_black_36dp),
                                contentDescription = "Error list",
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                tint = Color.LightGray
                            )
                            Text(
                                text = stringResource(R.string.data_error),
                                color = Color.LightGray,
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

fun getTotal(finances: List<Finance>): Float {
    var total = 0f
    for (finance in finances) {
        total += finance.value
    }
    return total
}
