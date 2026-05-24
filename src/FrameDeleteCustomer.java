import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
        
        JLabel idLabel = new JLabel("ID:"); 
        idLabel.setBounds(100, 80, 150, 30);

        //textfield
        JTextField t1 = new JTextField();
        t1.setBounds(200, 80, 200, 30);

        //button
        JButton delete = new JButton("Delete");
        delete.setBounds(200, 140, 90, 30);

        frame.add(title);
        frame.add(idLabel);
        frame.add(t1);
        frame.add(delete);

        delete.addActionListener(e -> {

            //empty field check
            if (t1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Please enter ID!");
                return;
            }

            String idInput = t1.getText().trim();
            boolean found = false;
            String[] targetFiles = {"data/customers.txt", "data/staff.txt"};

            for (String filePath : targetFiles) {
                File file = new File(filePath);
                if (!file.exists()) continue;

                List<String> fileLines = new ArrayList<>();
                boolean fileUpdated = false;

                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(":");
                        if (parts.length > 0 && parts[0].trim().equals(idInput)) {
                            fileUpdated = true;
                            found = true;
                        } else {
                            fileLines.add(line);
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error reading database file: " + filePath);
                    return;
                }

                if (fileUpdated) {
                    try (FileWriter fw = new FileWriter(file, false)) {
                        for (String remainingLine : fileLines) {
                            fw.write(remainingLine + "\n");
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Error updating database file: " + filePath);
                        return;
                    }
                    
                    JOptionPane.showMessageDialog(frame,
                            "Profile Deleted Successfully!");
                    break;
                }
            }

            //customer not found
            if (!found) {
                JOptionPane.showMessageDialog(frame,
                        "ID not found!");
            }
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}