package GUI;

import entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private User user;
    private JPanel mainPanel;
    public MainFrame(){
        setLayout(new BorderLayout());
        final MainMenu menu = new MainMenu();
        final LogIn logIn = new LogIn(this);
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(menu.$$$getRootComponent$$$());
        add(mainPanel, BorderLayout.CENTER);
        logIn.setSize(200,100);
        setSize(500,300);
        //frame.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        logIn.setVisible(true);
        user = logIn.getUser();
        final Label logInAs = new Label("Signed in as : "+user.getName()+" "+user.getSurname());
        add(logInAs,BorderLayout.NORTH);
        mainPanel.updateUI();
        final JButton back = new JButton("Back to main menu");
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(menu.$$$getRootComponent$$$());
                mainPanel.updateUI();
                back.setVisible(false);
            }
        });
        add(back,BorderLayout.SOUTH);
        back.setVisible(false);
       if(user.isAdmin()){
            menu.adminStaffButton.setVisible(true);
       }else if (user.isInBlackList()){
           menu.adminStaffButton.setVisible(false);
           menu.changeProfileInfoButton.setEnabled(false);
           menu.findBookButton.setEnabled(false);
           new BlackListDialog(this).setVisible(true);
           // TODO: 10.05.2021 normal size of window
       }
       menu.changeProfileInfoButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
                ChangeInfo changeInfo = new ChangeInfo(user);
                mainPanel.removeAll();
                mainPanel.add(changeInfo.$$$getRootComponent$$$());
                back.setVisible(true);
                mainPanel.updateUI();
           }
       });
       menu.findBookButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               SearchForm searchForm = new SearchForm();
               mainPanel.removeAll();
               mainPanel.add(searchForm.$$$getRootComponent$$$());
               back.setVisible(true);
               mainPanel.updateUI();
           }
       });
       menu.myBooksButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               MyBooks myBooks = new MyBooks(user.getId());
               mainPanel.removeAll();
               mainPanel.add(myBooks.$$$getRootComponent$$$());
               back.setVisible(true);
               mainPanel.updateUI();
           }
       });
       menu.logOutButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               menu.adminStaffButton.setVisible(false);
               logInAs.setText("");
               logIn.clearFields();
               logIn.setVisible(true);
               user = logIn.getUser();
               logInAs.setText("Signed in as : "+user.getName()+" "+user.getSurname());
           }
       });
       menu.adminStaffButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               AdminMenu adminMenu = new AdminMenu();
               mainPanel.removeAll();
               mainPanel.add(adminMenu.$$$getRootComponent$$$());
               back.setVisible(true);
               mainPanel.updateUI();
           }
       });
    }
}
