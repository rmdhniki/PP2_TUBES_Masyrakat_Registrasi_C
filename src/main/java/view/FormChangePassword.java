package view;

import controller.ControllerUser;

import javax.swing.*;
import java.awt.*;

public class FormChangePassword extends JDialog {
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JLabel errorLabel;
    private JButton okButton;
    private JButton cancelButton;
    private final String email;
    private final MainFrame mainFrame;
    private final ControllerUser userController;


    public FormChangePassword(JFrame parent, String email, MainFrame mainFrame) {
        super(parent, "Change Password", true);
        this.email = email;
        this.mainFrame = mainFrame;
        this.userController = new ControllerUser();
        initComponents();
        setupLayout();
        setupListeners();

    }

    private void initComponents() {
        oldPasswordField = new JPasswordField(20);
        newPasswordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setForeground(Color.RED);
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        Dimension fieldSize = new Dimension(300, 40);
        oldPasswordField.setPreferredSize(fieldSize);
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

        formPanel.add(new JLabel("Old Password:", SwingConstants.LEFT), formGbc);
        formPanel.add(oldPasswordField, formGbc);
        formPanel.add(new JLabel("New Password:", SwingConstants.LEFT), formGbc);
        formPanel.add(newPasswordField, formGbc);
        formPanel.add(new JLabel("Confirm New Password:", SwingConstants.LEFT), formGbc);
        formPanel.add(confirmPasswordField, formGbc);
        formPanel.add(errorLabel, formGbc);
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
            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            errorLabel.setText("");


            if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                errorLabel.setText("Please fill all the fields!");
                return;
            }
            if (!newPassword.equals(confirmPassword)) {
                errorLabel.setText("Passwords do not match!");
                return;
            }
            try {
                boolean changed = userController.changePassword(email,oldPassword,newPassword);
                if(changed) {
                    JOptionPane.showMessageDialog(this,
                            "Change password successful!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }else{
                    errorLabel.setText("Wrong Old Password");
                }
            }catch(Exception ex){
                errorLabel.setText("Failed to change password!");
            }

        });
        cancelButton.addActionListener(e -> dispose());

    }
}