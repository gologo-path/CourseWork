package entities;

import java.io.Serializable;

public class Book implements Serializable {
    protected String isbn;
    protected String name;
    protected String year;
    protected String language;
    protected String publisher;
    protected String location;
    protected String annotation;
    protected String genres;
    protected String authors;

    public Book(String isbn, String name, String year, String language, String publisher, String location, String annotation){
        this.isbn = isbn;
        this.name = name;
        this.year = year;
        this.language = language;
        this.publisher = publisher;
        this.location = location;
        this.annotation = annotation;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisher() {
        return publisher;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getGenres() {
        return genres;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getAuthors() {
        return authors;
    }
}