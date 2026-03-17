/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package busybee.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String HOST = "localhost";
    private static final int PORT = 1433;
    private static final String DB_NAME = "busybee_db";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "a24128";

    private static final String URL = "jdbc:sqlserver://"
            + HOST + ":" + PORT + ";"
            + "databaseName=" + DB_NAME + ";"
            + "encrypt=true;"
            + "trustServerCertificate=true;"
            + "loginTimeout=5;";

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQL Server Driver not found", e);
        }
    }

    public static Connection getConn() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
