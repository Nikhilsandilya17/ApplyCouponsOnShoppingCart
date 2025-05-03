import model.Product;
import model.ProductType;

import java.util.List;

public class TypeCouponDecorator extends CouponDecorator {

    private static final List<ProductType> eligibleTypes = List.of(ProductType.HOUSEHOLD, ProductType.ELECTRONIC);

    Product product;
    ProductType productType;
    int discountPercentage;


    public TypeCouponDecorator(Product product, int discountPercentage, ProductType productType) {
        super(product.name, product.price, product.productType);
        this.product = product;
        this.discountPercentage = discountPercentage;
        this.productType = productType;

    }

    @Override
    public double getPrice() {
        double price = product.getPrice();
        if(eligibleTypes.contains(productType)){
            System.out.println("Product type is eligible for discount for pro" +
                    "duct: "+ product.name);
            System.out.println("Applying discount of: "+ discountPercentage+ " % on product: "+ product.name);
            return price - (price * discountPercentage / 100);
        }
        System.out.println("Product type is not eligible for discount for product: "+ product.name);
        return price;
    }
}
