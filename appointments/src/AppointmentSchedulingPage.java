

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
    private TextField businessName = new TextField();

    private VBox searchResults = new VBox();
    private Label errorLabel = new Label();

    public AppointmentSchedulingPage(Stage primaryStage, Label label){

        switcher = new SceneSwitcher(primaryStage);

        errorLabel = label;

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

        //set up title
        HBox setUp = new HBox();
        VBox appointmentColumn = new VBox();
        Label title = new Label("Schedule An Appointment");
        Label spacingBuffer = new Label(" ");
        spacingBuffer.setFont(Font.font("Arial", FontWeight.BOLD, 90));

        // set up business search box
        HBox businessBox = new HBox();
        Label businessLabel = new Label("Business Name: ");
        Label businessBuffer = new Label("\t\t\t  ");
        businessName.setPromptText("Business Name");
        businessName.setMinWidth(182);
        businessBox.getChildren().addAll(businessLabel, businessName, businessBuffer);
        businessBox.setAlignment(Pos.CENTER_LEFT);

        //set sizes
        setUp.setPrefWidth(200);
        setUp.setSpacing(2);
        backButton.setMinWidth(90);
        searchButton.setMinWidth(90);
        setUp.getChildren().addAll(backButton, searchButton);
        setUp.setAlignment(Pos.CENTER);

        //switch to calendar page with back button
        backButton.setOnAction(e->switcher.switchToCalendarPage(appointmentSchedulingPage.getWindow(), primaryStage));

        //add everything to main page
        appointmentColumn.getChildren().addAll(spacingBuffer, title, businessBox, setUp, errorLabel);
        appointmentColumn.setSpacing(5);
        appointmentColumn.setAlignment(Pos.CENTER);

        //search for business
        search(primaryStage, appointmentColumn);

        return appointmentColumn;


    }

    public void search(Stage primaryStage, VBox home){
        searchButton.setOnAction(e->{

            //clear past search
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
    
                int counter = 0;
                //read in data and create list of available appointments
                while((line = br.readLine()) != null){
                    tempArr = line.split(",");
                    tempAppointment = new Appointment(tempArr[0], tempArr[1], tempArr[2], Boolean.parseBoolean(tempArr[3]), Integer.parseInt(tempArr[4]), Integer.parseInt(tempArr[5]), tempArr[6], Integer.parseInt(tempArr[7]));

                    tempBusiness = tempAppointment.getProvider();
                    Button signUpButton = new Button("Schedule");
       
                    //check to see if its the correct business and if the appointment is available then display
                    if(userInput.equals(tempBusiness.getType()) && Boolean.parseBoolean(tempArr[3])){
                        HBox column = new HBox();
                        signUp(primaryStage, signUpButton, tempAppointment);
                        Label app = new Label(tempBusiness.getType() + " " + tempArr[0] + " " + tempBusiness.getName() + "        ");
                        column.getChildren().addAll(app, signUpButton);
                        viewApp(column, primaryStage, tempAppointment);
                        searchResults.getChildren().addAll(column);
                        counter++;
                    }
                }

                //sent note if there are no appointments
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

    public void viewApp(HBox column, Stage primaryStage, Appointment appointment){
        column.setOnMouseClicked(e->{
            switcher.switchToViewAppointmentPage(appointmentSchedulingPage.getWindow(), primaryStage, appointment);
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
                ZonedDateTime tempStartDate, tempEndDate;
                Appointment tempAppointment;
   
                //read in data and determine if appointment already exists
                while((line = br.readLine()) != null){
                    tempArr = line.split(",");
                    tempAppointment = new Appointment(tempArr[0], tempArr[1], tempArr[2], Boolean.parseBoolean(tempArr[3]), Integer.parseInt(tempArr[4]), Integer.parseInt(tempArr[5]), tempArr[6], Integer.parseInt(tempArr[7]));
                    tempStartDate = ZonedDateTime.parse(tempArr[1], formatter);
                    tempEndDate = ZonedDateTime.parse(tempArr[2], formatter);
                    tempCustomer = Integer.parseInt(tempArr[5]);
   
                    //check to see if another appointment is scheduled
                    if(tempCustomer == userLoggedin.getID() && ((appointment.stringToStartDate().isBefore(tempEndDate) && appointment.stringToStartDate().isAfter(tempStartDate)) || (appointment.stringToEndDate().isBefore(tempStartDate) && appointment.stringToEndDate().isAfter(tempEndDate)))){
                        alreadyExists = true;
                        errorLabel.setText("An appointment is already schedule for this time.");
                        break;
                    }else if(appointment.getID() == Integer.parseInt(tempArr[7])){
                        tempAppointment.setCustomer(userLoggedin.getID());
                        tempAppointment.setAvailability(false);
                        userLoggedin.addAppointment(tempAppointment);
                        primaryStage.setUserData(userLoggedin);
                        errorLabel.setText("Appointment Scheduled");
                    }

                    appointmentList.add(tempAppointment);
                }
                br.close();
            }catch(IOException except){
                System.out.println(except);
            }

            //write to file with new information
            try{              
                FileWriter fileWriterUser = new FileWriter("appointmentList.csv", false);

                if(!alreadyExists){
                    for(Appointment a : appointmentList){
                        fileWriterUser.write(a.getType() + "," + a.getStartDate() + "," + a.getEndDate() + "," + a.getAvailability() + "," + a.getProvider().getID() + "," + a.getCustomer().getID() + "," + a.getCost() + "," + a.getID() + "\n");
                    }
                }

                fileWriterUser.close();

            }catch(IOException except){
                System.out.println(except);
            }

            //refresh page
            switcher.switchToAppointmentSchedulingPage(appointmentSchedulingPage.getWindow(), primaryStage, errorLabel);

        });
    }
}