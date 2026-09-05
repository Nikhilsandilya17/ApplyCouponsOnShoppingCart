package models;

import java.time.LocalDateTime;
import java.util.Set;

public class FlatCouponType extends Coupon {

    private final double flatDiscountValue;

    public FlatCouponType(int maxNumberOfUsage, Double minOrderValue, LocalDateTime expirationDate, Double maxDiscountValue, Double flatDiscountValue) {
        this(maxNumberOfUsage, minOrderValue, expirationDate, maxDiscountValue, flatDiscountValue, null);
    }

    public FlatCouponType(int maxNumberOfUsage, Double minOrderValue, LocalDateTime expirationDate, Double maxDiscountValue, Double flatDiscountValue, Set<ProductType> applicableProductTypes) {
        super(maxNumberOfUsage, minOrderValue, expirationDate, maxDiscountValue, applicableProductTypes);
        this.flatDiscountValue = flatDiscountValue;
    }

    @Override
    public double calculateDiscount(double cartPrice) {
        return Math.min(flatDiscountValue, getMaxDiscountValue());
    }
}
