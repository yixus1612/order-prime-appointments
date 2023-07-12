import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class EditProfilePageBusiness {

    public Scene settingsPage;
    public Rectangle scheduleTabRectangle, calendarTabRectangle, appointmentsTabRectangle, settingsTabRectangle;
    Text scheduleTabText, calendarTabText, appointmentsTabText, settingsTabText;
    Rectangle profilePicture;
    Rectangle buffer1, buffer2;
    private Business businessLoggedin = new Business();
    private SceneSwitcher switcher;

    public EditProfilePageBusiness(Stage primaryStage){

        switcher = new SceneSwitcher(primaryStage);

        businessLoggedin = (Business) primaryStage.getUserData();

        BorderPane layout = new BorderPane();
        
        VBox mainPage = mainPage(primaryStage);
        layout.setCenter(mainPage);

        HBox sidebar = sideBar(primaryStage);
        layout.setLeft(sidebar);

        settingsPage = new Scene(layout, 600, 500);

    }

    public HBox sideBar(Stage primaryStage){

        ImageView imageView = new ImageView();
        imageView.setImage(businessLoggedin.getProfilePic());
        imageView.setFitHeight(65);
        imageView.setFitWidth(65);
        StackPane pfp = new StackPane(imageView);
        pfp.setAlignment(Pos.CENTER);

        Text nameText = new Text(businessLoggedin.getName());
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

        scheduleTabRectangle.setOnMouseClicked(e -> switcher.switchToAppointmentSchedulingPage(settingsPage.getWindow(), primaryStage));
        scheduleTabText.setOnMouseClicked(e -> switcher.switchToAppointmentSchedulingPage(settingsPage.getWindow(), primaryStage));

        appointmentsTabRectangle.setOnMouseClicked(e->switcher.switchToAppointmentsPage(settingsPage.getWindow(), primaryStage, businessLoggedin.appointmentList));
        appointmentsTabText.setOnMouseClicked(e -> switcher.switchToAppointmentsPage(settingsPage.getWindow(), primaryStage, businessLoggedin.appointmentList));

        calendarTabRectangle.setOnMouseClicked(e -> switcher.switchToCalendarPage(settingsPage.getWindow(), primaryStage));
        calendarTabText.setOnMouseClicked(e -> switcher.switchToCalendarPage(settingsPage.getWindow(), primaryStage));

        return sidebar;

    }

    public VBox mainPage(Stage primaryStage){
        VBox center = new VBox();
        center.setAlignment(Pos.CENTER);
        Button submitButton = new Button("Submit");
        Button deleteButton = new Button("Delete");
        Button backButton = new Button("Back");
        HBox [] boxs = new HBox[4];
        for(int i = 0; i < 4; i++){
            boxs[i] = new HBox();
        }

        Label firstNameLabel = new Label("First Name: ");
        TextField firstName = new TextField(businessLoggedin.getName().split(" (?!.* )")[0]);
        String userFirstName = firstName.getText();
        boxs[0].getChildren().addAll(firstNameLabel, firstName);

        Label lastNameLabel = new Label("Last Name: ");
        TextField lastName = new TextField(businessLoggedin.getName().split(" (?!.* )")[1]);
        String userLastName = lastName.getText();
        boxs[1].getChildren().addAll(lastNameLabel, lastName);

        Label typeLabel = new Label("Business Name: ");
        TextField type = new TextField(businessLoggedin.getType());
        String userType = lastName.getText();
        boxs[2].getChildren().addAll(typeLabel, type);

        Label emailLabel = new Label("Email: ");
        TextField email = new TextField(businessLoggedin.getEmail());
        String userEmail = email.getText();
        boxs[3].getChildren().addAll(emailLabel, email);

        submitButton.setOnAction(e->{
            businessLoggedin.setName(userFirstName + " " + userLastName);
            businessLoggedin.setEmail(userEmail);
            businessLoggedin.setType(userType);
        });

        deleteButton.setOnAction(e->{
            delete(primaryStage);
        });

        backButton.setOnAction(e->{
            switcher.switchToSettingsPage(settingsPage.getWindow(), primaryStage);
        });

        center.getChildren().addAll(boxs[0], boxs[1], boxs[2], boxs[3], submitButton, deleteButton, backButton);
        return center;

    }

    public void changeAppointment(Stage primaryStage){
        List<Business> businessList = new ArrayList<>();
        Boolean changeEmail = false;
        String oldEmail = "";
        try{
            //set up FileReader
            FileReader fileReaderAccount = new FileReader("businessList.csv");
            BufferedReader br = new BufferedReader(fileReaderAccount);
            String line = "";
            String[] tempArr;
            Business tempBusines;
   
            //read in data and determine if appointment already exists
            while((line = br.readLine()) != null){
                tempArr = line.split(",");
                tempBusines = new Business(tempArr[0], tempArr[1], tempArr[2], Integer.parseInt(tempArr[2]));
   
                //keep note if email is found
                if(businessLoggedin.getID() == tempBusines.getID()){
                    if(businessLoggedin.getEmail() != tempBusines.getEmail()){
                        oldEmail = tempBusines.getEmail();
                        changeEmail = true;
                    }
                    tempBusines = businessLoggedin;
                }

                businessList.add(tempBusines);

            }
            br.close();
        }catch(IOException except){
            System.out.println(except);
        }

        try{              
            FileWriter fileWriterUser = new FileWriter("appointmentList.csv", false);

            for(Business u : businessList){
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
                        tempArr[0] = businessLoggedin.getEmail();
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

        switcher.switchToSettingsPageBusiness(settingsPage.getWindow(), primaryStage);
    }

    public void delete(Stage primaryStage){
        List<Business> businessList = new ArrayList<>();
        try{
            //set up FileReader
            FileReader fileReaderAccount = new FileReader("businessList.csv");
            BufferedReader br = new BufferedReader(fileReaderAccount);
            String line = "";
            String[] tempArr;
            Business tempBusiness;
   
            //read in data and determine if appointment already exists
            while((line = br.readLine()) != null){
                tempArr = line.split(",");
                tempBusiness = new Business(tempArr[0], tempArr[1], tempArr[2], Integer.parseInt(tempArr[3]));
   
                //keep note if email is found
                if(businessLoggedin.getID() != tempBusiness.getID()){
                    businessList.add(tempBusiness);
                }

            }
            br.close();
        }catch(IOException except){
            System.out.println(except);
        }

        try{              
            FileWriter fileWriterUser = new FileWriter("businessList.csv", false);

            for(Business b : businessList){
                fileWriterUser.write(b.getName() + "," + b.getEmail() + "," + b.getID() + "\n");
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
                if(!(businessLoggedin.getEmail().equals(tempArr[0]))){
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


        businessLoggedin = null;
        switcher.switchToLoginPage(settingsPage.getWindow(), primaryStage);
    }
}