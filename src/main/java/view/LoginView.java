package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnGoToRegister;
    private JButton btnBack;
    private JLabel lblTitle;
    private JLabel lblMessage;
    private JLabel lblRegisterPrompt;
    private JLabel lblForgotPassword;
    private JLabel lblEmailError;  // Deklarasi lblEmailError


    public LoginView() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(102, 152, 108));

        // Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(102, 152, 108));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

        lblTitle = new JLabel("LOGIN E-Waste", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitle.setForeground(new Color(255, 255, 255));
        titlePanel.add(lblTitle, BorderLayout.CENTER);

        // Content Panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(102, 152, 108));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Adding components to content panel
        txtUsername = createTextField("Email");
        txtPassword = createPasswordField("Password");

        btnLogin = createButton("Login", new Color(24, 120, 36), Color.WHITE);
        btnGoToRegister = createButton("Register", new Color(24, 120, 36), Color.WHITE);
        btnBack = createButton("Back", new Color(128, 37, 26), Color.WHITE);

        // Label for register prompt
        lblRegisterPrompt = new JLabel("Don't have an account? Click Register", SwingConstants.CENTER);
        lblRegisterPrompt.setFont(new Font("Arial", Font.ITALIC, 12));
        lblRegisterPrompt.setForeground(new Color(255, 255, 255));

        // Label for forgot password
        lblForgotPassword = new JLabel("Forgot password? Click Here", SwingConstants.CENTER);
        lblForgotPassword.setFont(new Font("Arial", Font.ITALIC, 12));
        lblForgotPassword.setForeground(new Color(255, 255, 255));
        lblForgotPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblForgotPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                lblForgotPassword.setForeground(new Color(200, 200, 200));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                lblForgotPassword.setForeground(new Color(255, 255, 255));
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // Listener untuk forgot password diatur di controller
            }
        });

        // Error message label
        lblMessage = new JLabel("");
        lblMessage.setFont(new Font("Arial", Font.PLAIN, 12));
        lblMessage.setForeground(Color.RED);


        // Inisialisasi label error email
        lblEmailError = new JLabel("");
        lblEmailError.setFont(new Font("Arial", Font.PLAIN, 12));
        lblEmailError.setForeground(Color.RED);

        // Adding components to grid
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(new JLabel("Email:"), gbc);
        gbc.gridy++;
        contentPanel.add(txtUsername, gbc);

        gbc.gridy++;
        contentPanel.add(lblEmailError, gbc); // Add label email error di bawah txtUsername

        gbc.gridy++;
        contentPanel.add(new JLabel("Password:"), gbc);
        gbc.gridy++;
        contentPanel.add(txtPassword, gbc);

        gbc.gridy++;
        contentPanel.add(lblForgotPassword, gbc); // Add forgot password label

        gbc.gridy++;
        gbc.insets = new Insets(20, 10, 10, 10);
        contentPanel.add(btnLogin, gbc);

        gbc.gridy++;
        contentPanel.add(lblRegisterPrompt, gbc);

        gbc.gridy++;
        contentPanel.add(btnGoToRegister, gbc);

        gbc.gridy++;
        contentPanel.add(lblMessage, gbc);

        gbc.gridy++;
        contentPanel.add(btnBack, gbc);

        // Add panels to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Add main panel to frame
        add(mainPanel);
    }

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
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

    public void addForgotPasswordListener(ActionListener listener) {
        lblForgotPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                listener.actionPerformed(null);
            }
        });
    }

    public void addRegisterListener(ActionListener listener) {
        btnGoToRegister.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        btnBack.addActionListener(listener);
    }

    public void addSignInListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }


    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public JLabel getLblMessage() {
        return lblMessage;
    }

    public JLabel getLblEmailError() {
        return lblEmailError;
    }
}