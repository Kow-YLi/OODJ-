import javax.swing.*;
import java.awt.*;

public class FrameViewAppointment {

    public FrameViewAppointment() {

        //frame
        JFrame frame = new JFrame("View Appointments");
        frame.setSize(600, 700);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.white);

        //label
        JLabel title = new JLabel("Appointment List");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(250, 20, 250, 30);
        frame.add(title);

        //text area
        JTextArea area = new JTextArea();
        area.setBounds(50, 80, 580, 500);
        area.setEditable(false);
         frame.add(area);

        //build appointment list
        StringBuilder sb = new StringBuilder();

        if (DataStored.appointments.isEmpty()) {
            sb.append("No appointments found.");
        } else {

            for (CreateAppointment a : DataStored.appointments) {

                sb.append("Appointment ID: ").append(a.app_id).append("\n");
                sb.append("Customer ID: ").append(a.customer.cus_id).append("\n");
                sb.append("Customer Name: ").append(a.customer.cus_name).append("\n");
                sb.append("Service: ").append(a.app_service_type).append("\n");
                sb.append("Status: ").append(a.app_status).append("\n");
                sb.append("Technician ID: ").append(a.tech_id).append("\n");
                sb.append("Time: ").append(a.app_time).append("\n");
                sb.append("---------------------------------\n");
            }
        }

        area.setText(sb.toString());

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}