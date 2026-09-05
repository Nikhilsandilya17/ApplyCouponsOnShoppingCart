package validators;

import models.Cart;
import models.Coupon;
import models.User;

public class FinishingValidator implements CouponValidator {

    @Override
    public void validate(Coupon coupon, Cart cart, User user) {
        System.out.println("Finishing Coupon Validator");
    }
}
