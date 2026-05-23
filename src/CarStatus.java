import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CarStatus extends JFrame {
    private Customer customer;
    private JFrame parentDashboard;

    public CarStatus(Customer customer, JFrame dashboard) {
        this.customer = customer;
        this.parentDashboard = dashboard;

        String vehicleModel = customer.getVehicleModel();
        String vehiclePlate = customer.getVehiclePlate();
        String displayName = customer.getName();

        setTitle("Service Tracking: " + displayName.toUpperCase());
        setSize(1000, 600); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245)); 

        Object[][] rowData = getTableData(customer.getUsername(), vehicleModel, vehiclePlate);

        JPanel notiPanel = new JPanel(new BorderLayout());
        notiPanel.setBackground(new Color(232, 244, 253)); 
        notiPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 210, 230), 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        LocalDate today = LocalDate.now();

        List<Object[]> activeBookingsList = new ArrayList<>();
        
        for (int i = 0; i < rowData.length; i++) {
            String status = String.valueOf(rowData[i][6]);
            String dateStr = String.valueOf(rowData[i][3]);
            
            try {
                LocalDate appointmentDate = LocalDate.parse(dateStr);
                
                if (!appointmentDate.isBefore(today)) {
                    activeBookingsList.add(rowData[i]);
                }
            } catch (Exception ex) {
                System.out.println("Error parsing date: " + dateStr);
            }
        }

        Collections.sort(activeBookingsList, new Comparator<Object[]>() {
            @Override
            public int compare(Object[] o1, Object[] o2) {
                LocalDate date1 = LocalDate.parse(String.valueOf(o1[3]));
                LocalDate date2 = LocalDate.parse(String.valueOf(o2[3]));
                return date1.compareTo(date2);
            }
        });

        Object[] latestActiveBooking = null;
        if (!activeBookingsList.isEmpty()) {
            latestActiveBooking = activeBookingsList.get(0);
        }

        String latestInfo;
        if (latestActiveBooking != null) {
            latestInfo = "<html><body style='width: 800px;'>" +
                         "<b>ACTIVE BOOKING TRACKING:</b> Plate <b>" + latestActiveBooking[2] + "</b> | " +
                         "Service: <font color='#d9534f'><b>" + latestActiveBooking[5] + "</b></font> | " +
                         "Time: <b>" + latestActiveBooking[3] + " @ " + latestActiveBooking[4] + "</b><br>" +
                         "Current Status: <font color='#0056b3'><b>" + latestActiveBooking[6] + "</b></font>" +
                         "</body></html>";
        } else {
            notiPanel.setBackground(new Color(240, 248, 240));
            notiPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 220, 190), 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
            ));
            latestInfo = "<html><body><b>All clear!</b> You have no pending or upcoming service appointments at this moment.</body></html>";
        }

        JLabel notiLabel = new JLabel(latestInfo);
        notiLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        notiPanel.add(notiLabel, BorderLayout.CENTER);

        String[] columns = {"Appt ID", "Car Model", "Plate Number", "Date", "Booking Time", "Service Item", "Status"};
        
        DefaultTableModel model = new DefaultTableModel(rowData, columns) {
            @Override 
            public boolean isCellEditable(int r, int c) { 
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel centerWrapper = new JPanel(new BorderLayout(0, 15));
        centerWrapper.setOpaque(false);
        centerWrapper.add(notiPanel, BorderLayout.NORTH);
        centerWrapper.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(centerWrapper, BorderLayout.CENTER);

        JButton backBtn = new JButton("BACK TO MENU");
        backBtn.setBackground(new Color(52, 73, 94));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        backBtn.setFocusPainted(false);
        backBtn.setOpaque(true);
        backBtn.setBorderPainted(false);
        backBtn.setPreferredSize(new Dimension(220, 40));
        
        backBtn.addActionListener(e -> {
            this.dispose();
            if (parentDashboard != null) {
                parentDashboard.setVisible(true);
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);
        bottomPanel.add(backBtn);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private Object[][] getTableData(String userId, String model, String plate) {
        String appointmentFile = "data/appointments.txt";
        File file = new File(appointmentFile);

        if (!file.exists()) {
            try {
                new File("data").mkdirs();
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                    bw.write("APT0921:" + userId + ":2026-01-15:1130:Tyre Replacement:Completed\n");
                    bw.write("APT1001:" + userId + ":2026-05-20:1030:Oil Change:Assigned\n");
                    bw.write("APT1002:" + userId + ":2026-06-05:1600:Brake Repair:Pending\n");
                }
            } catch (IOException e) { 
                e.printStackTrace(); 
            }
        }

        List<String[]> filteredList = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(appointmentFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":", 6);
                
                if (parts.length >= 6 && parts[1].equals(userId)) {
                    filteredList.add(parts);
                }
            }
        } catch (IOException e) { 
            e.printStackTrace(); 
        }

        if (filteredList.isEmpty()) {
            return new Object[0][7];
        }

        Object[][] data = new Object[filteredList.size()][7];
        for (int i = 0; i < filteredList.size(); i++) {
            String[] row = filteredList.get(i);
            data[i][0] = row[0];
            data[i][1] = model;
            data[i][2] = plate;
            data[i][3] = row[2];
            data[i][4] = row[3];
            data[i][5] = row[4];
            data[i][6] = row[5];
        }
        return data;
    }
}