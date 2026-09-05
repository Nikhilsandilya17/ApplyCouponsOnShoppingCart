package validators;

import models.Cart;
import models.Coupon;
import models.User;

public class MinOrderValueValidator implements CouponValidator {
    private final CouponValidator nextCouponValidator;

    public MinOrderValueValidator(CouponValidator nextCouponValidator) {
        this.nextCouponValidator = nextCouponValidator;
    }

    @Override
    public void validate(Coupon coupon, Cart cart, User user) {
        System.out.println("Min Order Value Validator");
        double cartPrice = cart.getCartTotalPrice();
        if (cartPrice < coupon.getMinCartOrderValue()) {
            throw new RuntimeException("Cart price " + cartPrice + " is below minimum order value " + coupon.getMinCartOrderValue());
        }
        this.nextCouponValidator.validate(coupon, cart, user);
    }
}
