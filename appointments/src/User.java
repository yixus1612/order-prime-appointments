import java.util.Vector;

public class User extends Account{
    
    private String name;
    private int iD;
    public Vector <Appointment> appointmentList = new Vector <Appointment>();
    //when appointment class is created, declare an appointment vector here

    User(String name, int iD){
        this.name = name;
        this.iD = iD;
    }
    
    public int getID(){
        return iD;
    }

    public String getName(){
        return name;
    }

    public void setID(int newID){
        this.iD = newID;
    }

    public void setName(String newName){
        this.name = newName;
    }
}
