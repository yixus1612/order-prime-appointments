import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application{

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Appointments");

        LoginPage Login = new LoginPage(primaryStage);

        primaryStage.setScene(Login.loginPage);
        primaryStage.show();
    }
}
