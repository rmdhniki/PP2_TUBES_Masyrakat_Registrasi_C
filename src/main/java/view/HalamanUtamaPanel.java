package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import controller.ControllerJenisSampah;
import controller.ControllerKategori;
import controller.ControllerUser;
import model.JenisSampah;
import model.Kategori;

public class HalamanUtamaPanel extends JPanel {

    private final MainFrame mainFrame;
    private final ControllerKategori categoryController;
    private final ControllerJenisSampah itemTypeController;
    private final ControllerUser userController;

    private JPanel navbar;
    private JPanel categoryPanel;
    private JTable itemTypeTable;
    private DefaultTableModel itemTypeTableModel;
    private JLabel imageLabel;
    private JLabel titleLabel;
    private JPanel itemPanel;

    public HalamanUtamaPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.categoryController = new ControllerKategori();
        this.itemTypeController = new ControllerJenisSampah();
        this.userController = new ControllerUser();

        initComponents();
        setupLayout();
        loadDefaultCategoryItems();
    }

    private void initComponents() {
        setBackground(new Color(0x66986C));
        navbar = createNavbar();
        categoryPanel = createCategoryPanel();
        itemTypeTableModel = new DefaultTableModel(new String[]{"Nama", "Deskripsi"}, 0);
        itemTypeTable = new JTable(itemTypeTableModel);
        itemTypeTable.setFocusable(false);
        itemTypeTable.setRowSelectionAllowed(false);
        itemTypeTable.setDefaultEditor(Object.class, null);
        itemTypeTable.getColumnModel().getColumn(1).setCellRenderer(new TextAreaRenderer()); // Set custom renderer for "Deskripsi"
        imageLabel = new JLabel();
        titleLabel = new JLabel("Welcome to E-Waste Category", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Sans-Serif", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        itemPanel = new JPanel(new BorderLayout());

        itemTypeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = itemTypeTable.rowAtPoint(e.getPoint());
                int column = itemTypeTable.columnAtPoint(e.getPoint());

                if (row >= 0 && column == 1) {
                    Object value = itemTypeTable.getValueAt(row, column);
                    if (value != null){
                        String description = value.toString();
                        showDescriptionDialog(description);
                    }

                }
            }
        });
    }

    // Memisahkan pembuatan navbar
    private JPanel createNavbar() {
        JPanel navbar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton("Logout");
        JButton profileButton = new JButton("Profil");


        // Tambahkan ActionListener untuk tombol Profil
        profileButton.addActionListener(e -> mainFrame.showProfile());

        // Logout Button ActionListener
        logoutButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Apakah anda ingin logout?",
                    "Konfirmasi Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if(result == JOptionPane.YES_OPTION) {
                mainFrame.showLogin();
            }
        });

        profileButton.setBackground(new Color(0x187824));
        profileButton.setForeground(Color.WHITE);
        profileButton.setFocusPainted(false);
        profileButton.setOpaque(true);

        logoutButton.setBackground(new Color(0x80251A));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setOpaque(true);


        navbar.add(profileButton);
        navbar.add(logoutButton);
        navbar.setBackground(new Color(0x66986C));
        return navbar;
    }

    // Memisahkan pembuatan panel kategori
    private JPanel createCategoryPanel() {
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.X_AXIS));
        categoryPanel.setOpaque(false);
        return categoryPanel;
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        add(navbar, BorderLayout.NORTH);

        JPanel mainContentPanel = new JPanel(new BorderLayout());

        JPanel itemDisplayPanel = createItemPanel();

        mainContentPanel.add(itemDisplayPanel, BorderLayout.CENTER);
        add(mainContentPanel, BorderLayout.CENTER);
        add(createTitlePanel(),BorderLayout.SOUTH);
    }
    private JPanel createTitlePanel(){
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(0x66986C));
        titlePanel.add(titleLabel,BorderLayout.CENTER);

        return titlePanel;
    }


    private JPanel createItemPanel() {
        JPanel itemPanel = new JPanel(new BorderLayout());

        // Wrap tabel dan gambar dalam panel
        JPanel tablePanel = new JPanel(new BorderLayout());

        JScrollPane tableScrollPane = new JScrollPane(itemTypeTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        // Add categoryPanel at the SOUTH of tablePanel to position it at the bottom
        JScrollPane categoryScrollPane = new JScrollPane(categoryPanel);
        categoryScrollPane.setPreferredSize(new Dimension(400, 100));
        tablePanel.add(categoryScrollPane, BorderLayout.SOUTH);

        tablePanel.setBackground(new Color(0x66986C));
        itemPanel.add(tablePanel, BorderLayout.CENTER);

        itemPanel.setBackground(new Color(0x66986C));

        return itemPanel; // return itemPanel, bukan JScrollPane
    }


    private void loadCategories() {
        List<Kategori> categories = categoryController.getAllCategories();
        categoryPanel.removeAll();
        for (Kategori category : categories) {
            JButton categoryButton = new JButton(category.getName());
            categoryButton.addActionListener(e -> loadItemTypes(category.getId()));
            categoryButton.setFont(new Font("Sans-Serif", Font.PLAIN, 10)); // set small font
            categoryButton.setFocusPainted(false); // remove focus border
            categoryButton.setBackground(new Color(0x187824)); // Set background color
            categoryButton.setForeground(Color.WHITE); // Set text color
            categoryButton.setOpaque(true); // Make button opaque

            // Create a wrapper panel
            JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
            wrapperPanel.add(categoryButton);
            categoryButton.setPreferredSize(new Dimension(135, 40)); // set smaller size
            categoryButton.setMaximumSize(new Dimension(135,40)); //set max size
            wrapperPanel.setOpaque(false);
            categoryPanel.add(wrapperPanel);
        }
    }

    private void loadDefaultCategoryItems() {
        Kategori besarCategory = null;
        List<Kategori> categories = categoryController.getAllCategories();

        for (Kategori category : categories) {
            if (category.getName().equals("Besar")) {
                besarCategory = category;
                break;
            }
        }
        if(besarCategory != null) {
            loadItemTypes(besarCategory.getId());
        } else {
            JOptionPane.showMessageDialog(this,"Kategori Besar tidak ditemukan","Error", JOptionPane.ERROR_MESSAGE);
        }
        loadCategories();

    }

    //  loadItemTypes
    private void loadItemTypes(int categoryId) {
        List<JenisSampah> itemTypes = itemTypeController.getByCategoryId(categoryId);
        itemTypeTableModel.setRowCount(0); // Clear table
        if (itemTypes == null || itemTypes.isEmpty()) {
            imageLabel.setIcon(null);
            imageLabel.setText("No Item in This Category");
            return;
        }
        for (JenisSampah itemType : itemTypes) {
            itemTypeTableModel.addRow(new Object[]{itemType.getName(), itemType.getDescription()}); // Only add name and description
        }
        imageLabel.setIcon(null);
        imageLabel.setText("");
    }


    private void showDescriptionDialog(String description) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(parentFrame, "Description", true);
        JTextArea textArea = new JTextArea(description);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        dialog.add(panel);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private static class TextAreaRenderer extends JTextArea implements TableCellRenderer {

        public TextAreaRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }
            setFont(table.getFont());
            if (value != null) {
                setText(value.toString());
            } else {
                setText("");
            }
            return this;
        }
    }
}