//import javax.swing.JFileChooser;

import Screens.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application{

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Appointments");

        LoginPage Login = new LoginPage();
        CreateAccountPage CreateAccount = new CreateAccountPage();
        HomePage Home = new HomePage();
        ProfilePage Profile = new ProfilePage();
        CalendarPage Calendar = new CalendarPage();
        PlacesPage Places = new PlacesPage();
        PaymentPage Payment = new PaymentPage();
        SettingsPage Settings = new SettingsPage();

        Login.switchToAccount(primaryStage, CreateAccount);
        CreateAccount.switchToLogin(primaryStage, Login);
        Login.loginUser(primaryStage, Home);

        Home.SetupPageSwitching(primaryStage, Profile, Calendar, Places, Payment, Settings);
        Profile.SetupPageSwitching(primaryStage, Home, Calendar, Places, Payment, Settings);
        Calendar.SetupPageSwitching(primaryStage, Home, Profile, Places, Payment, Settings);
        Places.SetupPageSwitching(primaryStage, Home, Profile, Calendar, Payment, Settings);
        Payment.SetupPageSwitching(primaryStage, Home, Profile, Calendar, Places, Settings);
        Settings.SetupPageSwitching(primaryStage, Home, Profile, Calendar, Places, Payment);

        primaryStage.setScene(Login.loginPage);
        primaryStage.show();
    }
}
