
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
// import for the Scene 1. 

// this class is the first scene of the application, it is the first thing that the user will see when he launches the application.
// it contains two buttons, one for signing up and the other for logging in.
// the user can choose to sign up or log in, if he chooses to sign up, he will be redirected to the sign up page, 
// if he chooses to log in, he will be redirected to the log in page.

// the background is a music partition. 


public class Scene1 extends StackPane {
    public Scene createScene() { // this method creates the scene 1 we call in the main. 
        StackPane root = new StackPane(); // we create a stackpane to add the elements to it.
        //Image sus = new Image("file:///home/simon/PNGS/sus.png"); 
        Image wallpapermusic = new Image("file:SocialNetworkLast/PNGS/wallpapermusic.png"); // initialize the background image (relative path). 
        ImageView imageView2 = new ImageView(wallpapermusic); // allow to view the picture.


        Button sign = new Button(); // create the sign up button.
        sign.setText("Sign up!"); // set the text of the button.
        sign.setFont(new Font("Times New Roman",40));  // set the font of the text.
        sign.setMinWidth(100);  // set the width of the button.
        sign.setMinHeight(50); // set the height of the button.
        sign.setTextFill(Color.BLACK); // set the color of the text.
        sign.setOnAction(event -> { // when the button is clicked, we create a new scene (sign up page) and we show it.
            Scene2 scene2 = new Scene2(); // the sign up page is created. (see Scene2.java)
            Scene newScene = scene2.createScene();
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(newScene);
            secondaryStage.setFullScreen(true);
            secondaryStage.show();
        });        
        Button log = new Button(); 
        log.setText("Log in");
        log.setFont(new Font("Times New Roman",40));
        log.setMinWidth(100);
        log.setMinHeight(50);
        log.setTranslateY(100);
        log.setTextFill(Color.BLACK);
        log.setOnAction(event -> { // when the button is clicked, we create a new scene (log in page) and we show it.
            Login login = new Login(); // the log in page is created. (see Login.java)
            Scene newScene = login.createScene(); 
            Stage LoginStage = new Stage(); 
            LoginStage.setScene(newScene);
            LoginStage.setFullScreen(true);
            LoginStage.show();
        });


        imageView2.setFitWidth(1920); // set the width of the image.
        imageView2.setFitHeight(1600); // set the height of the image.
        imageView2.setPreserveRatio(true); // keep the ratio of the image.

        // create a label to welcome the user.
        Label welcoming = new Label("Welcome, fellow music composer !"); 
        welcoming.setFont(new Font("Arial",40));
        welcoming.setTranslateY(-300);
        welcoming.setTextFill(Color.WHITESMOKE);
        
        // create a label to welcome the user.
        Label welcoming2 = new Label("ready to share your art ?");
        welcoming2.setFont(new Font("Arial",40));
        welcoming2.setTranslateY(-200);
        welcoming2.setTextFill(Color.WHITE);

        // add the elements to the stackpane.
        root.getChildren().add(imageView2);
        root.getChildren().add(log);
        root.getChildren().add(sign);
        root.getChildren().add(welcoming);
        root.getChildren().add(welcoming2);
        
        // Finally we create the scene and we return it for this method. 
        Scene scene = new Scene(root, 400, 400);
        return scene;
    }
    
}