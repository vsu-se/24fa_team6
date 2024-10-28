package CS4321;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AuctionSystemGUI extends JFrame {
    private CategoryController categoryController;
    private CommissionController commissionController;
    private BuyerPremiumController buyerPremiumController;
    private DefaultListModel<String> categoryListModel;
    private ItemController itemController;
    private DefaultListModel<String> itemListModel;

    public AuctionSystemGUI() {
        // Initialize controllers
        categoryController = new CategoryController(new ArrayList<>());
        commissionController = new CommissionController(new Commission(0));
        buyerPremiumController = new BuyerPremiumController(new BuyerPremium(0));
        itemController = new ItemController(new ArrayList<>());

        // Set up the frame
        setTitle("Auction System");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create components
        JTextField categoryNameField = new JTextField(15);
        JButton addCategoryButton = new JButton("Add Category");
        JTextField commissionField = new JTextField(15);
        JButton setCommissionButton = new JButton("Set Commission");
        JTextField buyerPremiumField = new JTextField(15);
        JButton setBuyerPremiumButton = new JButton("Set Buyer Premium");
        JTextField itemNameField = new JTextField(15);
        JTextField itemPriceField = new JTextField(10);
        JTextField itemEndDateField = new JTextField(10);
        JTextField itemShippingCostField = new JTextField(10);
        JButton addItemButton = new JButton("Add Item");

        // List model to display categories
        categoryListModel = new DefaultListModel<>();
        itemListModel = new DefaultListModel<>();
        JList<String> categoryList = new JList<>(categoryListModel);
        JList<String> itemList = new JList<>(itemListModel);
        JScrollPane scrollPane = new JScrollPane(categoryList);
        JScrollPane itemScrollPane = new JScrollPane(itemList);

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

        setBuyerPremiumButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    double newBuyerPremium = Double.parseDouble(buyerPremiumField.getText());
                    buyerPremiumController.setBuyerPremium(newBuyerPremium);
                    buyerPremiumField.setText("");
                    JOptionPane.showMessageDialog(null, "Buyer Premium set to: " + newBuyerPremium);
                }
                catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                }
            }
        });

        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    String itemName = itemNameField.getText();
                    double startingPrice = Double.parseDouble(itemPriceField.getText());
                    LocalDate endDate = LocalDate.parse(itemEndDateField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    double shippingCost = Double.parseDouble(itemShippingCostField.getText());
                    itemController.listItem(itemName, startingPrice, endDate, shippingCost);
                    itemListModel.addElement("Item: " + itemName + ", Starting Price: $" + startingPrice + ", End Date: " + endDate + ", Shipping Cost: $" + shippingCost);
                    itemNameField.setText("");
                    itemPriceField.setText("");
                    itemEndDateField.setText("");
                    itemShippingCostField.setText("");
                }
                catch (NumberFormatException | java.time.format.DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid values for all item fields.");
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

        JPanel buyerPremiumPanel = new JPanel();
        buyerPremiumPanel.add(new JLabel("Buyer Premium:"));
        buyerPremiumPanel.add(buyerPremiumField);
        buyerPremiumPanel.add(setBuyerPremiumButton);

        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new GridLayout(5, 2));
        itemPanel.add(new JLabel("Item Name:"));
        itemPanel.add(itemNameField);
        itemPanel.add(new JLabel("Starting Price:"));
        itemPanel.add(itemPriceField);
        itemPanel.add(new JLabel("End Date (yyyy-MM-dd):"));
        itemPanel.add(itemEndDateField);
        itemPanel.add(new JLabel("Shipping Cost:"));
        itemPanel.add(itemShippingCostField);
        itemPanel.add(addItemButton);

        // Add components to the frame
        add(scrollPane, BorderLayout.WEST);
        add(itemScrollPane,BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(commissionPanel, BorderLayout.SOUTH);
        add(buyerPremiumPanel, BorderLayout.EAST);
        add(itemPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AuctionSystemGUI frame = new AuctionSystemGUI();
            frame.setVisible(true);
        });
    }


}
