package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HalamanBeranda extends JPanel {
    private final MainFrame mainFrame;
    private JButton btnSignIn;
    private JButton btnSignUp;

    public HalamanBeranda(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1, 10, 10)); // 4 baris
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Lebih kecil
        mainPanel.setBackground(new Color(0x66986C)); // Warna background

        // Label untuk judul
        JLabel lblTitle = new JLabel("Welcome to E-Waste", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Sans-Serif", Font.BOLD, 28)); // Font lebih besar
        lblTitle.setForeground(Color.WHITE);
        mainPanel.add(lblTitle);

        // Tombol Sign In
        btnSignIn = createButton("Sign In", new Color(0x187824), Color.WHITE);

        // Tombol Sign Up
        btnSignUp = createButton("Register", new Color(0x187824), Color.WHITE);


        // Menambahkan tombol ke dalam panel
        mainPanel.add(btnSignIn);
        mainPanel.add(btnSignUp);

        // Menambahkan panel ke dalam frame
        add(mainPanel);


        setupListeners();
    }
    // Helper method untuk create button
   private JButton createButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Sans-Serif", Font.BOLD, 16));
       button.setBackground(bgColor);
         button.setForeground(fgColor);
        button.setFocusPainted(false);
         button.setPreferredSize(new Dimension(200, 40));
       button.setMaximumSize(new Dimension(200, 40));
         button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }
    private void setupListeners() {
        btnSignIn.addActionListener(e -> mainFrame.showLogin());
        btnSignUp.addActionListener(e -> mainFrame.showRegister());
    }
}