package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import com.example.data.CartItem
import com.example.data.PaymentMethod
import com.example.ui.PizzaViewModel
import com.example.ui.theme.*

@Composable
fun CartScreen(
    viewModel: PizzaViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val cartItems by viewModel.cartItems.collectAsState()
    val couponCode by viewModel.couponCode.collectAsState()
    val isCouponApplied by viewModel.isCouponApplied.collectAsState()
    val paymentMethod by viewModel.paymentMethod.collectAsState()
    val deliveryAddress by viewModel.deliveryAddress.collectAsState()
    val deliveryReference by viewModel.deliveryReference.collectAsState()
    val cashAmount by viewModel.cashAmount.collectAsState()

    val subtotal = viewModel.calculateSubtotal()
    val discount = if (isCouponApplied) subtotal * 0.10 else 0.0
    val total = (subtotal - discount + 5.0).coerceAtLeast(0.0)

    var showChangeAddressDialog by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 120.dp)
                .testTag("cart_screen")
        ) {
            // Progress Tracker Banner
            Surface(
                color = PizzaSurfaceContainer,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.LocalFireDepartment,
                                contentDescription = null,
                                tint = PizzaPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "¡Olor a masa fresca en camino!",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Surface(
                            color = PizzaTertiaryFixed.copy(alpha = 0.4f),
                            shape = CircleShape
                        ) {
                            Text(
                                text = "Paso 2 de 2",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = PizzaTertiary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { 1f },
                        color = PizzaPrimary,
                        trackColor = PizzaSurfaceContainerHighest,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Revisa tus delicias de horno de barro antes de hornear.",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Cart Items List
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ShoppingBag,
                            contentDescription = null,
                            tint = PizzaPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Tus Pizzas & Antojos",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            color = PizzaPrimaryFixed,
                            shape = CircleShape
                        ) {
                            Text(
                                text = "${cartItems.sumOf { it.quantity }} ítems",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = PizzaOnPrimaryFixedVariant,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }

                    TextButton(
                        onClick = { viewModel.navigateTo(AppScreen.HOME) },
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.EditNote,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Agregar más",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                if (cartItems.isEmpty()) {
                    Surface(
                        color = PizzaSurfaceContainerLowest,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.LocalPizza,
                                contentDescription = null,
                                tint = PizzaPrimary.copy(alpha = 0.5f),
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                "Tu carrito está esperando por pizzas",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = { viewModel.navigateTo(AppScreen.HOME) },
                                colors = ButtonDefaults.buttonColors(containerColor = PizzaPrimary)
                            ) {
                                Text("Ver la Carta")
                            }
                        }
                    }
                } else {
                    cartItems.forEach { item ->
                        CartItemRow(
                            item = item,
                            onIncrement = { viewModel.incrementCartItem(item.id) },
                            onDecrement = { viewModel.decrementCartItem(item.id) },
                            onDelete = { viewModel.removeCartItem(item.id) },
                            onModify = { viewModel.navigateTo(AppScreen.CUSTOMIZER) }
                        )
                    }
                }
            }

            // Coupon Code Section
            Surface(
                color = PizzaSurfaceContainerLowest,
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .testTag("coupon_section")
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Loyalty,
                                contentDescription = null,
                                tint = PizzaSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "¿Tienes un cupón pizzero?",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Text(
                            text = "10% OFF",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PizzaPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = couponCode,
                            onValueChange = { viewModel.setCouponCode(it) },
                            placeholder = { Text("Ingresa código") },
                            trailingIcon = {
                                if (isCouponApplied) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = PizzaTertiary
                                    )
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .testTag("coupon_input")
                        )

                        Button(
                            onClick = { viewModel.applyCoupon() },
                            colors = ButtonDefaults.buttonColors(containerColor = PizzaPrimary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .height(50.dp)
                                .testTag("btn_apply_coupon")
                        ) {
                            Text("Aplicar", fontWeight = FontWeight.Bold)
                        }
                    }

                    if (isCouponApplied) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            color = PizzaTertiary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = null,
                                        tint = PizzaTertiary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Cupón 'CHICAPRIMERA' aplicado (-10% en pizzas)",
                                        fontSize = 11.sp,
                                        color = PizzaTertiary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                                IconButton(
                                    onClick = { viewModel.removeCoupon() },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Quitar",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Delivery Logistics Section
            Surface(
                color = PizzaSurfaceContainerLowest,
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .testTag("delivery_logistics_section")
            ) {
                Column {
                    // Map simulated header
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        PizzaInverseSurface.copy(alpha = 0.6f),
                                        PizzaInverseSurface
                                    )
                                )
                            )
                            .padding(12.dp),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = PizzaInverseSurface.copy(alpha = 0.85f),
                                shape = CircleShape
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint = PizzaPrimary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Zona Miraflores - Cobertura Activa",
                                        fontSize = 11.sp,
                                        color = PizzaInverseOnSurface
                                    )
                                }
                            }

                            Surface(
                                color = PizzaTertiary,
                                shape = CircleShape
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(5.dp)
                                            .background(Color.White, CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "En rango",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }

                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
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
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .background(PizzaPrimaryFixed.copy(alpha = 0.4f), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Apartment,
                                        contentDescription = null,
                                        tint = PizzaPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = deliveryAddress.split(",").firstOrNull() ?: "Calle Las Poncianas 245",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Surface(
                                            color = PizzaSurfaceContainerHighest,
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text(
                                                text = "Depa",
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                            )
                                        }
                                    }
                                    Text(
                                        text = deliveryAddress,
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            TextButton(onClick = { showChangeAddressDialog = true }) {
                                Text(
                                    text = "Cambiar",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PizzaPrimary
                                )
                            }
                        }

                        // Delivery Speed Banner
                        Surface(
                            color = PizzaSurfaceContainer,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Outlined.TwoWheeler,
                                        contentDescription = null,
                                        tint = PizzaTertiary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = "Delivery Express Caliente",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "Mochila térmica sellada (25-35 min)",
                                            fontSize = 10.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Text(
                                    text = "S/ 5.00",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PizzaPrimary
                                )
                            }
                        }

                        // Address Reference Input
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Referencia para el repartidor:",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            OutlinedTextField(
                                value = deliveryReference,
                                onValueChange = { viewModel.setDeliveryReference(it) },
                                singleLine = true,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("delivery_reference_input")
                            )
                        }
                    }
                }
            }

            // Payment Methods Section
            Surface(
                color = PizzaSurfaceContainerLowest,
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .testTag("payment_methods_section")
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Payments,
                                contentDescription = null,
                                tint = PizzaPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Método de Pago",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = PizzaTertiary,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "100% Seguro",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = PizzaTertiary
                            )
                        }
                    }

                    // Option 1: Yape
                    PaymentOptionCard(
                        title = "Paga al toque con QR o Celular",
                        badgeText = "Más Usado",
                        badgeColor = PizzaPrimary,
                        badgeBg = PizzaPrimary.copy(alpha = 0.12f),
                        isSelected = paymentMethod == PaymentMethod.YAPE,
                        onSelect = { viewModel.selectPaymentMethod(PaymentMethod.YAPE) },
                        testTag = "pay_opt_yape",
                        customPill = {
                            Surface(
                                color = YapePurple,
                                shape = CircleShape
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Bolt,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Text(
                                        text = "Yape",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color.White
                                    )
                                }
                            }
                        },
                        expandedContent = {
                            Surface(
                                color = PizzaSurfaceContainer.copy(alpha = 0.7f),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Número Yape oficial:", fontSize = 11.sp)
                                        Text("934 398 066", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Titular registrado:", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Text("La Chica De Las Pizzas", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PizzaPrimary)
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        "*Envías la constancia por WhatsApp con 1 tap al terminar tu orden.",
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    )

                    // Option 2: Plin
                    PaymentOptionCard(
                        title = "Interbank, BBVA, Scotiabank",
                        subtitle = "934 398 066",
                        isSelected = paymentMethod == PaymentMethod.PLIN,
                        onSelect = { viewModel.selectPaymentMethod(PaymentMethod.PLIN) },
                        testTag = "pay_opt_plin",
                        customPill = {
                            Surface(
                                color = PlinCyan,
                                shape = CircleShape
                            ) {
                                Text(
                                    text = "Plin",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF003434),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                    )

                    // Option 3: Tarjeta
                    PaymentOptionCard(
                        title = "Tarjeta Débito o Crédito",
                        subtitle = "POS Inalámbrico en tu puerta (Visa, Mastercard, Amex)",
                        isSelected = paymentMethod == PaymentMethod.TARJETA,
                        onSelect = { viewModel.selectPaymentMethod(PaymentMethod.TARJETA) },
                        testTag = "pay_opt_tarjeta",
                        leadingIcon = Icons.Default.CreditCard
                    )

                    // Option 4: Efectivo
                    PaymentOptionCard(
                        title = "Efectivo contra entrega",
                        subtitle = "Pagas al recibir tu pizza caliente",
                        isSelected = paymentMethod == PaymentMethod.EFECTIVO,
                        onSelect = { viewModel.selectPaymentMethod(PaymentMethod.EFECTIVO) },
                        testTag = "pay_opt_efectivo",
                        leadingIcon = Icons.Default.Payments,
                        expandedContent = {
                            Surface(
                                color = PizzaSurfaceContainer,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text("¿Con cuánto pagas?", fontSize = 11.sp)
                                    OutlinedTextField(
                                        value = cashAmount,
                                        onValueChange = { viewModel.setCashAmount(it) },
                                        prefix = { Text("S/ ", fontWeight = FontWeight.Bold) },
                                        singleLine = true,
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(44.dp)
                                    )
                                }
                            }
                        }
                    )
                }
            }

            // Order Breakdown Section
            Surface(
                color = PizzaSurfaceContainerLowest,
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .testTag("order_breakdown_section")
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Receipt,
                            contentDescription = null,
                            tint = PizzaPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Desglose del Pedido",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Subtotal productos (${cartItems.sumOf { it.quantity }})",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            "S/ ${String.format("%.2f", subtotal)}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    if (isCouponApplied) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Sell,
                                    contentDescription = null,
                                    tint = PizzaTertiary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    "Descuento cupón (CHICAPRIMERA -10%)",
                                    fontSize = 12.sp,
                                    color = PizzaTertiary
                                )
                            }
                            Text(
                                "- S/ ${String.format("%.2f", discount)}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = PizzaTertiary
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Moped,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                "Envío Miraflores Express",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text("S/ 5.00", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 6.dp),
                        color = PizzaSurfaceContainerHighest
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Total a Pagar",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                "IGV y empaque térmico incluidos",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                "S/ ${String.format("%.2f", total)}",
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                                color = PizzaPrimary
                            )
                            if (isCouponApplied) {
                                Text(
                                    "Ahorraste S/ ${String.format("%.2f", discount)} hoy",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PizzaTertiary
                                )
                            }
                        }
                    }
                }
            }
        }

        // Sticky Bottom Checkout Bar
        Surface(
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.98f),
            tonalElevation = 8.dp,
            shadowElevation = 10.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .testTag("checkout_sticky_bar")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // WhatsApp pill
                Surface(
                    color = PizzaTertiary.copy(alpha = 0.1f),
                    shape = CircleShape,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.MarkChatRead,
                            contentDescription = null,
                            tint = PizzaTertiary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Confirmación inmediata y tracking en vivo a tu WhatsApp",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                // CTA Button
                Button(
                    onClick = { viewModel.confirmOrder() },
                    colors = ButtonDefaults.buttonColors(containerColor = PizzaPrimary),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("btn_confirm_order")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocalPizza,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Confirmar y Pedir",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "S/ ${String.format("%.2f", total)}",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    if (showChangeAddressDialog) {
        AlertDialog(
            onDismissRequest = { showChangeAddressDialog = false },
            title = { Text("Cambiar Dirección") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(
                        "Calle Las Poncianas 245, Dpto 402, Miraflores, Lima • Perú",
                        "Av. Larco 850, Miraflores, Lima • Perú",
                        "Calle Cantuarias 160, Miraflores, Lima • Perú"
                    ).forEach { addr ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = PizzaSurfaceContainer,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setDeliveryAddress(addr)
                                    showChangeAddressDialog = false
                                }
                        ) {
                            Text(
                                text = addr,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showChangeAddressDialog = false }) {
                    Text("Listo")
                }
            }
        )
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onDelete: () -> Unit,
    onModify: () -> Unit
) {
    Surface(
        color = PizzaSurfaceContainerLowest,
        shape = RoundedCornerShape(14.dp),
        shadowElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("cart_item_${item.id}")
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AsyncImage(
                model = item.image,
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(68.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(PizzaSurfaceContainer)
            )

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = item.name,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "S/ ${String.format("%.2f", item.price)}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PizzaPrimary
                    )
                }

                if (item.flavorPills.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        item.flavorPills.forEach { pill ->
                            Surface(
                                color = PizzaSurfaceContainer,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = pill,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }

                if (item.sauce != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = null,
                            tint = PizzaSecondaryContainer,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "Salsa: ${item.sauce}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                if (item.portionInfo != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        color = PizzaSecondaryFixed.copy(alpha = 0.5f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = item.portionInfo,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = PizzaOnSecondaryFixed,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (item.flavorPills.isNotEmpty()) {
                        TextButton(
                            onClick = onModify,
                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
                            modifier = Modifier
                                .height(28.dp)
                                .background(PizzaPrimaryFixed.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = null,
                                tint = PizzaPrimary,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                "Modificar sabores",
                                fontSize = 10.sp,
                                color = PizzaPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.width(1.dp))
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = onDecrement,
                            modifier = Modifier
                                .size(28.dp)
                                .background(PizzaSurfaceContainer, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = "Menos",
                                modifier = Modifier.size(14.dp)
                            )
                        }

                        Text(
                            text = item.quantity.toString(),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        IconButton(
                            onClick = onIncrement,
                            modifier = Modifier
                                .size(28.dp)
                                .background(PizzaSurfaceContainer, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Más",
                                modifier = Modifier.size(14.dp)
                            )
                        }

                        IconButton(
                            onClick = onDelete,
                            modifier = Modifier
                                .padding(start = 6.dp)
                                .size(28.dp)
                                .background(PizzaSurfaceContainerHigh, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.DeleteOutline,
                                contentDescription = "Eliminar",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentOptionCard(
    title: String,
    subtitle: String? = null,
    badgeText: String? = null,
    badgeColor: Color = PizzaPrimary,
    badgeBg: Color = PizzaPrimary.copy(alpha = 0.12f),
    isSelected: Boolean,
    onSelect: () -> Unit,
    testTag: String,
    customPill: (@Composable () -> Unit)? = null,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    expandedContent: (@Composable () -> Unit)? = null
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = PizzaSurfaceContainerLowest,
        shadowElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelect)
            .testTag(testTag)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = onSelect,
                        colors = RadioButtonDefaults.colors(selectedColor = PizzaPrimary)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    if (customPill != null) {
                        customPill()
                        Spacer(modifier = Modifier.width(8.dp))
                    } else if (leadingIcon != null) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .background(PizzaSurfaceContainer, RoundedCornerShape(6.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = leadingIcon,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    Column {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        if (subtitle != null) {
                            Text(
                                text = subtitle,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                if (badgeText != null) {
                    Surface(
                        color = badgeBg,
                        shape = CircleShape
                    ) {
                        Text(
                            text = badgeText,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = badgeColor,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            if (isSelected && expandedContent != null) {
                expandedContent()
            }
        }
    }
}
