package models;

import java.util.UUID;

public class Product {
    private final String id;
    private final String name;
    private final ProductType productType;
    private final double price;

    private Product(ProductBuilder productBuilder) {
        this.id = productBuilder.id;
        this.productType = productBuilder.productType;
        this.name = productBuilder.name;
        this.price = productBuilder.price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ProductType getProductType() {
        return productType;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", productType=" + productType + '}';
    }

    public static class ProductBuilder{
        private final String id;
        private String name;
        private ProductType productType;
        private double price;

        public ProductBuilder() {
            this.id = UUID.randomUUID().toString();
        }

        public ProductBuilder setName(String name){
            this.name = name;
            return this;
        }

        public ProductBuilder setProductType(ProductType productType){
            this.productType = productType;
            return this;
        }

        public ProductBuilder setPrice(double price){
            if(price < 0){
                throw new IllegalArgumentException("Price cannot be negative");
            }
            this.price = price;
            return this;
        }

        public Product build(){
            return new Product(this);
        }
    }
}
