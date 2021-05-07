package database;

import entities.Book;

import java.sql.*;
import java.util.ArrayList;

public class MySQLManager {
    private Connection openConnection(){
        String driver = "com.mysql.jdbc.Driver";
        String connectionString = "jdbc:mysql://localhost:3306/lib?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&verifyServerCertificate=false";
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
                Book b = new Book(rs.getString("isbn"),rs.getString("name"), rs.getString("year"),rs.getString("language"),rs.getString("publisher"),rs.getString("location"),rs.getString("annotation"));
                b.setGenres(this.getGenresByIsbn(rs.getString("isbn")));
                b.setAuthors(this.getAuthorsByIsbn(rs.getString("isbn")));
                books.add(b);
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
    private String getGenresByIsbn(String isbn) throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            String genres = "";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT genre FROM genre INNER JOIN book_genre ON genre.id = book_genre.id_g " +
                    "WHERE book_genre.id_b = '"+ isbn +"'");
            while (rs.next()){
                genres += rs.getString("genre") + " ";
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
    private String getAuthorsByIsbn(String isbn) throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            String genres = "";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT CONCAT(LEFT(NAME,1),'. ',IF(fathers IS NULL,'',CONCAT(LEFT(fathers,1),'. ')), surname) AS FIO FROM author_book INNER JOIN author ON author_book.id_a = author.id WHERE id_b = '"+isbn+"'");
            while (rs.next()){
                genres += rs.getString("FIO") + " ";
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

    public ArrayList<String> getPublishers() throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            ArrayList<String> genres = new ArrayList<String>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT name FROM publisher");
            while (rs.next()){
                genres.add(rs.getString("name"));
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

    public ArrayList<Book> getByRequest(String SQL) throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            ArrayList<Book> books = new ArrayList<Book>();
            Statement stm = conn.createStatement();
            System.out.println(SQL);
            ResultSet rs = stm.executeQuery(SQL);
            while (rs.next()){
                Book b = new Book(rs.getString("isbn"),rs.getString("name"), rs.getString("year"),rs.getString("language"),rs.getString("publisher"),rs.getString("location"),rs.getString("annotation"));
                b.setGenres(this.getGenresByIsbn(rs.getString("isbn")));
                b.setAuthors(this.getAuthorsByIsbn(rs.getString("isbn")));
                books.add(b);
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
