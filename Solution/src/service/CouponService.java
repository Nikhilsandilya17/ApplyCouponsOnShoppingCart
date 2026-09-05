package service;

import models.Coupon;

public interface CouponService {
    Coupon createCoupon(Coupon coupon);
    Coupon getCoupon(String couponId);
}
