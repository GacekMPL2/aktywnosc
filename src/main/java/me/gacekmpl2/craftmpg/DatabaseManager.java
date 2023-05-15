package me.gacekmpl2.craftmpg;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public class DatabaseManager {
    private HikariDataSource hikariDS;

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

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mariadb://" + this.host + ":" + this.port + "/" + this.database);
        config.setUsername(this.username);
        config.setPassword(this.password);

        this.hikariDS = new HikariDataSource();
    }

    /**
     * Executes query
     * @param query query to execute
     * @param values optional values to set
     * @return true if query was executed successfully, false otherwise
     */
    public boolean execute(String query, Object... values) {
        try (Connection connection = hikariDS.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                if (values.length > 0) {
                    for (int i = 0; i < values.length; i++) {
                        statement.setObject(i + 1, values[i]);
                    }
                }
                return statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Executes query and returns result
     * @param query query to execute
     * @param values optional values to set
     * @return result of query
     */
    public ResultSet query(String query, Object... values) {
        try (Connection connection = hikariDS.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                if (values.length > 0) {
                    for (int i = 0; i < values.length; i++) {
                        stmt.setObject(i + 1, values[i]);
                    }
                }
                return stmt.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Executes update and returns result
     * @param query query to execute
     * @param values optional values to set
     * @return result of update
     */
    public int update(String query, Object... values) {
        try (Connection connection = hikariDS.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                if (values.length > 0) {
                    for (int i = 0; i < values.length; i++) {
                        stmt.setObject(i + 1, values[i]);
                    }
                }
                return stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
