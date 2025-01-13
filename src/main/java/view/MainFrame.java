package view;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.ControllerUser;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private HalamanLogin loginPanel;
    private HalamanUtamaPanel halamanUtamaPanel;
    private HalamanRegister registerPanel;
    private HalamanOtp otpPanel;
    private HalamanProfile profilePanel;
    private DashboardAdmin dashboardPanel;
    private HalamanBeranda halamanBerandaPanel;
    private HalamanForgotPassword forgotPasswordPanel;
    private String emailForVerification;
    private Integer currentUserId;

    public MainFrame() {
        initComponents();
        setupFrame();
    }

    private void initComponents() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        halamanBerandaPanel = new HalamanBeranda(this);
        loginPanel = new HalamanLogin(this);
        registerPanel = new HalamanRegister(this);
        otpPanel = new HalamanOtp(this);
        halamanUtamaPanel = new HalamanUtamaPanel(this);
        ControllerUser userController = new ControllerUser();
        dashboardPanel = new DashboardAdmin(this);
        profilePanel = new HalamanProfile(this, currentUserId);
        forgotPasswordPanel = new HalamanForgotPassword(this);

        mainPanel.add(halamanBerandaPanel,"BERANDA");
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(registerPanel, "REGISTER");
        mainPanel.add(otpPanel,"OTP");
        mainPanel.add(halamanUtamaPanel, "HALAMAN_UTAMA");
        mainPanel.add(profilePanel, "PROFILE");
        mainPanel.add(forgotPasswordPanel,"FORGOT_PASSWORD");
        mainPanel.add(dashboardPanel, "DASHBOARD");
        add(mainPanel);
        showBeranda();
    }

    public void showProfile() {
        if (currentUserId != null) {
            profilePanel = new HalamanProfile(this, currentUserId);
            mainPanel.add(profilePanel, "PROFILE");
            cardLayout.show(mainPanel, "PROFILE");
        } else {
            JOptionPane.showMessageDialog(this, "User ID tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void showResetPasswordOtp(String email) {
        otpPanel = new HalamanOtp(this, email,"reset_password");
        mainPanel.add(otpPanel, "OTP_RESET_PASSWORD");
        cardLayout.show(mainPanel, "OTP_RESET_PASSWORD");
    }
    public void showResetPasswordForm(String email){
        FormResetPassword resetPassword = new FormResetPassword(this, email, this);
        resetPassword.setVisible(true);
    }

    public void showBeranda() {
        cardLayout.show(mainPanel, "BERANDA");
    }

    private void setupFrame() {
        setTitle("E-Waste");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(420, 800);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void showLogin() {
        cardLayout.show(mainPanel, "LOGIN");
    }

    public void showRegister() {
        cardLayout.show(mainPanel, "REGISTER");
    }

    public void showOTP(String email) {
        otpPanel = new HalamanOtp(this, email, "register");
        mainPanel.add(otpPanel,"OTP");
        cardLayout.show(mainPanel, "OTP");
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