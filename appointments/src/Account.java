public class Account {
    //this class will have create account and login functions. 
    private String userEmail;
    private String password;

    public void createAccount(){
        //this class will allow the user to create an account
    }

    public void login(){
        //if the user already has an account created, this class will allow them to login in to their account
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
