package utils;

import javax.swing.*;
import java.sql.*;

/**
 * Created by Emile Bronkhorst.
 * Created with IntelliJ IDEA
 * Copyright © 2015 Etheon Solutions
 */
public class Database {
    private Database(){}

    private static Database database = null;
    private Connection conn;
    private ResultSet results;
    private ResultSetMetaData rsm;
    private String USERNAME = "Admin";
    private String PASSWORD = "paladin12";
    private String IPADDRESS = "//127.0.0.1:3306/";
    private String DATABASE =  "UserTable";


    public static Database getInstance(){
        if(database == null)database = new Database();

        return database;
    }

    public boolean connectToDBForName(String name){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql:" + IPADDRESS + name, USERNAME, PASSWORD);
        } catch (SQLException e){
            JOptionPane.showMessageDialog(new JOptionPane(), e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return conn != null;
    }

    public boolean connectDB(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql:" + IPADDRESS + DATABASE, USERNAME, PASSWORD);
        } catch (SQLException e){
            JOptionPane.showMessageDialog(new JOptionPane(), e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return conn != null;
    }

    public ResultSet executeQuery(String query){
        if(connectDB()){
            try{
                Statement sqlStatement = conn.createStatement();
                results = sqlStatement.executeQuery(query);
            } catch(SQLException e){}
        }
        return results;
    }

    public Integer executeUpdate(String query){
        if(connectDB()){
            try{
                Statement sqlStatement = conn.createStatement();
                return sqlStatement.executeUpdate(query);
            } catch(SQLException e){}
        }
        return -1;
    }

    public ResultSet executeQuery(String query, String dbName){
        if(connectToDBForName(dbName)){
            try{
                Statement sqlStatement = conn.createStatement();
                results = sqlStatement.executeQuery(query);
            } catch(SQLException e){}
        }
        return results;
    }

    public boolean execute(String query){
        if(connectDB()){
            try{
                Statement sqlStatement = conn.createStatement();
                return sqlStatement.execute(query);
            } catch(SQLException e){}
        }
        return false;
    }

    public ResultSet getResults(){
        return this.results;
    }

    public ResultSetMetaData getMetaData(){
        return this.rsm;
    }

    public static void main(String a[]){
        Database db = Database.getInstance();
        try{
            if(db.connectDB()){
                System.out.println("Connected to Database!");
                db.executeQuery("SELECT Password FROM Login WHERE Username='Admin'");
                while(db.getResults().next()){
                    System.out.println("result: " + db.getResults().getString(1));
                }
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
