package view;

import controller.ControllerUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormResetPassword extends JDialog {
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton okButton;
    private JButton cancelButton;
    private final String email;
    private final MainFrame mainFrame;
    private final ControllerUser userController;


    public FormResetPassword(JFrame parent, String email, MainFrame mainFrame) {
        super(parent, "Reset Password", true);
        this.email = email;
        this.mainFrame = mainFrame;
        this.userController = new ControllerUser();
        initComponents();
        setupLayout();
        setupListeners();

    }

    private void initComponents() {
        newPasswordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        Dimension fieldSize = new Dimension(300, 40);
        newPasswordField.setPreferredSize(fieldSize);
        confirmPasswordField.setPreferredSize(fieldSize);

    }
    private void setupLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.gridwidth = GridBagConstraints.REMAINDER;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.insets = new Insets(5, 10, 5, 10);
        formPanel.add(new JLabel("New Password:", SwingConstants.LEFT), formGbc);
        formPanel.add(newPasswordField, formGbc);
        formPanel.add(new JLabel("Confirm New Password:", SwingConstants.LEFT), formGbc);
        formPanel.add(confirmPasswordField, formGbc);
        formPanel.add(Box.createVerticalStrut(10), formGbc);
        formPanel.add(okButton,formGbc);
        formPanel.add(Box.createVerticalStrut(5), formGbc);
        formPanel.add(cancelButton,formGbc);
        add(formPanel, gbc);
        pack();
        setLocationRelativeTo(getParent());
    }
    private void setupListeners() {
        okButton.addActionListener(e -> {
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all the fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                userController.resetPassword(email,newPassword);
                JOptionPane.showMessageDialog(this,
                        "Reset password successful!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
                mainFrame.showLogin();
            }catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Failed to Reset Password: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        });
        cancelButton.addActionListener(e -> dispose());

    }
}