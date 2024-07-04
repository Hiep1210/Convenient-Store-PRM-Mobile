package com.example.convenientstoremobile.model;

import java.sql.Date;

public class Order {
    private int id;
    private Date date;
    private int userId;
    private String address;
    private User user;

    public Order(int id, Date date, int userId, String address, User user) {
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.address = address;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
