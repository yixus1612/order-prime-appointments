import java.sql.Date;
import java.util.Vector;

public class Business extends Account{

    public Vector <Appointment> appointmentList = new Vector <Appointment>();
    private String name;
    private String type;


    Business(String name, String type){
        this.name = name;
        this.type = type;
    }

    public void displayAvailableAppointments(){
        //this will display available appointments...?
    }

    public void createAppointment(){
        //this function will allow the business to create appointments that will be accessible to users
    }

    public void deleteAppointment(Date appointmentDate){
        //this function takes a date, uses it to find an appointment at that date and time, and deletes it 
    }

    public void editAppointment(Date appointmentDate){
        //this function takes a date and allows the business to edit the appointment at that date and time
    }

    public String getName(){
        return name;
    }

    public String getType(){
        return type;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void setType(String newType){
        this.type = newType;
    }
}
