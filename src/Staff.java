import java.io.*;
import java.util.List;

public class Staff extends User {
    private static final String FILE_PATH = "data/staff.txt";

    private String department;
    private int yearsOfExperience;

    public Staff(String username, String password) {
        super(username, password);
        this.department = "";
        this.yearsOfExperience = 0;
    }

    public static Staff authenticate(String username, String password) {
        ensureDataDirectoryExists();
        
        List<String> lines = readAllLines(FILE_PATH);
        
        for (String line : lines) {
            String[] data = line.split(":");
            if (data.length >= 2 && data[0].equals(username) && data[1].equals(password)) {
                Staff staff = new Staff(data[0], data[1]);
                if (data.length > 2) staff.setName(data[2]);
                if (data.length > 3) staff.setEmail(data[3]);
                if (data.length > 4) staff.setPhone(data[4]);
                if (data.length > 5) staff.setDepartment(data[5]);
                if (data.length > 6) staff.setYearsOfExperience(Integer.parseInt(data[6]));
                return staff;
            }
        }
        return null;
    }

    public void saveToFile() {
        ensureDataDirectoryExists();
        
        List<String> lines = readAllLines(FILE_PATH);
        boolean found = false;
        
        String[] newData = {
            username,
            password,
            name,
            email,
            phone,
            department,
            String.valueOf(yearsOfExperience)
        };
        String newLine = String.join(":", newData);
        
        for (int i = 0; i < lines.size(); i++) {
            String[] data = lines.get(i).split(":");
            if (data.length > 0 && data[0].equals(username)) {
                lines.set(i, newLine);
                found = true;
                break;
            }
        }
        
        if (!found) {
            lines.add(newLine);
        }
        
        writeAllLines(FILE_PATH, lines);
    }

    @Override
    public void showDashboard() {
        new Dashboard(this);
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public int getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(int yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }

    @Override
    public String toString() {
        return "Staff [" + name + " | " + department + "]";
    }
}