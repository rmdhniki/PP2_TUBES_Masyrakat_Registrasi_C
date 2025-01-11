package view;

import controller.ControllerUser;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class FormDialogUser extends JDialog {
    private JTextField nameField;
    private JTextField emailField;
    private JTextField addressField;
    private JTextField birthDateField;
    private JComboBox<Integer> roleComboBox;
    private JButton saveButton;
    private JButton cancelButton;
    private User selectedUser;

    public FormDialogUser(JFrame parent, String title, ControllerUser userController, User selectedUser) {
        super(parent, title, true);
        this.selectedUser = selectedUser;

        nameField = new JTextField(20);
        emailField = new JTextField(20);
        addressField = new JTextField(20);
        birthDateField = new JTextField(20);
        roleComboBox = new JComboBox<>(new Integer[] {1,2}); // Assuming role IDs are 1 and 2
        saveButton = new JButton("Simpan");
        cancelButton = new JButton("Batal");

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        inputPanel.add(new JLabel("Nama:"),gbc);
        gbc.gridx++;
        inputPanel.add(nameField,gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Email:"),gbc);
        gbc.gridx++;
        inputPanel.add(emailField,gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Alamat:"),gbc);
        gbc.gridx++;
        inputPanel.add(addressField,gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Tanggal Lahir (YYYY-MM-DD):"),gbc);
        gbc.gridx++;
        inputPanel.add(birthDateField,gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Role ID:"),gbc);
        gbc.gridx++;
        inputPanel.add(roleComboBox,gbc);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);


        pack();
        setLocationRelativeTo(parent);
        setResizable(false);


        cancelButton.addActionListener(e -> dispose());
        if(selectedUser != null){
            loadUserData(selectedUser);
        }

    }
    private void loadUserData(User user){
        nameField.setText(user.getName());
        emailField.setText(user.getEmail());
        addressField.setText(user.getAddress() != null ? user.getAddress() : "");
        birthDateField.setText(user.getBirthDate() != null ? user.getBirthDate().toString() : "");
        if(user.getRoleId() != null){
            roleComboBox.setSelectedItem(user.getRoleId());
        }
    }


    public String getName() {
        return nameField.getText().trim();
    }
    public String getEmail() {
        return emailField.getText().trim();
    }

    public String getAddress() {
        return addressField.getText().trim();
    }

    public LocalDate getBirthDate() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
            return LocalDate.parse(birthDateField.getText().trim(), formatter);
        } catch (DateTimeParseException e) {
            return null; // Return null if parsing fails
        }
    }
    public Integer getRoleId(){
        return (Integer) roleComboBox.getSelectedItem();
    }

    public void addSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }
}