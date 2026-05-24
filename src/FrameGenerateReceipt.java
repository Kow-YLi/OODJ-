import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FrameGenerateReceipt {

    public FrameGenerateReceipt() {

        //frame
        JFrame frame = new JFrame("Generate Receipt Frame");
        frame.setSize(600, 700);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.white);

        //label
        JLabel title = new JLabel("Generate Receipt");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(0x2d6a4f));
        title.setBounds(200, 30, 300, 30);

        JLabel l1 = new JLabel("Payment ID:");
        l1.setForeground(Color.black);
        l1.setBounds(100, 100, 150, 30);

        //textfield
        JTextField t1 = new JTextField();
        t1.setBounds(250, 100, 200, 30);

        //textarea - in paragraph
        JTextArea area = new JTextArea();
        area.setBounds(100, 200, 380, 350);
        area.setEditable(false);
        area.setBackground(new Color(245, 245, 245));
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));

        //button
        JButton generateBtn = new JButton("Generate");
        generateBtn.setBounds(200, 150, 120, 30);

        generateBtn.addActionListener(e -> {

            String paymentIdInput = t1.getText().trim();

            if (paymentIdInput.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Enter Payment ID!");
                return;
            }

            File paymentFile = new File("data/payments.txt");
            if (!paymentFile.exists()) {
                JOptionPane.showMessageDialog(frame, "Database error: Payments record file not found!");
                return;
            }

            boolean found = false;

            try (BufferedReader br = new BufferedReader(new FileReader(paymentFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    // Scheme format: PaymentID:ReceiptNo:AppointmentID:Amount:PaymentMethod
                    String[] parts = line.split(":");
                    if (parts.length >= 5 && parts[0].trim().equalsIgnoreCase(paymentIdInput)) {
                        
                        StringBuilder receiptBuilder = new StringBuilder();
                        receiptBuilder.append("========================================\n");
                        receiptBuilder.append("           OFFICIAL RECEIPT             \n");
                        receiptBuilder.append("========================================\n\n");
                        receiptBuilder.append(" Payment ID      : ").append(parts[0].trim()).append("\n");
                        receiptBuilder.append(" Receipt No      : ").append(parts[1].trim()).append("\n");
                        receiptBuilder.append(" Appointment ID  : ").append(parts[2].trim()).append("\n");
                        receiptBuilder.append("----------------------------------------\n");
                        receiptBuilder.append(" Total Amount    : RM ").append(parts[3].trim()).append("\n");
                        receiptBuilder.append(" Payment Method  : ").append(parts[4].trim()).append("\n");
                        receiptBuilder.append(" Status          : PAID\n\n");
                        receiptBuilder.append("========================================\n");
                        receiptBuilder.append("          Thank you for choosing us!    \n");
                        receiptBuilder.append("========================================");
                        
                        area.setText(receiptBuilder.toString());
                        found = true;
                        break;
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error reading database records.");
                return;
            }

            if (!found) {
                area.setText("");
                JOptionPane.showMessageDialog(frame, "Payment record not found!");
            }
        });
        
        frame.add(area);
        frame.add(title);
        frame.add(t1);
        frame.add(l1);
        frame.add(generateBtn);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}