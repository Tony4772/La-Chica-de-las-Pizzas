package com.example.data

object PizzaRepository {
    const val LOGO_URL =
        "https://lh3.googleusercontent.com/aida-public/AB6AXuAtQkXg2bvCrA4ViYVG6ExBkswRqdAeKEMpw7u3JmY1aloSptEgvVo0ObmJ4MGO7fEH47NQOmF8tFmlP4l6wKURmyN_ho8EzW6glP0qRLhzJVnnaeujmHBwSyety5c4XjJGDwAO0I-D0QEeKvHr0GxPi12KYeZ4iYzb0Q14O89yZgFl2G6IEfkmcF5J_k2Cn_bdmej0R_D-QlmPlWDZKYqPUX5syF6ox4a91O-hEDaPJ1J6m_BjMmyLaQ"

    const val PROFILE_AVATAR_URL =
        "https://lh3.googleusercontent.com/aida-public/AB6AXuDFAqs0EfGERGZ-qa7Erm1BUuaNut2XtDPD_373dkaMBwwYY-Fjo1MaN_Dtk_8TBBjr5pB3_QS-XyHyKXpZbi-JUDOakQnUugwYs4WIkNDRMRixg0JSdlM9z28RLSDUgu0xUAVUP_t2wqEghYItpq4IDCql5IygG7o76K6uiUhpqFSl9IR-q08-6Wy81mv6hyp7wyVyF9DAkndCWWVAdZUdzB_awiK-FLe_TDG8zTtItv6tXvMQ2xQixA"

    const val CUSTOMIZER_HERO_URL =
        "https://lh3.googleusercontent.com/aida-public/AB6AXuADiq0jlRXlTYXw4s-50F_VoEC1iZIe6r5jNPOsthz2jgcLTA_bLqGGWUDUkukVANuc6rjaOqbr4sksf2yHx7cWiv_0-ChmvN0sEXPYjbzDa9jdTp3qrh0Y2EkalXKGvvHk4Mt18IYPZtAGec2-GywvO8eOq4lDUjMsDa6qDqOcOgOg0_ic1VQvaqFwfLU6on65Fy_W5PvyuUM12WMrSSV1BFzedX8yp2s02mwTOis42YPKzhqa6yj79g"

    const val COURIER_URL =
        "https://lh3.googleusercontent.com/aida-public/AB6AXuA0v4PIVZyiJ706E9-BnObLas_QEnJO1-zzmFgUUPaKiI8ezVdnwfuLFWa786492cUPRlguhKJmboKQq71CIooI635BRPGTreMRd4udRRPbCtIHX3sJBtvmcx73TCKUV2_2YlcUQiBGYz1-CwvGGFJqLHJdBuAoWTsk_J99YfjsnOdcCmBXHKrPPOZUztpwWE4FheXb8CI47oY4AutYVPQBePDrbEQyN-fNf8fldrSoRHIFs8kzfVs9Cg"

    const val AUTH_HERO_URL =
        "https://lh3.googleusercontent.com/aida-public/AB6AXuDGnP-A0_3V8rlNVyRhVP-0mXljYKKApwcytRqdMlTHcVpxfuVpj7ULPcOcTfkmghLJbct3u_z_zFUrOZRK4_u2gMuwqBODBsxChKEJk91sQUBRwFddez1kZDhn3OZ54xnUW-4Pcv012Fg8IWOp5dAA2csMzGG0_ih9cgXKmZ-J6ipg3zw9WSKodtazKJ-4F67l5kASuPCNe9tuDRMO7omAI91P-HpKRGLjBBvzwwTxm6ME-e4HL3BJsA"

    val initialProducts = listOf(
        Product(
            id = "box_degustacion_6",
            name = "Box Degustación x6 Minipizzas",
            description = "La consentida de la casa. Escoge hasta 3 sabores distintos entre Pepperoni Supremo, Cuatro Quesos, Hawaiana Artesanal y Fugazzeta.",
            price = 26.90,
            originalPrice = 32.00,
            rating = 4.9,
            reviewsCount = 128,
            badge = "Masa Madre 48h",
            image = "https://lh3.googleusercontent.com/aida-public/AB6AXuA0HdUSpTHR4EJZCcaCvImj1S7Ipq7Y6KSlT3bcbUPIesjwqQz9iblexLw7yupgm2kUh5eiIUUO6xJ3EQp6OfGz4Up0IsheEGeQqkdgGw9N4xeDcAVov4mQ3PrM4ffk4pz3xnY1uBcNc__J1qgFrLM5_rdjKQoyJq8YCU3sp3_GNPvjAK3nBZuyVpg-6oRJ23nA3vNX9BKO98ZjfND96pQLn7XEDS6ZuOn9VB_7PXXEiw8GrNwBP4QoQg",
            category = "boxes",
            isCustomizable = true
        ),
        Product(
            id = "pizza_grande_especial",
            name = "Pizza Grande La Chica Especial",
            description = "Salsa pomodoro casera, abundante mozzarella fior di latte, jamón artesanal premium, champiñones salteados al ajo y albahaca fresca.",
            price = 39.90,
            rating = 5.0,
            reviewsCount = 210,
            badge = "Fórmula Secreta",
            image = "https://lh3.googleusercontent.com/aida-public/AB6AXuAXAHXZgwv33sJ0hBWMlWRnFxxijFrZ1MMIfjeU73cIHLXrWier3_x0hYrxvYsOJ9xSUZ1hZsYcNMrw_Tsc45djrBMCrmHc7qMEzSNHtShzd5L0QfDwugtZ0Z30Za30XNZcVDgzgwCr4zqMiWcGUkC3HO5sTEPKkpGjxq3yIPu9UC2oobw_Uc-jtR8AFM2Ne4WLa_cI3YlFQHar10n4TpykTnYTtBhgarmWX5JFOLqrSt5BvhlFzYJRnQ",
            category = "grandes",
            portionsInfo = "8 porciones (35cm)"
        ),
        Product(
            id = "box_familiar_12",
            name = "Box Familiar x12 Minipizzas",
            description = "Para compartir a lo grande: 4 sabores a elección entre toda la carta + 2 salsas artesanales a elección de cortesía.",
            price = 48.00,
            rating = 4.9,
            reviewsCount = 340,
            badge = "+2 Salsas Gratis",
            image = "https://lh3.googleusercontent.com/aida-public/AB6AXuAA7cGaPRvMJ83eawvVH33mJHADDzx4uX7eRosP1T6MROzvsdkxfyv476g8RfnlR3EOVi7MDDH8M9cBt-eKVznqvu5FAPg-JyCdcVYYWHIf3Ai7Q1ri3zczGxLFOt6DPpZOQIBBk-V8PNPS_NlJ7PQWbbZxAyZZ7y95pAGp1RJmuWUL9nUEbuyd5fHNIpJNUBHZPQqZX5gSJCnrskHYWKG7y5u4fdOHAu9p-nOlkH6d4JqIqATCaWyG3Q",
            category = "boxes",
            portionsInfo = "Ideal 4-5 personas"
        ),
        Product(
            id = "pizza_lomo_saltado",
            name = "Pizza Lomo Saltado Fusión",
            description = "Tiras de lomo fino salteado al wok en fuego vivo, cebolla crocante, gajos de tomate fresco y crema artesanal de rocoto suave.",
            price = 42.00,
            rating = 4.8,
            reviewsCount = 89,
            badge = "Fusión Criolla",
            image = "https://lh3.googleusercontent.com/aida-public/AB6AXuDZ_xWfdA-f21uZ7VUQ31jqh3Qsmlumpmrc2_4zTBXt3eBiHi90kdepjWS8UUXQk_Gk4StE1dPUIsgZgxEgK0sI0DJFTkqnTYTNDhSYI49GkniC2sJAnvMBtF4pPFmu9A19FIw4dncIpgN9PpqRnlh7LHC2ybjhW7Cyco1ncH4-nQOjzZQTJe5h72XOmVvwFI6HAlZkOhZQ2yMZV0NnwPA9jaTBxCO1lul9HUwUEmGGS7HIiWrWJJ2tSQ",
            category = "grandes",
            portionsInfo = "Edición Limitada"
        )
    )

    val artisanalPizzas = listOf(
        ArtisanalPizza(
            id = "box_degustacion_6",
            name = "Box Degustación x6 Minipizzas",
            description = "La consentida de la casa. Escoge hasta 3 sabores distintos entre Pepperoni Supremo, Cuatro Quesos, Hawaiana Artesanal y Fugazzeta horneadas a la leña.",
            price = 26.90,
            originalPrice = 32.00,
            rating = 4.9,
            reviewsCount = 128,
            badge = "Masa Madre 48h",
            image = "https://lh3.googleusercontent.com/aida-public/AB6AXuA0HdUSpTHR4EJZCcaCvImj1S7Ipq7Y6KSlT3bcbUPIesjwqQz9iblexLw7yupgm2kUh5eiIUUO6xJ3EQp6OfGz4Up0IsheEGeQqkdgGw9N4xeDcAVov4mQ3PrM4ffk4pz3xnY1uBcNc__J1qgFrLM5_rdjKQoyJq8YCU3sp3_GNPvjAK3nBZuyVpg-6oRJ23nA3vNX9BKO98ZjfND96pQLn7XEDS6ZuOn9VB_7PXXEiw8GrNwBP4QoQg",
            category = PizzaCategory.BOXES,
            size = PizzaSize.MINI_BOX,
            doughFermentationHours = 48,
            ingredients = listOf("Mozzarella Fior di Latte", "Pepperoni", "Cuatro Quesos Andinos", "Pomodoro rústico"),
            isChefPick = true,
            isCustomizable = true
        ),
        ArtisanalPizza(
            id = "pizza_grande_especial",
            name = "Pizza Grande La Chica Especial",
            description = "Salsa pomodoro casera, abundante mozzarella fior di latte, jamón artesanal premium, champiñones salteados al ajo y albahaca fresca de huerto.",
            price = 39.90,
            originalPrice = 45.00,
            rating = 5.0,
            reviewsCount = 210,
            badge = "Fórmula Secreta",
            image = "https://lh3.googleusercontent.com/aida-public/AB6AXuAXAHXZgwv33sJ0hBWMlWRnFxxijFrZ1MMIfjeU73cIHLXrWier3_x0hYrxvYsOJ9xSUZ1hZsYcNMrw_Tsc45djrBMCrmHc7qMEzSNHtShzd5L0QfDwugtZ0Z30Za30XNZcVDgzgwCr4zqMiWcGUkC3HO5sTEPKkpGjxq3yIPu9UC2oobw_Uc-jtR8AFM2Ne4WLa_cI3YlFQHar10n4TpykTnYTtBhgarmWX5JFOLqrSt5BvhlFzYJRnQ",
            category = PizzaCategory.GRANDES,
            size = PizzaSize.GRANDE,
            doughFermentationHours = 48,
            ingredients = listOf("Pomodoro casero", "Mozzarella Fior di Latte", "Jamón artesanal", "Champiñones al ajo", "Albahaca"),
            isChefPick = true
        ),
        ArtisanalPizza(
            id = "box_familiar_12",
            name = "Box Familiar x12 Minipizzas",
            description = "Para compartir a lo grande: 4 sabores a elección entre toda la carta + 2 salsas artesanales a elección de cortesía.",
            price = 48.00,
            originalPrice = 56.00,
            rating = 4.9,
            reviewsCount = 340,
            badge = "+2 Salsas Gratis",
            image = "https://lh3.googleusercontent.com/aida-public/AB6AXuAA7cGaPRvMJ83eawvVH33mJHADDzx4uX7eRosP1T6MROzvsdkxfyv476g8RfnlR3EOVi7MDDH8M9cBt-eKVznqvu5FAPg-JyCdcVYYWHIf3Ai7Q1ri3zczGxLFOt6DPpZOQIBBk-V8PNPS_NlJ7PQWbbZxAyZZ7y95pAGp1RJmuWUL9nUEbuyd5fHNIpJNUBHZPQqZX5gSJCnrskHYWKG7y5u4fdOHAu9p-nOlkH6d4JqIqATCaWyG3Q",
            category = PizzaCategory.BOXES,
            size = PizzaSize.FAMILIAR_BOX,
            doughFermentationHours = 48,
            ingredients = listOf("12 minipizzas artesanales", "4 sabores a elección", "2 salsas artesanales gratis"),
            isCustomizable = true
        ),
        ArtisanalPizza(
            id = "pizza_lomo_saltado",
            name = "Pizza Lomo Saltado Fusión",
            description = "Tiras de lomo fino salteado al wok en fuego vivo, cebolla crocante, gajos de tomate fresco y crema artesanal de rocoto ahumado.",
            price = 42.00,
            originalPrice = 48.00,
            rating = 4.8,
            reviewsCount = 89,
            badge = "Fusión Criolla",
            image = "https://lh3.googleusercontent.com/aida-public/AB6AXuDZ_xWfdA-f21uZ7VUQ31jqh3Qsmlumpmrc2_4zTBXt3eBiHi90kdepjWS8UUXQk_Gk4StE1dPUIsgZgxEgK0sI0DJFTkqnTYTNDhSYI49GkniC2sJAnvMBtF4pPFmu9A19FIw4dncIpgN9PpqRnlh7LHC2ybjhW7Cyco1ncH4-nQOjzZQTJe5h72XOmVvwFI6HAlZkOhZQ2yMZV0NnwPA9jaTBxCO1lul9HUwUEmGGS7HIiWrWJJ2tSQ",
            category = PizzaCategory.FUSION,
            size = PizzaSize.GRANDE,
            doughFermentationHours = 48,
            ingredients = listOf("Lomo fino al wok", "Cebolla morada", "Tomates confitados", "Rocoto parrillero", "Cilantro"),
            isSpicy = true,
            isChefPick = true
        ),
        ArtisanalPizza(
            id = "cuatro_quesos_andinos",
            name = "Cuatro Quesos Andinos & Miel",
            description = "Selección de quesos peruanos: Paria cajamarquino, Gouda andino, queso azul y mozzarella hilada, con sutiles hilos de miel aromatizada.",
            price = 38.50,
            rating = 4.9,
            reviewsCount = 115,
            badge = "Quesos Artesanales",
            image = "https://lh3.googleusercontent.com/aida-public/AB6AXuBPpwptkeupvPx7ZGvzKGab51xDAm6ISQJOXabxgHsyQ1rdo8d6UUg4MmPrM7BI3ZH6rVnbySGLQWjfNXAb3nLIsoYLG5AIomeM7TKWdsihM_lUg8ny5rSDRzol6f8Q40uWL1Sf3-0bONx3qVaZCKXnOonbZT62TnvGmFm_RrNejjCJV1x_p9Vb64RMWPAdKsvKgz_JcyVN_0jqTHfCTjUlTxz5aihJUDk0jGYo7NW0rERaC6TctjKt2Q",
            category = PizzaCategory.VEGETARIAN,
            size = PizzaSize.GRANDE,
            doughFermentationHours = 48,
            ingredients = listOf("Queso Paria", "Gouda Andino", "Queso Azul", "Mozzarella", "Miel aromatizada"),
            isVegetarian = true
        ),
        ArtisanalPizza(
            id = "margarita_confitada",
            name = "Margarita con Tomates Confitados",
            description = "Tomates cherry caramelizados al sarmiento, hojas tiernas de albahaca fresca, mozzarella fior di latte y aceite de oliva extra virgen prensado en frío.",
            price = 34.00,
            rating = 4.8,
            reviewsCount = 142,
            badge = "100% Tradicional",
            image = "https://lh3.googleusercontent.com/aida-public/AB6AXuCAd2DO_r23U1oYSuqGz35i6Q31YUzseLxpgj8TnAaN8OsBBhhjz966leszf9786kOYSBA8XicbUt6pz1NRn-T2BrqTHPkp3WfFxRgFEU_YOrEvTYaeXl1vMWnp7R0anu3DjPMK9Ut9htfRsr3c0hMAORYzWMujfC4GwFDuQxRjrGlI4mlVR-vD3nbPG6G-QnNPecBom4gHcrKhXU2GB_q4JGgs4vaI9fPbMrjIMSIXHEhGfhtJpRxrhg",
            category = PizzaCategory.VEGETARIAN,
            size = PizzaSize.GRANDE,
            doughFermentationHours = 48,
            ingredients = listOf("Pomodoro casero", "Tomates cherry confitados", "Fior di Latte", "Albahaca de huerto", "AOVE"),
            isVegetarian = true
        )
    )

    val saucesList = listOf(
        Product(
            id = "salsa_rocoto",
            name = "Rocoto Parrillero",
            description = "Picante equilibrado ahumado",
            price = 3.50,
            image = "",
            category = "salsas"
        ),
        Product(
            id = "salsa_ajo",
            name = "Ajo Confitado Cremoso",
            description = "Suave y caramelizado al horno",
            price = 3.50,
            image = "",
            category = "salsas"
        ),
        Product(
            id = "salsa_chimichurri",
            name = "Chimichurri Artesanal",
            description = "Hierbas frescas & aceite de oliva",
            price = 3.50,
            image = "",
            category = "salsas"
        )
    )

    val initialFlavors = listOf(
        MiniPizzaFlavor(
            id = "pepperoni",
            name = "Pepperoni Clásico Crujiente",
            description = "Salsa pomodoro y mozzarella",
            image = "https://lh3.googleusercontent.com/aida-public/AB6AXuDrKfRKk86EpQg7dBFzlgR35cXTgE6Y__htOfSVZIdKJqKhrEXhnpfCs2W9ZQs_uqJRAO52upRSCBC1vmkzQ1axD0FF02Rcj58hhEnDvqPM1I28yzgwLinnFOiu20NQHXCQDlN-PVMWaV56NSuDxoHU5SCMslilL_LNsPW-m1kW_qQV2aACKIbjwSwmwel_Z8enNupE6K5P-ZaAGcIdWRwvBUI2sefYkF_zHBnThNpda_fNyEnafzU-CA",
            quantity = 2
        ),
        MiniPizzaFlavor(
            id = "lomo",
            name = "Lomo Saltado Ahumado",
            description = "Chef pick",
            extraPrice = 2.00,
            isChefPick = true,
            image = "https://lh3.googleusercontent.com/aida-public/AB6AXuCkhRusjStO47qRusrSBUfeoYTt2ysPUksIGko1jEObgzL6DKnqv_FkAEv93_Yklavw_iKZAGuWQJeDMDNWoGGlX9_6QDxb3vDvQh3YmTqKeDg8Uo_azJjnFxDnKioncp12xfbCJ7_qwYCHNlBcDVtDhw-H1affgHy2Iqvf-gvfYhQ68x_90RASaE-mvZJDHpsz5MG5fVn2hNtcQk-wkmxd3Ga067L1EBJprgPBY8tpgD36mcg4mKCWlA",
            quantity = 2
        ),
        MiniPizzaFlavor(
            id = "cuatroquesos",
            name = "Cuatro Quesos Andinos & Mozzarella",
            description = "Gouda, Paria, Azul y Mozzarella",
            image = "https://lh3.googleusercontent.com/aida-public/AB6AXuBPpwptkeupvPx7ZGvzKGab51xDAm6ISQJOXabxgHsyQ1rdo8d6UUg4MmPrM7BI3ZH6rVnbySGLQWjfNXAb3nLIsoYLG5AIomeM7TKWdsihM_lUg8ny5rSDRzol6f8Q40uWL1Sf3-0bONx3qVaZCKXnOonbZT62TnvGmFm_RrNejjCJV1x_p9Vb64RMWPAdKsvKgz_JcyVN_0jqTHfCTjUlTxz5aihJUDk0jGYo7NW0rERaC6TctjKt2Q",
            quantity = 2
        ),
        MiniPizzaFlavor(
            id = "hawaiana",
            name = "Hawaiana Gourmet con Piña Asada",
            description = "Jamón inglés ahumado y piña dulce",
            image = "https://lh3.googleusercontent.com/aida-public/AB6AXuBL5f7h_o8rQQdFwiProLcC4vIQr8Wz9uSz1s-zYDNIxYvDdOucF8tF9kjSHad705O6rV9yURE4sEIp29Xv_X75j0v1dXreIQv0-CTRtx8QjPqYyy77QCdNXX-EYeKRp0WrfgeP6S8_Q3AzN2_mezK5bkclSVjD0dp1Hf3rH3YI_46s4qYoUqR6UrqCYYbgtY7xmFR41KNzAK7wCcoaU8JizgRB7u1R1i4--eqTnfXZDbk32rqF7D3oNw",
            quantity = 0
        ),
        MiniPizzaFlavor(
            id = "margarita",
            name = "Margarita con Tomates Confitados",
            description = "Albahaca fresca y aceite de oliva",
            image = "https://lh3.googleusercontent.com/aida-public/AB6AXuCAd2DO_r23U1oYSuqGz35i6Q31YUzseLxpgj8TnAaN8OsBBhhjz966leszf9786kOYSBA8XicbUt6pz1NRn-T2BrqTHPkp3WfFxRgFEU_YOrEvTYaeXl1vMWnp7R0anu3DjPMK9Ut9htfRsr3c0hMAORYzWMujfC4GwFDuQxRjrGlI4mlVR-vD3nbPG6G-QnNPecBom4gHcrKhXU2GB_q4JGgs4vaI9fPbMrjIMSIXHEhGfhtJpRxrhg",
            quantity = 0
        )
    )

    val crustStyles = listOf(
        CrustStyle("tradicional", "Masa Tradicional Delgada & Crocante", "48 horas de fermentación lenta en frío", 0.0),
        CrustStyle("relleno", "Borde Relleno de Queso Crema & Orégano", "Cremoso y aromático en cada bocado", 4.0)
    )

    val sauces = listOf(
        SauceOption("rocoto", "Crema de Rocoto Parrillero (Favorita)", isFavorite = true),
        SauceOption("ajo", "Ajo Confitado Cremoso"),
        SauceOption("oliva", "Aceite de Oliva con Finas Hierbas")
    )

    val initialCartItems = listOf(
        CartItem(
            id = "cart_box_degustacion",
            name = "Box Degustación x6 Minipizzas",
            description = "",
            price = 28.90,
            quantity = 1,
            image = "https://lh3.googleusercontent.com/aida-public/AB6AXuBEqNm0Sm-Wkc9TrJJ1H1mQjWGp-Fpkqq2nTpA4fRASy6CsHq3FDwJdnEONsOa7xr3CbyEWX6-F-_MtpOsy8AVMyP4OW7vI33C4Z5ppaIoj9IoypBY4twX1YYhImNry7_l-uRllpXN3eTw0J_Ro7fP3aWp6kbOpEFGQZ0iNr5bvbp95cac2C2F41ly52OiJRNiTIRChVL8Lb1DJ4EM1xZbW-YhyfHKYVJxJSBesI_IeA6IExLhpJk9SVA",
            flavorPills = listOf("2x Pepperoni", "2x Lomo Saltado (+S/2)", "2x Cuatro Quesos"),
            sauce = "Rocoto Parrillero Casero"
        ),
        CartItem(
            id = "cart_pizza_grande",
            name = "Pizza Grande La Chica Especial",
            description = "Masa Tradicional artesanal • Orégano silvestre extra",
            price = 39.90,
            quantity = 1,
            image = "https://lh3.googleusercontent.com/aida-public/AB6AXuA9KawiJ41bpHgSz8ev-j9ZNkLIVWEpr90Pz9dyUSn-3U4SVicaF2DM-kbMMduk1XHlBaZbkJacVfEV4VNizeMfAZh2iSRZVcz_47V2fofZs8Oq5i89NhNYJhPNg6wUCePyZV5IgalaMcMsmqJjVKyk08RorScJO1e6gNAmjbN7YT4Q1kBHriYChHvz49hdPCHcpthmBSqJRPSF5Qbwt60mvODKqwNVabOhCQmRg-GLza2QRquOxpyMeQ",
            portionInfo = "8 porciones • 35cm"
        ),
        CartItem(
            id = "cart_inca_kola",
            name = "Inca Kola 1.5L Helada",
            description = "Gaseosa heladita para acompañar",
            price = 8.50,
            quantity = 1,
            image = "https://lh3.googleusercontent.com/aida-public/AB6AXuADq0HglE0wcc7PV4TXJ9lPVZ8j2R1ElujSmabEA6N1LXNMjpzJbeDEKwCjFLz-iaZZuz6EPqvGrUPYmhbIhyh2ASTjxk5uiLJb3-vrxs3eP8YwyHI47P9UgVIA_Oga6f-b5mnYXMZtglqzvh4uZJzq52TezNO5RVDkDqRMXbgNFfpG20oSrbUIxDtdeKZ4H2kk4cLQrvON5Ix-V2oFVFbMFfvHb5oNcMWaI2GwKeON22JZgDfP57ZXIA"
        )
    )
}
