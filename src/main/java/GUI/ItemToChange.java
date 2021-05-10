package GUI;

import entities.Book;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemToChange extends BookItem{
    public ItemToChange(Book book) {
        super(book);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeBook changeBook = new ChangeBook(ItemToChange.super.getBook());
            }
        });
    }
}
