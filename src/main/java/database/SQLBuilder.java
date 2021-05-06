package database;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;

public class SQLBuilder {
    private String SQL = "";

    public SQLBuilder(String by, String language, String genre, String search){

        SQL += "SELECT isbn, book.name AS name, year, publisher.name AS publisher, language, annotation, location FROM (book INNER JOIN publisher ON book.id_publisher = publisher.id) INNER JOIN language ON book.id_language ";
        if (by.equals("By name")){
            if (!search.equals("")){
                search+="%";
            }
            SQL += "WHERE book.name LIKE '%"+search+"' ";
        }else if (by.equals("By author")){
            SQL = "SELECT book.isbn, book.name AS NAME, YEAR, publisher.name AS publisher, LANGUAGE, annotation, location FROM ((book INNER JOIN \n" +
                    "(SELECT id_a,id_b FROM author_book INNER JOIN author ON author.id = author_book.id_a WHERE author.surname LIKE '%"+search+"%') AS auth\n" +
                    "ON book.isbn = auth.id_b) INNER JOIN publisher ON book.id_publisher = publisher.id) INNER JOIN LANGUAGE ON book.id_language ";
        }else if (by.equals("By publisher")){
            if (!search.equals("")){
                search+="%";
            }
            SQL += "WHERE publisher.name LIKE '%"+search+"' ";
        }else if (by.equals("By ISBN")){
            SQL += "WHERE book.isbn = '"+search+"' ";
        }
        if(!language.equals("Any language")){
            SQL+= "AND language = '"+language+"' ";
        }
    }

    public String getSQL() {
        System.out.println(SQL);
        return SQL;
    }



}