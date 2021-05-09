package GUI;

import entities.User;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private User user;
    private JPanel mainPanel;
    public MainFrame(){
        setLayout(new BorderLayout());
        MainMenu menu = new MainMenu();
        LogIn logIn = new LogIn(this);
        mainPanel = (JPanel) menu.$$$getRootComponent$$$();
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
       if(user.isAdmin()){
            menu.adminStaffButton.setVisible(true);
       }else if (user.isInBlackList()){
           menu.adminStaffButton.setVisible(false);
           menu.changeProfileInfoButton.setEnabled(false);
           menu.findBookButton.setEnabled(false);

       }
    }
}
