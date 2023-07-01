package Screens;

import java.io.*;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
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

public class AppointmentCreationPage {

    public Scene appointmentCreationPage;

    public Rectangle homeTabRectangle, calendarTabRectangle, profileTabRectangle, placesTabRectangle, paymentTabRectangle, settingsTabRectangle;
    Text homeTabText, profileTabText, calendarTabText, placesTabText, paymentTabText, settingsTabText;
    Rectangle profilePicture;
    Rectangle buffer1, buffer2;

    private BorderPane layout = new BorderPane();

    public AppointmentCreationPage(){
        

        HBox sidebar = sideBar();
        layout.setLeft(sidebar);

        //VBox center = mainPage();
        //layout.setCenter(center);

        appointmentCreationPage = new Scene(layout, 600, 500);
    }

    public void SetupPageSwitching(Stage primaryStage, HomePage Home, ProfilePage Profile, PlacesPage Places, PaymentPage Payment, SettingsPage Settings){

        homeTabRectangle.setOnMouseClicked(e -> primaryStage.setScene(Home.homePage));
        homeTabText.setOnMouseClicked(e -> primaryStage.setScene(Home.homePage));

        profileTabRectangle.setOnMouseClicked(e -> primaryStage.setScene(Profile.profilePage));
        profileTabText.setOnMouseClicked(e -> primaryStage.setScene(Profile.profilePage));

        placesTabRectangle.setOnMouseClicked(e -> primaryStage.setScene(Places.placesPage));
        placesTabText.setOnMouseClicked(e -> primaryStage.setScene(Places.placesPage));

        paymentTabRectangle.setOnMouseClicked(e -> primaryStage.setScene(Payment.paymentPage));
        paymentTabText.setOnMouseClicked(e -> primaryStage.setScene(Payment.paymentPage));
        
        settingsTabRectangle.setOnMouseClicked(e -> primaryStage.setScene(Settings.settingsPage));
        settingsTabText.setOnMouseClicked(e -> primaryStage.setScene((Settings.settingsPage)));

    }

    public HBox sideBar(){
        // FIXME this should display the user's profile picture
        profilePicture = new Rectangle(65, 65, Color.CORAL);
        StackPane pfp = new StackPane(profilePicture);
        pfp.setAlignment(Pos.CENTER);

        // FIXME this should display the user's name
        Text nameText = new Text("Name");
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

        paymentTabText = new Text("  Payment Options");
        paymentTabText.setFill(Color.WHITE);
        paymentTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        paymentTabRectangle = new Rectangle(110,25, Color.web("#3064b8"));
        StackPane paymentTab = new StackPane();
        paymentTab.getChildren().addAll(paymentTabRectangle, paymentTabText);
        paymentTab.setAlignment(Pos.CENTER_LEFT);

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
        tabStack.getChildren().addAll(buffer1, pfp, name, buffer2, homeTab, profileTab, calendarTab, placesTab, paymentTab, settingsTab);
        
        HBox sidebar = new HBox(tabStack, sidebarSeparator);
        sidebar.setBackground(new Background(new BackgroundFill(Color.web("#4681e0"), null, null)));

        return sidebar;

    }

    public VBox mainPage(){
        HBox setUp = new HBox();
        VBox appointmentColumn = new VBox();
        Label title = new Label("Create An Appointment");
        TextField appointmentName = new TextField();
        appointmentName.setPromptText("Appointment Name");
        Button backButton = new Button("Back");
        Button createButton = new Button("Create");

        setUp.setPrefWidth(200);
        backButton.setMinWidth(97.5);
        createButton.setMinWidth(97.5);
        setUp.getChildren().addAll(backButton, createButton);
        setUp.setAlignment(Pos.CENTER);

        appointmentColumn.getChildren().addAll(title, appointmentName, setUp);
        appointmentColumn.setSpacing(5);
        appointmentColumn.setAlignment(Pos.CENTER);

        return appointmentColumn;


    }

}