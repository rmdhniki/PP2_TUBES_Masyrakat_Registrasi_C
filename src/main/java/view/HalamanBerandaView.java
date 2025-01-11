package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HalamanBerandaView extends JFrame {
    private JButton btnUserProfile;
    private JButton btnJenisSampah;
    private JButton btnKategoriSampah;
    private JButton btnExit;

    public HalamanBerandaView() {
        setTitle("Halaman Beranda E-Waste");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400); // Ukuran frame
        setLocationRelativeTo(null);
        setResizable(false);

        // Layout utama
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(7, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        mainPanel.setBackground(new Color(102, 152, 108));

        // Label untuk judul
        JLabel lblTitle = new JLabel("Halaman Beranda", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Sans-Serif", Font.BOLD, 24)); // Set font dan ukuran
        lblTitle.setForeground(new Color(255, 255, 255)); // Set warna teks
        mainPanel.add(lblTitle); // Tambahkan label ke panel

        // Label untuk E-Waste
        JLabel lblEWaste = new JLabel("E-Waste", SwingConstants.CENTER);
        lblEWaste.setFont(new Font("Sans-Serif", Font.BOLD, 18)); // Set font dan ukuran
        lblEWaste.setForeground(new Color(255, 255, 255)); // Set warna teks
        mainPanel.add(lblEWaste); // Tambahkan label ke panel

        // Tombol User Profile
        btnUserProfile = createButton("User Profile");

        // Tombol Jenis Sampah
        btnJenisSampah = createButton("Jenis Sampah");

        // Tombol Kategori Sampah
        btnKategoriSampah = createButton("Kategori Sampah");

        // Tombol Exit dengan warna yang diminta
        btnExit = createButton("Exit", new Color(128, 37, 26)); // Dark Red

        // Menambahkan tombol ke dalam panel
        mainPanel.add(btnUserProfile);
        mainPanel.add(btnJenisSampah);
        mainPanel.add(btnKategoriSampah);
        mainPanel.add(btnExit);

        // Menambahkan panel ke dalam frame
        add(mainPanel);
    }

    // Helper method to create styled JButton
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        button.setBackground(new Color(24, 120, 36));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // Helper method to create styled JButton with custom color
    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // Method to add listeners for each button
    public void addUserProfileListener(ActionListener listener) {
        btnUserProfile.addActionListener(listener);
    }

    public void addJenisSampahListener(ActionListener listener) {
        btnJenisSampah.addActionListener(listener);
    }

    public void addKategoriSampahListener(ActionListener listener) {
        btnKategoriSampah.addActionListener(listener);
    }

    public void addExitListener(ActionListener listener) {
        btnExit.addActionListener(listener);
    }
}