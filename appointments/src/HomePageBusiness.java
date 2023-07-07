import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class HomePageBusiness {
    public Scene homePage;
    Rectangle homeTabRectangle, calendarTabRectangle, profileTabRectangle, placesTabRectangle, paymentTabRectangle, settingsTabRectangle;
    Text homeTabText, profileTabText, calendarTabText, placesTabText, settingsTabText;
    Rectangle profilePicture;
    Rectangle buffer1, buffer2;
    private Business businessLoggedin = new Business();
    private ProfilePageBusiness Profile;
    private CalendarPageBusiness Calendar;
    private PlacesPageBusiness Places;
    private SettingsPageBusiness Settings;

    public HomePageBusiness(Stage primaryStage){

        Profile = new ProfilePageBusiness(primaryStage);
        Calendar = new CalendarPageBusiness(primaryStage);
        Places = new PlacesPageBusiness(primaryStage);
        Settings = new SettingsPageBusiness(primaryStage);

        //businessLoggedin = (Business) primaryStage.getUserData();
        HBox sidebar = sideBar(primaryStage);
        BorderPane layout = new BorderPane();
        layout.setLeft(sidebar);

        homePage = new Scene(layout, 600, 500);

    }

    public HBox sideBar(Stage primaryStage){

        businessLoggedin = (Business) primaryStage.getUserData();
        System.out.println(businessLoggedin.getName());
        
        // FIXME this should display the user's profile picture
        profilePicture = new Rectangle(65, 65, Color.CORAL);
        StackPane pfp = new StackPane(profilePicture);
        pfp.setAlignment(Pos.CENTER);

        // FIXME this should display the user's name
        Text nameText = new Text(businessLoggedin.getName());
        StackPane name = new StackPane(nameText);
        name.setAlignment(Pos.CENTER);

        homeTabText = new Text("  Home");
        homeTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        homeTabRectangle = new Rectangle(110,25, Color.web("#f2efd0"));
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
        calendarTabText.setFill(Color.WHITE);
        calendarTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        calendarTabRectangle = new Rectangle(110,25, Color.web("#3064b8"));
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

        Profile.SetupPageSwitching(primaryStage, this, Calendar, Places, Settings);
        Calendar.SetupPageSwitching(primaryStage, this, Profile, Places, Settings);
        Places.SetupPageSwitching(primaryStage, this, Profile, Calendar, Settings);
        Settings.SetupPageSwitching(primaryStage, this, Profile, Calendar, Places);
        SetupPageSwitching(primaryStage, Profile, Calendar, Places, Settings);

        return sidebar;
        
    }

    //Text homeTabText, profileTabText, calendarTabText, placesTabText, paymentTabText, settingsTabText;
    // this function sets up page switching between all the other pages in the sidebar
    public void SetupPageSwitching(Stage primaryStage, ProfilePageBusiness Profile, CalendarPageBusiness Calendar, PlacesPageBusiness Places, SettingsPageBusiness Settings){

        profileTabRectangle.setOnMouseClicked(e ->primaryStage.setScene(Profile.profilePage));
        profileTabText.setOnMouseClicked(e -> primaryStage.setScene(Profile.profilePage));

        calendarTabRectangle.setOnMouseClicked(e -> primaryStage.setScene(Calendar.calendarPage));
        calendarTabText.setOnMouseClicked(e -> primaryStage.setScene(Calendar.calendarPage));

        placesTabRectangle.setOnMouseClicked(e -> primaryStage.setScene(Places.placesPage));
        placesTabText.setOnMouseClicked(e -> primaryStage.setScene(Places.placesPage));
        
        settingsTabRectangle.setOnMouseClicked(e -> primaryStage.setScene(Settings.settingsPage));
        settingsTabText.setOnMouseClicked(e -> primaryStage.setScene((Settings.settingsPage)));

    }
    
}
