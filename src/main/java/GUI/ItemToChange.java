package GUI;

import entities.Book;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemToChange extends BookItem{
    private ItemToChange th;
    private Container container;
    public ItemToChange(Book book) {
        super(book);
        th = this;
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                container = th.$$$getRootComponent$$$().getParent().getParent().getParent().getParent().getParent();
                ChangeBook changeBook = new ChangeBook(ItemToChange.super.getBook(),container);
                container.removeAll();
                container.add(changeBook.$$$getRootComponent$$$());
                container.setVisible(false);
                container.setVisible(true);
            }
        });
        button1.setText("Change");
    }

    public Container getContainer() {
        return container;
    }
}
