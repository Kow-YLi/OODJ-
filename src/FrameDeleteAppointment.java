import javax.swing.*;
import java.awt.*;

public class FrameDeleteAppointment {

    public FrameDeleteAppointment() {

        //frame
        JFrame frame = new JFrame("Delete Appointment Frame");
        frame.setSize(600, 700);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.white);

        //label
        JLabel title = new JLabel("Enter Appointment Details");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(0x2d6a4f));
        title.setBounds(120, 10, 400, 50);

        JLabel l1 = new JLabel("Customer ID:");
        l1.setBounds(100, 80, 150, 30);

        //textfield
        JTextField t1 = new JTextField();
        t1.setBounds(250, 80, 200, 30);

        //button
        JButton delete = new JButton("Delete");
        delete.setBounds(200, 150, 120, 30);

        frame.add(title);
        frame.add(l1);
        frame.add(t1);
        frame.add(delete);

        delete.addActionListener(e -> {

            if (t1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter Appointment ID!");
                return;
            }

            int appId;

            try {
                appId = Integer.parseInt(t1.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Appointment ID must be a number!");
                return;
            }

            boolean found = false;

            for (int i = 0; i < DataStored.appointments.size(); i++) {

                if (DataStored.appointments.get(i).customer.cus_id == appId) {

                    DataStored.appointments.remove(i);

                    JOptionPane.showMessageDialog(frame, "Appointment Deleted!");
                    found = true;
                    break;
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(frame, "Appointment not found!");
            }
        });
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
} 