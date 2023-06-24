package Screens;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.io.*;
import java.util.Random;
import java.util.regex.*;

public class CreateAccountPage {
   public Scene createAccountPage;

   public Button signUpButton, backButton;
   public TextField nameField, newEmailField;
   public PasswordField newPasswordField, confirmPasswordField;

   /*
      email must:
         - 1 character except new line before "@"
         - must contain a "@"
         - must be followed by any character except whitespace
   */
   private static final String EMAIL_PATTERN = "^(.+)@(\\S+)$";
   /*
      password mustc contain:
         - at least one digit
         - at least one lowercase letter
         - at least one uppercase letter
         - at least one special character
         - must be greater than 6 and less than 15 characters in length
    */
   private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()-[{}]:;',?/*~$^+=<>]).{6,15}$";

   public CreateAccountPage(){

      //Set up labels
      Label title = new Label("Create Account");
      title.setTextFill(Color.WHITE);
      title.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
      Label accountCreation = new Label("");
      accountCreation.setTextFill(Color.WHITE);

      //set up buttons
       signUpButton = new Button("Sign Up");
       backButton = new Button("Back");

       //set up textfields
       newEmailField = new TextField();
       newPasswordField = new PasswordField();
       confirmPasswordField = new PasswordField();
       nameField = new TextField();

       //set prompt text for each textfield
       newEmailField.setPromptText("Email");
       newPasswordField.setPromptText("Password");
       confirmPasswordField.setPromptText("Confirm Password");
       nameField.setPromptText("Name");
       
       newEmailField.setMaxWidth(200);
       newPasswordField.setMaxWidth(200);
       confirmPasswordField.setMaxWidth(200);
       nameField.setMaxWidth(200);

      HBox hButtonsCreateAcc = new HBox();
      VBox createAccColumn = new VBox();

      //set up button layout
      signUpButton.minWidth(97.5);
      signUpButton.setPrefWidth(97.5);
      backButton.setPrefWidth(97.5);
      backButton.minWidth(97.5);

      //set up layout
      hButtonsCreateAcc.getChildren().addAll(backButton, signUpButton);
      hButtonsCreateAcc.setAlignment(Pos.CENTER);
      hButtonsCreateAcc.setSpacing(5);
      hButtonsCreateAcc.setPrefWidth(200);

      createAccColumn.setBackground(new Background(new BackgroundFill(Color.web("#4681e0"), null, null)));
      createAccColumn.setAlignment(Pos.CENTER);
      createAccColumn.setSpacing(5);
      createAccColumn.getChildren().addAll(title, nameField, newEmailField, newPasswordField, confirmPasswordField, hButtonsCreateAcc, accountCreation);

      createAccountPage = new Scene(createAccColumn, 600, 500);



      //create action for sign up button
      signUpButton.setOnAction(e-> {
         validateAccountCreation(accountCreation);
      });

   }

   public void switchToLogin(Stage primaryStage, LoginPage login){
      backButton.setOnAction(e-> primaryStage.setScene(login.loginPage));
   }

   public void validateAccountCreation(Label note){
      //get variables from text fields
      String name = nameField.getText();
      String email = newEmailField.getText();
      String password = newPasswordField.getText();
      String confirmPassword = confirmPasswordField.getText();

      //set up regex comparison for email
      Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
      Matcher emailMatcher = emailPattern.matcher(email);

      //set up regex compartison for password
      Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
      Matcher passwordMatcher = passwordPattern.matcher(password);

      //create random id for account
      Random random = new Random();
      int id = random.nextInt(1000000000);
      Boolean alreadyExists = false;

      //read in csv file for saved accounts
      try{
               
         //set up FileReader
         FileReader fileReaderAccount = new FileReader("accountList.csv");
         BufferedReader br = new BufferedReader(fileReaderAccount);
         String line = "";
         String[] tempArr;
         String tempEmail;

         //read in data and determine if an email already exists
         while((line = br.readLine()) != null){
            tempArr = line.split(",");
            tempEmail = tempArr[0];

            //keep note if email is found
            if(tempEmail.equals(email)){
               alreadyExists = true;
            }
         }
         br.close();
      }catch(IOException except){
         System.out.println(except);
      }

      if(!name.isEmpty() && emailMatcher.matches() && passwordMatcher.matches()){

         //write to file if password is correct and the email doesn't already exist
         if(password.equals(confirmPassword) && !alreadyExists){
            //test code
            System.out.println("Name: " + name + "\nEmail: " + email + "\nPassword: " + password + "\nConfirm: " + confirmPassword);

            //create new file output for account
            try{
               //write to account file
               FileWriter fileWriterAccount = new FileWriter("accountList.csv", true);
               fileWriterAccount.write(email + "," + password + "\n");
               fileWriterAccount.close();

               //write to user file
               FileWriter fileWriterUser = new FileWriter("userList.csv", true);
               fileWriterUser.write(name + "," + email + "," + id + "\n");
               fileWriterUser.close();

               }catch(IOException except){
                  System.out.println(except);
               }

            //update bottom text
            note.setText("Account Created!");

         //if the email already exists, print message saying so
         }else if(alreadyExists){
            note.setText("Account already exists.");

         //last condition is passwords not matching
         }else{
            note.setText("Passwords do not match.");
         }

 
      }else if(name.isEmpty()){
         note.setText("Please enter a name");
      }else if(!emailMatcher.matches()){
         note.setText("Please enter a valid email");
      }else if(!passwordMatcher.matches()){
         note.setText("Please enter a vaild password.");
      }

      //clear text fields after attempt
      nameField.clear();
      newEmailField.clear();
      newPasswordField.clear();
      confirmPasswordField.clear();

   }
   
};
