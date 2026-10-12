package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.AppScreen
import com.example.ui.PizzaViewModel
import com.example.ui.components.openWhatsApp
import com.example.ui.theme.*

@Composable
fun AdminScreen(
    viewModel: PizzaViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isStoreOpen by viewModel.isStoreOpen.collectAsState()
    val ordersToday by viewModel.ordersCountToday.collectAsState()
    val salesToday by viewModel.totalSalesToday.collectAsState()
    val editingProduct by viewModel.editingProduct.collectAsState()
    val products by viewModel.products.collectAsState()

    var editingName by remember(editingProduct) { mutableStateOf(editingProduct.name) }
    var editingDesc by remember(editingProduct) { mutableStateOf(editingProduct.description) }
    var selectedAdminCategory by remember { mutableStateOf("boxes") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
            .testTag("admin_screen")
    ) {
        // Operational Status Hero Bar
        Surface(
            color = PizzaSurfaceContainerLow,
            shape = RoundedCornerShape(18.dp),
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
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(if (isStoreOpen) PizzaTertiary else MaterialTheme.colorScheme.error, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Modo Dueña • En Vivo",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Surface(
                        color = PizzaSecondaryContainer.copy(alpha = 0.2f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Sede Miraflores",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = PizzaSecondary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Quick Store Toggle Card
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
                                    .size(36.dp)
                                    .background(
                                        if (isStoreOpen) PizzaTertiaryContainer.copy(alpha = 0.2f) else MaterialTheme.colorScheme.error.copy(alpha = 0.15f),
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Storefront,
                                    contentDescription = null,
                                    tint = if (isStoreOpen) PizzaTertiary else MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = if (isStoreOpen) "Local Abierto" else "Local Cerrado / Pausado",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isStoreOpen) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error
                                )
                                Text(
                                    text = if (isStoreOpen) "Recibiendo pedidos WhatsApp & Yape" else "Pedidos pausados temporalmente",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Switch(
                            checked = isStoreOpen,
                            onCheckedChange = { viewModel.toggleStore() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = PizzaTertiary
                            ),
                            modifier = Modifier.testTag("admin_toggle_store")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Live Metrics Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        color = PizzaSurfaceContainerLowest,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Pedidos hoy", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Icon(
                                    imageVector = Icons.Default.LocalPizza,
                                    contentDescription = null,
                                    tint = PizzaPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = "$ordersToday",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "+3 ult. hora",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PizzaTertiary,
                                    modifier = Modifier.padding(bottom = 2.dp)
                                )
                            }
                        }
                    }

                    Surface(
                        color = PizzaSurfaceContainerLowest,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Total vendido", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Icon(
                                    imageVector = Icons.Default.Payments,
                                    contentDescription = null,
                                    tint = PizzaTertiary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text("S/ ", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(
                                    text = String.format("%.2f", salesToday),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PizzaPrimary
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section Title & Add Button
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Gestionar Carta y Precios",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "4 Categorías",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Button(
                onClick = { viewModel.showToast("Formulario para nueva pizza listo") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PizzaSurfaceContainerHighest,
                    contentColor = PizzaPrimary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .testTag("admin_add_pizza_button")
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("+ Nueva Pizza o Box de Degustación", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }

        // Live Editor Card
        Surface(
            color = PizzaSurfaceContainerLowest,
            shape = RoundedCornerShape(18.dp),
            shadowElevation = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .testTag("admin_live_editor")
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = PizzaPrimaryFixed,
                            shape = CircleShape
                        ) {
                            Text(
                                text = "Editando Ahora",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = PizzaOnPrimaryFixedVariant,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = PizzaTertiary,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Photo preview
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(PizzaSurfaceContainerLow)
                ) {
                    AsyncImage(
                        model = editingProduct.image,
                        contentDescription = editingProduct.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .background(Color.Black.copy(alpha = 0.5f))
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { viewModel.showToast("Cámara activada") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.9f), contentColor = PizzaPrimary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(36.dp)
                        ) {
                            Icon(Icons.Default.PhotoCamera, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Tomar Foto", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = { viewModel.showToast("Abriendo Galería") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.9f), contentColor = PizzaSecondary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(36.dp)
                        ) {
                            Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Galería", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Name input
                Text("Nombre del Producto", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                OutlinedTextField(
                    value = editingName,
                    onValueChange = {
                        editingName = it
                        viewModel.setEditingName(it)
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("admin_product_name_input")
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Category chips
                Text("Categoría de la Carta", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("Boxes Minipizzas", "Pizzas Grandes", "Salsas", "Bebidas").forEach { cat ->
                        val isSelected = selectedAdminCategory.lowercase() in cat.lowercase()
                        Surface(
                            color = if (isSelected) PizzaPrimary else PizzaSurfaceContainerHigh,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.clickable { selectedAdminCategory = cat }
                        ) {
                            Text(
                                text = cat,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Pricing row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Current price with steppers
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Precio Actual (S/)", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Surface(
                            color = PizzaSurfaceContainerLow,
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("S/ ", fontSize = 12.sp, color = PizzaPrimary, fontWeight = FontWeight.Bold)
                                    Text(
                                        text = String.format("%.2f", editingProduct.price),
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Column {
                                    IconButton(
                                        onClick = { viewModel.updateEditingPrice(0.50) },
                                        modifier = Modifier.size(20.dp)
                                    ) {
                                        Icon(Icons.Default.ExpandLess, contentDescription = null, modifier = Modifier.size(16.dp))
                                    }
                                    IconButton(
                                        onClick = { viewModel.updateEditingPrice(-0.50) },
                                        modifier = Modifier.size(20.dp)
                                    ) {
                                        Icon(Icons.Default.ExpandMore, contentDescription = null, modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }
                    }

                    // Promo / Old price
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Precio Normal / Promo", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Surface(
                            color = PizzaSurfaceContainerLow,
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("S/ ", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(
                                    text = "32.00",
                                    fontSize = 14.sp,
                                    textDecoration = TextDecoration.LineThrough,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Description input
                Text("Descripción Tentadora", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                OutlinedTextField(
                    value = editingDesc,
                    onValueChange = {
                        editingDesc = it
                        viewModel.setEditingDescription(it)
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Availability toggle
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
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = PizzaTertiary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("Disponible en carta", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text("Visible inmediatamente a clientes", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }

                        Switch(
                            checked = editingProduct.stockActive,
                            onCheckedChange = { viewModel.toggleEditingStock() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = PizzaTertiary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { viewModel.navigateTo(AppScreen.HOME) },
                        colors = ButtonDefaults.buttonColors(containerColor = PizzaSurfaceContainerHigh, contentColor = MaterialTheme.colorScheme.onSurface),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                    ) {
                        Text("Descartar", fontSize = 12.sp)
                    }

                    Button(
                        onClick = { viewModel.saveAdminProduct() },
                        colors = ButtonDefaults.buttonColors(containerColor = PizzaPrimaryContainer),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(2f)
                            .height(44.dp)
                            .testTag("admin_save_changes_btn")
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Guardar Cambios", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }

        // Other Products in Menu
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Otros Productos en Carta",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Toca para editar",
                    fontSize = 11.sp,
                    color = PizzaPrimary
                )
            }

            products.forEach { prod ->
                Surface(
                    color = PizzaSurfaceContainerLowest,
                    shape = RoundedCornerShape(12.dp),
                    shadowElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            AsyncImage(
                                model = prod.image,
                                contentDescription = prod.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(PizzaSurfaceContainer)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = prod.name,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "S/ ${String.format("%.2f", prod.price)}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PizzaPrimary
                                    )
                                    Text(" • ", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                                    Text(
                                        text = "Stock Activo",
                                        fontSize = 10.sp,
                                        color = PizzaTertiary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }

                        Button(
                            onClick = { viewModel.showToast("Cargando ${prod.name} en editor") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PizzaSurfaceContainerHigh,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text("Editar", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // WhatsApp Support channel for store owner
        Surface(
            color = PizzaSurfaceContainer,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(PizzaTertiary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SupportAgent,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "¿Dudas con la carta o pagos Yape?",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Atención directa al canal oficial de soporte para locales.",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Button(
                        onClick = { openWhatsApp(context, "Hola, necesito soporte en el panel de administradora") },
                        colors = ButtonDefaults.buttonColors(containerColor = PizzaTertiary),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("WhatsApp Soporte: 934 398 066", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
