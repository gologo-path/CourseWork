package GUI;

import entities.Book;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemToChange extends BookItem{
    ItemToChange th;
    public ItemToChange(Book book) {
        super(book);
        th = this;
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeBook changeBook = new ChangeBook(ItemToChange.super.getBook());
                Container component = th.$$$getRootComponent$$$().getParent().getParent().getParent().getParent().getParent();
                component.removeAll();
                component.add(changeBook.$$$getRootComponent$$$());
                component.setVisible(false);
                component.setVisible(true);
            }
        });
        button1.setText("Change");
    }
}
