package me.gacekmpl2.craftmpg;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private Connection connection;

    private String host;

    private String port;

    private String database;

    private String username;

    private String password;

    public DatabaseManager(String host, String port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        if (this.connection != null && !this.connection.isClosed())
            return;
        synchronized (this) {
            if (this.connection != null && !this.connection.isClosed())
                return;
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
        }
    }

    public ResultSet query(String query) {
        try {
            openConnection();
            Statement statement = this.connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (ClassNotFoundException exception2) {
            exception2.printStackTrace();
        }
        return null;
    }

    public void update(String query) {
        try {
            openConnection();
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (ClassNotFoundException exception2) {
            exception2.printStackTrace();
        }
    }
}