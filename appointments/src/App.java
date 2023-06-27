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

        Login.switchToAccount(primaryStage, CreateAccount);
        CreateAccount.switchToLogin(primaryStage, Login);
        Login.loginUser(primaryStage, Home);

        primaryStage.setScene(Login.loginPage);
        primaryStage.show();
    }
}
