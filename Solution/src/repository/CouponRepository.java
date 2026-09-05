package repository;

import models.Coupon;

public interface CouponRepository {
    void save(Coupon coupon);
    Coupon findById(String couponId);
}
