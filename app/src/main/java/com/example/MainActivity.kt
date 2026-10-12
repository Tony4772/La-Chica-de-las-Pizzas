package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.AppScreen
import com.example.ui.PizzaViewModel
import com.example.ui.components.AppBottomNav
import com.example.ui.components.AppHeader
import com.example.ui.components.FloatingToast
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp(viewModel: PizzaViewModel = viewModel()) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState()

    // Android System Back handling
    if (currentScreen != AppScreen.HOME) {
        BackHandler {
            viewModel.navigateTo(AppScreen.HOME)
        }
    }

    val headerTitle = when (currentScreen) {
        AppScreen.ADMIN -> "Admin: Editar Carta & Precios"
        AppScreen.TRACKING -> "Rastreo En Vivo"
        AppScreen.AUTH -> "Club de Las Pizzas"
        else -> "La Chica De Las Pizzas"
    }

    val headerSubtitle = when (currentScreen) {
        AppScreen.HOME -> "Inicio"
        AppScreen.CUSTOMIZER -> "Personalizar"
        AppScreen.CART -> "Carrito"
        AppScreen.TRACKING -> "En Horno"
        AppScreen.ADMIN -> "Admin"
        AppScreen.AUTH -> "Registro"
    }

    val showBackInHeader = currentScreen in listOf(AppScreen.ADMIN, AppScreen.AUTH, AppScreen.TRACKING)

    Scaffold(
        topBar = {
            AppHeader(
                title = headerTitle,
                subtitle = headerSubtitle,
                showBack = showBackInHeader,
                onBack = { viewModel.navigateTo(AppScreen.HOME) },
                onProfileClick = { viewModel.navigateTo(AppScreen.AUTH) },
                onAdminClick = { viewModel.navigateTo(AppScreen.ADMIN) }
            )
        },
        bottomBar = {
            if (currentScreen in listOf(AppScreen.HOME, AppScreen.CUSTOMIZER, AppScreen.CART, AppScreen.TRACKING)) {
                AppBottomNav(
                    currentScreen = currentScreen,
                    cartCount = cartItems.sumOf { it.quantity },
                    onTabSelected = { screen -> viewModel.navigateTo(screen) }
                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .testTag("app_scaffold")
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                AppScreen.HOME -> HomeScreen(viewModel = viewModel)
                AppScreen.CUSTOMIZER -> CustomizerScreen(viewModel = viewModel)
                AppScreen.CART -> CartScreen(viewModel = viewModel)
                AppScreen.TRACKING -> TrackingScreen(viewModel = viewModel)
                AppScreen.ADMIN -> AdminScreen(viewModel = viewModel)
                AppScreen.AUTH -> AuthScreen(viewModel = viewModel)
            }

            // Floating Toast notification
            FloatingToast(
                message = toastMessage,
                onViewCart = {
                    viewModel.dismissToast()
                    viewModel.navigateTo(AppScreen.CART)
                },
                onDismiss = { viewModel.dismissToast() }
            )
        }
    }
}
