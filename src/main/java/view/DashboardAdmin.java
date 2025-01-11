package view;

import controller.ControllerUser;
import controller.ControllerKategori;
import controller.ControllerJenisSampah;
import model.User;
import model.Kategori;
import model.JenisSampah;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DashboardAdmin extends JPanel {
    private final MainFrame mainFrame;
    private final ControllerUser userController;
    private final ControllerKategori categoryController;
    private final ControllerJenisSampah itemTypeController;

    private JTabbedPane tabbedPane;

    // Components for Users Tab
    private JTable usersTable;
    private DefaultTableModel userTableModel;
    private JButton refreshUserButton;
    private JButton updateUserButton;
    private JButton deleteUserButton;
    private JButton generatePdfUsersButton;

    // Components for Categories Tab
    private JTable categoriesTable;
    private DefaultTableModel categoryTableModel;
    private JButton refreshCategoryButton;
    private JButton addCategoryButton;
    private JButton deleteCategoryButton;
    private JButton updateCategoryButton;
    private JButton generatePdfCategoryButton;

    // Components for Item Types Tab
    private JTable itemTypeTable;
    private DefaultTableModel itemTypeTableModel;
    private JButton refreshItemTypeButton;
    private JButton addItemTypeButton;
    private JButton deleteItemTypeButton;
    private JButton updateItemTypeButton;
    private JComboBox<Integer> categoryComboBox;
    private JButton generatePdfItemTypeButton;

    // Logout Button
    private JButton logoutButton;

    public DashboardAdmin(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.userController = new ControllerUser();
        this.categoryController = new ControllerKategori();
        this.itemTypeController = new ControllerJenisSampah(); // Inisialisasi ItemTypeController

        initComponents();
        setupLayout();
        setupListeners();
        loadUsers();
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        // Users Tab
        userTableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Email", "Alamat", "Tanggal Lahir", "Role"}, 0);
        usersTable = new JTable(userTableModel);
        refreshUserButton = createButton("Refresh Akun", new Color(52, 152, 219), Color.WHITE);
        updateUserButton = createButton("Rubah Akun", new Color(243, 156, 18), Color.WHITE);
        deleteUserButton = createButton("Hapus Akun", new Color(220, 53, 69), Color.WHITE);
        generatePdfUsersButton = createButton("Cetak Laporan User", new Color(26, 188, 156), Color.WHITE);

        // Categories Tab
        categoryTableModel = new DefaultTableModel(new String[]{"ID", "Nama Kategori", "Deskripsi"}, 0);
        categoriesTable = new JTable(categoryTableModel);
        refreshCategoryButton = createButton("Refresh Kategori", new Color(52, 152, 219), Color.WHITE);
        addCategoryButton = createButton("Tambah Kategori", new Color(46, 204, 113), Color.WHITE);
        deleteCategoryButton = createButton("Hapus Kategori", new Color(220, 53, 69), Color.WHITE);
        updateCategoryButton = createButton("Rubah Kategori", new Color(243, 156, 18), Color.WHITE);
        generatePdfCategoryButton = createButton("Cetak Laporan Category", new Color(26, 188, 156), Color.WHITE);

        // Item Types Tab
        itemTypeTableModel = new DefaultTableModel(new String[]{"ID", "Nama Jenis Item", "Kategori", "Deskripsi"}, 0);
        itemTypeTable = new JTable(itemTypeTableModel);
        refreshItemTypeButton = createButton("Refresh Jenis Item", new Color(52, 152, 219), Color.WHITE);
        addItemTypeButton = createButton("Tambah Jenis Item", new Color(46, 204, 113), Color.WHITE);
        updateItemTypeButton = createButton("Rubah Jenis Item", new Color(243, 156, 18), Color.WHITE);
        deleteItemTypeButton = createButton("Hapus Jenis Item", new Color(220, 53, 69), Color.WHITE);
        generatePdfItemTypeButton = createButton("Cetak Laporan Item Type", new Color(26, 188, 156), Color.WHITE);

        // Logout Button
        logoutButton = createButton("Logout", new Color(0x800000), Color.WHITE); // Merah Maroon
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Users Tab Layout
        JPanel userPanel = new JPanel(new BorderLayout());
        JPanel userButtonPanel = createButtonPanel(refreshUserButton, updateUserButton, deleteUserButton,generatePdfUsersButton);
        userButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 45, 20)); // Jarak antara tombol dan panel
        userPanel.add(userButtonPanel, BorderLayout.NORTH);
        userPanel.add(new JScrollPane(usersTable), BorderLayout.CENTER);
        tabbedPane.addTab("Kelola Akun", userPanel);

        // Categories Tab Layout
        JPanel categoryPanel = new JPanel(new BorderLayout());
        JPanel categoryButtonPanel = createButtonPanel(refreshCategoryButton, addCategoryButton, updateCategoryButton ,deleteCategoryButton, generatePdfCategoryButton);
        categoryButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 75, 10)); // Jarak antara tombol dan panel
        categoryPanel.add(categoryButtonPanel, BorderLayout.NORTH);
        categoryPanel.add(new JScrollPane(categoriesTable), BorderLayout.CENTER);
        tabbedPane.addTab("Kelola Kategori", categoryPanel);

        // Item Types Tab Layout
        JPanel itemTypePanel = new JPanel(new BorderLayout());
        JPanel itemTypeButtonPanel = createButtonPanel(refreshItemTypeButton, addItemTypeButton, updateItemTypeButton, deleteItemTypeButton, generatePdfItemTypeButton);
        itemTypeButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 75, 10)); // Jarak antara tombol dan panel
        itemTypePanel.add(itemTypeButtonPanel, BorderLayout.NORTH);
        itemTypePanel.add(new JScrollPane(itemTypeTable), BorderLayout.CENTER);
        tabbedPane.addTab("Kelola Jenis Item", itemTypePanel);

        // Tambahkan TabbedPane dan Logout Button
        add(tabbedPane, BorderLayout.CENTER);

        // Panel untuk tombol Logout
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.add(logoutButton);
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 50, 10));
        add(logoutPanel, BorderLayout.SOUTH);
    }

    private JPanel createButtonPanel(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }
    // Helper method untuk create button
    private JButton createButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        return button;
    }

    private void loadCategories() {
        List<Kategori> categories = categoryController.getAllCategories();
        categoryTableModel.setRowCount(0); // Bersihkan data tabel sebelumnya

        for (Kategori category : categories) {
            categoryTableModel.addRow(new Object[]{
                    category.getId(),
                    category.getName(),
                    category.getDescription()
            });
        }
    }

    private void loadItemTypes() {
        List<JenisSampah> itemTypes = itemTypeController.getAllItemTypes();

        itemTypeTableModel.setRowCount(0);

        Set<String> addedItemTypes = new HashSet<>();

        for (JenisSampah itemType : itemTypes) {
            String uniqueKey = itemType.getId() + "_" + itemType.getName();
            if (!addedItemTypes.contains(uniqueKey)) {
                addedItemTypes.add(uniqueKey);
                String categoryName = (itemType.getCategory() != null) ? itemType.getCategory().getName() : "Unknown Category";

                itemTypeTableModel.addRow(new Object[]{
                        itemType.getId(),
                        itemType.getName(),
                        categoryName,
                        itemType.getDescription()
                });
            }
        }
    }

    private void loadUsers() {
        List<User> users = userController.getAllUsers();
        userTableModel.setRowCount(0);

        for (User user : users) {
            userTableModel.addRow(new Object[]{
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getAddress(),
                    user.getBirthDate() != null ? user.getBirthDate() : "",
                    user.getRoleName() != null ? user.getRoleName() : "User"
            });
        }
    }

    private void setupListeners() {
        // User Tab Listeners
        refreshUserButton.addActionListener(e -> loadUsers());
        updateUserButton.addActionListener(e -> {
            int selectedRow = usersTable.getSelectedRow();
            if (selectedRow != -1) {
                int userId = (int) userTableModel.getValueAt(selectedRow, 0);
                User selectedUser = userController.getUserById(userId);
                FormDialogUser dialog = new FormDialogUser((JFrame) SwingUtilities.getWindowAncestor(this), "Ubah Akun", userController,selectedUser);


                dialog.addSaveButtonListener(event -> {
                    String name = dialog.getName();
                    String email = dialog.getEmail();
                    String address = dialog.getAddress();
                    LocalDate birthDate = dialog.getBirthDate();
                    int roleId = dialog.getRoleId();

                    if (!name.isEmpty() && !email.isEmpty() && !address.isEmpty() && birthDate!=null) {
                        selectedUser.setName(name);
                        selectedUser.setEmail(email);
                        selectedUser.setAddress(address);
                        selectedUser.setBirthDate(birthDate);
                        selectedUser.setRoleId(roleId);

                        boolean updated = userController.updateUser(selectedUser);
                        if (updated) {
                            loadUsers();
                            JOptionPane.showMessageDialog(this, "User berhasil diperbarui!");
                            dialog.dispose();
                        }else {
                            JOptionPane.showMessageDialog(this, "Failed to updated user");
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                    }
                });
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Silahkan pilih akun yang ingin diubah!");
            }
        });


        deleteUserButton.addActionListener(e -> {
            int selectedRow = usersTable.getSelectedRow();
            if (selectedRow != -1) {
                String userName = (String) userTableModel.getValueAt(selectedRow, 1);
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Apakah anda yakin ingin menghapus akun '" + userName + "'?",
                        "Konfirmasi",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    int userId = (int) userTableModel.getValueAt(selectedRow, 0);
                    userController.deleteUser(userId);
                    loadUsers();
                    JOptionPane.showMessageDialog(this, "Akun berhasil dihapus");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Silahkan pilih akun yang ingin dihapus!");
            }
        });
        generatePdfUsersButton.addActionListener(e -> {
            List<User> users = userController.getAllUsers();
            String filePath = "user_report.pdf";
            GeneratePdf.generateUserReport(users, filePath);
            JOptionPane.showMessageDialog(this, "Laporan PDF Berhasil Dicetak di: " + filePath);
        });

        // Category Tab Listeners
        addCategoryButton.addActionListener(e -> {
            FormDialogKategori dialog = new FormDialogKategori((JFrame) SwingUtilities.getWindowAncestor(this), "Tambah Kategori");

            dialog.addSaveButtonListener(event -> {
                String name = dialog.getCategoryName();
                String description = dialog.getCategoryDescription();

                if (!name.isEmpty() && !description.isEmpty()) {
                    Kategori newCategory = new Kategori();
                    newCategory.setName(name);
                    newCategory.setDescription(description);

                    categoryController.addCategory(newCategory);
                    loadCategories();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                }
            });

            dialog.setVisible(true);
        });


        updateCategoryButton.addActionListener(e -> {
            int selectedRow = categoriesTable.getSelectedRow();
            if (selectedRow != -1) {
                int categoryId = (int) categoryTableModel.getValueAt(selectedRow, 0);
                String currentName = (String) categoryTableModel.getValueAt(selectedRow, 1);
                String currentDescription = (String) categoryTableModel.getValueAt(selectedRow, 2);

                FormDialogKategori dialog = new FormDialogKategori((JFrame) SwingUtilities.getWindowAncestor(this), "Ubah Kategori");
                dialog.setCategoryName(currentName);
                dialog.setCategoryDescription(currentDescription);

                dialog.addSaveButtonListener(event -> {
                    String newName = dialog.getCategoryName();
                    String newDescription = dialog.getCategoryDescription();

                    if (!newName.isEmpty() && !newDescription.isEmpty()) {
                        Kategori updatedCategory = new Kategori();
                        updatedCategory.setId(categoryId);
                        updatedCategory.setName(newName);
                        updatedCategory.setDescription(newDescription);

                        categoryController.updateCategory(updatedCategory);
                        loadCategories();
                        JOptionPane.showMessageDialog(this, "Kategori berhasil diperbarui!");
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                    }
                });

                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Silahkan pilih kategori yang ingin diubah!");
            }
        });

        refreshCategoryButton.addActionListener(e -> loadCategories());
        deleteCategoryButton.addActionListener(e -> {
            int selectedRow = categoriesTable.getSelectedRow();
            if (selectedRow != -1) {
                String categoryName = (String) categoryTableModel.getValueAt(selectedRow, 1);
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Apakah anda yakin ingin menghapus kategori '" + categoryName + "'?",
                        "Konfirmasi",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    int categoryId = (int) categoryTableModel.getValueAt(selectedRow, 0);
                    categoryController.deleteCategory(categoryId);
                    loadCategories();
                    JOptionPane.showMessageDialog(this, "Kategori berhasil dihapus");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Silahkan pilih kategori yang ingin dihapus!");
            }
        });
        generatePdfCategoryButton.addActionListener(e -> {
            List<Kategori> categories = categoryController.getAllCategories();
            String filePath = "category_report.pdf";
            GeneratePdf.generateCategoryReport(categories, filePath);
            JOptionPane.showMessageDialog(this, "Laporan PDF Berhasil Dicetak di: " + filePath);
        });

        // Item Type Tab Listeners
        // Tombol "Add" untuk menambahkan Item Type
        addItemTypeButton.addActionListener(e -> {
            FormJenisSampah dialog = new FormJenisSampah(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    itemTypeController,
                    categoryController,
                    "Tambah Jenis Item"
            );

            dialog.addSaveButtonListener(event -> {
                String name = dialog.getItemTypeName();
                String description = dialog.getItemTypeDescription();
                Kategori selectedCategory = dialog.getSelectedCategory();

                if (!name.isEmpty() && !description.isEmpty() && selectedCategory != null) {
                    JenisSampah newItemType = new JenisSampah();
                    newItemType.setName(name);
                    newItemType.setDescription(description);
                    newItemType.setCategoryId(selectedCategory.getId());

                    itemTypeController.addItemType(newItemType);
                    loadItemTypes();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                }
            });

            dialog.setVisible(true);
        });

        // Tombol "Update" untuk memperbarui Item Type yang dipilih
        updateItemTypeButton.addActionListener(e -> {
            int selectedRow = itemTypeTable.getSelectedRow();
            if (selectedRow != -1) {
                int itemTypeId = (int) itemTypeTableModel.getValueAt(selectedRow, 0);
                String currentName = (String) itemTypeTableModel.getValueAt(selectedRow, 1);
                String currentDescription = (String) itemTypeTableModel.getValueAt(selectedRow, 3);

                String currentCategoryName = (String) itemTypeTableModel.getValueAt(selectedRow, 2);

                Kategori currentCategory = categoryController.getCategoryByName(currentCategoryName);

                if (currentCategory != null) {
                    FormJenisSampah dialog = new FormJenisSampah(
                            (JFrame) SwingUtilities.getWindowAncestor(this),
                            itemTypeController,
                            categoryController,
                            "Ubah Jenis Item"
                    );

                    dialog.setItemTypeName(currentName);
                    dialog.setItemTypeDescription(currentDescription);
                    dialog.setCategoryComboBoxSelectedItem(currentCategory);

                    dialog.addSaveButtonListener(event -> {
                        String newName = dialog.getItemTypeName();
                        String newDescription = dialog.getItemTypeDescription();
                        Kategori newCategory = dialog.getSelectedCategory();

                        if (!newName.isEmpty() && !newDescription.isEmpty() && newCategory != null) {
                            JenisSampah updatedItemType = new JenisSampah();
                            updatedItemType.setId(itemTypeId);
                            updatedItemType.setName(newName);
                            updatedItemType.setDescription(newDescription);
                            updatedItemType.setCategoryId(newCategory.getId());

                            itemTypeController.updateItemType(updatedItemType);
                            loadItemTypes();
                            JOptionPane.showMessageDialog(this, "Jenis Item berhasil diperbarui!");
                            dialog.dispose();
                        } else {
                            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                        }
                    });

                    dialog.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Silahkan pilih jenis item yang ingin diubah!");
            }
        });


        refreshItemTypeButton.addActionListener(e -> loadItemTypes());
        deleteItemTypeButton.addActionListener(e -> {
            int selectedRow = itemTypeTable.getSelectedRow();
            if (selectedRow != -1) {
                String itemName = (String) itemTypeTableModel.getValueAt(selectedRow, 1);
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Apakah anda yakin ingin menghapus jenis item '" + itemName + "'?",
                        "Konfirmasi",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    int itemTypeId = (int) itemTypeTableModel.getValueAt(selectedRow, 0);
                    itemTypeController.deleteItemType(itemTypeId);
                    loadItemTypes();
                    JOptionPane.showMessageDialog(this, "Jenis Item berhasil dihapus");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Silahkan pilih jenis item yang ingin dihapus!");
            }
        });
        generatePdfItemTypeButton.addActionListener(e ->{
            List<JenisSampah> itemTypes = itemTypeController.getAllItemTypes();
            String filePath = "itemtype_report.pdf";
            GeneratePdf.generateItemTypeReport(itemTypes, filePath);
            JOptionPane.showMessageDialog(this, "Laporan PDF Berhasil Dicetak di: " + filePath);
        });

        // Logout Button Listener
        logoutButton.addActionListener(e -> mainFrame.showLogin());
    }
}