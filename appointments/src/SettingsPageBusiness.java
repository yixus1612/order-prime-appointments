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

public class SettingsPageBusiness {

    public Scene settingsPage;
    public Rectangle createTabRectangle, calendarTabRectangle, appointmentsTabRectangle, settingsTabRectangle;
    Text createTabText, calendarTabText, appointmentsTabText, settingsTabText;
    Rectangle profilePicture;
    Rectangle buffer1, buffer2;
    private Business businessLoggedin = new Business();
    private SceneSwitcher switcher;

    public SettingsPageBusiness(Stage primaryStage){

        switcher = new SceneSwitcher(primaryStage);

        businessLoggedin = (Business) primaryStage.getUserData();

        BorderPane layout = new BorderPane();

        VBox mainPage = mainPage(primaryStage);
        layout.setCenter(mainPage);
        
        HBox sidebar = sideBar(primaryStage);
        layout.setLeft(sidebar);

        settingsPage = new Scene(layout, 600, 500);
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
        tabStack.getChildren().addAll(buffer1, pfp, name, buffer2, createTab, calendarTab, appointmentsTab, settingsTab);
        
        HBox sidebar = new HBox(tabStack, sidebarSeparator);
        sidebar.setBackground(new Background(new BackgroundFill(Color.web("#4681e0"), null, null)));

        Label tempLabel = new Label();
        createTabRectangle.setOnMouseClicked(e -> switcher.switchToAppointmentCreationPage(settingsPage.getWindow(), primaryStage, tempLabel));
        createTabText.setOnMouseClicked(e -> switcher.switchToAppointmentCreationPage(settingsPage.getWindow(), primaryStage, tempLabel));

        appointmentsTabRectangle.setOnMouseClicked(e->switcher.switchToAppointmentsPageBusiness(settingsPage.getWindow(), primaryStage, businessLoggedin.appointmentList));
        appointmentsTabText.setOnMouseClicked(e -> switcher.switchToAppointmentsPageBusiness(settingsPage.getWindow(), primaryStage, businessLoggedin.appointmentList));

        calendarTabRectangle.setOnMouseClicked(e -> switcher.switchToCalendarPageBusiness(settingsPage.getWindow(), primaryStage));
        calendarTabText.setOnMouseClicked(e -> switcher.switchToCalendarPageBusiness(settingsPage.getWindow(), primaryStage));

        return sidebar;

    }

    public VBox mainPage(Stage primaryStage){
        VBox center = new VBox();
        center.setAlignment(Pos.TOP_CENTER);
        Button editButton = new Button("Edit");
        Button signOutButton = new Button("Sign Out");

        ImageView imageView = new ImageView();
        imageView.setImage(businessLoggedin.getProfilePic());
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        Label nameLabel = new Label("Name: " + businessLoggedin.getName());

        Label emailLabel = new Label("Email: " + businessLoggedin.getEmail());

        Label typeLabel = new Label("Business Name: " + businessLoggedin.getType());

        editButton.setOnAction(e->{
            switcher.switchToEditProfilePageBusiness(settingsPage.getWindow(), primaryStage);
        });

        signOutButton.setOnAction(e->{
            businessLoggedin = null;
            switcher.switchToLoginPage(settingsPage.getWindow(), primaryStage);
        });
        
        Label spacingBuffer1 = new Label(" ");
        Label spacingBuffer2 = new Label(" ");
        Label title = new Label("Profile");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        spacingBuffer1.setFont(Font.font("Arial", FontWeight.BOLD, 45));
        spacingBuffer2.setFont(Font.font("Arial", FontWeight.BOLD, 5));

        center.getChildren().addAll(spacingBuffer1, title, spacingBuffer2, imageView, nameLabel, emailLabel, typeLabel, editButton, signOutButton);
        return center;

    }
}
