package database;

import com.sun.org.apache.xalan.internal.xsltc.dom.SimpleResultTreeImpl;
import entities.Author;
import entities.Book;
import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MySQLManager {
    private Connection openConnection(){
        String driver = "com.mysql.jdbc.Driver";
        String connectionString = "jdbc:mysql://localhost:3306/lib?useUnicode=true&" +
                "characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&verifyServerCertificate=false";
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

    public HashMap<String, Integer> getGenres() throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            HashMap<String, Integer> genres = new HashMap<String, Integer>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT id, genre FROM genre");
            while (rs.next()){
                genres.put(rs.getString("genre"), Integer.valueOf(rs.getString("id")));
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

    public HashMap<String, Integer> getLanguages() throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            HashMap<String, Integer> language = new HashMap<String, Integer>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT id, language FROM language");
            while (rs.next()){
                language.put(rs.getString("language"), Integer.valueOf(rs.getString("id")));
            }
            rs.close();
            stm.close();
            return language;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
        return null;
    }

    // possibly useless method
    /*public ArrayList<Book> getBooks() throws SQLException {
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
    }*/
    private HashMap<String, Integer> getGenresByIsbn(String isbn) throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            HashMap<String, Integer> genres = new HashMap<String, Integer>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT genre.id AS ID, genre FROM genre INNER JOIN book_genre ON genre.id = book_genre.id_g " +
                    "WHERE book_genre.id_b = '"+ isbn +"'");
            while (rs.next()){
                genres.put(rs.getString("genre"), Integer.valueOf(rs.getString("ID")));
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
    private HashMap<String, Integer> getAuthorsByIsbn(String isbn) throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            HashMap<String, Integer> authors = new HashMap<String, Integer>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT author.id AS ID, CONCAT(LEFT(NAME,1),'. ',IF(fathers IS NULL,'',CONCAT(LEFT(fathers,1),'. ')), surname) AS FIO FROM author_book INNER JOIN author ON author_book.id_a = author.id WHERE id_b = '"+isbn+"'");
            while (rs.next()){
                authors.put(rs.getString("FIO"), Integer.valueOf(rs.getString("ID")));
            }
            rs.close();
            stm.close();
            return authors;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
        return null;
    }

    public HashMap<String, Integer> getPublishers() throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            HashMap<String, Integer> publishers = new HashMap<String, Integer>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT id, name FROM publisher");
            while (rs.next()){
                publishers.put(rs.getString("name"), Integer.valueOf(rs.getString("id")));
            }
            rs.close();
            stm.close();
            return publishers;
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
            final ResultSet rs = stm.executeQuery(SQL);
            while (rs.next()){
                Book b = new Book(rs.getString("isbn"),
                        rs.getString("name"),
                        rs.getString("year"),
                        new HashMap<String, Integer>(){{put(rs.getString("language"), Integer.valueOf(rs.getString("id_l")));}},new HashMap<String, Integer>(){{put(rs.getString("publisher"), Integer.valueOf(rs.getString("id_p")));}},rs.getString("location"),rs.getString("annotation"));
                b.setGenres(this.getGenresByIsbn(rs.getString("isbn")));
                b.setAuthors(this.getAuthorsByIsbn(rs.getString("isbn")));
                b.setAmounts(this.getAmountTotal(rs.getString("isbn")));
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

    public boolean isEmailRegistered(String email) throws SQLException {
        boolean flag = false;
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM user WHERE email = '"+email+"'");
            while (rs.next()){
                flag = true;
            }
            rs.close();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
        return flag;
    }
    public void addUser(User user, String pass) throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            int rs = stm.executeUpdate("INSERT INTO user(name,surname,fathers,email,pass) VALUES('"+
                    user.getName()+"', '"+user.getSurname()+"', '"+user.getFathers()+"', '"+user.getEmail()+"', '"+pass+"');");
            System.out.println(rs);
            conn.commit();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
    }

    public String getHashByEmail(String email) throws SQLException {
        Connection conn = null;
        try{
            String hash = null;
            conn = openConnection();
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT pass FROM user WHERE email = '"+email+"' ");
            if (rs.next()){
                hash = rs.getString("pass");
            }
            rs.close();
            stm.close();
            return hash;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
        return null;
    }

    public User getUserByEmail(String email) throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            User user = null;
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT id, name, surname, fathers, admin FROM user WHERE email = '"+email+"'");
            if (rs.next()){
                user = new User(Integer.parseInt(rs.getString("id")),rs.getString("name"),rs.getString("surname"),rs.getString("fathers"),Integer.parseInt(rs.getString("admin")));
            }
            rs.close();
            stm.close();
            return user;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
        return null;
    }

    public boolean isInBlackList(int id) throws SQLException {
        boolean flag = false;
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM blacklist WHERE id_u = '"+id+"'");
            while (rs.next()){
                flag = true;
            }
            rs.close();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
        return flag;
    }

    public boolean isExistLanguage(String language) throws SQLException {
        boolean flag = false;
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM language WHERE language = '"+language+"'");
            while (rs.next()){
                flag = true;
            }
            rs.close();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
        return flag;
    }

    public void addLanguage(String language) throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            int rs = stm.executeUpdate("INSERT INTO language (language) VALUES('"+ language +"');");
            System.out.println(rs);
            conn.commit();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
    }
    public boolean isExistPublisher(String publisher) throws SQLException {
        boolean flag = false;
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM publisher WHERE name = '"+publisher+"'");
            while (rs.next()){
                flag = true;
            }
            rs.close();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
        return flag;
    }

    public void addPublisher(String publisher) throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            int rs = stm.executeUpdate("INSERT INTO publisher (name) VALUES('"+ publisher +"');");
            System.out.println(rs);
            conn.commit();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
    }

    public HashMap<String, Integer> getAuthors() throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            HashMap<String, Integer> authors = new HashMap<String, Integer>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT author.id AS ID, CONCAT(LEFT(NAME,1),'. ',IF(fathers IS NULL,'',CONCAT(LEFT(fathers,1),'. ')), surname) AS FIO FROM author");
            while (rs.next()){
                authors.put(rs.getString("FIO"), Integer.valueOf(rs.getString("ID")));
            }
            rs.close();
            stm.close();
            return authors;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
        return null;
    }
    public boolean isExistAuthor(Author author) throws SQLException {
        Connection conn = null;
        boolean flag = false;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            ArrayList<Author> authors = new ArrayList<Author>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT id, name, surname, fathers  FROM author WHERE name = '"+author.getName()+"' AND surname = '"+author.getSurname()+"' AND fathers = '"+author.getFathers()+"'");
            while (rs.next()){
                flag = true;
            }
            rs.close();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
        return flag;
    }
    public void addAuthor(Author author) throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            if (author.getFathers() != null) {
                int rs = stm.executeUpdate("INSERT INTO author (name,surname,fathers) VALUES('" + author.getName() + "', '" + author.getSurname() + "', '" + author.getFathers() + "');");
            }else {
                int rs = stm.executeUpdate("INSERT INTO author (name,surname,fathers) VALUES('" + author.getName() + "', '" + author.getSurname() + "', NULL );");
            }
            conn.commit();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
    }
    public HashMap<String , Integer> getAuthorBySurname(String surname) throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            HashMap<String, Integer> authors = new HashMap<String, Integer>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT id , CONCAT(LEFT(NAME,1),'. ',IF(fathers IS NULL,'',CONCAT(LEFT(fathers,1),'. ')), surname) AS FIO FROM author WHERE surname LIKE '"+surname+"%'");
            while (rs.next()){
                authors.put(rs.getString("FIO"), Integer.valueOf(rs.getString("ID")));
            }
            rs.close();
            stm.close();
            return authors;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
        return null;
    }
    public void changeBookAuthors(String book_id,ArrayList<Integer> ids) throws SQLException {
        System.out.println(ids.size());
        removeOldAuthors(book_id);
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            for (Integer i : ids){
                int rs = stm.executeUpdate("INSERT INTO author_book (id_a,id_b) VALUES("+i+", '"+book_id+"')");
            }
            conn.commit();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
    }
    private void removeOldAuthors(String  book_id) throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            int rs = stm.executeUpdate("DELETE FROM author_book WHERE id_b = "+book_id);
            conn.commit();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
    }
    public void changeBookGenres(String book_id,ArrayList<Integer> ids) throws SQLException {
        removeOldGenres(book_id);
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            for (Integer i : ids){
                int rs = stm.executeUpdate("INSERT INTO book_genre (id_g,id_b) VALUES("+i+", '"+book_id+"')");
            }
            conn.commit();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
    }
    private void removeOldGenres(String  book_id) throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            int rs = stm.executeUpdate("DELETE FROM book_genre WHERE id_b = "+book_id);
            conn.commit();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
    }
    public HashMap<String , Integer> getGenreByName(String genre) throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            HashMap<String, Integer> genres = new HashMap<String, Integer>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT id, genre FROM genre WHERE genre LIKE '"+genre+"%'");
            while (rs.next()){
                genres.put(rs.getString("genre"), Integer.valueOf(rs.getString("id")));
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
    public boolean isExistGenre(String genre) throws SQLException {
        boolean flag = false;
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            HashMap<String, Integer> genres = new HashMap<String, Integer>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT id, genre FROM genre WHERE genre = '"+genre+"'");
            while (rs.next()){
                flag = true;
            }
            rs.close();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
        return flag;
    }
    public void addGenre(String genre) throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            int rs = stm.executeUpdate("INSERT INTO genre (genre) VALUES('"+genre+"')");
            System.out.println(rs);
            conn.commit();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
    }

    public void changeBook(Book book, String lastIsbn) throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            int rs = stm.executeUpdate("UPDATE book SET isbn = '"+book.getIsbn()+"', name = '"+book.getName()+"', " +
                    "year = "+book.getYear()+", id_publisher = "+book.getPublisher().get(book.getPublisher().keySet().iterator().next())+", " +
                    "id_language = "+book.getLanguage().get(book.getLanguage().keySet().iterator().next())+", annotation = '"+book.getAnnotation()+"', " +
                    "location = '"+book.getLocation()+"' "+"WHERE isbn = '"+lastIsbn+"'");
            conn.commit();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
    }

    public void addBool(Book book) throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            int rs = stm.executeUpdate("INSERT INTO book (isbn,name,year,id_publisher,id_language,annotation,location)" +
                    "VALUES ('"+book.getIsbn()+"','"+book.getName()+"',"+book.getYear()+", "+book.getPublisher().get(book.getPublisher().keySet().iterator().next())+", "+
                    book.getLanguage().get(book.getLanguage().keySet().iterator().next())+", '"+ book.getAnnotation() +"', '"+ book.getLocation() +"')");
            conn.commit();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
    }
    public HashMap<String , Integer> getAmountTotal(String isbn) throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            HashMap<String, Integer> amounts = new HashMap<String, Integer>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT current_amount, amount FROM number_of_books WHERE isbn = '"+isbn+"'");
            while (rs.next()){
                amounts.put("current_amount", Integer.valueOf(rs.getString("current_amount")));
                amounts.put("amount", Integer.valueOf(rs.getString("amount")));
            }
            rs.close();
            stm.close();
            return amounts;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
        return null;
    }

    public void addAmountTotal(String isbn,HashMap<String , Integer> amounts) throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            int rs = stm.executeUpdate("INSERT INTO number_of_books (isbn,amount,current_amount) VALUES('"+isbn+"'" +
                    ", "+amounts.get("amount")+", "+amounts.get("current_amount")+")");
            conn.commit();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
    }

    public void changeAmountTotal(String isbn,HashMap<String , Integer> amounts) throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            int rs = stm.executeUpdate("UPDATE number_of_books SET" +
                    " amount = '" +amounts.get("amount")+"', current_amount = '"+amounts.get("current_amount")+"' " +
                    "WHERE isbn = '"+isbn+"'");
            conn.commit();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
    }
    public void changeUser(User user) throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            if (user.getFathers() != null) {
                int rs = stm.executeUpdate("UPDATE user SET name = '" + user.getName() + "', surname = '" + user.getSurname()
                        + "', fathers = '" + user.getFathers() + "', email = '" + user.getEmail() + "' WHERE id = " + user.getId());
            }else{
                int rs = stm.executeUpdate("UPDATE user SET name = '" + user.getName() + "', surname = '" + user.getSurname()
                        + "', fathers = NULL, email = '" + user.getEmail() + "' WHERE id = " + user.getId());
            }
                conn.commit();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
    }
    public void addBookUser(int id,String isbn) throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            int rs = stm.executeUpdate("INSERT INTO book_user (id_b,id_u,date_start,date_finish) VALUES('"+isbn+"', " +
                    id+", NOW(), DATE_ADD(NOW(),INTERVAL 10 DAY))");
            conn.commit();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
    }
    public void removeBookUser(int id, String isbn) throws SQLException {
        Connection conn = null;
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            int rs = stm.executeUpdate("DELETE FROM book_user WHERE id_b = '"+isbn+"' AND id_u = "+id);
            conn.commit();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
    }
    public ArrayList<User> getUsersBy(String SQL) throws SQLException {
        Connection conn = null;
        ArrayList<User>users = new ArrayList<User>();
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(SQL);
            while(rs.next()){
                users.add(new User(Integer.parseInt(rs.getString("id")),rs.getString("name"),
                        rs.getString("surname"),rs.getString("fathers"),
                        rs.getString("email"),Integer.parseInt(rs.getString("admin"))));
            }
            conn.commit();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
        return users;
    }
    public HashMap<User,String> getBlackList() throws SQLException {
        Connection conn = null;
        HashMap<User, String >users = new HashMap<User, String>();
        try{
            conn = openConnection();
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT id_u,date_add,name,surname,fathers,email FROM blacklist INNER JOIN user ON blacklist.id_u = user.id");
            while(rs.next()){
                User tmp = new User(rs.getString("name"),rs.getString("surname"),rs.getString("fathers"),rs.getString("email"));
                users.put(tmp,rs.getString("date_add"));
            }
            conn.commit();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn.close();
        }
        return users;
    }
}
