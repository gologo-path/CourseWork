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
    protected HashMap <String, Integer> amounts;

    public Book(String isbn, String name, String year, HashMap <String, Integer> language, HashMap <String, Integer> publisher, String location, String annotation){
        this.isbn = isbn;
        this.name = name;
        this.year = year;
        this.language = language;
        this.publisher = publisher;
        this.location = location;
        this.annotation = annotation;
    }

    public Book() {
        language = new HashMap<String, Integer>();
        publisher = new HashMap<String, Integer>();
        genres = new HashMap<String, Integer>();
        authors = new HashMap<String, Integer>();
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
    public String getLocation() {
        return location;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public void setLanguage(HashMap<String, Integer> language) {
        this.language = language;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPublisher(HashMap<String, Integer> publisher) {
        this.publisher = publisher;
    }

    public HashMap<String, Integer> getAmounts() {
        return amounts;
    }

    public void setAmounts(HashMap<String, Integer> amounts) {
        this.amounts = amounts;
    }
}