package repository;

import models.Coupon;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CouponRepositoryImpl implements CouponRepository {
    private final Map<String, Coupon> coupons = new ConcurrentHashMap<>();
    public static volatile CouponRepositoryImpl instance;
    public static CouponRepositoryImpl getInstance() {
        if (instance == null) {
            return instance = new CouponRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void save(Coupon coupon) {
        coupons.put(coupon.getId(), coupon);
    }

    @Override
    public Coupon findById(String couponId) {
        return coupons.get(couponId);
    }
}
