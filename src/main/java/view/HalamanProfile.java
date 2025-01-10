package view;

import controller.ControllerUser;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.awt.geom.Ellipse2D;
import javax.swing.border.EmptyBorder;

public class HalamanProfile extends JPanel {
    private final MainFrame mainFrame;
    private final ControllerUser userController;

    private JTextField nameField;
    private JTextField emailField;
    private JTextField addressField;
    private JTextField birthDateField; // Format: yyyy-MM-dd
    private JLabel profilePictureLabel;
    private JButton saveButton;
    private JButton changePasswordButton;
    private JButton uploadPhotoButton;
    private JButton backButton;

    private User user;

    public HalamanProfile(MainFrame mainFrame, Integer userId) {
        this.mainFrame = mainFrame;
        this.userController = new ControllerUser();
        this.user = userController.getUserById(userId); // Ambil data user berdasarkan ID

        initComponents();
        populateFields();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        nameField = new JTextField(20);
        addressField = new JTextField(20);
        birthDateField = new JTextField(20);

        profilePictureLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                if (getIcon() != null) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    int diameter = Math.min(getWidth(), getHeight());
                    int x = (getWidth() - diameter) / 2;
                    int y = (getHeight() - diameter) / 2;

                    g2.setClip(new Ellipse2D.Float(x, y, diameter, diameter));
                    super.paintComponent(g2);
                    g2.dispose();
                } else {
                    super.paintComponent(g);
                }
            }
        };
        profilePictureLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profilePictureLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
        profilePictureLabel.setPreferredSize(new Dimension(150, 150));

        saveButton = createButton("Save", new Color(0x187824), Color.WHITE);
        changePasswordButton = createButton("Reset Password",new Color(0x187824), Color.WHITE);
        backButton = createButton("Back", new Color(0x80251A), Color.WHITE);
        uploadPhotoButton = createButton("Upload Photo",new Color(0x187824), Color.WHITE);


        Dimension fieldSize = new Dimension(300, 40);
        nameField.setPreferredSize(fieldSize);
        addressField.setPreferredSize(fieldSize);
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

    private void populateFields() {
        if (user != null) {
            nameField.setText(user.getName());
            addressField.setText(user.getAddress() != null ? user.getAddress() : "");
            birthDateField.setText(user.getBirthDate() != null ? user.getBirthDate().toString() : "");

            if (user.getPhotoPath() != null) {
                File imgFile = new File(user.getPhotoPath());
                if (imgFile.exists() && imgFile.isFile()) {
                    ImageIcon originalIcon = new ImageIcon(user.getPhotoPath());
                    Image scaledImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    profilePictureLabel.setIcon(new ImageIcon(scaledImage));
                } else {
                    setDefaultImage();
                }
            } else {
                setDefaultImage();
            }
        }
    }

    private void setDefaultImage() {
        try {
            ImageIcon defaultIcon = new ImageIcon(getClass().getResource("/user.png"));
            Image scaledImage = defaultIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            profilePictureLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            profilePictureLabel.setText("Foto tidak ditemukan");
        }
    }

    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(0x66986C));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0x66986C));
        headerPanel.add(backButton, BorderLayout.WEST);

        JPanel profilePanel = new JPanel();
        profilePanel.setBackground(new Color(0x66986C));
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.add(Box.createVerticalStrut(10));
        profilePanel.add(profilePictureLabel);
        profilePanel.add(Box.createVerticalStrut(10));
        profilePanel.add(uploadPhotoButton);

        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(0x66986C));
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nama Lengkap:", SwingConstants.LEFT), gbc);

        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Alamat:", SwingConstants.LEFT), gbc);

        gbc.gridx = 1;
        formPanel.add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Tanggal Lahir:", SwingConstants.LEFT), gbc);

        gbc.gridx = 1;
        formPanel.add(birthDateField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0x66986C));
        buttonPanel.add(saveButton);
        buttonPanel.add(changePasswordButton);



        add(headerPanel);
        add(Box.createVerticalStrut(10));
        add(profilePanel);
        add(Box.createVerticalStrut(10));
        add(formPanel);
        add(Box.createVerticalStrut(20));
        add(buttonPanel);
    }

    private void setupListeners() {
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String address = addressField.getText();
            String birthDateStr = birthDateField.getText();

            if (name.isEmpty() || birthDateStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Nama atau tanggal lahir harus diisi!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                LocalDate birthDate = LocalDate.parse(birthDateStr);
                user.setName(name);
                user.setAddress(address);
                user.setBirthDate(birthDate);

                boolean updated = userController.updateUser(user);
                if (updated) {
                    JOptionPane.showMessageDialog(this,
                            "Profil berhasil diperbarui!",
                            "Sukses",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Gagal memperbarui profil.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        uploadPhotoButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String photoPath = fileChooser.getSelectedFile().getAbsolutePath();
                user.setPhotoPath(photoPath);

                ImageIcon imageIcon = new ImageIcon(photoPath);
                Image image = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                profilePictureLabel.setIcon(new ImageIcon(image));
            }
        });

        changePasswordButton.addActionListener(e -> {
            mainFrame.showForgotPassword();
        });

        backButton.addActionListener(e -> {
            mainFrame.showHalamanUtama();
        });
    }
}