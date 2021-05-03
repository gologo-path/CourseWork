import GUI.SearchForm;

import javax.swing.*;

public class test {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        SearchForm searchBooks = new SearchForm();
        frame.add(searchBooks.$$$getRootComponent$$$());
        frame.setVisible(true);
        frame.setSize(500,300);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
