package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Cart {
    private final String id;
    private final List<Product> products;

    public Cart() {
        this.id = UUID.randomUUID().toString();
        this.products = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void addItem(Product product) {
        products.add(product);
    }

    public void removeItem(Product product) {
        products.remove(product);
    }

    public double getCartTotalPrice() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }
}
