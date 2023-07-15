
import java.io.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private SceneSwitcher switcher;

    public LoginPage(Stage primaryStage){

        switcher = new SceneSwitcher(primaryStage);

        title = new Label("Login");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        note = new Label("");
        note.setTextFill(Color.WHITE);

        loginButton = new Button("Login");
        createAccButton = new Button("Create Account");

        HBox emailBox = new HBox();
        Label emailLabel = new Label("\t\t\t\t\t\t   Email: ");
        emailField = new TextField();
        emailBox.getChildren().addAll(emailLabel, emailField);
        emailLabel.setTextFill(Color.WHITE);

        HBox passwordBox = new HBox();
        Label passwordLabel = new Label("\t\t\t\t\t    Password: ");
        passwordField = new PasswordField();
        passwordBox.getChildren().addAll(passwordLabel, passwordField);
        passwordLabel.setTextFill(Color.WHITE);

        HBox hButtonsLogin = new HBox();
        VBox loginColumn = new VBox();

        emailField.setPromptText("Email");
        passwordField.setPromptText("Password");
        emailField.setMinWidth(200);
        passwordField.setMinWidth(200);

        hButtonsLogin.setPrefWidth(200);
        loginButton.setMinWidth(97.5);
        createAccButton.setMinWidth(97.5);

        createAccButton.setOnAction(e->switcher.switchToChooseAccountPage(loginPage.getWindow(), primaryStage));

        // placing buttons in an HBox and spacing them by 5 pixels
        hButtonsLogin.getChildren().addAll(createAccButton, loginButton);
        hButtonsLogin.setSpacing(5);
        hButtonsLogin.setAlignment(Pos.CENTER);

        loginColumn.getChildren().addAll(title, emailBox, passwordBox, hButtonsLogin, note);
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
                Encryption encrypt = new Encryption();
                String encryptedPassword = encrypt.hash(password, email);

            //read account file
                while((line = br.readLine()) != null){
                    tempArr = line.split(",");

                    //check to see if email and password are correctly inputted
                    if(tempArr[0].equals(email)){
                        if(tempArr[1].equals(encryptedPassword)){
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
                userLoggedin.createAppointmentList();
                
                // switch to business or user side of the program depending on the login
                Label tempLabel = new Label();
                if(isUser){
                    primaryStage.setUserData(userLoggedin);
                    switcher.switchToAppointmentSchedulingPage(loginPage.getWindow(), primaryStage, tempLabel);
                }else{
                    Business businessLoggedin = findBusiness(email);
                    businessLoggedin.createAppointmentList();
                    primaryStage.setUserData(businessLoggedin);
                    switcher.switchToAppointmentCreationPage(loginPage.getWindow(), primaryStage, tempLabel);
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

                    // finding the account with the email given by the user
                    if(userEmail.equals(tempArr[1])){
                        tempUser.setName(tempArr[0]);
                        tempUser.setEmail(tempArr[1]);
                        tempUser.setID(Integer.parseInt(tempArr[2]));
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


                //finding the account based on the email given by the user
                if(userEmail.equals(tempArr[1])){
                    tempBusiness.setName(tempArr[0]);
                    tempBusiness.setEmail(tempArr[1]);
                    tempBusiness.setType(tempArr[2]);
                    tempBusiness.setID(Integer.parseInt(tempArr[3]));
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

    public void refresh(){
        List<Appointment> totalAppointmentList = new ArrayList<>();
        try{
            //set up FileReader
            FileReader fileReaderAccount = new FileReader("appointmentList.csv");
            BufferedReader br = new BufferedReader(fileReaderAccount);
            String line = "";
            String[] tempArr;
            Appointment tempAppointment;
            ZonedDateTime  tempStartDate;
   
            //read in data and determine if appointment already exists
            while((line = br.readLine()) != null){
                tempArr = line.split(",");
                tempAppointment = new Appointment(tempArr[0], tempArr[1], tempArr[2], Boolean.parseBoolean(tempArr[3]), Integer.parseInt(tempArr[4]), Integer.parseInt(tempArr[5]), tempArr[6], Integer.parseInt(tempArr[7]));
                tempStartDate = tempAppointment.stringToStartDate();

                //keep note if email is found
                if(tempStartDate.isAfter(ZonedDateTime.now())){
                    totalAppointmentList.add(tempAppointment);
                }
                    
            }

            br.close();

        }catch(IOException except){
            System.out.println(except);
        }

        try{              
            FileWriter fileWriterUser = new FileWriter("appointmentList.csv", false);

            for(Appointment a : totalAppointmentList){
                fileWriterUser.write(a.getType() + "," + a.getStartDate() + "," + a.getEndDate() + "," + a.getAvailability() + "," + a.getProvider().getID() + "," + a.getCustomer().getID() + "," + a.getCost() + "," + a.getID() + "\n");
            }

            fileWriterUser.close();

        }catch(IOException except){
            System.out.println(except);
        }
    }
};
