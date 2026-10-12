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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.AppScreen
import com.example.data.PizzaRepository
import com.example.ui.PizzaViewModel
import com.example.ui.theme.*

@Composable
fun AuthScreen(
    viewModel: PizzaViewModel,
    modifier: Modifier = Modifier
) {
    val authTab by viewModel.authTab.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val userPhone by viewModel.userPhone.collectAsState()

    var nameInput by remember(userName) { mutableStateOf(userName) }
    var phoneInput by remember(userPhone) { mutableStateOf(userPhone) }
    var addressInput by remember { mutableStateOf("Calle Las Poncianas 245, Miraflores") }
    var passwordInput by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var useWhatsAppCode by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 36.dp)
            .testTag("auth_screen")
    ) {
        // Hero Visual Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
                .background(PizzaSurfaceContainerHigh)
        ) {
            AsyncImage(
                model = PizzaRepository.AUTH_HERO_URL,
                contentDescription = "Maestra Pizzera",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.4f),
                                Color.Black.copy(alpha = 0.88f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .align(Alignment.BottomStart)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocalPizza,
                        contentDescription = null,
                        tint = PizzaSecondaryContainer,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "LA CHICA DE LAS PIZZAS",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PizzaSecondaryContainer,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "¡Únete al Club de Las Pizzas!",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                    color = Color.White
                )

                Text(
                    text = "Acumula puntos pizzeros y pide tu box favorito en 1 toque.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.85f)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Main Auth Body
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Segmented Switcher
            Surface(
                color = PizzaSurfaceContainer,
                shape = CircleShape,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(3.dp)) {
                    Button(
                        onClick = { viewModel.setAuthTab("register") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (authTab == "register") PizzaPrimary else Color.Transparent,
                            contentColor = if (authTab == "register") Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        shape = CircleShape,
                        elevation = null,
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp)
                            .testTag("tab_register_btn")
                    ) {
                        Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Registrarme", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }

                    Button(
                        onClick = { viewModel.setAuthTab("login") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (authTab == "login") PizzaPrimary else Color.Transparent,
                            contentColor = if (authTab == "login") Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        shape = CircleShape,
                        elevation = null,
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp)
                            .testTag("tab_login_btn")
                    ) {
                        Icon(Icons.Default.Login, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Ya tengo cuenta", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }

            if (authTab == "register") {
                // Perks Strip
                Surface(
                    color = PizzaSurfaceContainerLow,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .background(PizzaSecondaryContainer, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.WorkspacePremium, contentDescription = null, tint = PizzaOnSecondaryContainer, modifier = Modifier.size(16.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Gana 10 Puntos Pizzeros por cada S/ 10 para canjear minipizzas gratis.", fontSize = 12.sp)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .background(PizzaTertiaryContainer, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.AccountBalanceWallet, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Guarda tus direcciones y paga veloz con Yape, Plin o Tarjeta.", fontSize = 12.sp)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .background(PizzaPrimaryFixed, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Outlined.Moped, contentDescription = null, tint = PizzaOnPrimaryFixedVariant, modifier = Modifier.size(16.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Rastreo en tiempo real directo desde el horno hasta tu puerta.", fontSize = 12.sp)
                        }
                    }
                }

                // Registration Form
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    // Full name
                    Text("Nombre y Apellido", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = {
                            nameInput = it
                            viewModel.setUserName(it)
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Badge, contentDescription = null, tint = PizzaPrimary)
                        },
                        placeholder = { Text("Ej. Fiorella Mendoza") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("auth_name_input")
                    )

                    // Phone number
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Número de Celular", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Surface(color = PizzaTertiaryFixed, shape = CircleShape) {
                            Text("Perú (+51)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PizzaTertiary, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }

                    OutlinedTextField(
                        value = phoneInput,
                        onValueChange = {
                            phoneInput = it
                            viewModel.setUserPhone(it)
                        },
                        leadingIcon = {
                            Row(
                                modifier = Modifier.padding(start = 12.dp, end = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("🇵🇪", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("+51", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        },
                        placeholder = { Text("987 654 321") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("auth_phone_input")
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Chat, contentDescription = null, tint = PizzaTertiary, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Te enviaremos la confirmación de tus pizzas por WhatsApp.", fontSize = 11.sp, color = PizzaTertiary)
                    }

                    // Delivery Address
                    Text("Dirección de Entrega Principal", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = addressInput,
                        onValueChange = { addressInput = it },
                        leadingIcon = {
                            Icon(Icons.Default.PinDrop, contentDescription = null, tint = PizzaSecondary)
                        },
                        trailingIcon = {
                            Button(
                                onClick = {
                                    addressInput = "Av. Pardo y Aliaga 640, San Isidro"
                                    viewModel.showToast("Ubicación GPS fijada")
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = PizzaSecondaryContainer),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                modifier = Modifier.height(34.dp)
                            ) {
                                Icon(Icons.Default.MyLocation, contentDescription = null, modifier = Modifier.size(14.dp), tint = PizzaOnSecondaryContainer)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("GPS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PizzaOnSecondaryContainer)
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Password
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Contraseña", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        TextButton(
                            onClick = { useWhatsAppCode = !useWhatsAppCode },
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                if (useWhatsAppCode) "Usar Contraseña" else "Usar Código WhatsApp",
                                fontSize = 11.sp,
                                color = PizzaPrimary
                            )
                        }
                    }

                    if (useWhatsAppCode) {
                        Surface(
                            color = PizzaSurfaceContainerLow,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("Te enviaremos un código de seguridad de 4 dígitos por WhatsApp.", fontSize = 11.sp)
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text("• • • •", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = PizzaPrimary)
                                }
                            }
                        }
                    } else {
                        OutlinedTextField(
                            value = passwordInput,
                            onValueChange = { passwordInput = it },
                            placeholder = { Text("Mínimo 6 caracteres") },
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                        contentDescription = null
                                    )
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Register Button
                    Button(
                        onClick = {
                            viewModel.setDeliveryAddress(addressInput)
                            viewModel.showToast("¡Cuenta creada exitosamente!")
                            viewModel.navigateTo(AppScreen.HOME)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PizzaPrimary),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("btn_create_account")
                    ) {
                        Icon(Icons.Default.LocalPizza, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Crear Cuenta y Comenzar a Pedir", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            } else {
                // Login View
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Surface(
                        color = PizzaSurfaceContainerLow,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .background(PizzaPrimaryContainer, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.WavingHand, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("¡Hola de nuevo!", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                Text("Tus pizzas y direcciones guardadas te están esperando calientitas.", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }

                    Text("Celular (+51) o Correo", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = phoneInput,
                        onValueChange = { phoneInput = it },
                        placeholder = { Text("987 654 321 o tu@correo.com") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text("Contraseña", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = passwordInput,
                        onValueChange = { passwordInput = it },
                        placeholder = { Text("Ingresa tu contraseña") },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null
                                )
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            viewModel.showToast("¡Bienvenido al Club de Las Pizzas!")
                            viewModel.navigateTo(AppScreen.HOME)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PizzaPrimary),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("btn_login_account")
                    ) {
                        Icon(Icons.Default.OutdoorGrill, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Entrar a Mi Cuenta", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }

            // Divider
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f))
                Text(
                    text = "O INGRESA CON",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                HorizontalDivider(modifier = Modifier.weight(1f))
            }

            // Social Buttons
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // WhatsApp Social Button
                Button(
                    onClick = {
                        viewModel.showToast("Autenticado con WhatsApp")
                        viewModel.navigateTo(AppScreen.HOME)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WhatsAppGreen.copy(alpha = 0.15f),
                        contentColor = WhatsAppDark
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("btn_social_whatsapp")
                ) {
                    Icon(Icons.Default.Chat, contentDescription = null, tint = WhatsAppGreen, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Continuar con WhatsApp", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                // Google Social Button
                OutlinedButton(
                    onClick = {
                        viewModel.showToast("Autenticado con Google")
                        viewModel.navigateTo(AppScreen.HOME)
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("btn_social_google")
                ) {
                    Icon(Icons.Default.AccountCircle, contentDescription = null, tint = PizzaPrimary, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Continuar con Google", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }

            // Trust badges row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(color = YapePurple, shape = CircleShape) {
                    Text("Yape Directo", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Surface(color = PlinCyan, shape = CircleShape) {
                    Text("Plin", color = Color(0xFF003434), fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Surface(color = PizzaSurfaceContainerHigh, shape = CircleShape) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = PizzaTertiary, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text("Pago 100% Seguro", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            // Guest checkout
            TextButton(
                onClick = { viewModel.navigateTo(AppScreen.HOME) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("btn_guest_checkout")
            ) {
                Text("Continuar como Invitado ", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("(Hacer pedido rápido sin cuenta)", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp), tint = PizzaPrimary)
            }
        }
    }
}
