package com.example.store.Tests;

import com.example.store.Models.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTests {
  @Test
  public void testProduct() {
    var prod = new Product("a", "hat", "image.jpg", 5);

    assertEquals(prod.getProductId(), "a");
    assertEquals(prod.getName(), "hat");
    assertEquals(prod.getImageFile(), "image.jpg");
    assertEquals(prod.getPrice(), 5);
  }
}
