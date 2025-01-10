package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class HalamanForgotPassword extends JPanel {
    private final MainFrame mainFrame;
    private JTextField txtEmail;
    private JButton btnBack;
    private JButton btnResetPassword;
    private JLabel lblTitle;

    public HalamanForgotPassword(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(102, 152, 108));

        // Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(102, 152, 108));
        titlePanel.setBorder(new EmptyBorder(30, 0, 20, 0));

        lblTitle = new JLabel("Reset Your Password", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitle.setForeground(new Color(255, 255, 255));
        titlePanel.add(lblTitle, BorderLayout.CENTER);

        // Content Panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(102, 152, 108));
        contentPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Email input
        txtEmail = createTextField("Email Address");
        btnResetPassword = createButton("Reset Password", new Color(24, 120, 36), Color.WHITE);
        btnBack = createButton("BACK", new Color(128, 37, 26), Color.WHITE);

        // Adding components to grid
        gbc.gridx = 0; gbc.gridy = 0;
        contentPanel.add(new JLabel("Email Address:"), gbc);
        gbc.gridy++;
        contentPanel.add(txtEmail, gbc);

        gbc.gridy++;
        contentPanel.add(btnResetPassword, gbc);

        gbc.gridy++;
        contentPanel.add(btnBack, gbc);

        // Add panels to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Add main panel to frame
        add(mainPanel);
        setupListeners();
    }
    // Helper method to create styled JTextField
    private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField(20);
        textField.setPreferredSize(new Dimension(300, 40));
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(204, 204, 204)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        textField.setToolTipText(placeholder);
        return textField;
    }

    // Helper method to create styled JPasswordField
    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(300, 40));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(204, 204, 204)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        passwordField.setToolTipText(placeholder);
        return passwordField;
    }

    // Helper method to create styled JButton
    private JButton createButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(300, 45));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        return button;
    }


    public JTextField getTxtEmail() {
        return txtEmail;
    }
    private void setupListeners(){
        btnBack.addActionListener(e -> mainFrame.showLogin());
    }

}