package Screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class ChooseAccountType {
    public Scene chooseAccountType;

    public Button businessButton, userButton, backButton;

    public ChooseAccountType(){
        Label title = new Label("Choose Account Type");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font ("Arial", FontWeight.BOLD, 12));

        HBox hButtonsCreateAcc = new HBox();
        VBox createAccColumn = new VBox();

        businessButton = new Button("Business");
        userButton = new Button("User");
        backButton = new Button("Back");

        hButtonsCreateAcc.getChildren().addAll(businessButton, userButton, backButton);
        hButtonsCreateAcc.setAlignment(Pos.CENTER);
        hButtonsCreateAcc.setSpacing(5);
        hButtonsCreateAcc.setPrefWidth(200);

        createAccColumn.setBackground(new Background(new BackgroundFill(Color.web("#4681e0"), null, null)));
        createAccColumn.setAlignment(Pos.CENTER);
        createAccColumn.setSpacing(5);
        createAccColumn.getChildren().addAll(title, hButtonsCreateAcc);

        chooseAccountType = new Scene(createAccColumn, 600, 500);
    }

    public void switchToLogin(Stage primaryStage, LoginPage login){
        backButton.setOnAction(e->primaryStage.setScene(login.loginPage));
    }

    public void switchToUserAccount(Stage primaryStage, CreateAccountPage createAccount){
        userButton.setOnAction(e-> primaryStage.setScene(createAccount.createAccountPage));
    }

    public void switchToBusinessAccount(Stage primaryStage, CreateAccountBusiness createAccount){
        businessButton.setOnAction(e-> primaryStage.setScene(createAccount.createAccountPage));
    }
}
