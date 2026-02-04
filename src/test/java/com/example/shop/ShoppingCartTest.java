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

    @Test
    void getTotalPrice_shouldReturnSumOfAllItems() {
        shop.ShoppingCart cart = new shop.ShoppingCart();
        cart.addItem("Apple", 10.0, 2);   // 20
        cart.addItem("Banana", 5.0, 1);   // 5
        assertThat(cart.getTotalPrice()).isEqualTo(25.0);
    }

}
