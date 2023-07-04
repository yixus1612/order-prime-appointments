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
        CalendarPage Calendar = new CalendarPage(primaryStage);
        PlacesPage Places = new PlacesPage();
        AppointmentsPage Appointments = new AppointmentsPage();
        SettingsPage Settings = new SettingsPage();

        Login.switchToAccountType(primaryStage, chooseAccount);
        CreateUserAccount.switchToChooseAccount(primaryStage, chooseAccount);
        Login.loginUser(primaryStage, Home);
        chooseAccount.switchToUserAccount(primaryStage, CreateUserAccount);
        chooseAccount.switchToBusinessAccount(primaryStage, CreateBusinessAccount);
        CreateBusinessAccount.switchToChooseAccount(primaryStage, chooseAccount);
        chooseAccount.switchToLogin(primaryStage, Login);

        Home.SetupPageSwitching(primaryStage, Profile, Calendar, Places, Appointments, Settings);
        Profile.SetupPageSwitching(primaryStage, Home, Calendar, Places, Appointments, Settings);
        Calendar.SetupPageSwitching(primaryStage, Home, Profile, Places, Appointments, Settings);
        Places.SetupPageSwitching(primaryStage, Home, Profile, Calendar, Appointments, Settings);
        Appointments.SetupPageSwitching(primaryStage, Home, Profile, Calendar, Places, Settings);
        Settings.SetupPageSwitching(primaryStage, Home, Profile, Calendar, Places, Appointments);
        Calendar.appointmentPage.SetupPageSwitching(primaryStage, Home, Profile, Places, Appointments, Settings);

        primaryStage.setScene(Login.loginPage);
        primaryStage.show();
    }
}
