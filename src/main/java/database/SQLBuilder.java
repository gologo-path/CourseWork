package database;

public class SQLBuilder {
    private String SQL = "";

    public SQLBuilder(String by, String language, String genre, String publisher, String search){

        SQL += "SELECT isbn, book.name AS name, year, publisher.name AS publisher, language, annotation, location FROM (book INNER JOIN publisher ON book.id_publisher = publisher.id) INNER JOIN language ON book.id_language = language.id ";
        if (by.equals("By name")){
            if (!search.equals("")){
                search+="%";
            }
            SQL += "WHERE book.name LIKE '%"+search+"' ";
        }else if (by.equals("By author")){
            if (!search.equals("")){
                search+="%";
            }
            SQL += "AND book.isbn IN (SELECT id_b FROM author_book INNER JOIN author ON author_book.id_a = author.id WHERE author.surname LIKE '%"+search+"') ";
        }else if (by.equals("By ISBN")){
            SQL += "WHERE book.isbn = '"+search+"' ";
        }
        if(!genre.equals("Any genre")){
            SQL += "AND book.isbn IN (SELECT id_b FROM book_genre INNER JOIN genre ON book_genre.id_g = genre.id WHERE genre.genre = '"+genre+"') ";
        }
        if(!language.equals("Any language")){
            SQL+= "AND language = '"+language+"' ";
        }
        if(!publisher.equals("Any publisher")){
            SQL += "AND publisher.name = '"+publisher+"' ";
        }
    }

    public String getSQL() {
        System.out.println(SQL);
        return SQL;
    }



}