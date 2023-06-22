import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Random;


public class App extends Application {

    Button loginButton;
    Button createAccButton;
    Button signUpButton;
    Button backButton;
    TextField newNameField;
    TextField emailField;
    TextField newEmailField;
    TextField passwordField;
    TextField newPasswordField;
    TextField confirmPasswordField;

    Scene createAccount;
    Scene loginScene;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Appointments");

        /*try{
            File encryptedFile = new File("encrypt.txt");
            FileReader fileReader = new FileReader(encryptedFile);
            FileWriter fileWriter = new FileWriter(encryptedFile);
        }catch(IOException e){
            System.out.println(e);
        }*/
        
        //call decrypting function with info passed


        
        

/*-----------------------LOGIN PAGE-----------------------*/
        loginButton = new Button("Login");
        createAccButton = new Button("Create Account");
        emailField = new TextField();
        String email = emailField.getText();
        passwordField = new TextField();
        String password = passwordField.getText();
        HBox hButtonsLogin = new HBox();
        VBox loginColumn = new VBox();

        

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

        newNameField = new TextField();
        String newName = newNameField.getText();
        newEmailField = new TextField();
        String newEmail = newEmailField.getText();
        newPasswordField = new TextField();
        String newPassword = newPasswordField.getText();
        confirmPasswordField = new TextField();
        String confirmPassword = confirmPasswordField.getText();

        if(newPassword.equals(confirmPassword)){
            Random rand = new Random();
            int randomID = rand.nextInt(1000000000);
            User newAccount = new User(newName, randomID, newEmail, newPassword);
            System.out.println("Account Created");
        }

        newNameField.setPromptText("Name");
        newEmailField.setPromptText("Email");
        newPasswordField.setPromptText("Password");
        confirmPasswordField.setPromptText("Confirm Password");
        
        newNameField.setMaxWidth(200);
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
        createAccColumn.getChildren().addAll(newNameField, newEmailField, newPasswordField, confirmPasswordField, hButtonsCreateAcc);

        createAccount = new Scene(createAccColumn, 600, 500);
/*--------------------------------------------------------------------------------------------------------------*/
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

}
