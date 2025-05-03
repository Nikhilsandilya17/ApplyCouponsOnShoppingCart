import model.Item1;
import model.Product;
import model.ProductType;

public class Main{
    public static void main(String[] args) {
        Product product1 = new Item1("FAN", 100.0, ProductType.ELECTRONIC);
        Product product2 = new Item1("TV", 200.0, ProductType.ELECTRONIC);
        Product product3 = new Item1("BAT", 300.0, ProductType.SPORTS);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addToCart(product1);
        shoppingCart.addToCart(product2);
        shoppingCart.addToCart(product3);

        System.out.println("Total price after discount : " + shoppingCart.getTotalPrice());
    }
}