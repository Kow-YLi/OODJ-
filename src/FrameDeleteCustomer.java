
import javax.swing.*;
import java.awt.*;

public class FrameDeleteCustomer {
    
    public FrameDeleteCustomer(){
    
        //frame
        JFrame frame = new JFrame ("Delete Customer Frame");
        frame.setSize(600, 700);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.white);
        frame.setVisible(true);
        
        //label        
        JLabel title = new JLabel("Enter Details to Delete Profile");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(0x2d6a4f));
        title.setBounds(120, 10, 400, 50);

        JLabel idLabel = new JLabel("ID:");
        JLabel name = new JLabel("Name:");
        JLabel email = new JLabel("Email:");
        JLabel phone = new JLabel("Phone:");

        idLabel.setBounds(100, 80, 100, 30);
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
        
        frame.add(title);
        frame.add(idLabel);
        frame.add(name);
        frame.add(email);
        frame.add(phone);
        frame.add(t1);
        frame.add(t2);
        frame.add(t3);
        frame.add(t4);
        

        //button
        JButton delete = new JButton("Delete");
        delete.setBounds(150, 300, 100, 30);
        frame.add(delete);
        
        
         delete.addActionListener(e -> {

            if (t1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter ID");
                return;
            }

            int id;

            try {
                id = Integer.parseInt(t1.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "ID must be a number");
                return;
            }

            boolean found = false;

            for (int i = 0; i < DataStored.customers.size(); i++) {

                if (DataStored.customers.get(i).cus_id == id) {

                    DataStored.customers.remove(i);

                    JOptionPane.showMessageDialog(frame, "Customer Deleted!");
                    found = true;
                    break;
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(frame, "Customer not found!");
            }
        });
         
         frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         frame.setVisible(true);

        
        
        
        
        
        
        
        
        
        
        
        
        
        
    
    
    
    
    
    }
    
   
    
}
