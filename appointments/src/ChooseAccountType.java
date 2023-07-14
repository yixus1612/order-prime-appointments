

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
    private SceneSwitcher switcher;

    public ChooseAccountType(Stage primaryStage){

        switcher = new SceneSwitcher(primaryStage);
        Label title = new Label("Choose Account Type");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font ("Arial", FontWeight.BOLD, 12));

        HBox hButtonsCreateAcc = new HBox();
        VBox createAccColumn = new VBox();

        businessButton = new Button("Business");
        userButton = new Button("User");
        backButton = new Button("Back");
        businessButton.setMinWidth(60);
        userButton.setMinWidth(60);
        backButton.setMinWidth(125);


        backButton.setOnAction(e-> switcher.switchToLoginPage(chooseAccountType.getWindow(), primaryStage));
        userButton.setOnAction(e-> switcher.switchToAccountCreationPage(chooseAccountType.getWindow(), primaryStage));
        businessButton.setOnAction(e-> switcher.switchToBusinessAccountCreation(chooseAccountType.getWindow(), primaryStage));

        hButtonsCreateAcc.getChildren().addAll(businessButton, userButton);
        hButtonsCreateAcc.setAlignment(Pos.CENTER);
        hButtonsCreateAcc.setSpacing(5);
        hButtonsCreateAcc.setPrefWidth(200);

        createAccColumn.setBackground(new Background(new BackgroundFill(Color.web("#4681e0"), null, null)));
        createAccColumn.setAlignment(Pos.CENTER);
        createAccColumn.setSpacing(5);
        createAccColumn.getChildren().addAll(title, hButtonsCreateAcc, backButton);

        chooseAccountType = new Scene(createAccColumn, 600, 500);
    }

}
