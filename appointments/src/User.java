public class User {
    
    
    private String name;
    private int iD;

    public void User(String name, int iD){
        this.name = name;
        this.iD = iD;
    }
    
    public int getID(){
        return iD;
    }

    public String getName(){
        return name;
    }
    
}
