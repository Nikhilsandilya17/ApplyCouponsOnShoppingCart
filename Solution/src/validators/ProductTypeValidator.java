package validators;

import models.Cart;
import models.Coupon;
import models.User;

public class ProductTypeValidator implements CouponValidator {
    private final CouponValidator nextCouponValidator;

    public ProductTypeValidator(CouponValidator nextCouponValidator) {
        this.nextCouponValidator = nextCouponValidator;
    }

    @Override
    public void validate(Coupon coupon, Cart cart, User user) {
        System.out.println("Product Type Validator");
        if (coupon.getApplicableProducts(cart.getProducts()).isEmpty()) {
            throw new RuntimeException("Coupon not applicable to any product in the cart");
        }
        this.nextCouponValidator.validate(coupon, cart, user);
    }
}
