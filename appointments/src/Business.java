import java.sql.Date;
import java.util.ArrayList;

public class Business extends Account{

    public ArrayList <Appointment> appointmentList = new ArrayList <Appointment>();
    private String name;
    private String type;


    Business(String name, String type){
        this.name = name;
        this.type = type;
    }

    public void displayAppointments(Business business){
        //this will display the appointments that have been created under a specific business account  (eventually) sorted by their due dates
        for(int i = 0; i < business.appointmentList.size(); i++)
        {
            Appointment appointment = business.appointmentList.get(i);
            System.out.println("Appointment " + (i+1) + " date:" + appointment.getDate());
        }


    }

    public void createAppointment(Business business, Appointment newAppointment){   //takes business object as parameter to access its appointment vector. 
                                                        //This is because the login info will determine which account this appointment is being created under
        //this function assumes the user has already been prompted for the appointments information like date and time by the GUI, 
        //meaning an appointment object has been created and passed as a parameter

        business.appointmentList.add(newAppointment);

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
