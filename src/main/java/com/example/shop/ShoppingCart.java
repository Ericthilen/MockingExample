package shop;

public class ShoppingCart {

    private int totalQuantity = 0;

    public void addItem(String name, double price, int quantity) {
        totalQuantity += quantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }
}
