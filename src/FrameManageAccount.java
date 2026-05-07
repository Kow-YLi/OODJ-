import javax.swing.*;
import java.awt.*;

public class FrameManageAccount {

    JFrame frame;

    public FrameManageAccount() {

        frame = new JFrame("Manage Account Frame");
        frame.setSize(600, 700);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(0x2c5f8a));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //button
        JButton create = new JButton("Create Profile");
        create.setFont(new Font("Monospaced", Font.BOLD, 15));
        create.addActionListener(e -> {
            new FrameCreateCustomer();
        });
        
        JButton edit = new JButton("Edit Profile");
        edit.setFont(new Font("Monospaced", Font.BOLD, 15));
            edit.addActionListener(e -> {
            new FrameEditCustomer();
        });
            
        JButton view = new JButton("View Profile");
        view.setFont(new Font("Monospaced", Font.BOLD, 15));
        view.addActionListener(e -> {
            new FrameViewCustomer();
        });
        
        JButton delete = new JButton("Delete Profile");
        delete.setFont(new Font("Monospaced", Font.BOLD, 15));
        delete.addActionListener(e -> {
            new FrameDeleteCustomer();
        });

        create.setBounds(120,200,350,35);
        edit.setBounds(120, 250, 350, 35);
        view.setBounds(120, 300, 350, 35);
        delete.setBounds(120, 350, 350, 35);

        frame.add(create);
        frame.add(edit);
        frame.add(view);
        frame.add(delete);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        FrameManageAccount manageAccount = new FrameManageAccount();
  
    }
    
}