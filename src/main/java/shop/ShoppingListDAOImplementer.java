package shop;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

public class ShoppingListDAOImplementer implements ShoppingListDAO {
    private static final String SELECT_QUERY = "SELECT * FROM item";
    private static final String INSERT_QUERY = "INSERT INTO item (Name, Quantity) VALUES (?, ?)";
    private static final String COUNT_QUERY = "SELECT COUNT(*) AS numOfRecords FROM item WHERE Name IS NOT NULL;";
    private static final String DELETE_QUERY = "TRUNCATE TABLE item;";
    private static String host;
    private static String username;
    private static String password;

    public ShoppingListDAOImplementer() {
        try (InputStream configFile = Files.newInputStream(Paths.get("db-config.properties"))){
            final Properties properties = new Properties();
            properties.load(configFile);
            host = properties.get("host").toString();
            username = properties.get("user").toString();
            password = properties.get("pass").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Item> findAllItems() {
        List<Item> items = new LinkedList<>();
        try(final Connection con = getConnection(host, username, password);
            PreparedStatement SELECT = con.prepareStatement(SELECT_QUERY)){

            final ResultSet resultSet = SELECT.executeQuery();
            while (resultSet.next()){
                final String name = resultSet.getString("Name");
                final int quantity = resultSet.getInt("Quantity");
                items.add(new Item(name, quantity));
            }

        } catch (SQLException e){
            throw new QueryExecutionException(e);
        }
        return items;
    }
    @Override
    public void saveItems(List<Item> items) {
        try(final Connection con = getConnection(host, username, password);
            PreparedStatement INSERT = con.prepareStatement(INSERT_QUERY)){

            for (Item item : items) {
                INSERT.setString(1, item.getName());
                INSERT.setInt(2, item.getQuantity());
                INSERT.executeUpdate();
            }

        } catch (SQLException e){
            throw new QueryExecutionException(e);
        }
    }
    @Override
    public int countRecords() {
        try(final Connection con = getConnection(host, username, password);
            PreparedStatement COUNT = con.prepareStatement(COUNT_QUERY)){

            final ResultSet resultSet = COUNT.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e){
            throw new QueryExecutionException(e);
        }
    }
    @Override
    public void clearList() {
        try(final Connection con = getConnection(host, username, password);
            PreparedStatement DELETE = con.prepareStatement(DELETE_QUERY)){

            DELETE.execute();

        } catch (SQLException e){
            throw new QueryExecutionException(e);
        }
    }
}
