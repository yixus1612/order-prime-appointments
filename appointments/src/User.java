import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class User extends Account{
    
    private String name;
    private int iD;
    public ArrayList <Appointment> appointmentList = new ArrayList<Appointment>();
    //public Image profilePic;
    //when appointment class is created, declare an appointment vector here

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
