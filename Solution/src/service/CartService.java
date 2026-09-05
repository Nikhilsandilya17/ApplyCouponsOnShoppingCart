package service;

import models.Cart;
import models.Coupon;
import models.Product;
import models.User;

public interface CartService {
    Cart createCart();
    void addItemToCart(String cartId, Product product);
    void removeItemFromCart(String cartId, Product product);
    double getCartTotalPrice(String cartId);
    double applyCoupon(String cartId, User user, Coupon coupon);
}
