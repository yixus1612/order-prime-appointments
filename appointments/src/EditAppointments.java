import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a z");

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

        Label tempLabel = new Label();
        createTabRectangle.setOnMouseClicked(e -> switcher.switchToAppointmentCreationPage(editAppointmentsPage.getWindow(), primaryStage, tempLabel));
        createTabText.setOnMouseClicked(e -> switcher.switchToAppointmentCreationPage(editAppointmentsPage.getWindow(), primaryStage, tempLabel));

        calendarTabRectangle.setOnMouseClicked(e -> switcher.switchToCalendarPageBusiness(editAppointmentsPage.getWindow(), primaryStage));
        calendarTabText.setOnMouseClicked(e -> switcher.switchToCalendarPageBusiness(editAppointmentsPage.getWindow(), primaryStage));

        settingsTabRectangle.setOnMouseClicked(e -> switcher.switchToSettingsPageBusiness(editAppointmentsPage.getWindow(), primaryStage));
        settingsTabText.setOnMouseClicked(e -> switcher.switchToSettingsPageBusiness(editAppointmentsPage.getWindow(), primaryStage));

        return sidebar;
    }

    public VBox mainPage(Stage primaryStage, Appointment appointment, List<Appointment> appointmentListForDay){
        VBox center = new VBox();
        center.setAlignment(Pos.TOP_CENTER);
        Button submitButton = new Button("Submit");
        Button deleteButton = new Button("Delete");
        HBox [] boxs = new HBox[7];
        for(int i = 0; i < 7; i++){
            boxs[i] = new HBox();
            boxs[i].setAlignment(Pos.CENTER_LEFT);
        }

        Label typeLabel = new Label("\t\t\t\t\tAppointment Type:  ");
        TextField type = new TextField(appointment.getType());
        String userType = type.getText();
        boxs[0].getChildren().addAll(typeLabel, type);

        Label dateLabel = new Label("\t\t\t\t\tDate (MM/DD/YY) :  ");
        ZonedDateTime startDate = ZonedDateTime.parse(appointment.getStartDate(), formatter);
        ZonedDateTime endDate = ZonedDateTime.parse(appointment.getEndDate(), formatter);
        String amPM = "AM";

        ObservableList<String> months = FXCollections.observableArrayList(
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
        );
        final ComboBox month = new ComboBox(months);
        month.setPromptText("Month");
        if(startDate.getMonthValue() < 10){
            month.setValue("0" + Integer.toString(startDate.getMonthValue()));
        }else{
            month.setValue(startDate.getMonthValue());
        }

        ObservableList<String> days = FXCollections.observableArrayList(
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
        );
        final ComboBox day = new ComboBox(days);
        day.setPromptText("Day");
        if(startDate.getDayOfMonth() < 10){
        day.setValue("0" + Integer.toString(startDate.getDayOfMonth()));
        }else{
            day.setValue(startDate.getDayOfMonth());
        }

        ObservableList<String> years = FXCollections.observableArrayList(
            "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034"
        );
        final ComboBox year = new ComboBox(years);
        year.setPromptText("Year");
        year.setValue(startDate.getYear());

        boxs[1].getChildren().addAll(dateLabel, month, day, year);

        Label timeLabel = new Label("End Time: (HH:SS)");
        ObservableList<String> hours = FXCollections.observableArrayList(
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
        );
        final ComboBox hour = new ComboBox(hours);
        hour.setPromptText("Hour");
        int tempHour = startDate.getHour();
        if(tempHour > 12){
            amPM = "PM";
            if(tempHour < 10){
                hour.setValue("0" + Integer.toString(tempHour - 11));
            }else{
                hour.setValue(tempHour - 11);
            }

        }else{
            if(tempHour < 10){
                hour.setValue("0" + Integer.toString(tempHour + 1));
            }else{
                hour.setValue(tempHour + 1);
            }
        }

        ObservableList<String> mins = FXCollections.observableArrayList(
            "00", "15", "30", "45"
        );
        final ComboBox min = new ComboBox(mins);
        min.setPromptText("Min");
        if(startDate.getMinute() < 10){
            min.setValue("0" + Integer.toString(startDate.getMinute()));
        }else{
            min.setValue(startDate.getMinute());
        }

        ObservableList<String> time = FXCollections.observableArrayList(
            "AM", "PM"
        );
        final ComboBox comboBox6 = new ComboBox(time);
        comboBox6.setPromptText("AM/PM");
        comboBox6.setValue(amPM);

        boxs[2].getChildren().addAll(timeLabel, hour, min, comboBox6);

        Label availabilityLabel = new Label("\t\t\t\t\tAvailability:  ");
         ObservableList<String> availability = FXCollections.observableArrayList(
            "true", "false"
        );
        final ComboBox comboBoxAvailability = new ComboBox(availability);
        comboBoxAvailability.setValue(Boolean.toString(appointment.getAvailability()));
        Boolean userAvailability = Boolean.parseBoolean((String) comboBoxAvailability.getSelectionModel().getSelectedItem());
        boxs[3].getChildren().addAll(availabilityLabel, comboBoxAvailability);

        Label endTimeLabel = new Label("End Time: (HH:SS)");
        ObservableList<String> endHours = FXCollections.observableArrayList(
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
        );
        final ComboBox comboBox7 = new ComboBox(endHours);
        comboBox7.setPromptText("Hour");
        tempHour = startDate.getHour();
        if(tempHour > 12){
            amPM = "PM";
            if(tempHour < 10){
                hour.setValue("0" + Integer.toString(tempHour - 11));
            }else{
                hour.setValue(tempHour - 11);
            }

        }else{
            if(tempHour < 10){
                hour.setValue("0" + Integer.toString(tempHour + 1));
            }else{
                hour.setValue(tempHour + 1);
            }
        }

        ObservableList<String> endMins = FXCollections.observableArrayList(
            "00", "15", "30", "45"
        );
        final ComboBox comboBox8 = new ComboBox(endMins);
        comboBox8.setPromptText("Min");
        if(startDate.getMinute() < 10){
            min.setValue("0" + Integer.toString(startDate.getMinute()));
        }else{
            min.setValue(startDate.getMinute());
        }

        ObservableList<String> endTime = FXCollections.observableArrayList(
            "AM", "PM"
        );
        final ComboBox comboBox9 = new ComboBox(endTime);
        comboBox9.setPromptText("AM/PM");

        boxs[4].getChildren().addAll(endTimeLabel, comboBox7, comboBox8, comboBox9);

        Label customerLabel = new Label("\t\t\t\t\tCustomer Name:  ");
        Label customer = new Label(appointment.getCustomer().getName());
        boxs[4].getChildren().addAll(customerLabel, customer);

        Label costLabel = new Label("\t\t\t\t\tCost:  ");
        TextField cost = new TextField(appointment.getCost());
        String userCost = cost.getText();
        boxs[5].getChildren().addAll(costLabel, cost);

        submitButton.setOnAction(e->{
            Label errorLabel = new Label();
            if(userType != null && startDate.isAfter(ZonedDateTime.now()) && endDate.isAfter(startDate)){
                appointment.setType(userType);
                appointment.setStartDate((String) year.getSelectionModel().getSelectedItem() + "-" + (String) month.getSelectionModel().getSelectedItem() + "-" + (String) day.getSelectionModel().getSelectedItem() + " " + (String) hour.getSelectionModel().getSelectedItem() + ":" + (String) min.getSelectionModel().getSelectedItem() + ":00 " + (String) comboBox6.getSelectionModel().getSelectedItem() + " -05:00");
                appointment.setAvailability(userAvailability);
                appointment.setCost(userCost);
                changeAppointment(primaryStage, appointment, appointmentListForDay);
            }else if(userType == null){
                errorLabel.setText("Please enter an appointment name");
            }else if(startDate.isBefore(ZonedDateTime.now())){
                errorLabel.setText("Please enter a valid start date");
            }else if(endDate.isAfter(startDate)){
                errorLabel.setText("Please enter a valid end date");
            }
        });

        deleteButton.setOnAction(e->{
            delete(appointment, primaryStage, appointmentListForDay);
        });

        Label title = new Label("Edit Appointment");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));

        Label spacingBuffer1 = new Label(" ");
        Label spacingBuffer2 = new Label(" ");
        spacingBuffer1.setFont(Font.font("Arial", FontWeight.BOLD, 45));
        spacingBuffer2.setFont(Font.font("Arial", FontWeight.BOLD, 5));

        HBox buttons = new HBox(submitButton, deleteButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(2);

        center.setSpacing(3);
        center.getChildren().addAll(spacingBuffer1, title, spacingBuffer2, boxs[0], boxs[1], boxs[2], boxs[3], boxs[4], boxs[5], buttons);
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
                tempAppointment = new Appointment(tempArr[0], tempArr[1], tempArr[2], Boolean.parseBoolean(tempArr[3]), Integer.parseInt(tempArr[4]), Integer.parseInt(tempArr[5]), tempArr[6], Integer.parseInt(tempArr[7]));
   
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
                fileWriterUser.write(a.getType() + "," + a.getStartDate() + "," + a.getEndDate() + "," + a.getAvailability() + "," + a.getProvider().getID() + "," + a.getCustomer().getID() + "," + a.getCost() + "," + a.getID() + "\n");
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
                tempAppointment = new Appointment(tempArr[0], tempArr[1], tempArr[2], Boolean.parseBoolean(tempArr[3]), Integer.parseInt(tempArr[4]), Integer.parseInt(tempArr[5]), tempArr[6], Integer.parseInt(tempArr[7]));
   
                //keep note if email is found
                if(appointment.getID() == tempAppointment.getID()){
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
                fileWriterUser.write(a.getType() + "," + a.getStartDate() + "," + a.getEndDate() + "," + a.getAvailability() + "," + a.getProvider().getID() + "," + a.getCustomer().getID() + "," + a.getCost() + "," + a.getID() + "\n");
            }

            fileWriterUser.close();

        }catch(IOException except){
            System.out.println(except);
        }

        appointmentListForDay.remove(index);
        switcher.switchToAppointmentsPageBusiness(editAppointmentsPage.getWindow(), primaryStage, appointmentListForDay);

    }

}