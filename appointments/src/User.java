public class User {
    
    private String name;
    private int iD;
    private String userEmail;

    public void user(String name, int iD, String userEmail){
        this.name = name;
        this.iD = iD;
        this.userEmail = userEmail;
    }
    
    public int getID(){
        return iD;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return userEmail;
    }
    
    public void setID(int newID){
        this.iD = newID;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void setEmail(String newEmail){
        this.userEmail = newEmail;
    }
}
