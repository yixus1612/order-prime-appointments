import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class EditProfilePage {

    public Scene settingsPage;
    private static final String EMAIL_PATTERN = "^(.+)@(\\S+)$";
    public Rectangle scheduleTabRectangle, calendarTabRectangle, appointmentsTabRectangle, settingsTabRectangle;
    Text scheduleTabText, calendarTabText, appointmentsTabText, settingsTabText;
    Rectangle profilePicture;
    Rectangle buffer1, buffer2;
    private User userLoggedin = new User();
    private SceneSwitcher switcher;
    private Label errorLabel = new Label();

    public EditProfilePage(Stage primaryStage, Label label){

        switcher = new SceneSwitcher(primaryStage);

        errorLabel = label;

        userLoggedin = (User) primaryStage.getUserData();

        BorderPane layout = new BorderPane();

        VBox mainPage = mainPage(primaryStage);
        layout.setCenter(mainPage);

        HBox sidebar = sideBar(primaryStage);
        layout.setLeft(sidebar);

        settingsPage = new Scene(layout, 600, 500);

    }

    public HBox sideBar(Stage primaryStage){

        ImageView imageView = new ImageView();
        imageView.setImage(userLoggedin.getProfilePic());
        imageView.setFitHeight(65);
        imageView.setFitWidth(65);
        StackPane pfp = new StackPane(imageView);
        pfp.setAlignment(Pos.CENTER);

        Text nameText = new Text(userLoggedin.getName());
        StackPane name = new StackPane(nameText);
        name.setAlignment(Pos.CENTER);

        scheduleTabText = new Text("  Schedule");
        scheduleTabText.setFill(Color.WHITE);
        scheduleTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        scheduleTabRectangle = new Rectangle(110,25, Color.web("#3064b8"));
        StackPane scheduleTab = new StackPane();
        scheduleTab.getChildren().addAll(scheduleTabRectangle, scheduleTabText);
        scheduleTab.setAlignment(Pos.CENTER_LEFT);

        calendarTabText = new Text("  Calendar");
        calendarTabText.setFill(Color.WHITE);
        calendarTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        calendarTabRectangle = new Rectangle(110,25, Color.web("#3064b8"));
        StackPane calendarTab = new StackPane();
        calendarTab.getChildren().addAll(calendarTabRectangle, calendarTabText);
        calendarTab.setAlignment(Pos.CENTER_LEFT);

        appointmentsTabText = new Text("  Appointments");
        appointmentsTabText.setFill(Color.WHITE);
        appointmentsTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        appointmentsTabRectangle = new Rectangle(110,25, Color.web("#3064b8"));
        StackPane appointmentsTab = new StackPane();
        appointmentsTab.getChildren().addAll(appointmentsTabRectangle, appointmentsTabText);
        appointmentsTab.setAlignment(Pos.CENTER_LEFT);

        settingsTabText = new Text("  Settings");
        settingsTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        settingsTabRectangle = new Rectangle(110,25, Color.web("#f2efd0"));
        StackPane settingsTab = new StackPane();
        settingsTab.getChildren().addAll(settingsTabRectangle, settingsTabText);
        settingsTab.setAlignment(Pos.CENTER_LEFT);

        Line sidebarSeparator = new Line(110, 0, 110, 1000);
        sidebarSeparator.setFill(Color.BLACK);

        buffer1 = new Rectangle(5,10, Color.web("#4681e0"));
        buffer2 = new Rectangle(5,5, Color.web("#4681e0"));

        VBox tabStack = new VBox();
        tabStack.getChildren().addAll(buffer1, pfp, name, buffer2, scheduleTab, calendarTab, appointmentsTab, settingsTab);
        
        HBox sidebar = new HBox(tabStack, sidebarSeparator);
        sidebar.setBackground(new Background(new BackgroundFill(Color.web("#4681e0"), null, null)));

        Label tempLabel = new Label();
        scheduleTabRectangle.setOnMouseClicked(e -> switcher.switchToAppointmentSchedulingPage(settingsPage.getWindow(), primaryStage, tempLabel));
        scheduleTabText.setOnMouseClicked(e -> switcher.switchToAppointmentSchedulingPage(settingsPage.getWindow(), primaryStage, tempLabel));

        appointmentsTabRectangle.setOnMouseClicked(e->switcher.switchToAppointmentsPage(settingsPage.getWindow(), primaryStage, userLoggedin.appointmentList));
        appointmentsTabText.setOnMouseClicked(e -> switcher.switchToAppointmentsPage(settingsPage.getWindow(), primaryStage, userLoggedin.appointmentList));

        calendarTabRectangle.setOnMouseClicked(e -> switcher.switchToCalendarPage(settingsPage.getWindow(), primaryStage));
        calendarTabText.setOnMouseClicked(e -> switcher.switchToCalendarPage(settingsPage.getWindow(), primaryStage));

        return sidebar;

    }

    public VBox mainPage(Stage primaryStage){
        VBox center = new VBox();
        center.setAlignment(Pos.TOP_CENTER);
        Button submitButton = new Button("Submit");
        Button deleteButton = new Button("Delete");
        Button backButton = new Button("Back");
        HBox [] boxs = new HBox[3];
        for(int i = 0; i < 3; i++){
            boxs[i] = new HBox();
        }

        ImageView imageView = new ImageView();
        imageView.setImage(userLoggedin.getProfilePic());
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        Label firstNameLabel = new Label("\t\t\t\tFirst Name: ");
        TextField firstName = new TextField(userLoggedin.getName().split(" (?!.* )")[0]);
        boxs[0].getChildren().addAll(firstNameLabel, firstName);

        Label lastNameLabel = new Label("\t\t\t\tLast Name: ");
        TextField lastName = new TextField(userLoggedin.getName().split(" (?!.* )")[1]);
        boxs[1].getChildren().addAll(lastNameLabel, lastName);

        Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
        Label emailLabel = new Label("\t\t\t\tEmail:\t   ");
        TextField email = new TextField(userLoggedin.getEmail());
        boxs[2].getChildren().addAll(emailLabel, email);

        submitButton.setOnAction(e->{
            Matcher emailMatcher = emailPattern.matcher(email.getText());
            if(emailMatcher.matches() && !(firstName.getText() == null || firstName.getText().trim().isEmpty()) && !(lastName.getText() == null || lastName.getText().trim().isEmpty())){
                userLoggedin.setName(firstName.getText() + " " + lastName.getText());
                userLoggedin.setEmail(email.getText());
                changeAccount(primaryStage);
            }else if((firstName.getText() == null || firstName.getText().trim().isEmpty()) || (lastName.getText() == null || lastName.getText().trim().isEmpty())){
                errorLabel.setText("Please enter a name");
            }else{
                errorLabel.setText("Please enter a valid email");
            }
        });

        deleteButton.setOnAction(e->{
            delete(primaryStage);
        });

        backButton.setOnAction(e->{
            switcher.switchToSettingsPage(settingsPage.getWindow(), primaryStage);
        });

        HBox buttons = new HBox();
        buttons.getChildren().addAll(submitButton, deleteButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(2);

        Label spacingBuffer1 = new Label(" ");
        Label spacingBuffer2 = new Label(" ");
        Label title = new Label("Edit Profile");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        spacingBuffer1.setFont(Font.font("Arial", FontWeight.BOLD, 45));
        spacingBuffer2.setFont(Font.font("Arial", FontWeight.BOLD, 5));


        center.getChildren().addAll(spacingBuffer1, title, spacingBuffer2, imageView, boxs[0], boxs[1], boxs[2], buttons, backButton, errorLabel);
        center.setSpacing(2);
        return center;

    }

    public void changeAccount(Stage primaryStage){
        List<User> userList = new ArrayList<>();
        Boolean changeEmail = false;
        String oldEmail = "";
        try{
            //set up FileReader
            FileReader fileReaderAccount = new FileReader("userList.csv");
            BufferedReader br = new BufferedReader(fileReaderAccount);
            String line = "";
            String[] tempArr;
            User tempUser;
   
            //read in data and determine if appointment already exists
            while((line = br.readLine()) != null){
                tempArr = line.split(",");
                tempUser = new User(tempArr[0], tempArr[1], Integer.parseInt(tempArr[2]));
   
                //keep note if email is found
                if(userLoggedin.getID() == tempUser.getID()){
                    if(userLoggedin.getEmail() != tempUser.getEmail()){
                        oldEmail = tempUser.getEmail();
                        changeEmail = true;
                    }
                    tempUser = userLoggedin;
                }

                userList.add(tempUser);

            }
            br.close();
        }catch(IOException except){
            System.out.println(except);
        }

        try{              
            FileWriter fileWriterUser = new FileWriter("userList.csv", false);

            for(User u : userList){
                fileWriterUser.write(u.getName() + "," + u.getEmail() + "," + u.getID() + "\n");
            }

            fileWriterUser.close();

        }catch(IOException except){
            System.out.println(except);
        }

        if(changeEmail){
            List<String[]> accountList = new ArrayList<>();
            try{
                //set up FileReader
                FileReader fileReaderAccount = new FileReader("accountList.csv");
                BufferedReader br = new BufferedReader(fileReaderAccount);
                String line = "";
                String[] tempArr;
   
                //read in data and determine if appointment already exists
                while((line = br.readLine()) != null){
                    tempArr = line.split(",");
                    //keep note if email is found
                    if(oldEmail.equals(tempArr[0])){
                        tempArr[0] = userLoggedin.getEmail();
                    }
                    accountList.add(tempArr);

                }
                br.close();
            }catch(IOException except){
                System.out.println(except);
            }

            try{              
                FileWriter fileWriterUser = new FileWriter("accountList.csv", false);

                for(String[] s : accountList){
                    fileWriterUser.write(s[0] + "," + s[1] + "\n");
                }

                fileWriterUser.close();

            }catch(IOException except){
                System.out.println(except);
            }
        }

        switcher.switchToSettingsPage(settingsPage.getWindow(), primaryStage);
    }

    public void delete(Stage primaryStage){
        List<User> userList = new ArrayList<>();
        try{
            //set up FileReader
            FileReader fileReaderAccount = new FileReader("userList.csv");
            BufferedReader br = new BufferedReader(fileReaderAccount);
            String line = "";
            String[] tempArr;
            User tempUser;
   
            //read in data and determine if appointment already exists
            while((line = br.readLine()) != null){
                tempArr = line.split(",");
                tempUser = new User(tempArr[0], tempArr[1], Integer.parseInt(tempArr[2]));
   
                //keep note if email is found
                if(userLoggedin.getID() != tempUser.getID()){
                    userList.add(tempUser);
                }

            }
            br.close();
        }catch(IOException except){
            System.out.println(except);
        }

        try{              
            FileWriter fileWriterUser = new FileWriter("userList.csv", false);

            for(User u : userList){
                fileWriterUser.write(u.getName() + "," + u.getEmail() + "," + u.getID() + "\n");
            }

            fileWriterUser.close();

        }catch(IOException except){
            System.out.println(except);
        }

        List<String[]> accountList = new ArrayList<>();
        try{
            //set up FileReader
            FileReader fileReaderAccount = new FileReader("accountList.csv");
            BufferedReader br = new BufferedReader(fileReaderAccount);
            String line = "";
            String[] tempArr;
   
            //read in data and determine if appointment already exists
            while((line = br.readLine()) != null){
                tempArr = line.split(",");
                //keep note if email is found
                if(!(userLoggedin.getEmail().equals(tempArr[0]))){
                    accountList.add(tempArr);
                }

            }
            br.close();
        }catch(IOException except){
            System.out.println(except);
        }

        try{              
            FileWriter fileWriterUser = new FileWriter("accountList.csv", false);

            for(String[] s : accountList){
                fileWriterUser.write(s[0] + "," + s[1] + "\n");
            }

            fileWriterUser.close();

        }catch(IOException except){
            System.out.println(except);
        }

        List<Appointment> totalAppointmentList = new ArrayList<>();
        try{
            //set up FileReader
            FileReader fileReaderAccount = new FileReader("appointmentList.csv");
            BufferedReader br = new BufferedReader(fileReaderAccount);
            String line = "";
            String[] tempArr;
            Appointment tempAppointment;
   
            //read in data and determine if appointment already exists
            while((line = br.readLine()) != null){
                tempArr = line.split(",");
                tempAppointment = new Appointment(tempArr[0], tempArr[1], tempArr[2], Boolean.parseBoolean(tempArr[3]), Integer.parseInt(tempArr[4]), Integer.parseInt(tempArr[5]), tempArr[6], Integer.parseInt(tempArr[7]));
   
                //keep note if email is found
                if(userLoggedin.getID() == tempAppointment.getCustomer().getID()){
                    tempAppointment.setAvailability(true);
                    tempAppointment.getCustomer().setID(0);
                }

                totalAppointmentList.add(tempAppointment);

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

        userLoggedin = null;
        switcher.switchToLoginPage(settingsPage.getWindow(), primaryStage);
    }



}
