package Screens;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LoginPage{
    public Scene loginPage;

    public Button loginButton, createAccButton;
    public TextField emailField, passwordField;

    public LoginPage(){
        loginButton = new Button("Login");
        createAccButton = new Button("Create Account");
        emailField = new TextField();
        passwordField = new TextField();
        HBox hButtonsLogin = new HBox();
        VBox loginColumn = new VBox();


        emailField.setPromptText("Email");
        passwordField.setPromptText("Password");
        emailField.setMaxWidth(200);
        passwordField.setMaxWidth(200);

        hButtonsLogin.setPrefWidth(200);
        loginButton.setMinWidth(97.5);
        createAccButton.setMinWidth(97.5);

        //createAccButton.setOnAction(e -> primaryStage.setScene(createAccountScene)); // may have to move to event handler

        // placing buttons in an HBox and spacing them by 5 pixels
        hButtonsLogin.getChildren().addAll(createAccButton, loginButton);
        hButtonsLogin.setSpacing(5);
        hButtonsLogin.setAlignment(Pos.CENTER);

        loginColumn.getChildren().addAll(emailField, passwordField, hButtonsLogin);
        loginColumn.setSpacing(5);
        loginColumn.setAlignment(Pos.CENTER);
        loginColumn.setBackground(new Background( new BackgroundFill(Color.web("#4681e0"), null, null)));

        loginPage = new Scene(loginColumn, 600, 500); 
    }
};
