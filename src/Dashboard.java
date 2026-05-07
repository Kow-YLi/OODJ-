
import javax.swing.*;
import java.awt.*;


public class Dashboard {

    public Dashboard(){
        JFrame frame = new JFrame("Counter Staff Dashboard");
        frame.setSize(600,700);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(0x800020));
        
        //label 
        JLabel title = new JLabel ("Counter Staff Dashboard");
        title.setFont(new Font("Impact", Font.BOLD, 25));
        title.setForeground(new Color(0xf5e1ba));
        title.setBounds(145,50,300,150);
        
        //button
        JButton manage = new JButton("Manage Accounts"); 
        manage.setFont(new Font("Monospaced", Font.BOLD, 15));
        manage.setBackground(new Color(0xf69697));
        manage.addActionListener(e -> {
            new FrameManageAccount();
        });
        manage.setBounds(120,200,350,35);
        
        JButton appointment = new JButton("Manage Appointments");
        appointment.setFont(new Font("Monospaced", Font.BOLD, 15));
        appointment.setBackground(new Color(0xf69697));
        appointment.addActionListener(e -> {
            new FrameManageAppointment();
        });
        appointment.setBounds(120,300,350,35); 
        
        JButton payment = new JButton("Collect Payment");
        payment.setFont(new Font("Monospaced", Font.BOLD, 15));
        payment.setBackground(new Color(0xf69697));
        payment.addActionListener(e -> {
            new FrameCollectPayment();
        });
        payment.setBounds(120,250,350,35);
        
        JButton receipt = new JButton("Generate Receipt");
        receipt.setFont(new Font("Monospaced", Font.BOLD, 15));
        receipt.setBackground(new Color(0xf69697));
        receipt.addActionListener(e -> {
            new FrameGenerateReceipt();
        });
        receipt.setBounds(120,350,350,35); 
        
        frame.add(manage);
        frame.add(appointment);
        frame.add(payment);
        frame.add(receipt);
        frame.add(title);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        Dashboard dashboard = new Dashboard();
       
    }
}