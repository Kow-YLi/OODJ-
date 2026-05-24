public class CreateCustomer {

    
    public String cus_id, cus_password, cus_name, cus_email, cus_phone;
    
    public CreateCustomer(String id, String password, String name, String email, String phone){
        this.cus_id = id; 
        this.cus_password = password;
        this.cus_name = name;
        this.cus_email = email;
        this.cus_phone = phone;
    
    }
    @Override
    public String toString(){
        return "ID:" + cus_id + 
                "Password:" + cus_password +
                "Name:" + cus_name +
                "Email:" + cus_email + 
                "Phone:" + cus_phone;
    
    }
}
        
