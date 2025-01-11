package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import controller.ControllerUser;

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
        setResizable(false);

    }

    private void initComponents() {
        oldPasswordField = new JPasswordField(20);
        newPasswordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setForeground(Color.RED);
        okButton = createButton("OK", new Color(0x187824), Color.WHITE);
        cancelButton = createButton("Cancel", new Color(0x80251A), Color.WHITE);
        Dimension fieldSize = new Dimension(300, 40);
        oldPasswordField.setPreferredSize(fieldSize);
        newPasswordField.setPreferredSize(fieldSize);
        confirmPasswordField.setPreferredSize(fieldSize);


    }
    private  JButton createButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
      button.setPreferredSize(new Dimension(170, 40));
      button.setMaximumSize(new Dimension(170, 40));
        button.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
      button.setFocusPainted(false);
        return button;
    }
   private void setupLayout() {
        setLayout(new GridBagLayout());
       JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(0x66986C));
        GridBagConstraints gbc = new GridBagConstraints();
       gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.gridwidth = GridBagConstraints.REMAINDER;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.insets = new Insets(5, 10, 5, 10);

       JLabel oldPasswordLabel = new JLabel("Old Password:", SwingConstants.LEFT);
        oldPasswordLabel.setForeground(Color.WHITE);
        oldPasswordLabel.setFont(oldPasswordLabel.getFont().deriveFont(Font.BOLD,16));
        mainPanel.add(oldPasswordLabel, formGbc);
        mainPanel.add(oldPasswordField, formGbc);

        JLabel newPasswordLabel = new JLabel("New Password:", SwingConstants.LEFT);
        newPasswordLabel.setForeground(Color.WHITE);
        newPasswordLabel.setFont(newPasswordLabel.getFont().deriveFont(Font.BOLD,16));
        mainPanel.add(newPasswordLabel, formGbc);
       mainPanel.add(newPasswordField, formGbc);

        JLabel confirmPasswordLabel = new JLabel("Confirm New Password:", SwingConstants.LEFT);
       confirmPasswordLabel.setForeground(Color.WHITE);
       confirmPasswordLabel.setFont(confirmPasswordLabel.getFont().deriveFont(Font.BOLD,16));
       mainPanel.add(confirmPasswordLabel, formGbc);
       mainPanel.add(confirmPasswordField, formGbc);
       mainPanel.add(errorLabel, formGbc);
        mainPanel.add(Box.createVerticalStrut(10), formGbc);

       JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
       buttonPanel.setBackground(new Color(0x66986C));
        buttonPanel.add(okButton);
       buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel,formGbc);

       add(mainPanel,gbc);
       setBackground(new Color(0x66986C));
       setSize(400,350); //set fixed size of frame
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