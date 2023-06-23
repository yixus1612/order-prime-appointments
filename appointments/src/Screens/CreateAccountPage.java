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

public class CreateAccountPage {
   public Scene createAccountPage;

   public Button signUpButton, backButton;
   public TextField newEmailField, newPasswordField, confirmPasswordField;

   public CreateAccountPage(){
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

      hButtonsCreateAcc.getChildren().addAll(backButton, signUpButton);
      hButtonsCreateAcc.setAlignment(Pos.CENTER);
      hButtonsCreateAcc.setSpacing(5);
      hButtonsCreateAcc.setPrefWidth(200);

      createAccColumn.setBackground(new Background(new BackgroundFill(Color.web("#4681e0"), null, null)));
      createAccColumn.setAlignment(Pos.CENTER);
      createAccColumn.setSpacing(5);
      createAccColumn.getChildren().addAll(newEmailField, newPasswordField, confirmPasswordField, hButtonsCreateAcc);

      createAccountPage = new Scene(createAccColumn, 600, 500);

   }
   
};
