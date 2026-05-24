import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class FrameCreateAppointment {

    public FrameCreateAppointment() {

        //frame
        JFrame frame = new JFrame("Create Appointment Frame");
        frame.setSize(600, 700);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.white);

        //label
        JLabel title = new JLabel("Enter Appointment Details");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(0x2d6a4f));
        title.setBounds(130, 10, 400, 50);

        JLabel l1 = new JLabel("Customer ID:");
        JLabel l2 = new JLabel("Date (yyyy-MM-dd):");
        JLabel l3 = new JLabel("Time (HHmm):");
        JLabel l4 = new JLabel("Service:");

        l1.setBounds(100, 80, 150, 30);
        l2.setBounds(100, 130, 150, 30);
        l3.setBounds(100, 180, 150, 30);
        l4.setBounds(100, 230, 150, 30);

        //textfield
        JTextField t1 = new JTextField();
        JTextField t2 = new JTextField();
        JTextField t3 = new JTextField();
        JTextField t4 = new JTextField();

        t1.setBounds(250, 80, 200, 30);
        t2.setBounds(250, 130, 200, 30);
        t3.setBounds(250, 180, 200, 30);
        t4.setBounds(250, 230, 200, 30);

        //button
        JButton appBtn = new JButton("Create");
        appBtn.setBounds(200, 300, 120, 30);

        frame.add(title);
        frame.add(l1);
        frame.add(l2);
        frame.add(l3);
        frame.add(l4);
        frame.add(t1);
        frame.add(t2);
        frame.add(t3);
        frame.add(t4);
        frame.add(appBtn);

        appBtn.addActionListener(e -> {

            //empty check
            if (t1.getText().isEmpty() ||
                t2.getText().isEmpty() ||
                t3.getText().isEmpty() ||
                t4.getText().isEmpty()) {

                JOptionPane.showMessageDialog(frame, "Please fill all fields!");
                return;
            }

            String cusId = t1.getText().trim();

            //find customer
            CreateCustomer selectedCustomer = null;

            try (BufferedReader br = new BufferedReader(new FileReader("data/customers.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length >= 5 && parts[0].trim().equals(cusId)) {
                        selectedCustomer = new CreateCustomer(
                            parts[0].trim(), // ID
                            parts[1].trim(), // Password
                            parts[2].trim(), // Name
                            parts[3].trim(), // Email
                            parts[4].trim()  // Phone
                        );
                        break;
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error reading customer file!");
                return;
            }

            if (selectedCustomer == null) {
                JOptionPane.showMessageDialog(frame, "Customer not found!");
                return;
            }

            LocalDateTime dateTime;
            String rawTime = t3.getText().trim();

            if (rawTime.length() != 4) {
                JOptionPane.showMessageDialog(frame, "Invalid time length! Please enter exactly 4 digits (e.g., 1820).");
                return;
            }

            try {
                String formattedJavaTime = rawTime.substring(0, 2) + ":" + rawTime.substring(2, 4);
                dateTime = LocalDateTime.parse(t2.getText().trim() + "T" + formattedJavaTime);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid date/time format validation failed!");
                return;
            }

            int maxId = 1000;
            try (BufferedReader br = new BufferedReader(new FileReader("data/appointments.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length > 0 && parts[0].startsWith("A")) {
                        try {
                            int idNum = Integer.parseInt(parts[0].substring(1).trim());
                            if (idNum > maxId) {
                                maxId = idNum;
                            }
                        } catch (NumberFormatException ignored) {}
                    }
                }
            } catch (IOException ignored) {}

            String appId = String.format("A%04d", maxId + 1);

            //create appointment
            CreateAppointment appointment = new CreateAppointment(
                    selectedCustomer,
                    appId,  
                    t4.getText(),
                    "pending",
                    dateTime
            );

            String techId = "None";
            try (BufferedReader brTech = new BufferedReader(new FileReader("data/technician.txt"))) {
                String techLine = brTech.readLine();
                if (techLine != null) {
                    String[] techParts = techLine.split(":");
                    if (techParts.length > 0) {
                        techId = techParts[0].trim();
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error reading technician file!");
                return;
            }

            appointment.tech_id = techId;

            try (FileWriter fw = new FileWriter("data/appointments.txt", true)) {
                String appTimeStr = t2.getText().trim() + " " + rawTime;
                fw.write(appointment.app_id + ":" + 
                         appointment.customer.cus_id + ":" + 
                         appointment.app_service_type + ":" + 
                         appointment.app_status + ":" + 
                         appTimeStr + ":" + 
                         appointment.tech_id + "\n");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error saving appointment file!");
                return;
            }

            JOptionPane.showMessageDialog(frame,
                    """
                    Appointment Created!
                    Customer ID: """ + cusId +
                    "\nAppointment ID: " + appId +
                    "\nDate: " + t2.getText() +
                    "\nTime: " + rawTime +
                    "\nService: " + t4.getText()
            );
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}