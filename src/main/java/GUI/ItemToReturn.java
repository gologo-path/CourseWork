package GUI;

import database.MySQLManager;
import entities.Book;
import entities.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ItemToReturn extends BookItem{
    public ItemToReturn(final Book book, final User user) {
        super(book);
        button1.setText("Return");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MySQLManager manager = new MySQLManager();
                try {
                    manager.removeBookUser(user.getId(),book.getIsbn());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}
