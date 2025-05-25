package com.example.kirmizisepetapp;

public class Product {
    private String id;
    private String name;
    private double price;
    private String imageName;
    private String category;


    public Product() {}

    public Product(String id, String name, double price, String imageName, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageName = imageName;
        this.category = category;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImageName() {
        return imageName;
    }

    public String getCategory() { return category; }


    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setCategory(String category) { this.category = category; }


}
