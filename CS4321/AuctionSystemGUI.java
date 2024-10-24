package CS4321;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AuctionSystemGUI extends JFrame {
    private CategoryController categoryController;
    private CommissionController commissionController;
    private DefaultListModel<String> categoryListModel;

    public AuctionSystemGUI() {
        // Initialize controllers
        categoryController = new CategoryController(new ArrayList<>());
        commissionController = new CommissionController(new Commission(0));

        // Set up the frame
        setTitle("Auction System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create components
        JTextField categoryNameField = new JTextField(15);
        JButton addCategoryButton = new JButton("Add Category");
        JTextField commissionField = new JTextField(15);
        JButton setCommissionButton = new JButton("Set Commission");

        // List model to display categories
        categoryListModel = new DefaultListModel<>();
        JList<String> categoryList = new JList<>(categoryListModel);
        JScrollPane scrollPane = new JScrollPane(categoryList);

        // Action for adding categories
        addCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String categoryName = categoryNameField.getText();
                if (!categoryName.isEmpty()) {
                    categoryController.addCategory(categoryName);
                    categoryListModel.addElement(categoryName);
                    categoryNameField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Category name cannot be empty");
                }
            }
        });

        // Action for setting commission
        setCommissionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double newCommission = Double.parseDouble(commissionField.getText());
                    commissionController.setSellerCommission(newCommission);
                    commissionField.setText("");
                    JOptionPane.showMessageDialog(null, "Commission set to: " + newCommission);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number");
                }
            }
        });

        // Set up panels
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Category Name:"));
        inputPanel.add(categoryNameField);
        inputPanel.add(addCategoryButton);

        JPanel commissionPanel = new JPanel();
        commissionPanel.add(new JLabel("Seller Commission:"));
        commissionPanel.add(commissionField);
        commissionPanel.add(setCommissionButton);

        // Add components to the frame
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(commissionPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AuctionSystemGUI frame = new AuctionSystemGUI();
            frame.setVisible(true);
        });
    }


}
