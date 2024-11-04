package CS4321;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AuctionSystemGUI extends JFrame {
    private CategoryController categoryController;
    private CommissionController commissionController;
    private BuyerPremiumController buyerPremiumController;
    private ItemController itemController;
    private AuctionController auctionController;
    private String currentUser = "";

    public AuctionSystemGUI() {
        // Initialize controllers
        categoryController = new CategoryController(new ArrayList<>());
        commissionController = new CommissionController(new Commission(0.0));
        buyerPremiumController = new BuyerPremiumController(new BuyerPremium(0.0));
        itemController = new ItemController(new ArrayList<>());
        auctionController = new AuctionController(itemController.getItems());

        // Scheduled service to check on auctions.
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> auctionController.checkAndEndAuctions(), 0, 10, TimeUnit.SECONDS); // This would be changed to a higher interval; 5-15 minutes

        // Set up the main frame

        setTitle("Auction System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Add panels for each user role
        tabbedPane.add("Admin Panel", createAdminPanel());
        tabbedPane.add("Seller Panel", createSellerPanel());
        tabbedPane.add("Auction Viewer", createAuctionViewerPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createAdminPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        // Category Management
        JPanel categoryPanel = new JPanel();
        categoryPanel.setBorder(BorderFactory.createTitledBorder("Add Category"));
        JTextField categoryNameField = new JTextField(20);
        JButton addCategoryButton = new JButton("Add Category");
        addCategoryButton.addActionListener((ActionEvent e) -> {
            String categoryName = categoryNameField.getText().trim();
            if (!categoryName.isEmpty()) {
                if (categoryController.getCategories().stream().anyMatch(cat -> cat.getName().equalsIgnoreCase(categoryName))) {
                    JOptionPane.showMessageDialog(this, "Category '" + categoryName + "' already exists.");
                } else {
                    categoryController.addCategory(categoryName);
                    JOptionPane.showMessageDialog(this, "Category added: " + categoryName);
                    categoryNameField.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Category name cannot be empty.");
            }
        });
        categoryPanel.add(new JLabel("Category Name:"));
        categoryPanel.add(categoryNameField);
        categoryPanel.add(addCategoryButton);

        // Commission Management
        JPanel commissionPanel = new JPanel();
        commissionPanel.setBorder(BorderFactory.createTitledBorder("Set Seller Commission"));
        JTextField commissionField = new JTextField(10);
        JButton setCommissionButton = new JButton("Set Commission");
        setCommissionButton.addActionListener((ActionEvent e) -> {
            try {
                double commission = Double.parseDouble(commissionField.getText());
                commissionController.setSellerCommission(commission);
                JOptionPane.showMessageDialog(this, "Seller commission set to: " + commission + "%");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.");
            }
        });
        commissionPanel.add(new JLabel("Commission (%):"));
        commissionPanel.add(commissionField);
        commissionPanel.add(setCommissionButton);

        // Buyer Premium Management
        JPanel buyerPremiumPanel = new JPanel();
        buyerPremiumPanel.setBorder(BorderFactory.createTitledBorder("Set Buyer Premium"));
        JTextField premiumField = new JTextField(10);
        JButton setPremiumButton = new JButton("Set Premium");
        setPremiumButton.addActionListener((ActionEvent e) -> {
            try {
                double premium = Double.parseDouble(premiumField.getText());
                buyerPremiumController.setBuyerPremium(premium);
                JOptionPane.showMessageDialog(this, "Buyer premium set to: " + premium + "%");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.");
            }
        });
        buyerPremiumPanel.add(new JLabel("Premium (%):"));
        buyerPremiumPanel.add(premiumField);
        buyerPremiumPanel.add(setPremiumButton);

        panel.add(categoryPanel);
        panel.add(commissionPanel);
        panel.add(buyerPremiumPanel);

        return panel;
    }

    private JPanel createSellerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Item Listing Panel
        JPanel listItemPanel = new JPanel();
        listItemPanel.setBorder(BorderFactory.createTitledBorder("List Item for Auction"));
        JTextField itemNameField = new JTextField(30);
        JTextField startingPriceField = new JTextField(10);
        JTextField endDateField = new JTextField(10); // Format: yyyy-mm-dd
        JTextField shippingCostField = new JTextField(10);
        JButton listButton = new JButton("List Item");
        listButton.addActionListener((ActionEvent e) -> {
            try {
                String itemName = itemNameField.getText();
                double startingPrice = Double.parseDouble(startingPriceField.getText());
                LocalDate endDate = LocalDate.parse(endDateField.getText());
                double shippingCost = Double.parseDouble(shippingCostField.getText());
                itemController.listItem(itemName, startingPrice, endDate, shippingCost);
                JOptionPane.showMessageDialog(this, "Item listed: " + itemName);
                itemNameField.setText("");
                startingPriceField.setText("");
                endDateField.setText("");
                shippingCostField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid item details.");
            }
        });
        listItemPanel.add(new JLabel("Name:"));
        listItemPanel.add(itemNameField);
        listItemPanel.add(new JLabel("Starting Price:"));
        listItemPanel.add(startingPriceField);
        listItemPanel.add(new JLabel("End Date (yyyy-mm-dd):"));
        listItemPanel.add(endDateField);
        listItemPanel.add(new JLabel("Shipping Cost:"));
        listItemPanel.add(shippingCostField);
        listItemPanel.add(listButton);

        // Seller's Auction List Panel
        JPanel myAuctionsPanel = new JPanel(new BorderLayout());
        myAuctionsPanel.setBorder(BorderFactory.createTitledBorder("My Auctions"));
        JTextArea auctionsTextArea = new JTextArea();
        auctionsTextArea.setEditable(false);
        JButton refreshButton = new JButton("Refresh My Auctions");
        refreshButton.addActionListener(e -> {
            List<Item> myAuctions = itemController.getMyAuctions();
            auctionsTextArea.setText("");
            for (Item item : myAuctions) {
                auctionsTextArea.append("Item: " + item.getName() + ", Price: $" + item.getStartingPrice() + ", End Date: " + item.getEndDate() + ", Active: " + item.isActive() + "\n");
            }
        });
        myAuctionsPanel.add(new JScrollPane(auctionsTextArea), BorderLayout.CENTER);
        myAuctionsPanel.add(refreshButton, BorderLayout.SOUTH);

        panel.add(listItemPanel, BorderLayout.NORTH);
        panel.add(myAuctionsPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createAuctionViewerPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Active Auctions Panel
        JTextArea activeAuctionsTextArea = new JTextArea();
        activeAuctionsTextArea.setEditable(false);
        JButton refreshAuctionsButton = new JButton("Show Active Auctions");
        refreshAuctionsButton.addActionListener(e -> {
            auctionController.checkAndEndAuctions();
            List<Item> activeAuctions = auctionController.getActiveAuctions();
            activeAuctionsTextArea.setText("");
            for (Item item : activeAuctions) {
                if (item.hasBidFromUser(currentUser)) {
                    activeAuctionsTextArea.append("** Item: " + item.getName() + ", Current Bid: $" + item.getHighestBid() + ", End Date: " + item.getEndDate() + ", High Bidder: " + item.getCurrentBidder() + "\n");
                } else {
                    activeAuctionsTextArea.append("    Item: " + item.getName() + ", Current Bid: $" + item.getHighestBid() + ", End Date: " + item.getEndDate() + ", High Bidder: " + item.getCurrentBidder() + "\n");
                }
            }
        });

        // Bidding Panel
        JPanel bidPanel = new JPanel();
        bidPanel.setBorder(BorderFactory.createTitledBorder("Place a Bid"));
        JTextField bidItemNameField = new JTextField(10);
        JTextField bidderNameField = new JTextField(10);
        JTextField bidAmountField = new JTextField(10);
        JButton placeBidButton = new JButton("Place Bid");
        placeBidButton.addActionListener(e -> {
            try {
                String itemName = bidItemNameField.getText();
                String bidderName = bidderNameField.getText();
                currentUser = bidderName;

                if (!auctionController.itemExists(itemName)) {
                    JOptionPane.showMessageDialog(this, "Item '" + itemName + "' not found.");
                }
                else {
                    double bidAmount = Double.parseDouble(bidAmountField.getText());
                    boolean success = auctionController.placeBid(itemName, new Bid(bidderName, bidAmount));
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Bid placed on " + itemName);
                    } else {
                        JOptionPane.showMessageDialog(this, "Bid failed. Either auction is closed or bid amount is too low.");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid bid details.");
            }
        });
        bidPanel.add(new JLabel("Item Name:"));
        bidPanel.add(bidItemNameField);
        bidPanel.add(new JLabel("Bidder Name:"));
        bidPanel.add(bidderNameField);
        bidPanel.add(new JLabel("Bid Amount:"));
        bidPanel.add(bidAmountField);
        bidPanel.add(placeBidButton);

        panel.add(new JScrollPane(activeAuctionsTextArea), BorderLayout.CENTER);
        panel.add(refreshAuctionsButton, BorderLayout.NORTH);
        panel.add(bidPanel, BorderLayout.SOUTH);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AuctionSystemGUI gui = new AuctionSystemGUI();
            gui.setVisible(true);
        });
    }
}
