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
        //HomePage home = new HomePage();

        Login.createAccButton.setOnAction(e-> primaryStage.setScene(CreateAccount.createAccountPage));
        CreateAccount.backButton.setOnAction(e-> primaryStage.setScene(Login.loginPage));
        

        primaryStage.setScene(Login.loginPage);
        primaryStage.show();
    }
}
