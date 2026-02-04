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
    private double discount = 0.0;

    public void addItem(String name, double price, int quantity) {
        items.add(new Item(name, price, quantity));
    }

    public int getTotalQuantity() {
        return items.stream().mapToInt(i -> i.quantity).sum();
    }

    public double getTotalPrice() {
        double total = items.stream().mapToDouble(i -> i.price * i.quantity).sum();
        return total - (total * discount);
    }

    public void removeItem(String name) {
        items.removeIf(i -> i.name.equals(name));
    }

    public void updateQuantity(String name, int newQuantity) {
        for (Item item : items) {
            if (item.name.equals(name)) {
                if (newQuantity <= 0) {
                    items.remove(item);
                } else {
                    item.quantity = newQuantity;
                }
                return;
            }
        }
    }

    public void applyDiscount(double percent) {
        if (percent < 0 || percent > 1) {
            throw new IllegalArgumentException("Discount must be between 0 and 1");
        }
        this.discount = percent;
    }
}
