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
    Label errorLabel = new Label();
    private Appointment appointment;

    public EditAppointments(Stage primaryStage, Appointment appointment, List<Appointment> appointmentListForDay, Label label){

        businessLoggedin = (Business) primaryStage.getUserData();

        errorLabel = label;

        this.appointment = appointment;

        switcher = new SceneSwitcher(primaryStage);
    
        businessLoggedin = (Business) primaryStage.getUserData();

        HBox sidebar = sideBar(primaryStage);

        BorderPane layout = new BorderPane();
        layout.setLeft(sidebar);

        VBox center = mainPage(primaryStage, appointmentListForDay);
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

    public VBox mainPage(Stage primaryStage, List<Appointment> appointmentListForDay){
        VBox center = new VBox();
        center.setAlignment(Pos.TOP_CENTER);
        Button submitButton = new Button("Submit");
        Button deleteButton = new Button("Delete");
        HBox [] boxs = new HBox[7];
        for(int i = 0; i < 7; i++){
            boxs[i] = new HBox();
            boxs[i].setAlignment(Pos.CENTER_LEFT);
        }

        Label typeLabel = new Label("\t\t Appointment Type: ");
        TextField type = new TextField(appointment.getType());
        String userType = type.getText();
        boxs[0].getChildren().addAll(typeLabel, type);

        Label dateLabel = new Label("\t\t Date (MM/DD/YY) : ");
        ZonedDateTime startDate = ZonedDateTime.parse(appointment.getStartDate(), formatter);
        ZonedDateTime endDate = ZonedDateTime.parse(appointment.getEndDate(), formatter);
        String amPM = "AM";

        ObservableList<String> months = FXCollections.observableArrayList(
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
        );
        final ComboBox<String> month = new ComboBox<String>(months);
        month.setPromptText("Month");
        if(startDate.getMonthValue() < 10){
            month.setValue("0" + Integer.toString(startDate.getMonthValue()));
        }else{
            month.setValue(Integer.toString(startDate.getMonthValue()));
        }

        ObservableList<String> days = FXCollections.observableArrayList(
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
        );
        final ComboBox<String> day = new ComboBox<String>(days);
        day.setPromptText("Day");
        if(startDate.getDayOfMonth() < 10){
        day.setValue("0" + Integer.toString(startDate.getDayOfMonth()));
        }else{
            day.setValue(Integer.toString(startDate.getDayOfMonth()));
        }

        ObservableList<String> years = FXCollections.observableArrayList(
            "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034"
        );
        final ComboBox<String> year = new ComboBox<String>(years);
        year.setPromptText("Year");
        year.setValue(Integer.toString(startDate.getYear()));

        boxs[1].getChildren().addAll(dateLabel, month, day, year);


        Label timeLabel = new Label("\t\t  Start Time (HH:SS): ");
        ObservableList<String> hours = FXCollections.observableArrayList(
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
        );
        final ComboBox<String> hour = new ComboBox<String>(hours);
        hour.setPromptText("Hour");
        int tempHour = startDate.getHour();
        if(tempHour > 12){
            amPM = "PM";
            if(tempHour < 10){
                hour.setValue("0" + Integer.toString(tempHour - 11));
            }else{
                hour.setValue(Integer.toString(tempHour - 11));
            }

        }else{
            if(tempHour < 10){
                if(tempHour == 0){
                    hour.setValue("12");
                }else{
                    hour.setValue("0" + Integer.toString(tempHour));
                }
            }else{
                hour.setValue(Integer.toString(tempHour));
            }
        }

        ObservableList<String> mins = FXCollections.observableArrayList(
            "00", "15", "30", "45"
        );
        final ComboBox<String> min = new ComboBox<String>(mins);
        min.setPromptText("Min");
        if(startDate.getMinute() < 10){
            min.setValue("0" + Integer.toString(startDate.getMinute()));
        }else{
            min.setValue(Integer.toString(startDate.getMinute()));
        }

        ObservableList<String> time = FXCollections.observableArrayList(
            "AM", "PM"
        );
        final ComboBox<String> comboBox6 = new ComboBox<String>(time);
        comboBox6.setPromptText("AM/PM");
        comboBox6.setValue(amPM);

        boxs[2].getChildren().addAll(timeLabel, hour, min, comboBox6);

        Label availabilityLabel = new Label("\t\t\t      Availability:  ");
         ObservableList<String> availability = FXCollections.observableArrayList(
            "true", "false"
        );
        final ComboBox<String> comboBoxAvailability = new ComboBox<String>(availability);
        comboBoxAvailability.setValue(Boolean.toString(appointment.getAvailability()));
        Boolean userAvailability = Boolean.parseBoolean((String) comboBoxAvailability.getSelectionModel().getSelectedItem());
        boxs[3].getChildren().addAll(availabilityLabel, comboBoxAvailability);

        String endAMPM = "AM";
        Label endTimeLabel = new Label("\t\t    End Time (HH:SS): ");
        ObservableList<String> endHours = FXCollections.observableArrayList(
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
        );
        final ComboBox<String> comboBox7 = new ComboBox<String>(endHours);
        comboBox7.setPromptText("Hour");
        tempHour = endDate.getHour();
        if(tempHour > 12){
            amPM = "PM";
            if(tempHour < 10){
                comboBox7.setValue("0" + Integer.toString(tempHour - 11));
            }else{
                comboBox7.setValue(Integer.toString(tempHour - 11));
            }

        }else{
            if(tempHour < 10){
                if(tempHour == 0){
                    comboBox7.setValue("12");
                }else{
                    comboBox7.setValue("0" + Integer.toString(tempHour));
                }
            }else{
                comboBox7.setValue(Integer.toString(tempHour));
            }
        }

        ObservableList<String> endMins = FXCollections.observableArrayList(
            "00", "15", "30", "45"
        );
        final ComboBox<String> comboBox8 = new ComboBox<String>(endMins);
        comboBox8.setPromptText("Min");
        if(startDate.getMinute() < 10){
            comboBox8.setValue("0" + Integer.toString(startDate.getMinute()));
        }else{
            comboBox8.setValue(Integer.toString(startDate.getMinute()));
        }

        ObservableList<String> endTime = FXCollections.observableArrayList(
            "AM", "PM"
        );
        final ComboBox<String> comboBox9 = new ComboBox<String>(endTime);
        comboBox9.setPromptText("AM/PM");
        comboBox9.setValue(endAMPM);


        boxs[4].getChildren().addAll(endTimeLabel, comboBox7, comboBox8, comboBox9);

        Label customerLabel = new Label("\t\t     Customer Name:  ");
        Label customer = new Label(appointment.getCustomer().getName());
        boxs[5].getChildren().addAll(customerLabel, customer);

        Label costLabel = new Label("\t\t\t\t\tCost:  ");
        TextField cost = new TextField(appointment.getCost());
        String userCost = cost.getText();
        boxs[6].getChildren().addAll(costLabel, cost);

        Appointment tempAppointment = appointment;
        submitButton.setOnAction(e->{
            tempAppointment.setStartDate((String) year.getSelectionModel().getSelectedItem() + "-" + (String) month.getSelectionModel().getSelectedItem() + "-" + (String) day.getSelectionModel().getSelectedItem() + " " + (String) hour.getSelectionModel().getSelectedItem() + ":" + (String) min.getSelectionModel().getSelectedItem() + ":00 " + (String) comboBox6.getSelectionModel().getSelectedItem() + " -05:00");
            tempAppointment.setEndDate((String) year.getSelectionModel().getSelectedItem() + "-" + (String) month.getSelectionModel().getSelectedItem() + "-" + (String) day.getSelectionModel().getSelectedItem() + " " + (String) comboBox7.getSelectionModel().getSelectedItem() + ":" + (String) comboBox8.getSelectionModel().getSelectedItem() + ":00 " + (String) comboBox9.getSelectionModel().getSelectedItem() + " -05:00");
            //System.out.println(tempAppointment.getStartDate());
            //System.out.println(tempAppointment.getEndDate());
            if(userType != null && tempAppointment.stringToStartDate().isAfter(ZonedDateTime.now()) && tempAppointment.stringToEndDate().isAfter(tempAppointment.stringToStartDate())){
                System.out.println("True");
                tempAppointment.setType(userType);
                tempAppointment.setAvailability(userAvailability);
                tempAppointment.setCost(userCost);
                changeAppointment(primaryStage, tempAppointment, appointmentListForDay);
            }else if(userType == null){
                errorLabel.setText("Please enter an appointment name");
            }else if(tempAppointment.stringToStartDate().isBefore(ZonedDateTime.now())){
                errorLabel.setText("Please enter a valid start date");
            }else if(tempAppointment.stringToEndDate().isBefore(tempAppointment.stringToStartDate())){
                errorLabel.setText("Please enter a valid end date");
            }
            System.out.println("Nothing");
        });

        deleteButton.setOnAction(e->{
            delete(primaryStage, appointmentListForDay);
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
        center.getChildren().addAll(spacingBuffer1, title, spacingBuffer2, boxs[0], boxs[1], boxs[2], boxs[4], boxs[3], boxs[5], boxs[6], buttons, errorLabel);
        return center;

    }

    public void changeAppointment(Stage primaryStage, Appointment passedAppointment, List<Appointment> appointmentListForDay){
        List<Appointment> totalAppointmentList = new ArrayList<>();
        Boolean alreadyExists = false;
        try{
            //set up FileReader
            FileReader fileReaderAccount = new FileReader("appointmentList.csv");
            BufferedReader br = new BufferedReader(fileReaderAccount);
            String line = "";
            String[] tempArr;
            Appointment tempAppointment;
            ZonedDateTime tempEndDate, tempStartDate;
   
            //read in data and determine if appointment already exists
            while((line = br.readLine()) != null){
                tempArr = line.split(",");
                tempAppointment = new Appointment(tempArr[0], tempArr[1], tempArr[2], Boolean.parseBoolean(tempArr[3]), Integer.parseInt(tempArr[4]), Integer.parseInt(tempArr[5]), tempArr[6], Integer.parseInt(tempArr[7]));
                tempEndDate = tempAppointment.stringToEndDate();
                tempStartDate = tempAppointment.stringToStartDate();

                //keep note if email is found
                if(tempAppointment.getProvider().getID() == businessLoggedin.getID() && ((passedAppointment.stringToStartDate().isBefore(tempEndDate) && passedAppointment.stringToStartDate().isAfter(tempStartDate)) || (passedAppointment.stringToEndDate().isBefore(tempEndDate) && passedAppointment.stringToEndDate().isAfter(tempStartDate))) && tempAppointment.getID() != passedAppointment.getID() && !(passedAppointment.stringToStartDate().equals(tempStartDate) && passedAppointment.stringToEndDate().equals(tempEndDate))){
                    alreadyExists = true;
                }
                if(passedAppointment.getID() == tempAppointment.getID()){
                    tempAppointment = passedAppointment;
                }

                totalAppointmentList.add(tempAppointment);

            }
            br.close();
        }catch(IOException except){
            System.out.println(except);
        }

        if(!alreadyExists){
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
                if(a.getID() == passedAppointment.getID()){
                    break;
                }
                index++;
            }

            appointmentListForDay.set(index, passedAppointment);
            switcher.switchToAppointmentsPageBusiness(editAppointmentsPage.getWindow(), primaryStage, appointmentListForDay);

        }else{
            errorLabel.setText("An appointment already exists at this time");
            switcher.switchToEditAppointmentsPage(editAppointmentsPage.getWindow(), primaryStage, appointment, totalAppointmentList, errorLabel);
        }
    }

    public void delete(Stage primaryStage, List<Appointment> appointmentListForDay){
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