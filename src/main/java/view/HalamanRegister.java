package view;

import controller.ControllerUser;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class HalamanRegister extends JPanel {
    private final MainFrame mainFrame;
    private final ControllerUser userController;
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextArea addressArea;
    private JTextField birthDateField;
    private JButton registerButton;
    private JButton backButton;


    public HalamanRegister(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.userController = new ControllerUser();

        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        addressArea = new JTextArea(3, 20);
        addressArea.setLineWrap(true);
        birthDateField = new JTextField(20);
        registerButton = createButton("Register", new Color(0x187824), Color.WHITE); // Menggunakan helper method
        backButton = createButton("Back to Login",  new Color(0x80251A), Color.WHITE); // Menggunakan helper method

        // Set preferred sizes
        Dimension fieldSize = new Dimension(300, 40);
        nameField.setPreferredSize(fieldSize);
        emailField.setPreferredSize(fieldSize);
        passwordField.setPreferredSize(fieldSize);
        confirmPasswordField.setPreferredSize(fieldSize);
        birthDateField.setPreferredSize(fieldSize);

    }
    private  JButton createButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(300, 50));
        button.setFont(new Font("Sans-Serif", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(300,50));
        return button;
    }
    private void setupLayout() {
        setLayout(new GridBagLayout());
        setBackground(new Color(0x66986C));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);


        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0x66986C));
        JLabel titleLabel = new JLabel("Register New Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(0x66986C));
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.gridwidth = GridBagConstraints.REMAINDER;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.insets = new Insets(5, 10, 5, 10);

        formPanel.add(new JLabel("Name:", SwingConstants.LEFT), formGbc);
        formPanel.add(nameField, formGbc);
        formPanel.add(new JLabel("Email:", SwingConstants.LEFT), formGbc);
        formPanel.add(emailField, formGbc);
        formPanel.add(new JLabel("Password:", SwingConstants.LEFT), formGbc);
        formPanel.add(passwordField, formGbc);
        formPanel.add(new JLabel("Confirm Password:", SwingConstants.LEFT), formGbc);
        formPanel.add(confirmPasswordField, formGbc);
        formPanel.add(new JLabel("Address:", SwingConstants.LEFT), formGbc);
        formPanel.add(new JScrollPane(addressArea), formGbc);
        formPanel.add(new JLabel("Birth Date (YYYY-MM-DD):", SwingConstants.LEFT), formGbc);
        formPanel.add(birthDateField, formGbc);
        formPanel.add(Box.createVerticalStrut(10), formGbc);
        formPanel.add(registerButton, formGbc);
        formPanel.add(Box.createVerticalStrut(5), formGbc);
        formPanel.add(backButton, formGbc);

        add(titlePanel, gbc);
        add(Box.createVerticalStrut(20), gbc);
        add(formPanel, gbc);
    }

    private void setupListeners() {
        registerButton.addActionListener(e -> {
            if (validateInput()) {
                registerUser();
            }
        });
        backButton.addActionListener(e -> {
            clearFields();
            mainFrame.showLogin();
        });
    }

    private boolean validateInput() {
        if (nameField.getText().isEmpty() || emailField.getText().isEmpty() ||
                new String(passwordField.getPassword()).isEmpty() ||
                new String(confirmPasswordField.getPassword()).isEmpty() ||
                addressArea.getText().isEmpty() || birthDateField.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please fill all fields",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!new String(passwordField.getPassword()).equals(new String(confirmPasswordField.getPassword()))) {
            JOptionPane.showMessageDialog(this,
                    "Passwords do not match",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            LocalDate.parse(birthDateField.getText());
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Invalid date format. Please use YYYY-MM-DD",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void registerUser() {
        try {
            User user = new User();
            user.setName(nameField.getText());
            user.setEmail(emailField.getText());
            user.setPassword(new String(passwordField.getPassword()));
            user.setAddress(addressArea.getText());
            user.setBirthDate(LocalDate.parse(birthDateField.getText()));

            userController.register(user);

            // Tampilkan pesan sukses dan informasikan pengguna untuk cek email
            JOptionPane.showMessageDialog(this,
                    "Registration successful! Please check your email to verify your account.",
                    "Registration Success",
                    JOptionPane.INFORMATION_MESSAGE);

            // Simpan email pengguna untuk digunakan di OTPPanel
            mainFrame.setEmailForVerification(user.getEmail());

            // Redirect ke OTPPanel
            mainFrame.showOTP();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Registration failed: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        addressArea.setText("");
        birthDateField.setText("");
    }

}