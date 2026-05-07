import javax.swing.*;
import java.awt.Color;
import javax.swing.JFrame;

public class FrameViewCustomer {
    
    public FrameViewCustomer(){
        
        //frame
        JFrame frame = new JFrame("View Customer Frame");
        frame.setSize(600,700);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.white);
        frame.setVisible(true);
        
        //swing that displays multiple lines of text 
        JTextArea area = new JTextArea();
        area.setBounds(30,30,420,300); 
        frame.add(area);
        
        //build cus list
        StringBuilder sb = new StringBuilder();

        for (CreateCustomer c : DataStored.customers) {

            sb.append("ID: ").append(c.cus_id).append("\n");
            sb.append("Name: ").append(c.cus_name).append("\n");
            sb.append("Email: ").append(c.cus_email).append("\n");
            sb.append("Phone: ").append(c.cus_phone).append("\n");
            sb.append("----------------------\n");
        }

        area.setText(sb.toString());
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }
    
}
