import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class User extends Account{
    
    private String name;
    private int iD;
    public List <Appointment> appointmentList = new ArrayList<Appointment>();
    //public Image profilePic;

    User(){
        super(null);
        this.name = null;
        this.iD = 0;
        //this.profilePic = null;
    }

    User(String name, String email, int iD){
        super(email);
        this.name = name;
        this.iD = iD;
        //this.profilePic = new Image("BlankImage.jpg");
    }

    /*User(String name, String email, int iD, Image pic){
        super(email);
        this.name = name;
        this.iD = iD;
        //this.profilePic = pic;
    }*/
    
    public int getID(){
        return iD;
    }

    /*public Image getProfilePic(){
        return profilePic;
    }*/

    public String getName(){
        return name;
    }

    public void setID(int newID){
        this.iD = newID;
    }

    public void setName(String newName){
        this.name = newName;
    }

  /*  public void setProfilePic(Image newImage){
        this.profilePic = newImage; 
    }*/

    public void createAppointmentList(){
        try{
            //set up fileReader
            FileReader fileReaderAccount = new FileReader("appointmentList.csv");
            BufferedReader br = new BufferedReader(fileReaderAccount);
            String line = "";
            String[] tempArr;
            Appointment tempAppointment = new Appointment();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a z");

            //read account file
            while((line = br.readLine()) != null){
                tempArr = line.split(",");
                    
                //check if id matches user and add it to their appointment list.
                if(Integer.parseInt(tempArr[4]) == this.iD){
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
    
    public void addAppointment(Appointment appointment){
        appointmentList.add(appointment);
    }
}
