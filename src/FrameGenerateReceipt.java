import javax.swing.*;
import java.awt.*;

public class FrameGenerateReceipt {

    public FrameGenerateReceipt() {

        //frame
        JFrame frame = new JFrame("Generate Receipt");
        frame.setSize(600, 700);
        frame.setLayout(null);

        //label
        JLabel title = new JLabel("Generate Receipt");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color (0x2d6a4f));
        title.setBounds(200, 30, 300, 30);

        
        JLabel l1 = new JLabel("Payment ID:");
        l1.setForeground(Color.black);
        l1.setBounds(100, 100, 150, 30);

        //textfield
        JTextField t1 = new JTextField();
        t1.setBounds(250, 100, 200, 30);

        //textarea - in paragraph
        JTextArea area = new JTextArea();
        area.setBounds(100, 200, 350, 300);
        area.setEditable(false);
        area.setBackground(Color.LIGHT_GRAY);

        //button
        JButton generateBtn = new JButton("Generate");
        generateBtn.setBounds(200, 150, 120, 30);


        generateBtn.addActionListener(e -> {

            if (t1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Enter Payment ID!");
                return;
            }

            int paymentId;

            try {
                paymentId = Integer.parseInt(t1.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Payment ID must be a number!");
                return;
            }

            ManagePayment foundPayment = null;

            for (ManagePayment p : DataStored.payments) {

                if (p.payment_id == paymentId) {
                    foundPayment = p;
                    break;
                }
            }

            if (foundPayment == null) {
                JOptionPane.showMessageDialog(frame, "Payment not found!");
                return;
            }

            area.setText(foundPayment.toString());
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