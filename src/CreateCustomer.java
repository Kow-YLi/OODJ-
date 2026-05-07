
public class CreateCustomer {

    
    public String cus_name, cus_email, cus_phone;
    public int cus_id;
    
    public CreateCustomer(int id, String name, String email, String phone){
        this.cus_name = name;
        this.cus_email = email;
        this.cus_id = id; 
        this.cus_phone = phone;
    
    }
    @Override
    public String toString(){
        return "ID:" + cus_id + 
                "Name:" + cus_name +
                "Email:" + cus_email + 
                "Phone:" + cus_phone;
    
    }
}

        
