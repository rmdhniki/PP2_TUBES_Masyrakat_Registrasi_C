package view;

import javax.swing.*;
import java.awt.*;
import controller.ControllerUser;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private HalamanLogin loginPanel;
    private HalamanUtamaPanel halamanUtamaPanel;
    private HalamanRegister registerPanel;
    private HalamanOtp otpPanel;
    private HalamanProfile profilePanel;
    private DashboardAdmin dashboardPanel; // Dashboard Panel
    private HalamanBeranda halamanBerandaPanel;
    private HalamanForgotPassword forgotPasswordPanel; // Halaman forgot password
    private String emailForVerification;
    private Integer currentUserId; // Untuk menyimpan user ID yang login


    public MainFrame() {
        initComponents();
        setupFrame();
    }

    private void initComponents() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize panels
        halamanBerandaPanel = new HalamanBeranda(this);
        loginPanel = new HalamanLogin(this);
        registerPanel = new HalamanRegister(this);
        otpPanel = new HalamanOtp(this);
        halamanUtamaPanel = new HalamanUtamaPanel(this);
        // Initialize Dashboard with ControllerUser (without UserDao)
        ControllerUser userController = new ControllerUser();
        dashboardPanel = new DashboardAdmin(this);
        profilePanel = new HalamanProfile(this, currentUserId);
        forgotPasswordPanel = new HalamanForgotPassword(this); // Inisialisasi forgot password

        // Add panels to card layout
        mainPanel.add(halamanBerandaPanel,"BERANDA");
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(registerPanel, "REGISTER");
        mainPanel.add(otpPanel, "OTP");
        mainPanel.add(halamanUtamaPanel, "HALAMAN_UTAMA");
        mainPanel.add(profilePanel, "PROFILE");
        mainPanel.add(forgotPasswordPanel,"FORGOT_PASSWORD"); // Tambahkan ForgotPassword ke card layout
        mainPanel.add(dashboardPanel, "DASHBOARD"); // Tambahkan dashboard ke main panel



        // Add main panel to frame
        add(mainPanel);

        // Show beranda by default
        showBeranda();
    }
    // Tambahkan metode untuk menampilkan ProfilePanel
    public void showProfile() {
        // Pastikan userId diambil dari user yang login
        if (currentUserId != null) {
            profilePanel = new HalamanProfile(this, currentUserId); // Berikan userId saat inisialisasi
            mainPanel.add(profilePanel, "PROFILE"); // Tambahkan panel ke mainPanel
            cardLayout.show(mainPanel, "PROFILE"); // Tampilkan ProfilePanel
        } else {
            JOptionPane.showMessageDialog(this, "User ID tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showBeranda() {
        cardLayout.show(mainPanel, "BERANDA");
    }

    private void setupFrame() {
        setTitle("E-WastePas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void showLogin() {
        cardLayout.show(mainPanel, "LOGIN");
    }

    public void showRegister() {
        cardLayout.show(mainPanel, "REGISTER");
    }

    public void showOTP() {
        cardLayout.show(mainPanel, "OTP"); // Menampilkan OTPPanel
    }

    public void showDashboard() {
        cardLayout.show(mainPanel, "DASHBOARD");
    }
    public void showForgotPassword(){
        cardLayout.show(mainPanel, "FORGOT_PASSWORD");
    }


    public void setEmailForVerification(String email) {
        this.emailForVerification = email;
    }

    public String getEmailForVerification() {
        return emailForVerification;
    }

    public void showHalamanUtama() {
        cardLayout.show(mainPanel, "HALAMAN_UTAMA");
    }

    public void setCurrentUserId(Integer userId) {
        this.currentUserId = userId;
    }

    public Integer getCurrentUserId() {
        return currentUserId;
    }
}