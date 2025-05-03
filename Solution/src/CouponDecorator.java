import model.Product;
import model.ProductType;

public abstract class CouponDecorator extends Product {

    protected CouponDecorator(String name, double price, ProductType productType) {
        super(name, price, productType);
    }
}
