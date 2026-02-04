package shop;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private static class Item {
        String name;
        double price;
        int quantity;

        Item(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }
    }

    private final List<Item> items = new ArrayList<>();

    public void addItem(String name, double price, int quantity) {
        items.add(new Item(name, price, quantity));
    }

    public int getTotalQuantity() {
        return items.stream().mapToInt(i -> i.quantity).sum();
    }

    public double getTotalPrice() {
        return items.stream().mapToDouble(i -> i.price * i.quantity).sum();
    }

    public void removeItem(String name) {
        items.removeIf(i -> i.name.equals(name));
    }
}
