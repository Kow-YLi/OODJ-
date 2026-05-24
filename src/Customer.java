public class Customer extends User {
    private static final String FILE_PATH = "data/customers.txt";

    private String address;
    private String vehicleModel;
    private String vehiclePlate;
    
    public Customer(String username, String password) {
        super(username, password);
    }
 
    public static Customer authenticate(String username, String password) {
        ensureDataDirectoryExists();
        
        java.util.List<String> lines = readAllLines(FILE_PATH);
        
        for (String line : lines) {
            String[] data = line.split(":");
            if (data.length >= 2 && data[0].equals(username) && data[1].equals(password)) {
                Customer customer = new Customer(data[0], data[1]);
                if (data.length > 2) customer.setName(data[2]);
                if (data.length > 3) customer.setEmail(data[3]);
                if (data.length > 4) customer.setPhone(data[4]);
                if (data.length > 5) customer.setAddress(data[5]);
                if (data.length > 6) customer.setVehicleModel(data[6]);
                if (data.length > 7) customer.setVehiclePlate(data[7]);
                return customer;
            }
        }
        return null;
    }

    public void saveToFile() {
        ensureDataDirectoryExists();
        
        java.util.List<String> lines = readAllLines(FILE_PATH);
        boolean found = false;
        
        String[] newData = {
            username,
            password,
            name,
            email,
            phone,
            address,
            vehicleModel,
            vehiclePlate
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

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getVehicleModel() { return vehicleModel; }
    public void setVehicleModel(String vehicleModel) { this.vehicleModel = vehicleModel; }

    public String getVehiclePlate() { return vehiclePlate; }
    public void setVehiclePlate(String vehiclePlate) { this.vehiclePlate = vehiclePlate; }
    
    @Override
    public void showDashboard() {
        new CustomerDashboard(this);
    }
    
    @Override
    public String toString() {
        return "Customer [" + name + " | " + vehiclePlate + "]";
    }
}