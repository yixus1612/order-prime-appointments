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

    User(String name, String email, int iD, Image pic){
        super(email);
        this.name = name;
        this.iD = iD;
        //this.profilePic = pic;
    }
    
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
}
