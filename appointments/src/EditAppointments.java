import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
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

public class EditAppointments {

    public Scene editAppointmentsPage;
    public Rectangle createTabRectangle, calendarTabRectangle, appointmentsTabRectangle, settingsTabRectangle;
    Text createTabText, calendarTabText, appointmentsTabText, settingsTabText;
    Rectangle profilePicture;
    Rectangle buffer1, buffer2;
    private SceneSwitcher switcher;
    private Business businessLoggedin;

    public EditAppointments(Stage primaryStage, Appointment appointment, List<Appointment> appointmentListForDay){

        businessLoggedin = (Business) primaryStage.getUserData();

        switcher = new SceneSwitcher(primaryStage);
    
        businessLoggedin = (Business) primaryStage.getUserData();

        HBox sidebar = sideBar(primaryStage);

        BorderPane layout = new BorderPane();
        layout.setLeft(sidebar);

        VBox center = mainPage(primaryStage, appointment, appointmentListForDay);
        layout.setCenter(center);

        editAppointmentsPage = new Scene(layout, 600, 500);
    }
    
    public HBox sideBar(Stage primaryStage){
        ImageView imageView = new ImageView();
        imageView.setImage(businessLoggedin.getProfilePic());
        imageView.setFitHeight(65);
        imageView.setFitWidth(65);
        StackPane pfp = new StackPane(imageView);
        pfp.setAlignment(Pos.CENTER);;

        Text nameText = new Text(businessLoggedin.getName());
        StackPane name = new StackPane(nameText);
        name.setAlignment(Pos.CENTER);

        createTabText = new Text("  Create");
        createTabText.setFill(Color.WHITE);
        createTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        createTabRectangle = new Rectangle(110,25, Color.web("#3064b8"));
        StackPane createTab = new StackPane();
        createTab.getChildren().addAll(createTabRectangle, createTabText);
        createTab.setAlignment(Pos.CENTER_LEFT);

        calendarTabText = new Text("  Calendar");
        calendarTabText.setFill(Color.WHITE);
        calendarTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        calendarTabRectangle = new Rectangle(110,25, Color.web("#3064b8"));
        StackPane calendarTab = new StackPane();
        calendarTab.getChildren().addAll(calendarTabRectangle, calendarTabText);
        calendarTab.setAlignment(Pos.CENTER_LEFT);

        appointmentsTabText = new Text("  Appointments");
        appointmentsTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        appointmentsTabRectangle = new Rectangle(110,25, Color.web("#f2efd0"));
        StackPane appointmentsTab = new StackPane();
        appointmentsTab.getChildren().addAll(appointmentsTabRectangle, appointmentsTabText);
        appointmentsTab.setAlignment(Pos.CENTER_LEFT);

        settingsTabText = new Text("  Settings");
        settingsTabText.setFill(Color.WHITE);
        settingsTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        settingsTabRectangle = new Rectangle(110,25, Color.web("#3064b8"));
        StackPane settingsTab = new StackPane();
        settingsTab.getChildren().addAll(settingsTabRectangle, settingsTabText);
        settingsTab.setAlignment(Pos.CENTER_LEFT);

        Line sidebarSeparator = new Line(110, 0, 110, 1000);
        sidebarSeparator.setFill(Color.BLACK);

        buffer1 = new Rectangle(5,10, Color.web("#4681e0"));
        buffer2 = new Rectangle(5,5, Color.web("#4681e0"));

        VBox tabStack = new VBox();
        tabStack.getChildren().addAll(buffer1, pfp, name, buffer2, createTab, calendarTab, appointmentsTab, settingsTab);
        
        HBox sidebar = new HBox(tabStack, sidebarSeparator);
        sidebar.setBackground(new Background(new BackgroundFill(Color.web("#4681e0"), null, null)));

        createTabRectangle.setOnMouseClicked(e -> switcher.switchToAppointmentCreationPage(editAppointmentsPage.getWindow(), primaryStage));
        createTabText.setOnMouseClicked(e -> switcher.switchToAppointmentCreationPage(editAppointmentsPage.getWindow(), primaryStage));

        calendarTabRectangle.setOnMouseClicked(e -> switcher.switchToCalendarPageBusiness(editAppointmentsPage.getWindow(), primaryStage));
        calendarTabText.setOnMouseClicked(e -> switcher.switchToCalendarPageBusiness(editAppointmentsPage.getWindow(), primaryStage));

        settingsTabRectangle.setOnMouseClicked(e -> switcher.switchToSettingsPageBusiness(editAppointmentsPage.getWindow(), primaryStage));
        settingsTabText.setOnMouseClicked(e -> switcher.switchToSettingsPageBusiness(editAppointmentsPage.getWindow(), primaryStage));

        return sidebar;
    }

    public VBox mainPage(Stage primaryStage, Appointment appointment, List<Appointment> appointmentListForDay){
        VBox center = new VBox();
        center.setAlignment(Pos.CENTER);
        Button submitButton = new Button("Submit");
        Button deleteButton = new Button("Delete");
        HBox [] boxs = new HBox[6];
        for(int i = 0; i < 6; i++){
            boxs[i] = new HBox();
        }

        Label typeLabel = new Label("Appointment Type: ");
        TextField type = new TextField(appointment.getType());
        String userType = type.getText();
        boxs[0].getChildren().addAll(typeLabel, type);

        Label dateLabel = new Label("Date: ");
        TextField date = new TextField(appointment.getDate());
        String userDate = date.getText();
        boxs[1].getChildren().addAll(dateLabel, date);

        Label availabilityLabel = new Label("Availability: ");
         ObservableList<String> availability = FXCollections.observableArrayList(
            "true", "false"
        );
        final ComboBox comboBoxAvailability = new ComboBox(availability);
        comboBoxAvailability.setValue(Boolean.toString(appointment.getAvailability()));
        Boolean userAvailability = Boolean.parseBoolean((String) comboBoxAvailability.getSelectionModel().getSelectedItem());
        boxs[2].getChildren().addAll(availabilityLabel, comboBoxAvailability);

        Label customerLabel = new Label("Customer Name: ");
        Label customer = new Label(appointment.getCustomer().getName());
        boxs[3].getChildren().addAll(customerLabel, customer);

        Label costLabel = new Label("Cost: ");
        TextField cost = new TextField(appointment.getCost());
        String userCost = cost.getText();
        boxs[4].getChildren().addAll(costLabel, cost);

        submitButton.setOnAction(e->{
            appointment.setType(userType);
            appointment.setDate(userDate);
            appointment.setAvailability(userAvailability);
            appointment.setCost(userCost);
            changeAppointment(primaryStage, appointment, appointmentListForDay);
        });

        deleteButton.setOnAction(e->{
            delete(appointment, primaryStage, appointmentListForDay);
        });

        center.getChildren().addAll(boxs[0], boxs[1], boxs[2], boxs[3], boxs[4], submitButton, deleteButton);
        return center;

    }

    public void changeAppointment(Stage primaryStage, Appointment appointment, List<Appointment> appointmentListForDay){
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
                tempAppointment = new Appointment(tempArr[0], tempArr[1], Boolean.parseBoolean(tempArr[2]), Integer.parseInt(tempArr[3]), Integer.parseInt(tempArr[4]), tempArr[5], Integer.parseInt(tempArr[6]));
   
                //keep note if email is found
                if(appointment.getID() == tempAppointment.getID()){
                    tempAppointment = appointment;
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
                fileWriterUser.write(a.getType() + "," + a.getDate() + "," + a.getAvailability() + "," + a.getProvider().getID() + "," + a.getCustomer().getID() + "," + a.getCost() + "," + a.getID() + "\n");
            }

            fileWriterUser.close();

        }catch(IOException except){
            System.out.println(except);
        }

        int index = 0;
        for(Appointment a : appointmentListForDay){
            if(a.getID() == appointment.getID()){
                break;
            }
            index++;
        }

        appointmentListForDay.set(index, appointment);

        switcher.switchToAppointmentsPageBusiness(editAppointmentsPage.getWindow(), primaryStage, appointmentListForDay);
    }

    public void delete(Appointment appointment, Stage primaryStage, List<Appointment> appointmentListForDay){
        List<Appointment> totalAppointmentList = new ArrayList<>();
        int index = 0;
        try{
            //set up FileReader
            FileReader fileReaderAccount = new FileReader("appointmentList.csv");
            BufferedReader br = new BufferedReader(fileReaderAccount);
            String line = "";
            String[] tempArr;
            Appointment tempAppointment;
            int counter = 0;
   
            //read in data and determine if appointment already exists
            while((line = br.readLine()) != null){
                tempArr = line.split(",");
                tempAppointment = new Appointment(tempArr[0], tempArr[1], Boolean.parseBoolean(tempArr[2]), Integer.parseInt(tempArr[3]), Integer.parseInt(tempArr[4]), tempArr[5], Integer.parseInt(tempArr[6]));
   
                //keep note if email is found
                if(appointment.getID() != tempAppointment.getID()){
                    index = counter;
                }else{
                    totalAppointmentList.add(tempAppointment);
                }

                counter++; 

            }
            br.close();
        }catch(IOException except){
            System.out.println(except);
        }

        try{              
            FileWriter fileWriterUser = new FileWriter("appointmentList.csv", false);

            for(Appointment a : totalAppointmentList){
                fileWriterUser.write(a.getType() + "," + a.getDate() + "," + a.getAvailability() + "," + a.getProvider().getID() + "," + a.getCustomer().getID() + "," + a.getCost() + "," + a.getID() + "\n");
            }

            fileWriterUser.close();

        }catch(IOException except){
            System.out.println(except);
        }

        appointmentListForDay.remove(index);
        switcher.switchToAppointmentsPageBusiness(editAppointmentsPage.getWindow(), primaryStage, appointmentListForDay);

    }

}