package service;

import models.Cart;
import models.Coupon;
import models.Product;
import models.User;
import repository.CartRepository;
import repository.CartRepositoryImpl;
import validatorFactory.ValidatorHandlerFactory;
import validators.CouponValidator;

public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    public CartServiceImpl() {
        this.cartRepository = CartRepositoryImpl.getInstance();
    }

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart createCart() {
        Cart cart = new Cart();
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public void addItemToCart(String cartId, Product product) {
        Cart cart = getCartOrThrow(cartId);
        cart.addItem(product);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(String cartId, Product product) {
        Cart cart = getCartOrThrow(cartId);
        cart.removeItem(product);
        cartRepository.save(cart);
    }

    @Override
    public double applyCoupon(String cartId, User user, Coupon coupon) {
        Cart cart = getCartOrThrow(cartId);
        CouponValidator couponValidator = ValidatorHandlerFactory.getCouponValidator(coupon);
        try {
            couponValidator.validate(coupon, cart, user);
        } catch (Exception e) {
            System.out.println("Coupon validation failed, cannot be applied");
            throw new IllegalArgumentException("Coupon validation failed: " + coupon.getId());
        }
        double cartPrice = cart.getCartTotalPrice();
        double applicableTotal = coupon.getApplicableTotal(cart.getProducts());
        double discount = coupon.calculateDiscount(applicableTotal);
        coupon.useCoupon();
        return cartPrice - discount;
    }

    @Override
    public double getCartTotalPrice(String cartId) {
        return getCartOrThrow(cartId).getCartTotalPrice();
    }

    private Cart getCartOrThrow(String cartId) {
        Cart cart = cartRepository.findById(cartId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found: " + cartId);
        }
        return cart;
    }
}
