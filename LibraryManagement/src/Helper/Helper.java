package Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {

    public static int screenCenterPosition(String X_Y, Dimension d) {
        int coordinate;
        switch (X_Y) {
            case "X":
            case "x":
                coordinate = (Toolkit.getDefaultToolkit().getScreenSize().width - d.width) / 2;
                break;
            case "y":
            case "Y":
                coordinate = (Toolkit.getDefaultToolkit().getScreenSize().height - d.height) / 2;
                break;
            default:
                coordinate = 0;
        }
        return coordinate;
    }

    public static boolean isFieldEmpty(JTextField textField) {
        return textField.getText().trim().isEmpty();
    }

    public static void showMessage(String text) {
        JOptionPane.showMessageDialog(null, text, "Bilgilendirme", JOptionPane.INFORMATION_MESSAGE);

    }

    public static boolean confirmTransaction(String message) {
        return JOptionPane.showConfirmDialog(null, message, "Dikkat", JOptionPane.YES_NO_OPTION) == 0;
    }

    public static void setLayout() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static boolean isPasswordAndUsernameMatches(String username, String password) {
        if (username.equals(Config.APP_USERNAME) && password.equals(Config.APP_PASSWORD)) {
            return true;
        } else {
            return false;
        }
    }
}
