package Screens;
public class Account {
    //this class will have create account and login functions. 
    private String userEmail;
    private String password;

    Account(String email){
        userEmail = email;
    }

    public void setEmail(String newEmail){
        this.userEmail = newEmail;
    }
    public void setPassword(String newPassword){
        this.password = newPassword;
    }

    public String getEmail(){
            return userEmail;
    }

    public String getPassword(){
        return password;
    }

}
