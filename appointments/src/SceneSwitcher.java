import java.util.List;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SceneSwitcher {

    Stage primaryStage;
    LoginPage LoginPage;
    CreateAccountBusiness CreateAccountBusiness;
    CreateAccountPage CreateAccountPage;
    ChooseAccountType ChooseAccountType;
    HomePage HomePage;
    HomePageBusiness HomePageBusiness;
    AppointmentCreationPage AppointmentCreationPage;
    AppointmentSchedulingPage AppointmentSchedulingPage;
    CalendarPage CalendarPage;
    CalendarPageBusiness CalendarPageBusiness;
    PlacesPage PlacesPage;
    PlacesPageBusiness PlacesPageBusiness;
    ProfilePage ProfilePage;
    ProfilePageBusiness ProfilePageBusiness;
    SettingsPage SettingsPage;
    SettingsPageBusiness SettingsPageBusiness;
    AppointmentsPage AppointmentsPage;
    EditAppointments EditAppointments;
    
    public SceneSwitcher(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void switchToLoginPage(Window w, Stage primaryStage){
        LoginPage = new LoginPage(primaryStage);
        if(w instanceof Stage){
            Stage s = (Stage) w;
            s.setScene(LoginPage.loginPage);
        }

    }

    public void switchToBusinessAccountCreation(Window w, Stage primaryStage){
        CreateAccountBusiness = new CreateAccountBusiness(primaryStage);
        if(w instanceof Stage){
            Stage s = (Stage) w;
            s.setScene(CreateAccountBusiness.createAccountPage);
        }
    }

    public void switchToAccountCreationPage(Window w, Stage primaryStage){
        CreateAccountPage = new CreateAccountPage(primaryStage);
        if(w instanceof Stage){
            Stage s = (Stage) w;
            s.setScene(CreateAccountPage.createAccountPage);
        }
    }

    public void switchToChooseAccountPage(Window w, Stage primaryStage){
        ChooseAccountType = new ChooseAccountType(primaryStage);
        if(w instanceof Stage){
            Stage s = (Stage) w;
            s.setScene(ChooseAccountType.chooseAccountType);
        }
    }

    public void switchToAppointmentCreationPage(Window w, Stage primaryStage){
        AppointmentCreationPage = new AppointmentCreationPage(primaryStage);
        if(w instanceof Stage){
            Stage s = (Stage) w;
            s.setScene(AppointmentCreationPage.appointmentCreationPage);
        }
    }

    public void switchToAppointmentSchedulingPage(Window w, Stage primaryStage){
        AppointmentSchedulingPage = new AppointmentSchedulingPage(primaryStage);
        if(w instanceof Stage){
            Stage s = (Stage) w;
            s.setScene(AppointmentSchedulingPage.appointmentSchedulingPage);
        }
    }

    public void switchToCalendarPage(Window w, Stage primaryStage){
        CalendarPage = new CalendarPage(primaryStage);
        if(w instanceof Stage){
            Stage s = (Stage) w;
            s.setScene(CalendarPage.calendarPage);
        }
    }

    public void switchToCalendarPageBusiness(Window w, Stage primaryStage){
        CalendarPageBusiness = new CalendarPageBusiness(primaryStage);
        if(w instanceof Stage){
            Stage s = (Stage) w;
            s.setScene(CalendarPageBusiness.calendarPage);
        }
    }

    public void switchToHomePage(Window w, Stage primaryStage){
        HomePage = new HomePage(primaryStage);
        if(w instanceof Stage){
            Stage s = (Stage) w;
            s.setScene(HomePage.homePage);
        }
    }

    public void switchToHomePageBusiness(Window w, Stage primaryStage){
        HomePageBusiness = new HomePageBusiness(primaryStage);
        if(w instanceof Stage){
            Stage s = (Stage) w;
            s.setScene(HomePageBusiness.homePage);
        }
    }

    public void switchToPlacesPage(Window w, Stage primaryStage){
        PlacesPage = new PlacesPage(primaryStage);
        if(w instanceof Stage){
            Stage s = (Stage) w;
            s.setScene(PlacesPage.placesPage);
        }
    }

    public void switchToPlacesPageBusiness(Window w, Stage primaryStage){
        PlacesPageBusiness = new PlacesPageBusiness(primaryStage);
        if(w instanceof Stage){
            Stage s = (Stage) w;
            s.setScene(PlacesPageBusiness.placesPage);
        }
    }

    public void switchToProfilePage(Window w, Stage primaryStage){
        ProfilePage = new ProfilePage(primaryStage);
        if(w instanceof Stage){
            Stage s = (Stage) w;
            s.setScene(ProfilePage.profilePage);
        }
    }

    public void switchToProfilePageBusiness(Window w, Stage primaryStage){
        ProfilePageBusiness = new ProfilePageBusiness(primaryStage);
        if(w instanceof Stage){
            Stage s = (Stage) w;
            s.setScene(ProfilePageBusiness.profilePage);
        }
    }

    public void switchToSettingsPage(Window w, Stage primaryStage){
        SettingsPage = new SettingsPage(primaryStage);
        if(w instanceof Stage){
            Stage s = (Stage) w;
            s.setScene(SettingsPage.settingsPage);
        }
    }

    public void switchToSettingsPageBusiness(Window w, Stage primaryStage){
        SettingsPageBusiness = new SettingsPageBusiness(primaryStage);
        if(w instanceof Stage){
            Stage s = (Stage) w;
            s.setScene(SettingsPageBusiness.settingsPage);
        }
    }

    public void switchToEditAppointmentsPage(Window w, Stage primaryStage, Appointment appointment){
        EditAppointments = new EditAppointments(primaryStage, appointment);
        if(w instanceof Stage){
            Stage s = (Stage) w;
            s.setScene(EditAppointments.editAppointmentsPage);
        }
    }

    public void switchToAppointmentsPage(Window w, Stage primaryStage, List<Appointment> appointmentList){
        AppointmentsPage = new AppointmentsPage(primaryStage, appointmentList);
        if(w instanceof Stage){
            Stage s = (Stage) w;
            s.setScene(AppointmentsPage.appointmentsPage);
        }
    }
}
