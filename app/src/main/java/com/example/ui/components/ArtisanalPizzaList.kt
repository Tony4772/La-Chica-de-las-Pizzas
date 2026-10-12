package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.ArtisanalPizza
import com.example.data.PizzaCategory
import com.example.ui.theme.*

/**
 * Reusable list UI component to display available artisanal pizza options,
 * with search, category filtering, dietary tags, ingredient highlights,
 * and quick cart/customization actions.
 */
@Composable
fun ArtisanalPizzaList(
    pizzas: List<ArtisanalPizza>,
    selectedCategory: PizzaCategory,
    onCategorySelected: (PizzaCategory) -> Unit,
    onPizzaSelected: (ArtisanalPizza) -> Unit,
    onAddToCart: (ArtisanalPizza) -> Unit,
    onCustomize: (ArtisanalPizza) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var onlyVegetarian by remember { mutableStateOf(false) }
    var onlyChefPick by remember { mutableStateOf(false) }

    val filteredPizzas = remember(pizzas, selectedCategory, searchQuery, onlyVegetarian, onlyChefPick) {
        pizzas.filter { pizza ->
            val matchesCategory = when (selectedCategory) {
                PizzaCategory.ALL -> true
                else -> pizza.category == selectedCategory
            }
            val matchesSearch = searchQuery.isBlank() ||
                    pizza.name.contains(searchQuery, ignoreCase = true) ||
                    pizza.description.contains(searchQuery, ignoreCase = true) ||
                    pizza.ingredients.any { it.contains(searchQuery, ignoreCase = true) }
            val matchesVeg = !onlyVegetarian || pizza.isVegetarian
            val matchesChef = !onlyChefPick || pizza.isChefPick

            matchesCategory && matchesSearch && matchesVeg && matchesChef
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("artisanal_pizza_list")
    ) {
        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Buscar pizzas, ingredientes o masas...", fontSize = 13.sp) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar",
                    tint = PizzaPrimary,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Limpiar búsqueda",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = PizzaSurfaceContainerLowest,
                unfocusedContainerColor = PizzaSurfaceContainerLow,
                focusedBorderColor = PizzaPrimary,
                unfocusedBorderColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .height(52.dp)
                .testTag("pizza_search_input")
        )

        // Category Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PizzaCategory.values().forEach { category ->
                val isSelected = selectedCategory == category
                val count = if (category == PizzaCategory.ALL) pizzas.size else pizzas.count { it.category == category }
                Surface(
                    color = if (isSelected) PizzaPrimary else PizzaSurfaceContainerHigh,
                    shape = RoundedCornerShape(24.dp),
                    shadowElevation = if (isSelected) 2.dp else 0.dp,
                    modifier = Modifier
                        .clickable { onCategorySelected(category) }
                        .testTag("cat_chip_${category.id}")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = category.title,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            color = if (isSelected) Color.White.copy(alpha = 0.25f) else PizzaSurfaceContainerHighest,
                            shape = CircleShape
                        ) {
                            Text(
                                text = count.toString(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }

        // Dietary & Chef Pick Quick Toggles
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterToggleChip(
                label = "Vegetarianas",
                icon = Icons.Default.Eco,
                isActive = onlyVegetarian,
                activeColor = PizzaTertiary,
                activeBg = PizzaTertiary.copy(alpha = 0.12f),
                onClick = { onlyVegetarian = !onlyVegetarian }
            )

            FilterToggleChip(
                label = "Chef Pick",
                icon = Icons.Default.Star,
                isActive = onlyChefPick,
                activeColor = PizzaSecondary,
                activeBg = PizzaSecondaryContainer.copy(alpha = 0.2f),
                onClick = { onlyChefPick = !onlyChefPick }
            )
        }

        // Section Results Count Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(width = 4.dp, height = 16.dp)
                        .background(PizzaPrimary, RoundedCornerShape(2.dp))
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Opciones Artesanales Disponibles",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Text(
                text = "${filteredPizzas.size} variedad${if (filteredPizzas.size == 1) "" else "es"}",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.SemiBold
            )
        }

        // List of Pizza Cards
        if (filteredPizzas.isEmpty()) {
            Surface(
                color = PizzaSurfaceContainerLowest,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Outlined.SearchOff,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(44.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "No se encontraron pizzas con estos filtros",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Prueba seleccionando otra categoría o borrando tu búsqueda.",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            searchQuery = ""
                            onlyVegetarian = false
                            onlyChefPick = false
                            onCategorySelected(PizzaCategory.ALL)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PizzaPrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Restablecer Filtros", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                filteredPizzas.forEach { pizza ->
                    ArtisanalPizzaCard(
                        pizza = pizza,
                        onClick = { onPizzaSelected(pizza) },
                        onAddToCart = { onAddToCart(pizza) },
                        onCustomize = { onCustomize(pizza) }
                    )
                }
            }
        }
    }
}

/**
 * Modern Material 3 card presenting an artisanal pizza with all its sensory details.
 */
@Composable
fun ArtisanalPizzaCard(
    pizza: ArtisanalPizza,
    onClick: () -> Unit,
    onAddToCart: () -> Unit,
    onCustomize: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = PizzaSurfaceContainerLowest),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("artisanal_pizza_card_${pizza.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Hero Image with Badges
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(176.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(PizzaSurfaceContainer)
            ) {
                AsyncImage(
                    model = pizza.image,
                    contentDescription = pizza.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Top Left Badges (Fermentation & Chef Pick)
                Column(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (pizza.badge != null) {
                        Surface(
                            color = PizzaSecondaryContainer,
                            shape = RoundedCornerShape(20.dp),
                            shadowElevation = 2.dp
                        ) {
                            Text(
                                text = pizza.badge,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = PizzaOnSecondaryContainer,
                                modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp)
                            )
                        }
                    }

                    if (pizza.isChefPick) {
                        Surface(
                            color = PizzaPrimary.copy(alpha = 0.9f),
                            shape = RoundedCornerShape(20.dp),
                            shadowElevation = 2.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = StarGold,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = "Chef Pick",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                // Top Right Dietary Badges (Veggie / Spicy)
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (pizza.isVegetarian) {
                        Surface(
                            color = PizzaTertiary.copy(alpha = 0.9f),
                            shape = CircleShape
                        ) {
                            Icon(
                                imageVector = Icons.Default.Eco,
                                contentDescription = "Vegetariana",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(26.dp)
                                    .padding(5.dp)
                            )
                        }
                    }
                    if (pizza.isSpicy) {
                        Surface(
                            color = PizzaPrimary.copy(alpha = 0.9f),
                            shape = CircleShape
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = "Picante",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(26.dp)
                                    .padding(5.dp)
                            )
                        }
                    }
                }

                // Bottom Right Rating Pill
                Surface(
                    color = PizzaInverseSurface.copy(alpha = 0.88f),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp)
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
                            text = pizza.rating.toString(),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PizzaInverseOnSurface
                        )
                        Text(
                            text = " (${pizza.reviewsCount})",
                            fontSize = 10.sp,
                            color = PizzaInverseOnSurface.copy(alpha = 0.75f)
                        )
                    }
                }

                // Bottom Left Size / Slices Pill
                Surface(
                    color = PizzaInverseSurface.copy(alpha = 0.85f),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalPizza,
                            contentDescription = null,
                            tint = PizzaSecondaryContainer,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = pizza.size.label,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = PizzaInverseOnSurface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Pizza Name and Pricing Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = pizza.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "S/ ${String.format("%.2f", pizza.price)}",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PizzaPrimary
                    )
                    if (pizza.originalPrice != null && pizza.originalPrice > pizza.price) {
                        Text(
                            text = "S/ ${String.format("%.2f", pizza.originalPrice)}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.outline,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Description
            Text(
                text = pizza.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            // Ingredients Tags
            if (pizza.ingredients.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    pizza.ingredients.forEach { ingredient ->
                        Surface(
                            color = PizzaSurfaceContainerLow,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = ingredient,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Bottom Action Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (pizza.isCustomizable) {
                    Surface(
                        color = PizzaTertiary.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .clickable(onClick = onCustomize)
                            .testTag("customize_btn_${pizza.id}")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
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
                                text = "Personalizar Sabores",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = PizzaTertiary
                            )
                        }
                    }
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            tint = PizzaSecondary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Fermentación ${pizza.doughFermentationHours}h",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Button(
                    onClick = onAddToCart,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PizzaPrimary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = Modifier
                        .height(42.dp)
                        .testTag("add_to_cart_${pizza.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.AddShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
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
private fun FilterToggleChip(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isActive: Boolean,
    activeColor: Color,
    activeBg: Color,
    onClick: () -> Unit
) {
    Surface(
        color = if (isActive) activeBg else PizzaSurfaceContainerLow,
        shape = RoundedCornerShape(16.dp),
        border = if (isActive) androidx.compose.foundation.BorderStroke(1.dp, activeColor) else null,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isActive) activeColor else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                color = if (isActive) activeColor else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
