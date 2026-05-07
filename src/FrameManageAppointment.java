import javax.swing.*;
import java.awt.*;

public class FrameManageAppointment {
    
    public FrameManageAppointment(){
        
        //frame
        JFrame frame = new JFrame("Manage Appointment Frame");
        frame.setSize(600, 700);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(0x52a447));

        
        //button
        JButton create = new JButton("Create Appointment");
        create.setFont(new Font("Monospaced", Font.BOLD, 15));
        create.setBackground (new Color (0xcce7c9));
        create.addActionListener(e -> {
            new FrameCreateAppointment();
        });
        
        JButton view = new JButton("View Appointment");
        view.setFont(new Font("Monospaced", Font.BOLD, 15));
        view.setBackground (new Color (0xcce7c9));
        view.addActionListener(e -> {
        new FrameViewAppointment();
        });


        JButton delete = new JButton("Delete Appointment");
        delete.setFont(new Font("Monospaced", Font.BOLD, 15));
        delete.setBackground (new Color (0xcce7c9));
        delete.addActionListener(e -> {
            new FrameDeleteAppointment();
        });

        create.setBounds(120,200,350,35);
        view.setBounds(120, 250, 350, 35);
        delete.setBounds(120, 300, 350, 35);

        frame.add(create);
        frame.add(delete);
        frame.add(view);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    
    
    
    
    
    }
    
}
