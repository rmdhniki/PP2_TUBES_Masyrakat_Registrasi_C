package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import controller.ControllerUser;
import model.User;

public class HalamanProfile extends JPanel {
    private final MainFrame mainFrame;
    private final ControllerUser userController;

    private JTextField nameField;
    private JTextField emailField;
    private JTextField addressField;
    private JTextField birthDateField;
    private JLabel profilePictureLabel;
    private JButton saveButton;
    private JButton changePasswordButton;
    private JButton uploadPhotoButton;
     private JButton backButton;
      private JLabel titleLabel;

    private User user;

    public HalamanProfile(MainFrame mainFrame, Integer userId) {
        this.mainFrame = mainFrame;
        this.userController = new ControllerUser();
        this.user = userController.getUserById(userId);

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
       profilePictureLabel.setPreferredSize(new Dimension(120, 120));

        saveButton = createButton("Save", new Color(0x187824), Color.WHITE);
       changePasswordButton = createButton("Change Password", new Color(0x80251A), Color.WHITE);
        uploadPhotoButton = createRoundedButton("Upload Photo", new Color(0xADD8E6), Color.BLACK);
         backButton = createButton("Back", new Color(0x80251A), Color.WHITE);
         titleLabel = new JLabel("Update Your Profile", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
      titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20));

         Dimension fieldSize = new Dimension(280, 40);
        nameField.setPreferredSize(fieldSize);
        addressField.setPreferredSize(fieldSize);
         birthDateField.setPreferredSize(fieldSize);

    }
   private JButton createButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
       button.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        button.setBackground(bgColor);
         button.setForeground(fgColor);
       button.setFocusPainted(false);
       button.setPreferredSize(new Dimension(170, 40));
       button.setMaximumSize(new Dimension(170, 40));
        return button;
   }
    private JButton createRoundedButton(String text, Color bgColor, Color fgColor) {
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
                int cornerRadius = 20;
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
        button.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        button.setBackground(bgColor);
       button.setForeground(fgColor);
      button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        button.setMaximumSize(new Dimension(150, 40));
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
                    Image scaledImage = originalIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
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
            Image scaledImage = defaultIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            profilePictureLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            profilePictureLabel.setText("Foto tidak ditemukan");
        }
    }
    private void setupLayout() {
        setLayout(new BorderLayout());
       setBackground(new Color(0x66986C));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
       headerPanel.setBackground(new Color(0x66986C));
        backButton.setPreferredSize(new Dimension(100, 40));
      backButton.setMaximumSize(new Dimension(100, 40));
        headerPanel.add(backButton);
        add(headerPanel, BorderLayout.NORTH);


        JPanel mainPanel = new JPanel();
       mainPanel.setBackground(new Color(0x66986C));
       mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
       gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;

       gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
         JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
         titlePanel.setBackground(new Color(0x66986C));
        titlePanel.add(titleLabel);
     mainPanel.add(titlePanel,gbc);
        
      JPanel spacingPanel1 = new JPanel();
        spacingPanel1.setBackground(new Color(0x66986C));
       spacingPanel1.setPreferredSize(new Dimension(0, 5));
       gbc.gridy++;
       gbc.gridwidth = 2;
       mainPanel.add(spacingPanel1,gbc);


       JPanel profilePanel = new JPanel(new GridBagLayout());
        profilePanel.setBackground(new Color(0x66986C));
      GridBagConstraints profileGbc = new GridBagConstraints();
        profileGbc.gridx = 0;
        profileGbc.gridy = 0;
         profileGbc.insets = new Insets(5, 5, 5, 5);
       profileGbc.anchor = GridBagConstraints.CENTER;
       profilePanel.add(profilePictureLabel, profileGbc);

        profileGbc.gridy++;
        profileGbc.insets = new Insets(10, 10, 10, 10);
        profilePanel.add(uploadPhotoButton, profileGbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(profilePanel,gbc);


         JPanel spacingPanel2 = new JPanel();
        spacingPanel2.setBackground(new Color(0x66986C));
       spacingPanel2.setPreferredSize(new Dimension(0, 10));
       gbc.gridy++;
        gbc.gridwidth = 2;
         mainPanel.add(spacingPanel2,gbc);


       gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
         JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
         namePanel.setBackground(new Color(0x66986C));
        JLabel nameLabel = new JLabel("Nama Lengkap:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD,14));
      namePanel.add(nameLabel);
       mainPanel.add(namePanel, gbc);

        gbc.gridx = 1;
        JPanel nameInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        nameInputPanel.setBackground(new Color(0x66986C));
       nameInputPanel.add(nameField);
         mainPanel.add(nameInputPanel, gbc);

         gbc.gridx = 0;
         gbc.gridy++;
      JPanel addressPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
       addressPanel.setBackground(new Color(0x66986C));
       JLabel addressLabel = new JLabel("Alamat:");
        addressLabel.setForeground(Color.WHITE);
        addressLabel.setFont(addressLabel.getFont().deriveFont(Font.BOLD,14));
         addressPanel.add(addressLabel);
       mainPanel.add(addressPanel, gbc);


        gbc.gridx = 1;
        JPanel addressInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
       addressInputPanel.setBackground(new Color(0x66986C));
        addressInputPanel.add(addressField);
       mainPanel.add(addressInputPanel, gbc);

        gbc.gridx = 0;
       gbc.gridy++;
        JPanel birthPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        birthPanel.setBackground(new Color(0x66986C));
        JLabel birthLabel = new JLabel("Tanggal Lahir:");
       birthLabel.setForeground(Color.WHITE);
       birthLabel.setFont(birthLabel.getFont().deriveFont(Font.BOLD,14));
         birthPanel.add(birthLabel);
      mainPanel.add(birthPanel, gbc);

        gbc.gridx = 1;
       JPanel birthInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
       birthInputPanel.setBackground(new Color(0x66986C));
      birthInputPanel.add(birthDateField);
        mainPanel.add(birthInputPanel, gbc);
        
       gbc.gridx = 0;
        gbc.gridy++;
       gbc.gridwidth = 2;

       JPanel spacingPanel3 = new JPanel();
        spacingPanel3.setBackground(new Color(0x66986C));
         spacingPanel3.setPreferredSize(new Dimension(0, 20));
         mainPanel.add(spacingPanel3, gbc);

       gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
      buttonPanel.setBackground(new Color(0x66986C));
         buttonPanel.add(saveButton);
      buttonPanel.add(changePasswordButton);
     mainPanel.add(buttonPanel, gbc);
       JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
       add(scrollPane, BorderLayout.CENTER);

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
                Image image = imageIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                profilePictureLabel.setIcon(new ImageIcon(image));
            }
        });

        changePasswordButton.addActionListener(e ->{
            FormChangePassword dialog = new FormChangePassword((JFrame) SwingUtilities.getWindowAncestor(this), user.getEmail(), mainFrame);
            dialog.setVisible(true);
        });

      backButton.addMouseListener(new MouseAdapter() {
            @Override
           public void mouseClicked(MouseEvent e) {
                mainFrame.showHalamanUtama();
           }
        });
   }
}