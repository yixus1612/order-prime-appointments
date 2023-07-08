
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

public class Business extends Account{

    public ArrayList <Appointment> appointmentList = new ArrayList <Appointment>();
    private String name;
    private String type;
    private int id;

    Business(){
        super(null);
        this.name = null;
        this.type = null;
        this.id = 0;
    }

    Business(String name, String email, String type, int id){
        super(email);
        this.name = name;
        this.type = type;
        this.id = id;
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

    public int getID(){
        return id;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void setType(String newType){
        this.type = newType;
    }

    public void setID(int id){
        this.id = id;
    }

    public void createAppointmentList(String file){
        try{
                //set up fileReader
                FileReader fileReaderAccount = new FileReader(file);
                BufferedReader br = new BufferedReader(fileReaderAccount);
                String line = "";
                String[] tempArr;
                Appointment tempAppointment = new Appointment();
                int counter = 0;

            //read account file
                while((line = br.readLine()) != null){
                    tempArr = line.split(",");
                    //check to see if email and password are correctly inputted
                    tempAppointment.setType(tempArr[0]);
                    //tempAppointment.setDate(tempArr[1]);
                    //tempAppointment.setAvailability(tempArr[2]);
                    tempAppointment.setProvider(Integer.parseInt(tempArr[3]));
                    tempAppointment.setCustomer(Integer.parseInt(tempArr[4]));
                    tempAppointment.setCost(Integer.parseInt(tempArr[5]));
                    appointmentList.add(counter, tempAppointment);
                    counter++;
                }

                br.close();
                fileReaderAccount.close();

            }catch(IOException except){
                System.out.println(except);
            }
    }        
}
