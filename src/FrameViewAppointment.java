import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

        boolean appointmentsFound = false;

        try (BufferedReader brApp = new BufferedReader(new FileReader("data/appointments.txt"))) {
            String appLine;
            while ((appLine = brApp.readLine()) != null) {
                String[] appParts = appLine.split(":");
                if (appParts.length >= 6) {
                    appointmentsFound = true;
                    
                    String appId = appParts[0].trim();
                    String cusId = appParts[1].trim();
                    String serviceType = appParts[2].trim();
                    String status = appParts[3].trim();
                    String rawTimeField = appParts[4].trim();
                    String techId = appParts[5].trim();


                    String formattedDisplayTime = rawTimeField;
                    if (rawTimeField.length() >= 4) {
                        String datePart = rawTimeField.substring(0, rawTimeField.length() - 4).trim();
                        String timePart = rawTimeField.substring(rawTimeField.length() - 4);
                        formattedDisplayTime = datePart + " " + timePart.substring(0, 2) + ":" + timePart.substring(2, 4);
                    }

                    String customerName = "Unknown Customer";
                    try (BufferedReader brCus = new BufferedReader(new FileReader("data/customers.txt"))) {
                        String cusLine;
                        while ((cusLine = brCus.readLine()) != null) {
                            String[] cusParts = cusLine.split(":");
                            if (cusParts.length >= 3 && cusParts[0].trim().equals(cusId)) {
                                customerName = cusParts[2].trim();
                                break;
                            }
                        }
                    } catch (IOException ignored) {}

                    sb.append("Appointment ID: ").append(String.format("A%04d", Integer.parseInt(appId.substring(1)))).append("\n");
                    sb.append("Customer ID: ").append(cusId).append("\n");
                    sb.append("Customer Name: ").append(customerName).append("\n");
                    sb.append("Service: ").append(serviceType).append("\n");
                    sb.append("Status: ").append(status).append("\n");
                    sb.append("Technician ID: ").append(techId).append("\n");
                    sb.append("Time: ").append(formattedDisplayTime).append("\n"); 
                    sb.append("---------------------------------\n");
                }
            }
        } catch (IOException e) {
            sb.append("Error reading appointments file.");
            appointmentsFound = true;
        }

        if (!appointmentsFound) {
            sb.append("No appointments found.");
        }

        area.setText(sb.toString());

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}