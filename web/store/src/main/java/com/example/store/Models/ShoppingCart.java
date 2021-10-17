package com.example.store.Models;

import java.util.ArrayList;

public class ShoppingCart {
  private ArrayList<CartProduct> cartProducts = new ArrayList<>();

  public ArrayList<CartProduct> getCartProducts() {
    return cartProducts;
  }

  public float getPrice() {
    float sum = 0;
    for (var shoppingCartProduct : cartProducts) sum += shoppingCartProduct.getPrice();
    return sum;
  }

  public void resetCart() {
    this.cartProducts.clear();
  }

  public void addSingle(Product product) {
    addWithCount(product, 1);
  }

  public void addWithCount(Product product, int count) {
    for (var shoppingCartProduct : cartProducts)
      if (shoppingCartProduct.getProduct().productId.equals(product.productId)) {
        shoppingCartProduct.setCount(shoppingCartProduct.getCount() + count);
        return;
      }

    cartProducts.add(new CartProduct(product, count));
  }
}
