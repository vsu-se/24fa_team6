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
        panel.setLayout(new GridLayout(6, 1));

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

        // Concluded Auctions
        JPanel concludedAuctionsPanel = new JPanel(new BorderLayout());
        concludedAuctionsPanel.setBorder(BorderFactory.createTitledBorder("Concluded Auctions"));
        JTextArea concludedAuctionsTextArea = new JTextArea();
        concludedAuctionsTextArea.setEditable(false);
        JButton refreshConcludedAuctionsButton = new JButton("Refresh Concluded Auctions");
        refreshConcludedAuctionsButton.addActionListener(e -> {
            List<Item> concludedAuctions = auctionController.getConcludedAuctions(); // List all ended auctions
            concludedAuctionsTextArea.setText("");
            for (Item item : concludedAuctions) {
                concludedAuctionsTextArea.append("Item: " + item.getName() + ", End Date: " + item.getEndDate() + ", Price: $" + item.getHighestBid() + ", High Bidder: " + item.getCurrentBidder() + "\n");
            }
        });
        concludedAuctionsPanel.add(new JScrollPane(concludedAuctionsTextArea), BorderLayout.CENTER);
        concludedAuctionsPanel.add(refreshConcludedAuctionsButton, BorderLayout.SOUTH);

        // Bid History
        JPanel bidHistoryPanel = new JPanel();
        bidHistoryPanel.setBorder(BorderFactory.createTitledBorder("View Bid History"));
        JTextField bidHistoryItemNameField = new JTextField(20);
        JButton viewBidHistoryButton = new JButton("View Bid History");
        viewBidHistoryButton.addActionListener((ActionEvent e) -> {
            String itemName = bidHistoryItemNameField.getText().trim();
            if (!itemName.isEmpty()) {
                if (auctionController.itemExists(itemName)) {
                    List<Bid> bidHistory = auctionController.getBidHistory(itemName);
                    StringBuilder bidHistoryText = new StringBuilder();
                    for (Bid bid : bidHistory) {
                        bidHistoryText.append("Bidder: ").append(bid.getBidderName()).append(", Amount: $").append(bid.getAmount()).append("\n");
                    }
                    JOptionPane.showMessageDialog(this, bidHistoryText.toString(), "Bid History for " + itemName, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Item '" + itemName + "' not found.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Item name cannot be empty.");
            }
        });
        bidHistoryPanel.add(new JLabel("Item Name:"));
        bidHistoryPanel.add(bidHistoryItemNameField);
        bidHistoryPanel.add(viewBidHistoryButton);

        // Set Current Date
        JPanel currentDatePanel = new JPanel();
        currentDatePanel.setBorder(BorderFactory.createTitledBorder("Set Current Date"));
        JTextField currentDateField = new JTextField(10); // Format: yyyy-mm-dd
        JButton setCurrentDateButton = new JButton("Set Current Date");
        setCurrentDateButton.addActionListener((ActionEvent e) -> {
            try {
                String currentDate = currentDateField.getText();
                auctionController.setTimeSetting(currentDate);
                JOptionPane.showMessageDialog(this, "Current date set to: " + currentDate);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid date in the format yyyy-mm-dd.");
            }
        });
        currentDatePanel.add(new JLabel("Current Date (yyyy-mm-dd) or 'live':"));
        currentDatePanel.add(currentDateField);
        currentDatePanel.add(setCurrentDateButton);

        panel.add(categoryPanel);
        panel.add(commissionPanel);
        panel.add(buyerPremiumPanel);
        panel.add(concludedAuctionsPanel);
        panel.add(bidHistoryPanel);
        panel.add(currentDatePanel);

        return panel;
    }

    private JScrollPane createSellerPanel() {
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

        // Seller's Report Panel
        JPanel reportPanel = new JPanel(new BorderLayout());
        reportPanel.setBorder(BorderFactory.createTitledBorder("Seller's Report"));
        JTextArea reportTextArea = new JTextArea();
        reportTextArea.setEditable(false);
        JButton generateReportButton = new JButton("Generate Report");
        generateReportButton.addActionListener(e -> {
            AuctionReport auctionReport = new AuctionReport(commissionController, buyerPremiumController, itemController, auctionController);
            String report = auctionReport.generateSellerReport();
            reportTextArea.setText(report);
        });
        reportPanel.add(new JScrollPane(reportTextArea), BorderLayout.CENTER);
        reportPanel.add(generateReportButton, BorderLayout.SOUTH);

        panel.add(listItemPanel, BorderLayout.NORTH);
        panel.add(myAuctionsPanel, BorderLayout.CENTER);
        panel.add(reportPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(panel);
        return scrollPane;
    }

    private JPanel createAuctionViewerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        // Active Auctions Panel
        JPanel activeAuctionsPanel = new JPanel(new BorderLayout());
        activeAuctionsPanel.setBorder(BorderFactory.createTitledBorder("Active Auctions"));
        JTextArea activeAuctionsTextArea = new JTextArea();
        activeAuctionsTextArea.setEditable(false);
        JButton refreshAuctionsButton = new JButton("Show Active Auctions");
        refreshAuctionsButton.addActionListener(e -> {
            auctionController.checkAndEndAuctions();
            List<Item> activeAuctions = auctionController.getActiveAuctions();
            activeAuctionsTextArea.setText("");
            for (Item item : activeAuctions) {
                if (item.hasBidFromUser(currentUser)) {
                    activeAuctionsTextArea.append("** Item: " + item.getName() + ", Current Bid: $" + item.getHighestBid() + ", End Date: " + item.getEndDate() + ", High Bidder: " + item.getCurrentBidder() + ", Time Remaining: " + item.getTimeRemaining(auctionController.getCurrentDate()).getDays() + " days" + "\n");
                } else {
                    activeAuctionsTextArea.append("    Item: " + item.getName() + ", Current Bid: $" + item.getHighestBid() + ", End Date: " + item.getEndDate() + ", High Bidder: " + item.getCurrentBidder() + "\n");
                }
            }
        });
        activeAuctionsPanel.add(new JScrollPane(activeAuctionsTextArea), BorderLayout.CENTER);
        activeAuctionsPanel.add(refreshAuctionsButton, BorderLayout.SOUTH);

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
                } else {
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

        // Buyer's Report Panel
        JPanel reportPanel = new JPanel(new BorderLayout());
        reportPanel.setBorder(BorderFactory.createTitledBorder("Buyer's Report"));
        JTextArea reportTextArea = new JTextArea();
        reportTextArea.setEditable(false);
        JButton generateReportButton = new JButton("Generate Report");
        generateReportButton.addActionListener(e -> {
            AuctionReport auctionReport = new AuctionReport(commissionController, buyerPremiumController, itemController, auctionController);
            String report = auctionReport.generateBuyerReport(currentUser);
            reportTextArea.setText(report);
        });
        reportPanel.add(new JScrollPane(reportTextArea), BorderLayout.CENTER);
        reportPanel.add(generateReportButton, BorderLayout.SOUTH);

        // Bid History Panel
        JPanel bidHistoryPanel = new JPanel();
        bidHistoryPanel.setBorder(BorderFactory.createTitledBorder("View Bid History"));
        JTextField bidHistoryItemNameField = new JTextField(20);
        JButton viewBidHistoryButton = new JButton("View Bid History");
        viewBidHistoryButton.addActionListener((ActionEvent e) -> {
            String itemName = bidHistoryItemNameField.getText().trim();
            if (!itemName.isEmpty()) {
                if (auctionController.itemExists(itemName)) {
                    List<Bid> bidHistory = auctionController.getBidHistory(itemName);
                    StringBuilder bidHistoryText = new StringBuilder();
                    for (Bid bid : bidHistory) {
                        bidHistoryText.append("Bidder: ").append(bid.getBidderName()).append(", Amount: $").append(bid.getAmount()).append("\n");
                    }
                    JOptionPane.showMessageDialog(this, bidHistoryText.toString(), "Bid History for " + itemName, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Item '" + itemName + "' not found.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Item name cannot be empty.");
            }
        });
        bidHistoryPanel.add(new JLabel("Item Name:"));
        bidHistoryPanel.add(bidHistoryItemNameField);
        bidHistoryPanel.add(viewBidHistoryButton);

        panel.add(activeAuctionsPanel);
        panel.add(bidPanel);
        panel.add(reportPanel);
        panel.add(bidHistoryPanel);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AuctionSystemGUI gui = new AuctionSystemGUI();
            gui.setVisible(true);
        });
    }
}
