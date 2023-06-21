public class User {
    
    private String name;
    private int iD;
    private String userEmail;

    public void User(String name, int iD, String userEmail){
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
    
}
