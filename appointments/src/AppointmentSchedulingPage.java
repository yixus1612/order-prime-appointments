

import java.io.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AppointmentSchedulingPage {

    public Scene appointmentSchedulingPage;

    public Rectangle homeTabRectangle, calendarTabRectangle, profileTabRectangle, placesTabRectangle, paymentTabRectangle, settingsTabRectangle;
    public Text homeTabText, profileTabText, calendarTabText, placesTabText, settingsTabText;
    public Rectangle profilePicture;
    public Rectangle buffer1, buffer2;
    public GridPane center;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a z");
    private User userLoggedin;
    private SceneSwitcher switcher;

    private BorderPane layout = new BorderPane();
    TextField businessName = new TextField();
    Button backButton = new Button("Back");
    Button searchButton = new Button("Search");
    List <Label> appointments = new ArrayList<Label>();

    public AppointmentSchedulingPage(Stage primaryStage){

        switcher = new SceneSwitcher(primaryStage);

        userLoggedin = (User) primaryStage.getUserData();

        HBox sidebar = sideBar(primaryStage);
        layout.setLeft(sidebar);

        center = new GridPane();
        center.setPrefWidth(490);
        VBox mainpage= mainPage(primaryStage);
        center.add(mainpage, 1, 1);

        layout.setCenter(center);

        appointmentSchedulingPage = new Scene(layout, 600, 500);
    }

    public HBox sideBar(Stage primaryStage){
        User userLoggedin = (User) primaryStage.getUserData();

        // FIXME this should display the user's profile picture
        profilePicture = new Rectangle(65, 65, Color.CORAL);
        StackPane pfp = new StackPane(profilePicture);
        pfp.setAlignment(Pos.CENTER);

        // FIXME this should display the user's name
        Text nameText = new Text(userLoggedin.getName());
        StackPane name = new StackPane(nameText);
        name.setAlignment(Pos.CENTER);

        homeTabText = new Text("  Home");
        homeTabText.setFill(Color.WHITE);
        homeTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        homeTabRectangle = new Rectangle(110,25, Color.web("#3064b8"));
        StackPane homeTab = new StackPane();
        homeTab.getChildren().addAll(homeTabRectangle, homeTabText);
        homeTab.setAlignment(Pos.CENTER_LEFT);

        profileTabText = new Text("  Profile");
        profileTabText.setFill(Color.WHITE);
        profileTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        profileTabRectangle = new Rectangle(110,25, Color.web("#3064b8"));
        StackPane profileTab = new StackPane();
        profileTab.getChildren().addAll(profileTabRectangle, profileTabText);
        profileTab.setAlignment(Pos.CENTER_LEFT);

        calendarTabText = new Text("  Calendar");
        calendarTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        calendarTabRectangle = new Rectangle(110,25, Color.web("#f2efd0"));
        StackPane calendarTab = new StackPane();
        calendarTab.getChildren().addAll(calendarTabRectangle, calendarTabText);
        calendarTab.setAlignment(Pos.CENTER_LEFT);

        placesTabText = new Text("  Your Places");
        placesTabText.setFill(Color.WHITE);
        placesTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        placesTabRectangle = new Rectangle(110,25, Color.web("#3064b8"));
        StackPane placesTab = new StackPane();
        placesTab.getChildren().addAll(placesTabRectangle, placesTabText);
        placesTab.setAlignment(Pos.CENTER_LEFT);

        settingsTabText = new Text("  Settings");
        settingsTabText.setFill(Color.WHITE);
        settingsTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        settingsTabRectangle = new Rectangle(110,25, Color.web("#3064b8"));
        StackPane settingsTab = new StackPane();
        settingsTab.getChildren().addAll(settingsTabRectangle, settingsTabText);
        settingsTab.setAlignment(Pos.CENTER_LEFT);

        Line sidebarSeparator = new Line(110, 0, 110, 1000);
        sidebarSeparator.setFill(Color.BLACK);

        buffer1 = new Rectangle(5,10, Color.web("#4681e0"));
        buffer2 = new Rectangle(5,5, Color.web("#4681e0"));

        VBox tabStack = new VBox();
        tabStack.getChildren().addAll(buffer1, pfp, name, buffer2, homeTab, profileTab, calendarTab, placesTab, settingsTab);
        
        HBox sidebar = new HBox(tabStack, sidebarSeparator);
        sidebar.setBackground(new Background(new BackgroundFill(Color.web("#4681e0"), null, null)));

        homeTabRectangle.setOnMouseClicked(e -> switcher.switchToHomePage(appointmentSchedulingPage.getWindow(), primaryStage));
        homeTabText.setOnMouseClicked(e -> switcher.switchToHomePage(appointmentSchedulingPage.getWindow(), primaryStage));

        profileTabRectangle.setOnMouseClicked(e -> switcher.switchToProfilePage(appointmentSchedulingPage.getWindow(), primaryStage));
        profileTabText.setOnMouseClicked(e -> switcher.switchToProfilePage(appointmentSchedulingPage.getWindow(), primaryStage));

        placesTabRectangle.setOnMouseClicked(e -> switcher.switchToPlacesPage(appointmentSchedulingPage.getWindow(), primaryStage));
        placesTabText.setOnMouseClicked(e -> switcher.switchToPlacesPage(appointmentSchedulingPage.getWindow(), primaryStage));

        settingsTabRectangle.setOnMouseClicked(e -> switcher.switchToSettingsPage(appointmentSchedulingPage.getWindow(), primaryStage));
        settingsTabText.setOnMouseClicked(e -> switcher.switchToSettingsPage(appointmentSchedulingPage.getWindow(), primaryStage));

        return sidebar;

    }

    public VBox mainPage(Stage primaryStage){
        HBox setUp = new HBox();
        VBox appointmentColumn = new VBox();
        Label title = new Label("Schedule An Appointment");

        businessName.setPromptText("Business Name");

        setUp.setPrefWidth(200);
        backButton.setMinWidth(97.5);
        searchButton.setMinWidth(97.5);
        setUp.getChildren().addAll(backButton, searchButton);
        setUp.setAlignment(Pos.CENTER);

        backButton.setOnAction(e->switcher.switchToCalendarPage(appointmentSchedulingPage.getWindow(), primaryStage));

        appointmentColumn.getChildren().addAll(title, businessName, setUp);
        appointmentColumn.setSpacing(5);
        appointmentColumn.setAlignment(Pos.CENTER);

        search(primaryStage, appointmentColumn);

        return appointmentColumn;


    }

    public void search(Stage primaryStage, VBox home){
        searchButton.setOnAction(e->{
            String userInput = businessName.getText();
            HBox column = new HBox();
            try{
               
                //set up FileReader
                FileReader fileReaderAccount = new FileReader("appointmentList.csv");
                BufferedReader br = new BufferedReader(fileReaderAccount);
                String line = "";
                String[] tempArr;

                Appointment tempAppointment;
                Business tempBusiness = new Business();
                User tempCustomer = new User();
       
                //read in data and create list of available appointments
                int counter = 0;
                while((line = br.readLine()) != null){
                    tempArr = line.split(",");
                    tempAppointment = new Appointment(tempArr[0], tempArr[1], Boolean.parseBoolean(tempArr[2]), Integer.parseInt(tempArr[3]), Integer.parseInt(tempArr[4]), tempArr[5], Integer.parseInt(tempArr[6]));

                    tempBusiness = tempAppointment.findBusiness(Integer.parseInt(tempArr[3]));
                    Button signUpButton = new Button("schedule");
       
                    
                    if(userInput.equals(tempBusiness.getType()) && Boolean.parseBoolean(tempArr[2])){
                        signUp(primaryStage, signUpButton, tempAppointment);
                        column.getChildren().addAll(new Label(tempBusiness.getType() + " " + tempArr[0] + " " + tempBusiness.getName() + "        "), signUpButton);
                        home.getChildren().add(column);
                        counter++;
                    }
                }
                br.close();
            }catch(IOException except){
                System.out.println(except);
            }

        });
    }

    public void signUp(Stage primaryStage, Button button, Appointment appointment){
        button.setOnAction(e->{
            
            Boolean alreadyExists = false;
            List<Appointment> appointmentList = new ArrayList<>();
            try{
                //set up FileReader
                FileReader fileReaderAccount = new FileReader("appointmentList.csv");
                BufferedReader br = new BufferedReader(fileReaderAccount);
                String line = "";
                String[] tempArr;
                int tempCustomer;
                ZonedDateTime tempDate;
                Appointment tempAppointment;
   
                //read in data and determine if appointment already exists
                while((line = br.readLine()) != null){
                    tempArr = line.split(",");
                    tempAppointment = new Appointment(tempArr[0], tempArr[1], Boolean.parseBoolean(tempArr[2]), Integer.parseInt(tempArr[3]), Integer.parseInt(tempArr[4]), tempArr[5], Integer.parseInt(tempArr[6]));
                    tempDate = ZonedDateTime.parse(tempArr[1], formatter);
                    tempCustomer = Integer.parseInt(tempArr[4]);
   
                    //keep note if email is found
                    if(tempCustomer == appointment.getCustomer().getID() && tempDate.isEqual(appointment.stringToDate())){
                        alreadyExists = true;
                        System.out.println("Appointment already exists");
                    }else if(appointment.getID() == Integer.parseInt(tempArr[6])){
                        tempAppointment.setCustomer(userLoggedin.getID());
                        tempAppointment.setAvailability(false);
                        userLoggedin.addAppointment(tempAppointment);
                        primaryStage.setUserData(userLoggedin);
                    }

                    appointmentList.add(tempAppointment);

                }
                br.close();
            }catch(IOException except){
                System.out.println(except);
            }

            try{              
                FileWriter fileWriterUser = new FileWriter("appointmentList.csv", false);

                if(!alreadyExists){
                    for(Appointment a : appointmentList){
                        fileWriterUser.write(a.getType() + "," + a.getDate() + "," + a.getAvailability() + "," + a.getProvider().getID() + "," + a.getCustomer().getID() + "," + a.getCost() + "," + a.getID() + "\n");
                    }
                }

                fileWriterUser.close();

            }catch(IOException except){
                System.out.println(except);
            }

            switcher.switchToAppointmentSchedulingPage(appointmentSchedulingPage.getWindow(), primaryStage);

        });
    }
}