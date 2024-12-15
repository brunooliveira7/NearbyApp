package com.example.nearby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.nearby.data.model.MarKet
import com.example.nearby.ui.screen.HomeScreen
import com.example.nearby.ui.screen.MarketDetailsScreen
import com.example.nearby.ui.screen.SplashScreen
import com.example.nearby.ui.screen.WelcomeScreen
import com.example.nearby.ui.screen.route.Home
import com.example.nearby.ui.screen.route.Splash
import com.example.nearby.ui.screen.route.Welcome
import com.example.nearby.ui.theme.NearbyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NearbyTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Splash
                ) {
                    composable<Splash> {
                        SplashScreen(
                            onNavigateToWelcome = {
                                navController.navigate(Welcome)
                            }
                        )
                    }
                    composable<Welcome> {
                        WelcomeScreen(
                            onNavigateToHome = {
                                navController.navigate(Home)
                           }
                        )
                    }
                    composable<Home> {
                        HomeScreen(onNavigateToMarketDetails = { selectMarket ->
                            navController.navigate(selectMarket)
                        })
                    }
                    composable<MarKet> {
                        val selectedMarKet = it.toRoute<MarKet>()

                        MarketDetailsScreen(
                            market = selectedMarKet,
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}