package com.example.shop;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class ShoppingCartTest {

    @Test
    void addItem_shouldIncreaseTotalQuantity() {
        shop.ShoppingCart cart = new shop.ShoppingCart();
        cart.addItem("Apple", 10.0, 1);
        assertThat(cart.getTotalQuantity()).isEqualTo(1);
    }
}
