
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.scene.image.Image;

public class Business extends Account{

    public List <Appointment> appointmentList = new ArrayList <Appointment>();
    private String name;
    private String type;
    private int id;
    public Image profilePic;

    Business(){
        super(null);
        this.name = null;
        this.type = null;
        this.id = 0;
        this.profilePic = new Image(getClass().getResourceAsStream("/images/BlankImage.jpg"));
    }

    Business(String name, String email, String type, int id){
        super(email);
        this.name = name;
        this.type = type;
        this.id = id;
        this.profilePic = new Image(getClass().getResourceAsStream("/images/BlankImage.jpg"));
    }

    public void displayAppointments(Business business){
        //this will display the appointments that have been created under a specific business account  (eventually) sorted by their due dates
        for(int i = 0; i < business.appointmentList.size(); i++)
        {
            Appointment appointment = business.appointmentList.get(i);
            System.out.println("Appointment " + (i+1) + " date:" + appointment.getDate());
        }


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

    public Image getProfilePic(){
        return profilePic;
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

    public void setProfilePic(Image newImage){
        this.profilePic = newImage; 
    }

    public void createAppointmentList(){
        try{
            //set up fileReader
            FileReader fileReaderAccount = new FileReader("appointmentList.csv");
            BufferedReader br = new BufferedReader(fileReaderAccount);
            String line = "";
            String[] tempArr;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");

            //read account file
            while((line = br.readLine()) != null){
                tempArr = line.split(",");

                //check to see if email and password are correctly inputted
                if(Integer.parseInt(tempArr[3]) == this.id){
                    Appointment tempAppointment = new Appointment();
                    tempAppointment.setType(tempArr[0]);
                    tempAppointment.setDate(tempArr[1]);
                    tempAppointment.setAvailability(Boolean.parseBoolean(tempArr[2]));
                    tempAppointment.setProvider(Integer.parseInt(tempArr[3]));
                    tempAppointment.setCustomer(Integer.parseInt(tempArr[4]));
                    tempAppointment.setCost(tempArr[5]);
                    tempAppointment.setID(Integer.parseInt(tempArr[6]));
                    appointmentList.add(tempAppointment);
                }
            }

            br.close();
            fileReaderAccount.close();

        }catch(IOException except){
            System.out.println(except);
        }
    }     
    
    void addAppointment(Appointment appointment){
        appointmentList.add(appointment);
    }
}
