import models.*;
import repository.CartRepository;
import repository.CartRepositoryImpl;
import repository.CouponRepositoryImpl;
import service.CartService;
import service.CartServiceImpl;
import service.CouponService;
import service.CouponServiceImpl;

import java.time.LocalDateTime;
import java.util.Set;

public class CouponManagementSystem {
    public static void main(String[] args) {
        System.out.println("----Coupon Management System Demo----");

        CartService cartService = new CartServiceImpl(CartRepositoryImpl.getInstance());
        CouponService couponService = new CouponServiceImpl(CouponRepositoryImpl.getInstance());

        // 1. Create products
        Product macBook = new Product.ProductBuilder()
                .setName("MacBook")
                .setProductType(ProductType.LAPTOPS)
                .setPrice(2000.0)
                .build();

        Product iPhone = new Product.ProductBuilder()
                .setName("IPhone 17 Pro Max")
                .setProductType(ProductType.MOBILE)
                .setPrice(1000.0)
                .build();

        Product cricketBat = new Product.ProductBuilder()
                .setName("SG Bat")
                .setProductType(ProductType.SPORTS)
                .setPrice(500.0)
                .build();


        // 2. Create a User and let the user add products to cart
        User user = new User("Nikhil");
        Cart cart = cartService.createCart();
        cartService.addItemToCart(cart.getId(), macBook);
        cartService.addItemToCart(cart.getId(), iPhone);
        cartService.addItemToCart(cart.getId(), cricketBat);

        // 3. Apply Coupon
        System.out.println("Cart Price before applying coupon");
        System.out.println(cartService.getCartTotalPrice(cart.getId()));

        System.out.println("Cart Price after applying Flat coupon");
        Coupon flatCoupon = couponService.createCoupon(
                new FlatCouponType(1, 2000.0, LocalDateTime.now().plusDays(2), 800.0, 400.0));
        double finalPrice = cartService.applyCoupon(cart.getId(), user, flatCoupon);
        System.out.println(finalPrice);

        System.out.println("Cart Price after applying Percentage coupon");
        Coupon percentageCoupon = couponService.createCoupon(
                new PercentageCouponType(1, 2000.0, LocalDateTime.now().plusDays(2), 800.0, 50.0));
        double finalCartPrice = cartService.applyCoupon(cart.getId(), user, percentageCoupon);
        System.out.println(finalCartPrice);

        System.out.println("Cart Price after applying coupon restricted to MOBILE products (discount on eligible items only)");
        Coupon mobileOnlyCoupon = couponService.createCoupon(
                new PercentageCouponType(1, 2000.0, LocalDateTime.now().plusDays(2), 800.0, 50.0,
                        Set.of(ProductType.MOBILE)));
        double restrictedPrice = cartService.applyCoupon(cart.getId(), user, mobileOnlyCoupon);
        System.out.println(restrictedPrice);

        System.out.println("Coupon usage count after redemption: " + percentageCoupon.getUsageCount());


    }
}
