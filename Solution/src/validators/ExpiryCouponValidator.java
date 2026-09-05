package validators;

import models.Coupon;
import models.User;
import models.Cart;

import java.time.LocalDateTime;

public class ExpiryCouponValidator implements CouponValidator {
    private final CouponValidator nextCouponValidator;

    public ExpiryCouponValidator(CouponValidator nextCouponValidator) {
        this.nextCouponValidator = nextCouponValidator;
    }

    @Override
    public void validate(Coupon coupon, Cart cart, User user) {
        System.out.println("Checking Expiry of Coupon");
        if (!coupon.getExpirationDate().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("Coupon has expired");
        }
        this.nextCouponValidator.validate(coupon, cart, user);
    }
}
