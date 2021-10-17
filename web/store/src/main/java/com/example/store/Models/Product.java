package com.example.store.Models;

public class Product {
  public String productId;
  public String name;
  public String imageFile;
  public float price;

  public Product(String productId, String name, String imageFile, float price) {
    this.productId = productId;
    this.name = name;
    this.imageFile = imageFile;
    this.price = price;
  }

  public String getProductId() {
    return productId;
  }

  public String getName() {
    return name;
  }

  public String getImageFile() {
    return imageFile;
  }

  public float getPrice() {
    return price;
  }
}
