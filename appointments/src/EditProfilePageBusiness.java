import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class EditProfilePageBusiness {

    public Scene settingsPage;
    public Rectangle scheduleTabRectangle, calendarTabRectangle, appointmentsTabRectangle, settingsTabRectangle;
    private static final String EMAIL_PATTERN = "^(.+)@(\\S+)$";
    Text scheduleTabText, calendarTabText, appointmentsTabText, settingsTabText;
    Rectangle profilePicture;
    Rectangle buffer1, buffer2;
    private Business businessLoggedin = new Business();
    private SceneSwitcher switcher;
    private Label errorLabel = new Label();

    public EditProfilePageBusiness(Stage primaryStage, Label label){

        switcher = new SceneSwitcher(primaryStage);

        errorLabel = label;

        businessLoggedin = (Business) primaryStage.getUserData();
        System.out.println(businessLoggedin.getName());

        BorderPane layout = new BorderPane();
        
        VBox mainPage = mainPage(primaryStage);
        layout.setCenter(mainPage);

        HBox sidebar = sideBar(primaryStage);
        layout.setLeft(sidebar);

        settingsPage = new Scene(layout, 600, 500);

    }

    public HBox sideBar(Stage primaryStage){

        ImageView imageView = new ImageView();
        imageView.setImage(businessLoggedin.getProfilePic());
        imageView.setFitHeight(65);
        imageView.setFitWidth(65);
        StackPane pfp = new StackPane(imageView);
        pfp.setAlignment(Pos.CENTER);

        Text nameText = new Text(businessLoggedin.getName());
        StackPane name = new StackPane(nameText);
        name.setAlignment(Pos.CENTER);

        scheduleTabText = new Text("  Create");
        scheduleTabText.setFill(Color.WHITE);
        scheduleTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        scheduleTabRectangle = new Rectangle(110,25, Color.web("#3064b8"));
        StackPane scheduleTab = new StackPane();
        scheduleTab.getChildren().addAll(scheduleTabRectangle, scheduleTabText);
        scheduleTab.setAlignment(Pos.CENTER_LEFT);

        calendarTabText = new Text("  Calendar");
        calendarTabText.setFill(Color.WHITE);
        calendarTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        calendarTabRectangle = new Rectangle(110,25, Color.web("#3064b8"));
        StackPane calendarTab = new StackPane();
        calendarTab.getChildren().addAll(calendarTabRectangle, calendarTabText);
        calendarTab.setAlignment(Pos.CENTER_LEFT);

        appointmentsTabText = new Text("  Appointments");
        appointmentsTabText.setFill(Color.WHITE);
        appointmentsTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        appointmentsTabRectangle = new Rectangle(110,25, Color.web("#3064b8"));
        StackPane appointmentsTab = new StackPane();
        appointmentsTab.getChildren().addAll(appointmentsTabRectangle, appointmentsTabText);
        appointmentsTab.setAlignment(Pos.CENTER_LEFT);

        settingsTabText = new Text("  Settings");
        settingsTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        settingsTabRectangle = new Rectangle(110,25, Color.web("#f2efd0"));
        StackPane settingsTab = new StackPane();
        settingsTab.getChildren().addAll(settingsTabRectangle, settingsTabText);
        settingsTab.setAlignment(Pos.CENTER_LEFT);

        Line sidebarSeparator = new Line(110, 0, 110, 1000);
        sidebarSeparator.setFill(Color.BLACK);

        buffer1 = new Rectangle(5,10, Color.web("#4681e0"));
        buffer2 = new Rectangle(5,5, Color.web("#4681e0"));

        VBox tabStack = new VBox();
        tabStack.getChildren().addAll(buffer1, pfp, name, buffer2, scheduleTab, calendarTab, appointmentsTab, settingsTab);
        
        HBox sidebar = new HBox(tabStack, sidebarSeparator);
        sidebar.setBackground(new Background(new BackgroundFill(Color.web("#4681e0"), null, null)));

        Label label = new Label();
        scheduleTabRectangle.setOnMouseClicked(e -> switcher.switchToAppointmentCreationPage(settingsPage.getWindow(), primaryStage, label));
        scheduleTabText.setOnMouseClicked(e -> switcher.switchToAppointmentCreationPage(settingsPage.getWindow(), primaryStage, label));

        appointmentsTabRectangle.setOnMouseClicked(e->switcher.switchToAppointmentsPageBusiness(settingsPage.getWindow(), primaryStage, businessLoggedin.appointmentList));
        appointmentsTabText.setOnMouseClicked(e -> switcher.switchToAppointmentsPageBusiness(settingsPage.getWindow(), primaryStage, businessLoggedin.appointmentList));

        calendarTabRectangle.setOnMouseClicked(e -> switcher.switchToCalendarPageBusiness(settingsPage.getWindow(), primaryStage));
        calendarTabText.setOnMouseClicked(e -> switcher.switchToCalendarPageBusiness(settingsPage.getWindow(), primaryStage));

        return sidebar;

    }

    public VBox mainPage(Stage primaryStage){
        //set up mainpage
        VBox center = new VBox();
        center.setAlignment(Pos.TOP_CENTER);
        Button submitButton = new Button("Submit");
        Button deleteButton = new Button("Delete");
        Button backButton = new Button("Back");
        HBox [] boxs = new HBox[4];
        for(int i = 0; i < 4; i++){
            boxs[i] = new HBox();
        }

        //set up first name field
        Label firstNameLabel = new Label("\t\t\t\tFirst Name:\t  ");
        TextField firstName = new TextField(businessLoggedin.getName().split(" (?!.* )")[0]);
        boxs[0].getChildren().addAll(firstNameLabel, firstName);

        //set up last name field
        Label lastNameLabel = new Label("\t\t\t\tLast Name:\t  ");
        TextField lastName = new TextField(businessLoggedin.getName().split(" (?!.* )")[1]);
        boxs[1].getChildren().addAll(lastNameLabel, lastName);

        //set up type field
        Label typeLabel = new Label("\t\t\t\tBusiness Name: ");
        TextField type = new TextField(businessLoggedin.getType());
        boxs[2].getChildren().addAll(typeLabel, type);

        //set up email field
        Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
        Label emailLabel = new Label("\t\t\t\tEmail:\t\t  ");
        TextField email = new TextField(businessLoggedin.getEmail());
        boxs[3].getChildren().addAll(emailLabel, email);

        submitButton.setOnAction(e->{
            Matcher emailMatcher = emailPattern.matcher(email.getText());
            //check for vaild name and email else send message
            if(emailMatcher.matches() && !(firstName.getText() == null || firstName.getText().trim().isEmpty()) && !(lastName.getText() == null || lastName.getText().trim().isEmpty())){
                businessLoggedin.setName(firstName.getText() + " " + lastName.getText());
                businessLoggedin.setEmail(email.getText());
                businessLoggedin.setType(type.getText());
                changeProfile(primaryStage);
                switcher.switchToSettingsPageBusiness(settingsPage.getWindow(), primaryStage);
            }else if((firstName.getText() == null || firstName.getText().trim().isEmpty()) || (lastName.getText() == null || lastName.getText().trim().isEmpty())){
                errorLabel.setText("Please enter a name");
            }else if(type.getText() == null){
                errorLabel.setText("Please enter a business name");
            }else{
                errorLabel.setText("Please enter a valid email");
            }
        });

        deleteButton.setOnAction(e->{
            System.out.println(businessLoggedin);
            delete(primaryStage);
        });

        backButton.setOnAction(e->{
            switcher.switchToSettingsPageBusiness(settingsPage.getWindow(), primaryStage);
        });

        HBox buttons = new HBox(submitButton, deleteButton, backButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(2);
        
        Label spacingBuffer1 = new Label(" ");
        Label spacingBuffer2 = new Label(" ");
        Label title = new Label("Profile");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        spacingBuffer1.setFont(Font.font("Arial", FontWeight.BOLD, 45));
        spacingBuffer2.setFont(Font.font("Arial", FontWeight.BOLD, 5));

        center.getChildren().addAll(spacingBuffer1, title, spacingBuffer2, boxs[0], boxs[1], boxs[2], boxs[3], buttons, errorLabel);
        center.setSpacing(2);
        return center;

    }

    public void changeProfile(Stage primaryStage){
        List<Business> businessList = new ArrayList<>();
        Boolean changeEmail = false;
        String oldEmail = "";
        try{
            //set up FileReader
            FileReader fileReaderAccount = new FileReader("businessList.csv");
            BufferedReader br = new BufferedReader(fileReaderAccount);
            String line = "";
            String[] tempArr;
            Business tempBusiness;
   
            //read in data and find business
            while((line = br.readLine()) != null){
                tempArr = line.split(",");
                tempBusiness = new Business(tempArr[0], tempArr[1], tempArr[2], Integer.parseInt(tempArr[3]));
   
                //keep note if business is found
                if(businessLoggedin.getID() == tempBusiness.getID()){
                    //determine if email was changed
                    if(businessLoggedin.getEmail() != tempBusiness.getEmail()){
                        oldEmail = tempBusiness.getEmail();
                        changeEmail = true;
                    //determine if business name was changed
                    }else if(businessLoggedin.getType() != tempBusiness.getType()){
                        changeAppointment(primaryStage, businessLoggedin.getType());
                    }
                    tempBusiness = businessLoggedin;
                }

                businessList.add(tempBusiness);

            }
            br.close();
        }catch(IOException except){
            System.out.println(except);
        }

        //write business list to file
        try{              
            FileWriter fileWriterUser = new FileWriter("businessList.csv", false);

            for(Business u : businessList){
                fileWriterUser.write(u.getName() + "," + u.getEmail() + "," + u.getType() + "," + u.getID() + "\n");
            }

            fileWriterUser.close();

        }catch(IOException except){
            System.out.println(except);
        }

        //if the email is change then write to account file
        if(changeEmail){
            List<String[]> accountList = new ArrayList<>();
            try{
                //set up FileReader
                FileReader fileReaderAccount = new FileReader("accountList.csv");
                BufferedReader br = new BufferedReader(fileReaderAccount);
                String line = "";
                String[] tempArr;
   
                //read in data and find account
                while((line = br.readLine()) != null){
                    tempArr = line.split(",");
                    //keep note if email is found
                    if(oldEmail.equals(tempArr[0])){
                        tempArr[0] = businessLoggedin.getEmail();
                    }
                    accountList.add(tempArr);

                }
                br.close();
            }catch(IOException except){
                System.out.println(except);
            }

            //write to account file with new list
            try{              
                FileWriter fileWriterUser = new FileWriter("accountList.csv", false);

                for(String[] s : accountList){
                    fileWriterUser.write(s[0] + "," + s[1] + "\n");
                }

                fileWriterUser.close();

            }catch(IOException except){
                System.out.println(except);
            }
        }

    }

    public void delete(Stage primaryStage){

        List<Appointment> totalAppointmentList = new ArrayList<>();
        try{
            //set up FileReader
            FileReader fileReaderAccount = new FileReader("appointmentList.csv");
            BufferedReader br = new BufferedReader(fileReaderAccount);
            String line = "";
            String[] tempArr;
            Appointment tempAppointment = new Appointment();
   
            //read in data and find appointment
            while((line = br.readLine()) != null){
                tempArr = line.split(",");
                tempAppointment.setType(tempArr[0]);
                tempAppointment.setStartDate(tempArr[1]);
                tempAppointment.setEndDate(tempArr[2]);
                tempAppointment.setAvailability(Boolean.parseBoolean(tempArr[3]));
                tempAppointment.setProvider(businessLoggedin.getID());
                tempAppointment.setCustomer(Integer.parseInt(tempArr[5]));
                tempAppointment.setCost(tempArr[6]);
                tempAppointment.setID(Integer.parseInt(tempArr[7]));
   
                //keep note if appointment is found and add to list if it doent have the business id
                if(businessLoggedin.getID() != tempAppointment.getProvider().getID()){
                    totalAppointmentList.add(tempAppointment);
                }

            }
            br.close();
        }catch(IOException except){
            System.out.println(except);
        }

        //write new appointment list to file
        try{              
            FileWriter fileWriterUser = new FileWriter("appointmentList.csv", false);

            for(Appointment a : totalAppointmentList){
                fileWriterUser.write(a.getType() + "," + a.getStartDate() + "," + a.getEndDate() + "," + a.getAvailability() + "," + a.getProvider().getID() + "," + a.getCustomer().getID() + "," + a.getCost() + "," + a.getID() + "\n");
            }

            fileWriterUser.close();

        }catch(IOException except){
            System.out.println(except);
        }

        List<Business> businessList = new ArrayList<>();
        try{
            //set up FileReader
            FileReader fileReaderAccount = new FileReader("businessList.csv");
            BufferedReader br = new BufferedReader(fileReaderAccount);
            String line = "";
            String[] tempArr;
            Business tempBusiness;
   
            //read in data and find businesss
            while((line = br.readLine()) != null){
                tempArr = line.split(",");
                tempBusiness = new Business(tempArr[0], tempArr[1], tempArr[2], Integer.parseInt(tempArr[3]));
   
                //keep note if business is found
                if(businessLoggedin.getID() != tempBusiness.getID()){
                    System.out.println(tempBusiness.getID());
                    businessList.add(tempBusiness);
                }

            }
            br.close();
        }catch(IOException except){
            System.out.println(except);
        }

        //write business list to file
        try{              
            FileWriter fileWriterUser = new FileWriter("businessList.csv", false);

            for(Business b : businessList){
                fileWriterUser.write(b.getName() + "," + b.getEmail() + "," + b.getID() + "\n");
            }

            fileWriterUser.close();

        }catch(IOException except){
            System.out.println(except);
        }

        List<String[]> accountList = new ArrayList<>();
        try{
            //set up FileReader
            FileReader fileReaderAccount = new FileReader("accountList.csv");
            BufferedReader br = new BufferedReader(fileReaderAccount);
            String line = "";
            String[] tempArr;
   
            //read in data and find account
            while((line = br.readLine()) != null){
                tempArr = line.split(",");
                //keep note if email is found
                if(!(businessLoggedin.getEmail().equals(tempArr[0]))){
                    accountList.add(tempArr);
                }

            }
            br.close();
        }catch(IOException except){
            System.out.println(except);
        }

        //write new account list to file
        try{              
            FileWriter fileWriterUser = new FileWriter("accountList.csv", false);

            for(String[] s : accountList){
                fileWriterUser.write(s[0] + "," + s[1] + "\n");
            }

            fileWriterUser.close();

        }catch(IOException except){
            System.out.println(except);
        }

        businessLoggedin = null;
        switcher.switchToLoginPage(settingsPage.getWindow(), primaryStage);
    }

    public void changeAppointment(Stage primaryStage, String newName){
        List<Appointment> totalAppointmentList = new ArrayList<>();
        try{
            //set up FileReader
            FileReader fileReaderAccount = new FileReader("appointmentList.csv");
            BufferedReader br = new BufferedReader(fileReaderAccount);
            String line = "";
            String[] tempArr;
            Appointment tempAppointment;
   
            //read in data and find appointment
            while((line = br.readLine()) != null){
                tempArr = line.split(",");
                tempAppointment = new Appointment(tempArr[0], tempArr[1], tempArr[2], Boolean.parseBoolean(tempArr[3]), Integer.parseInt(tempArr[4]), Integer.parseInt(tempArr[5]), tempArr[6], Integer.parseInt(tempArr[7]));

                //keep note if appointment is found and change it
                if(businessLoggedin.getID() == tempAppointment.getProvider().getID()){
                    tempAppointment.setType(newName);
                }

                totalAppointmentList.add(tempAppointment);

            }
            br.close();
        }catch(IOException except){
            System.out.println(except);
        }

        //write new appointment list to file
        try{              
            FileWriter fileWriterUser = new FileWriter("appointmentList.csv", false);

            for(Appointment a : totalAppointmentList){
                fileWriterUser.write(a.getType() + "," + a.getStartDate() + "," + a.getEndDate() + "," + a.getAvailability() + "," + a.getProvider().getID() + "," + a.getCustomer().getID() + "," + a.getCost() + "," + a.getID() + "\n");
            }

            fileWriterUser.close();

        }catch(IOException except){
            System.out.println(except);
        }

    }
}
