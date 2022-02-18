package com.example.financeapp.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.example.financeapp.graph.NavGraph
import com.example.financeapp.ui.theme.FinanceAppTheme
import com.example.financeapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    init {
        instance = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinanceAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    NavGraph()
                }
            }
        }
    }

    companion object {

        @JvmStatic
        lateinit var instance: MainActivity

        fun reload() {
            instance.getSharedPreferences(Constants.USER_PREFERENCES, 0).edit().clear().apply()
            instance.finish()
            instance.startActivity(instance.intent)
        }
    }
}
