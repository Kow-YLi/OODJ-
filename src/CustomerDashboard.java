import javax.swing.*;
import java.awt.*;

public class CustomerDashboard extends JFrame {
    private Customer customer;
    
    public CustomerDashboard(Customer customer) {
        this.customer = customer;
        showMainMenu();
    }
    
    private void showMainMenu() {
        setTitle("Customer Dashboard - ID: " + customer.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null); 
        setResizable(false);
        getContentPane().setBackground(ThemeColors.WHITE);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setBackground(ThemeColors.WHITE);
        
        // Welcome Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(ThemeColors.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel welcomeLabel = new JLabel("Welcome, " + customer.getName() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setForeground(ThemeColors.DEEP_BLUE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel idLabel = new JLabel("Customer ID: " + customer.getUsername());
        idLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        idLabel.setForeground(ThemeColors.DARK_GRAY);
        idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subLabel = new JLabel("PLEASE CHOOSE YOUR SERVICES BY CLICKING BUTTONS BELOW");
        subLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        subLabel.setForeground(ThemeColors.BLUE);
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(welcomeLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        headerPanel.add(idLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        headerPanel.add(subLabel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonsPanel.setBackground(ThemeColors.WHITE);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        
        JButton statusButton = new JButton("CHECK CAR STATUS");
        styleButton(statusButton, ThemeColors.BLUE);
        statusButton.addActionListener(e -> {
            dispose();
            new CarStatus(customer, this); 
        });
        
        JButton historyButton = new JButton("SERVICE AND PAYMENT HISTORY");
        styleButton(historyButton, ThemeColors.FOREST_GREEN);
        historyButton.addActionListener(e -> {
            dispose();
            new ServiceHistory(customer, this);
        });
        
        JButton staffCommentButton = new JButton("STAFF RATING AND COMMENT");
        styleButton(staffCommentButton, ThemeColors.RACING_RED);
        staffCommentButton.addActionListener(e -> {
            dispose();
            new StaffComment(customer, this);
        });
        
        JButton editProfileButton = new JButton("EDIT PROFILE");
        styleButton(editProfileButton, ThemeColors.DARK_GRAY);
        editProfileButton.addActionListener(e -> {
            dispose();
            new Profile(customer, this);
        });
        
        buttonsPanel.add(statusButton);
        buttonsPanel.add(historyButton);
        buttonsPanel.add(staffCommentButton);
        buttonsPanel.add(editProfileButton);
        
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
        
        // Logout Button Layout
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false); 
        bottomPanel.setBackground(ThemeColors.WHITE); 

        JButton logoutButton = new JButton("LOGOUT");
        logoutButton.setBackground(ThemeColors.RACING_RED);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setOpaque(true);
        logoutButton.setPreferredSize(new Dimension(120, 40));

        logoutButton.addActionListener(e -> {
            dispose();
            new Login(); 
        });

        bottomPanel.add(logoutButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(320, 80));
    }
}