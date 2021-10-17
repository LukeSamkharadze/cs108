package Metropolis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * RUN metropolisesTests.sql before testing
 *
 * @author Luka Samkharadze
 */
public class MetropolisTableModelTests {
  private final MetropolisDb metropolisDb = new MetropolisDb();
  public MetropolisTableModel metropolisTableModel = new MetropolisTableModel(metropolisDb, MetropolisDb.METROPOLIS_TESTS_TABLE_NAME);

  public MetropolisTableModelTests() throws SQLException {
  }

  @BeforeEach
  public void setup() throws SQLException {
    metropolisDb.getConnection().prepareStatement("DELETE FROM metropolisesTests;").executeUpdate();
    metropolisDb.getConnection().prepareStatement(
        """
            INSERT INTO metropolisesTests
            VALUES
            ("Mumbai", "Asia", 20400000),
            ("New York", "North America", 21295000),
            ("San Francisco", "North America", 5780000),
            ("London", "Europe", 8580000),
            ("Rome", "Europe", 2715000),
            ("Melbourne", "Australia", 3900000),
            ("San Jose", "North America", 7354555),
            ("Rostov-on-Don", "Europe", 1052000);
            """).executeUpdate();
    metropolisTableModel.syncDatabase();
  }

  @Test
  public void testGetColumnsNames() {
    var namesArray = new ArrayList<String>();
    namesArray.add(metropolisTableModel.getColumnName(0));
    namesArray.add(metropolisTableModel.getColumnName(1));
    namesArray.add(metropolisTableModel.getColumnName(2));

    assertTrue(namesArray.contains("Metropolis"));
    assertTrue(namesArray.contains("Continent"));
    assertTrue(namesArray.contains("Population"));
  }

  @Test
  public void testGetColumnCount() {
    assertEquals(3, metropolisTableModel.getColumnCount());
  }

  @Test
  public void testGetValueAt() {
    assertEquals("Mumbai", metropolisTableModel.getValueAt(0, 0));
  }

  @Test
  public void testAdd() throws SQLException {
    var oldRowCount = metropolisTableModel.getRowCount();
    metropolisTableModel.add("Test", "Test", "100");
    ResultSet rs = metropolisDb.getConnection().prepareStatement("SELECT COUNT(*) AS rowcount FROM metropolisesTests").executeQuery();
    rs.next();
    int count = rs.getInt("rowcount");
    rs.close();
    assertEquals(oldRowCount+1, count);
  }

  @Test
  public void testSearch() {
    metropolisTableModel.search("New", "", "", false, false);
    assertEquals(1, metropolisTableModel.getRowCount());
    metropolisTableModel.search("", "", "20000000", true, false);
    assertEquals(2, metropolisTableModel.getRowCount());
  }
}
