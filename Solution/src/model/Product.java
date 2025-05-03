package model;

public abstract class Product {
    public String name;
    public double price;
    public ProductType productType;
    public abstract double getPrice();

    protected Product(String name, double price, ProductType productType) {
        this.name = name;
        this.price = price;
        this.productType = productType;
    }

    public ProductType getProductType() {
        return productType;
    }
}
