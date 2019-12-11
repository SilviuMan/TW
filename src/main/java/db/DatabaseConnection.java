package db;

import java.sql.*;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private String url;
    private static Connection myConnection = null;
    private Statement myStatement = null;
    private ResultSet myResultSet = null;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private DatabaseConnection(String url) {
        try {
            this.url = url;
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            this.myConnection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/macao" ,"root","root");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("CONNECTION FAILED");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return myConnection;
    }

    public static DatabaseConnection getInstance() throws SQLException {
        return instance;
    }

    public synchronized static DatabaseConnection init(String url) {
        instance = new DatabaseConnection(url);
        return instance;
    }
}