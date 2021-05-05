package database;

public class SQLBuilder {
    private String SQL = "";

    public SQLBuilder(String by, String language, String genre, String search){
        SQL += "SELECT isbn, book.name AS name, year, publisher.name AS publisher, language, annotation, location FROM (book INNER JOIN publisher ON book.id_publisher = publisher.id) INNER JOIN LANGUAGE ON book.id_language ";
        if (by.equals("By name")){
            SQL += "WHERE book.name LIKE '%"+search+"'";
        }else if (by.equals("By author")){

        }else if (by.equals("By publisher")){
            SQL += "WHERE publisher.name LIKE '%"+search+"'";
        }else if (by.equals("By ISBN")){
            SQL += "WHERE book.isbn = "+search;
        }
    }

    public String getSQL() {
        return SQL;
    }



}