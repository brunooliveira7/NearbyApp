package com.example.nearby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.nearby.data.model.MarKet
import com.example.nearby.ui.screen.home.HomeScreen
import com.example.nearby.ui.screen.home.HomeViewModel
import com.example.nearby.ui.screen.market_details.MarketDetailsScreen
import com.example.nearby.ui.screen.market_details.MarketDetailsUiEvent
import com.example.nearby.ui.screen.market_details.MarketDetailsViewModel
import com.example.nearby.ui.screen.qrcode_scanner.QRCodeScannerScreen
import com.example.nearby.ui.screen.splash.SplashScreen
import com.example.nearby.ui.screen.welcome.WelcomeScreen
import com.example.nearby.ui.screen.route.Home
import com.example.nearby.ui.screen.route.QRCodeScanner
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

                val homeViewModel by viewModels<HomeViewModel>()
                val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()

                val marketDetailsViewModel by viewModels<MarketDetailsViewModel>()
                val marketDetailsUiState by marketDetailsViewModel.uiState.collectAsStateWithLifecycle()

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
                        HomeScreen(
                            onNavigateToMarketDetails = { selectMarket ->
                            navController.navigate(selectMarket)
                        },
                            uiState = homeUiState,
                            onEvent = homeViewModel::onEvent)
                    }
                    composable<MarKet> {
                        val selectedMarKet = it.toRoute<MarKet>()

                        MarketDetailsScreen(
                            market = selectedMarKet,
                            uiState = marketDetailsUiState,
                            onEvent = marketDetailsViewModel::OnEvent,
                            onNavigateBack = {
                                navController.popBackStack()
                            },
                            onNavigateToQRCodeScanner = {
                                navController.navigate(QRCodeScanner)
                            }
                        )
                    }
                    composable<QRCodeScanner> {
                        QRCodeScannerScreen(
                            onCompleteScan =  {qrCodeContent ->
                                if (qrCodeContent.isNotEmpty())
                                     marketDetailsViewModel.OnEvent(
                                        MarketDetailsUiEvent.OnFetchCoupon(
                                            qrCodeContent = qrCodeContent
                                    )
                                )
                            navController.popBackStack()
                        }
                    )
                    }
                }
            }
        }
    }
}