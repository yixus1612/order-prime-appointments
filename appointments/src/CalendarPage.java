import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
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
import javafx.scene.control.Button;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;

import java.time.ZoneId;
import javafx.geometry.Insets;

public class CalendarPage {

    public Scene calendarPage;
    public Rectangle homeTabRectangle, calendarTabRectangle, profileTabRectangle, placesTabRectangle, paymentTabRectangle, settingsTabRectangle;
    Text homeTabText, profileTabText, calendarTabText, placesTabText, settingsTabText;
    Rectangle profilePicture;
    Rectangle buffer1, buffer2;
    private ZonedDateTime userDate = ZonedDateTime.now();
    private ZonedDateTime today = ZonedDateTime.now();
    private AnchorPane background = new AnchorPane();
    private FlowPane calendar = new FlowPane();
    private BorderPane layout = new BorderPane();
    private double calendarWidth = 490;
    private double calendarHeight = 400; 
    private Text year = new Text(); 
    private Text month = new Text();
    private Button leftButton, rightButton;
    private User userLoggedin = new User();


    public CalendarPage(Stage primaryStage, AppointmentSchedulingPage Scheduling){


        HBox sidebar = sideBar(primaryStage);
        layout.setLeft(sidebar);

        HBox yearBar = yearBox(primaryStage, Scheduling);

        HBox weekBar = weekBox();
        
        HBox calendarBox = new HBox();
        calendar.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        calendar.setPrefWidth(calendarWidth);
        drawCalendar(primaryStage, Scheduling);
        calendarBox.getChildren().addAll(calendar);

        background.getChildren().addAll(yearBar, weekBar, calendar);
        background.setLeftAnchor(yearBar, 190.0);
        background.setTopAnchor(yearBar, 0.0);
        background.setTopAnchor(weekBar, 30.0);
        background.setLeftAnchor(weekBar, 30.0);
        background.setTopAnchor(calendar, 50.0);
        background.setLeftAnchor(calendar, 10.0);
        layout.setCenter(background);

        calendarPage = new Scene(layout, 600, 500);
    }
    
    // this function sets up page switching between all the other pages in the sidebar
    public void SetupPageSwitching(Stage primaryStage, HomePage Home, ProfilePage Profile, PlacesPage Places, SettingsPage Settings){

        homeTabRectangle.setOnMouseClicked(e -> primaryStage.setScene(Home.homePage));
        homeTabText.setOnMouseClicked(e -> primaryStage.setScene(Home.homePage));

        profileTabRectangle.setOnMouseClicked(e -> primaryStage.setScene(Profile.profilePage));
        profileTabText.setOnMouseClicked(e -> primaryStage.setScene(Profile.profilePage));

        placesTabRectangle.setOnMouseClicked(e -> primaryStage.setScene(Places.placesPage));
        placesTabText.setOnMouseClicked(e -> primaryStage.setScene(Places.placesPage));

        settingsTabRectangle.setOnMouseClicked(e -> primaryStage.setScene(Settings.settingsPage));
        settingsTabText.setOnMouseClicked(e -> primaryStage.setScene((Settings.settingsPage)));

    }

    public HBox sideBar(Stage primaryStage){
        userLoggedin = (User) primaryStage.getUserData();
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


        return sidebar;

    }

    public HBox yearBox(Stage primaryStage, AppointmentSchedulingPage scheduling){
        year.setText(String.valueOf(userDate.getYear()));
        month.setText(String.valueOf(userDate.getMonth()));
        leftButton = new Button("<");
        leftButton.setOnAction(e->{
            userDate = userDate.minusMonths(1);
            calendar.getChildren().clear();
            drawCalendar(primaryStage, scheduling);
        });
        rightButton = new Button(">");
        rightButton.setOnAction(e->{
            userDate = userDate.plusMonths(1);
            calendar.getChildren().clear();
            drawCalendar(primaryStage, scheduling);
        });
        HBox yearBar = new HBox();
        yearBar.getChildren().addAll(leftButton, month, year, rightButton);
        yearBar.setSpacing(5);
        yearBar.setAlignment(Pos.CENTER);

        return yearBar;
    }

    public HBox weekBox(){
        Text monday = new Text("Mon");
        Text tuesday = new Text("Tue");
        Text wednesday = new Text("Wed");
        Text thursday = new Text("Thu");
        Text friday = new Text("Fri");
        Text saturday = new Text("Sat");
        Text sunday = new Text("Sun");
        HBox weekBar = new HBox();
        weekBar.getChildren().addAll(sunday, monday, tuesday, wednesday, thursday, friday, saturday);
        weekBar.setSpacing(45);
        return weekBar;
    }

    public void drawCalendar(Stage primaryStage, AppointmentSchedulingPage scheduling){
        year.setText(String.valueOf(userDate.getYear()));
        month.setText(String.valueOf(userDate.getMonth()));

        //set up size of calendar
        calendar.setVgap(5);
        calendar.setHgap(5);
        double rectangleWidth = (calendarWidth - 5)/8;
        double rectangeHeight = (calendarHeight - 5)/6;

        int monthMaxDate = userDate.getMonth().maxLength();

        if(userDate.getYear() % 4 != 0 && monthMaxDate == 29){
            monthMaxDate = 28;
        }

       int setOffset = ZonedDateTime.of(userDate.getYear(), userDate.getMonthValue(), 1, 0, 0, 0, 0, userDate.getZone()).getDayOfWeek().getValue();

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
                StackPane stackPane = new StackPane();
            
                //create boxes for calendar
                Rectangle calendarRectangles = new Rectangle();
                calendarRectangles.setFill(Color.TRANSPARENT);
                calendarRectangles.setStroke(Color.BLACK);
                calendarRectangles.setWidth(rectangleWidth);
                calendarRectangles.setHeight(rectangeHeight);
                calendarRectangles.setOnMouseClicked(e->primaryStage.setScene(scheduling.appointmentSchedulingPage));
                stackPane.getChildren().add(calendarRectangles);

                //put numbers on calendar
                int calculatedDate = (1+j)+(i*7);
                int currentDate = calculatedDate - setOffset;
                if(calculatedDate > setOffset){
                    if(currentDate <= monthMaxDate){
                        Text date = new Text(String.valueOf(currentDate));
                        date.setTranslateY(-(rectangeHeight/2)* .75);
                        stackPane.getChildren().add(date);
                    }
                }

                if(today.getYear() == userDate.getYear() && today.getMonth() == userDate.getMonth() && today.getDayOfMonth() == currentDate){
                    calendarRectangles.setStroke(Color.RED);
                }


                calendar.getChildren().add(stackPane);
            }
        }
       
        
    }

}
