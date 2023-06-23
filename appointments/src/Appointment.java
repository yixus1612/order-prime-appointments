import java.util.Date;

public class Appointment {
    private String appointmentType;
    private Date appointmentDate;
    private Boolean availability;
    private String businessName;
    private String provider;
    private User customer;
    private float cost;

    public void sendNotification(){
        //this function will inform the user they have an upcoming appointment
    }

    public String getType(){
            return appointmentType;
    }

    public Date getDate(){
            return appointmentDate;
    }

    public Boolean getAvailability(){
        return  availability;
    }

    public String getBusinessName(){
        return businessName;
    }

    public String getProvider(){
            return provider;
    }

    public User getCustomer(){
            return customer;
    }

    public float getCost(){
            return cost;
    }

    public void setType(String newType){
        this.appointmentType = newType;
    }

    public void setDate(Date newDate){
        this.appointmentDate = newDate;
    }

    public void setAvailability(Boolean newAvail){
        this.availability = newAvail;
    }

    public void setBusinessName(String newBusinessName){
        this.businessName = newBusinessName;
    }

    public void setProvider(String newProvider){
        this.provider = newProvider;
    }

    public void setCustomer(User newCustomer){
        this.customer = newCustomer;
    }

    public void setCost(float newCost){
        this.cost = newCost;
    }
}
