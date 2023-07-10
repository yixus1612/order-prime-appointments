

import java.io.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class AppointmentCreationPage {

    public Scene appointmentCreationPage;

    public Rectangle homeTabRectangle, calendarTabRectangle, profileTabRectangle, placesTabRectangle, paymentTabRectangle, settingsTabRectangle;
    public Text homeTabText, profileTabText, calendarTabText, placesTabText, settingsTabText;
    public Rectangle profilePicture;
    public Rectangle buffer1, buffer2;
    public GridPane center;
    public Business businessLoggedin;
    public ZonedDateTime date;
    public Appointment createdAppointment = new Appointment();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a z");

    Button backButton = new Button("Back");
    Button createButton = new Button("Create");
    TextField cost = new TextField();
    TextField appointmentName = new TextField();

    private BorderPane layout = new BorderPane();
    private SceneSwitcher switcher;

    public AppointmentCreationPage(Stage primaryStage){

        switcher = new SceneSwitcher(primaryStage);
        
        businessLoggedin = (Business) primaryStage.getUserData();

        HBox sidebar = sideBar(primaryStage);
        layout.setLeft(sidebar);

        center = new GridPane();
        center.setPrefWidth(490);
        VBox mainpage = mainPage(primaryStage);
        center.add(mainpage, 1, 1);
        layout.setCenter(center);

        

        appointmentCreationPage = new Scene(layout, 600, 500);
    }

    public HBox sideBar(Stage primaryStage){

        // FIXME this should display the user's profile picture
        profilePicture = new Rectangle(65, 65, Color.CORAL);
        StackPane pfp = new StackPane(profilePicture);
        pfp.setAlignment(Pos.CENTER);

        // FIXME this should display the user's name
        Text nameText = new Text(businessLoggedin.getName());
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

        homeTabRectangle.setOnMouseClicked(e -> switcher.switchToHomePageBusiness(appointmentCreationPage.getWindow(), primaryStage));
        homeTabText.setOnMouseClicked(e -> switcher.switchToHomePageBusiness(appointmentCreationPage.getWindow(), primaryStage));

        profileTabRectangle.setOnMouseClicked(e -> switcher.switchToProfilePageBusiness(appointmentCreationPage.getWindow(), primaryStage));
        profileTabText.setOnMouseClicked(e -> switcher.switchToProfilePageBusiness(appointmentCreationPage.getWindow(), primaryStage));

        placesTabRectangle.setOnMouseClicked(e -> switcher.switchToPlacesPageBusiness(appointmentCreationPage.getWindow(), primaryStage));
        placesTabText.setOnMouseClicked(e -> switcher.switchToPlacesPageBusiness(appointmentCreationPage.getWindow(), primaryStage));

        settingsTabRectangle.setOnMouseClicked(e -> switcher.switchToSettingsPageBusiness(appointmentCreationPage.getWindow(), primaryStage));
        settingsTabText.setOnMouseClicked(e -> switcher.switchToSettingsPageBusiness(appointmentCreationPage.getWindow(), primaryStage));

        return sidebar;

    }

    public VBox mainPage(Stage primaryStage){
        HBox setUp = new HBox();
        VBox appointmentColumn = new VBox();
        Label title = new Label("Create An Appointment");

        HBox dateBox = new HBox();

        cost.setPromptText("Cost");
        cost.setPrefWidth(200);
        System.out.println(cost.getText());
        System.out.println(createdAppointment.getCost());

        ObservableList<String> hours = FXCollections.observableArrayList(
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
        );
        final ComboBox comboBox = new ComboBox(hours);

        ObservableList<String> mins = FXCollections.observableArrayList(
            "00", "15", "30", "45"
        );
        final ComboBox comboBox2 = new ComboBox(mins);

        ObservableList<String> time = FXCollections.observableArrayList(
            "AM", "PM"
        );
        final ComboBox comboBox3 = new ComboBox(time);

        createAppointment(primaryStage, comboBox,comboBox2,comboBox3);

        dateBox.getChildren().addAll(comboBox, comboBox2, comboBox3);

        appointmentName.setPromptText("Appointment Name");

        setUp.setPrefWidth(200);
        backButton.setMinWidth(97.5);
        createButton.setMinWidth(97.5);
        setUp.getChildren().addAll(backButton, createButton);
        setUp.setAlignment(Pos.CENTER);
        backButton.setOnAction(e->switcher.switchToCalendarPage(appointmentCreationPage.getWindow(), primaryStage));

        appointmentColumn.getChildren().addAll(title, appointmentName, dateBox, cost, setUp);
        appointmentColumn.setSpacing(5);
        appointmentColumn.setAlignment(Pos.CENTER);


        return appointmentColumn;


    }

    public void createAppointment(Stage primaryStage, ComboBox hour, ComboBox min, ComboBox amPM){
        createButton.setOnAction(e->{

            //sets up current info for appointemnt
            createdAppointment.setType(appointmentName.getText());
            createdAppointment.setProvider(businessLoggedin.getID());
            createdAppointment.setCost(cost.getText());
            if(((String) amPM.getSelectionModel().getSelectedItem()).equals("PM")){}
            createdAppointment.setDate("2023-07-11 " + (String) hour.getSelectionModel().getSelectedItem() + ":" + (String) min.getSelectionModel().getSelectedItem() + ":00 " + (String) amPM.getSelectionModel().getSelectedItem() + " -05:00");
            Random random = new Random();
            createdAppointment.setID(random.nextInt(1000000000));

            //checks to see if appointment is already created
            Boolean alreadyExists = false;
             
            if(new File("appointmentList.csv").exists()){
                try{
               
                    //set up FileReader
                    FileReader fileReaderAccount = new FileReader("appointmentList.csv");
                    BufferedReader br = new BufferedReader(fileReaderAccount);
                    String line = "";
                    String[] tempArr;
                    int tempProvider;
                    ZonedDateTime tempDate;
       
                    //read in data and determine if appointment already exists
                    while((line = br.readLine()) != null){
                        tempArr = line.split(",");
                        System.out.println(tempArr[1]);
                        tempDate = ZonedDateTime.parse(tempArr[1], formatter);
                        tempProvider = Integer.parseInt(tempArr[3]);
       
                        //keep note if email is found
                        if(tempProvider == createdAppointment.getProvider().getID() && tempDate.isEqual(createdAppointment.stringToDate())){
                            alreadyExists = true;
                            System.out.println("Appointment already exists");
                        }
                    }
                    br.close();
                }catch(IOException except){
                    System.out.println(except);
                }
            }

            //writes appointment to file
            try{              
                FileWriter fileWriterUser = new FileWriter("appointmentList.csv", true);

                //checks to see if appointment is before current time
                System.out.println(createdAppointment.stringToDate().isBefore(ZonedDateTime.now()));
                if(createdAppointment.stringToDate().isBefore(ZonedDateTime.now()) || alreadyExists){
                    System.out.println("Please choose a valid date");
                }else{
                    fileWriterUser.write(createdAppointment.getType() + "," + createdAppointment.getDate() + "," + createdAppointment.getAvailability() + "," + createdAppointment.getProvider().getID() + "," + 0 + "," + createdAppointment.getCost() + "," + createdAppointment.getID() + "\n");
                    Business businessLoggedIn = (Business) primaryStage.getUserData();
                    businessLoggedin.addAppointment(createdAppointment);
                    primaryStage.setUserData(businessLoggedIn);
                }

                fileWriterUser.close();

            }catch(IOException except){
                System.out.println(except);
            }

            switcher.switchToAppointmentCreationPage(appointmentCreationPage.getWindow(), primaryStage);
        });
    }

}
