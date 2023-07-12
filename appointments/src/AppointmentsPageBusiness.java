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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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

public class AppointmentsPageBusiness {

    public Scene appointmentsPage;
    public Rectangle createTabRectangle, calendarTabRectangle, appointmentsTabRectangle, settingsTabRectangle;
    Text createTabText, calendarTabText, appointmentsTabText, settingsTabText;
    Rectangle profilePicture;
    Rectangle buffer1, buffer2;
    private SceneSwitcher switcher;
    private List<Appointment> appointmentlist;
    private Business businessLoggedin;

    public AppointmentsPageBusiness(Stage primaryStage, List<Appointment> appointmentListForDay){

        switcher = new SceneSwitcher(primaryStage);
    
        businessLoggedin = (Business) primaryStage.getUserData();

        HBox sidebar = sideBar(primaryStage);

        BorderPane layout = new BorderPane();
        layout.setLeft(sidebar);

        VBox center = mainPage(primaryStage, appointmentListForDay);
        layout.setCenter(center);

        appointmentsPage = new Scene(layout, 600, 500);
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

        createTabText = new Text("  Create");
        createTabText.setFill(Color.WHITE);
        createTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        createTabRectangle = new Rectangle(110,25, Color.web("#3064b8"));
        StackPane createTab = new StackPane();
        createTab.getChildren().addAll(createTabRectangle, createTabText);
        createTab.setAlignment(Pos.CENTER_LEFT);

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
        tabStack.getChildren().addAll(buffer1, pfp, name, buffer2, createTab, calendarTab, appointmentsTab, settingsTab);
        
        HBox sidebar = new HBox(tabStack, sidebarSeparator);
        sidebar.setBackground(new Background(new BackgroundFill(Color.web("#4681e0"), null, null)));

        createTabRectangle.setOnMouseClicked(e -> switcher.switchToAppointmentCreationPage(appointmentsPage.getWindow(), primaryStage));
        createTabText.setOnMouseClicked(e -> switcher.switchToAppointmentCreationPage(appointmentsPage.getWindow(), primaryStage));

        calendarTabRectangle.setOnMouseClicked(e -> switcher.switchToCalendarPageBusiness(appointmentsPage.getWindow(), primaryStage));
        calendarTabText.setOnMouseClicked(e -> switcher.switchToCalendarPageBusiness(appointmentsPage.getWindow(), primaryStage));

        settingsTabRectangle.setOnMouseClicked(e -> switcher.switchToSettingsPageBusiness(appointmentsPage.getWindow(), primaryStage));
        settingsTabText.setOnMouseClicked(e -> switcher.switchToSettingsPageBusiness(appointmentsPage.getWindow(), primaryStage));

        return sidebar;
    }

    public VBox mainPage(Stage primaryStage, List<Appointment> appointmentListForDay){
        VBox center = new VBox();

        if(appointmentListForDay.size() == 0){
            Label noAppointments = new Label("There are no appointments at this time");
            center.getChildren().add(noAppointments);
        }else{
            center.getChildren().clear();
            for(Appointment appointment : appointmentListForDay){
                Label appointmentType = new Label(appointment.getType() + " " + appointment.getDate() + " " + appointment.getProvider().getName() + " " + appointment.getCost() + "       ");
                Button cancelButton = new Button("Edit");
                cancelButton.setMinWidth(97.5);
                cancelButton.setOnAction(e-> switcher.switchToEditAppointmentsPage(appointmentsPage.getWindow(), primaryStage, appointment, appointmentListForDay));
                HBox appointmentData = new HBox();
                appointmentData.getChildren().addAll(appointmentType, cancelButton);
                center.getChildren().add(appointmentData);
            }
        }

        

        return center;
    }

}