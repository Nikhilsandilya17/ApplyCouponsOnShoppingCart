package validators;

import models.Cart;
import models.Coupon;
import models.User;

public interface CouponValidator {
    void validate(Coupon coupon, Cart cart, User user);
}
