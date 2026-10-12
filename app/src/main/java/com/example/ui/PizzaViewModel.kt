package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PizzaViewModel : ViewModel() {

    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _selectedCategory = MutableStateFlow("boxes")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _products = MutableStateFlow(PizzaRepository.initialProducts)
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _artisanalPizzas = MutableStateFlow(PizzaRepository.artisanalPizzas)
    val artisanalPizzas: StateFlow<List<ArtisanalPizza>> = _artisanalPizzas.asStateFlow()

    private val _selectedPizzaCategory = MutableStateFlow(PizzaCategory.ALL)
    val selectedPizzaCategory: StateFlow<PizzaCategory> = _selectedPizzaCategory.asStateFlow()

    private val _cartItems = MutableStateFlow(PizzaRepository.initialCartItems)
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    // Customizer state
    private val _flavors = MutableStateFlow(PizzaRepository.initialFlavors)
    val flavors: StateFlow<List<MiniPizzaFlavor>> = _flavors.asStateFlow()

    private val _selectedCrustId = MutableStateFlow("tradicional")
    val selectedCrustId: StateFlow<String> = _selectedCrustId.asStateFlow()

    private val _selectedSauceId = MutableStateFlow("rocoto")
    val selectedSauceId: StateFlow<String> = _selectedSauceId.asStateFlow()

    private val _boxQuantity = MutableStateFlow(1)
    val boxQuantity: StateFlow<Int> = _boxQuantity.asStateFlow()

    private val _customizerNotes = MutableStateFlow("")
    val customizerNotes: StateFlow<String> = _customizerNotes.asStateFlow()

    // Cart / Checkout state
    private val _couponCode = MutableStateFlow("CHICAPRIMERA")
    val couponCode: StateFlow<String> = _couponCode.asStateFlow()

    private val _isCouponApplied = MutableStateFlow(true)
    val isCouponApplied: StateFlow<Boolean> = _isCouponApplied.asStateFlow()

    private val _paymentMethod = MutableStateFlow(PaymentMethod.YAPE)
    val paymentMethod: StateFlow<PaymentMethod> = _paymentMethod.asStateFlow()

    private val _deliveryAddress = MutableStateFlow("Calle Las Poncianas 245, Dpto 402, Miraflores, Lima • Perú")
    val deliveryAddress: StateFlow<String> = _deliveryAddress.asStateFlow()

    private val _deliveryReference = MutableStateFlow("Frente al parque, tocar intercomunicador 402")
    val deliveryReference: StateFlow<String> = _deliveryReference.asStateFlow()

    private val _cashAmount = MutableStateFlow("100.00")
    val cashAmount: StateFlow<String> = _cashAmount.asStateFlow()

    // Tracking state
    private val _orderTracking = MutableStateFlow(
        OrderTracking(
            courierImage = PizzaRepository.COURIER_URL
        )
    )
    val orderTracking: StateFlow<OrderTracking> = _orderTracking.asStateFlow()

    private val _isSummaryExpanded = MutableStateFlow(false)
    val isSummaryExpanded: StateFlow<Boolean> = _isSummaryExpanded.asStateFlow()

    // Admin state
    private val _isStoreOpen = MutableStateFlow(true)
    val isStoreOpen: StateFlow<Boolean> = _isStoreOpen.asStateFlow()

    private val _ordersCountToday = MutableStateFlow(18)
    val ordersCountToday: StateFlow<Int> = _ordersCountToday.asStateFlow()

    private val _totalSalesToday = MutableStateFlow(684.50)
    val totalSalesToday: StateFlow<Double> = _totalSalesToday.asStateFlow()

    private val _editingProduct = MutableStateFlow(
        Product(
            id = "box_degustacion_6",
            name = "Box Degustación x6 Minipizzas Artesanales",
            description = "La consentida de la casa. Masa madre crocante horneada al sarmiento. Escoge hasta 3 sabores distintos entre Pepperoni Supremo, Cuatro Quesos, Lomo Saltado Fusión y Champiñones al Ajo.",
            price = 26.90,
            originalPrice = 32.00,
            image = "https://lh3.googleusercontent.com/aida-public/AB6AXuCCMzHZmRqqzreO7bmcRrDzdiCc4WyYJ8dVvA8O6IA2hR5wEjQ6ltlXanPm1JRXy0e0SvTuMYX4lK2HBdZqP0ICD5tLLyPYmD8M0FOYk3DkI7TTVwUheMc3eaGHddsiHTtaY4N0TTbj9zoD4-jauEChApr8STHgBwk8X7OOrAeUnnJjLbf5evh1x8_duNU3KdmIhAjwHjocpEJ-PVoNcdEnbO317a8pXJUYAYOQ-TjpoorfULslEHOC6Q",
            category = "boxes",
            isCustomizable = true,
            stockActive = true
        )
    )
    val editingProduct: StateFlow<Product> = _editingProduct.asStateFlow()

    // Auth state
    private val _authTab = MutableStateFlow("register") // "register" or "login"
    val authTab: StateFlow<String> = _authTab.asStateFlow()

    private val _userName = MutableStateFlow("Fiorella Mendoza")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _userPhone = MutableStateFlow("987 654 321")
    val userPhone: StateFlow<String> = _userPhone.asStateFlow()

    // Toast state
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun selectCategory(cat: String) {
        _selectedCategory.value = cat
    }

    fun selectPizzaCategory(category: PizzaCategory) {
        _selectedPizzaCategory.value = category
    }

    fun addArtisanalPizzaToCart(pizza: ArtisanalPizza) {
        val existing = _cartItems.value.find { it.id == pizza.id }
        if (existing != null) {
            _cartItems.value = _cartItems.value.map {
                if (it.id == pizza.id) it.copy(quantity = it.quantity + 1) else it
            }
        } else {
            _cartItems.value = _cartItems.value + CartItem(
                id = pizza.id,
                name = pizza.name,
                description = pizza.description.take(48) + "...",
                price = pizza.price,
                quantity = 1,
                image = pizza.image,
                portionInfo = "${pizza.size.slices} porciones • ${pizza.size.diameterCm}cm"
            )
        }
        showToast("¡${pizza.name} añadida al carrito!")
    }

    fun showToast(msg: String) {
        viewModelScope.launch {
            _toastMessage.value = msg
            delay(2400)
            if (_toastMessage.value == msg) {
                _toastMessage.value = null
            }
        }
    }

    fun dismissToast() {
        _toastMessage.value = null
    }

    // Customizer actions
    fun incrementFlavor(id: String) {
        val totalNow = _flavors.value.sumOf { it.quantity }
        if (totalNow < 6) {
            _flavors.value = _flavors.value.map {
                if (it.id == id) it.copy(quantity = it.quantity + 1) else it
            }
        }
    }

    fun decrementFlavor(id: String) {
        _flavors.value = _flavors.value.map {
            if (it.id == id && it.quantity > 0) it.copy(quantity = it.quantity - 1) else it
        }
    }

    fun selectCrust(id: String) {
        _selectedCrustId.value = id
    }

    fun selectSauce(id: String) {
        _selectedSauceId.value = id
    }

    fun incrementBoxQty() {
        _boxQuantity.value++
    }

    fun decrementBoxQty() {
        if (_boxQuantity.value > 1) {
            _boxQuantity.value--
        }
    }

    fun updateCustomizerNotes(notes: String) {
        _customizerNotes.value = notes
    }

    fun addCustomBoxToCart() {
        val totalSlices = _flavors.value.sumOf { it.quantity }
        if (totalSlices != 6) {
            showToast("Debes seleccionar exactamente 6 minipizzas")
            return
        }

        var basePrice = 26.90
        val flavorExtras = _flavors.value.sumOf { it.quantity * it.extraPrice }
        val crustExtra = if (_selectedCrustId.value == "relleno") 4.0 else 0.0
        val singleBoxPrice = basePrice + flavorExtras + crustExtra

        val pills = _flavors.value
            .filter { it.quantity > 0 }
            .map {
                val extraTag = if (it.extraPrice > 0) " (+S/${it.extraPrice.toInt()})" else ""
                "${it.quantity}x ${it.name.split(" ").first()}$extraTag"
            }

        val sauceName = PizzaRepository.sauces.find { it.id == _selectedSauceId.value }?.name ?: "Salsa de la Casa"

        val newItem = CartItem(
            id = "box_${System.currentTimeMillis()}",
            name = "Box Degustación x6 Minipizzas",
            description = if (_selectedCrustId.value == "relleno") "Borde Relleno de Queso Crema" else "Masa Tradicional 48h",
            price = singleBoxPrice,
            quantity = _boxQuantity.value,
            image = PizzaRepository.initialCartItems[0].image,
            flavorPills = pills,
            sauce = sauceName
        )

        _cartItems.value = listOf(newItem) + _cartItems.value
        showToast("¡Box x6 añadido al carrito!")
        _currentScreen.value = AppScreen.CART
    }

    // Direct add product
    fun addProductToCart(product: Product) {
        val existing = _cartItems.value.find { it.id == product.id }
        if (existing != null) {
            _cartItems.value = _cartItems.value.map {
                if (it.id == product.id) it.copy(quantity = it.quantity + 1) else it
            }
        } else {
            _cartItems.value = _cartItems.value + CartItem(
                id = product.id,
                name = product.name,
                description = product.description.take(45) + "...",
                price = product.price,
                quantity = 1,
                image = product.image.ifEmpty { PizzaRepository.initialCartItems[0].image },
                portionInfo = product.portionsInfo
            )
        }
        showToast("¡${product.name} añadido!")
    }

    fun addPromoCombo() {
        val comboItem = CartItem(
            id = "promo_combo_12",
            name = "Box x12 Minipizzas + 2 Gaseosas",
            description = "Promo de la Semana: Masa madre horneada",
            price = 45.90,
            quantity = 1,
            image = PizzaRepository.initialProducts[2].image,
            portionInfo = "Combo Familiar"
        )
        _cartItems.value = listOf(comboItem) + _cartItems.value
        showToast("¡Box x12 + Gaseosas añadido!")
    }

    // Cart actions
    fun incrementCartItem(id: String) {
        _cartItems.value = _cartItems.value.map {
            if (it.id == id) it.copy(quantity = it.quantity + 1) else it
        }
    }

    fun decrementCartItem(id: String) {
        _cartItems.value = _cartItems.value.mapNotNull {
            if (it.id == id) {
                if (it.quantity > 1) it.copy(quantity = it.quantity - 1) else null
            } else it
        }
    }

    fun removeCartItem(id: String) {
        _cartItems.value = _cartItems.value.filterNot { it.id == id }
        showToast("Producto eliminado del carrito")
    }

    fun setCouponCode(code: String) {
        _couponCode.value = code
    }

    fun applyCoupon() {
        if (_couponCode.value.trim().equals("CHICAPRIMERA", ignoreCase = true)) {
            _isCouponApplied.value = true
            showToast("Cupón 'CHICAPRIMERA' aplicado (-10%)")
        } else {
            _isCouponApplied.value = false
            showToast("Cupón no válido")
        }
    }

    fun removeCoupon() {
        _isCouponApplied.value = false
        showToast("Cupón removido")
    }

    fun selectPaymentMethod(method: PaymentMethod) {
        _paymentMethod.value = method
    }

    fun setCashAmount(amount: String) {
        _cashAmount.value = amount
    }

    fun setDeliveryReference(ref: String) {
        _deliveryReference.value = ref
    }

    fun setDeliveryAddress(addr: String) {
        _deliveryAddress.value = addr
    }

    // Confirm order
    fun confirmOrder() {
        val total = calculateTotal()
        _orderTracking.value = _orderTracking.value.copy(
            total = total,
            subtotal = calculateSubtotal(),
            discount = if (_isCouponApplied.value) calculateSubtotal() * 0.10 else 0.0
        )
        showToast("¡Orden confirmada en el horno!")
        _currentScreen.value = AppScreen.TRACKING
    }

    fun calculateSubtotal(): Double {
        return _cartItems.value.sumOf { it.price * it.quantity }
    }

    fun calculateTotal(): Double {
        val sub = calculateSubtotal()
        val disc = if (_isCouponApplied.value) sub * 0.10 else 0.0
        return (sub - disc + 5.0).coerceAtLeast(0.0)
    }

    fun toggleOrderSummary() {
        _isSummaryExpanded.value = !_isSummaryExpanded.value
    }

    fun refreshTracking() {
        viewModelScope.launch {
            showToast("Estado actualizado en vivo")
        }
    }

    // Admin actions
    fun toggleStore() {
        _isStoreOpen.value = !_isStoreOpen.value
    }

    fun updateEditingPrice(delta: Double) {
        val newPrice = (_editingProduct.value.price + delta).coerceAtLeast(0.0)
        _editingProduct.value = _editingProduct.value.copy(price = newPrice)
    }

    fun setEditingPrice(price: Double) {
        _editingProduct.value = _editingProduct.value.copy(price = price)
    }

    fun setEditingName(name: String) {
        _editingProduct.value = _editingProduct.value.copy(name = name)
    }

    fun setEditingDescription(desc: String) {
        _editingProduct.value = _editingProduct.value.copy(description = desc)
    }

    fun toggleEditingStock() {
        _editingProduct.value = _editingProduct.value.copy(stockActive = !_editingProduct.value.stockActive)
    }

    fun saveAdminProduct() {
        _products.value = _products.value.map {
            if (it.id == _editingProduct.value.id) _editingProduct.value else it
        }
        showToast("¡Guardado con Éxito!")
    }

    // Auth actions
    fun setAuthTab(tab: String) {
        _authTab.value = tab
    }

    fun setUserName(name: String) {
        _userName.value = name
    }

    fun setUserPhone(phone: String) {
        _userPhone.value = phone
    }
}
