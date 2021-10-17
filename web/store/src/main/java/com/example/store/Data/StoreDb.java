package com.example.store.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StoreDb {
  private static final String USER = "root";
  private static final String PASSWORD = "root";
  private final Connection conn;

  public StoreDb() throws SQLException {
    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/basic-store", USER, PASSWORD);
  }

  public PreparedStatement prep(String query) throws SQLException {
    return conn.prepareStatement(query);
  }

  public Connection getConn() {
    return conn;
  }
}
