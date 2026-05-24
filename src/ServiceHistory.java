import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class ServiceHistory extends JFrame {
    private Customer customer;
    private CustomerDashboard dashboard;

    private class ServiceRecord {
        String id, date, serviceType, amount;
        ServiceRecord(String id, String date, String type, String amt) {
            this.id = id; this.date = date; this.serviceType = type; this.amount = amt;
        }
    }

    private class PaymentRecord {
        String id, date, amount, method;
        PaymentRecord(String id, String date, String amt, String method) {
            this.id = id; this.date = date; this.amount = amt; this.method = method;
        }
    }

    private class FeedbackRecord {
        String appointmentId, rating, comment;
        FeedbackRecord(String id, String rating, String comment) {
            this.appointmentId = id; this.rating = rating; this.comment = comment;
        }
    }

    private List<ServiceRecord> allServiceRecords = new ArrayList<>();
    private List<PaymentRecord> allPaymentRecords = new ArrayList<>();
    private List<FeedbackRecord> allFeedbacks = new ArrayList<>();

    private DefaultTableModel serviceModel;
    private DefaultTableModel paymentModel;
    private JTextField idFilter, dateFilter, minFilter, maxFilter;

    public ServiceHistory(Customer customer, CustomerDashboard dashboard) {
        this.customer = customer;
        this.dashboard = dashboard;
        loadData();
        setupUI();
    }

    private void loadData() {
        String username = customer.getUsername();
        
        try (BufferedReader br = new BufferedReader(new FileReader("data/services.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 5 && parts[1].equals(username)) {
                    allServiceRecords.add(new ServiceRecord(parts[0], parts[2], parts[3], parts[4]));
                }
            }
        } catch (IOException e) { 
            System.err.println("Error reading services: " + e.getMessage());
        }

        try (BufferedReader br = new BufferedReader(new FileReader("data/payments.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 5 && parts[1].equals(username)) {
                    allPaymentRecords.add(new PaymentRecord(parts[0], parts[2], parts[3], parts[4]));
                }
            }
        } catch (IOException e) { 
            System.err.println("Error reading payments: " + e.getMessage());
        }

        try (BufferedReader br = new BufferedReader(new FileReader("data/feedbacks.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 4 && parts[1].equals(username)) {
                    allFeedbacks.add(new FeedbackRecord(parts[0], parts[2], parts[3]));
                }
            }
        } catch (IOException e) { 
            System.err.println("Error reading feedback: " + e.getMessage());
        }
    }

    private void setupUI() {
        setTitle("Service & Payment History");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout(0, 15));
        main.setBackground(Color.WHITE);
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel filterPanel = new JPanel(new GridLayout(2, 4, 10, 5));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filters"));

        filterPanel.add(new JLabel("ID Search:"));
        idFilter = new JTextField();
        filterPanel.add(idFilter);

        filterPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateFilter = new JTextField();
        filterPanel.add(dateFilter);

        filterPanel.add(new JLabel("Min RM:"));
        minFilter = new JTextField();
        filterPanel.add(minFilter);

        filterPanel.add(new JLabel("Max RM:"));
        maxFilter = new JTextField();
        filterPanel.add(maxFilter);

        JButton applyBtn = new JButton("Apply Filters");
        applyBtn.addActionListener(e -> applyFilterLogic());
        filterPanel.add(applyBtn);

        JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(e -> {
            idFilter.setText("");
            dateFilter.setText("");
            minFilter.setText("");
            maxFilter.setText("");
            refreshTables(allServiceRecords, allPaymentRecords);
        });
        filterPanel.add(resetBtn);

        main.add(filterPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        
        String[] sCols = {"Appointment ID", "Date", "Service Type", "Amount (RM)", "Feedback"};
        serviceModel = createNonEditableModel(sCols);
        JTable serviceTable = createStyledTable(serviceModel, true);
        
        serviceTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = serviceTable.getSelectedRow();
                int col = serviceTable.columnAtPoint(e.getPoint());
                if (col == 4 && row != -1) {
                    String sid = serviceModel.getValueAt(row, 0).toString();
                    String type = serviceModel.getValueAt(row, 2).toString();
                    showFeedbackDialog(sid, type);
                }
            }
        });

        String[] pCols = {"Payment ID", "Payment Date", "Amount (RM)", "Method"};
        paymentModel = createNonEditableModel(pCols);
        JTable paymentTable = createStyledTable(paymentModel, false);

        tabbedPane.addTab("Service History", new JScrollPane(serviceTable));
        tabbedPane.addTab("Payment History", new JScrollPane(paymentTable));
        main.add(tabbedPane, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back to Menu");
        backBtn.addActionListener(e -> {
            dispose();
            dashboard.setVisible(true);
        });
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setOpaque(false);
        bottom.add(backBtn);
        main.add(bottom, BorderLayout.SOUTH);

        refreshTables(allServiceRecords, allPaymentRecords);
        add(main);
        setVisible(true);
    }

    private void applyFilterLogic() {
        String id = idFilter.getText().trim().toLowerCase();
        String date = dateFilter.getText().trim();
        String minStr = minFilter.getText().trim();
        String maxStr = maxFilter.getText().trim();

        List<ServiceRecord> fS = new ArrayList<>();
        for (ServiceRecord r : allServiceRecords) {
            boolean match = true;
            if (!id.isEmpty() && !r.id.toLowerCase().contains(id)) match = false;
            if (!date.isEmpty() && !r.date.equals(date)) match = false;
            try {
                if (!minStr.isEmpty() && Double.parseDouble(r.amount) < Double.parseDouble(minStr)) match = false;
                if (!maxStr.isEmpty() && Double.parseDouble(r.amount) > Double.parseDouble(maxStr)) match = false;
            } catch (Exception e) {}
            if (match) fS.add(r);
        }

        List<PaymentRecord> fP = new ArrayList<>();
        for (PaymentRecord p : allPaymentRecords) {
            boolean match = true;
            if (!id.isEmpty() && !p.id.toLowerCase().contains(id)) match = false;
            if (!date.isEmpty() && !p.date.equals(date)) match = false;
            try {
                if (!minStr.isEmpty() && Double.parseDouble(p.amount) < Double.parseDouble(minStr)) match = false;
                if (!maxStr.isEmpty() && Double.parseDouble(p.amount) > Double.parseDouble(maxStr)) match = false;
            } catch (Exception e) {}
            if (match) fP.add(p);
        }
        refreshTables(fS, fP);
    }

    private void refreshTables(List<ServiceRecord> sList, List<PaymentRecord> pList) {
        serviceModel.setRowCount(0);
        for (ServiceRecord r : sList) {
            serviceModel.addRow(new Object[]{r.id, r.date, r.serviceType, r.amount, "<html><u>View Feedback</u></html>"});
        }
        paymentModel.setRowCount(0);
        for (PaymentRecord p : pList) {
            paymentModel.addRow(new Object[]{p.id, p.date, p.amount, p.method});
        }
    }

    private JTable createStyledTable(DefaultTableModel model, boolean isService) {
        JTable table = new JTable(model) {
            public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                }
                if (isService && col == 4) c.setForeground(Color.BLUE);
                else c.setForeground(Color.BLACK);
                return c;
            }
        };
        table.setRowHeight(35);
        if (isService) {
            table.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseMoved(MouseEvent e) {
                    int col = table.columnAtPoint(e.getPoint());
                    table.setCursor(col == 4 ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : Cursor.getDefaultCursor());
                }
            });
        }
        return table;
    }

    private DefaultTableModel createNonEditableModel(String[] cols) {
        return new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
    }

    private void showFeedbackDialog(String appointmentId, String serviceType) {
        FeedbackRecord target = null;
        for (FeedbackRecord fdb : allFeedbacks) {
            if (fdb.appointmentId.equals(appointmentId)) {
                target = fdb;
                break;
            }
        }
        JDialog dialog = new JDialog(this, "Service Feedback", true);
        dialog.setSize(350, 250);
        dialog.setLocationRelativeTo(this);
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        p.setBackground(Color.WHITE);
        
        if (target != null) {
            p.add(new JLabel("<html><b>" + serviceType + "</b></html>"), BorderLayout.NORTH);
            JTextArea area = new JTextArea(target.comment);
            area.setEditable(false);
            area.setLineWrap(true);
            area.setBackground(new Color(250, 250, 250));
            p.add(new JScrollPane(area), BorderLayout.CENTER);
        } else {
            p.add(new JLabel("No feedback found for this service."), BorderLayout.CENTER);
        }
        
        JButton close = new JButton("Close");
        close.addActionListener(e -> dialog.dispose());
        p.add(close, BorderLayout.SOUTH);
        dialog.add(p);
        dialog.setVisible(true);
    }
}