package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.AppScreen
import com.example.data.PizzaRepository
import com.example.ui.theme.*

fun openWhatsApp(context: Context, text: String = "Hola La Chica De Las Pizzas, deseo hacer un pedido") {
    try {
        val url = "https://wa.me/51934398066?text=" + Uri.encode(text)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    } catch (_: Exception) {
    }
}

fun callPhone(context: Context, phone: String = "51934398066") {
    try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        context.startActivity(intent)
    } catch (_: Exception) {
    }
}

@Composable
fun AppHeader(
    title: String? = null,
    subtitle: String = "Inicio",
    showBack: Boolean = false,
    onBack: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onAdminClick: () -> Unit = {}
) {
    val context = LocalContext.current

    Surface(
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        tonalElevation = 2.dp,
        shadowElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("app_header")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showBack) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .size(40.dp)
                            .testTag("header_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                }

                // Brand Logo
                AsyncImage(
                    model = PizzaRepository.LOGO_URL,
                    contentDescription = "Logo La Chica De Las Pizzas",
                    modifier = Modifier
                        .height(32.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = title ?: "La Chica De Las Pizzas",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        // Open badge
                        Surface(
                            color = PizzaTertiary.copy(alpha = 0.12f),
                            shape = CircleShape
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(PizzaTertiary, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Abierto ahora",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PizzaTertiary
                                )
                            }
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Moped,
                            contentDescription = null,
                            tint = PizzaPrimary,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "Lima, Perú • Delivery 30-45m",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = " • ",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.outline
                        )
                        Text(
                            text = subtitle,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PizzaPrimary
                        )
                    }
                }
            }

            // Right action icons (WhatsApp + Profile avatar)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Admin shortcut
                IconButton(
                    onClick = onAdminClick,
                    modifier = Modifier
                        .size(36.dp)
                        .background(PizzaSecondaryFixed.copy(alpha = 0.5f), CircleShape)
                        .testTag("admin_shortcut_button")
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Storefront,
                        contentDescription = "Admin Panel",
                        tint = PizzaSecondary,
                        modifier = Modifier.size(19.dp)
                    )
                }

                // WhatsApp icon button
                IconButton(
                    onClick = { openWhatsApp(context) },
                    modifier = Modifier
                        .size(36.dp)
                        .background(PizzaTertiary.copy(alpha = 0.12f), CircleShape)
                        .testTag("whatsapp_header_button")
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Chat,
                        contentDescription = "WhatsApp",
                        tint = PizzaTertiary,
                        modifier = Modifier.size(19.dp)
                    )
                }

                // Profile Avatar button
                IconButton(
                    onClick = onProfileClick,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .testTag("profile_header_button")
                ) {
                    AsyncImage(
                        model = PizzaRepository.PROFILE_AVATAR_URL,
                        contentDescription = "Perfil",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Composable
fun AppBottomNav(
    currentScreen: AppScreen,
    cartCount: Int,
    onTabSelected: (AppScreen) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        tonalElevation = 6.dp,
        shadowElevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .testTag("bottom_navigation_bar")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            // Tab 1: Inicio
            BottomNavItem(
                icon = Icons.Filled.LocalPizza,
                label = "Inicio",
                isSelected = currentScreen == AppScreen.HOME,
                testTag = "tab_inicio",
                onClick = { onTabSelected(AppScreen.HOME) }
            )

            // Tab 2: Personalizar
            BottomNavItem(
                icon = Icons.Filled.AutoFixHigh,
                label = "Personalizar",
                isSelected = currentScreen == AppScreen.CUSTOMIZER,
                testTag = "tab_personalizar",
                onClick = { onTabSelected(AppScreen.CUSTOMIZER) }
            )

            // Tab 3: Carrito
            BottomNavItem(
                icon = Icons.Filled.ShoppingBag,
                label = "Carrito",
                isSelected = currentScreen == AppScreen.CART,
                badgeCount = cartCount,
                testTag = "tab_carrito",
                onClick = { onTabSelected(AppScreen.CART) }
            )

            // Tab 4: Mis Pedidos
            BottomNavItem(
                icon = Icons.Filled.ReceiptLong,
                label = "Mis Pedidos",
                isSelected = currentScreen == AppScreen.TRACKING,
                hasNotificationDot = true,
                testTag = "tab_pedidos",
                onClick = { onTabSelected(AppScreen.TRACKING) }
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean,
    badgeCount: Int = 0,
    hasNotificationDot: Boolean = false,
    testTag: String,
    onClick: () -> Unit
) {
    val tint = if (isSelected) PizzaPrimary else MaterialTheme.colorScheme.onSurfaceVariant
    val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal

    Column(
        modifier = Modifier
            .width(76.dp)
            .clickable(onClick = onClick)
            .testTag(testTag)
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = tint,
                modifier = Modifier.size(24.dp)
            )

            if (badgeCount > 0) {
                Surface(
                    color = PizzaPrimary,
                    shape = CircleShape,
                    modifier = Modifier
                        .offset(x = 10.dp, y = (-6).dp)
                        .size(17.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = badgeCount.toString(),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            } else if (hasNotificationDot) {
                Box(
                    modifier = Modifier
                        .offset(x = 10.dp, y = (-6).dp)
                        .size(8.dp)
                        .background(PizzaSecondaryContainer, CircleShape)
                )
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = fontWeight,
            color = tint,
            maxLines = 1
        )
    }
}

@Composable
fun FloatingToast(
    message: String?,
    onViewCart: () -> Unit,
    onDismiss: () -> Unit
) {
    AnimatedVisibility(
        visible = message != null,
        enter = slideInVertically { it } + fadeIn(),
        exit = slideOutVertically { it } + fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 76.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Surface(
                color = PizzaInverseSurface,
                shape = RoundedCornerShape(14.dp),
                shadowElevation = 10.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("floating_toast")
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = PizzaSecondaryFixed,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = message ?: "",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                            color = PizzaInverseOnSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    TextButton(
                        onClick = onViewCart,
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                        modifier = Modifier.testTag("toast_view_cart_button")
                    ) {
                        Text(
                            text = "Ver Carrito",
                            fontWeight = FontWeight.Bold,
                            color = PizzaSecondaryFixed,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
