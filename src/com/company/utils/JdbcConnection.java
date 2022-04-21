package com.company.utils;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcConnection {
    private static final Logger LOGGER =
            Logger.getLogger(JdbcConnection.class.getName());
    private static Optional connection = Optional.empty();

    public static Optional getConnection() {
        if (connection.isEmpty()) {
            String url = "jdbc:postgresql://localhost:5432/testdb";
            String user = "user1";
            String password = "postgres";

            try {
                connection = Optional.ofNullable(
                        DriverManager.getConnection(url, user, password));
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        return connection;
    }
}
