package GUI;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import database.MySQLManager;
import entities.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class AddAuthor {
    private JPanel panel1;
    private JButton saveButton;
    private JButton addNewButton;
    private JButton cancelButton;
    private JTextField textField1;
    private JButton searchButton;
    private JPanel items;
    private JButton onlySelectedButton;
    private MySQLManager manager;
    private ArrayList<String> visible;
    private ArrayList<String> checked;
    private HashMap<String, Integer> allAuthors;

    public AddAuthor(final Book book, final Container container, final ArrayList<Integer> ids, final ICommonGuiClass parent) {
        items.setLayout(new GridLayout(0, 1, 0, 20));
        checked = new ArrayList<String>();
        visible = new ArrayList<String>();
        manager = new MySQLManager();
        checked.addAll(book.getAuthors().keySet());
        visible.addAll(book.getAuthors().keySet());
        printVisible();

        addNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddNewAuthor addNewAuthor = new AddNewAuthor();
                addNewAuthor.setSize(500,300);
                addNewAuthor.cleanFields();
                addNewAuthor.setVisible(true);
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ids.clear();
                try {
                    allAuthors = manager.getAuthors();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                for (String selected : checked) {
                    ids.add(allAuthors.get(selected));
                }
                System.out.println(ids.size());

            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                container.removeAll();
                container.add(parent.$$$getRootComponent$$$());
                container.setVisible(false);
                container.setVisible(true);
            }
        });
        onlySelectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visible.clear();
                visible.addAll(checked);
                printVisible();
                container.setVisible(false);
                container.setVisible(true);
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visible.clear();
                try {
                    visible.addAll(manager.getAuthorBySurname(textField1.getText()).keySet());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                printVisible();
                container.setVisible(false);
                container.setVisible(true);
            }
        });
    }

    private void printVisible() {
        items.removeAll();
        for (String st : visible) {
            Checkbox ch = new Checkbox(st);
            ch.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    switch (e.getStateChange()) {
                        case ItemEvent.SELECTED:
                            checked.add((String) e.getItem());
                            break;
                        case ItemEvent.DESELECTED:
                            checked.remove(e.getItem());
                            break;
                    }
                }
            });
            if (checked.contains(st)) {
                ch.setState(true);
            }
            items.add(ch);
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
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, BorderLayout.SOUTH);
        saveButton = new JButton();
        saveButton.setText("Save");
        panel2.add(saveButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addNewButton = new JButton();
        addNewButton.setText("Add new");
        panel2.add(addNewButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelButton = new JButton();
        cancelButton.setText("Back");
        panel2.add(cancelButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel2.add(spacer1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, BorderLayout.NORTH);
        textField1 = new JTextField();
        panel3.add(textField1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        searchButton = new JButton();
        searchButton.setText("Search");
        panel3.add(searchButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        onlySelectedButton = new JButton();
        onlySelectedButton.setText("Only selected");
        panel3.add(onlySelectedButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, BorderLayout.CENTER);
        items = new JPanel();
        items.setLayout(new GridBagLayout());
        scrollPane1.setViewportView(items);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
