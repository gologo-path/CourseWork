package entities;

import javax.jws.soap.SOAPBinding;

public class User {
    private String name;
    private String surname;
    private String fathers;
    private String email;
    private int id;
    private boolean inBlackList;
    private boolean isAdmin;

    public User(int id, String name, String surname, String fathers, int isAdmin) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.fathers = fathers;
        this.isAdmin = isAdmin != 0;
    }

    public User(int id, String name, String surname, int isAdmin) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.fathers = " ";
        this.isAdmin = isAdmin != 0;
    }

    public User (String name, String surname, String fathers, String email){
        this.name = name;
        this.surname = surname;
        this.fathers = fathers;
        this.email = email;
    }

    public User(int id, String name, String surname, String fathers, String email, int isAdmin) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.fathers = fathers;
        this.isAdmin = isAdmin != 0;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getFathers() {
        return fathers;
    }

    public int getId() {
        return id;
    }

    public boolean isInBlackList() {
        return inBlackList;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setInBlackList(boolean inBlackList) {
        this.inBlackList = inBlackList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setFathers(String fathers) {
        this.fathers = fathers;
    }
}
