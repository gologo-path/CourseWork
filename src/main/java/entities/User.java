package entities;

import javax.jws.soap.SOAPBinding;

public class User {
    private String name;
    private String surname;
    private String fathers;
    private int id;
    private boolean inBlackList;
    private boolean isAdmin;

    public User(int id, String name, String surname, String fathers, boolean isAdmin) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.fathers = fathers;
        this.isAdmin = isAdmin;
    }

    public User(int id, String name, String surname, boolean isAdmin) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.fathers = " ";
        this.isAdmin = isAdmin;
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
}
