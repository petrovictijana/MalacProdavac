package server.server.models;

import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "seller_id")
    private int sellerId;

    @Column(name="product_name")
    private String productName;
    private String picture;
    private String description;
    private double price;

    private int idCategory;
    private int idMeasurement;



    public Product() {
    }

    public Product(Long productId, int sellerId, String productName, String picture, String description, double price, int idCategory, int idMeasurement) {
        this.productId = productId;
        this.sellerId = sellerId;
        this.productName = productName;
        this.picture = picture;
        this.description = description;
        this.price = price;
        this.idCategory = idCategory;
        this.idMeasurement = idMeasurement;
    }

    public Product(int sellerId, String productName, String picture, String description, double price, int idCategory, int idMeasurement) {
        this.sellerId = sellerId;
        this.productName = productName;
        this.picture = picture;
        this.description = description;
        this.price = price;
        this.idCategory = idCategory;
        this.idMeasurement = idMeasurement;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getIdMeasurement() {
        return idMeasurement;
    }

    public void setIdMeasurement(int idMeasurement) {
        this.idMeasurement = idMeasurement;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", sellerId=" + sellerId +
                ", productName='" + productName + '\'' +
                ", picture='" + picture + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", idCategory=" + idCategory +
                ", idMeasurement=" + idMeasurement +
                '}';
    }
}
