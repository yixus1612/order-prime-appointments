
import javafx.geometry.Pos;
import javafx.scene.Scene;
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

public class SettingsPage {

    public Scene settingsPage;
    public Rectangle scheduleTabRectangle, calendarTabRectangle, appointmentsTabRectangle, settingsTabRectangle;
    Text scheduleTabText, calendarTabText, appointmentsTabText, settingsTabText;
    Rectangle profilePicture;
    Rectangle buffer1, buffer2;
    private User userLoggedin = new User();
    private SceneSwitcher switcher;

    public SettingsPage(Stage primaryStage){

        switcher = new SceneSwitcher(primaryStage);

        userLoggedin = (User) primaryStage.getUserData();

        BorderPane layout = new BorderPane();
        HBox sidebar = sideBar(primaryStage);
        layout.setLeft(sidebar);

        settingsPage = new Scene(layout, 600, 500);

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
        appointmentsTabText.setFill(Color.WHITE);
        appointmentsTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        appointmentsTabRectangle = new Rectangle(110,25, Color.web("#3064b8"));
        StackPane appointmentsTab = new StackPane();
        appointmentsTab.getChildren().addAll(appointmentsTabRectangle, appointmentsTabText);
        appointmentsTab.setAlignment(Pos.CENTER_LEFT);

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
        tabStack.getChildren().addAll(buffer1, pfp, name, buffer2, scheduleTab, calendarTab, appointmentsTab, settingsTab);
        
        HBox sidebar = new HBox(tabStack, sidebarSeparator);
        sidebar.setBackground(new Background(new BackgroundFill(Color.web("#4681e0"), null, null)));

        scheduleTabRectangle.setOnMouseClicked(e -> switcher.switchToAppointmentSchedulingPage(settingsPage.getWindow(), primaryStage));
        scheduleTabText.setOnMouseClicked(e -> switcher.switchToAppointmentSchedulingPage(settingsPage.getWindow(), primaryStage));

        appointmentsTabRectangle.setOnMouseClicked(e->switcher.switchToAppointmentsPage(settingsPage.getWindow(), primaryStage, userLoggedin.appointmentList));
        appointmentsTabText.setOnMouseClicked(e -> switcher.switchToAppointmentsPage(settingsPage.getWindow(), primaryStage, userLoggedin.appointmentList));

        calendarTabRectangle.setOnMouseClicked(e -> switcher.switchToCalendarPage(settingsPage.getWindow(), primaryStage));
        calendarTabText.setOnMouseClicked(e -> switcher.switchToCalendarPage(settingsPage.getWindow(), primaryStage));

        return sidebar;

    }
}
