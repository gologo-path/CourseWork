import GUI.SearchForm;
import GUI.logIn;

import javax.swing.*;

public class test {
    static boolean admin;
    static boolean inBlackList;
    static int id;
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        SearchForm searchBooks = new SearchForm();
        frame.add(searchBooks.$$$getRootComponent$$$());
        frame.setVisible(true);
        frame.setSize(500,300);
        //frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JDialog dialog = new logIn(frame);
        dialog.setVisible(true);
    }
}
