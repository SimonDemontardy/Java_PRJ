
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
// the imports for javafx elements.

import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
// the imports for Dates (normal java elements)

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// the imports for the database.

// this Scene is quite big i confess, but it is the currentUser home page.
// the user can see his posts, delete them, like them, and create new ones.
// the user can also see the other Users . 




public class Scene3 extends StackPane {
    // we initialize the variables we will use in the class.
    Users selectedUser; // the user we are going to see the wall.
    static Users currentUser; // the user who is connected.
    private static final String URLBDD = Users.URLBDD; // the URL of the database coming from the Users class.

    // the constructor of the class (we create the scene with the user we are going to see the wall of (btw the current User)).
    public Scene createScene(Users user) throws SQLException {
        // we create the stackPane that will contain all the elements of the scene.
        StackPane root3 = new StackPane();

        // first we take care of the background so its done.
        Image fond= new Image("file:SocialNetworkLast/PNGS/png.png"); // searching for the background image. 
        VBox imagebox = new VBox(); // we create a VBox to put the images in it.
        for (int i =0; i< 5; i++) { // we add 5 images to the VBox.
            ImageView Floral = new ImageView(fond); // we create the image view
            Floral.setFitWidth(1900);   // we set the width of the image
            Floral.setPreserveRatio(true); // we set the ratio of the image
            imagebox.getChildren().add(Floral); // we add the image to the VBox
        }
        imagebox.setSpacing(-5); // a lil parameter to avoid a blank space between the images.
        // ugly background but it is done.

        // some other variables that we will use in the class
        currentUser = user; // we are making sure that we keep the current user in the class.
        int userid= currentUser.getiDU(); // we get the idU of the current user.
        @SuppressWarnings("static-access") // we are going to use the static method of the Users class.
        List<Post> Postalui = currentUser.getPostsFromBDD(userid); // we get the posts of the current user from the database. 
        currentUser.setPostlist(Postalui); // we add them in the postlist of the current user.
        List<Post> SonMur = currentUser.getPostlist(); // we get the posts of the current user.
        
        

        


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/// first a layer of the differents boxs in which we will add the differents Posts. 
        // introduction of different stackPane that we will fill or not considering the type of post
        // a dossiertxt for the text posts
        VBox dossiertxt= new VBox();
        dossiertxt.setSpacing(50);
        // a dossierimg for the image posts
        VBox dossierimg= new VBox();
        dossierimg.setSpacing(50);
        // a dossiervid for the video posts
        VBox dossiervid= new VBox();
        dossiervid.setSpacing(50);
        
        // we add theses posts in a bigger Vbox that we will shape.
        // for the videos
        VBox dossiervid2= new VBox();
        dossiervid2.setSpacing(75);
        // with a label to introduce the posts and their type.
        Label start3= new Label("Videos shared :");
        start3.setStyle("-fx-font-size: 50px; -fx-font-weight: bold; -fx-text-fill: black;");
        dossiervid2.getChildren().add(start3);
        dossiervid2.getChildren().add(dossiervid);
        dossiervid2.setTranslateY(800);
        dossiervid2.setTranslateX(1360);
        dossiervid2.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        // for the images
        VBox dossierimg2= new VBox();
        dossierimg2.setSpacing(75);
        // the label to introduce the posts and their type.
        Label start2= new Label("Pictures posted :");
            start2.setStyle("-fx-font-size: 50px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossierimg2.getChildren().add(start2);
        dossierimg2.getChildren().add(dossierimg);
        dossierimg2.setTranslateX(735);
        dossierimg2.setTranslateY(800);
        dossierimg2.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        // for the texts
        VBox dossiertxt2= new VBox();
        // the label to introduce the posts and their type.
        dossiertxt2.setSpacing(75);
        Label start= new Label("Text added : ");
            start.setStyle("-fx-font-size: 50px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossiertxt2.getChildren().add(start);
        dossiertxt2.getChildren().add(dossiertxt);
        dossiertxt2.setTranslateX(110);
        dossiertxt2.setTranslateY(800);
        dossiertxt2.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        
        // we initialize the stackPane that will contain everythg on the page and the differents VBOX
        StackPane content= new StackPane();

        // Now for each Post in the List of Post of the Current User we are going to display it in the right VBox.
        // LINES 139 TO 360 
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        for (Post post : SonMur) {
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //now we are going to treat the TEXT posts . TEXTE POSTS
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // if the post is a text post
        if (post instanceof Post_texte) {
            Post_texte postT = (Post_texte) post;
            // We add the Post in the dossiertxt with every information we need.
            // a separator to separate the posts
            Rectangle separator = new Rectangle();
            separator.setWidth(500); // Largeur du rectangle
            separator.setHeight(5); // Hauteur du rectangle
            separator.setFill(Color.BLACK); // Couleur de remplissage du rectangle
            dossiertxt.getChildren().add(separator);
            // the author of the post
            Label oteurT = new Label("author: "+postT.getAuthor().getnametag());
            oteurT.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossiertxt.getChildren().add(oteurT);
            // the date of the post
            Label DateT= new Label("posted the "+postT.getDate().toString());
            DateT.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossiertxt.getChildren().add(DateT);
            // the text of the post
            Label TexteT = new Label(postT.getTexte());
            TexteT.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossiertxt.getChildren().add(TexteT);
            // the like button and the number of likes
            HBox likebox = new HBox();
            likebox.setSpacing(100);
            Label LikeT = new Label("Liked "+Integer.toString(Post.getLikesForPost(postT.getidP()))+" times !");
            LikeT.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            likebox.getChildren().add(LikeT);
            dossiertxt.getChildren().add(likebox);
            // a delete Button to delete the post in case the User wants it to disappear.
            Button deleteT = new Button("Delete");
            deleteT.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            deleteT.setTranslateX(200);
            dossiertxt.getChildren().add(deleteT);
            deleteT.setOnAction(event->{
                // the event will remove the Post from the display
                SonMur.remove(postT);
                dossiertxt.getChildren().remove(separator);
                dossiertxt.getChildren().remove(oteurT);
                dossiertxt.getChildren().remove(DateT);
                dossiertxt.getChildren().remove(TexteT);
                dossiertxt.getChildren().remove(likebox);
                dossiertxt.getChildren().remove(deleteT);
                // and then remove it from the database with the corresponding method.
                try {
                    Post.deletePostBDD(postT.getidP());
                } catch (SQLException e) {
                    System.err.println("Error while deleting the post"+e.getMessage());
                } 
            });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //now we are going to treat the IMAGE posts the same way. IMAGE POSTS
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // if the post is an image post
        } else if (post instanceof Post_image){
            Post_image postI= (Post_image) post; // we cast the post in a post_image
            // we add the post in the dossierimg with every information we need.
            // a separator to separate the posts
            Rectangle separator = new Rectangle();
            separator.setWidth(500); // Largeur du rectangle
            separator.setHeight(5); // Hauteur du rectangle
            separator.setFill(Color.BLACK); // Couleur de remplissage du rectangle
            dossierimg.getChildren().add(separator);
            // the author of the post
            Label oteurI = new Label("author: "+postI.getAuthor().getnametag());
            oteurI.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossierimg.getChildren().add(oteurI);
            // the date of the post
            Label DateI = new Label("posted the "+postI.getDate().toString());
            DateI.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossierimg.getChildren().add(DateI);
            // the text of the post
            Label texteI = new Label(postI.gettexte());
            texteI.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossierimg.getChildren().add(texteI);
            // the image of the post in this case.
            Image picture = new Image(postI.getImageURL());
            ImageView Picture = new ImageView(picture);
            Picture.setPreserveRatio(true);
            Picture.setFitWidth(400);
            dossierimg.getChildren().add(Picture);
            // the like button and the number of likes
            HBox likebox2 = new HBox();
            likebox2.setSpacing(100);
            Label LikeI = new Label("liked "+Integer.toString(Post.getLikesForPost(postI.getidP()))+" times !");
            LikeI.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            likebox2.getChildren().add(LikeI);
            dossierimg.getChildren().add(likebox2);
            // same as before a delete button to delete the post.
            Button deleteI = new Button("Delete");
            deleteI.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            deleteI.setTranslateX(200);
            dossierimg.getChildren().add(deleteI);
            deleteI.setOnAction(event->{
                SonMur.remove(postI);
                dossierimg.getChildren().remove(separator);
                dossierimg.getChildren().remove(oteurI);
                dossierimg.getChildren().remove(DateI);
                dossierimg.getChildren().remove(texteI);
                dossierimg.getChildren().remove(Picture);
                dossierimg.getChildren().remove(likebox2);
                dossierimg.getChildren().remove(deleteI);
                try {
                    Post.deletePostBDD(postI.getidP());
                } catch (SQLException e) {
                    System.err.println("Error while deleting the post"+e.getMessage());
                }
            });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //now we are going to treat the VIDEO posts the same way. VIDEO POSTS
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
        // if the post is a video post
        } else if (post instanceof Post_video){
            Post_video postV = (Post_video) post;
            // we add the post in the dossiervid with every information we need.
            // a separator to separate the posts
            Rectangle separator = new Rectangle();
            separator.setWidth(500); // Largeur du rectangle
            separator.setHeight(5); // Hauteur du rectangle
            separator.setFill(Color.BLACK); // Couleur de remplissage du rectangle
            dossiervid.getChildren().add(separator);
            // the author of the post
            Label oteurV = new Label("author: "+postV.getAuthor().getnametag());
            oteurV.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossiervid.getChildren().add(oteurV);
            // the date of the post
            Label DateV = new Label("posted the "+postV.getDate().toString());
            DateV.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossiervid.getChildren().add(DateV);
            // the text of the post
            Label texteV = new Label(postV.gettexte());
            texteV.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossiervid.getChildren().add(texteV);
            // the video of the post (URL) in this case.
            Label video = new Label(postV.getVideoURL());
            video.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossiervid.getChildren().add(video);
            // the time of the video
            Label Time= new Label(postV.getTime());
            Time.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossiervid.getChildren().add(Time);
            // the like button and the number of likes
            HBox likebox3 = new HBox();
            likebox3.setSpacing(100);
            // the number of likes
            Label LikeV = new Label("liked "+Integer.toString(Post.getLikesForPost(postV.getidP()))+" times !");
            LikeV.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            likebox3.getChildren().add(LikeV);
            dossiervid.getChildren().add(likebox3);
            // a delete button to delete the post.
            Button deleteV = new Button("Delete");
            deleteV.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            deleteV.setTranslateX(200);
            dossiervid.getChildren().add(deleteV);
            deleteV.setOnAction(event->{
                SonMur.remove(postV);
                dossiervid.getChildren().remove(separator);
                dossiervid.getChildren().remove(oteurV);
                dossiervid.getChildren().remove(DateV);
                dossiervid.getChildren().remove(texteV);
                dossiervid.getChildren().remove(video);
                dossiervid.getChildren().remove(Time);
                dossiervid.getChildren().remove(likebox3);
                dossiervid.getChildren().remove(deleteV);
                try {
                    Post.deletePostBDD(postV.getidP());
                } catch (SQLException e) {
                    System.err.println("Error while deleting the post vid"+e.getMessage());
                }
            });
        }
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // END OF POSTS DISPLAY

        
        // now we are going to implement the possibility of creating a post. 
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /// CREATION OF A POST
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // we start by creating a choiceBox to choose the type of post we want to create.
        ChoiceBox<String> whichpost = new ChoiceBox<>();
        whichpost.getItems().addAll("Text", "Image", "Video");
        whichpost.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
        whichpost.setTranslateX(650);
        whichpost.setTranslateY(-3200);
        // each item in the choiceBox will create a different post.
        // we click on the button to initialize the creation
        Button createpost = new Button();
        createpost.setText("Create a post");
        createpost.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
        createpost.setTranslateY(-3200);
        createpost.setTranslateX(450);
        createpost.setOnAction(event->{
            // the action of the Post creation
            String choice = whichpost.getValue();
            // depends of the choice we made
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // CREATION OF TEXT POST
            if (choice.equals("Text")){
                // in the case we choosed text, we are going to create a new post_texte
                // we display the tools to create the post
                // a textfield to enter the text
                TextField TextfieldText = new TextField();
                TextfieldText.setPromptText("Enter your text");
                TextfieldText.setTranslateY(-3000);
                TextfieldText.setMaxWidth(600);
                content.getChildren().add(TextfieldText);
                // and a button to submit the post
                Button submit= new Button("Submit");
                submit.setTranslateY(-3000);
                submit.setTranslateX(600);
                submit.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                content.getChildren().add(submit);
                submit.setOnAction(event2->{
                    // the action of the submit button
                    if (!TextfieldText.getText().isEmpty()) {
                        // if the textfield is not empty 
                        // we add the separator
                        Rectangle separator = new Rectangle();
                        separator.setWidth(200); // Largeur du rectangle
                        separator.setHeight(5); // Hauteur du rectangle
                        separator.setFill(Color.BLACK); // Couleur de remplissage du rectangle
                        dossiertxt.getChildren().add(separator);
                        // we create a new date
                        Date datenow = new Date();
                        Instant instant = datenow.toInstant();
                        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
                        LocalDate localDate = zdt.toLocalDate();
                        // we create the post
                        Post_texte newpostT= new Post_texte(0,user, localDate, 0, Post.generateNewIdW(), TextfieldText.getText());
                        SonMur.add(newpostT);
                        // and we add it to the list of Posts of the current User
                        // we get rid of the unuseful elements
                        content.getChildren().remove(TextfieldText);
                        content.getChildren().remove(submit);
                        // we display the post in the dossiertxt
                        // the author of the post
                        Label newoteurT = new Label("author: "+newpostT.getAuthor().getnametag());
                        newoteurT.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                        dossiertxt.getChildren().add(newoteurT);
                        // the date of the post (today)
                        Label newDateT = new Label("posted the "+newpostT.getDate().toString());
                        newDateT.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                        dossiertxt.getChildren().add(newDateT);
                        // the text of the post
                        Label newTextT = new Label(newpostT.getTexte());
                        newTextT.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                        dossiertxt.getChildren().add(newTextT);
                        // the texte of the post
                        Label newtexteT = new Label(newpostT.gettexte());
                        newtexteT.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                        // the like button and the number of likes
                        HBox newlikeboxT = new HBox();
                        newlikeboxT.setSpacing(100);
                        int thislikecount;
                        try {
                            thislikecount=Post.getLikesForPost(newpostT.getidP());
                        } catch (SQLException e) {
                            thislikecount=0;
                        }
                        Label newLikeTlabel = new Label("Liked "+Integer.toString(thislikecount)+" times !");
                        newLikeTlabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                        newlikeboxT.getChildren().add(newLikeTlabel);

                        // after displaying it at first glance, we add it to the database. BDD
                        try (Connection connecting = DriverManager.getConnection(URLBDD)) {
                            // BDD opening and insertion of the post
                            String insertPostQuery = "INSERT INTO POSTS (texte,format,urlIMG,urlVID,duree, dateC, dateM, `#idU` ,`#idW`) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                            PreparedStatement statement = connecting.prepareStatement(insertPostQuery);
                            // we format the date and we state the different elements of the post into the database.
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
                            String formatteddate = newpostT.getDate().format(formatter);
                            statement.setString(1, newpostT.gettexte());
                            statement.setString(2, null);
                            statement.setString(3, null);
                            statement.setString(4, null);
                            statement.setString(5, null);
                            statement.setString(6, formatteddate);
                            statement.setString(7, null);
                            statement.setInt(8, user.getiDU());
                            statement.setInt(9, newpostT.getIdWall());
                            statement.executeUpdate();
                            statement.close();
                        } catch (SQLException e) {
                            System.err.println("Error while inserting the post"+e.getMessage());
                        }
                        // We are taking now the id of the post we just generate in the BDD auto completion to add it to the post.
                        try (Connection connecting = DriverManager.getConnection(URLBDD)) {
                            String selectPostQuery= "SELECT * FROM POSTS ORDER BY idP DESC LIMIT 1";
                            PreparedStatement statement = connecting.prepareStatement(selectPostQuery);
                            ResultSet PTresult = statement.executeQuery();
                            while (PTresult.next()) {
                                int idP = PTresult.getInt("idP");
                                newpostT.setidP(idP);
                            }
                        } catch (SQLException e) {
                            System.err.println("Error while selecting the post"+e.getMessage());
                        }
                        // the delete button to delete the post as usual.
                        Button deletenewT = new Button("Delete");
                        deletenewT.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                        deletenewT.setTranslateX(200);
                        dossiertxt.getChildren().add(deletenewT);
                        deletenewT.setOnAction(event3->{
                            SonMur.remove(newpostT);
                            dossiertxt.getChildren().remove(separator);
                            dossiertxt.getChildren().remove(newoteurT);
                            dossiertxt.getChildren().remove(newDateT);
                            dossiertxt.getChildren().remove(newtexteT);
                            dossiertxt.getChildren().remove(newTextT);
                            dossiertxt.getChildren().remove(newlikeboxT);
                            dossiertxt.getChildren().remove(deletenewT);
                            try {
                                Post.deletePostBDD(newpostT.getidP());
                            } catch (SQLException e) {
                                System.err.println("Error while deleting the post"+e.getMessage());
                            }
                        });
                    }
                    // if the textfield is empty we display an alert to tell the user to complete the post
                    // because whats the point of a post without text.
                    else {
                        Alert uncompleted1 = new Alert(AlertType.ERROR);
                        uncompleted1.setTitle("Error");
                        uncompleted1.setHeaderText("Uncompleted fields");
                        uncompleted1.setContentText("bro, please create your post, are you kidding me ?");
                        uncompleted1.showAndWait();
                    }
                });
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // CREATION OF IMAGE POST    
            } else if (choice.equals("Image")){
                // in the case we choosed image, we are going to create a new post_image
                // a possible addresse to try it out 
                // https://blog.allegromusique.fr/hs-fs/hubfs/2000px-Grand_staff.svg.png?width=260&name=2000px-Grand_staff.svg.png
                // we display the tools to create the post
                // a textfield to enter the image URL
                TextField Textfieldimage = new TextField();
                Textfieldimage.setPromptText("Enter your image URL");
                Textfieldimage.setTranslateY(-3000);
                Textfieldimage.setMaxWidth(600);
                content.getChildren().add(Textfieldimage);
                // a textfield to enter the text
                TextField textfieldcontent = new TextField();
                textfieldcontent.setPromptText("Enter your text");
                textfieldcontent.setTranslateY(-2900);
                textfieldcontent.setMaxWidth(600);
                content.getChildren().add(textfieldcontent);
                // a button to submit the post
                Button submit2= new Button("Submit");
                submit2.setTranslateY(-3000);
                submit2.setTranslateX(600);
                submit2.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                content.getChildren().add(submit2);
                submit2.setOnAction(event2->{
                    // the action of the submit button
                    if (!Textfieldimage.getText().isEmpty()) {
                        // if the textfield is not empty cause no need to make a image post without an image.
                        Rectangle separator = new Rectangle();
                        separator.setWidth(500); // Largeur du rectangle
                        separator.setHeight(5); // Hauteur du rectangle
                        separator.setFill(Color.BLACK); // Couleur de remplissage du rectangle
                        dossierimg.getChildren().add(separator);
                        // we create a new date
                        Date datenow2 = new Date();
                        Instant instant = datenow2.toInstant();
                        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
                        LocalDate localDate = zdt.toLocalDate();
                        // we create the post
                        Post_image newpostI= new Post_image(Post.generateNewIdP(),user, localDate, textfieldcontent.getText(), 0, Post.generateNewIdW(), Textfieldimage.getText());
                        SonMur.add(newpostI);
                        content.getChildren().remove(Textfieldimage);
                        content.getChildren().remove(textfieldcontent);
                        content.getChildren().remove(submit2);
                        // the author
                        Label newoteurI = new Label("author: "+newpostI.getAuthor().getnametag());
                        newoteurI.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                        dossierimg.getChildren().add(newoteurI);
                        // the date
                        Label newDateI = new Label("posted the "+newpostI.getDate().toString());
                        newDateI.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                        dossierimg.getChildren().add(newDateI);
                        // the image
                        Image imagenew= new Image(newpostI.getImageURL());
                        ImageView Picture = new ImageView(imagenew);
                        Picture.setPreserveRatio(true);
                        Picture.setFitWidth(400);
                        dossierimg.getChildren().add(Picture);
                        // the text if here
                        Label newtexteI = new Label(newpostI.gettexte());
                        newtexteI.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                        dossierimg.getChildren().add(newtexteI);
                        // the like button and the number of likes
                        HBox newlikeboxI = new HBox();
                        newlikeboxI.setSpacing(100);
                        int thislikecount;
                        try {
                            thislikecount=Post.getLikesForPost(newpostI.getidP());
                        } catch (SQLException e) {
                            thislikecount=0;
                        }
                        Label newLikeIlabel = new Label("Liked "+Integer.toString(thislikecount)+" times !");
                        newLikeIlabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                        newlikeboxI.getChildren().add(newLikeIlabel);                      
                    dossierimg.getChildren().add(newlikeboxI);
                    // the delete button to delete the post
                    Button deletenewI = new Button("Delete");
                    deletenewI.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                    deletenewI.setTranslateX(200);
                    dossierimg.getChildren().add(deletenewI);
                    deletenewI.setOnAction(event3->{
                            SonMur.remove(newpostI);
                            dossierimg.getChildren().remove(separator);
                            dossierimg.getChildren().remove(newoteurI);
                            dossierimg.getChildren().remove(newDateI);
                            dossierimg.getChildren().remove(newtexteI);
                            dossierimg.getChildren().remove(Picture);
                            dossierimg.getChildren().remove(newlikeboxI);
                            dossierimg.getChildren().remove(deletenewI);
                            try {
                                Post.deletePostBDD(newpostI.getidP());
                            } catch (SQLException e) {
                                System.err.println("Error while deleting the post"+e.getMessage());
                            }
                        });
                        // we add the post IMAGE to the database
                        try (Connection connecting = DriverManager.getConnection(URLBDD)) {
                            String insertPostQuery = "INSERT INTO POSTS (texte,format,urlIMG,urlVID,duree, dateC, dateM, `#idU` ,`#idW`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                            PreparedStatement statement = connecting.prepareStatement(insertPostQuery);
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
                            String formatteddate = newpostI.getDate().format(formatter);
                            statement.setString(1, newpostI.gettexte());
                            statement.setString(2, "jpeg");
                            statement.setString(3, newpostI.getImageURL());
                            statement.setString(4, null);
                            statement.setString(5, null);
                            statement.setString(6, formatteddate);
                            statement.setString(7, null);
                            statement.setInt(8, user.getiDU());
                            statement.setInt(9, newpostI.getIdWall());
                            statement.executeUpdate();
                            statement.close();
                        } catch (SQLException e) {
                            System.err.println("Error while inserting the post"+e.getMessage());
                        }
                        // we take the id of the post we just created to add it to the post.
                        try (Connection connecting = DriverManager.getConnection(URLBDD)) {
                            String selectPostQuery= "SELECT * FROM POSTS ORDER BY idP DESC LIMIT 1";
                            PreparedStatement statement = connecting.prepareStatement(selectPostQuery);
                            ResultSet PIresult = statement.executeQuery();
                            while (PIresult.next()) {
                                int idP = PIresult.getInt("idP");
                                newpostI.setidP(idP);
                            }
                        } catch (SQLException e) {
                            System.err.println("Error while reading the idP"+e.getMessage());
                        }
                    }
                    // if the textfield image is empty we display an alert to tell the user to complete the post
                    else {
                        Alert uncompleted2 = new Alert(AlertType.ERROR);
                        uncompleted2.setTitle("Error");
                        uncompleted2.setHeaderText("Uncompleted fields");
                        uncompleted2.setContentText("bro, please create your post, are you kidding me ?");
                        uncompleted2.showAndWait();
                    }
                });
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // CREATION OF VIDEO POST
            } else if (choice.equals("Video")){
                // there we are adding a so called "video post"
                ///////////////////////////https://www.youtube.com/watch?v=Q5j2vZC7b0A
                // im not going to explain again the cycle of the creation of the post
                TextField Textfieldvideo = new TextField();
                Textfieldvideo.setPromptText("Enter your image URL");
                Textfieldvideo.setTranslateY(-3000);
                Textfieldvideo.setMaxWidth(600);
                content.getChildren().add(Textfieldvideo);
                TextField Textfieldtime = new TextField();
                Textfieldtime.setPromptText("Enter the time of the video");
                Textfieldtime.setTranslateY(-2950);
                Textfieldtime.setMaxWidth(600);
                content.getChildren().add(Textfieldtime);
                TextField textfieldcontent = new TextField();
                textfieldcontent.setPromptText("Enter your text");
                textfieldcontent.setTranslateY(-2900);
                textfieldcontent.setMaxWidth(600);
                content.getChildren().add(textfieldcontent);
                Button submit3= new Button("Submit");
                submit3.setTranslateY(-3000);
                submit3.setTranslateX(600);
                submit3.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                content.getChildren().add(submit3);
                submit3.setOnAction(event2->{  
                    if (!Textfieldvideo.getText().isEmpty() && !textfieldcontent.getText().isEmpty()){
                        Rectangle separator = new Rectangle();
                        separator.setWidth(200); // Largeur du rectangle
                        separator.setHeight(5); // Hauteur du rectangle
                        separator.setFill(Color.BLACK); // Couleur de remplissage du rectangle
                        dossiervid.getChildren().add(separator);
                        Date datenow3 = new Date();
                        Instant instant = datenow3.toInstant();
                        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
                        LocalDate localDate = zdt.toLocalDate();
                        // we create the post
                        Post_video newpostV= new Post_video(Post.generateNewIdP(),user, localDate, textfieldcontent.getText(), 0, Post.generateNewIdW(), Textfieldvideo.getText(), Textfieldtime.getText());
                        SonMur.add(newpostV);
                        // we get rid of the unuseful elements
                        content.getChildren().remove(Textfieldvideo);
                        content.getChildren().remove(Textfieldtime);
                        content.getChildren().remove(textfieldcontent);
                        content.getChildren().remove(submit3);
                        // the author
                        Label newoteurV = new Label("author: "+newpostV.getAuthor().getnametag());
                        newoteurV.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                        dossiervid.getChildren().add(newoteurV);
                        // the date
                        Label newDateV = new Label("posted the "+newpostV.getDate().toString());
                        newDateV.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                        dossiervid.getChildren().add(newDateV);
                        // the video URL
                        Label newvideo = new Label(newpostV.getVideoURL());
                        newvideo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                        dossiervid.getChildren().add(newvideo);
                        // the text in case there is one
                        Label newtexteV = new Label(newpostV.gettexte());
                        newtexteV.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                        dossiervid.getChildren().add(newtexteV);
                        // tbe time of the video
                        Label newTimeV= new Label(newpostV.getTime());
                        newTimeV.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                        dossiervid.getChildren().add(newTimeV);
                        // the like button and the number of likes
                        HBox newlikeboxV = new HBox();
                        newlikeboxV.setSpacing(100);
                        int thislikecount;
                        try {
                            thislikecount=Post.getLikesForPost(newpostV.getidP());
                        } catch (SQLException e) {
                            thislikecount=0;
                        }
                        Label newLikeVlabel = new Label("Liked "+Integer.toString(thislikecount)+" times !");
                        newLikeVlabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                        newlikeboxV.getChildren().add(newLikeVlabel);
                        dossiervid.getChildren().add(newlikeboxV);
                        // the delete button to delete the post
                        Button deletenewV = new Button("Delete");
                        deletenewV.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                        deletenewV.setTranslateX(200);
                        dossiervid.getChildren().add(deletenewV);
                        deletenewV.setOnAction(event3->{
                            SonMur.remove(newpostV);
                            dossiervid.getChildren().remove(separator);
                            dossiervid.getChildren().remove(newoteurV);
                            dossiervid.getChildren().remove(newDateV);
                            dossiervid.getChildren().remove(newtexteV);
                            dossiervid.getChildren().remove(newvideo);
                            dossiervid.getChildren().remove(newTimeV);
                            dossiervid.getChildren().remove(newlikeboxV);
                            dossiervid.getChildren().remove(deletenewV);
                            try {
                                Post.deletePostBDD(newpostV.getidP());
                            } catch (SQLException e) {
                                System.err.println("Error while deleting the post"+e.getMessage());
                            }
                        });
                        // we add the post to the database
                        try (Connection connecting = DriverManager.getConnection(URLBDD)) {
                            String insertPostQuery = "INSERT INTO POSTS (texte,format,urlIMG,urlVID,duree, dateC, dateM, `#idU` ,`#idW`) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                            PreparedStatement statement = connecting.prepareStatement(insertPostQuery);
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
                            String formatteddate = newpostV.getDate().format(formatter);
                            statement.setString(1, newpostV.gettexte());
                            statement.setString(2, "youtube");
                            statement.setString(3, null);
                            statement.setString(4, newpostV.getVideoURL());
                            statement.setString(5, newpostV.getTime());
                            statement.setString(6, formatteddate);
                            statement.setString(7, null);
                            statement.setInt(8, user.getiDU());
                            statement.setInt(9, newpostV.getIdWall());
                            statement.executeUpdate();
                            statement.close();
                        } catch (SQLException e) {
                            System.err.println("Error while inserting the postV"+e.getMessage());
                        }
                        // we take the id of the post we just created to add it to the post.
                        try (Connection connecting = DriverManager.getConnection(URLBDD)) {
                            String selectPostQuery= "SELECT * FROM POSTS ORDER BY idP DESC LIMIT 1";
                            PreparedStatement statement = connecting.prepareStatement(selectPostQuery);
                            ResultSet PVresult = statement.executeQuery();
                            while (PVresult.next()) {
                                int idP = PVresult.getInt("idP");
                                newpostV.setidP(idP);
                            }
                        } catch (SQLException e) {
                            System.err.println("Error while reading the idP of V"+e.getMessage());
                        }
                    }
                    // in case the textfield is empty we display an alert to tell the user to complete the post
                    else {
                        Alert uncompleted3 = new Alert(AlertType.ERROR);
                        uncompleted3.setTitle("Error");
                        uncompleted3.setHeaderText("Uncompleted fields");
                        uncompleted3.setContentText("bro, please create your post, are you kidding me ?");
                        uncompleted3.showAndWait();
                    }
                });
            }
        });
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// END OF CREATION CYCLE
        
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // now we display other boxes that can be useful to the user
        // a label to redirect to the users page
        Label Usersshow = new Label("Show Users : ");
        Usersshow.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: black;");
        Usersshow.setTranslateY(-3200);
        Usersshow.setTranslateX(-400);
        
        // this choiceBox will display every user pages
        ChoiceBox<String> UsersBox = new ChoiceBox<>();
        List<Users> AllUsers = new ArrayList<>(); // we create a list of all the users
        Users.usercompletecreation(AllUsers); // we use the method to complete the list with every user in the database
        for (Users u : AllUsers) { // for each one we add them to the choiceBox
            UsersBox.getItems().add(u.getnametag()); // by their name
        }
        UsersBox.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
        UsersBox.setOnAction(event->{ // when we click on one of the users
            String Userchoosed = UsersBox.getValue();  // we stock the user choosed
            for (Users u: AllUsers) { // we look for the user in the list
                if (Userchoosed.equals(u.getnametag())) { // if we find it
                    selectedUser = u; 
                    Wall userconWall = new Wall(); // we create a new wall
                    Scene newScene = userconWall.createScene(u); // we create the scene of the wall
                    Stage wallStage = new Stage();
                    wallStage.setFullScreen(true);
                    wallStage.setScene(newScene);
                    wallStage.show();
                    // we show the wall
                }
            }
        });
        UsersBox.setTranslateY(-3200);
        UsersBox.setTranslateX(100);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        // a label to redirect to the users who got a wall
        Label Usersshowconwall = new Label("Show Users who got a wall : ");
        Usersshowconwall.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: black;");
        Usersshowconwall.setTranslateY(-3100);
        Usersshowconwall.setTranslateX(-400);
        
        // this choiceBox will display every user who got a wall
        ChoiceBox<String> UsersconWallBox = new ChoiceBox<>(); // we create the choiceBox
        List<Users> UsersconWall = new ArrayList<>(); // we create a new list of users
        Users.userCompleteCreationconWall(UsersconWall); // we complete the list with the users who got a wall by using the proper method.
        for (Users u : UsersconWall) { // for each user we add them to the choiceBox
            UsersconWallBox.getItems().add(u.getnametag()); // by their name
        }
        UsersconWallBox.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
        UsersconWallBox.setOnAction(event->{
            String Userchoosed = UsersconWallBox.getValue();
            for (Users u: UsersconWall) {
                if (Userchoosed.equals(u.getnametag())) {
                    selectedUser = u;
                    Wallteacher userconWall = new Wallteacher(); // we create a new wallteacher
                    Scene newScene = null; // Initialize newScene variable
                    try {
                        newScene = userconWall.createScene(u);
                    } catch (SQLException e) {
                        System.err.println("Error while creating the wallteacher"+e.getMessage());
                    }
                    Stage wallStage2 = new Stage();
                    wallStage2.setFullScreen(true);
                    wallStage2.setScene(newScene);
                    wallStage2.show();
                    // we display the new stage
                }
            }
        });
        UsersconWallBox.setTranslateY(-3100);
        UsersconWallBox.setTranslateX(100);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        // this label is the Big introduction of the page.
        Label wall = new Label("welcome into your home page !\n               "+user.getnametag());
        wall.setStyle("-fx-font-size: 80px; -fx-font-weight: bold; -fx-text-fill: black;");
        wall.setTranslateY(-3400);
        
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        content.getChildren().addAll(imagebox, wall, dossiertxt2, dossierimg2, dossiervid2, whichpost,Usersshow, Usersshowconwall,UsersBox,UsersconWallBox, createpost);
        // we add the content to the scrollin pane that will take care of the entire Scene.
        ScrollPane scrolling = new ScrollPane(content);
        scrolling.setPannable(true);
        
        // we add the scrolling pane to the root3
        root3.getChildren().add(scrolling);
        Scene scene = new Scene(root3);
        // we return the scene
        return scene;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // this little method is used to get the current user on others scenes
    public static Users getcurrentUser(){
        return currentUser;
    }
}