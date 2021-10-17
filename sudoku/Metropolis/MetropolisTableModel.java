package Metropolis;

import javax.swing.table.AbstractTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MetropolisTableModel extends AbstractTableModel {

  private MetropolisDb metropolisDb;
  private ResultSet resultSet;

  private final String tableName;

  MetropolisTableModel(String tableName) throws SQLException {
    metropolisDb = new MetropolisDb();
    this.tableName = tableName;
    resultSet = metropolisDb.prepareStatement("select * from " + tableName).executeQuery();
  }

  public MetropolisTableModel(MetropolisDb metropolisDb, String tableName) throws SQLException {
    this.metropolisDb = metropolisDb;
    this.tableName = tableName;
  }

  /**
   * @return number of rows in table
   */
  public int getRowCount() {
    try {
      resultSet.last();
      var a = resultSet.getRow();
      return a;
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return 0;
  }

  /**
   * @return number of columns in table
   */
  public int getColumnCount() {
    try {
      return resultSet.getMetaData().getColumnCount();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return 0;
  }

  /**
   * Gets latest database info
   * @throws SQLException
   */
  public void syncDatabase() throws SQLException {
    resultSet = metropolisDb.prepareStatement("select * from " + tableName).executeQuery();
  }

  /**
   * @param column id of column you want to get name for
   * @return name of column with specified id
   */
  public String getColumnName(int column) {
    try {
      var columnName = resultSet.getMetaData().getColumnName(column + 1);
      if (columnName.length() == 0)
        return columnName;
      return Character.toUpperCase(columnName.charAt(0)) + columnName.substring(1);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return null;
  }

  /**
   * @param rowIndex    Row index of value you want to get
   * @param columnIndex Column index of value you want to get
   * @return Value given specified row and column
   */
  public Object getValueAt(int rowIndex, int columnIndex) {
    try {
      resultSet.absolute(rowIndex + 1);
      return resultSet.getObject(columnIndex + 1);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return null;
  }

  /**
   * Adds metropolis to the database and table
   *
   * @param metropolis Name of metropolis
   * @param continent  Continent of metropolis
   * @param population Population of metropolis
   */
  public void add(String metropolis, String continent, String population) {
    try {
      String query = "INSERT INTO " + tableName + " VALUES(?,?,?);";

      PreparedStatement preparedStatement = metropolisDb.prepareStatement(query);
      preparedStatement.setString(1, metropolis);
      preparedStatement.setString(2, continent);
      preparedStatement.setString(3, population);

      preparedStatement.executeUpdate();

      search(metropolis, continent, population, true, true);

    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  /**
   * Queries database and updates table
   *
   * @param metropolis           Name of metropolis
   * @param continent            Continent of metropolis
   * @param population           Population of metropolis
   * @param populationLargerThan True if you are searching for bigger population
   * @param exactMatch           True if you are searching for exact match
   */
  public void search(String metropolis,
                     String continent,
                     String population,
                     boolean populationLargerThan,
                     boolean exactMatch) {
    try {
      String query = createSearchQuery(metropolis, continent, population, populationLargerThan, exactMatch);
      var preparedStatement = metropolisDb.prepareStatement(query);

      int currColumnID = 0;
      if (!metropolis.isEmpty())
        preparedStatement.setString(++currColumnID, appendPercentSign(metropolis, exactMatch));
      if (!continent.isEmpty())
        preparedStatement.setString(++currColumnID, appendPercentSign(continent, exactMatch));
      if (!population.isEmpty())
        preparedStatement.setString(++currColumnID, population);

      resultSet = preparedStatement.executeQuery();

      fireTableStructureChanged();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  /**
   * Helper method for making database queries
   *
   * @param input   String that needs to be appended with AND
   * @param needAnd True if it needs to appended with AND
   * @return Returns appended string if specified with needAnd
   */
  private String appendAnd(String input, boolean needAnd) {
    if (needAnd)
      return " AND" + input;
    return input;
  }


  /**
   * @param input          String that needs to be appended with %
   * @param needPercentage True if it needs to appended with %
   * @return Returns apended string if specified with needPercentage
   */
  private String appendPercentSign(String input, boolean needPercentage) {
    if (needPercentage)
      return input;
    return "%" + input + "%";
  }

  /**
   * @param metropolis            Name of metropolis
   * @param continent             Continent of metropolis
   * @param population            Population of metropolis
   * @param populationGreaterThan True if you are searching for bigger population
   * @param isExactMatch          Tue if you are searching for exact match
   * @return Query that will be executed on database, but needs data to be placed instead of (?)s
   */
  private String createSearchQuery(String metropolis,
                                   String continent,
                                   String population,
                                   boolean populationGreaterThan,
                                   boolean isExactMatch) {
    String query = "SELECT * FROM " + tableName;

    if (metropolis.isEmpty() && continent.isEmpty() && population.isEmpty())
      return query + ";";

    query += " WHERE";

    String operatorForStr = isExactMatch ? "=" : "LIKE";
    String operatorForInt = isExactMatch ? operatorForStr : populationGreaterThan ? ">" : "<";

    if (!metropolis.isEmpty())
      query += " metropolis " + operatorForStr + " ?";
    if (!continent.isEmpty())
      query += appendAnd(" continent " + operatorForStr + " ?", !metropolis.isEmpty());
    if (!population.isEmpty())
      query += appendAnd(" population " + operatorForInt + " ?", !metropolis.isEmpty() || !continent.isEmpty());

    return query + ";";
  }
}
