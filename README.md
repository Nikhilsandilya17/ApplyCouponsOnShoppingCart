# Coupon Management System (LLD)

This is a popular interview question (commonly asked as "Design a Coupon Management System for a food delivery app like Swiggy/Zomato").

## Functional Requirements

1. **Create Coupon** — Admin can create coupons of different types (FLAT, PERCENTAGE, BNGO) with attributes like discount value, max discount cap, min order value, expiry date, global usage limit, and per-user usage limit.

2. **Coupon Applicability Rules** — A coupon can be restricted to specific restaurants, item categories, or item types (e.g., only on pizzas, or only on a user's first N orders).

3. **Apply Coupon to Cart** — Given a cart, the system should determine if a coupon is applicable (validate rules, expiry, usage limits) and calculate the discounted cart total.

4. **Redeem Coupon** — On successful order placement, the coupon usage must be recorded (global + per-user counters) atomically so limits are never breached.

5. **List/Search Coupons** — Fetch all active coupons applicable to a given cart/user so they can be shown to the user before checkout.

6. **Usage Stats / History** — Admin can view coupon usage statistics (total redemptions, users reached, discount given) and a user can view their redeemed coupon history.

## Prioritization (interview strategy)

- **Must build (core)**: Create Coupon, Apply Coupon to Cart (incl. BNGO logic), Redeem Coupon (atomic usage tracking)
- **Build lightly**: Applicability Rules (design as rule chain/filter, implement 2-3 rules), List Coupons
- **Talk about, don't build**: Usage Stats — track a `CouponRedemption` record per order, from which all stats derive

---

## Implementation Overview

### Class Structure

```
src/
├── CouponManagementSystem.java        # Demo driver
├── models/
│   ├── Coupon.java                    # Abstract base: id, limits, expiry, applicable product types
│   ├── FlatCouponType.java            # Flat-amount discount coupon
│   ├── PercentageCouponType.java      # Percentage discount coupon
│   ├── Cart.java                      # Entity: owns cart items + total calculation
│   ├── Product.java                   # Immutable product (builder pattern)
│   ├── ProductType.java               # Enum: SPORTS, LAPTOPS, MOBILE
│   └── User.java
├── validators/                        # Chain of Responsibility
│   ├── CouponValidator.java           # Interface: validate(coupon, cart, user)
│   ├── ExpiryCouponValidator.java     # Checks expiration date
│   ├── MinOrderValueValidator.java    # Checks cart total >= min order value
│   ├── MaxUsageCouponValidator.java   # Checks usageCount < maxNumberOfUsage
│   ├── ProductTypeValidator.java      # Checks cart has at least one eligible product
│   └── FinishingValidator.java        # Terminal node of the chain
├── validatorFactory/
│   └── ValidatorHandlerFactory.java   # Builds the validator chain
├── repository/
│   ├── CartRepository.java / CartRepositoryImpl.java       # save / findById
│   └── CouponRepository.java / CouponRepositoryImpl.java   # save / findById
└── service/
    ├── CartService.java / CartServiceImpl.java     # createCart, add/remove items, applyCoupon
    └── CouponService.java / CouponServiceImpl.java # createCoupon, getCoupon
```

### Design Patterns Used

1. **Template Method / Polymorphism (Coupon hierarchy)** — `Coupon` is abstract with `calculateDiscount(double)`; `FlatCouponType` and `PercentageCouponType` override it. Callers never cast or use `instanceof` — adding a new type (e.g., BNGO) requires no changes to existing code (Open/Closed Principle).

2. **Chain of Responsibility (validators)** — Each validator checks one rule and delegates to the next: `Expiry → MinOrderValue → MaxUsage → ProductType → FinishingValidator`. New rules plug into the chain without modifying existing validators.

3. **Builder (Product)** — `Product` is built via `ProductBuilder` with validation (e.g., price >= 0) at build time.

4. **Singleton (repositories)** — `CartRepositoryImpl` and `CouponRepositoryImpl` expose a static `getInstance()` returning a single shared instance. (Note: this simple lazy-check version is not thread-safe — two threads can pass the `instance == null` check simultaneously. Thread-safe alternatives: synchronized double-checked locking, the initialization-on-demand holder idiom, or an enum singleton — good interview follow-up material.)

5. **Repository** — In-memory `ConcurrentHashMap`-backed storage, interfaces + impls. Repositories speak only storage language (`save`, `findById`) — no business logic.

### Key Design Decisions

- **Rich entities, thin repositories.** Cart behavior (`addItem`, `removeItem`, `getCartTotalPrice`) lives on `Cart`, not in `CartRepository`. Repositories only manage lifecycle (`save`, `findById`). Litmus test: if a method only needs the object's own data, it's an entity method.

- **Domain rules live on the entity that owns the data.** `isApplicableTo(ProductType)`, `getApplicableProducts(...)`, and `getApplicableTotal(...)` are on `Coupon` because the filtering rule depends on the coupon's own `applicableProductTypes` field — and both the validator and the discount calculation reuse the same logic.

- **Discount computed on eligible items only.** `applyCoupon` passes `coupon.getApplicableTotal(cart.getProducts())` to `calculateDiscount`, so a MOBILE-only coupon discounts only the mobile items in a mixed cart, not the full cart price.

- **Apply = preview + usage increment.** `applyCoupon` validates, calculates, and increments the coupon's usage count. (Trade-off: a preview consumes usage with no rollback — in a full system, usage would be committed by an `OrderService.placeOrder` instead, keeping preview and commit separate.)

- **Validators receive full context** (`coupon, cart, user`) — each validator pulls only what it needs (expiry validator ignores cart/user), which keeps the chain extensible for future rules like per-user limits.

### Optional: Strategy Pattern Instead of Abstract Coupon

The discount behavior could alternatively be modeled with **composition over inheritance** — a single concrete `Coupon` class holding a `DiscountStrategy`:

```java
public interface DiscountStrategy {
    double calculateDiscount(double amount);
}

public class FlatDiscountStrategy implements DiscountStrategy {
    private final double flatValue;
    public double calculateDiscount(double amount) { return flatValue; }
}

public class PercentageDiscountStrategy implements DiscountStrategy {
    private final double percentage;
    public double calculateDiscount(double amount) { return amount * percentage / 100; }
}

public class Coupon {
    private final DiscountStrategy discountStrategy;
    // ... id, limits, expiry, applicable types

    public double calculateDiscount(double cartPrice) {
        return Math.min(discountStrategy.calculateDiscount(cartPrice), maxDiscountValue);
    }
}
```

**When to prefer Strategy over subclassing:**
- Discount type can change at runtime (e.g., admin edits a coupon from FLAT to PERCENTAGE — swap the strategy object)
- You want to avoid class explosion (one class per discount type)
- The `maxDiscountValue` cap applies uniformly to all types — with Strategy, the cap is enforced once in `Coupon.calculateDiscount` instead of duplicated in every subclass

**When subclassing (current design) is fine:**
- Discount types are fixed and known up front
- Coupon types may diverge in more than just calculation (e.g., BNGO needs cart item structure, not just a price — a strategy taking only `double` wouldn't fit)
- Simpler to sketch quickly in an interview

## How to Run

```bash
cd Solution/src
javac -d out $(find . -name "*.java")
java -cp out CouponManagementSystem
```
