

import java.io.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class AppointmentCreationPage {

    //declare scene
    public Scene appointmentCreationPage;

    //declare graphics
    private Rectangle createTabRectangle, calendarTabRectangle, appointmentsTabRectangle, settingsTabRectangle;
    private Text createTabText, calendarTabText, appointmentsTabText, settingsTabText;
    private Rectangle buffer1, buffer2;
    private GridPane center;
    private Business businessLoggedin;
    private Appointment createdAppointment = new Appointment();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a z");

    //declare the interactive graphics
    private Button backButton = new Button("Back");
    private Button createButton = new Button("Create");
    private TextField cost = new TextField();
    private TextField appointmentName = new TextField();
    private Label errorLabel;

    //declare layout for scene
    private BorderPane layout = new BorderPane();

    //declare scene switcher for screen
    private SceneSwitcher switcher;

    //AppointmentCreationPage constructor/initializer
    public AppointmentCreationPage(Stage primaryStage, Label label){

        //declare scene switcher
        switcher = new SceneSwitcher(primaryStage);

        //set the error label from the previous search if applicable
        errorLabel = label;
        
        //find the business currently logged in
        businessLoggedin = (Business) primaryStage.getUserData();

        //set up the side bar
        HBox sidebar = sideBar(primaryStage);
        layout.setLeft(sidebar);

        //set up center page
        center = new GridPane();
        center.setPrefWidth(490);
        VBox mainpage = mainPage(primaryStage);
        center.add(mainpage, 1, 1);
        center.setAlignment(Pos.TOP_CENTER);
        layout.setCenter(center);
        
        //create scene
        appointmentCreationPage = new Scene(layout, 600, 500);
    }

    //create sidebar
    public HBox sideBar(Stage primaryStage){

        //create profile image
        ImageView imageView = new ImageView();
        imageView.setImage(businessLoggedin.getProfilePic());
        imageView.setFitHeight(65);
        imageView.setFitWidth(65);
        StackPane pfp = new StackPane(imageView);
        pfp.setAlignment(Pos.CENTER);

        //create name
        Text nameText = new Text(businessLoggedin.getName());
        StackPane name = new StackPane(nameText);
        name.setAlignment(Pos.CENTER);

        //create appointment creation tab
        createTabText = new Text("  Create");
        createTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        createTabRectangle = new Rectangle(110,25, Color.web("#f2efd0"));
        StackPane homeTab = new StackPane();
        homeTab.getChildren().addAll(createTabRectangle, createTabText);
        homeTab.setAlignment(Pos.CENTER_LEFT);

        //create calendar tab
        calendarTabText = new Text("  Calendar");
        calendarTabText.setFill(Color.WHITE);
        calendarTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        calendarTabRectangle = new Rectangle(110,25, Color.web("#3064b8"));
        StackPane calendarTab = new StackPane();
        calendarTab.getChildren().addAll(calendarTabRectangle, calendarTabText);
        calendarTab.setAlignment(Pos.CENTER_LEFT);

        //create appointments tab
        appointmentsTabText = new Text("  Appointments");
        appointmentsTabText.setFill(Color.WHITE);
        appointmentsTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        appointmentsTabRectangle = new Rectangle(110,25, Color.web("#3064b8"));
        StackPane appointmentsTab = new StackPane();
        appointmentsTab.getChildren().addAll(appointmentsTabRectangle, appointmentsTabText);
        appointmentsTab.setAlignment(Pos.CENTER_LEFT);

        //create settings tab
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

        //sets up calendar page switching
        calendarTabRectangle.setOnMouseClicked(e -> switcher.switchToCalendarPageBusiness(appointmentCreationPage.getWindow(), primaryStage));
        calendarTabText.setOnMouseClicked(e -> switcher.switchToCalendarPageBusiness(appointmentCreationPage.getWindow(), primaryStage));

        //sets up appointments page switching
        appointmentsTabRectangle.setOnMouseClicked(e->switcher.switchToAppointmentsPageBusiness(appointmentCreationPage.getWindow(), primaryStage, businessLoggedin.appointmentList));
        appointmentsTabText.setOnMouseClicked(e -> switcher.switchToAppointmentsPageBusiness(appointmentCreationPage.getWindow(), primaryStage, businessLoggedin.appointmentList));

        //sets up settings page switching
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

        ObservableList<String> months = FXCollections.observableArrayList(
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
        );
        final ComboBox<String> comboBox1 = new ComboBox<String>(months);
        comboBox1.setPromptText("Month");

        ObservableList<String> days = FXCollections.observableArrayList(
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
        );
        final ComboBox<String> comboBox2 = new ComboBox<String>(days);
        comboBox2.setPromptText("Day");

        ObservableList<String> years = FXCollections.observableArrayList(
            "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034"
        );
        final ComboBox<String> comboBox3 = new ComboBox<String>(years);
        comboBox3.setPromptText("Year");

        ObservableList<String> hours = FXCollections.observableArrayList(
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
        );
        final ComboBox<String> comboBox4 = new ComboBox<String>(hours);
        comboBox4.setPromptText("Hour");

        ObservableList<String> mins = FXCollections.observableArrayList(
            "00", "15", "30", "45"
        );
        final ComboBox<String> comboBox5 = new ComboBox<String>(mins);
        comboBox5.setPromptText("Min");

        ObservableList<String> time = FXCollections.observableArrayList(
            "AM", "PM"
        );
        final ComboBox<String> comboBox6 = new ComboBox<String>(time);
        comboBox6.setPromptText("AM/PM");

        ObservableList<String> endHours = FXCollections.observableArrayList(
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
        );
        final ComboBox<String> comboBox7= new ComboBox<String>(endHours);
        comboBox7.setPromptText("Hour");

        ObservableList<String> endMins = FXCollections.observableArrayList(
            "00", "15", "30", "45"
        );
        final ComboBox<String> comboBox8 = new ComboBox<String>(endMins);
        comboBox8.setPromptText("Min");

        ObservableList<String> endTime = FXCollections.observableArrayList(
            "AM", "PM"
        );
        final ComboBox<String> comboBox9 = new ComboBox<String>(endTime);
        comboBox9.setPromptText("AM/PM");

        createAppointment(primaryStage, comboBox1, comboBox2, comboBox3, comboBox4, comboBox5, comboBox6, comboBox7, comboBox8, comboBox9);

        HBox dateBox = new HBox();
        Label dateLabel= new Label("\t\t\tDate: ");
        dateBox.getChildren().addAll(dateLabel, comboBox1, comboBox2, comboBox3);
        comboBox3.setMinWidth(75);
        dateBox.setSpacing(1);

        HBox startTimeBox = new HBox();
        Label startTimeLabel = new Label("\t\tStart Time: ");
        startTimeBox.getChildren().addAll(startTimeLabel, comboBox4, comboBox5, comboBox6);
        comboBox4.setMaxWidth(69);
        comboBox3.setMaxWidth(63);
        comboBox6.setMaxWidth(82.5);
        startTimeBox.setSpacing(1);
        startTimeBox.setAlignment(Pos.CENTER);

        HBox endTimeBox = new HBox();
        Label endTimeLabel = new Label("\t\t End Time: ");
        endTimeBox.getChildren().addAll(endTimeLabel, comboBox7, comboBox8, comboBox9);
        comboBox7.setMaxWidth(69);
        comboBox8.setMaxWidth(63);
        comboBox9.setMaxWidth(82.5);
        endTimeBox.setSpacing(1);

        HBox nameBox = new HBox();
        Label name = new Label("Appointment Name: ");
        Label nameBuffer = new Label("\t\t\t\t");
        appointmentName.setPromptText("Appointment Name");
        appointmentName.setMinWidth(218);
        nameBox.getChildren().addAll(name, appointmentName, nameBuffer);

        HBox costBox = new HBox();
        Label costLabel = new Label("\t\t\t Cost: ");
        cost.setPromptText("Cost");
        cost.setMinWidth(218);
        //cost.setPrefWidth(200);
        costBox.getChildren().addAll(costLabel, cost);

        //setUp.setPrefWidth(200);
        backButton.setMinWidth(97.5);
        createButton.setMinWidth(97.5);
        setUp.getChildren().addAll(backButton, createButton);
        setUp.setAlignment(Pos.CENTER);
        setUp.setSpacing(3);
        backButton.setOnAction(e->switcher.switchToCalendarPageBusiness(appointmentCreationPage.getWindow(), primaryStage));

        appointmentColumn.getChildren().addAll(spacingBuffer,title, nameBox, dateBox, startTimeBox, endTimeBox, costBox, setUp, errorLabel);
        appointmentColumn.setSpacing(5);
        appointmentColumn.setAlignment(Pos.CENTER);


        return appointmentColumn;


    }

    public void createAppointment(Stage primaryStage, ComboBox<String> month, ComboBox<String> day, ComboBox<String> year, ComboBox<String> hour, ComboBox<String> min, ComboBox<String> amPM, ComboBox<String> endHour, ComboBox<String> endMin, ComboBox<String> endAMPM){
        createButton.setOnAction(e->{

            //sets up current info for appointemnt
            if(appointmentName.getText() != null && month.getValue() != null && day.getValue() != null && year.getValue() != null && hour.getValue() != null && min.getValue() != null && amPM.getValue() != null && endHour.getValue() != null && endMin.getValue() != null && endAMPM.getValue() != null){
                createdAppointment.setType(appointmentName.getText());
                createdAppointment.setProvider(businessLoggedin.getID());
                if(cost.getText() != null){
                    createdAppointment.setCost(cost.getText());
                }else{
                    createdAppointment.setCost("No cost");
                }
                createdAppointment.setStartDate((String) year.getSelectionModel().getSelectedItem() + "-" + (String) month.getSelectionModel().getSelectedItem() + "-" + (String) day.getSelectionModel().getSelectedItem() + " " + (String) hour.getSelectionModel().getSelectedItem() + ":" + (String) min.getSelectionModel().getSelectedItem() + ":00 " + (String) amPM.getSelectionModel().getSelectedItem() + " -05:00");
                createdAppointment.setEndDate((String) year.getSelectionModel().getSelectedItem() + "-" + (String) month.getSelectionModel().getSelectedItem() + "-" + (String) day.getSelectionModel().getSelectedItem() + " " + (String) endHour.getSelectionModel().getSelectedItem() + ":" + (String) endMin.getSelectionModel().getSelectedItem() + ":00 " + (String) endAMPM.getSelectionModel().getSelectedItem() + " -05:00");
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
                        ZonedDateTime tempStartDate, tempEndDate;
       
                        //read in data and determine if appointment already exists
                        while((line = br.readLine()) != null){
                            tempArr = line.split(",");
                            tempStartDate = ZonedDateTime.parse(tempArr[1], formatter);
                            tempEndDate = ZonedDateTime.parse(tempArr[2], formatter);
                            tempProvider = Integer.parseInt(tempArr[4]);
       
                            //keep note if email is found
                            if(tempProvider == businessLoggedin.getID() && ((createdAppointment.stringToStartDate().isBefore(tempEndDate) && createdAppointment.stringToStartDate().isAfter(tempStartDate)) || (createdAppointment.stringToEndDate().isBefore(tempEndDate) && createdAppointment.stringToEndDate().isAfter(tempStartDate)))){
                                alreadyExists = true;
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
                    if(createdAppointment.stringToStartDate().isBefore(ZonedDateTime.now()) || alreadyExists || createdAppointment.stringToEndDate().isBefore(createdAppointment.stringToStartDate())){
                        if(alreadyExists){
                            errorLabel.setText("An appointment already exists at this time");
                        }else if(createdAppointment.stringToStartDate().isBefore(ZonedDateTime.now())){
                            errorLabel.setText("Please choose a valid start date");
                        }else{
                            errorLabel.setText("Please choose a valid end date");
                        }
                    }else{
                        fileWriterUser.write(createdAppointment.getType() + "," + createdAppointment.getStartDate() + "," + createdAppointment.getEndDate() + "," + createdAppointment.getAvailability() + "," + createdAppointment.getProvider().getID() + "," + 0 + "," + createdAppointment.getCost() + "," + createdAppointment.getID() + "\n");
                        Business businessLoggedIn = (Business) primaryStage.getUserData();
                        businessLoggedin.addAppointment(createdAppointment);
                        primaryStage.setUserData(businessLoggedIn);
                        errorLabel.setText("Appointment Created");
                    }

                    fileWriterUser.close();

                }catch(IOException except){
                    System.out.println(except);
                }

                switcher.switchToAppointmentCreationPage(appointmentCreationPage.getWindow(), primaryStage, errorLabel);

            }else if(appointmentName.getText() == null){
                errorLabel.setText("Please enter an appoinment name");
            }else if(month.getValue() == null || day.getValue() == null || year.getValue() == null || hour.getValue() == null || min.getValue() == null || amPM.getValue() == null){
                errorLabel.setText("Please enter a valid start date");
            }else if(endHour.getValue() == null || endMin.getValue() == null || endAMPM.getValue() == null){
                errorLabel.setText("Please enter a valid end time");
            }
        });
    }

}
