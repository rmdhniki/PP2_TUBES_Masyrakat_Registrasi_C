package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
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
         forgotPasswordLabel.setForeground(new Color(0x00008B));
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
        JLabel titleLabel = new JLabel("Login to E-Waste");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);


        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(0x66986C));

        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.gridwidth = GridBagConstraints.REMAINDER;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.insets = new Insets(5, 10, 5, 10);


         JLabel emailLabel = new JLabel("Email:", SwingConstants.LEFT);
       emailLabel.setForeground(Color.WHITE);
       emailLabel.setFont(emailLabel.getFont().deriveFont(Font.BOLD, 14));
        formPanel.add(emailLabel, formGbc);
        formPanel.add(emailField, formGbc);
        JLabel passwordLabel = new JLabel("Password:", SwingConstants.LEFT);
       passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(passwordLabel.getFont().deriveFont(Font.BOLD, 14));
        formPanel.add(passwordLabel, formGbc);
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
     private JButton createButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isArmed()) {
                    g.setColor(getBackground().darker());
                } else {
                   g.setColor(getBackground());
                }
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int cornerRadius = 15;
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
                 super.paintComponent(g2);
                g2.dispose();
           }
            @Override
            public void updateUI() {
                super.updateUI();
                setOpaque(false);
              setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            }
        };
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
            public void mouseEntered(MouseEvent e) {
                forgotPasswordLabel.setForeground(Color.WHITE);
            }

            @Override
           public void mouseExited(MouseEvent e) {
                forgotPasswordLabel.setForeground(new Color(0x00008B));
           }

            @Override
            public void mouseClicked(MouseEvent e) {
                 forgotPasswordLabel.setForeground(new Color(0x00008B));
               mainFrame.showForgotPassword();
            }
       });
    }
}