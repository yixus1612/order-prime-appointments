
import javafx.geometry.Pos;
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

public class SettingsPage {

    public Scene settingsPage;
    public Rectangle homeTabRectangle, calendarTabRectangle, profileTabRectangle, placesTabRectangle, paymentTabRectangle, settingsTabRectangle;
    Text homeTabText, profileTabText, calendarTabText, placesTabText, settingsTabText;
    Rectangle profilePicture;
    Rectangle buffer1, buffer2;
    private User userLoggedin = new User();
    private SceneSwitcher switcher;

    public SettingsPage(Stage primaryStage){

        switcher = new SceneSwitcher(primaryStage);

        BorderPane layout = new BorderPane();
        HBox sidebar = sideBar(primaryStage);
        layout.setLeft(sidebar);

        settingsPage = new Scene(layout, 600, 500);

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
        settingsTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        settingsTabRectangle = new Rectangle(110,25, Color.web("#f2efd0"));
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

        homeTabRectangle.setOnMouseClicked(e -> switcher.switchToHomePage(settingsPage.getWindow(), primaryStage));
        homeTabText.setOnMouseClicked(e -> switcher.switchToHomePage(settingsPage.getWindow(), primaryStage));

        profileTabRectangle.setOnMouseClicked(e -> switcher.switchToProfilePage(settingsPage.getWindow(), primaryStage));
        profileTabText.setOnMouseClicked(e -> switcher.switchToProfilePage(settingsPage.getWindow(), primaryStage));

        calendarTabRectangle.setOnMouseClicked(e -> switcher.switchToCalendarPage(settingsPage.getWindow(), primaryStage));
        calendarTabText.setOnMouseClicked(e -> switcher.switchToCalendarPage(settingsPage.getWindow(), primaryStage));

        placesTabRectangle.setOnMouseClicked(e -> switcher.switchToPlacesPage(settingsPage.getWindow(), primaryStage));
        placesTabText.setOnMouseClicked(e -> switcher.switchToPlacesPage(settingsPage.getWindow(), primaryStage));

        return sidebar;

    }
}
