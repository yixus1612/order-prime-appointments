import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.AnchorPane;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarPage {

    public Scene calendarPage;
    private Rectangle scheduleTabRectangle, calendarTabRectangle, appointmentsTabRectangle, settingsTabRectangle;
    private Text scheduleTabText, calendarTabText, appointmentsTabText, settingsTabText;
    private Rectangle buffer1, buffer2;
    private ZonedDateTime userDate = ZonedDateTime.now();
    private ZonedDateTime today = ZonedDateTime.now();
    private AnchorPane background = new AnchorPane();
    private FlowPane calendar = new FlowPane();
    private BorderPane layout = new BorderPane();
    private double calendarWidth = 490;
    private double calendarHeight = 400; 
    private Text year = new Text(); 
    private Text month = new Text();
    private Button leftButton, rightButton;
    private User userLoggedin = new User();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a z");
    private SceneSwitcher switcher;
    private int index = 0;


    public CalendarPage(Stage primaryStage){

        userLoggedin = (User) primaryStage.getUserData();

        switcher = new SceneSwitcher(primaryStage);

        HBox sidebar = sideBar(primaryStage);
        layout.setLeft(sidebar);

        HBox yearBar = yearBox(primaryStage);

        HBox weekBar = weekBox();
        
        HBox calendarBox = new HBox();
        calendar.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        calendar.setPrefWidth(calendarWidth);
        drawCalendar(primaryStage);
        calendarBox.getChildren().addAll(calendar);

        background.getChildren().addAll(yearBar, weekBar, calendar);
        AnchorPane.setLeftAnchor(yearBar, 190.0);
        AnchorPane.setTopAnchor(yearBar, 0.0);
        AnchorPane.setTopAnchor(weekBar, 30.0);
        AnchorPane.setLeftAnchor(weekBar, 30.0);
        AnchorPane.setTopAnchor(calendar, 50.0);
        AnchorPane.setLeftAnchor(calendar, 10.0);
        layout.setCenter(background);

        calendarPage = new Scene(layout, 600, 500);
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
        calendarTabText.setFont(Font.font ("Arial", FontWeight.BOLD, 12));
        calendarTabRectangle = new Rectangle(110,25, Color.web("#f2efd0"));
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
        tabStack.getChildren().addAll(buffer1, pfp, name, buffer2, scheduleTab, calendarTab, appointmentsTab, settingsTab);
        
        HBox sidebar = new HBox(tabStack, sidebarSeparator);
        sidebar.setBackground(new Background(new BackgroundFill(Color.web("#4681e0"), null, null)));

        Label tempLabel = new Label();
        scheduleTabRectangle.setOnMouseClicked(e -> switcher.switchToAppointmentSchedulingPage(calendarPage.getWindow(), primaryStage, tempLabel));
        scheduleTabText.setOnMouseClicked(e -> switcher.switchToAppointmentSchedulingPage(calendarPage.getWindow(), primaryStage, tempLabel));

        appointmentsTabRectangle.setOnMouseClicked(e->switcher.switchToAppointmentsPage(calendarPage.getWindow(), primaryStage, userLoggedin.appointmentList));
        appointmentsTabText.setOnMouseClicked(e -> switcher.switchToAppointmentsPage(calendarPage.getWindow(), primaryStage, userLoggedin.appointmentList));

        settingsTabRectangle.setOnMouseClicked(e -> switcher.switchToSettingsPage(calendarPage.getWindow(), primaryStage));
        settingsTabText.setOnMouseClicked(e -> switcher.switchToSettingsPage(calendarPage.getWindow(), primaryStage));

        return sidebar;

    }

    public HBox yearBox(Stage primaryStage){
        //set the year of the current selected year and month
        year.setText(String.valueOf(userDate.getYear()));
        month.setText(String.valueOf(userDate.getMonth()));

        //create back button to switch to previous year
        leftButton = new Button("<");
        leftButton.setOnAction(e->{
            userDate = userDate.minusMonths(1);
            calendar.getChildren().clear();
            drawCalendar(primaryStage);
        });

        //create forward button to switch to forward year
        rightButton = new Button(">");
        rightButton.setOnAction(e->{
            userDate = userDate.plusMonths(1);
            calendar.getChildren().clear();
            drawCalendar(primaryStage);
        });

        HBox yearBar = new HBox();
        yearBar.getChildren().addAll(leftButton, month, year, rightButton);
        yearBar.setSpacing(5);
        yearBar.setAlignment(Pos.CENTER);

        return yearBar;
    }

    //creates week days on calendar
    public HBox weekBox(){
        Text monday = new Text("Mon");
        Text tuesday = new Text("Tue");
        Text wednesday = new Text("Wed");
        Text thursday = new Text("Thu");
        Text friday = new Text("Fri");
        Text saturday = new Text("Sat");
        Text sunday = new Text("Sun");
        HBox weekBar = new HBox();
        weekBar.getChildren().addAll(sunday, monday, tuesday, wednesday, thursday, friday, saturday);
        weekBar.setSpacing(45);
        return weekBar;
    }

    public void drawCalendar(Stage primaryStage){
        //set the year of the current selected year and month
        year.setText(String.valueOf(userDate.getYear()));
        month.setText(String.valueOf(userDate.getMonth()));

        //set up size of calendar
        calendar.setVgap(5);
        calendar.setHgap(5);
        double rectangleWidth = (calendarWidth - 5)/8;
        double rectangeHeight = (calendarHeight - 5)/6;

        //get appointments for the month
        Map<Integer, List<Appointment>> appointmentMap = getAppointments(userDate);//integer points to day and list points to appointment

        //get maximum days in the selected month
        int maxDate = userDate.getMonth().maxLength();

        if(userDate.getYear() % 4 != 0 && maxDate == 29){
            maxDate = 28;
        }

        //find the start day on the calendar
       int startDate = ZonedDateTime.of(userDate.getYear(), userDate.getMonthValue(), 1, 0, 0, 0, 0, userDate.getZone()).getDayOfWeek().getValue();

       //go through each rectangle
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
                StackPane stackPane = new StackPane();
            
                //create boxes for calendar
                Rectangle calendarRectangles = new Rectangle();
                calendarRectangles.setFill(Color.TRANSPARENT);
                calendarRectangles.setStroke(Color.BLACK);
                calendarRectangles.setWidth(rectangleWidth);
                calendarRectangles.setHeight(rectangeHeight);
                stackPane.getChildren().add(calendarRectangles);

                //put numbers on calendar
                int calculatedDate = (1+j)+(i*7);//calculate the "day" on the calendar
                int currentDate = calculatedDate - startDate;
                if(calculatedDate > startDate){//if the calculated date is after the startDate then display date
                    if(currentDate <= maxDate){// if current date is before the max date then display date

                        //add labels to day
                        Text date = new Text(String.valueOf(currentDate));
                        date.setTranslateY(-(rectangeHeight/2)* .75);
                        stackPane.getChildren().add(date);
                        Label label = new Label();
                        calendarRectangles.setOnMouseClicked(e->switcher.switchToAppointmentSchedulingPage(calendarPage.getWindow(), primaryStage, label));

                        //get appointments for the day
                        List<Appointment> appointmentList = appointmentMap.get(currentDate);
                        if(appointmentList != null){//if there are appointments create a graphic list
                            createAppointment(appointmentList, rectangeHeight, rectangleWidth, stackPane, primaryStage);
                        }
                    }

                    //find the current day and highlight it
                    if(today.getYear() == userDate.getYear() && today.getMonth() == userDate.getMonth() && today.getDayOfMonth() == currentDate){
                        calendarRectangles.setStroke(Color.RED);
                    }
                }
            calendar.getChildren().add(stackPane);
            }
        }
          
    }

    //create the appointments on calendar
    public void createAppointment(List<Appointment> appointments, double height, double width, StackPane stack, Stage primaryStage){
        VBox appointmentBox = new VBox();
        for(int i = 0; i < appointments.size(); i++){//loop through appointment list
            if(i >= 1){//add a see more option if it doesn't fit on calendar
                Text more = new Text("...");
                more.setFill(Color.WHITE);
                appointmentBox.getChildren().add(more);
                more.setOnMouseClicked(e-> {
                    switcher.switchToAppointmentsPage(calendarPage.getWindow(), primaryStage, appointments);
                });
                break;
            }
            Text text = new Text(appointments.get(i).getType());
            text.setFill(Color.WHITE);
            appointmentBox.getChildren().add(text);
            index = i;
            //switch to view appointment if clicked on appointment
            text.setOnMouseClicked(e->{
                switcher.switchToViewAppointmentPage(calendarPage.getWindow(), primaryStage, appointments.get(index));
            });
        }
        //box details
        appointmentBox.setTranslateY((height/2) * .2);
        appointmentBox.setMaxHeight(height * .1);
        appointmentBox.setMaxWidth(width * .8);
        appointmentBox.setStyle("-fx-background-color:BLUE");
        stack.getChildren().add(appointmentBox);

    }

    //create the appointment list map for calendar
    public Map<Integer, List<Appointment>> createMap(List<Appointment> appointments){
        Map<Integer, List<Appointment>> appointmentMap = new HashMap<>();

        for(Appointment appointment: appointments){
            int appointmentDate = appointment.stringToStartDate().getDayOfMonth();
            //if key day doesnt correlate to day selected then add appointment list
            if(!appointmentMap.containsKey(appointmentDate)){
                appointmentMap.put(appointmentDate, List.of(appointment));
            }else{//if not then make a new list and assign it to the map
                List<Appointment> OldListByDate = appointmentMap.get(appointmentDate);
                List<Appointment> newList = new ArrayList<>(OldListByDate);
                newList.add(appointment);
                appointmentMap.put(appointmentDate, newList);
            }
        }
        return appointmentMap;
    }

    private Map<Integer, List<Appointment>> getAppointments(ZonedDateTime date){
        List<Appointment> appointments = new ArrayList<>();
        int year = date.getYear();
        int month = date.getMonth().getValue();

        try{
               
            //set up FileReader
            FileReader fileReaderAccount = new FileReader("appointmentList.csv");
            BufferedReader br = new BufferedReader(fileReaderAccount);
            String line = "";
            String[] tempArr;
            int tempCustomer;
            ZonedDateTime tempDate;
       
            //read in data and determine if appointment already exists
            while((line = br.readLine()) != null){
                tempArr = line.split(",");
                tempDate = ZonedDateTime.parse(tempArr[1], formatter);
                tempCustomer = Integer.parseInt(tempArr[5]);
       
                //keep note if customer id is found and has the same date
                if(tempCustomer == userLoggedin.getID() && tempDate.getYear() == year && tempDate.getMonth().getValue() == month){
                    appointments.add(new Appointment(tempArr[0], tempArr[1], tempArr[2], Boolean.parseBoolean(tempArr[3]), Integer.parseInt(tempArr[4]), Integer.parseInt(tempArr[5]), tempArr[6], Integer.parseInt(tempArr[7])));
                }
            }
            br.close();
        }catch(IOException except){
            System.out.println(except);
        }

        return createMap(appointments);
    }

}
