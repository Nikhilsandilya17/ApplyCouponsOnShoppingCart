package models;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class Coupon {
    private final String id;
    private final int maxNumberOfUsage;
    private final Double minCartOrderValue;
    private final LocalDateTime expirationDate;
    private final Double maxDiscountValue;
    private final Set<ProductType> applicableProductTypes;
    private int usageCount;

    public Coupon(int maxNumberOfUsage, Double minCartOrderValue, LocalDateTime expirationDate, Double maxDiscountValue) {
        this(maxNumberOfUsage, minCartOrderValue, expirationDate, maxDiscountValue, null);
    }

    public Coupon(int maxNumberOfUsage, Double minCartOrderValue, LocalDateTime expirationDate, Double maxDiscountValue, Set<ProductType> applicableProductTypes) {
        this.id = UUID.randomUUID().toString();
        this.maxNumberOfUsage = maxNumberOfUsage;
        this.minCartOrderValue = minCartOrderValue;
        this.expirationDate = expirationDate;
        this.maxDiscountValue = maxDiscountValue;
        this.applicableProductTypes = applicableProductTypes == null
                ? new HashSet<>()
                : new HashSet<>(applicableProductTypes);
        this.usageCount = 0;
    }

    public List<Product> getApplicableProducts(List<Product> products) {
        if (applicableProductTypes.isEmpty()) {
            return products;
        }
        return products.stream()
                .filter(product -> applicableProductTypes.contains(product.getProductType()))
                .collect(Collectors.toList());
    }

    public double getApplicableTotal(List<Product> products) {
        return getApplicableProducts(products).stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public Set<ProductType> getApplicableProductTypes() {
        return Collections.unmodifiableSet(applicableProductTypes);
    }

    public String getId() {
        return id;
    }

    public int getMaxNumberOfUsage() {
        return maxNumberOfUsage;
    }

    public Double getMinCartOrderValue() {
        return minCartOrderValue;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public Double getMaxDiscountValue() {
        return maxDiscountValue;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void useCoupon(){
        this.usageCount++;
    }

    public abstract double calculateDiscount(double cartPrice);
}
