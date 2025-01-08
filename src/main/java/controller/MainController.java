package controller;

import model.LoginMapperImpl;
import view.ResetPasswordView;
import view.HalamanUtamaView;
import view.LoginView;
import view.RegisterView;
import view.HalamanBerandaView;

import javax.swing.*;

public class MainController {
    private HalamanUtamaView halamanUtamaView;
    private LoginView loginView;
    private RegisterView registerView;
    private ResetPasswordView resetPasswordView;
    private HalamanBerandaView halamanBerandaView;
    private LoginMapperImpl loginMapperImpl;


    public MainController() {
        halamanUtamaView = new HalamanUtamaView();
        loginMapperImpl = new LoginMapperImpl();


        // Listener untuk tombol Sign In
        halamanUtamaView.addSignInListener(e -> openLoginView());

        // Listener untuk tombol Sign Up
        halamanUtamaView.addSignUpListener(e -> openRegisterView());

        // Tampilkan halaman utama
        halamanUtamaView.setVisible(true);
    }

    private void openLoginView() {
        if (loginView == null) {
            loginView = new LoginView();

            // Listener untuk tombol Login di LoginView
            loginView.addSignInListener(e -> {
                String email = loginView.getTxtUsername().getText();
                String password = new String(loginView.getTxtPassword().getPassword());
                if (!isValidEmail(email,loginView)){
                    return;
                }
                if (loginMapperImpl.validateLogin(email, password) > 0) {
                    JOptionPane.showMessageDialog(null, "Login Succes <3", "Success", JOptionPane.INFORMATION_MESSAGE);
                    openHalamanBerandaView();
                } else {
                    JOptionPane.showMessageDialog(null, "Login Failed:(", "Failed", JOptionPane.INFORMATION_MESSAGE);
                }

            });


            // Listener untuk tombol Register di LoginView
            loginView.addRegisterListener(e -> {
                loginView.setVisible(false);
                openRegisterView();
            });


            // Listener untuk tombol Back di LoginView
            loginView.addBackListener(e -> {
                loginView.setVisible(false);
                halamanUtamaView.setVisible(true);
            });

            // Listener untuk forgot password in LoginView
            loginView.addForgotPasswordListener(e -> openResetPasswordView());
        }
        loginView.setVisible(true);
        halamanUtamaView.setVisible(false);
    }

    private void openHalamanBerandaView() {
        if (halamanBerandaView == null) {
            halamanBerandaView = new HalamanBerandaView();

            // Listener untuk tombol User Profile
            halamanBerandaView.addUserProfileListener(e -> {
                // Aksi untuk membuka user profile
            });


            // Listener untuk tombol Jenis Sampah
            halamanBerandaView.addJenisSampahListener(e -> {
                // Aksi untuk membuka jenis sampah
            });

            // Listener untuk tombol Kategori Sampah
            halamanBerandaView.addKategoriSampahListener(e -> {
                // Aksi untuk membuka kategori sampah
            });

            // Listener untuk tombol Exit
            halamanBerandaView.addExitListener(e -> {
                halamanBerandaView.setVisible(false);
                openLoginView(); // Kembali ke halaman login
            });
        }
        halamanBerandaView.setVisible(true);
        if (loginView != null) {
            loginView.setVisible(false);
        }
    }

    private void openRegisterView() {
        if (registerView == null) {
            registerView = new RegisterView();


            // Listener untuk tombol Register di RegisterView
            registerView.addRegisterListener(e -> {
                String emailOrPhone = registerView.getTxtEmail().getText();
                String password = new String(registerView.getTxtPassword().getPassword());
                String retypePassword = new String(registerView.getTxtRetypePassword().getPassword());
                if (password.equals(retypePassword)) {
                    if (loginMapperImpl.registerUser(emailOrPhone, password) > 0) {
                        JOptionPane.showMessageDialog(null, "regist Succes <3", "Success", JOptionPane.INFORMATION_MESSAGE);
                        registerView.dispose();
                        openLoginView();
                    } else {
                        JOptionPane.showMessageDialog(null, "Regist Failed:(", "Failed", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    registerView.getLblMessage().setText("Password Not Match");
                }

            });
            // Listener untuk tombol Back di RegisterView
            registerView.addBackListener(e -> {
                registerView.setVisible(false);
                halamanUtamaView.setVisible(true);
            });
        }
        registerView.setVisible(true);

        if (loginView != null) {
            loginView.setVisible(false);
        }
        halamanUtamaView.setVisible(false);
    }

    private void openResetPasswordView() {
        if (resetPasswordView == null) {
            resetPasswordView = new ResetPasswordView();

            // Listener untuk tombol Back di ResetPasswordView
            resetPasswordView.addBackListener(e -> {
                resetPasswordView.setVisible(false);
                if (loginView != null) {
                    loginView.setVisible(true);
                }
            });
        }
        resetPasswordView.setVisible(true);
        if (loginView != null) {
            loginView.setVisible(false);
        }
    }
    private boolean isValidEmail(String email,LoginView loginView) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!email.matches(regex)){
            loginView.getLblEmailError().setText("*Wrong Input");
            return false;
        }
        loginView.getLblEmailError().setText("");
        return true;
    }
}