package com.example

import com.example.data.*
import com.example.ui.PizzaViewModel
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testInitialState() {
        val viewModel = PizzaViewModel()
        assertEquals(AppScreen.HOME, viewModel.currentScreen.value)
        assertEquals(3, viewModel.cartItems.value.size)
        assertTrue(viewModel.isStoreOpen.value)
        assertEquals(PaymentMethod.YAPE, viewModel.paymentMethod.value)
    }

    @Test
    fun testArtisanalPizzaModelAndList() {
        val pizzas = PizzaRepository.artisanalPizzas
        assertTrue("Artisanal pizzas list should not be empty", pizzas.isNotEmpty())

        val firstPizza = pizzas.first()
        assertEquals("box_degustacion_6", firstPizza.id)
        assertEquals(PizzaCategory.BOXES, firstPizza.category)
        assertEquals(PizzaSize.MINI_BOX, firstPizza.size)
        assertEquals(48, firstPizza.doughFermentationHours)
        assertTrue(firstPizza.isChefPick)
        assertTrue(firstPizza.isCustomizable)
        assertTrue(firstPizza.ingredients.isNotEmpty())

        // Test filtering by category
        val fusionPizzas = pizzas.filter { it.category == PizzaCategory.FUSION }
        assertEquals(1, fusionPizzas.size)
        assertEquals("pizza_lomo_saltado", fusionPizzas.first().id)
        assertTrue(fusionPizzas.first().isSpicy)

        val veggiePizzas = pizzas.filter { it.isVegetarian }
        assertTrue(veggiePizzas.size >= 2)
    }

    @Test
    fun testAddArtisanalPizzaToCart() {
        val viewModel = PizzaViewModel()
        val initialCount = viewModel.cartItems.value.size
        val pizza = PizzaRepository.artisanalPizzas.first()

        viewModel.addArtisanalPizzaToCart(pizza)
        assertEquals(initialCount + 1, viewModel.cartItems.value.size)

        val added = viewModel.cartItems.value.find { it.id == pizza.id }
        assertNotNull(added)
        assertEquals(pizza.name, added?.name)
        assertEquals(pizza.price, added?.price ?: 0.0, 0.001)
    }

    @Test
    fun testAddProductAndCartCalculations() {
        val viewModel = PizzaViewModel()
        val initialCount = viewModel.cartItems.value.size
        val testProduct = Product(
            id = "test_pizza",
            name = "Pizza Test",
            description = "Desc",
            price = 30.0,
            image = "",
            category = "grandes"
        )
        viewModel.addProductToCart(testProduct)
        assertEquals(initialCount + 1, viewModel.cartItems.value.size)

        // Test coupon application
        viewModel.setCouponCode("CHICAPRIMERA")
        viewModel.applyCoupon()
        assertTrue(viewModel.isCouponApplied.value)

        val subtotal = viewModel.calculateSubtotal()
        assertTrue(subtotal > 0)
        val total = viewModel.calculateTotal()
        assertTrue(total < subtotal + 5.0) // Due to 10% discount
    }

    @Test
    fun testCustomizerFlow() {
        val viewModel = PizzaViewModel()
        viewModel.navigateTo(AppScreen.CUSTOMIZER)
        assertEquals(AppScreen.CUSTOMIZER, viewModel.currentScreen.value)

        // Default initial flavors sum to 6 (2 + 2 + 2)
        val initialSum = viewModel.flavors.value.sumOf { it.quantity }
        assertEquals(6, initialSum)

        viewModel.selectCrust("relleno")
        assertEquals("relleno", viewModel.selectedCrustId.value)

        viewModel.addCustomBoxToCart()
        assertEquals(AppScreen.CART, viewModel.currentScreen.value)
    }
}
