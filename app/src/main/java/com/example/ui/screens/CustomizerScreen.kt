package com.example.ui.screens

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.MiniPizzaFlavor
import com.example.data.PizzaRepository
import com.example.ui.PizzaViewModel
import com.example.ui.theme.*

@Composable
fun CustomizerScreen(
    viewModel: PizzaViewModel,
    modifier: Modifier = Modifier
) {
    val flavors by viewModel.flavors.collectAsState()
    val selectedCrustId by viewModel.selectedCrustId.collectAsState()
    val selectedSauceId by viewModel.selectedSauceId.collectAsState()
    val boxQuantity by viewModel.boxQuantity.collectAsState()
    val notes by viewModel.customizerNotes.collectAsState()

    val totalSelected = flavors.sumOf { it.quantity }
    val isComplete = totalSelected == 6

    var isFavorite by remember { mutableStateOf(false) }

    val basePrice = 26.90
    val flavorExtras = flavors.sumOf { it.quantity * it.extraPrice }
    val crustExtra = if (selectedCrustId == "relleno") 4.0 else 0.0
    val singleBoxPrice = basePrice + flavorExtras + crustExtra
    val grandTotal = singleBoxPrice * boxQuantity

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 96.dp)
                .testTag("customizer_screen")
        ) {
            // Hero Sensory Image Showcase
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(PizzaSurfaceContainer)
            ) {
                AsyncImage(
                    model = PizzaRepository.CUSTOMIZER_HERO_URL,
                    contentDescription = "Box Degustación",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Dark gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.35f),
                                    Color.Black.copy(alpha = 0.85f)
                                )
                            )
                        )
                )

                // Top floating action tags
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.TopCenter),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = Color.White.copy(alpha = 0.9f),
                        shape = CircleShape
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocalFireDepartment,
                                contentDescription = null,
                                tint = PizzaPrimary,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Horno de Leña",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = PizzaPrimary
                            )
                        }
                    }

                    IconButton(
                        onClick = { isFavorite = !isFavorite },
                        modifier = Modifier
                            .size(38.dp)
                            .background(Color.White.copy(alpha = 0.9f), CircleShape)
                            .testTag("customizer_favorite_button")
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = PizzaPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Product Title overlay
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.BottomStart)
                ) {
                    Surface(
                        color = PizzaSecondaryContainer,
                        shape = CircleShape
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = null,
                                tint = PizzaOnSecondaryContainer,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Masa Madre Fermentación 48h",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = PizzaOnSecondaryContainer
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Box Degustación x6 Minipizzas Artesanales",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "S/ 26.90",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = PizzaSecondaryFixed
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "• Rinde 2 a 3 personas",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }
                }
            }

            // Main Content Area
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Value Proposition Callout
                Surface(
                    color = PizzaSurfaceContainerLow,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(PizzaPrimary.copy(alpha = 0.12f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Palette,
                                contentDescription = null,
                                tint = PizzaPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Arma tu combinación ideal",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Distribuye exactamente 6 unidades entre tus sabores predilectos recién salidos del horno.",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // STEP 1: Sabores selector
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "1. Distribuye tus 6 Minipizzas",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Combina hasta 3 sabores distintos",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        val badgeColor = when {
                            isComplete -> PizzaTertiary
                            totalSelected < 6 -> PizzaSecondary
                            else -> MaterialTheme.colorScheme.error
                        }
                        val badgeBg = when {
                            isComplete -> PizzaTertiary.copy(alpha = 0.12f)
                            totalSelected < 6 -> PizzaSecondaryContainer.copy(alpha = 0.2f)
                            else -> MaterialTheme.colorScheme.error.copy(alpha = 0.12f)
                        }
                        val badgeText = when {
                            isComplete -> "6 de 6 seleccionadas ✓"
                            totalSelected < 6 -> "Faltan ${6 - totalSelected} unidades"
                            else -> "Excediste por ${totalSelected - 6}"
                        }

                        Surface(
                            color = badgeBg,
                            shape = CircleShape
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (isComplete) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = badgeColor,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                }
                                Text(
                                    text = badgeText,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = badgeColor
                                )
                            }
                        }
                    }

                    // Progress Bar
                    LinearProgressIndicator(
                        progress = { (totalSelected / 6f).coerceIn(0f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = if (isComplete) PizzaTertiary else PizzaSecondaryContainer,
                        trackColor = PizzaSurfaceContainerHighest,
                    )

                    // Flavors List
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        flavors.forEach { flavor ->
                            FlavorRowItem(
                                flavor = flavor,
                                onIncrement = { viewModel.incrementFlavor(flavor.id) },
                                onDecrement = { viewModel.decrementFlavor(flavor.id) }
                            )
                        }
                    }
                }

                // STEP 2: Crust Selection
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "2. Elige el Estilo de Masa",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Obligatorio",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    PizzaRepository.crustStyles.forEach { crust ->
                        val isSelected = selectedCrustId == crust.id
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = PizzaSurfaceContainerLowest,
                            shadowElevation = if (isSelected) 2.dp else 0.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.selectCrust(crust.id) }
                                .testTag("crust_option_${crust.id}")
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(20.dp)
                                            .background(
                                                if (isSelected) PizzaPrimary else PizzaSurfaceContainerHighest,
                                                CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isSelected) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(13.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = crust.name,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = crust.description,
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Text(
                                    text = if (crust.extraPrice > 0) "+S/ 4.00" else "Incluido",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (crust.extraPrice > 0) PizzaPrimary else PizzaTertiary
                                )
                            }
                        }
                    }
                }

                // STEP 3: Sauce Selection
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "3. Salsa Artesanal de la Casa",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "1 porción incluida para tus bordes",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Surface(
                            color = PizzaTertiary.copy(alpha = 0.12f),
                            shape = CircleShape
                        ) {
                            Text(
                                text = "Gratis",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = PizzaTertiary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PizzaRepository.sauces.forEach { sauce ->
                            val isSelected = selectedSauceId == sauce.id
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = if (isSelected) PizzaPrimary else PizzaSurfaceContainerLowest,
                                shadowElevation = if (isSelected) 2.dp else 1.dp,
                                modifier = Modifier
                                    .clickable { viewModel.selectSauce(sauce.id) }
                                    .testTag("sauce_chip_${sauce.id}")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = if (sauce.isFavorite) Icons.Default.Whatshot else Icons.Default.Restaurant,
                                        contentDescription = null,
                                        tint = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = sauce.name.take(20),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }

                // STEP 4: Special Notes
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.EditNote,
                            contentDescription = null,
                            tint = PizzaPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Notas para la Maestra Pizzera",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    OutlinedTextField(
                        value = notes,
                        onValueChange = { viewModel.updateCustomizerNotes(it) },
                        placeholder = {
                            Text(
                                "Ej: Bien doraditas, orégano aparte, masa bien crocante...",
                                fontSize = 12.sp
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(84.dp)
                            .testTag("customizer_notes_input")
                    )
                }

                // Trust Stamp
                Surface(
                    color = PizzaSurfaceContainerHigh,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .background(YapePurple, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Y",
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 13.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Aceptamos Yape & Plin",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Pago rápido sin comisiones extras",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = null,
                            tint = PizzaTertiary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }

        // Sticky Bottom Interactive Floating Panel
        Surface(
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.98f),
            tonalElevation = 6.dp,
            shadowElevation = 8.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .testTag("customizer_bottom_bar")
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Box Qty Stepper
                Surface(
                    color = PizzaSurfaceContainer,
                    shape = CircleShape
                ) {
                    Row(
                        modifier = Modifier.padding(3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { viewModel.decrementBoxQty() },
                            modifier = Modifier
                                .size(32.dp)
                                .background(PizzaSurfaceContainerLowest, CircleShape)
                                .testTag("box_qty_minus")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = "Menos",
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Text(
                            text = boxQuantity.toString(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )

                        IconButton(
                            onClick = { viewModel.incrementBoxQty() },
                            modifier = Modifier
                                .size(32.dp)
                                .background(PizzaSurfaceContainerLowest, CircleShape)
                                .testTag("box_qty_plus")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Más",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                // Add to Cart Button
                Button(
                    onClick = { viewModel.addCustomBoxToCart() },
                    enabled = isComplete,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PizzaPrimaryContainer,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("add_custom_box_button")
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
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Añadir al Carrito",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                        Text(
                            text = "S/ ${String.format("%.2f", grandTotal)}",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FlavorRowItem(
    flavor: MiniPizzaFlavor,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Surface(
        color = PizzaSurfaceContainerLowest,
        shape = RoundedCornerShape(14.dp),
        shadowElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("flavor_row_${flavor.id}")
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
                AsyncImage(
                    model = flavor.image,
                    contentDescription = flavor.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(PizzaSurfaceContainer)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = flavor.name,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1
                        )
                        if (flavor.isChefPick) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = PizzaSecondaryContainer.copy(alpha = 0.25f),
                                shape = CircleShape
                            ) {
                                Text(
                                    text = "Chef pick",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PizzaSecondary,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    if (flavor.extraPrice > 0) {
                        Text(
                            text = "+S/ ${String.format("%.2f", flavor.extraPrice)} por porción",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PizzaPrimary
                        )
                    } else {
                        Text(
                            text = flavor.description,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1
                        )
                    }
                }
            }

            // Stepper controls
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onDecrement,
                    modifier = Modifier
                        .size(34.dp)
                        .background(PizzaSurfaceContainer, CircleShape)
                        .testTag("flavor_minus_${flavor.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Menos",
                        modifier = Modifier.size(16.dp)
                    )
                }

                Text(
                    text = flavor.quantity.toString(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )

                IconButton(
                    onClick = onIncrement,
                    modifier = Modifier
                        .size(34.dp)
                        .background(PizzaPrimaryContainer, CircleShape)
                        .testTag("flavor_plus_${flavor.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Más",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
