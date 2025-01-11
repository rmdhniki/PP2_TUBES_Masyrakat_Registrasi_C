package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HalamanUtamaView extends JFrame {
    private JButton btnSignIn;
    private JButton btnSignUp;

    public HalamanUtamaView() {
        setTitle("Halaman Utama");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300); // Ukuran frame
        setLocationRelativeTo(null);
        setResizable(false);

        // Layout utama
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1, 10, 10)); // Ubah menjadi 4 baris untuk menampung label
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        mainPanel.setBackground(new Color(102, 152, 108));

        // Label untuk judul
        JLabel lblTitle = new JLabel("E-Waste", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Sans-Serif", Font.BOLD, 24)); // Set font dan ukuran
        lblTitle.setForeground(new Color(255, 255, 255)); // Set warna teks
        mainPanel.add(lblTitle); // Tambahkan label ke panel

        // Tombol Sign In
        btnSignIn = new JButton("Sign In");
        btnSignIn.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        btnSignIn.setBackground(new Color(24, 120, 36));
        btnSignIn.setForeground(Color.WHITE);
        btnSignIn.setFocusPainted(false);
        btnSignIn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Tombol Sign Up
        btnSignUp = new JButton("Register");
        btnSignUp.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        btnSignUp.setBackground(new Color(24, 120, 36));
        btnSignUp.setForeground(Color.WHITE);
        btnSignUp.setFocusPainted(false);
        btnSignUp.setCursor(new Cursor(Cursor.HAND_CURSOR));


        // Menambahkan tombol ke dalam panel
        mainPanel.add(btnSignIn);
        mainPanel.add(btnSignUp);

        // Menambahkan panel ke dalam frame
        add(mainPanel);
    }

    // Method untuk menambahkan listener Sign In
    public void addSignInListener(ActionListener listener) {
        btnSignIn.addActionListener(listener);
    }

    // Method untuk menambahkan listener Sign Up
    public void addSignUpListener(ActionListener listener) {
        btnSignUp.addActionListener(listener);
    }
}