import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FrameEditCustomer {

    public FrameEditCustomer() {

        //frame
        JFrame frame = new JFrame("Edit Profile Frame");
        frame.setSize(600, 700);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.white);
        frame.setVisible(true);

        //labels
        JLabel title = new JLabel("Enter Details to Edit Profile");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(0x2d6a4f));
        title.setBounds(120, 10, 400, 50);

        JLabel id = new JLabel("ID:");
        JLabel name = new JLabel("Name:");
        JLabel email = new JLabel("Email:");
        JLabel phone = new JLabel("Phone:");

        id.setBounds(100, 80, 100, 30);
        name.setBounds(100, 130, 100, 30);
        email.setBounds(100, 180, 100, 30);
        phone.setBounds(100, 230, 100, 30);

        //textfield
        JTextField t1 = new JTextField();
        JTextField t2 = new JTextField();
        JTextField t3 = new JTextField();
        JTextField t4 = new JTextField();

        t1.setBounds(200, 80, 200, 30);
        t2.setBounds(200, 130, 200, 30);
        t3.setBounds(200, 180, 200, 30);
        t4.setBounds(200, 230, 200, 30);

        //button
        JButton edit = new JButton("Edit");
        edit.setBounds(150, 300, 100, 30);
        frame.add(edit);

        edit.addActionListener(e -> {

    //check empty field
    if (t1.getText().isEmpty() ||
        t2.getText().isEmpty() ||
        t3.getText().isEmpty() ||
        t4.getText().isEmpty()) {

        JOptionPane.showMessageDialog(frame,
            "Error: Please fill in all fields!",
            "Input Error",
            JOptionPane.ERROR_MESSAGE);
        return;
    }

    String idInput = t1.getText().trim();
    String newName = t2.getText().trim();
    String newEmail = t3.getText().trim();
    String newPhone = t4.getText().trim();

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
                if (parts.length >= 5 && parts[0].trim().equals(idInput)) {
                    String password = parts[1].trim();
                    fileLines.add(parts[0].trim() + ":" + password + ":" + newName + ":" + newEmail + ":" + newPhone);
                    fileUpdated = true;
                    found = true;
                } else {
                    fileLines.add(line);
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error reading database file: " + filePath, "File Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (fileUpdated) {
            try (FileWriter fw = new FileWriter(file, false)) {
                for (String updatedLine : fileLines) {
                    fw.write(updatedLine + "\n");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error writing to database file: " + filePath, "File Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            JOptionPane.showMessageDialog(frame,
                "Profile Updated Successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            break;
        }
    }

    //not found error
    if (!found) {
        JOptionPane.showMessageDialog(frame,
            "Error: Customer or Staff ID not found!",
            "Not Found",
            JOptionPane.ERROR_MESSAGE);
    }
});
        frame.add(title);
        frame.add(t1);
        frame.add(t2);
        frame.add(t3);
        frame.add(t4);
        frame.add(id);
        frame.add(name);
        frame.add(email);
        frame.add(phone);
        frame.add(edit);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}