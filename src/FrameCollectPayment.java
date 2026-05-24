import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FrameCollectPayment {

    public FrameCollectPayment() {
        
        //frame
        JFrame frame = new JFrame("Payment Collection Frame");
        frame.setSize(600, 700);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.white);

        //label
        JLabel title = new JLabel("Collect Payment");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(0x2d6a4f));
        title.setBounds(200, 30, 300, 30);
        frame.add(title);

        JLabel l1 = new JLabel("Appointment ID:");
        JLabel l2 = new JLabel("Amount (RM):");
        JLabel l3 = new JLabel("Payment Method:");

        l1.setBounds(100, 100, 150, 30);
        l2.setBounds(100, 150, 150, 30);
        l3.setBounds(100, 200, 150, 30);

        frame.add(l1);
        frame.add(l2);
        frame.add(l3);

        //text field
        JTextField t1 = new JTextField();
        JTextField t2 = new JTextField();
        JTextField t3 = new JTextField();

        t1.setBounds(250, 100, 200, 30);
        t2.setBounds(250, 150, 200, 30);
        t3.setBounds(250, 200, 200, 30);

        frame.add(t1);
        frame.add(t2);
        frame.add(t3);

        //button
        JButton payBtn = new JButton("Collect Payment");
        payBtn.setBounds(200, 280, 160, 40);
        frame.add(payBtn);

        payBtn.addActionListener(e -> {

            if (t1.getText().isEmpty() ||
                t2.getText().isEmpty() ||
                t3.getText().isEmpty()) {

                JOptionPane.showMessageDialog(frame, "Please fill all fields!");
                return;
            }

            String appIdInput = t1.getText().trim();
            double amount;

            try {
                amount = Double.parseDouble(t2.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid number format for Amount!");
                return;
            }

            String method = t3.getText().trim();

            File appointmentFile = new File("data/appointments.txt");
            if (!appointmentFile.exists()) {
                JOptionPane.showMessageDialog(frame, "Database error: Appointments ledger file not found!");
                return;
            }

            List<String> appointmentLines = new ArrayList<>();
            boolean appointmentUpdated = false;
            String matchedLineDetails = "";

            // Step 1: Scan and update the status inside appointments.txt file
            try (BufferedReader br = new BufferedReader(new FileReader(appointmentFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length >= 6 && parts[0].trim().equalsIgnoreCase(appIdInput)) {
                        // Layout: ID:CusID:Service:Status:Timestamp:TechID
                        parts[3] = "PAID"; 
                        String updatedLine = String.join(":", parts);
                        appointmentLines.add(updatedLine);
                        appointmentUpdated = true;
                        matchedLineDetails = line;
                    } else {
                        appointmentLines.add(line);
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error accessing appointments data storage.");
                return;
            }

            if (!appointmentUpdated) {
                JOptionPane.showMessageDialog(frame, "Appointment ID not found!");
                return;
            }

            // Rewrite appointments ledger with updated PAID flag status
            try (FileWriter fw = new FileWriter(appointmentFile, false)) {
                for (String line : appointmentLines) {
                    fw.write(line + "\n");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error modifying appointments file status.");
                return;
            }

            // Step 2: Auto-calculate tracking ID numbers sequentially by scanning payments log
            int maxPaymentId = 2000;
            File paymentFile = new File("data/payments.txt");
            
            if (paymentFile.exists()) {
                try (BufferedReader brPay = new BufferedReader(new FileReader(paymentFile))) {
                    String line;
                    while ((line = brPay.readLine()) != null) {
                        String[] parts = line.split(":");
                        if (parts.length > 0 && parts[0].startsWith("P")) {
                            try {
                                int idNum = Integer.parseInt(parts[0].substring(1).trim());
                                if (idNum > maxPaymentId) {
                                    maxPaymentId = idNum;
                                }
                            } catch (NumberFormatException ignored) {}
                        }
                    }
                } catch (IOException ignored) {}
            }

            String nextPaymentIdStr = "P" + (maxPaymentId + 1);
            String nextReceiptNoStr = "REC" + (maxPaymentId + 5001);

            // Step 3: Append structural billing summary row to data/payments.txt
            try (FileWriter fwPay = new FileWriter("data/payments.txt", true)) {
                // Scheme layout format: PaymentID:ReceiptNo:AppointmentID:Amount:PaymentMethod
                fwPay.write(nextPaymentIdStr + ":" + nextReceiptNoStr + ":" + appIdInput + ":" + amount + ":" + method + "\n");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error appending entry data into billing data file records.");
                return;
            }

            JOptionPane.showMessageDialog(frame,
                    "Payment Successful!\n\n" +
                    "Payment ID: " + nextPaymentIdStr + "\n" +
                    "Receipt No: " + nextReceiptNoStr + "\n" +
                    "Appointment ID: " + appIdInput + "\n" +
                    "Amount: RM " + amount + "\n" +
                    "Method: " + method
            );
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}