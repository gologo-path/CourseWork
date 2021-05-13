package GUI;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import database.MySQLManager;
import entities.Book;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ChangeBook implements ICommonGuiClass {
    private JButton submitChangesButton;
    private JTextField name;
    private JTextField isbn;
    private JTextField year;
    private JComboBox publisher;
    private JComboBox language;
    private JButton addNewLanguage;
    private JButton addNewPublisher;
    private JButton editAuthorsListButton;
    private JButton editGenresListButton;
    private JTextPane annotation;
    private JPanel root;
    private JTextField location;
    private JSpinner amount;
    private JSpinner total;
    private MySQLManager manager;
    private Book book;
    private ArrayList<Integer> ids_a;
    private ArrayList<Integer> ids_g;
    private String lastIsbn;
    private AddAuthor addAuthor;
    private AddGenres addGenres;
    private SpinnerNumberModel amountModel;
    private SpinnerNumberModel totalModel;

    public ChangeBook(final Book book, final Container container) {
        this.book = book;
        lastIsbn = book.getIsbn();
        ids_a = new ArrayList<Integer>();
        ids_g = new ArrayList<Integer>();
        final AddLanguage addLanguage = new AddLanguage();
        final AddPublisher addPublisher = new AddPublisher();
        addAuthor = new AddAuthor(book, container, ids_a, this);
        addGenres = new AddGenres(book, container, ids_g, this);
        manager = new MySQLManager();
        editAuthorsListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                container.removeAll();
                container.add(addAuthor.$$$getRootComponent$$$());
                container.setVisible(false);
                container.setVisible(true);
            }
        });
        addNewLanguage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addLanguage.cleanFields();
                addLanguage.setVisible(true);
                updateLanguages();
            }
        });
        addNewPublisher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPublisher.cleanFields();
                addPublisher.setVisible(true);
                updatePublishers();
            }
        });
        editGenresListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                container.removeAll();
                container.add(addGenres.$$$getRootComponent$$$());
                container.setVisible(false);
                container.setVisible(true);
            }
        });
        submitChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                book.setIsbn(isbn.getText());
                book.setAnnotation(annotation.getText());
                book.setLanguage(new HashMap<String, Integer>() {{
                    try {
                        put((String) language.getSelectedItem(), manager.getLanguages().get(language.getSelectedItem()));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }});
                book.setName(name.getText());
                book.setYear(year.getText());
                book.setPublisher(new HashMap<String, Integer>() {{
                    try {
                        put((String) publisher.getSelectedItem(), manager.getPublishers().get(publisher.getSelectedItem()));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }});
                book.setLocation(location.getText());
                try {
                    manager.changeBook(book, lastIsbn);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    if (ids_g.size() > 0) {
                        manager.changeBookGenres(book.getIsbn(), ids_g);
                    }
                    if (ids_a.size() > 0) {
                        manager.changeBookAuthors(book.getIsbn(), ids_a);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    manager.changeAmountTotal(book.getIsbn(), new HashMap<String, Integer>() {{
                        put("current_amount", (Integer) amount.getValue());
                        put("amount", (Integer) total.getValue());
                    }});
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        name.setText(book.getName());
        isbn.setText(book.getIsbn());
        year.setText(book.getYear());
        location.setText(book.getLocation());

        HashMap<String, Integer> amounts = new HashMap<String, Integer>();
        try {
            amounts.putAll(manager.getAmountTotal(book.getIsbn()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        amountModel = new SpinnerNumberModel(amounts.get("current_amount").intValue(), 0, amounts.get("amount").intValue(), 1);
        amount.setModel(amountModel);
        totalModel = new SpinnerNumberModel(amounts.get("amount").intValue(), 1, Integer.MAX_VALUE, 1);
        total.setModel(totalModel);

        total.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                amountModel.setMaximum((Integer) total.getValue());
            }
        });

        // TODO: 10.05.2021 Make something with date. I don't now what, but make.
        annotation.setText(book.getAnnotation());
        manager = new MySQLManager();
        updateLanguages();
        updatePublishers();
    }

    private void updateLanguages() {
        language.removeAllItems();
        try {
            for (String st : manager.getLanguages().keySet()) {
                language.addItem(st);
                if (st.equals(book.getLanguage().keySet().iterator().next())) {
                    language.setSelectedItem(st);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void updatePublishers() {
        publisher.removeAllItems();
        try {
            for (String st : manager.getPublishers().keySet()) {
                publisher.addItem(st);
                if (st.equals(book.getPublisher().keySet().iterator().next())) {
                    publisher.setSelectedItem(st);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        root = new JPanel();
        root.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(10, 3, new Insets(0, 0, 0, 0), -1, -1));
        root.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Name");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Year");
        panel1.add(label2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Publisher");
        panel1.add(label3, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Genre");
        panel1.add(label4, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Annotation");
        panel1.add(label5, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("ISBN");
        panel1.add(label6, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Language");
        panel1.add(label7, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Author");
        panel1.add(label8, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        name = new JTextField();
        panel1.add(name, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        isbn = new JTextField();
        panel1.add(isbn, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        year = new JTextField();
        panel1.add(year, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        publisher = new JComboBox();
        panel1.add(publisher, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        language = new JComboBox();
        panel1.add(language, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addNewLanguage = new JButton();
        addNewLanguage.setText("Add new ");
        panel1.add(addNewLanguage, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addNewPublisher = new JButton();
        addNewPublisher.setText("Add new");
        panel1.add(addNewPublisher, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editAuthorsListButton = new JButton();
        editAuthorsListButton.setText("Edit authors list");
        panel1.add(editAuthorsListButton, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editGenresListButton = new JButton();
        editGenresListButton.setText("Edit genres list");
        panel1.add(editGenresListButton, new GridConstraints(6, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        annotation = new JTextPane();
        panel1.add(annotation, new GridConstraints(9, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Location");
        panel1.add(label9, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        location = new JTextField();
        panel1.add(location, new GridConstraints(7, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("amount/total amount");
        panel1.add(label10, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        amount = new JSpinner();
        panel1.add(amount, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        total = new JSpinner();
        panel1.add(total, new GridConstraints(8, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        submitChangesButton = new JButton();
        submitChangesButton.setText("Submit changes");
        root.add(submitChangesButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return root;
    }

}
