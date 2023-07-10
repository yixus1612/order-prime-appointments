import javafx.application.Application;
import javafx.scene.Parent;
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
