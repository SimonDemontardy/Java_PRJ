import java.sql.SQLException;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
// imports pour la classe Main
// 
public class Main extends Application { // Main class extends Application
    public static void main(String[] args) { // main method
        launch(args); // launch the application
    } 

    public static final String URLBDD = "jdbc:sqlite:/home/simon/myLink.db"; // URL of the database
    
    @Override // start method
    public void start(Stage primaryStage) throws SQLException { 
        // title creation
        primaryStage.setTitle("social Network"); // title of the stage,
        Scene1 scene1 = new Scene1(); // create a new Scene1 object,
            Scene scene = scene1.createScene(); // create a new scene,
            primaryStage.setScene(scene); // set the scene,
            primaryStage.setFullScreen(true); // set the stage to full screen,
            primaryStage.show(); // show the stage,
        // here im calling the first scene to display the login page and start the social network. 


    }
}
