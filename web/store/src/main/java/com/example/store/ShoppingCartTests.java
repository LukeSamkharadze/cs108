//package com.example.store;
//
//import com.example.store.Models.Product;
//import com.example.store.Models.ShoppingCart;
//import com.example.store.Models.ShoppingCartProduct;
//import org.junit.jupiter.api.*;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class ShoppingCartTests {
//  ShoppingCart shoppingCart;
//
//  @BeforeEach
//  public void setup() {
//    shoppingCart = new ShoppingCart();
//  }
//
//  @Test
//  public void test1() {
//    Product product = new Product("01", "ado", "ado.png", 10);
//    assertEquals(product.getProductId(), "01");
//    assertEquals(product.getName(), "ado");
//    assertEquals(product.getImageFile(), "ado.png");
//    assertEquals(product.getPrice(), 10);
//  }
//
//  @Test
//  public void test2() {
//    Product product = new Product("01", "ado", "ado.png", 10);
//    ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct(product, 1);
//    assertEquals(shoppingCartProduct.getProduct(), product);
//    assertEquals(shoppingCartProduct.getCount(), 1);
//    assertEquals(shoppingCartProduct.getPrice(), 10);
//
//    shoppingCartProduct.setCount(shoppingCartProduct.getCount()+1);
//
//    assertEquals(shoppingCartProduct.getPrice(), 20);
//  }
//
//  @Test
//  public void test3() {
//    Product product1 = new Product("01", "ado", "ado.png", 10);
//    Product product2 = new Product("02", "ado", "ado.png", 20);
//    ShoppingCart shoppingCart = new ShoppingCart();
//
//    assertEquals(shoppingCart.getPrice(), 0);
//
//    shoppingCart.add(product1);
//    assertEquals(shoppingCart.getPrice(), 10);
//
//    shoppingCart.add(product1);
//    assertEquals(shoppingCart.getPrice(), 20);
//
//    shoppingCart.add(product2);
//    assertEquals(shoppingCart.getPrice(), 40);
//
//    shoppingCart.add(product2);
//    assertEquals(shoppingCart.getPrice(), 60);
//  }
//
//  @Test
//  public void test4() {
//    Product product = new Product("01", "ado", "ado.png", 10);
//    ShoppingCart shoppingCart = new ShoppingCart();
//    assertEquals(shoppingCart.getPrice(), 0);
//
//    shoppingCart.set(product, 1);
//    assertEquals(shoppingCart.getPrice(), 10);
//    shoppingCart.clear();
//
//
//    shoppingCart.set(product, 2);
//    assertEquals(shoppingCart.getPrice(), 20);
//    shoppingCart.clear();
//  }
//
//  @Test
//  public void test5() {
//    Product product1 = new Product("01", "ado", "ado.png", 10);
//    Product product2 = new Product("02", "ado", "ado.png", 20);
//
//    ShoppingCart shoppingCart = new ShoppingCart();
//
//    shoppingCart.add(product1);
//    assertEquals(shoppingCart.getshoppingCartProducts().size(), 1);
//
//    shoppingCart.add(product2);
//    assertEquals(shoppingCart.getshoppingCartProducts().size(), 2);
//  }
//}
