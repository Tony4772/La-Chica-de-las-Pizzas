package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.AppScreen
import com.example.data.PizzaRepository
import com.example.ui.PizzaViewModel
import com.example.ui.components.callPhone
import com.example.ui.components.openWhatsApp
import com.example.ui.theme.*

@Composable
fun TrackingScreen(
    viewModel: PizzaViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val tracking by viewModel.orderTracking.collectAsState()
    val isSummaryExpanded by viewModel.isSummaryExpanded.collectAsState()

    // Smooth pulse animation for fire status
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
            .testTag("tracking_screen")
    ) {
        // Status Pulse Banner
        Surface(
            color = PizzaSurfaceContainerLow,
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = PizzaPrimary.copy(alpha = 0.12f),
                        shape = CircleShape
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = tracking.statusText.uppercase(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = PizzaPrimary
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(PizzaPrimary.copy(alpha = pulseAlpha), CircleShape)
                            )
                        }
                    }

                    Text(
                        text = tracking.timePlaced,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Pedido ${tracking.orderNumber}",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "Preparado al momento en horno de piedra rústico",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(14.dp))

                // ETA Display Block
                Surface(
                    color = PizzaSurfaceContainerLowest,
                    shape = RoundedCornerShape(12.dp),
                    shadowElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .background(PizzaSecondaryFixed, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocalFireDepartment,
                                    contentDescription = null,
                                    tint = PizzaOnSecondaryFixed,
                                    modifier = Modifier.size(24.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .size(10.dp)
                                        .background(PizzaPrimary, CircleShape)
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                Text(
                                    text = "TIEMPO ESTIMADO",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    letterSpacing = 0.5.sp
                                )
                                Text(
                                    text = tracking.etaRange,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = PizzaPrimary
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.Schedule,
                                    contentDescription = null,
                                    tint = PizzaTertiary,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = tracking.etaTime,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PizzaTertiary
                                )
                            }
                            Text(
                                text = "Llegada aprox.",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Progress indicator
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(PizzaSurfaceContainerHigh)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.58f)
                                .fillMaxHeight()
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(
                                            PizzaSecondaryContainer,
                                            PizzaPrimary,
                                            PizzaPrimaryContainer
                                        )
                                    )
                                )
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Horneado en curso",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Paso 3 de 5",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PizzaPrimary
                        )
                    }
                }
            }
        }

        // Live Stepper Section
        Surface(
            color = PizzaSurfaceContainerLowest,
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .testTag("live_stepper_section")
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Timeline,
                            contentDescription = null,
                            tint = PizzaPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Seguimiento en Vivo",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Surface(
                        color = PizzaTertiaryFixed.copy(alpha = 0.35f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Actualizado",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PizzaTertiaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Step 1
                TrackingStepRow(
                    title = "Pedido recibido y confirmado",
                    time = "8:16 PM",
                    desc = "Comprobante validado por caja central.",
                    isPassed = true,
                    isActive = false,
                    isLast = false
                )

                // Step 2
                TrackingStepRow(
                    title = "Masa estirada e ingredientes listos",
                    time = "8:20 PM",
                    desc = "Salsa pomodoro rústica y mozzarella fresca.",
                    isPassed = true,
                    isActive = false,
                    isLast = false
                )

                // Step 3 (Active)
                TrackingStepRow(
                    title = "¡En el horno artesanal!",
                    time = "8:28 PM",
                    desc = "Dorándose a 350°C en piedra volcánica para una base crujiente perfecta.",
                    isPassed = false,
                    isActive = true,
                    isLast = false
                )

                // Step 4
                TrackingStepRow(
                    title = "En camino con el repartidor",
                    time = "Pendiente",
                    desc = "Mochila térmica sellada para máxima temperatura.",
                    isPassed = false,
                    isActive = false,
                    isLast = false
                )

                // Step 5
                TrackingStepRow(
                    title = "¡Entregado y a disfrutar!",
                    time = "Pendiente",
                    desc = "Directo a tu mesa familiar.",
                    isPassed = false,
                    isActive = false,
                    isLast = true
                )
            }
        }

        // Courier & Delivery Card
        Surface(
            color = PizzaSurfaceContainerLowest,
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .testTag("courier_card")
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        AsyncImage(
                            model = tracking.courierImage,
                            contentDescription = "Repartidor",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(PizzaSurfaceContainer)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = tracking.courierName,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    color = PizzaSecondaryFixed,
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = "Motorizado",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PizzaOnSecondaryFixed,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                    )
                                }
                            }
                            Text(
                                text = tracking.courierVehicle,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = null,
                                    tint = PizzaTertiary,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "Repartidor verificado",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = PizzaTertiary
                                )
                            }
                        }
                    }

                    Button(
                        onClick = { callPhone(context, "934398066") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PizzaSurfaceContainerHigh,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        shape = CircleShape,
                        modifier = Modifier.height(38.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = null,
                            tint = PizzaPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Llamar", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                // WhatsApp Direct Pizzeria CTA
                Surface(
                    color = PizzaSurfaceContainerLow,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.Storefront,
                                    contentDescription = null,
                                    tint = PizzaTertiary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Pizzería La Chica De Las Pizzas",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "Sede Miraflores",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Button(
                            onClick = { openWhatsApp(context, "Hola, consulta del pedido ${tracking.orderNumber}") },
                            colors = ButtonDefaults.buttonColors(containerColor = PizzaTertiaryContainer),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                                .testTag("btn_whatsapp_pizzeria")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "WhatsApp o Llamar al 934 398 066",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }

        // Collapsible Order Summary
        Surface(
            color = PizzaSurfaceContainerLowest,
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .clickable { viewModel.toggleOrderSummary() }
                .testTag("order_summary_collapsible")
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ReceiptLong,
                            contentDescription = null,
                            tint = PizzaPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Resumen del Pedido",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "3 productos • Pagado con Yape",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "S/ ${String.format("%.2f", tracking.total)}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = PizzaPrimary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = if (isSummaryExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                AnimatedVisibility(visible = isSummaryExpanded) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Summary items
                        SummaryProductRow(
                            name = "1x Box Degustación x6 Minipizzas",
                            desc = "Masa madre 48h, variedad del chef",
                            price = 38.00,
                            img = PizzaRepository.initialCartItems[0].image
                        )
                        SummaryProductRow(
                            name = "1x Pizza Grande La Chica Especial",
                            desc = "Salsa rústica, doble queso y jamón selva",
                            price = 26.57,
                            img = PizzaRepository.initialCartItems[1].image
                        )
                        SummaryProductRow(
                            name = "1x Inca Kola 1.5L",
                            desc = "Bien helada",
                            price = 10.00,
                            img = PizzaRepository.initialCartItems[2].image
                        )

                        // Yape status pill
                        Surface(
                            color = PizzaSurfaceContainerLow,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .background(YapePurple, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            "Y",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text("Pagado vía Yape", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        Text("Operación #4910283", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }

                                Surface(
                                    color = PizzaTertiaryFixed.copy(alpha = 0.35f),
                                    shape = CircleShape
                                ) {
                                    Text(
                                        text = "Completado",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PizzaTertiary,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Baker Note Card
        Surface(
            color = Color.Transparent,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                PizzaSecondaryFixed.copy(alpha = 0.45f),
                                PizzaSurfaceContainer
                            )
                        )
                    )
                    .padding(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .background(PizzaSecondaryContainer, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.OutdoorGrill,
                            contentDescription = null,
                            tint = PizzaOnSecondaryContainer,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Nota del Maestro Pizzero",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = tracking.bakerNote,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Action Buttons Footer
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { viewModel.refreshTracking() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PizzaSurfaceContainerHigh,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .testTag("btn_refresh_tracking")
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Actualizar Estado", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { viewModel.navigateTo(AppScreen.HOME) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PizzaSurfaceContainerLow,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp)
                        .testTag("btn_back_home")
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Volver al Inicio", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = { openWhatsApp(context, "Necesito ayuda con mi pedido ${tracking.orderNumber}") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PizzaSurfaceContainerLow,
                        contentColor = PizzaPrimary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp)
                        .testTag("btn_help_order")
                ) {
                    Icon(
                        imageVector = Icons.Outlined.HelpOutline,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = PizzaPrimary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Pedir Ayuda", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun TrackingStepRow(
    title: String,
    time: String,
    desc: String,
    isPassed: Boolean,
    isActive: Boolean,
    isLast: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(
                        when {
                            isPassed -> PizzaTertiary
                            isActive -> PizzaPrimaryContainer
                            else -> PizzaSurfaceContainerHigh
                        },
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isPassed) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                } else if (isActive) {
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(MaterialTheme.colorScheme.outlineVariant, CircleShape)
                    )
                }
            }

            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(34.dp)
                        .background(if (isPassed) PizzaTertiary else PizzaSurfaceContainerHighest)
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .then(
                    if (isActive) {
                        Modifier
                            .background(PizzaPrimary.copy(alpha = 0.08f), RoundedCornerShape(10.dp))
                            .padding(8.dp)
                    } else {
                        Modifier.padding(top = 2.dp, bottom = 12.dp)
                    }
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isActive) PizzaPrimary else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = time,
                    fontSize = 11.sp,
                    color = if (isActive) PizzaPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
                )
            }
            Text(
                text = desc,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SummaryProductRow(
    name: String,
    desc: String,
    price: Double,
    img: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            AsyncImage(
                model = img,
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(PizzaSurfaceContainer)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(name, fontSize = 12.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                Text(desc, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
            }
        }
        Text("S/ ${String.format("%.2f", price)}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}
