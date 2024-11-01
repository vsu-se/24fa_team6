package CS4321;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AuctionSystemGUI extends JFrame {
    private CategoryController categoryController;
    private CommissionController commissionController;
    private BuyerPremiumController buyerPremiumController;
    private AuctionController auctionController;
    private ItemController itemController;

    private DefaultListModel<String> categoryListModel;
    private DefaultListModel<String> itemListModel;
    private DefaultListModel<String> activeAuctionListModel;
    private JComboBox<String> itemSelector;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public AuctionSystemGUI(AuctionController auctionController) {
        // Initialize controllers
        this.auctionController = auctionController;
        categoryController = new CategoryController(new ArrayList<>());
        commissionController = new CommissionController(new Commission(0));
        buyerPremiumController = new BuyerPremiumController(new BuyerPremium(0));
        itemController = new ItemController(new ArrayList<>());

        // Set up the frame
        setTitle("Auction System");
        setSize(800, 600);
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

        // List models to display categories and items
        categoryListModel = new DefaultListModel<>();
        itemListModel = new DefaultListModel<>();
        activeAuctionListModel = new DefaultListModel<>();
        JList<String> categoryList = new JList<>(categoryListModel);
        JList<String> itemList = new JList<>(itemListModel);
        JList<String> activeAuctionList = new JList<>(activeAuctionListModel);

        JScrollPane categoryScrollPane = new JScrollPane(categoryList);
        JScrollPane itemScrollPane = new JScrollPane(itemList);
        JScrollPane activeAuctionScrollPane = new JScrollPane(activeAuctionList);

        // Sorting options for auctions
        JComboBox<String> sortOptions = new JComboBox<>(new String[] {
                "Sort by Name", "Sort by Starting Price", "Sort by End Date"
        });
        JButton refreshButton = new JButton("Refresh List");

        // Bidding components
        itemSelector = new JComboBox<>();
        JTextField bidAmountField = new JTextField(10);
        JTextField bidderNameField = new JTextField(10);
        JButton placeBidButton = new JButton("Place Bid");

        // Action listeners
        addCategoryButton.addActionListener(e -> handleAddCategory(categoryNameField));
        setCommissionButton.addActionListener(e -> handleSetCommission(commissionField));
        setBuyerPremiumButton.addActionListener(e -> handleSetBuyerPremium(buyerPremiumField));
        addItemButton.addActionListener(e -> handleAddItem(itemNameField, itemPriceField, itemEndDateField, itemShippingCostField));
        refreshButton.addActionListener(e -> handleRefreshList(sortOptions));
        placeBidButton.addActionListener(e -> handlePlaceBid(bidAmountField, bidderNameField));

        // Organize Panels
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

        JPanel itemPanel = new JPanel(new GridLayout(5, 2));
        itemPanel.add(new JLabel("Item Name:"));
        itemPanel.add(itemNameField);
        itemPanel.add(new JLabel("Starting Price:"));
        itemPanel.add(itemPriceField);
        itemPanel.add(new JLabel("End Date (yyyy-MM-dd):"));
        itemPanel.add(itemEndDateField);
        itemPanel.add(new JLabel("Shipping Cost:"));
        itemPanel.add(itemShippingCostField);
        itemPanel.add(addItemButton);

        // Sorting panel
        JPanel sortPanel = new JPanel();
        sortPanel.add(new JLabel("Sort Auctions By:"));
        sortPanel.add(sortOptions);
        sortPanel.add(refreshButton);

        // Bidding panel
        JPanel biddingPanel = new JPanel();
        biddingPanel.add(new JLabel("Select Item:"));
        biddingPanel.add(itemSelector);
        biddingPanel.add(new JLabel("Bid Amount:"));
        biddingPanel.add(bidAmountField);
        biddingPanel.add(new JLabel("Bidder Name:"));
        biddingPanel.add(bidderNameField);
        biddingPanel.add(placeBidButton);

        // Control panel to hold other panels
        JPanel controlPanel = new JPanel(new GridLayout(5, 1));
        controlPanel.add(inputPanel);
        controlPanel.add(commissionPanel);
        controlPanel.add(buyerPremiumPanel);
        controlPanel.add(sortPanel);
        controlPanel.add(biddingPanel);

        // Add components to frame
        add(categoryScrollPane, BorderLayout.WEST);
        add(itemScrollPane, BorderLayout.CENTER);
        add(activeAuctionScrollPane, BorderLayout.EAST);
        add(controlPanel, BorderLayout.NORTH);
        add(itemPanel, BorderLayout.SOUTH);
    }

    // Action handling methods
    private void handleAddCategory(JTextField categoryNameField) {
        String categoryName = categoryNameField.getText().trim();
        if (!categoryName.isEmpty()) {
            if (categoryController.getCategories().stream().anyMatch(cat -> cat.getName().equalsIgnoreCase(categoryName))) {
                JOptionPane.showMessageDialog(this, "Category already exists.");
            } else {
                categoryController.addCategory(categoryName);
                categoryListModel.addElement(categoryName);
                categoryNameField.setText("");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Category name cannot be empty");
        }
    }

    private void handleSetCommission(JTextField commissionField) {
        try {
            double newCommission = Double.parseDouble(commissionField.getText().trim());
            commissionController.setSellerCommission(newCommission);
            commissionField.setText("");
            JOptionPane.showMessageDialog(this, "Commission set to: " + newCommission);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for the commission");
        }
    }

    private void handleSetBuyerPremium(JTextField buyerPremiumField) {
        try {
            double newBuyerPremium = Double.parseDouble(buyerPremiumField.getText().trim());
            buyerPremiumController.setBuyerPremium(newBuyerPremium);
            buyerPremiumField.setText("");
            JOptionPane.showMessageDialog(this, "Buyer Premium set to: " + newBuyerPremium);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for the buyer premium");
        }
    }

    private void handleAddItem(JTextField itemNameField, JTextField itemPriceField, JTextField itemEndDateField, JTextField itemShippingCostField) {
        try {
            String itemName = itemNameField.getText().trim();
            double startingPrice = Double.parseDouble(itemPriceField.getText().trim());
            LocalDate endDate = LocalDate.parse(itemEndDateField.getText().trim(), DATE_FORMAT);
            double shippingCost = Double.parseDouble(itemShippingCostField.getText().trim());

            itemController.listItem(itemName, startingPrice, endDate, shippingCost);
            itemListModel.addElement("Item: " + itemName + ", Starting Price: $" + startingPrice + ", End Date: " + endDate + ", Shipping Cost: $" + shippingCost);
            itemSelector.addItem(itemName); // Add item to bidding selector

            itemNameField.setText("");
            itemPriceField.setText("");
            itemEndDateField.setText("");
            itemShippingCostField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numerical values for price and shipping cost");
        } catch (java.time.format.DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Please enter the date in yyyy-MM-dd format");
        }
    }

    private void handleRefreshList(JComboBox<String> sortOptions) {
        List<Item> items = auctionController.getActiveAuction(); // Get only active items

        // Sort based on selected option
        switch ((String) sortOptions.getSelectedItem()) {
            case "Sort by Name":
                items.sort(Comparator.comparing(Item::getName));
                break;
            case "Sort by Starting Price":
                items.sort(Comparator.comparing(Item::getStartingPrice));
                break;
            case "Sort by End Date":
                items.sort(Comparator.comparing(Item::getEndDate));
                break;
        }

        // Update the active auction list model
        activeAuctionListModel.clear();
        for (Item item : items) {
            activeAuctionListModel.addElement(item.toString());
        }
    }

    private void handlePlaceBid(JTextField bidAmountField, JTextField bidderNameField) {
        // Check if auctionController is initialized
        if (this.auctionController == null) {
            System.out.println("AuctionController is null!");
            return; // Early return to avoid further processing
        }

        // Get the bid amount and bidder name from the text fields
        String bidderName = bidderNameField.getText();
        String bidAmountText = bidAmountField.getText();

        // Validate input
        if (bidderName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Bidder name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double bidAmount;
        try {
            bidAmount = Double.parseDouble(bidAmountText);
            if (bidAmount <= 0) {
                JOptionPane.showMessageDialog(null, "Bid amount must be greater than zero.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid bid amount. Please enter a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a new Bid object
        Bid bid = new Bid(bidderName, bidAmount);

        // Place the bid using the auction controller
        boolean success = auctionController.placeBid(bidderName, bid);

        // Provide feedback to the user
        if (success) {
            JOptionPane.showMessageDialog(null, "Bid placed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to place bid. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<Item> items = new ArrayList<>();
            AuctionController controller = new AuctionController(items);
            AuctionSystemGUI gui = new AuctionSystemGUI(controller);
            gui.setVisible(true);
        });
    }
}
