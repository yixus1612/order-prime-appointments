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
        ChooseAccountType chooseAccount = new ChooseAccountType();
        CreateAccountPage CreateUserAccount = new CreateAccountPage();
        CreateAccountBusiness CreateBusinessAccount = new CreateAccountBusiness();
        HomePage Home = new HomePage();
        ProfilePage Profile = new ProfilePage();
        CalendarPage Calendar = new CalendarPage();
        PlacesPage Places = new PlacesPage();
        PaymentPage Payment = new PaymentPage();
        SettingsPage Settings = new SettingsPage();

        Login.switchToAccountType(primaryStage, chooseAccount);
        CreateUserAccount.switchToChooseAccount(primaryStage, chooseAccount);
        Login.loginUser(primaryStage, Home);
        chooseAccount.switchToUserAccount(primaryStage, CreateUserAccount);
        chooseAccount.switchToBusinessAccount(primaryStage, CreateBusinessAccount);
        CreateBusinessAccount.switchToChooseAccount(primaryStage, chooseAccount);
        chooseAccount.switchToLogin(primaryStage, Login);

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
