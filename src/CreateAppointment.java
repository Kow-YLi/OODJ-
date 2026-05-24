import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateAppointment {

    public String app_id;
    public String tech_id; 
    public String app_service_type;
    public String app_status;
    public LocalDateTime app_time;
    public LocalDateTime end_time;   
    public CreateCustomer customer;

    public CreateAppointment(CreateCustomer customer,
        String app_id,
        String app_service_type,
        String app_status,
        LocalDateTime app_time) {

        this.customer = customer;
        this.app_id = app_id;
        this.app_service_type = app_service_type;
        this.app_status = app_status;
        this.app_time = app_time;

        this.tech_id = "None"; 

        //calculate duration
        int duration = getDurationHours();

        //calculate end time
        this.end_time = app_time.plusHours(duration);
    }

    //service duration 
    public int getDurationHours() {
        String service = app_service_type.toLowerCase();
        if (service.contains("normal") || service.contains("tyre")) {
            return 1;
        } else {
            return 3; 
        }
    }

    //assign technician
    public void assignTechnician(String tech_id) {
        this.tech_id = tech_id;
        this.app_status = "assigned";
    }

    public String display() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return "Appointment ID: " + app_id + "\n" +
               "Customer ID: " + customer.cus_id + "\n" +
               "Name: " + customer.cus_name + "\n" +
               "Service: " + app_service_type + "\n" +
               "Status: " + app_status + "\n" +
               "Start Time: " + app_time.format(formatter) + "\n" +
               "End Time: " + end_time.format(formatter) + "\n" +
               "Duration: " + getDurationHours() + " hour(s)\n" +
               "Technician ID: " + tech_id;
    }

    @Override
    public String toString() {
        return display();
    }
}