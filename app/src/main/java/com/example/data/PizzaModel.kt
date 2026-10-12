package com.example.data

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val originalPrice: Double? = null,
    val rating: Double = 4.9,
    val reviewsCount: Int = 100,
    val badge: String? = null,
    val image: String,
    val category: String, // "boxes", "grandes", "combos", "bebidas", "salsas"
    val isCustomizable: Boolean = false,
    val portionsInfo: String? = null,
    val stockActive: Boolean = true
)

enum class PizzaSize(val label: String, val slices: Int, val diameterCm: Int) {
    MINI_BOX("Minipizzas (Box x6)", 6, 12),
    GRANDE("Grande (35cm)", 8, 35),
    FAMILIAR_BOX("Familiar (Box x12)", 12, 12)
}

enum class PizzaCategory(val id: String, val title: String) {
    ALL("all", "Todas"),
    BOXES("boxes", "Boxes Minipizzas"),
    GRANDES("grandes", "Pizzas Grandes"),
    FUSION("fusion", "Fusión Criolla"),
    VEGETARIAN("vegetarian", "Vegetarianas")
}

data class ArtisanalPizza(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val originalPrice: Double? = null,
    val rating: Double = 4.9,
    val reviewsCount: Int = 120,
    val badge: String? = null,
    val image: String,
    val category: PizzaCategory = PizzaCategory.GRANDES,
    val size: PizzaSize = PizzaSize.GRANDE,
    val doughFermentationHours: Int = 48,
    val ingredients: List<String> = emptyList(),
    val isSpicy: Boolean = false,
    val isVegetarian: Boolean = false,
    val isChefPick: Boolean = false,
    val isCustomizable: Boolean = false,
    val isAvailable: Boolean = true
)

data class MiniPizzaFlavor(
    val id: String,
    val name: String,
    val description: String,
    val extraPrice: Double = 0.0,
    val isChefPick: Boolean = false,
    val image: String,
    val quantity: Int = 0
)

data class CrustStyle(
    val id: String,
    val name: String,
    val description: String,
    val extraPrice: Double = 0.0
)

data class SauceOption(
    val id: String,
    val name: String,
    val isFavorite: Boolean = false
)

data class CartItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int = 1,
    val image: String,
    val flavorPills: List<String> = emptyList(),
    val sauce: String? = null,
    val portionInfo: String? = null
)

enum class PaymentMethod(val title: String, val subtitle: String) {
    YAPE("Yape", "Paga al toque con QR o Celular"),
    PLIN("Plin", "Interbank, BBVA, Scotiabank"),
    TARJETA("Tarjeta Débito o Crédito", "POS Inalámbrico en tu puerta (Visa, Mastercard, Amex)"),
    EFECTIVO("Efectivo contra entrega", "Pagas al recibir tu pizza caliente")
}

data class OrderTracking(
    val orderNumber: String = "#LP-8421",
    val timePlaced: String = "Hoy, 8:15 PM",
    val statusText: String = "En Preparación",
    val etaRange: String = "18 - 25 min",
    val etaTime: String = "8:45 PM",
    val currentStep: Int = 3,
    val totalSteps: Int = 5,
    val courierName: String = "Carlos M.",
    val courierVehicle: String = "Moto Honda roja • Placa 4821-B",
    val courierImage: String,
    val bakerNote: String = "\"Tu masa reposó 48 horas en frío. En 4 minutos sale del horno doradita y crujiente.\"",
    val subtotal: Double = 77.30,
    val discount: Double = 7.73,
    val deliveryFee: Double = 5.00,
    val total: Double = 74.57
)

enum class AppScreen {
    HOME,
    CUSTOMIZER,
    CART,
    TRACKING,
    ADMIN,
    AUTH
}
