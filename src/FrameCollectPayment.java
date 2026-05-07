import javax.swing.*;
import java.awt.*;

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
        title.setForeground( new Color (0x2d6a4f));
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

            int appId;
            double amount;

            try {
                appId = Integer.parseInt(t1.getText());
                amount = Double.parseDouble(t2.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid number format!");
                return;
            }

            String method = t3.getText();

            CreateAppointment selected = null;

            for (CreateAppointment a : DataStored.appointments) {
                if (a.app_id == appId) {
                    selected = a;
                    break;
                }
            }

            if (selected == null) {
                JOptionPane.showMessageDialog(frame, "Appointment not found!");
                return;
            }

            ManagePayment payment = new ManagePayment(
                    DataStored.nextPaymentId++,
                    selected,
                    amount,
                    method
            );

            DataStored.payments.add(payment);
            selected.app_status = "PAID";

            JOptionPane.showMessageDialog(frame,
                    "Payment Successful!\n\n" +
                    "Payment ID: " + payment.payment_id + "\n" +
                    "Receipt No: " + payment.receipt_no + "\n" +
                    "Appointment ID: " + payment.appointment.app_id + "\n" +
                    "Amount: RM " + payment.payment_amount + "\n" +
                    "Method: " + payment.payment_method
            );
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}