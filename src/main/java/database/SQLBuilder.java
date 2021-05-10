package database;

public class SQLBuilder {
    private String SQL = "";
    private String by;
    private String language;
    private String genre;
    private String publisher;
    private String search;

    public SQLBuilder(String by, String language, String genre, String publisher, String search){
        this.by = by;
        this.language = language;
        this.genre = genre;
        this.publisher = publisher;
        this.search = search;
    }


    public String forBookCollection(){
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
        return SQL;
    }

    public String forMyCollection(int id){
        SQL += "SELECT isbn, book.name AS NAME, YEAR, publisher.name AS publisher, LANGUAGE, annotation, location FROM ((book INNER JOIN publisher ON book.id_publisher = publisher.id) INNER JOIN LANGUAGE ON book.id_language = language.id) INNER JOIN book_user ON book_user.id_b = book.isbn WHERE book_user.id_u = "+id+" ";
        if (by.equals("By name")){
            if (!search.equals("")){
                search+="%";
            }
            SQL += "AND book.name LIKE '%"+search+"' ";
        }else if (by.equals("By author")){
            if (!search.equals("")){
                search+="%";
            }
            SQL += "AND book.isbn IN (SELECT id_b FROM author_book INNER JOIN author ON author_book.id_a = author.id WHERE author.surname LIKE '%"+search+"') ";
        }else if (by.equals("By ISBN")){
            SQL += "AND book.isbn = '"+search+"' ";
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
        return SQL;
    }



}