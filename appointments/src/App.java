

import Screens.*;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class App extends Application{

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Appointments");

        LoginPage Login = new LoginPage(primaryStage);
        ChooseAccountType chooseAccount = new ChooseAccountType();
        CreateAccountPage CreateUserAccount = new CreateAccountPage();
        CreateAccountBusiness CreateBusinessAccount = new CreateAccountBusiness();

        Login.switchToAccountType(primaryStage, chooseAccount);
        CreateUserAccount.switchToChooseAccount(primaryStage, chooseAccount);
        chooseAccount.switchToUserAccount(primaryStage, CreateUserAccount);
        chooseAccount.switchToBusinessAccount(primaryStage, CreateBusinessAccount);
        CreateBusinessAccount.switchToChooseAccount(primaryStage, chooseAccount);
        chooseAccount.switchToLogin(primaryStage, Login);

        primaryStage.setScene(Login.loginPage);
        primaryStage.show();
    }
}
