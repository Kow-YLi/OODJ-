import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton loginButton;
    private JButton exitButton;
    private JButton forgotPasswordButton;

    public Login() {
        createLoginUI();
    }

    private void createLoginUI() {
        frame = new JFrame("APU Automotive Service Centre - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(248, 250, 212));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 15, 10, 15);

        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

        JLabel titleLabel = new JLabel("APU Automotive Service Centre");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(ThemeColors.DEEP_BLUE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 3;
        gbc.gridx = 0;

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(18);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeColors.BLUE, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        mainPanel.add(usernameField, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(18);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeColors.BLUE, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        mainPanel.add(passwordField, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 14)); 
        mainPanel.add(roleLabel, gbc);

        gbc.gridx = 1;
        String[] roles = {"Choose Your Role", "Manager", "Counter Staff", "Technician", "Customer"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        roleComboBox.setBackground(Color.WHITE);
        roleComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeColors.BLUE, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        mainPanel.add(roleComboBox, gbc);

        gbc.gridy = 6;
        gbc.gridx = 1;

        forgotPasswordButton = new JButton("Forgot Password?");
        forgotPasswordButton.setFont(new Font("Arial", Font.PLAIN, 12));
        forgotPasswordButton.setForeground(ThemeColors.BLUE);
        forgotPasswordButton.setContentAreaFilled(false);
        forgotPasswordButton.setBorderPainted(false);
        forgotPasswordButton.setFocusPainted(false);
        forgotPasswordButton.setCursor(handCursor);
        forgotPasswordButton.setHorizontalAlignment(SwingConstants.RIGHT);
        forgotPasswordButton.addActionListener(new ForgotPasswordAction());
        mainPanel.add(forgotPasswordButton, gbc);

        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        buttonPanel.setOpaque(false);

        loginButton = createStyledButton("Login", ThemeColors.FOREST_GREEN);
        exitButton = createStyledButton("Exit", ThemeColors.RACING_RED);

        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);
        mainPanel.add(buttonPanel, gbc);

        frame.add(mainPanel);

        loginButton.addActionListener(new LoginAction());
        exitButton.addActionListener(e -> System.exit(0));
        passwordField.addActionListener(e -> loginButton.doClick());

        frame.setVisible(true);
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(130, 42));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String role = (String) roleComboBox.getSelectedItem();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Please Enter Your Username and Password.",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            User user = null;

            switch (role) {
                case "Manager":
                    user = Manager.authenticate(username, password);
                    break;
                case "Counter Staff":
                    user = Staff.authenticate(username, password);
                    break;
                case "Technician":
                    user = Technician.authenticate(username, password);
                    break;
                case "Customer":
                    user = Customer.authenticate(username, password);
                    break;
            }

            if (user != null) {
                JOptionPane.showMessageDialog(frame,
                        "Login Successful!\nWelcome " + user.getName() + " (" + role + ")",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                user.showDashboard();
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Invalid Username, Password, or Role.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
            }
        }
    }

    private class ForgotPasswordAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String phone = JOptionPane.showInputDialog(frame,
                    "Enter your registered Phone Number:",
                    "Password Recovery",
                    JOptionPane.QUESTION_MESSAGE);

            if (phone == null || phone.trim().isEmpty()) return;
            phone = phone.trim();

            String fileFound = User.findFileByPhone(phone);

            if (fileFound == null) {
                JOptionPane.showMessageDialog(frame,
                        "Phone number not found in system database.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int otp = User.generateOTP();

            showSMS(phone, otp);

            String inputOTP = JOptionPane.showInputDialog(frame,
                    "Enter OTP:");

            if (inputOTP != null && inputOTP.trim().equals(String.valueOf(otp))) {

                String newPassword = JOptionPane.showInputDialog(frame,
                        "Enter New Password:");

                if (newPassword != null && !newPassword.trim().isEmpty()) {
                    boolean success = User.updatePasswordByPhone(fileFound, phone, newPassword.trim());
                    
                    if (success) {
                        JOptionPane.showMessageDialog(frame,
                                "Password Updated Successfully!");
                    } else {
                        JOptionPane.showMessageDialog(frame,
                                "Failed to update password.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (inputOTP != null) {
                JOptionPane.showMessageDialog(frame,
                        "Incorrect OTP.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        // SMS popup dialog
        private void showSMS(String phone, int otp) {
            JDialog d = new JDialog(frame, false);
            d.setUndecorated(true);
            d.setSize(360, 120);

            Dimension s = Toolkit.getDefaultToolkit().getScreenSize();
            d.setLocation(s.width - 380, s.height - 170);

            JPanel panel = new JPanel();
            panel.setBackground(new Color(20, 20, 20));
            panel.setLayout(new BorderLayout(10, 10));
            panel.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)));

            JTextArea area = new JTextArea(
                    "SECURITY VERIFICATION\n\n" +
                    "YOUR OTP NUMBER IS: " + otp + "\n\n" +
                    "Do not share this code with anyone."
            );

            area.setFont(new Font("Arial", Font.PLAIN, 13));
            area.setForeground(Color.WHITE);
            area.setBackground(new Color(20, 20, 20));
            area.setEditable(false);

            panel.add(area, BorderLayout.CENTER);

            d.add(panel);
            d.setVisible(true);

            Timer t = new Timer(8000, ev -> d.dispose());
            t.setRepeats(false);
            t.start();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Login();
        });
    }
}