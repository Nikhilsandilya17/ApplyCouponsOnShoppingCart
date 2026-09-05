package repository;

import models.Cart;

public interface CartRepository {
    void save(Cart cart);
    Cart findById(String cartId);
}
