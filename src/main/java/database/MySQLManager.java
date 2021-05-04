package database;

import entities.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLManager {
    private Connection openConnection(){
        String driver = "com.mysql.jdbc.Driver";
        String connectionString = "jdbc:mysql://localhost:3306/lib?useUnicode=true&serverTimezone=UTC&useSSL=false&verifyServerCertificate=false";
        String username = "libr";
        String password = "libr";
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

    public ArrayList<Book> getBooks() throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            ArrayList<Book> books = new ArrayList<Book>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT isbn, book.name AS name, year, publisher.name AS publisher, language, annotation, location FROM (book INNER JOIN publisher ON book.id_publisher = publisher.id) INNER JOIN LANGUAGE ON book.id_language;");
            while (rs.next()){
                books.add(new Book(rs.getString("isbn"),rs.getString("name"), rs.getString("year"),rs.getString("language"),rs.getString("publisher"),rs.getString("location"),rs.getString("annotation")));
            }
            rs.close();
            stm.close();
            return books;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
        return null;
    }

}
