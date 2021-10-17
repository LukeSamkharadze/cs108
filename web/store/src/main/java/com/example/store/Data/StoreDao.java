package com.example.store.Data;

import com.example.store.Models.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public class StoreDao {
  private static StoreDb store;

  static {
    try {
      store = new StoreDb();
    } catch (SQLException ignored) { }
  }

  public StoreDao() {
  }

  public StoreDao(StoreDb storeDb) {
    store = storeDb;
  }

  public Product getProd(String productId) throws SQLException {
    var con = store.getConn();
    var prep = con.prepareStatement("select * from products where productId = ?");
    prep.setString(1, productId);
    var set = prep.executeQuery();

    set.next();

    return new Product(
        set.getString(1),
        set.getString(2),
        set.getString(3),
        set.getFloat(4)
    );
  }

  public ArrayList<Product> getProds() throws SQLException {
    var set = store.prep("select * from products").executeQuery();

    var prods = new ArrayList<Product>();
    while (set.next()) {
      prods.add(new Product(
          set.getString(1),
          set.getString(2),
          set.getString(3),
          set.getFloat(4)
      ));
    }

    return prods;
  }
}
