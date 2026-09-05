package models;

import java.time.LocalDateTime;
import java.util.Set;

public class PercentageCouponType extends Coupon {

    private final double discountPercentage;

    public PercentageCouponType(int maxNumberOfUsage, Double minOrderValue, LocalDateTime expirationDate, Double maxDiscountValue, Double discountPercentage) {
        this(maxNumberOfUsage, minOrderValue, expirationDate, maxDiscountValue, discountPercentage, null);
    }

    public PercentageCouponType(int maxNumberOfUsage, Double minOrderValue, LocalDateTime expirationDate, Double maxDiscountValue, Double discountPercentage, Set<ProductType> applicableProductTypes) {
        super(maxNumberOfUsage, minOrderValue, expirationDate, maxDiscountValue, applicableProductTypes);
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double calculateDiscount(double cartPrice) {
        double discount = cartPrice * discountPercentage / 100;
        return Math.min(discount, getMaxDiscountValue());
    }
}
