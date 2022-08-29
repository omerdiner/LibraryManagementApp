package GUI;

import Helper.Config;
import Helper.Helper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private JPanel wrapper;
    private JTextField field_username;
    private JPasswordField field_password;
    private JButton button_login;
    private JPanel panel_top;
    private JPanel panel_bottom;

    public LoginGUI() {
        add(wrapper);
        setSize(600, 800);
        setLocation(Helper.screenCenterPosition("x", getSize()), Helper.screenCenterPosition("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
        button_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.isFieldEmpty(field_password) || Helper.isFieldEmpty(field_username)) {
                    Helper.showMessage("Kullanıcı adı ve şifrenizi giriniz.");
                } else {
                    if (Helper.isPasswordAndUsernameMatches(field_username.getText(), field_password.getText())) {
                        OperatorGUI operatorGUI = new OperatorGUI();
                        dispose();
                    } else {
                        Helper.showMessage("Hatalı kullanıcı adı ve şifre.");
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        LoginGUI loginGUI = new LoginGUI();
    }
}
