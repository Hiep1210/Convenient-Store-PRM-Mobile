package com.example.convenientstoremobile.model;

public class OrderDetail {
    private int orderId;
    private int quantity;
    private int productId;
    private int id;
    private Product product;
    public OrderDetail(){

    }

    public OrderDetail(int orderId, int quantity, int productId, int id, Product product) {
        this.orderId = orderId;
        this.quantity = quantity;
        this.productId = productId;
        this.id = id;
        this.product = product;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
