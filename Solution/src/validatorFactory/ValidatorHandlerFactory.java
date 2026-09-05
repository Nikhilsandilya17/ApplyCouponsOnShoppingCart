package validatorFactory;

import models.Coupon;
import validators.*;

public class ValidatorHandlerFactory {
    public static CouponValidator getCouponValidator(Coupon coupon) {
        return new ExpiryCouponValidator(
                new MinOrderValueValidator(
                        new MaxUsageCouponValidator(
                                new ProductTypeValidator(
                                        new FinishingValidator()))));
    }
}
