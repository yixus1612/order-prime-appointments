import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

public class Appointment {
    private String appointmentType;
    private Date appointmentDate;
    private Boolean availability;
    private Business provider;
    private User customer;
    private float cost;

    public void sendNotification(){
        //this function will inform the user they have an upcoming appointment
    }

    Appointment(){
            this.appointmentType = null;
            this.appointmentDate = null;
            this.availability = null;
            this.provider = null;
            this.customer = null;
            this.cost = 0;
    }
    
    Appointment(String appointmentType, Date appointmentDate, Boolean availability, int businessID, int customerID, float cost){
            this.appointmentType = appointmentType;
            this.appointmentDate = appointmentDate;
            this.availability = availability;
            this.provider = findBusiness(businessID);
            this.customer = findUser(customerID);
            this.cost = cost;
    }
    public String getType(){
            return appointmentType;
    }

    public Date getDate(){
            return appointmentDate;
    }

    public Boolean getAvailability(){
        return  availability;
    }

    public Business getProvider(){
            return provider;
    }

    public User getCustomer(){
            return customer;
    }

    public float getCost(){
            return cost;
    }

    public void setType(String newType){
        this.appointmentType = newType;
    }

    public void setDate(Date newDate){
        this.appointmentDate = newDate;
    }

    public void setAvailability(Boolean newAvail){
        this.availability = newAvail;
    }

    public void setProvider(int newProvider){
        this.provider = findBusiness(newProvider);
    }

    public void setCustomer(int newCustomer){
        this.customer = findUser(newCustomer);
    }

    public void setCost(float newCost){
        this.cost = newCost;
    }

    public User findUser(int id){
        User tempUser = new User();
        try{
            FileReader fileReaderUser = new FileReader("userList.csv");
            BufferedReader br = new BufferedReader(fileReaderUser);
            String line = "";
            String[] tempArr;

            while((line = br.readLine()) != null){
                    tempArr = line.split(",");
                    tempUser.setName(tempArr[0]);
                    tempUser.setEmail(tempArr[1]);
                    tempUser.setID(Integer.parseInt(tempArr[2]));

                    //check to see if email and password are correctly inputted
                    if(tempUser.getID() == id){
                        br.close();
                        return tempUser;
                    }
            }

            br.close();
            fileReaderUser.close();

        }catch(IOException except){
            System.out.println(except);
        }

        return tempUser;
    }

    public Business findBusiness(int id){
        Business tempBusiness = new Business();
        try{
            FileReader fileReaderUser = new FileReader("businessList.csv");
            BufferedReader br = new BufferedReader(fileReaderUser);
            String line = "";
            String[] tempArr;

            while((line = br.readLine()) != null){
                tempArr = line.split(",");
                tempBusiness.setName(tempArr[0]);
                tempBusiness.setEmail(tempArr[1]);
                tempBusiness.setType(tempArr[2]);
                tempBusiness.setID(Integer.parseInt(tempArr[3]));


                //check to see if email and password are correctly inputted
                if(tempBusiness.getID() == id){
                    br.close();
                    return tempBusiness;
                }  
            }

            br.close();
            fileReaderUser.close();

        }catch(IOException except){
            System.out.println(except);
        }

        return tempBusiness;
    }
}
