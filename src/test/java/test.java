import GUI.LogIn;
import GUI.MainMenu;
import GUI.SearchForm;
import entities.User;

import javax.swing.*;

public class test {
    static User user;
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        MainMenu searchBooks = new MainMenu();
        LogIn logIn = new LogIn(frame);
        frame.add(searchBooks.$$$getRootComponent$$$());
        logIn.setSize(200,100);
        frame.setSize(500,300);
        //frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        logIn.setVisible(true);
        System.out.println(logIn.getUser());
    }
}
