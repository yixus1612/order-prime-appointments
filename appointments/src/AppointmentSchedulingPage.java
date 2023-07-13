

import java.io.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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

    private Rectangle scheduleTabRectangle, calendarTabRectangle, appointmentsTabRectangle, settingsTabRectangle;
    private Text scheduleTabText, calendarTabText, appointmentsTabText, settingsTabText;
    private Rectangle buffer1, buffer2;
    private GridPane center;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a z");
    private User userLoggedin;
    private SceneSwitcher switcher;

    private BorderPane layout = new BorderPane();
    private Button backButton = new Button("Back");
    private Button searchButton = new Button("Search");
    private List <Label> appointments = new ArrayList<Label>();
    private TextField businessName = new TextField();

    private String previousSearch = null;
    private VBox searchResults = new VBox();

    public AppointmentSchedulingPage(Stage primaryStage){

        switcher = new SceneSwitcher(primaryStage);

        userLoggedin = (User) primaryStage.getUserData();

        HBox sidebar = sideBar(primaryStage);
        layout.setLeft(sidebar);

        center = new GridPane();
        center.setPrefWidth(490);
        VBox mainpage= mainPage(primaryStage);
        center.add(mainpage, 1, 1);
        center.setAlignment(Pos.TOP_CENTER);

        layout.setCenter(center);

        appointmentSchedulingPage = new Scene(layout, 600, 500);
    }

    public HBox sideBar(Stage primaryStage){

        ImageView imageView = new ImageView();
        imageView.setImage(userLoggedin.getProfilePic());
        imageView.setFitHeight(65);
        imageView.setFitWidth(65);
        StackPane pfp = new StackPane(imageView);
        pfp.setAlignment(Pos.CENTER);

        Text nameText = new Text(userLoggedin.getName());
        StackPane name = new StackPane(nameText);
        name.setAlignment(Pos.CENTER);

        scheduleTabText = new Text("  Schedule");
        scheduleTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        scheduleTabRectangle = new Rectangle(110,25, Color.web("#f2efd0"));
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
        tabStack.getChildren().addAll(buffer1, pfp, name, buffer2, scheduleTab, calendarTab, appointmentsTab, settingsTab);
        
        HBox sidebar = new HBox(tabStack, sidebarSeparator);
        sidebar.setBackground(new Background(new BackgroundFill(Color.web("#4681e0"), null, null)));

        appointmentsTabRectangle.setOnMouseClicked(e->switcher.switchToAppointmentsPage(appointmentSchedulingPage.getWindow(), primaryStage, userLoggedin.appointmentList));
        appointmentsTabText.setOnMouseClicked(e -> switcher.switchToAppointmentsPage(appointmentSchedulingPage.getWindow(), primaryStage, userLoggedin.appointmentList));

        calendarTabRectangle.setOnMouseClicked(e -> switcher.switchToCalendarPage(appointmentSchedulingPage.getWindow(), primaryStage));
        calendarTabText.setOnMouseClicked(e -> switcher.switchToCalendarPage(appointmentSchedulingPage.getWindow(), primaryStage));

        settingsTabRectangle.setOnMouseClicked(e -> switcher.switchToSettingsPage(appointmentSchedulingPage.getWindow(), primaryStage));
        settingsTabText.setOnMouseClicked(e -> switcher.switchToSettingsPage(appointmentSchedulingPage.getWindow(), primaryStage));

        return sidebar;

    }

    public VBox mainPage(Stage primaryStage){
        HBox setUp = new HBox();
        VBox appointmentColumn = new VBox();
        Label title = new Label("Schedule An Appointment");
        Label spacingBuffer = new Label(" ");
        spacingBuffer.setFont(Font.font("Arial", FontWeight.BOLD, 90));

        HBox businessBox = new HBox();
        Label businessLabel = new Label("Business Name: ");
        businessName.setPromptText("Business Name");
        businessBox.getChildren().addAll(businessLabel, businessName);

        setUp.setPrefWidth(200);
        setUp.setSpacing(2);
        backButton.setMinWidth(97.5);
        searchButton.setMinWidth(97.5);
        setUp.getChildren().addAll(backButton, searchButton);
        setUp.setAlignment(Pos.CENTER);

        backButton.setOnAction(e->switcher.switchToCalendarPage(appointmentSchedulingPage.getWindow(), primaryStage));

        appointmentColumn.getChildren().addAll(spacingBuffer, title, businessBox, setUp);
        appointmentColumn.setSpacing(5);
        appointmentColumn.setAlignment(Pos.CENTER);

        search(primaryStage, appointmentColumn);

        return appointmentColumn;


    }

    public void search(Stage primaryStage, VBox home){
        searchButton.setOnAction(e->{
            home.getChildren().remove(searchResults);
            searchResults.getChildren().clear();
            searchResults = new VBox();
            searchResults.setSpacing(2);

            String userInput = businessName.getText();
                
            try{
                //set up FileReader
                FileReader fileReaderAccount = new FileReader("appointmentList.csv");
                BufferedReader br = new BufferedReader(fileReaderAccount);
                String line = "";
                String[] tempArr;

                Appointment tempAppointment;
                Business tempBusiness = new Business();
                User tempCustomer = new User();
    
                int counter = 0;
                //read in data and create list of available appointments
                while((line = br.readLine()) != null){
                    tempArr = line.split(",");
                    tempAppointment = new Appointment(tempArr[0], tempArr[1], Boolean.parseBoolean(tempArr[2]), Integer.parseInt(tempArr[3]), Integer.parseInt(tempArr[4]), tempArr[5], Integer.parseInt(tempArr[6]));

                    tempBusiness = tempAppointment.getProvider();
                    Button signUpButton = new Button("Schedule");
       
                    
                    if(userInput.equals(tempBusiness.getType()) && Boolean.parseBoolean(tempArr[2])){
                        HBox column = new HBox();
                        signUp(primaryStage, signUpButton, tempAppointment);
                        column.getChildren().addAll(new Label(tempBusiness.getType() + " " + tempArr[0] + " " + tempBusiness.getName() + "        "), signUpButton);
                        searchResults.getChildren().add(column);
                        counter++;
                    }
                }

                if(counter == 0){
                    Label noAppointments = new Label("There are no appointments at this time.");
                    searchResults.getChildren().add(noAppointments);
                }
                home.getChildren().add(searchResults);
                
                br.close();
            }catch(IOException except){
                System.out.println(except);
            }

        });
    }

    public void signUp(Stage primaryStage, Button button, Appointment appointment){
        button.setOnAction(e->{
            previousSearch = null;

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
                    System.out.println(tempAppointment.findUser(tempCustomer).getName());
   
                    //keep note if email is found
                    if(tempCustomer == userLoggedin.getID() && tempDate.isEqual(appointment.stringToDate())){
                        alreadyExists = true;
                        System.out.println("You already scheduled an appointment at this time");
                        break;
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