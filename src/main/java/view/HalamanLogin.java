package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ControllerUser;
import model.User;

public class HalamanLogin extends JPanel {
    private final MainFrame mainFrame;
    private final ControllerUser userController;

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel forgotPasswordLabel;

    public HalamanLogin(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.userController = new ControllerUser();

        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = createButton("Login", new Color(0x187824), Color.WHITE);
        registerButton = createButton("Register", new Color(0x187824), Color.WHITE);
        forgotPasswordLabel = new JLabel("Forgot Password? Click Here");
        forgotPasswordLabel.setForeground(Color.BLUE);
        forgotPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Dimension fieldSize = new Dimension(300, 40);
        emailField.setPreferredSize(fieldSize);
        passwordField.setPreferredSize(fieldSize);


    }

    private void setupLayout() {
        setLayout(new GridBagLayout());
        setBackground(new Color(0x66986C));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0x66986C));
        JLabel titleLabel = new JLabel("E-WastePas");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);


        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(0x66986C));

        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.gridwidth = GridBagConstraints.REMAINDER;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.insets = new Insets(5, 10, 5, 10);


        formPanel.add(new JLabel("Email:", SwingConstants.LEFT), formGbc);
        formPanel.add(emailField, formGbc);
        formPanel.add(new JLabel("Password:", SwingConstants.LEFT), formGbc);
        formPanel.add(passwordField, formGbc);
        formPanel.add(forgotPasswordLabel, formGbc);
        formPanel.add(Box.createVerticalStrut(10), formGbc);
        formPanel.add(loginButton, formGbc);
        formPanel.add(Box.createVerticalStrut(5), formGbc);
        formPanel.add(registerButton, formGbc);


        add(headerPanel, gbc);
        add(Box.createVerticalStrut(20), gbc);
        add(formPanel, gbc);
    }
    // Helper method untuk create button
    private JButton createButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Sans-Serif", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(300, 50));
        button.setMaximumSize(new Dimension(300,50));
        return button;
    }

    private void setupListeners() {
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Tolong isi semua kolom",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                if (userController.login(email, password)) {
                    User user = userController.findUserByEmail(email);
                    mainFrame.setCurrentUserId(user.getId());

                    if (user.getRoleId() == 2) {
                        mainFrame.showHalamanUtama();
                    } else if (user.getRoleId() == 1) {
                        mainFrame.showDashboard();
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Login berhasil!",
                                "Sukses",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Email atau password salah",
                            "Login Gagal",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error saat login: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> mainFrame.showRegister());
        forgotPasswordLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showForgotPassword();
            }
        });
    }
}