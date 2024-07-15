package com.example.convenientstoremobile.model;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int id;
    private String date;
    private int userId;
    private String address;
    private boolean isProcess;
    private User user;
    private List<OrderDetail> orderdetails;
    public Order() {
    }

    public Order(int id, String date, int userId, String address, boolean isProcess, User user, List<OrderDetail> orderdetails) {
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.address = address;
        this.isProcess = isProcess;
        this.user = user;
        this.orderdetails = orderdetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isProcess() {
        return isProcess;
    }

    public void setProcess(boolean process) {
        isProcess = process;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderDetail> getOrderdetails() {
        return orderdetails;
    }

    public void setOrderdetails(List<OrderDetail> orderdetails) {
        this.orderdetails = orderdetails;
    }
}
