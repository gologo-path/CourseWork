package GUI;

import com.mysql.cj.protocol.a.MysqlBinaryValueDecoder;
import database.MySQLManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddAuthor extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextField textField1;
    private JButton cancelButton;
    private MySQLManager manager;

    public AddAuthor() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
    }

    protected void onOK() {
        manager = new MySQLManager();
        try {
            if (manager.isExistLanguage(textField1.getText())){
                JDialog dialog = new JDialog(this);
                JPanel panel = new JPanel(new BorderLayout());
                dialog.setModal(true);
                panel.add(new Label("Language already exist!"),BorderLayout.CENTER);
                JButton button = new JButton("OK");
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });
                panel.add(button,BorderLayout.SOUTH);
                dialog.add(panel);
                dialog.setSize(300,200);
                dialog.setVisible(true);
            }else{
                if (!textField1.getText().isEmpty()) {
                    manager.addLanguage(textField1.getText());
                    dispose();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private void onCancel(){

        dispose();
    }
    public void cleanFields(){
        textField1.setText("");
    }
}
