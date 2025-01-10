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

    // Components for Categories Tab
    private JTable categoriesTable;
    private DefaultTableModel categoryTableModel;
    private JButton refreshCategoryButton;
    private JButton addCategoryButton;
    private JButton deleteCategoryButton;
    private JButton updateCategoryButton;

    // Components for Item Types Tab
    private JTable itemTypeTable;
    private DefaultTableModel itemTypeTableModel;
    private JButton refreshItemTypeButton;
    private JButton addItemTypeButton;
    private JButton deleteItemTypeButton;
    private JButton updateItemTypeButton;
    private JComboBox<Integer> categoryComboBox;

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
        userTableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Email", "Alamat", "Tanggal Lahir", "Foto"}, 0);
        usersTable = new JTable(userTableModel);
        refreshUserButton = new JButton("Refresh Akun");
        updateUserButton = new JButton("Rubah Akun");
        deleteUserButton = new JButton("Hapus Akun");

        // Categories Tab
        categoryTableModel = new DefaultTableModel(new String[]{"ID", "Nama Kategori", "Deskripsi"}, 0);
        categoriesTable = new JTable(categoryTableModel);
        refreshCategoryButton = new JButton("Refresh Kategori");
        addCategoryButton = new JButton("Tambah Kategori");
        deleteCategoryButton = new JButton("Hapus Kategori");
        updateCategoryButton = new JButton("Rubah Kategori");

        // Item Types Tab
        itemTypeTableModel = new DefaultTableModel(new String[]{"ID", "Nama Jenis Item", "Kategori", "Deskripsi"}, 0);
        itemTypeTable = new JTable(itemTypeTableModel);
        refreshItemTypeButton = new JButton("Refresh Jenis Item");
        addItemTypeButton = new JButton("Tambah Jenis Item");
        updateItemTypeButton = new JButton("Rubah Jenis Item");
        deleteItemTypeButton = new JButton("Hapus Jenis Item");

        // Logout Button
        logoutButton = new JButton("Logout");
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Users Tab Layout
        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.add(new JScrollPane(usersTable), BorderLayout.CENTER);
        userPanel.add(createButtonPanel(refreshUserButton, updateUserButton, deleteUserButton), BorderLayout.SOUTH);
        tabbedPane.addTab("Kelola Akun", userPanel);

        // Categories Tab Layout
        JPanel categoryPanel = new JPanel(new BorderLayout());
        categoryPanel.add(new JScrollPane(categoriesTable), BorderLayout.CENTER);
        categoryPanel.add(createButtonPanel(refreshCategoryButton, addCategoryButton, updateCategoryButton ,deleteCategoryButton), BorderLayout.SOUTH);
        tabbedPane.addTab("Kelola Kategori", categoryPanel);

        // Item Types Tab Layout
        JPanel itemTypePanel = new JPanel(new BorderLayout());
        itemTypePanel.add(new JScrollPane(itemTypeTable), BorderLayout.CENTER);
        itemTypePanel.add(createButtonPanel(refreshItemTypeButton, addItemTypeButton, updateItemTypeButton, deleteItemTypeButton), BorderLayout.SOUTH);
        tabbedPane.addTab("Kelola Jenis Item", itemTypePanel);

        // Add TabbedPane and Logout Button
        add(tabbedPane, BorderLayout.CENTER);
        add(logoutButton, BorderLayout.SOUTH);
    }

    private JPanel createButtonPanel(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
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


    private void setupListeners() {
        // User Tab Listeners
        refreshUserButton.addActionListener(e -> loadUsers());
        updateUserButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Fitur Rubah Akun Belum Diimplementasikan"));
        deleteUserButton.addActionListener(e -> deleteUser());

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
        deleteCategoryButton.addActionListener(e -> deleteCategory());

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
                        Kategori selectedCategory = dialog.getSelectedCategory();

                        if (!newName.isEmpty() && !newDescription.isEmpty() && selectedCategory != null) {
                            JenisSampah updatedItemType = new JenisSampah();
                            updatedItemType.setId(itemTypeId);
                            updatedItemType.setName(newName);
                            updatedItemType.setDescription(newDescription);
                            updatedItemType.setCategoryId(selectedCategory.getId());

                            itemTypeController.updateItemType(updatedItemType);
                            loadItemTypes();
                            dialog.dispose();
                        } else {
                            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                        }
                    });

                    dialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Kategori yang dipilih tidak valid!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Silahkan pilih jenis item yang ingin diubah!");
            }
        });

        refreshItemTypeButton.addActionListener(e -> loadItemTypes());
        deleteItemTypeButton.addActionListener(e -> deleteItemType());

        // Logout Listener
        logoutButton.addActionListener(e -> mainFrame.showLogin());
    }

    public void loadUsers() {
        userTableModel.setRowCount(0);
        List<User> users = userController.getAllUsers();
        for (User user : users) {
            userTableModel.addRow(new Object[]{
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getAddress(),
                    user.getBirthDate(),
                    user.getPhotoPath()
            });
        }
    }

    private void deleteUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow != -1) {
            int userId = (int) userTableModel.getValueAt(selectedRow, 0);
            userController.deleteUser(userId);
            loadUsers();
        } else {
            JOptionPane.showMessageDialog(this, "Silahkan pilih akun yang ingin dihapus!");
        }
    }

    private void deleteCategory() {
        int selectedRow = categoriesTable.getSelectedRow();
        if (selectedRow != -1) {
            int categoryId = (int) categoryTableModel.getValueAt(selectedRow, 0);
            categoryController.deleteCategory(categoryId);
            loadCategories();
        } else {
            JOptionPane.showMessageDialog(this, "Silahkan pilih kategori yang ingin dihapus!");
        }
    }

    private void deleteItemType() {
        int selectedRow = itemTypeTable.getSelectedRow();
        if (selectedRow != -1) {
            int itemTypeId = (int) itemTypeTableModel.getValueAt(selectedRow, 0);
            itemTypeController.deleteItemType(itemTypeId);
            loadItemTypes();
        } else {
            JOptionPane.showMessageDialog(this, "Silahkan pilih jenis item yang ingin dihapus!");
        }
    }
}