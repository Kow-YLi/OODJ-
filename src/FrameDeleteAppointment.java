import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        // FIXED: Updated label text from "Customer ID:" to "Appointment ID:"
        JLabel l1 = new JLabel("Appointment ID:");
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

            String appId = t1.getText().trim();

            boolean found = false;
            List<String> remainingAppointments = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader("data/appointments.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                   
                    String[] parts = line.split(":");
                    if (parts.length > 0 && parts[0].trim().equals(appId)) {
                        found = true;
                    } else {
                        remainingAppointments.add(line);
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error reading appointment file!");
                return;
            }

            if (found) {
                try (FileWriter fw = new FileWriter("data/appointments.txt", false)) {
                    for (String appLine : remainingAppointments) {
                        fw.write(appLine + "\n");
                    }
                    JOptionPane.showMessageDialog(frame, "Appointment Deleted!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error updating appointment file!");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Appointment not found!");
            }
        });
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}