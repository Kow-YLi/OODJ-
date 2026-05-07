import javax.swing.*;
import java.awt.*;

public class FrameCreateCustomer {

    public FrameCreateCustomer() {
        
        //frame
        JFrame frame = new JFrame("Create Customer Frame");
        frame.setSize(600, 700);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.white);
        frame.setVisible(true);
        
        //label
        JLabel title = new JLabel("Enter Details to Create Profile");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(0x2d6a4f));
        title.setBounds(120, 10, 400, 50);
        
        JLabel l1 = new JLabel("Customer ID:"); 
        JLabel l2= new JLabel("Name:");
        JLabel l3 = new JLabel("Email:");
        JLabel l4 = new JLabel("Phone Number:");
        
        l1.setBounds(100,80,150,30);
        l2.setBounds(100,130,150,30);
        l3.setBounds(100,180,150,30);
        l4.setBounds(100,230,150,30);
        
        //textfield
        JTextField t1 = new JTextField();
        JTextField t2 = new JTextField();
        JTextField t3 = new JTextField();
        JTextField t4 = new JTextField();

        t1.setBounds(200, 80, 200, 30);
        t2.setBounds(200, 130, 200, 30);
        t3.setBounds(200, 180, 200, 30);
        t4.setBounds(200, 230, 200, 30);
        
        frame.add(title);
        frame.add(l1);
        frame.add(l2);
        frame.add(l3);
        frame.add(l4);

        frame.add(t1);
        frame.add(t2);
        frame.add(t3);
        frame.add(t4);

        //button
        JButton b1 = new JButton("Create");
        b1.setBounds(150, 300, 90, 30);
        frame.add(b1);

        b1.addActionListener(e -> {

    //check empty fields
    if (t1.getText().isEmpty() ||
        t2.getText().isEmpty() ||
        t3.getText().isEmpty() ||
        t4.getText().isEmpty()) {

        JOptionPane.showMessageDialog(frame, "Please fill all fields.");
        return;
    }

    int id;

    //check if theres wrong format
    try {
        id = Integer.parseInt(t1.getText());
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(
            frame,
            "Wrong format! ID must be a number.",
            "Input Error",
            JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    String name = t2.getText();
    String email = t3.getText();
    String phone = t4.getText();

    CreateCustomer customer = new CreateCustomer(id, name, email, phone);
    DataStored.customers.add(customer);

    JOptionPane.showMessageDialog(
        frame,
        "Customer Created!"
    );
    
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        });
    }
    
    }