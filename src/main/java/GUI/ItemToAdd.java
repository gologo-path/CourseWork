package GUI;

import entities.Book;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemToAdd extends BookItem{
    public ItemToAdd(Book book) {
        super(book);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: 10.05.2021 interact with db
            }
        });
        button1.setText("Take");
    }
}
