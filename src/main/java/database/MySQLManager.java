package database;

import entities.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLManager {
    private Connection openConnection(){
        String driver = "org.mysql.Driver";
        String connectionString = "jdbc:mysql://localhost:3306/lib";
        String username = "";
        String password = "";
        try{
            Class.forName(driver);
            return DriverManager.getConnection(connectionString,username,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getGenres() throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            ArrayList<String> genres = new ArrayList<String>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT genre FROM genre");
            while (rs.next()){
                genres.add(rs.getString("genre"));
            }
            rs.close();
            stm.close();
            return genres;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
        return null;
    }

    public ArrayList<String> getLanguages() throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            ArrayList<String> genres = new ArrayList<String>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT language FROM language");
            while (rs.next()){
                genres.add(rs.getString("language"));
            }
            rs.close();
            stm.close();
            return genres;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
        return null;
    }

}
