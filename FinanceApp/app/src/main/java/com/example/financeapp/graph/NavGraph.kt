package com.example.financeapp.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.financeapp.features.finance.addFinance.components.AddFinanceScreen
import com.example.financeapp.features.finance.dateReferenceList.components.DateReferenceListScreen
import com.example.financeapp.features.finance.financeDetails.components.FinanceScreen
import com.example.financeapp.features.user.login.components.LoginScreen
import com.example.financeapp.features.user.register.components.RegisterScreen
import com.example.financeapp.utils.Constants

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val sharedPrefs = LocalContext.current.getSharedPreferences(Constants.USER_PREFERENCES, 0)
    NavHost(navController = navController, startDestination = Constants.Routes.LOGIN) {
        composable(Constants.Routes.LOGIN) {
            if (sharedPrefs.getString(Constants.TOKEN, "").isNullOrBlank()) {
                LoginScreen(onNavigate = {
                    navController.navigate(it.route)
                })
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate(Constants.Routes.HOME) {
                        popUpTo(0)
                    }
                }
            }
        }
        composable(Constants.Routes.REGISTER) {
            RegisterScreen(onPopBackStack = {
                navController.popBackStack()
            })
        }
        composable(
            Constants.Routes.HOME
        ) {
            DateReferenceListScreen(onNavigate = {
                navController.navigate(it.route)
            })
        }
        composable(
            "${Constants.Routes.FINANCES}?id={id}&title={title}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("title") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { entry ->
            val title = entry.arguments?.getString("title") ?: ""
            FinanceScreen(title)
        }
        composable(Constants.Routes.ADD_FINANCE) {
            AddFinanceScreen(onPopBackStack = {
                navController.popBackStack()
            })
        }
    }
}
