import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.SQLException;
// the imports of the class.

// in case we arldy had an account we are redirected here, where the client can log in.
// the user can log in by entering his login and his password.
// when the user clicks on the log in button, the user is redirected to the third scene.(Scene3.java)

public class Login extends StackPane{
    public Scene createScene() {
        StackPane root2 = new StackPane(); // create a stackpane to add the elements to it.
        // we create the elements of the login page.
        // a button for logging in as the final step.
        Button Connexion = new Button();
        Connexion.setText("connexion");
        Connexion.setTranslateY(300);
        Connexion.setMinWidth(100);
        Connexion.setMinHeight(50);
        // a textfield for the login.
        TextField login = new TextField();
        login.setPromptText("Enter your login");
        login.setTranslateY(-200);
        login.setMaxWidth(400);
        // a passwordfield for the password.
        PasswordField password = new PasswordField();
        password.setPromptText("Enter your password");
        password.setTranslateY(0);
        password.setMaxWidth(400);

        // we create the background with style and especially with relatives paths. 
        Image gray= new Image("file:SocialNetworkLast/PNGS/motif.png");
        ImageView Gray = new ImageView(gray);
        Gray.setFitHeight(1100);
        Gray.setFitWidth(2000);
        Image mozart = new Image("file:SocialNetworkLast/PNGS/mozart.png");
        ImageView Mozart = new ImageView(mozart);
        Mozart.setPreserveRatio(true);
        Mozart.setTranslateX(-600);
        Mozart.setFitWidth(600);
        Image bach= new Image("file:SocialNetworkLast/PNGS/bach.png");
        ImageView Bach = new ImageView(bach);
        Bach.setPreserveRatio(true);
        Bach.setTranslateX(600);
        Bach.setFitWidth(600);

        // we configurate the button event.
        // this time it will check for a possible account in the database.
        Connexion.setOnAction(event -> {
            String loginText = login.getText(); // get the login entered by the user.
            String passwordText = password.getText(); // get the password entered by the user.
            if (loginText.isEmpty() || passwordText.isEmpty()) { // if the user didn't enter all the fields.
                Alert uncompleted = new Alert(AlertType.ERROR); // create an error message and display it !
                uncompleted.setTitle("Error");
                uncompleted.setHeaderText("Uncompleted fields");
                uncompleted.setContentText("Please fill all the fields");
                uncompleted.showAndWait(); 
            } else { // if the user entered all the fields.
                try { // try to connect to the database.
                    // get the user from the database with the login entered by the user.
                    Users userloged = Users.getuseronloginBDD(loginText);  
                    // if the user exists and the password entered by the user is the same as the password of the user.
                    if (userloged != null && userloged.getpassword().equals(passwordText)) { 
                        Scene3 scene3 = new Scene3(); // we can show him his profile.(Scene3.java)
                        Scene newScene = scene3.createScene(userloged); // create the scene.
                        // create a new stage and show the scene.
                        Stage tertiaryStage = new Stage();
                        tertiaryStage.setFullScreen(true);
                        tertiaryStage.setScene(newScene);
                        tertiaryStage.show();
                    } else { // if the user doesn't exist or the password is incorrect.
                        Alert uncompleted = new Alert(AlertType.ERROR);
                        uncompleted.setTitle("Error");
                        uncompleted.setHeaderText("Incorrect login");
                        uncompleted.setContentText("The login or password you entered is incorrect");
                        uncompleted.showAndWait();
                        // show an error message to make the user aware of the problem and doubt about his own life. 
                    }
                // if the connection to the database failed.
                } catch (SQLException e) {
                    // we display another kind of error message.
                    e.printStackTrace();
                    System.out.println("Error while connecting to the database");
                    Alert uncompleted = new Alert(AlertType.ERROR);
                    uncompleted.setTitle("Error");
                    uncompleted.setHeaderText("Database error");
                    uncompleted.setContentText("There was an error while connecting to the database");
                    uncompleted.showAndWait();
                }
            }
        });

        // we add all the elements to the stackpane.
        root2.getChildren().add(Gray);
        root2.getChildren().add(Bach);
        root2.getChildren().add(Mozart);
        root2.getChildren().add(login);
        root2.getChildren().add(password);
        root2.getChildren().add(Connexion);

        // we create the scene and we return it.
        Scene scene2= new Scene(root2, 400, 400);
        return scene2;
    }
}