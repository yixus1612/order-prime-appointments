package Screens;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LoginPage{
    public Scene loginPage;

    public Button loginButton, createAccButton;
    public TextField emailField;
    public PasswordField passwordField;

    public LoginPage(){

        Label title = new Label("Login");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        Label note = new Label("");
        note.setTextFill(Color.WHITE);

        loginButton = new Button("Login");
        createAccButton = new Button("Create Account");
        emailField = new TextField();
        passwordField = new PasswordField();
        HBox hButtonsLogin = new HBox();
        VBox loginColumn = new VBox();


        emailField.setPromptText("Email");
        passwordField.setPromptText("Password");
        emailField.setMaxWidth(200);
        passwordField.setMaxWidth(200);

        hButtonsLogin.setPrefWidth(200);
        loginButton.setMinWidth(97.5);
        createAccButton.setMinWidth(97.5);

        // placing buttons in an HBox and spacing them by 5 pixels
        hButtonsLogin.getChildren().addAll(createAccButton, loginButton);
        hButtonsLogin.setSpacing(5);
        hButtonsLogin.setAlignment(Pos.CENTER);

        loginColumn.getChildren().addAll(title, emailField, passwordField, hButtonsLogin, note);
        loginColumn.setSpacing(5);
        loginColumn.setAlignment(Pos.CENTER);
        loginColumn.setBackground(new Background( new BackgroundFill(Color.web("#4681e0"), null, null)));

        loginButton.setOnAction(e->{
            String email = emailField.getText();
            String password = passwordField.getText();
            Boolean login = false;

            try{
               FileReader fileReaderAccount = new FileReader("accountList.csv");
               BufferedReader br = new BufferedReader(fileReaderAccount);
               String line = "";
               String[] tempArr;
               String tempEmail;
               String tempPassword;

               while((line = br.readLine()) != null){
                  tempArr = line.split(",");
                  tempEmail = tempArr[0];
                  tempPassword = tempArr[1];
                  if(tempEmail.equals(email)){
                     if(tempPassword.equals(password)){
                        login = true;
                     }
                  }
               }
               br.close();
            }catch(IOException except){
               System.out.println(except);
            }

            if(login){
                note.setText("Logging In");
            }else{
                note.setText("Username or password incorrect");
            }
        });

        loginPage = new Scene(loginColumn, 600, 500);

    }
};
