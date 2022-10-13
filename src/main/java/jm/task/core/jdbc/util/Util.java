package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    private static String URL = "jdbc:mysql://localhost:3306/db_task1";
    private static String USER = "admin";
    private static String PSWD = "kata123";


    public static Connection getConnection()  {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PSWD);
        }catch (SQLException e) {
            System.out.println("Connection error");
        }
        return connection;
    }

}
