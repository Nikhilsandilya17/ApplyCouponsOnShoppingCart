package validators;

import models.Cart;
import models.Coupon;
import models.User;

public class MaxUsageCouponValidator implements CouponValidator {
    private final CouponValidator nextCouponValidator;

    public MaxUsageCouponValidator(CouponValidator nextCouponValidator) {
        this.nextCouponValidator = nextCouponValidator;
    }

    @Override
    public void validate(Coupon coupon, Cart cart, User user) {
        System.out.println("Max Usage Coupon Validator");
        if (coupon.getUsageCount() >= coupon.getMaxNumberOfUsage()) {
            throw new RuntimeException("Max Usage Coupon Amount Exceeded");
        }
        this.nextCouponValidator.validate(coupon, cart, user);
    }
}
