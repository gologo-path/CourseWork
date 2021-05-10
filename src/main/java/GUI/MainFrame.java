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
        LogIn logIn = new LogIn(this);
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
        add(new Label("Signed in as : "+user.getName()+" "+user.getSurname()),BorderLayout.NORTH);
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

    }
}
