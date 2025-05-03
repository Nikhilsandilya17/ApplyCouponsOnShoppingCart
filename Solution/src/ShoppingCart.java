import model.Product;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    List<Product> products;

    public ShoppingCart() {
        this.products = new ArrayList<>();
    }

    public void addToCart(Product product) {
        Product productsWithEligibleDiscount = new TypeCouponDecorator(new PercentageCouponDecorator(product, 10), 3, product.getProductType());
        products.add(productsWithEligibleDiscount);
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (Product product : products) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

}
