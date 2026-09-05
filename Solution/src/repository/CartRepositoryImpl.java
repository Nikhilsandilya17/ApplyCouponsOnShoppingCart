package repository;

import models.Cart;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CartRepositoryImpl implements CartRepository {
    private final Map<String, Cart> carts = new ConcurrentHashMap<>();
    public static volatile CartRepositoryImpl instance;

    public static CartRepository getInstance() {
        if (instance == null) {
            return instance = new CartRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void save(Cart cart) {
        carts.put(cart.getId(), cart);
    }

    @Override
    public Cart findById(String cartId) {
        return carts.get(cartId);
    }
}
