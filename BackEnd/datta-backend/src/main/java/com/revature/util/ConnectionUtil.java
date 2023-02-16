package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// ConnectionUtil - class responsible for connecting to the database
public class ConnectionUtil {

    // We only want one connection to the database the entire time
    private static Connection conn;

    // Private constructor 
    private ConnectionUtil() {
        conn = null;
    }

    // This method will either give us a connection to the DB
    // or return the existing connection
    public static Connection getConnection() {

        // Determine if a connection already exists and if so return it
        try {

            if (conn != null && !conn.isClosed()) {

                return conn;
            } 
        
        } catch (SQLException e) {
            e.printStackTrace();
        
        }

        String url, user, pass;

        // Obtain System variables
        url = System.getenv("datta_url");
        user = System.getenv("datta_user");
        pass = System.getenv("datta_password");

        try {
            
            conn = DriverManager.getConnection(url, user, pass);

        } catch (SQLException e) {
           
            e.printStackTrace();
            System.out.println("Incorrect password or URL.");
        }

        return conn;
    }
}