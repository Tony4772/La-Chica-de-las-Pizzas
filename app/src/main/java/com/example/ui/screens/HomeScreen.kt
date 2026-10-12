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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.AppScreen
import com.example.data.PizzaCategory
import com.example.data.Product
import com.example.ui.PizzaViewModel
import com.example.ui.components.ArtisanalPizzaList
import com.example.ui.components.openWhatsApp
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    viewModel: PizzaViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val products by viewModel.products.collectAsState()
    val artisanalPizzas by viewModel.artisanalPizzas.collectAsState()
    val selectedPizzaCategory by viewModel.selectedPizzaCategory.collectAsState()
    val deliveryAddress by viewModel.deliveryAddress.collectAsState()

    var showAddressDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
            .testTag("home_screen")
    ) {
        // Location Selector Bar
        Surface(
            color = PizzaSurfaceContainerLow,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable { showAddressDialog = true }
                .testTag("location_selector_bar")
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(PizzaPrimary.copy(alpha = 0.12f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = PizzaPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Entregar en",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Icon(
                                imageVector = Icons.Default.ExpandMore,
                                contentDescription = null,
                                tint = PizzaSecondary,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                        Text(
                            text = deliveryAddress.split("•").firstOrNull()?.trim() ?: "Miraflores, Lima",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Surface(
                    color = PizzaSurfaceContainerHigh,
                    shape = CircleShape
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Timer,
                            contentDescription = null,
                            tint = PizzaTertiary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "25-35 min",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        // Hero Promo Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .testTag("hero_promo_card")
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                PizzaPrimary,
                                PizzaPrimaryContainer,
                                PizzaSecondary
                            )
                        )
                    )
                    .padding(20.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Surface(
                            color = PizzaPrimaryFixed.copy(alpha = 0.25f),
                            shape = CircleShape
                        ) {
                            Text(
                                text = "¡PROMO DE LA SEMANA!",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = PizzaPrimaryFixed,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                letterSpacing = 0.5.sp
                            )
                        }

                        Surface(
                            color = PizzaSecondaryContainer,
                            shape = CircleShape
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.LocalFireDepartment,
                                    contentDescription = null,
                                    tint = PizzaOnSecondaryContainer,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = "-20% DCTO",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = PizzaOnSecondaryContainer
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Box x12 Minipizzas + 2 Gaseosas",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Masa madre artesanal doradita, horneada al instante para compartir en familia o con patas.",
                        style = MaterialTheme.typography.bodySmall,
                        color = PizzaPrimaryFixed.copy(alpha = 0.9f)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "S/ 45.90",
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                            color = Color.White
                        )
                        Text(
                            text = "S/ 58.00",
                            style = MaterialTheme.typography.bodyMedium,
                            textDecoration = TextDecoration.LineThrough,
                            color = PizzaPrimaryFixed.copy(alpha = 0.7f),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { viewModel.addPromoCombo() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = PizzaPrimary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("btn_promo_combo")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AddShoppingCart,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Pedir Combo",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }

                        Surface(
                            color = Color.White.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.size(46.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Filled.LocalPizza,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // WhatsApp Quick Support Banner
        Surface(
            color = PizzaTertiary.copy(alpha = 0.08f),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .clickable { openWhatsApp(context, "Hola! Quiero hacer un pedido especial de pizzas") }
                .testTag("whatsapp_quick_support_banner")
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .background(PizzaTertiary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(19.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = "¿Dudas o pedidos especiales?",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Escríbenos al WhatsApp 934 398 066",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PizzaTertiary
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(PizzaTertiary.copy(alpha = 0.15f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = PizzaTertiary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Category Chips
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Explora la Carta",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "4 Categorías",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CategoryChip(
                    label = "Boxes Minipizzas",
                    badge = "Popular",
                    isSelected = selectedCategory == "boxes",
                    onClick = { viewModel.selectCategory("boxes") },
                    testTag = "chip_boxes"
                )
                CategoryChip(
                    icon = Icons.Outlined.PieChart,
                    label = "Pizzas Grandes (35cm)",
                    isSelected = selectedCategory == "grandes",
                    onClick = { viewModel.selectCategory("grandes") },
                    testTag = "chip_grandes"
                )
                CategoryChip(
                    icon = Icons.Outlined.Celebration,
                    label = "Combos & Duplas",
                    isSelected = selectedCategory == "combos",
                    onClick = { viewModel.selectCategory("combos") },
                    testTag = "chip_combos"
                )
                CategoryChip(
                    icon = Icons.Outlined.LocalBar,
                    label = "Bebidas & Salsas",
                    isSelected = selectedCategory == "bebidas",
                    onClick = { viewModel.selectCategory("bebidas") },
                    testTag = "chip_bebidas"
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Available Artisanal Pizza Options List Component
        ArtisanalPizzaList(
            pizzas = artisanalPizzas,
            selectedCategory = selectedPizzaCategory,
            onCategorySelected = { viewModel.selectPizzaCategory(it) },
            onPizzaSelected = { pizza ->
                if (pizza.isCustomizable) {
                    viewModel.navigateTo(AppScreen.CUSTOMIZER)
                } else {
                    viewModel.addArtisanalPizzaToCart(pizza)
                }
            },
            onAddToCart = { pizza ->
                viewModel.addArtisanalPizzaToCart(pizza)
            },
            onCustomize = {
                viewModel.navigateTo(AppScreen.CUSTOMIZER)
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Salsas de la Casa Section
        Surface(
            color = PizzaSurfaceContainer,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag("salsas_section")
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Liquor,
                            contentDescription = null,
                            tint = PizzaPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Nuestras Salsas de la Casa",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Surface(
                        color = PizzaPrimary.copy(alpha = 0.12f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "S/ 3.50 c/u",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PizzaPrimary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Recetas secretas hechas a mano para darle el toque crocante perfecto a tus bordes.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    SalsaItem(
                        name = "Rocoto Parrillero",
                        desc = "Picante equilibrado ahumado",
                        icon = Icons.Default.LocalFireDepartment,
                        iconTint = PizzaPrimary,
                        iconBg = PizzaPrimary.copy(alpha = 0.12f),
                        onAdd = {
                            viewModel.addProductToCart(
                                Product(
                                    id = "salsa_rocoto",
                                    name = "Salsa Rocoto Parrillero",
                                    description = "Picante equilibrado ahumado",
                                    price = 3.50,
                                    image = "",
                                    category = "salsas"
                                )
                            )
                        }
                    )
                    SalsaItem(
                        name = "Ajo Confitado Cremoso",
                        desc = "Suave y caramelizado al horno",
                        icon = Icons.Default.Restaurant,
                        iconTint = PizzaSecondary,
                        iconBg = PizzaSecondaryContainer.copy(alpha = 0.2f),
                        onAdd = {
                            viewModel.addProductToCart(
                                Product(
                                    id = "salsa_ajo",
                                    name = "Salsa Ajo Confitado",
                                    description = "Suave y caramelizado al horno",
                                    price = 3.50,
                                    image = "",
                                    category = "salsas"
                                )
                            )
                        }
                    )
                    SalsaItem(
                        name = "Chimichurri Artesanal",
                        desc = "Hierbas frescas & aceite de oliva",
                        icon = Icons.Default.Eco,
                        iconTint = PizzaTertiary,
                        iconBg = PizzaTertiary.copy(alpha = 0.12f),
                        onAdd = {
                            viewModel.addProductToCart(
                                Product(
                                    id = "salsa_chimichurri",
                                    name = "Salsa Chimichurri Artesanal",
                                    description = "Hierbas frescas & aceite de oliva",
                                    price = 3.50,
                                    image = "",
                                    category = "salsas"
                                )
                            )
                        }
                    )
                }
            }
        }
    }

    // Address Change Dialog
    if (showAddressDialog) {
        val limaDistricts = listOf(
            "Calle Las Poncianas 245, Dpto 402, Miraflores",
            "Av. José Pardo 640, Miraflores",
            "Av. Las Flores 320, San Isidro",
            "Malecón Armendáriz 180, Barranco",
            "Av. El Polo 405, Surco"
        )
        AlertDialog(
            onDismissRequest = { showAddressDialog = false },
            title = {
                Text("Elegir Dirección de Entrega en Lima", fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Selecciona tu zona con cobertura express:",
                        style = MaterialTheme.typography.bodySmall
                    )
                    limaDistricts.forEach { dist ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (deliveryAddress.startsWith(dist.take(15))) PizzaPrimary.copy(alpha = 0.1f) else PizzaSurfaceContainerHigh,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setDeliveryAddress("$dist, Lima • Perú")
                                    showAddressDialog = false
                                }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = PizzaPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = dist,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showAddressDialog = false }) {
                    Text("Cerrar", color = PizzaPrimary)
                }
            }
        )
    }
}

@Composable
fun CategoryChip(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    badge: String? = null,
    isSelected: Boolean,
    testTag: String,
    onClick: () -> Unit
) {
    Surface(
        color = if (isSelected) PizzaPrimary else PizzaSurfaceContainerHigh,
        shape = RoundedCornerShape(24.dp),
        shadowElevation = if (isSelected) 2.dp else 0.dp,
        modifier = Modifier
            .clickable(onClick = onClick)
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
            }
            Text(
                text = label,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (badge != null) {
                Spacer(modifier = Modifier.width(6.dp))
                Surface(
                    color = PizzaSecondaryContainer,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = badge,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PizzaOnSecondaryContainer,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    onCustomize: () -> Unit,
    onAdd: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = PizzaSurfaceContainerLowest),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("product_card_${product.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Image with Badges
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(176.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(PizzaSurfaceContainer)
            ) {
                AsyncImage(
                    model = product.image,
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Top badge
                if (product.badge != null) {
                    Surface(
                        color = PizzaSecondaryContainer,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.TopStart)
                    ) {
                        Text(
                            text = product.badge,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PizzaOnSecondaryContainer,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                // Rating Pill
                Surface(
                    color = PizzaInverseSurface.copy(alpha = 0.85f),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = StarGold,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = product.rating.toString(),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PizzaInverseOnSurface
                        )
                        Text(
                            text = " (${product.reviewsCount})",
                            fontSize = 10.sp,
                            color = PizzaInverseOnSurface.copy(alpha = 0.75f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Title & Price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "S/ ${String.format("%.2f", product.price)}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PizzaPrimary
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = product.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Action row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (product.isCustomizable) {
                    Row(
                        modifier = Modifier
                            .clickable(onClick = onCustomize)
                            .testTag("personalizable_link_${product.id}"),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = null,
                            tint = PizzaTertiary,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Personalizable",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = PizzaTertiary
                        )
                    }
                } else if (product.portionsInfo != null) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocalPizza,
                            contentDescription = null,
                            tint = PizzaSecondary,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = product.portionsInfo,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.width(1.dp))
                }

                Button(
                    onClick = onAdd,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PizzaPrimary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = Modifier
                        .height(40.dp)
                        .testTag("add_product_button_${product.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Agregar",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
fun SalsaItem(
    name: String,
    desc: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    iconBg: Color,
    onAdd: () -> Unit
) {
    Surface(
        color = PizzaSurfaceContainerLowest,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .background(iconBg, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = name,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = desc,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            IconButton(
                onClick = onAdd,
                modifier = Modifier
                    .size(36.dp)
                    .background(PizzaSurfaceContainerHigh, CircleShape)
                    .testTag("add_salsa_${name.lowercase().replace(" ", "_")}")
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar $name",
                    tint = PizzaPrimary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
