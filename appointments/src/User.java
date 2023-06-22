public class User {
    
    private String name;
    private int iD;
    private String userEmail;
    private String password;

    User(String name, int iD, String userEmail, String password){
        this.name = name;
        this.iD = iD;
        this.userEmail = userEmail;
        this.password = password;
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

    public String getPassword(){
        return password;
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
    public void setPassword(String newPassword){
        this.password = newPassword;
    }
}
