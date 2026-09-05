package service;

import models.Coupon;
import repository.CouponRepository;
import repository.CouponRepositoryImpl;

public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;

    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public Coupon createCoupon(Coupon coupon) {
        couponRepository.save(coupon);
        return coupon;
    }

    @Override
    public Coupon getCoupon(String couponId) {
        Coupon coupon = couponRepository.findById(couponId);
        if (coupon == null) {
            throw new IllegalArgumentException("Coupon not found: " + couponId);
        }
        return coupon;
    }
}
