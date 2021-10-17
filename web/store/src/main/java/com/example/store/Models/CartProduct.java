package com.example.store.Models;

public class CartProduct {
  private final Product product;
  private int count = 0;

  public CartProduct(Product product, int count) {
    this.product = product;
    this.count = count;
  }

  public Product getProduct() {
    return product;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public float getPrice() {
    return count * product.price;
  }
}
