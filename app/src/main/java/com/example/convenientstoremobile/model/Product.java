package com.example.convenientstoremobile.model;

public class Product {
    private int id;
    private String name;
    private int supplierId;
    private Double price;
    private int catId;
    private int imageId;
    private Category cat;
    private Image image;
    private Supplier supplier;

    public Product(){
    }

    public Product(int id, String name, int supplierId, Double price, int catId, int imageId, Category cat, Image image, Supplier supplier) {
        this.id = id;
        this.name = name;
        this.supplierId = supplierId;
        this.price = price;
        this.catId = catId;
        this.imageId = imageId;
        this.cat = cat;
        this.image = image;
        this.supplier = supplier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Category getCat() {
        return cat;
    }

    public void setCat(Category cat) {
        this.cat = cat;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
