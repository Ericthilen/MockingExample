package shop;

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
        cart.addItem("Apple", 10.0, 2);
        cart.addItem("Banana", 5.0, 1);
        assertThat(cart.getTotalPrice()).isEqualTo(25.0);
    }

    @Test
    void removeItem_shouldRemoveItemCompletely() {
        shop.ShoppingCart cart = new shop.ShoppingCart();
        cart.addItem("Apple", 10.0, 2);
        cart.removeItem("Apple");
        assertThat(cart.getTotalQuantity()).isEqualTo(0);
        assertThat(cart.getTotalPrice()).isEqualTo(0.0);
    }

    @Test
    void updateQuantity_shouldModifyExistingItemQuantity() {
        shop.ShoppingCart cart = new shop.ShoppingCart();
        cart.addItem("Apple", 10.0, 2);
        cart.updateQuantity("Apple", 5);
        assertThat(cart.getTotalQuantity()).isEqualTo(5);
        assertThat(cart.getTotalPrice()).isEqualTo(50.0);
    }

    @Test
    void applyDiscount_shouldReduceTotalPrice() {
        shop.ShoppingCart cart = new shop.ShoppingCart();
        cart.addItem("Apple", 10.0, 2);
        cart.applyDiscount(0.10);
        assertThat(cart.getTotalPrice()).isEqualTo(18.0);
    }

    @Test
    void applyDiscount_invalidPercent_shouldThrowException() {
        shop.ShoppingCart cart = new shop.ShoppingCart();
        assertThatThrownBy(() -> cart.applyDiscount(1.1))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> cart.applyDiscount(-0.1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void updateQuantity_toZeroOrLess_shouldRemoveItem() {
        shop.ShoppingCart cart = new shop.ShoppingCart();
        cart.addItem("Apple", 10.0, 2);
        cart.updateQuantity("Apple", 0);
        assertThat(cart.getTotalQuantity()).isEqualTo(0);

        cart.addItem("Banana", 5.0, 1);
        cart.updateQuantity("Banana", -1);
        assertThat(cart.getTotalQuantity()).isEqualTo(0);
    }
}
