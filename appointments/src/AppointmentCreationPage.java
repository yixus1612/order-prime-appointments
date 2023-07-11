

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

    public Rectangle createTabRectangle, calendarTabRectangle, appointmentsTabRectangle, settingsTabRectangle;
    Text createTabText, calendarTabText, appointmentsTabText, settingsTabText;
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
        center.setAlignment(Pos.TOP_CENTER);
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

        createTabText = new Text("  Create");
        createTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        createTabRectangle = new Rectangle(110,25, Color.web("#f2efd0"));
        StackPane homeTab = new StackPane();
        homeTab.getChildren().addAll(createTabRectangle, createTabText);
        homeTab.setAlignment(Pos.CENTER_LEFT);

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
        tabStack.getChildren().addAll(buffer1, pfp, name, buffer2, homeTab, calendarTab, appointmentsTab, settingsTab);
        
        HBox sidebar = new HBox(tabStack, sidebarSeparator);
        sidebar.setBackground(new Background(new BackgroundFill(Color.web("#4681e0"), null, null)));

        calendarTabRectangle.setOnMouseClicked(e -> switcher.switchToCalendarPageBusiness(appointmentCreationPage.getWindow(), primaryStage));
        calendarTabText.setOnMouseClicked(e -> switcher.switchToCalendarPageBusiness(appointmentCreationPage.getWindow(), primaryStage));

        appointmentsTabRectangle.setOnMouseClicked(e->switcher.switchToAppointmentsPageBusiness(appointmentCreationPage.getWindow(), primaryStage, businessLoggedin.appointmentList));
        appointmentsTabText.setOnMouseClicked(e -> switcher.switchToAppointmentsPageBusiness(appointmentCreationPage.getWindow(), primaryStage, businessLoggedin.appointmentList));

        settingsTabRectangle.setOnMouseClicked(e -> switcher.switchToSettingsPageBusiness(appointmentCreationPage.getWindow(), primaryStage));
        settingsTabText.setOnMouseClicked(e -> switcher.switchToSettingsPageBusiness(appointmentCreationPage.getWindow(), primaryStage));

        return sidebar;

    }

    public VBox mainPage(Stage primaryStage){
        HBox setUp = new HBox();
        VBox appointmentColumn = new VBox();
        Label title = new Label("Create An Appointment");
        Label spacingBuffer = new Label(" ");
        spacingBuffer.setFont(Font.font("Arial", FontWeight.BOLD, 90));

        HBox dateBox = new HBox();

        cost.setPromptText("Cost");
        cost.setPrefWidth(200);
        System.out.println(cost.getText());
        System.out.println(createdAppointment.getCost());

        ObservableList<String> months = FXCollections.observableArrayList(
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
        );
        final ComboBox comboBox1 = new ComboBox(months);
        comboBox1.setPromptText("Month");

        ObservableList<String> days = FXCollections.observableArrayList(
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
        );
        final ComboBox comboBox2 = new ComboBox(days);
        comboBox2.setPromptText("Day");

        ObservableList<String> years = FXCollections.observableArrayList(
            "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034"
        );
        final ComboBox comboBox3 = new ComboBox(years);
        comboBox3.setPromptText("Year");

        ObservableList<String> hours = FXCollections.observableArrayList(
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
        );
        final ComboBox comboBox4 = new ComboBox(hours);
        comboBox4.setPromptText("Hour");

        ObservableList<String> mins = FXCollections.observableArrayList(
            "00", "15", "30", "45"
        );
        final ComboBox comboBox5 = new ComboBox(mins);
        comboBox5.setPromptText("Min");

        ObservableList<String> time = FXCollections.observableArrayList(
            "AM", "PM"
        );
        final ComboBox comboBox6 = new ComboBox(time);
        comboBox6.setPromptText("AM/PM");

        createAppointment(primaryStage, comboBox1, comboBox2, comboBox3, comboBox4, comboBox5, comboBox6);

        dateBox.getChildren().addAll(comboBox1, comboBox2, comboBox3, comboBox4, comboBox5, comboBox6);
        dateBox.setSpacing(1);

        appointmentName.setPromptText("Appointment Name");

        setUp.setPrefWidth(200);
        backButton.setMinWidth(97.5);
        createButton.setMinWidth(97.5);
        setUp.getChildren().addAll(backButton, createButton);
        setUp.setAlignment(Pos.CENTER);
        setUp.setSpacing(3);
        backButton.setOnAction(e->switcher.switchToCalendarPage(appointmentCreationPage.getWindow(), primaryStage));

        appointmentColumn.getChildren().addAll(spacingBuffer,title, appointmentName, dateBox, cost, setUp);
        appointmentColumn.setSpacing(5);
        appointmentColumn.setAlignment(Pos.CENTER);


        return appointmentColumn;


    }

    public void createAppointment(Stage primaryStage, ComboBox month, ComboBox day, ComboBox year, ComboBox hour, ComboBox min, ComboBox amPM){
        createButton.setOnAction(e->{

            //sets up current info for appointemnt
            createdAppointment.setType(appointmentName.getText());
            createdAppointment.setProvider(businessLoggedin.getID());
            createdAppointment.setCost(cost.getText());
            if(((String) amPM.getSelectionModel().getSelectedItem()).equals("PM")){}
            createdAppointment.setDate((String) year.getSelectionModel().getSelectedItem() + "-" + (String) month.getSelectionModel().getSelectedItem() + "-" + (String) day.getSelectionModel().getSelectedItem() + " " + (String) hour.getSelectionModel().getSelectedItem() + ":" + (String) min.getSelectionModel().getSelectedItem() + ":00 " + (String) amPM.getSelectionModel().getSelectedItem() + " -05:00");
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
