package entities;

import java.io.Serializable;
import java.util.HashMap;

public class Book implements Serializable {
    protected String isbn;
    protected String name;
    protected String year;
    protected HashMap <String, Integer> language;
    protected HashMap <String, Integer> publisher;
    protected String location;
    protected String annotation;
    protected HashMap <String, Integer> genres;
    protected HashMap <String, Integer> authors;

    public Book(String isbn, String name, String year, HashMap <String, Integer> language, HashMap <String, Integer> publisher, String location, String annotation){
        this.isbn = isbn;
        this.name = name;
        this.year = year;
        this.language = language;
        this.publisher = publisher;
        this.location = location;
        this.annotation = annotation;
    }

    public String getIsbn() {
        return isbn;
    }
    public String getName() {
        return name;
    }
    public String getYear() {
        return year;
    }
    public HashMap <String, Integer> getLanguage() {
        return language;
    }
    public HashMap <String, Integer> getPublisher() {
        return publisher;
    }
    public String getAnnotation() {
        return annotation;
    }
    public void setGenres(HashMap <String, Integer> genres) {
        this.genres = genres;
    }
    public HashMap <String, Integer> getGenres() {
        return genres;
    }
    public void setAuthors(HashMap <String, Integer> authors) {
        this.authors = authors;
    }
    public HashMap <String, Integer> getAuthors() {
        return authors;
    }
}