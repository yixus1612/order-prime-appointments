public class Account {

    //declare variables
    private String userEmail;
    private String password;

    //Account constructor for email
    Account(String email){
        userEmail = email;
    }

    //set methods
    public void setEmail(String newEmail){
        this.userEmail = newEmail;
    }

    public void setPassword(String newPassword){
        this.password = newPassword;
    }

    //get methods
    public String getEmail(){
            return userEmail;
    }

    public String getPassword(){
        return password;
    }

}
