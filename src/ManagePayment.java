import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ManagePayment {

    public int payment_id;
    public String payment_method;
    public String receipt_no;
    public double payment_amount;
    public LocalDateTime payment_date;

    public CreateAppointment appointment;

    public ManagePayment(int payment_id, CreateAppointment appointment, double payment_amount, String payment_method) {

        this.payment_id = payment_id;
        this.appointment = appointment;
        this.payment_amount = payment_amount;
        this.payment_method = payment_method;

        this.payment_date = LocalDateTime.now();
        this.receipt_no = "RCPT" + payment_id;
    }

    @Override
    public String toString() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return "Receipt No: " + receipt_no +
               "\nPayment ID: " + payment_id +
               "\nAppointment ID: " + appointment.app_id +
               "\nCustomer ID: " + appointment.customer.cus_id +
               "\nAmount: RM " + payment_amount +
               "\nMethod: " + payment_method +
               "\nDate: " + payment_date.format(formatter);
    }
}
   