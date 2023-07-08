
import java.io.*;

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
import javafx.stage.Stage;

public class LoginPage{
    public Scene loginPage;

    public Button loginButton, createAccButton;
    public TextField emailField;
    public PasswordField passwordField;
    public Label note, title;
    private boolean isUser = false;

    public LoginPage(Stage primaryStage){

        title = new Label("Login");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        note = new Label("");
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

        loginUser(primaryStage);

        loginPage = new Scene(loginColumn, 600, 500);
            
    }

    //switches to Account with click of button
    public void switchToAccountType(Stage primaryStage, ChooseAccountType createAccountType){
        createAccButton.setOnAction(e-> primaryStage.setScene(createAccountType.chooseAccountType));
    }

    //login feature
    public void loginUser(Stage primaryStage){
        loginButton.setOnAction(e->{
            //get text from fields
            String email = emailField.getText();
            String password = passwordField.getText();
            Boolean login = false;

            //read in file and check if info is correct
            try{
                //set up fileReader
                FileReader fileReaderAccount = new FileReader("accountList.csv");
                BufferedReader br = new BufferedReader(fileReaderAccount);
                String line = "";
                String[] tempArr;
                String tempEmail;
                String tempPassword;
                Encryption encrypt = new Encryption();
                String encryptedPassword = encrypt.hash(password);

            //read account file
                while((line = br.readLine()) != null){
                    tempArr = line.split(",");
                    tempEmail = tempArr[0];
                    tempPassword = tempArr[1];

                    //check to see if email and password are correctly inputted
                    if(tempEmail.equals(email)){
                        if(tempPassword.equals(encryptedPassword)){
                            login = true;
                        }
                    }
                }

                br.close();
                fileReaderAccount.close();

            }catch(IOException except){
                System.out.println(except);
            }

            //if login info is correct, log in user
            if(login){
                User userLoggedin = findUser(email);
                if(isUser){
                    primaryStage.setUserData(userLoggedin);
                    HomePage homepage = new HomePage(primaryStage);
                    primaryStage.setScene(homepage.homePage);
                }else{
                    Business businessLoggedin = findBusiness(email);
                    System.out.println(businessLoggedin.getName());
                    primaryStage.setUserData(businessLoggedin);
                    System.out.println(businessLoggedin.getName());
                    HomePageBusiness homepage = new HomePageBusiness(primaryStage);
                    primaryStage.setScene(homepage.homePage);
                }

            //if not, make them try again and update label
            }else{
                note.setText("Username or password incorrect");
            }

            //clear textfields after attempt
            emailField.clear();
            passwordField.clear();
        });

    }

    public User findUser(String userEmail){
        User tempUser = new User();
        try{
            FileReader fileReaderUser = new FileReader("userList.csv");
            BufferedReader br = new BufferedReader(fileReaderUser);
            String line = "";
            String[] tempArr;

            while((line = br.readLine()) != null){
                    tempArr = line.split(",");
                    tempUser.setName(tempArr[0]);
                    tempUser.setEmail(tempArr[1]);
                    tempUser.setID(Integer.parseInt(tempArr[2]));
                    tempUser.createAppointmentList(tempArr[3]);

                    //check to see if email and password are correctly inputted
                    if(userEmail.equals(tempUser.getEmail())){
                        isUser = true;
                        br.close();
                        return tempUser;
                    }
            }

            br.close();
            fileReaderUser.close();

        }catch(IOException except){
            System.out.println(except);
        }

        return tempUser;
    }

    public Business findBusiness(String userEmail){
        Business tempBusiness = new Business();
        try{
            FileReader fileReaderUser = new FileReader("businessList.csv");
            BufferedReader br = new BufferedReader(fileReaderUser);
            String line = "";
            String[] tempArr;

            while((line = br.readLine()) != null){
                tempArr = line.split(",");
                tempBusiness.setName(tempArr[0]);
                tempBusiness.setEmail(tempArr[1]);
                tempBusiness.setType(tempArr[2]);
                tempBusiness.setID(Integer.parseInt(tempArr[3]));
                tempBusiness.createAppointmentList(tempArr[4]);


                //check to see if email and password are correctly inputted
                if(userEmail.equals(tempBusiness.getEmail())){
                    br.close();
                    return tempBusiness;
                }  
            }
            br.close();
            fileReaderUser.close();

        }catch(IOException except){
            System.out.println(except);
        }

        return tempBusiness;
    }
};
