import java.io.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public abstract class User {
    protected String username;
    protected String password;
    protected String name;
    protected String email;
    protected String phone;
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public abstract void showDashboard();
    
    public static String findFileByPhone(String phone) {
        String[] files = {
            "data/customers.txt",
            "data/technician.txt",
            "data/staff.txt"
        };

        for (String path : files) {
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(":");
                    for (String d : data) {
                        if (d.trim().equals(phone)) {
                            return path;
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading " + path);
            }
        }
        return null;
    }

    public static boolean updatePasswordByPhone(String filePath, String phone, String newPassword) {
        List<String> updated = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(":");
                boolean match = false;
                
                for (String d : data) {
                    if (d.trim().equals(phone)) {
                        match = true;
                        break;
                    }
                }

                if (match) {
                    data[1] = newPassword;
                    updated.add(String.join(":", data));
                    found = true;
                } else {
                    updated.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Read error: " + e.getMessage());
            return false;
        }

        if (found) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (String l : updated) {
                    bw.write(l);
                    bw.newLine();
                }
                return true;
            } catch (IOException e) {
                System.out.println("Write error: " + e.getMessage());
            }
        }
        return false;
    }

    public static int generateOTP() {
        Random rand = new Random();
        return 1000 + rand.nextInt(9000);
    }
    
    protected static boolean ensureDataDirectoryExists() {
        File dir = new File("data");
        if (!dir.exists()) {
            return dir.mkdirs();
        }
        return true;
    }
    
    protected static List<String> readAllLines(String filePath) {
        List<String> lines = new ArrayList<>();
        File file = new File(filePath);
        
        if (!file.exists()) {
            return lines;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return lines;
    }
    
    protected static boolean writeAllLines(String filePath, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
            return false;
        }
    }
}