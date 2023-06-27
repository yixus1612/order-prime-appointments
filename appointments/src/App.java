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

        Login.switchToAccountType(primaryStage, chooseAccount);
        CreateUserAccount.switchToChooseAccount(primaryStage, chooseAccount);
        Login.loginUser(primaryStage, Home);
        chooseAccount.switchToUserAccount(primaryStage, CreateUserAccount);
        chooseAccount.switchToBusinessAccount(primaryStage, CreateBusinessAccount);
        CreateBusinessAccount.switchToChooseAccount(primaryStage, chooseAccount);
        chooseAccount.switchToLogin(primaryStage, Login);

        primaryStage.setScene(Login.loginPage);
        primaryStage.show();
    }
}
