package com.example.convenientstoremobile.model;

public class OrderDetail {
    private int orderId;
    private String quantity;
    private int productId;
    private int id;
    private boolean isProcess;
    private Order order;
    private Product product;

    public OrderDetail(int orderId, String quantity, int productId, int id, boolean isProcess, Order order, Product product) {
        this.orderId = orderId;
        this.quantity = quantity;
        this.productId = productId;
        this.id = id;
        this.isProcess = isProcess;
        this.order = order;
        this.product = product;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
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

    public boolean isProcess() {
        return isProcess;
    }

    public void setProcess(boolean process) {
        isProcess = process;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
