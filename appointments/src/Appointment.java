import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private String appointmentType;
    private String appointmentStartDate;
    private String appointmentEndDate;
    private Boolean availability;
    private Business provider;
    private User customer;
    private String cost;
    private int id;

    Appointment(){
        this.appointmentType = null;
        this.appointmentStartDate = null;
        this.appointmentEndDate = null;
        this.availability = true;
        this.provider = new Business();
        this.customer = new User();
        this.cost = null;
        this.id = 0;
    }
    
    Appointment(String appointmentType, String appointmentStartDate, String appointmentEndDate, Boolean availability, int businessID, int customerID, String cost, int id){
        this.appointmentType = appointmentType;
        this.appointmentStartDate = appointmentStartDate;
        this.appointmentEndDate = appointmentEndDate;
        this.availability = availability;
        this.provider = findBusiness(businessID);
        this.customer = findUser(customerID);
        this.cost = cost;
        this.id = id;
    }
    public String getType(){
        return appointmentType;
    }

    public String getStartDate(){
        return appointmentStartDate;
    }

    public String getEndDate(){
        return appointmentEndDate;
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

    public String getCost(){
        return cost;
    }

    public int getID(){
        return id;
    }

    public void setType(String newType){
        this.appointmentType = newType;
    }

    public void setStartDate(String newDate){
        this.appointmentStartDate = newDate;
    }

    public void setEndDate(String newDate){
        this.appointmentEndDate = newDate;
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

    public void setCost(String newCost){
        this.cost = newCost;
    }

    public void setID(int id){
        this.id = id;
    }

    public User findUser(int id){
        User tempUser;
        try{
            FileReader fileReaderUser = new FileReader("userList.csv");
            BufferedReader br = new BufferedReader(fileReaderUser);
            String line = "";
            String[] tempArr;

            while((line = br.readLine()) != null){
                    tempArr = line.split(",");
                    tempUser = new User();
                    tempUser.setName(tempArr[0]);
                    tempUser.setEmail(tempArr[1]);
                    tempUser.setID(Integer.parseInt(tempArr[2]));

                    //check to see if email and password are correctly inputted
                    if(tempUser.getID() == id){
                        br.close();
                        fileReaderUser.close();
                        return tempUser;
                    }
            }

            br.close();
            fileReaderUser.close();

        }catch(IOException except){
            System.out.println(except);
        }

        tempUser = new User();
        return tempUser;
    }

    public Business findBusiness(int id){
        Business tempBusiness;
        try{
            FileReader fileReaderUser = new FileReader("businessList.csv");
            BufferedReader br = new BufferedReader(fileReaderUser);
            String line = "";
            String[] tempArr;

            while((line = br.readLine()) != null){
                tempArr = line.split(",");
                tempBusiness = new Business();
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

        tempBusiness = new Business();
        return tempBusiness;
    }

    public ZonedDateTime stringToStartDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a z");
        ZonedDateTime dateTime = ZonedDateTime.parse(this.appointmentStartDate, formatter);
        return dateTime;
    }

    public ZonedDateTime stringToEndDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a z");
        ZonedDateTime dateTime = ZonedDateTime.parse(this.appointmentEndDate, formatter);
        return dateTime;
    }
}
