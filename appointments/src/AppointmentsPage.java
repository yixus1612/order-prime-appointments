import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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

public class AppointmentsPage {

    public Scene appointmentsPage;
    public Rectangle scheduleTabRectangle, calendarTabRectangle, appointmentsTabRectangle, settingsTabRectangle;
    Text scheduleTabText, calendarTabText, appointmentsTabText, settingsTabText;
    Rectangle profilePicture;
    Rectangle buffer1, buffer2;
    private User userLoggedin;
    private SceneSwitcher switcher;
    private List<Appointment> appointmentlist;

    public AppointmentsPage(Stage primaryStage, List<Appointment> appointmentListForDay){

        switcher = new SceneSwitcher(primaryStage);
    
        userLoggedin = (User) primaryStage.getUserData();

        HBox sidebar = sideBar(primaryStage);

        BorderPane layout = new BorderPane();
        layout.setLeft(sidebar);

        VBox center = mainPage(primaryStage, appointmentListForDay);
        layout.setCenter(center);

        appointmentsPage = new Scene(layout, 600, 500);
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
        calendarTabText.setFill(Color.WHITE);
        calendarTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        calendarTabRectangle = new Rectangle(110,25, Color.web("#3064b8"));
        StackPane calendarTab = new StackPane();
        calendarTab.getChildren().addAll(calendarTabRectangle, calendarTabText);
        calendarTab.setAlignment(Pos.CENTER_LEFT);

        appointmentsTabText = new Text("  Appointments");
        appointmentsTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        appointmentsTabRectangle = new Rectangle(110,25, Color.web("#f2efd0"));
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
        scheduleTabRectangle.setOnMouseClicked(e -> switcher.switchToAppointmentSchedulingPage(appointmentsPage.getWindow(), primaryStage, tempLabel));
        scheduleTabText.setOnMouseClicked(e -> switcher.switchToAppointmentSchedulingPage(appointmentsPage.getWindow(), primaryStage, tempLabel));

        calendarTabRectangle.setOnMouseClicked(e -> switcher.switchToCalendarPage(appointmentsPage.getWindow(), primaryStage));
        calendarTabText.setOnMouseClicked(e -> switcher.switchToCalendarPage(appointmentsPage.getWindow(), primaryStage));

        settingsTabRectangle.setOnMouseClicked(e -> switcher.switchToSettingsPage(appointmentsPage.getWindow(), primaryStage));
        settingsTabText.setOnMouseClicked(e -> switcher.switchToSettingsPage(appointmentsPage.getWindow(), primaryStage));

        return sidebar;
    }

    public VBox mainPage(Stage primaryStage, List<Appointment> appointmentListForDay){
        VBox center = new VBox();
        center.setAlignment(Pos.TOP_CENTER);

        Label spacingBuffer1 = new Label(" ");
        Label spacingBuffer2 = new Label(" ");
        Label title = new Label("Your Appointments");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        spacingBuffer1.setFont(Font.font("Arial", FontWeight.BOLD, 45));
        spacingBuffer2.setFont(Font.font("Arial", FontWeight.BOLD, 5));
        center.getChildren().addAll(spacingBuffer1, title, spacingBuffer2);

        int counter = 0;
        for(Appointment appointment : appointmentListForDay){
            Rectangle rowRectangle;
            if(counter % 2 == 0){
                rowRectangle = new Rectangle(400, 32, Color.WHITE);
            }
            else{rowRectangle = new Rectangle(400, 32, Color.LIGHTGRAY);}
            

            Label appointmentType = new Label(appointment.getType() + " " + appointment.getDate() + " " + appointment.getProvider().getName() + " " + appointment.getCost() + "       ");
            appointmentType.setOnMouseClicked(e->{
                switcher.switchToViewAppointmentPage(appointmentsPage.getWindow(), primaryStage, appointment);
            });
            Button editButton = new Button("Cancel");
            editButton.setMinWidth(97.5);
            editButton.setOnAction(e-> editAppointment(appointment, primaryStage, appointmentListForDay));
            HBox appointmentData = new HBox();
            appointmentData.setAlignment(Pos.CENTER);
            appointmentData.getChildren().addAll(appointmentType, editButton);

            StackPane row = new StackPane();
            row.setAlignment(Pos.CENTER);
            row.getChildren().addAll(rowRectangle, appointmentData);

            center.getChildren().add(row);

            counter++;
        }

        return center;
    }

    public void editAppointment(Appointment appointment, Stage primaryStage, List<Appointment> appointmentListForDay){

        List<Appointment> totalAppointmentList = new ArrayList<>();
        int index = 0;
        try{
            //set up FileReader
            FileReader fileReaderAccount = new FileReader("appointmentList.csv");
            BufferedReader br = new BufferedReader(fileReaderAccount);
            String line = "";
            String[] tempArr;
            Appointment tempAppointment;
            int counter = 0;
   
            //read in data and determine if appointment already exists
            while((line = br.readLine()) != null){
                tempArr = line.split(",");
                tempAppointment = new Appointment(tempArr[0], tempArr[1], Boolean.parseBoolean(tempArr[2]), Integer.parseInt(tempArr[3]), Integer.parseInt(tempArr[4]), tempArr[5], Integer.parseInt(tempArr[6]));
   
                //keep note if email is found
                if(appointment.getID() == tempAppointment.getID()){
                    tempAppointment.setAvailability(true);
                    tempAppointment.getCustomer().setID(0);
                    index = counter;
                }

                counter++;
                totalAppointmentList.add(tempAppointment);

            }
            br.close();
        }catch(IOException except){
            System.out.println(except);
        }

        try{              
            FileWriter fileWriterUser = new FileWriter("appointmentList.csv", false);

            for(Appointment a : totalAppointmentList){
                fileWriterUser.write(a.getType() + "," + a.getDate() + "," + a.getAvailability() + "," + a.getProvider().getID() + "," + a.getCustomer().getID() + "," + a.getCost() + "," + a.getID() + "\n");
            }

            fileWriterUser.close();

        }catch(IOException except){
            System.out.println(except);
        }

        appointmentListForDay.remove(index);
        switcher.switchToAppointmentsPage(appointmentsPage.getWindow(), primaryStage, appointmentListForDay);
    }

}

