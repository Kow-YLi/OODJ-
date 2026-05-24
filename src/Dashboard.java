import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    
    private Staff currentStaff;

    public Dashboard(Staff staff) {
        this.currentStaff = staff;
        setupUI();
    }
    
    private void setupUI() {
        setTitle("Counter Staff Dashboard - " + currentStaff.getName());
        setSize(600, 700);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(0x800020));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel title = new JLabel("Counter Staff Dashboard");
        title.setFont(new Font("Impact", Font.BOLD, 25));
        title.setForeground(new Color(0xf5e1ba));
        title.setBounds(145, 50, 300, 150);
        
        JButton manage = new JButton("Manage Accounts");
        manage.setFont(new Font("Monospaced", Font.BOLD, 15));
        manage.setBackground(new Color(0xf69697));
        manage.addActionListener(e -> {
            new FrameManageAccount();
        });
        manage.setBounds(120, 200, 350, 35);
        
        JButton appointment = new JButton("Manage Appointments");
        appointment.setFont(new Font("Monospaced", Font.BOLD, 15));
        appointment.setBackground(new Color(0xf69697));
        appointment.addActionListener(e -> {
            new FrameManageAppointment();
        });
        appointment.setBounds(120, 250, 350, 35);
        
        JButton payment = new JButton("Collect Payment");
        payment.setFont(new Font("Monospaced", Font.BOLD, 15));
        payment.setBackground(new Color(0xf69697));
        payment.addActionListener(e -> {
            new FrameCollectPayment();
        });
        payment.setBounds(120, 300, 350, 35);
        
        JButton receipt = new JButton("Generate Receipt");
        receipt.setFont(new Font("Monospaced", Font.BOLD, 15));
        receipt.setBackground(new Color(0xf69697));
        receipt.addActionListener(e -> {
            new FrameGenerateReceipt();
        });
        receipt.setBounds(120, 350, 350, 35);
        
        JButton logout = new JButton("Logout");
        logout.setFont(new Font("Monospaced", Font.BOLD, 15));
        logout.setBackground(new Color(0xf69697));
        logout.addActionListener(e -> {
            dispose();
            new Login();
        });
        logout.setBounds(120, 500, 350, 35);
        
        add(manage);
        add(appointment);
        add(payment);
        add(receipt);
        add(logout);
        add(title);
        
        setVisible(true);
    }
}