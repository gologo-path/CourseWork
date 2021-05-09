import GUI.LogIn;
import GUI.SearchForm;
import entities.User;

import javax.swing.*;

public class test {
    static User user;
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        SearchForm searchBooks = new SearchForm();
        LogIn logIn = new LogIn(frame);
        logIn.setVisible(true);
        frame.add(searchBooks.$$$getRootComponent$$$());
        frame.setVisible(true);
        frame.setSize(500,300);
        //frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}
