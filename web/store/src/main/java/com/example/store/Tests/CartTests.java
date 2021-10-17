package com.example.store.Tests;

import com.example.store.Models.Product;
import com.example.store.Models.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartTests {
  Product prod_1;
  Product prod_2;
  ShoppingCart cart;

  @BeforeEach
  public void beforeEach() {
    prod_1 = new Product("a", "hat", "image.jpg", 5);
    prod_2 = new Product("b", "bat", "image.jpg", 10);
    cart = new ShoppingCart();
  }

  @Test
  public void testCart1() {
    assertEquals(cart.getPrice(), 0);
    cart.addSingle(prod_1);

    assertEquals(5, cart.getPrice());
    cart.addSingle(prod_1);

    assertEquals(10, cart.getPrice());
    cart.addSingle(prod_2);

    assertEquals(20, cart.getPrice());
    cart.addSingle(prod_2);

    assertEquals(30, cart.getPrice());
  }

  @Test
  public void testCart2() {
    assertEquals(cart.getPrice(), 0);
    cart.addWithCount(prod_1, 1);

    assertEquals(5, cart.getPrice());
    cart.resetCart();

    cart.addWithCount(prod_1, 2);
    assertEquals(10, cart.getPrice());
  }

  @Test
  public void testCart3() {
    cart.addSingle(prod_1);
    assertEquals(cart.getCartProducts().size(), 1);

    cart.addSingle(prod_2);
    assertEquals(cart.getCartProducts().size(), 2);
  }
}
