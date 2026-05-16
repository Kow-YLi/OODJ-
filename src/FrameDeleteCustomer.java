import javax.swing.*;
import java.awt.*;

public class FrameDeleteCustomer {

    public FrameDeleteCustomer() {

        //frame
        JFrame frame = new JFrame("Delete Customer Frame");
        frame.setSize(600, 700);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.white);

        //label
        JLabel title = new JLabel("Enter Details to Delete Profile");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(0x2d6a4f));
        title.setBounds(120, 10, 400, 50);
        
        JLabel idLabel = new JLabel("Customer ID:"); 
        idLabel.setBounds(100,80,150,30);

        //textfield
        JTextField t1 = new JTextField();
        t1.setBounds(200,80,200,30);

        //button
        JButton delete = new JButton("Delete");
        delete.setBounds(180, 150, 90, 30);

        frame.add(title);
        frame.add(idLabel);
        frame.add(t1);
        frame.add(delete);

        delete.addActionListener(e -> {

            //empty field check
            if (t1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Please enter Customer ID!");
                return;
            }

            int id;

            //format check
            try {
                id = Integer.parseInt(t1.getText());
            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(frame,
                        "Customer ID must be a number!");
                return;
            }

            boolean found = false;

            //delete customer
            for (int i = 0; i < DataStored.customers.size(); i++) {

                if (DataStored.customers.get(i).cus_id == id) {

                    DataStored.customers.remove(i);

                    JOptionPane.showMessageDialog(frame,
                            "Customer Deleted Successfully!");

                    found = true;
                    break;
                }
            }

            //customer not found
            if (!found) {
                JOptionPane.showMessageDialog(frame,
                        "Customer not found!");
            }
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}