import model.Product;

public class PercentageCouponDecorator extends CouponDecorator {

    Product product;
    int discountPercentage;
    public PercentageCouponDecorator(Product product, int discountPercentage) {
        super(product.name, product.price, product.productType);
        this.product = product;
        this.discountPercentage = discountPercentage;

    }

    @Override
    public double getPrice() {
        double price = product.getPrice();
        System.out.println("Applying discount of: "+ discountPercentage+ " % on product: "+ product.name);
        return price - (price * discountPercentage / 100);
    }
}
