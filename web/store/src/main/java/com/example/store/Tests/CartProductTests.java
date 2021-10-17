package com.example.store.Tests;

import com.example.store.Models.CartProduct;
import com.example.store.Models.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartProductTests {
  @Test
  public void testCartProduct() {
    var prod = new Product("a", "hat", "image.jpg", 5);
    var cartProd = new CartProduct(prod, 1);

    assertEquals(cartProd.getProduct(), prod);
    assertEquals(cartProd.getCount(), 1);
    assertEquals(cartProd.getPrice(), 5);

    cartProd.setCount(cartProd.getCount() + 1);

    assertEquals(cartProd.getPrice(), 10);
  }
}
