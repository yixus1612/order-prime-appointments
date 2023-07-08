
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

public class CreateAccountBusiness {
   public Scene createAccountPage;

   public Button signUpButton, backButton;
   public TextField firstNameField, lastNameField, newEmailField, businessField;
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

   public CreateAccountBusiness(){

      //Set up labels
      Label title = new Label("Create Business Account");
      title.setTextFill(Color.WHITE);
      title.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
      Label accountCreation = new Label("");
      accountCreation.setTextFill(Color.WHITE);

      //set up buttons
       signUpButton = new Button("Sign Up");
       backButton = new Button("Back");

       //set up textfields
       businessField = new TextField();
       newEmailField = new TextField();
       newPasswordField = new PasswordField();
       confirmPasswordField = new PasswordField();
       firstNameField = new TextField();
      lastNameField = new TextField();

       //set prompt text for each textfield
       businessField.setPromptText("Business Name");
       newEmailField.setPromptText("Email");
       newPasswordField.setPromptText("Password");
       confirmPasswordField.setPromptText("Confirm Password");
       firstNameField.setPromptText("First Name");
       lastNameField.setPromptText("Last Name");
       
       newEmailField.setMaxWidth(200);
       newPasswordField.setMaxWidth(200);
       confirmPasswordField.setMaxWidth(200);
       firstNameField.setMaxWidth(200);
       lastNameField.setMaxWidth(200);
       businessField.setMaxWidth(200);

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
      createAccColumn.getChildren().addAll(title, businessField, firstNameField, lastNameField, newEmailField, newPasswordField, confirmPasswordField, hButtonsCreateAcc, accountCreation);

      createAccountPage = new Scene(createAccColumn, 600, 500);



      //create action for sign up button
      signUpButton.setOnAction(e-> {
         validateAccountCreation(accountCreation);
      });

   }

   public void switchToChooseAccount(Stage primaryStage, ChooseAccountType accountType){
      backButton.setOnAction(e-> primaryStage.setScene(accountType.chooseAccountType));
   }

   public void validateAccountCreation(Label note){
      //get variables from text fields
      String business = businessField.getText();
      String firstName = firstNameField.getText();
      String lastName = lastNameField.getText();
      String name = firstName + " " + lastName;
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

      if(!business.isEmpty() && !name.isEmpty() && emailMatcher.matches() && passwordMatcher.matches()){

         //write to file if password is correct and the email doesn't already exist
         if(password.equals(confirmPassword) && !alreadyExists){
            //test code
            System.out.println("Name: " + name + "\nEmail: " + email + "\nPassword: " + password + "\nConfirm: " + confirmPassword);

            //create new file output for account
            try{
               //write to account file
               Encryption encrypt = new Encryption();
               String encryptedPassword = encrypt.hash(confirmPassword);

               FileWriter fileWriterAccount = new FileWriter("accountList.csv", true);
               fileWriterAccount.write(email + "," + encryptedPassword + "\n");
               fileWriterAccount.close();

               //write to user file
               FileWriter fileWriterUser = new FileWriter("businessList.csv", true);
               fileWriterUser.write(name + "," + email + "," + business + "," + id + "," + fristName + lastName + "Appointments.csv" + "\n");
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
      }else if(business.isEmpty()){
         note.setText("Please enter a business");
      }

      //clear text fields after attempt
      firstNameField.clear();
      lastNameField.clear();
      newEmailField.clear();
      newPasswordField.clear();
      confirmPasswordField.clear();

   }
   
};
