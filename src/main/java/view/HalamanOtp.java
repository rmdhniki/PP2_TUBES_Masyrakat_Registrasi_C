package view;

import controller.ControllerUser;
import javax.swing.*;
import java.awt.*;

public class HalamanOtp extends JPanel {
    private final MainFrame mainFrame;
    private JTextField otpField;
    private JButton submitButton;
    private JButton backButton;
    private String type;
    private String email;
    public HalamanOtp(MainFrame mainFrame, String email, String type) {
        this.mainFrame = mainFrame;
        this.type = type;
        this.email = email;
        initComponents();
        setupLayout();
        setupListeners();
    }

    public HalamanOtp(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.type = "register"; // Set a default type for register
        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        otpField = new JTextField(20);
        submitButton = createButton("Submit", new Color(0x187824), Color.WHITE);
        backButton = createButton("Back",  new Color(0x80251A), Color.WHITE);

        Dimension fieldSize = new Dimension(300, 40);
        otpField.setPreferredSize(fieldSize);
    }
    // Helper method untuk create button
    private JButton createButton(String text, Color bgColor, Color fgColor) {
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

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(0x66986C));
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.gridwidth = GridBagConstraints.REMAINDER;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.insets = new Insets(5, 10, 5, 10);

        formPanel.add(new JLabel("Enter OTP:", SwingConstants.LEFT), formGbc);
        formPanel.add(otpField, formGbc);
        formPanel.add(Box.createVerticalStrut(20), formGbc);
        formPanel.add(submitButton, formGbc);
        formPanel.add(Box.createVerticalStrut(10), formGbc);
        formPanel.add(backButton, formGbc);

        add(formPanel, gbc);
    }

    private void setupListeners() {
        submitButton.addActionListener(e -> {
            String otp = otpField.getText().trim();

            if (otp.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter the OTP",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                ControllerUser userController = new ControllerUser();
                if (email == null || email.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Error: Email for verification is missing.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean verified = userController.verifyOtp(email, otp, type);

                if (verified) {
                    JOptionPane.showMessageDialog(this,
                            "OTP Verified Successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    if(type.equals("register")){
                        mainFrame.showLogin();
                    }
                    else{
                        mainFrame.showResetPasswordForm(email);
                    }

                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid or Expired OTP",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error during OTP verification: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            if (type != null && type.equals("register")) {
                mainFrame.showRegister();
            } else {
                mainFrame.showForgotPassword();
            }
        });

    }
}