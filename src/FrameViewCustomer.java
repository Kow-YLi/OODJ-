import javax.swing.*;
import java.awt.Color;
import java.io.*;

public class FrameViewCustomer {
    
    public FrameViewCustomer(){
        
        //frame
        JFrame frame = new JFrame("View Profiles Frame");
        frame.setSize(600,700);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.white);
        frame.setVisible(true);
        
        //swing that displays multiple lines of text 
        JTextArea area = new JTextArea();
        area.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setBounds(30,30,520,580); 
        frame.add(scrollPane);
        
        //build cus list
        StringBuilder sb = new StringBuilder();

        String[] targetFiles = {"data/customers.txt", "data/staff.txt"};
        
        for (String filePath : targetFiles) {
            File file = new File(filePath);
            if (!file.exists()) continue;
            
            String labelType = filePath.contains("customers") ? "CUSTOMER PROFILE" : "STAFF PROFILE";
            sb.append("=== ").append(labelType).append(" ===\n\n");
            
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length >= 5) {
                        sb.append("ID: ").append(parts[0].trim()).append("\n");
                        sb.append("Name: ").append(parts[2].trim()).append("\n");
                        sb.append("Email: ").append(parts[3].trim()).append("\n");
                        sb.append("Phone: ").append(parts[4].trim()).append("\n");
                        sb.append("----------------------\n");
                    }
                }
            } catch (IOException ex) {
                sb.append("Error reading data file: ").append(filePath).append("\n");
            }
            sb.append("\n");
        }

        area.setText(sb.toString());
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }
    
}