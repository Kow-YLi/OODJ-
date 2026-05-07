import java.util.ArrayList;

public class DataStored {
    public static ArrayList<CreateCustomer> customers = new ArrayList<>();
    public static ArrayList<CreateAppointment> appointments = new ArrayList<>();
    public static ArrayList<ManagePayment> payments = new ArrayList<>();
    
    //increment for each customer
    public static int nextCusId = 1;
    public static int nextAppId = 1;
    public static int nextPaymentId = 1;
}