package GUI;

import database.MySQLManager;
import entities.Book;
import entities.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ItemToAdd extends BookItem{
    public ItemToAdd(final Book book, final User user) {
        super(book);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MySQLManager manager = new MySQLManager();
                try {
                    manager.addBookUser(user.getId(),book.getIsbn());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        button1.setText("Take");
        if (book.getAmounts().get("current_amount") == 0){
            button1.setEnabled(false);
        }
    }
}
