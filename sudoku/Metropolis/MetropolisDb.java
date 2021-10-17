package Metropolis;

import java.sql.*;

/**
 * Makes calls to DB
 *
 * @author Luka Samkharadze
 */
public class MetropolisDb {
  private static final String PROTOCOL = "jdbc:mysql://";
  private static final String DOMAIN = "localhost";
  private static final String PORT = "3306";
  private static final String NAME = "metro";
  private static final String USERNAME = "root";
  private static final String PASSWORD = "root";

  public static final String METROPOLIS_TABLE_NAME = "metropolises";
  public static final String METROPOLIS_TESTS_TABLE_NAME = "metropolisesTests";

  private static Connection connection;

  MetropolisDb() throws SQLException {
    connection = DriverManager.getConnection(PROTOCOL + DOMAIN + ":" + PORT + "/" + NAME, USERNAME, PASSWORD);
  }

  PreparedStatement prepareStatement(String query) throws SQLException {
    return connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
  }

  Connection getConnection() {
    return  connection;
  }
}
