import javax.swing.*;
import java.awt.*;

public class Profile extends JFrame {
    private Customer customer;
    private CustomerDashboard dashboard;
    
    public Profile(Customer customer, CustomerDashboard dashboard) {
        this.customer = customer;
        this.dashboard = dashboard;
        setupUI();
    }
    
    private void setupUI() {
        setTitle("Edit My Profile");
        setSize(650, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(ThemeColors.WHITE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        main.setBackground(ThemeColors.WHITE);

        JLabel titleLabel = new JLabel("EDIT MY PROFILE", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(ThemeColors.DEEP_BLUE);
        main.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ThemeColors.BLUE),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        formPanel.add(new JLabel("Customer ID:"));
        JTextField idField = new JTextField(customer.getUsername());
        idField.setEditable(false);
        idField.setBackground(new Color(230, 230, 230));
        formPanel.add(idField);

        formPanel.add(new JLabel("Full Name:"));
        JTextField nameField = new JTextField(customer.getName());
        formPanel.add(nameField);

        formPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField(customer.getEmail());
        formPanel.add(emailField);

        formPanel.add(new JLabel("Phone Number:"));
        JTextField phoneField = new JTextField(customer.getPhone());
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Address:"));
        JTextArea addressArea = new JTextArea(customer.getAddress(), 3, 20);
        addressArea.setLineWrap(true);
        JScrollPane addressScroll = new JScrollPane(addressArea);
        formPanel.add(addressScroll);

        formPanel.add(new JLabel("Vehicle Model:"));
        JTextField vehicleField = new JTextField(customer.getVehicleModel());
        formPanel.add(vehicleField);

        formPanel.add(new JLabel("Vehicle Plate:"));
        JTextField plateField = new JTextField(customer.getVehiclePlate());
        formPanel.add(plateField);

        formPanel.add(new JSeparator());
        formPanel.add(new JSeparator());

        formPanel.add(new JLabel("Current Password:"));
        JTextField currentPassField = new JTextField();
        formPanel.add(currentPassField);

        formPanel.add(new JLabel("New Password:"));
        JTextField newPassField = new JTextField();
        formPanel.add(newPassField);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        main.add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setBackground(ThemeColors.WHITE);

        JButton saveBtn = new JButton("Save Changes");
        saveBtn.setPreferredSize(new Dimension(150, 35));
        saveBtn.setBackground(ThemeColors.FOREST_GREEN);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setOpaque(true);
        saveBtn.setBorderPainted(false);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setPreferredSize(new Dimension(150, 35));
        cancelBtn.setBackground(ThemeColors.DARK_GRAY);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setOpaque(true);
        cancelBtn.setBorderPainted(false);

        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);
        main.add(btnPanel, BorderLayout.SOUTH);

        saveBtn.addActionListener(e -> {
            String newName = nameField.getText().trim();
            String newEmail = emailField.getText().trim();
            String newPhone = phoneField.getText().trim();
            String newAddress = addressArea.getText().trim();
            String newVehicle = vehicleField.getText().trim();
            String newPlate = plateField.getText().trim();
            String currentPass = currentPassField.getText().trim();
            String newPass = newPassField.getText().trim();

            if (newName.isEmpty() || newEmail.isEmpty() || newPhone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields!");
                return;
            }
            if (!newEmail.contains("@")) {
                JOptionPane.showMessageDialog(this, "Invalid email address!");
                return;
            }
            if (!newPhone.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Phone number must contain only digits!");
                return;
            }

            if (!newPass.isEmpty()) {
                if (currentPass.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter current password!");
                    return;
                }
                if (!currentPass.equals(customer.getPassword())) {
                    JOptionPane.showMessageDialog(this, "Current password is incorrect!");
                    return;
                }
                if (newPass.length() < 4) {
                    JOptionPane.showMessageDialog(this, "Password must be at least 4 characters!");
                    return;
                }
                customer.setPassword(newPass);
            }

            customer.setName(newName);
            customer.setEmail(newEmail);
            customer.setPhone(newPhone);
            customer.setAddress(newAddress);
            customer.setVehicleModel(newVehicle);
            customer.setVehiclePlate(newPlate);
            customer.saveToFile();

            JOptionPane.showMessageDialog(this, "Profile updated successfully!");
            
            currentPassField.setText("");
            newPassField.setText("");
            
            dispose();
            dashboard.setVisible(true);
        });

        cancelBtn.addActionListener(e -> {
            dispose();
            dashboard.setVisible(true);
        });

        add(main);
        setVisible(true);
    }
}