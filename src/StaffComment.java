import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StaffComment extends JFrame {
    private Customer customer;
    private CustomerDashboard dashboard;
    private DefaultTableModel commentsModel;
    private static final String FILE_PATH = "data/staff_comments.txt";
    private List<String[]> allComments = new ArrayList<>();
    
    public StaffComment(Customer customer, CustomerDashboard dashboard) {
        this.customer = customer;
        this.dashboard = dashboard;
        setupUI();
    }
    
    private void setupUI() {
        setTitle("Staff Comments & Rating");
        setSize(900, 650);
        setLocationRelativeTo(null);
        getContentPane().setBackground(ThemeColors.WHITE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        main.setBackground(ThemeColors.WHITE);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(ThemeColors.BLUE), "Appointment Information"));
        inputPanel.setBackground(ThemeColors.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Appointment ID:"), gbc);
        gbc.gridx = 1;
        JTextField aptIdField = new JTextField(15);
        inputPanel.add(aptIdField, gbc);

        JPanel ratingPanel = new JPanel(new GridBagLayout());
        ratingPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(ThemeColors.FOREST_GREEN), "Rate & Comment"));
        ratingPanel.setBackground(ThemeColors.WHITE);
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        ratingPanel.add(new JLabel("Rating:"), gbc);
        gbc.gridx = 1;
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        radioPanel.setBackground(ThemeColors.WHITE);
        ButtonGroup ratingGroup = new ButtonGroup();
        JRadioButton[] ratingRadios = new JRadioButton[5];
        for (int i = 0; i < 5; i++) {
            ratingRadios[i] = new JRadioButton((i + 1) + " ★");
            ratingRadios[i].setBackground(ThemeColors.WHITE);
            ratingRadios[i].setFocusPainted(false);
            ratingGroup.add(ratingRadios[i]);
            radioPanel.add(ratingRadios[i]);
        }
        ratingRadios[2].setSelected(true);
        ratingPanel.add(radioPanel, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        ratingPanel.add(new JLabel("Comment:"), gbc);
        gbc.gridx = 1;
        JTextArea commentArea = new JTextArea(4, 25);
        commentArea.setLineWrap(true);
        JScrollPane commentScroll = new JScrollPane(commentArea);
        ratingPanel.add(commentScroll, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JButton submitBtn = new JButton("Submit Comment & Rating");
        ratingPanel.add(submitBtn, gbc);

        String[] columns = {"Appointment ID", "Rating", "Date", "Comment"};
        commentsModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        
        JTable commentsTable = new JTable(commentsModel) {
            public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                }
                if (col == 3) {
                    c.setForeground(Color.BLUE);
                    c.setFont(c.getFont().deriveFont(Font.PLAIN));
                } else {
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        };
        
        commentsTable.setRowHeight(40);
        commentsTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        commentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        commentsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = commentsTable.getSelectedRow();
                int col = commentsTable.columnAtPoint(e.getPoint());
                if (col == 3 && row != -1) {
                    String comment = allComments.get(row)[3];
                    showCommentDialog(comment);
                }
            }
        });
        
        commentsTable.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                int col = commentsTable.columnAtPoint(e.getPoint());
                commentsTable.setCursor(col == 3 ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : Cursor.getDefaultCursor());
            }
        });
        
        JScrollPane tableScroll = new JScrollPane(commentsTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(ThemeColors.BLUE), "Your Previous Comments"));
        tableScroll.setPreferredSize(new Dimension(500, 200));

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(ThemeColors.WHITE);
        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(ratingPanel, BorderLayout.CENTER);

        main.add(centerPanel, BorderLayout.CENTER);

        JPanel backBtnPanel = new JPanel();
        backBtnPanel.setBackground(ThemeColors.WHITE);
        JButton backBtn = new JButton("Back to Menu");
        backBtn.addActionListener(e -> {
            dispose();
            dashboard.setVisible(true);
        });
        backBtnPanel.add(backBtn);

        JPanel southPanel = new JPanel(new BorderLayout(5, 5));
        southPanel.setBackground(ThemeColors.WHITE);
        southPanel.add(tableScroll, BorderLayout.CENTER);
        southPanel.add(backBtnPanel, BorderLayout.SOUTH);
        main.add(southPanel, BorderLayout.SOUTH);

        submitBtn.addActionListener(e -> {
            String appointmentId = aptIdField.getText().trim();
            if (appointmentId.isEmpty()) {
                JOptionPane.showMessageDialog(StaffComment.this, "Please enter an Appointment ID");
                return;
            }

            int rating = 3;
            for (int i = 0; i < ratingRadios.length; i++) {
                if (ratingRadios[i].isSelected()) {
                    rating = i + 1;
                    break;
                }
            }

            String comment = commentArea.getText().trim();
            if (comment.isEmpty()) {
                JOptionPane.showMessageDialog(StaffComment.this, "Please enter a comment");
                return;
            }

            saveCommentToFile(customer.getUsername(), appointmentId, rating, comment);
            JOptionPane.showMessageDialog(StaffComment.this, "Comment saved! Thank you for your feedback.");

            aptIdField.setText("");
            commentArea.setText("");
            ratingRadios[2].setSelected(true);
            loadPreviousComments();
        });

        loadPreviousComments();
        add(main);
        setVisible(true);
    }

    private void loadPreviousComments() {
        commentsModel.setRowCount(0);
        allComments.clear();
        
        File file = new File(FILE_PATH);
       
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length >= 5 && parts[0].equals(customer.getUsername())) {
                        allComments.add(parts);
                        String ratingStars = getRatingStars(Integer.parseInt(parts[2]));
                        commentsModel.addRow(new Object[]{
                            parts[1], 
                            ratingStars, 
                            parts[4],
                            "<html><u>View Comment</u></html>"
                        });
                    }
                }
            } catch (IOException e) {
                System.err.println("Error loading comments: " + e.getMessage());
            }
        }
        
        if (commentsModel.getRowCount() == 0) {
            commentsModel.addRow(new Object[]{"No comments found", "", "", ""});
        }
    }
    
    private String getRatingStars(int rating) {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < rating; i++) {
            stars.append("★");
        }
        for (int i = rating; i < 5; i++) {
            stars.append("☆");
        }
        return stars.toString() + " (" + rating + "/5)";
    }

    private void saveCommentToFile(String username, String apptId, int rating, String comment) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String date = LocalDate.now().toString();
            String safeComment = comment.replace(":", " "); 
            writer.write(username + ":" + apptId + ":" + rating + ":" + safeComment + ":" + date);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving to file: " + e.getMessage());
        }
    }
    
    private void showCommentDialog(String comment) {
        JDialog dialog = new JDialog(this, "Comment Details", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);
        
        JTextArea commentArea = new JTextArea(comment);
        commentArea.setEditable(false);
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);
        commentArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        commentArea.setBackground(new Color(250, 250, 250));
        JScrollPane scrollPane = new JScrollPane(commentArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Comment"));
        
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(closeBtn);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
}