package com.example.store.Tests;

import com.example.store.Data.StoreDao;
import com.example.store.Data.StoreDb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class StoreDaoTests {
  StoreDao storeDao;

  @BeforeEach
  public void beforeEach() throws SQLException {
    storeDao = new StoreDao(new StoreDb());
  }

  /*
    Need to setup db for testing
   */
  @Test
  public void testStoreDao1() throws SQLException {
    var prod = storeDao.getProd("HC");

    assertEquals("Classic Hoodie", prod.getName());
  }

  @Test
  public void testStoreDao2() throws SQLException {
    var prods = storeDao.getProds();

    assertNotNull(prods);
    assertDoesNotThrow(() -> {
      prods.stream()
          .filter(p -> p.productId.equals("HC"))
          .findFirst().orElseThrow();
    });
  }

  @Test
  public void testStoreDao3() {
    assertDoesNotThrow(() -> {
      var storeDao = new StoreDao();
    });
  }
}
