import javax.swing.*;
import java.awt.*;
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
        JLabel l3 = new JLabel("Time (HH:mm):");
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

            int cusId;

            //validate cusID
            try {
                cusId = Integer.parseInt(t1.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Customer ID must be a number!");
                return;
            }

            //find customer
            CreateCustomer selectedCustomer = null;

            for (CreateCustomer c : DataStored.customers) {
                if (c.cus_id == cusId) {
                    selectedCustomer = c;
                    break;
                }
            }

            if (selectedCustomer == null) {
                JOptionPane.showMessageDialog(frame, "Customer not found!");
                return;
            }

            //parse date + time
            LocalDateTime dateTime;

            try {
                dateTime = LocalDateTime.parse(t2.getText() + "T" + t3.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid date/time format!");
                return;
            }

            //assign appID automatically 
            int appId = DataStored.nextAppId++;

            //create appointment
            CreateAppointment appointment = new CreateAppointment(
                    selectedCustomer,
                    appId,
                    t4.getText(),
                    "pending",
                    dateTime
            );

            DataStored.appointments.add(appointment);

            JOptionPane.showMessageDialog(frame,
                    """
                    Appointment Created!
                    Customer ID: """ + cusId +
                    "\nAppointment ID: " + appId +
                    "\nDate: " + t2.getText() +
                    "\nTime: " + t3.getText() +
                    "\nService: " + t4.getText()
            );
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
