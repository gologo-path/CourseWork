import javax.swing.*;

public class test {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        SearchBooks searchBooks = new SearchBooks();
        //frame.add(searchBooks);
        frame.setVisible(true);
        frame.setSize(500,300);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
