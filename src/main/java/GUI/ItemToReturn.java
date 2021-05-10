package GUI;

import entities.Book;

public class ItemToReturn extends BookItem{
    public ItemToReturn(Book book) {
        super(book);
        button1.setText("Return");
        // TODO: 10.05.2021 Interact with db 
    }
}
