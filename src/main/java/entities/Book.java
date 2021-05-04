package entities;

import java.io.Serializable;

public class Book implements Serializable {
    protected char id_b;
    protected String name;
    protected int year;
    protected String language;
    protected String publisher;
    protected String location;
    protected String annotation;

    Book(char id_b, String name, int year, String language,String publisher,String location,String annotation){
        this.id_b = id_b;
        this.name = name;
        this.year = year;
        this.language = language;
        this.publisher = publisher;
        this.location = location;
        this.annotation = annotation;
    }

    public void setId_b(char id_b) {
        this.id_b = id_b;
    }

    public char getId_b() {
        return id_b;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
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
}