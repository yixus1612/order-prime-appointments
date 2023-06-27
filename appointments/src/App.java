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


        emailField.setPromptText("Email");
        passwordField.setPromptText("Password");
        emailField.setMaxWidth(200);
        passwordField.setMaxWidth(200);

        hButtonsLogin.setPrefWidth(200);
        loginButton.setMinWidth(97.5);
        createAccButton.setMinWidth(97.5);

        createAccButton.setOnAction(e -> primaryStage.setScene(createAccount)); // may have to move to event handler

        // placing buttons in an HBox and spacing them by 5 pixels
        hButtonsLogin.getChildren().addAll(createAccButton, loginButton);
        hButtonsLogin.setSpacing(5);
        hButtonsLogin.setAlignment(Pos.CENTER);

        loginColumn.getChildren().addAll(emailField, passwordField, hButtonsLogin);
        loginColumn.setSpacing(5);
        loginColumn.setAlignment(Pos.CENTER);
        loginColumn.setBackground(new Background( new BackgroundFill(Color.web("#4681e0"), null, null)));

        loginScene = new Scene(loginColumn, 600, 500);
/*--------------------------------------------------------------------------------------------------*/
/*---------------------------------------CREATE ACCOUNT PAGE---------------------------------------*/
        signUpButton = new Button("Sign Up");
        backButton = new Button("Back");

        newEmailField = new TextField();
        newPasswordField = new TextField();
        confirmPasswordField = new TextField();

        newEmailField.setPromptText("Email");
        newPasswordField.setPromptText("Password");
        confirmPasswordField.setPromptText("Confirm Password");
        
        newEmailField.setMaxWidth(200);
        newPasswordField.setMaxWidth(200);
        confirmPasswordField.setMaxWidth(200);

        HBox hButtonsCreateAcc = new HBox();
        VBox createAccColumn = new VBox();


        signUpButton.minWidth(97.5);
        signUpButton.setPrefWidth(97.5);
        backButton.setPrefWidth(97.5);
        backButton.minWidth(97.5);
        backButton.setOnAction(e -> primaryStage.setScene(loginScene));

        hButtonsCreateAcc.getChildren().addAll(backButton, signUpButton);
        hButtonsCreateAcc.setAlignment(Pos.CENTER);
        hButtonsCreateAcc.setSpacing(5);
        hButtonsCreateAcc.setPrefWidth(200);

        createAccColumn.setBackground(new Background(new BackgroundFill(Color.web("#4681e0"), null, null)));
        createAccColumn.setAlignment(Pos.CENTER);
        createAccColumn.setSpacing(5);
        createAccColumn.getChildren().addAll(newEmailField, newPasswordField, confirmPasswordField, hButtonsCreateAcc);

        createAccount = new Scene(createAccColumn, 600, 500);
/*--------------------------------------------------------------------------------------------------------------*/
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }
}