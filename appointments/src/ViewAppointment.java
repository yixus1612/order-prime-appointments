
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

public class ViewAppointment {

    public Scene viewAppointmentPage;
    public Rectangle scheduleTabRectangle, calendarTabRectangle, appointmentsTabRectangle, settingsTabRectangle;
    Text scheduleTabText, calendarTabText, appointmentsTabText, settingsTabText;
    Rectangle profilePicture;
    Rectangle buffer1, buffer2;
    private User userLoggedin = new User();
    private SceneSwitcher switcher;
    private Appointment appointment;

    public ViewAppointment(Stage primaryStage, Appointment userAppointment){

        switcher = new SceneSwitcher(primaryStage);

        this.appointment = userAppointment;

        userLoggedin = (User) primaryStage.getUserData();

        BorderPane layout = new BorderPane();

        VBox mainPage = mainPage(primaryStage, appointment);
        layout.setCenter(mainPage);

        HBox sidebar = sideBar(primaryStage);
        layout.setLeft(sidebar);

        viewAppointmentPage = new Scene(layout, 600, 500);

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
        scheduleTabText.setFill(Color.WHITE);
        scheduleTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        scheduleTabRectangle = new Rectangle(110,25, Color.web("#3064b8"));
        StackPane scheduleTab = new StackPane();
        scheduleTab.getChildren().addAll(scheduleTabRectangle, scheduleTabText);
        scheduleTab.setAlignment(Pos.CENTER_LEFT);

        calendarTabText = new Text("  Calendar");
        calendarTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        calendarTabRectangle = new Rectangle(110,25, Color.web("#f2efd0"));
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

        Label tempLabel = new Label();
        scheduleTabRectangle.setOnMouseClicked(e -> switcher.switchToAppointmentSchedulingPage(viewAppointmentPage.getWindow(), primaryStage, tempLabel));
        scheduleTabText.setOnMouseClicked(e -> switcher.switchToAppointmentSchedulingPage(viewAppointmentPage.getWindow(), primaryStage, tempLabel));

        appointmentsTabRectangle.setOnMouseClicked(e->switcher.switchToAppointmentsPage(viewAppointmentPage.getWindow(), primaryStage, userLoggedin.appointmentList));
        appointmentsTabText.setOnMouseClicked(e -> switcher.switchToAppointmentsPage(viewAppointmentPage.getWindow(), primaryStage, userLoggedin.appointmentList));

        settingsTabRectangle.setOnMouseClicked(e -> switcher.switchToSettingsPage(viewAppointmentPage.getWindow(), primaryStage));
        settingsTabText.setOnMouseClicked(e -> switcher.switchToSettingsPage(viewAppointmentPage.getWindow(), primaryStage));

        return sidebar;

    }

    public VBox mainPage(Stage primaryStage, Appointment appointment){
        VBox center = new VBox();
        center.setAlignment(Pos.TOP_CENTER);
        Button cancelButton = new Button("Cancel");
        Button backButton = new Button("Back");

        Label typeLabel = new Label("Appointment Name: " + appointment.getType());

        Label startDateLabel = new Label("Start Date: " + appointment.getStartDate());

        Label endDateLabel = new Label("End Date: " + appointment.getEndDate());

        Label nameLabel = new Label("Provider Name: " + appointment.getProvider().getName());

        Label emailLabel = new Label("Provider's Email: " + appointment.getProvider().getEmail());

        Label costLabel = new Label("Cost: " + appointment.getCost());

        backButton.setOnAction(e->{
            switcher.switchToCalendarPage(viewAppointmentPage.getWindow(), primaryStage);
        });

        cancelButton.setOnAction(e->{
            editAppointment(appointment, primaryStage);
        });
        
        Label spacingBuffer1 = new Label(" ");
        Label spacingBuffer2 = new Label(" ");
        Label title = new Label("View Appointment");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        spacingBuffer1.setFont(Font.font("Arial", FontWeight.BOLD, 45));
        spacingBuffer2.setFont(Font.font("Arial", FontWeight.BOLD, 5));

        center.getChildren().addAll(spacingBuffer1, title, spacingBuffer2, typeLabel, startDateLabel, endDateLabel, nameLabel, emailLabel, costLabel, backButton, cancelButton);
        center.setSpacing(2);
        return center;

    }

    public void editAppointment(Appointment appointment, Stage primaryStage){

        List<Appointment> totalAppointmentList = new ArrayList<>();
        try{
            //set up FileReader
            FileReader fileReaderAccount = new FileReader("appointmentList.csv");
            BufferedReader br = new BufferedReader(fileReaderAccount);
            String line = "";
            String[] tempArr;
            Appointment tempAppointment;
   
            //read in data and determine if appointment already exists
            while((line = br.readLine()) != null){
                tempArr = line.split(",");
                tempAppointment = new Appointment(tempArr[0], tempArr[1], tempArr[2], Boolean.parseBoolean(tempArr[3]), Integer.parseInt(tempArr[4]), Integer.parseInt(tempArr[5]), tempArr[6], Integer.parseInt(tempArr[7]));
   
                //keep note if email is found
                if(appointment.getID() == tempAppointment.getID()){
                    tempAppointment.setAvailability(true);
                    tempAppointment.getCustomer().setID(0);
                }

                totalAppointmentList.add(tempAppointment);

            }
            br.close();
        }catch(IOException except){
            System.out.println(except);
        }

        try{              
            FileWriter fileWriterUser = new FileWriter("appointmentList.csv", false);

            for(Appointment a : totalAppointmentList){
                fileWriterUser.write(a.getType() + "," + a.getStartDate() + "," + a.getEndDate() + "," + a.getAvailability() + "," + a.getProvider().getID() + "," + a.getCustomer().getID() + "," + a.getCost() + "," + a.getID() + "\n");
            }

            fileWriterUser.close();

        }catch(IOException except){
            System.out.println(except);
        }

        switcher.switchToCalendarPage(viewAppointmentPage.getWindow(), primaryStage);
    }


}