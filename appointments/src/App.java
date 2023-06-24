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
        HomePage home = new HomePage();
        Encryption encrypt = new Encryption();

        Login.createAccButton.setOnAction(e-> primaryStage.setScene(CreateAccount.createAccountPage));
        CreateAccount.backButton.setOnAction(e-> primaryStage.setScene(Login.loginPage));
        CreateAccount.signUpButton.setOnAction(e-> {
            String name = CreateAccount.nameField.getText();
            String email = CreateAccount.newEmailField.getText();
            String password = CreateAccount.newPasswordField.getText();
            String confirmPassword = CreateAccount.confirmPasswordField.getText();

            String hash = encrypt.hash(confirmPassword);

            System.out.println("Name: " + name + "\nEmail: " + email + "\nPassword: " + password + "\nConfirm: " + confirmPassword + "\nHash: " + hash);
        });

        primaryStage.setScene(Login.loginPage);
        primaryStage.show();
    }
}
