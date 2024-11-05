
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.format.DateTimeFormatter;
// the imports of the class. 

// this class is the second scene of the application, it is the sign up page.
// the user can sign up by entering his login, his last name, his first name, his password and his birthdate.
// when the user clicks on the sign up button, the user is added to the database and redirected to the third scene.
// if the user doesn't fill all the fields, an error message will appear.
// the background is floral pattern with pictures of famous composers.


public class Scene2 extends StackPane {
    public Scene createScene() {
        StackPane root2 = new StackPane(); // create a stackpane to add the elements to it.
        // we create the elements of the sign up page.
        // a button for signing up as the final step.
        Button Connexion = new Button();
        Connexion.setText("connexion");
        Connexion.setTranslateY(300);
        Connexion.setMinWidth(100);
        Connexion.setMinHeight(50);
        // a textfield for the login.
        TextField login = new TextField();
        login.setPromptText("Enter your login");
        login.setTranslateY(-100);
        login.setMaxWidth(400);
        // a textfield for the last name.
        TextField Nom = new TextField();
        Nom.setPromptText("Enter your Last name");
        Nom.setTranslateY(-200);
        Nom.setMaxWidth(400);
        // a textfield for the first name.
        TextField prenom = new TextField();
        prenom.setPromptText("Enter your First name");
        prenom.setTranslateY(-300);
        prenom.setMaxWidth(400);
        // a passwordfield for the password.
        PasswordField password = new PasswordField();
        password.setPromptText("Enter your password");
        password.setTranslateY(0);
        password.setMaxWidth(400);
        // a datepicker for the birthdate.
        DatePicker Birthdate = new DatePicker();
        Birthdate.setPromptText("Enter your Birthdate");
        Birthdate.setTranslateY(100);
        Birthdate.setMaxWidth(400);

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

        // we set up the action of the button connexion
        // it will create a new user with the informations entered by the user.
        // the user will be added to the database.
        Connexion.setOnAction(event -> {
            // first we take the elements entered by the user.
            String loginText = login.getText(); 
            String lastnametext = Nom.getText();
            String firstnametext = prenom.getText();
            String passwordText = password.getText();
            String nametagText = lastnametext + " " + firstnametext;
            LocalDate BirthdateText = LocalDate.of(Birthdate.getValue().getYear(), Birthdate.getValue().getMonth(), Birthdate.getValue().getDayOfMonth());
            // we check if the fields are not empty.
            if (!loginText.isEmpty() && !nametagText.isEmpty() && !passwordText.isEmpty() && BirthdateText != null) {
                // and if they are not empty we,...
                Scene3 scene3 = new Scene3(); // ...Create an instance of the Scene3 class
                
                // we generate a new id for the user.
                int unnewid = Users.generateNewId();
                // we use every information gathered to create a new user.
                Users usercrea = new Users( unnewid, loginText, nametagText, passwordText, BirthdateText);
                // we add the user to the database.
                try {
                    String url = Users.URLBDD; 
                    Connection connecting = null; 
                    connecting= DriverManager.getConnection(url); // connection with the url
                    // we insert the user in the database.
                    String sql = "INSERT INTO USERS(idU, login, mdp, nom, prenom, dateNaiss) VALUES(?,?,?,?,?,?)";
                    
                    // each statement will import one infomation. 
                    PreparedStatement pstmt = connecting.prepareStatement(sql);
                    pstmt.setInt(1, unnewid);
                    pstmt.setString(2, loginText);
                    pstmt.setString(3, passwordText);
                    pstmt.setString(4, lastnametext);
                    pstmt.setString(5, firstnametext);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
                    String formatteddate = BirthdateText.format(formatter);
                    pstmt.setString(6, formatteddate);
                    // corresponding to the 6 ? in the sql statement.

                    // we are updating the database with precautions
                    try {
                        pstmt.executeUpdate();
                    } catch (Exception e) {
                        System.err.println("Error when dbb update: " + e.getMessage());
                    }
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
                
                // once we have the user in the database, we create the third scene.
                Scene newScene = null;
                try { 
                    newScene = scene3.createScene(usercrea);
                } catch (SQLException e) {
                    System.err.println("Error when creating scene3: " + e.getMessage());
                }

                // we show the third scene.
                Stage tertiaryStage = new Stage();
                tertiaryStage.setFullScreen(true);
                tertiaryStage.setScene(newScene);
                tertiaryStage.show();
            } else {
                // if the fields are empty, we show an error message.
                Alert uncompleted = new Alert(AlertType.ERROR);
                uncompleted.setTitle("Error");
                uncompleted.setHeaderText("Uncompleted fields");
                uncompleted.setContentText("Please fill all the fields");
                uncompleted.showAndWait();
            }
        });

        // we add all the elements to the stackpane.
        root2.getChildren().add(Gray);
        root2.getChildren().add(Bach);
        root2.getChildren().add(Mozart);
        root2.getChildren().add(login);
        root2.getChildren().add(Nom);
        root2.getChildren().add(prenom);
        root2.getChildren().add(password);
        root2.getChildren().add(Birthdate);
        root2.getChildren().add(Connexion);

        // we create the scene and we return it.
        Scene scene2= new Scene(root2, 400, 400);
        return scene2;
    }
}
