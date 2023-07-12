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
    AppointmentCreationPage AppointmentCreationPage;
    AppointmentSchedulingPage AppointmentSchedulingPage;
    CalendarPage CalendarPage;
    CalendarPageBusiness CalendarPageBusiness;
    SettingsPage SettingsPage;
    SettingsPageBusiness SettingsPageBusiness;
    AppointmentsPage AppointmentsPage;
    EditAppointments EditAppointments;
    AppointmentsPageBusiness AppointmentsPageBusiness;
    
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

    public void switchToEditAppointmentsPage(Window w, Stage primaryStage, Appointment appointment, List<Appointment> appointmentList){
        EditAppointments = new EditAppointments(primaryStage, appointment, appointmentList);
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

    public void switchToAppointmentsPageBusiness(Window w, Stage primaryStage, List<Appointment> appointmentList){
        AppointmentsPageBusiness = new AppointmentsPageBusiness(primaryStage, appointmentList);
        if(w instanceof Stage){
            Stage s = (Stage) w;
            s.setScene(AppointmentsPageBusiness.appointmentsPage);
        }
    }
}
