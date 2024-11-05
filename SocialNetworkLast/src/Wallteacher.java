// various imports of the SceneBuilder elements

import javafx.beans.property.BooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.ComboBox;
import javafx.scene.shape.Rectangle;
// imports of javafx

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
// imports of the classical java

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
// imports used in sql

// this Class is basically the wall of user
// but only if this user has a wall, ( so MM and PF)
// we are going to show here the posts of the user of his wall, AND the comments related to it


public class Wallteacher extends StackPane{
    public Scene createScene(Users userW) throws SQLException {
        if (Users.isblockedBDD(Scene3.currentUser.getiDU(), Users.getWallIdForUser(userW.getiDU()))){
            StackPane blockedRoot = new StackPane();
            Label blockedMessage = new Label("You have been blocked by this user.");
            blockedMessage.setStyle("-fx-font-size: 50px; -fx-font-weight: bold; -fx-text-fill: black;");
            blockedMessage.setTranslateY(500);
            Image cheh = new Image("file:///home/simon/PNGS/sheh.png");
            ImageView Cheh = new ImageView(cheh);
            blockedRoot.getChildren().addAll(blockedMessage, Cheh);
            return new Scene(blockedRoot, 400, 400);
        }
        else{

        // usual variables initialization
        int useridw = userW.getiDU();
        List<Post> Postofhim = new ArrayList<>();
        Postofhim = Post.getPostsFromUserWall(useridw);
        userW.setPostlist(Postofhim);
        List<Post> walling = userW.getPostlist();
        
        StackPane wallroot = new StackPane();
        // same setup as usual.
        Image floral = new Image("file:SocialNetworkLast/PNGS/png.png");
        VBox imagebox = new VBox();
            for (int i =0; i< 5; i++) {
                ImageView Floral = new ImageView(floral);
                Floral.setFitWidth(1900);
                //Floral.setFitHeight(1080);
                Floral.setPreserveRatio(true);
                imagebox.getChildren().add(Floral);
            }
        imagebox.setSpacing(-5);
        //
        //CLASSICAL SETUP OF THE SCENE (VBOX, SCROLLPANE, SCENE)
        // introduction of different stackPane that we will fill or not considering the type of post
        VBox dossiertxt= new VBox();
        dossiertxt.setSpacing(50);
        VBox dossierimg= new VBox();
        dossierimg.setSpacing(50);
        VBox dossiervid= new VBox();
        dossiervid.setSpacing(50);
        VBox dossiervid2= new VBox();
        dossiervid2.setSpacing(30);
        Label start3= new Label("Videos shared :");
        start3.setStyle("-fx-font-size: 50px; -fx-font-weight: bold; -fx-text-fill: black;");
        dossiervid2.getChildren().add(start3);
        dossiervid2.getChildren().add(dossiervid);
        dossiervid2.setTranslateY(800);
        dossiervid2.setTranslateX(1360);
        dossiervid2.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        VBox dossierimg2= new VBox();
        dossierimg2.setSpacing(30);
        Label start2= new Label("Pictures posted :");
            start2.setStyle("-fx-font-size: 50px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossierimg2.getChildren().add(start2);
        dossierimg2.getChildren().add(dossierimg);
        dossierimg2.setTranslateX(735);
        dossierimg2.setTranslateY(800);
        dossierimg2.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        VBox dossiertxt2= new VBox();
        dossiertxt2.setSpacing(30);
        Label start= new Label("Text added : ");
            start.setStyle("-fx-font-size: 50px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossiertxt2.getChildren().add(start);
        dossiertxt2.getChildren().add(dossiertxt);
        dossiertxt2.setTranslateX(110);
        dossiertxt2.setTranslateY(800);
        dossiertxt2.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        

        //
        StackPane content= new StackPane();
        
        // two new variables, a list of comments for each post, and a map of post and their containers
        Map<Post, List<Comments>> commentsForPost = new HashMap<>();
        Map<Post, VBox> postContainers = new HashMap<>();
        
        // this Class is basically a cycle of 3 loops (and another one for the comments) that i will separate with blocks of 5:
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        for (Post post : walling) {
            // the post container will contain a post and its comments
            VBox postContainer = new VBox();
            postContainer.setSpacing(50);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //FIRST BLOCK : TEXT POSTS
        if (post instanceof Post_texte && !(post instanceof Comments)) {
            // CLASSICAL SETUP OF THE TEXT POST
            Post_texte postT = (Post_texte) post;
            Rectangle separator = new Rectangle();
                        separator.setWidth(500); // Largeur du rectangle
                        separator.setHeight(5); // Hauteur du rectangle
                        separator.setFill(Color.BLACK); // Couleur de remplissage du rectangle
                        postContainer.getChildren().add(separator);
            Label oteurT = new Label("author: "+postT.getAuthor().getnametag());
            oteurT.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            postContainer.getChildren().add(oteurT);
            Label DateT= new Label("posted the "+postT.getDate().toString());
            DateT.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            postContainer.getChildren().add(DateT);
            Label TexteT = new Label(postT.getTexte());
            TexteT.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            postContainer.getChildren().add(TexteT);
            HBox likebox = new HBox();
            likebox.setSpacing(100);
            int thislikecount;
                try {
                    thislikecount=Post.getLikesForPost(postT.getidP());
                } catch (SQLException e) {
                    thislikecount=0;
                }
            Label LikeT = new Label("Liked "+Integer.toString(thislikecount)+" times !");
            LikeT.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            likebox.getChildren().add(LikeT);
            ToggleButton like = new ToggleButton();
            like.setText("Like");
            like.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            likebox.getChildren().add(like);
            postContainer.getChildren().add(likebox);
            BooleanProperty selection = like.selectedProperty();
            selection.addListener((observable, ancientV, newV) -> {
                if (newV) {
                    try {
                        Post.likePost(postT.getidP(), Scene3.currentUser.getiDU());
                    } catch (SQLException e) {
                        System.err.println("Error while liking the post"+e.getMessage());
                    }
                } else {
                    try {
                        Post.unlikePost(postT.getidP(), Scene3.currentUser.getiDU());
                    } catch (SQLException e) {
                        System.err.println("Error while liking the post"+e.getMessage());
                    }
                }
                try {
                    LikeT.setText("Liked "+Integer.toString(Post.getLikesForPost(postT.getidP()))+ " times !");
                } catch (SQLException e) {
                    System.err.println("Error in likes");
                }
            });
            postContainers.put(post, postContainer);
            dossiertxt.getChildren().add(postContainer);
            // insertion of a comment button inside the postContainer to allow the user to comment on the post text
            Button comment = new Button();
            comment.setText("Comment");
            comment.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            postContainer.getChildren().add(comment);
            comment.setOnAction(event-> {
                // when the comment button is pressed
                // we add a textfield to write the comment
                TextField  commentField = new TextField();
                commentField.setPromptText("Enter your comment");
                commentField.setMaxWidth(200);
                postContainer.getChildren().add(commentField);
                // we add a submit button to submit the comment
                Button submitC1 = new Button();
                submitC1.setText("Submit");
                submitC1.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                submitC1.setMaxWidth(100);
                postContainer.getChildren().add(submitC1);
                // when the submit button is pressed
                submitC1.setOnAction(event2 -> {
                    // inscription du commentaire dans la bdd, dans POSTS et dans COMMENTS
                    String URLBDD = Users.URLBDD; // URL of the database
                    try (Connection connecting = DriverManager.getConnection(URLBDD)) {
                        //idP se fait tt seul
                        // we insert the comment in the database TABLE POSTS
                        String insertPostQuery = "INSERT INTO POSTS (texte,format,urlIMG,urlVID,duree, dateC, dateM, `#idU` ,`#idW`) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement statement = connecting.prepareStatement(insertPostQuery);
                        LocalDate localDate = LocalDate.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
                        String formatteddate = localDate.format(formatter);
                        statement.setString(1, commentField.getText());
                        statement.setString(2, null);
                        statement.setString(3, null);
                        statement.setString(4, null);
                        statement.setString(5, null);
                        statement.setString(6, formatteddate);
                        statement.setString(7, null);
                        statement.setInt(8, Scene3.currentUser.getiDU());
                        statement.setInt(9, Users.getWallIdForUser(userW.getiDU()));
                        try {
                            statement.executeUpdate();
                        } catch (Exception e) {
                            System.err.println("Error in the insertion of the comment in the database");
                        }
                        postContainer.getChildren().remove(commentField);
                        postContainer.getChildren().remove(submitC1);
                    } catch (SQLException e) {
                        System.err.println("Error in the connection to the database");
                    }
                    ///////////////////////////////////////////////////////////////////////////////////////////////
                    // now we need to recuperate the idP of the comment we just inserted
                    try (Connection connecting = DriverManager.getConnection(URLBDD)) {
                        String selectPostQuery= "SELECT * FROM POSTS ORDER BY idP DESC LIMIT 1";
                        PreparedStatement statement = connecting.prepareStatement(selectPostQuery);
                        ResultSet comresult = statement.executeQuery();
                        while (comresult.next()) {
                            int idP = comresult.getInt("idP");
                            String texte = comresult.getString("texte");
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
                            String dateCStr = comresult.getString("dateC");
                            LocalDate dateC = LocalDate.parse(dateCStr, formatter);
                            int idU = comresult.getInt("#idU");
                            int idW = comresult.getInt("#idW");
                            Users author = Users.getUserByIdBDD(idU);

                            // and we create the comment in intern with everyinfo we have.
                            Comments newCommentX = new Comments(idP, author, dateC ,0 , idW, texte ,postT , postT.getidP());
                            // and we add it in the database but this time in the TABLE COMMENTS
                            /////////////////////////////////////////////////////////////////////
                            try (Connection connector = DriverManager.getConnection(URLBDD)) {
                                // idc se fait tt seul
                                String insertComment2Query = "INSERT INTO COMMENTS ( `#idP_Init`, `#idP_Rep`) VALUES ( ?, ?)";
                                PreparedStatement statementbis = connecting.prepareStatement(insertComment2Query);
                                
                                Rectangle separatorX = new Rectangle();
                                    separatorX.setWidth(100); // Largeur du rectangle
                                    separatorX.setHeight(5); // Hauteur du rectangle
                                    separatorX.setFill(Color.BLUE); // Couleur de remplissage du rectangle
                                    postContainer.getChildren().add(separatorX);
                                // we add the comment in the postContainer
                                Label oteurC = new Label("author: "+newCommentX.getAuthor().getnametag());
                                oteurC.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                                postContainer.getChildren().add(oteurC);
                                
                                Label DateC = new Label("posted the "+newCommentX.getDate().toString());
                                DateC.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                                postContainer.getChildren().add(DateC);
            
                                Label TexteC = new Label(newCommentX.getTexte());
                                TexteC.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                                postContainer.getChildren().add(TexteC);
            
                                HBox likebox4 = new HBox();
                                likebox4.setSpacing(100);
            
                                Label LikeC = new Label("liked "+ 0 +" times !");
                                LikeC.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                                likebox4.getChildren().add(LikeC);
            
                                ToggleButton like4 = new ToggleButton();
                                like4.setText("Like");
                                like4.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                                likebox4.getChildren().add(like4);
            
                                postContainer.getChildren().add(likebox4);
            
                                BooleanProperty selection4 = like4.selectedProperty();
                                selection4.addListener((observable, ancientV, newV) -> {
                                    if (newV) {
                                        try {
                                            Post.likePost(newCommentX.getidP(), Scene3.currentUser.getiDU());
                                        } catch (SQLException e) {
                                            System.err.println("Error while liking the post"+e.getMessage());
                                        }
                                    } else {
                                        try {
                                            Post.unlikePost(newCommentX.getidP(), Scene3.currentUser.getiDU());
                                        } catch (SQLException e) {
                                            System.err.println("Error while liking the post"+e.getMessage());
                                        }
                                    }
                                    LikeC.setText("Liked "+Integer.toString(newCommentX.getLike())+ " times !");
                                });
                                
                                statementbis.setInt(1, postT.getidP());
                                statementbis.setInt(2, newCommentX.getidP() );
        
                                try {
                                    statementbis.executeUpdate();
                                } catch (Exception e) {
                                    System.err.println("Error in the insertion of the comment in the database");
                                }
                            } catch (SQLException e) {
                                System.err.println("Error in the connection to the database");
                            }
                        }
                    } catch (SQLException e) {
                        System.err.println("Error in the connection to the database");
                    }
                });
            });
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //SECOND BLOCK : IMAGES POSTS
        } else if (post instanceof Post_image){
            // SAME THING WITH THE IMAGE POST
            Post_image postI= (Post_image) post;
            Rectangle separator = new Rectangle();
                        separator.setWidth(500); // Largeur du rectangle
                        separator.setHeight(5); // Hauteur du rectangle
                        separator.setFill(Color.BLACK); // Couleur de remplissage du rectangle
                        dossierimg.getChildren().add(separator);
            Label oteurI = new Label("author: "+postI.getAuthor().getnametag());
            oteurI.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            postContainer.getChildren().add(oteurI);
            Label DateI = new Label("posted the "+postI.getDate().toString());
            DateI.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            postContainer.getChildren().add(DateI);
            Label texteI = new Label(postI.gettexte());
            texteI.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            postContainer.getChildren().add(texteI);
            Image picture = new Image(postI.getImageURL());
            ImageView Picture = new ImageView(picture);
            Picture.setPreserveRatio(true);
            Picture.setFitWidth(400);
            postContainer.getChildren().add(Picture);
            HBox likebox2 = new HBox();
            likebox2.setSpacing(100);
            int thislikecount;
                try {
                    thislikecount=Post.getLikesForPost(postI.getidP());
                } catch (SQLException e) {
                    thislikecount=0;
                }
            Label LikeI = new Label("liked "+Integer.toString(thislikecount)+" times !");
            LikeI.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            likebox2.getChildren().add(LikeI);
            LikeI.setTranslateY(45);
            ToggleButton like2 = new ToggleButton();
            like2.setText("Like");
            like2.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            likebox2.getChildren().add(like2);
            postContainer.getChildren().add(likebox2);
            BooleanProperty selection2 = like2.selectedProperty();
            selection2.addListener((observable, ancientV, newV) -> {
                if (newV) {
                    try {
                        Post.likePost(postI.getidP(), Scene3.currentUser.getiDU());
                    } catch (SQLException e) {
                        System.err.println("Error while liking the post"+e.getMessage());
                    }
                } else {
                    try {
                        Post.unlikePost(postI.getidP(), Scene3.currentUser.getiDU());
                    } catch (SQLException e) {
                        System.err.println("Error while liking the post"+e.getMessage());
                    }
                }
                try {
                    LikeI.setText("Liked "+Integer.toString(Post.getLikesForPost(postI.getidP()))+ " times !");
                } catch (SQLException e) {
                    System.err.println("Error in likes");
                }
            });
            postContainers.put(post, postContainer);
            dossierimg.getChildren().add(postContainer);
            // insertion of a comment button inside the postContainer to allow the user to comment on the post image
            Button comment = new Button();
            comment.setText("Comment");
            comment.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            postContainer.getChildren().add(comment);
            comment.setOnAction(event-> {
                TextField  commentField = new TextField();
                commentField.setPromptText("Enter your comment");
                commentField.setMaxWidth(200);
                postContainer.getChildren().add(commentField);
                Button submitC1 = new Button();
                submitC1.setText("Submit");
                submitC1.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                submitC1.setMaxWidth(100);
                postContainer.getChildren().add(submitC1);
                submitC1.setOnAction(event2 -> {
                    // inscription du commentaire dans la bdd, dans POSTS et dans COMMENTS
                    String URLBDD = Users.URLBDD;
                    try (Connection connecting = DriverManager.getConnection(URLBDD)) {
                        //idP se fait tt seul
                        String insertPostQuery = "INSERT INTO POSTS (texte,format,urlIMG,urlVID,duree, dateC, dateM, `#idU` ,`#idW`) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement statement = connecting.prepareStatement(insertPostQuery);
                        LocalDate localDate = LocalDate.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
                        String formatteddate = localDate.format(formatter);
                        statement.setString(1, commentField.getText());
                        statement.setString(2, null);
                        statement.setString(3, null);
                        statement.setString(4, null);
                        statement.setString(5, null);
                        statement.setString(6, formatteddate);

                        statement.setString(7, null);
                        statement.setInt(8, Scene3.currentUser.getiDU());
                        statement.setInt(9, Users.getWallIdForUser(userW.getiDU()));
                        try {
                            statement.executeUpdate();
                            statement.close();
                        } catch (Exception e) {
                            System.err.println("Error in the insertion of the comment in the database");
                        }
                        postContainer.getChildren().remove(commentField);
                        postContainer.getChildren().remove(submitC1);
                    } catch (SQLException e) {
                        System.err.println("Error in the connection to the database");
                    }
                    // now we need to recuperate the idP of the comment we just inserted
                    try (Connection connecting = DriverManager.getConnection(URLBDD)) {
                        String selectPostQuery= "SELECT * FROM POSTS ORDER BY idP DESC LIMIT 1";
                        PreparedStatement statement = connecting.prepareStatement(selectPostQuery);
                        ResultSet comresult = statement.executeQuery();
                        while (comresult.next()) {
                            int idP = comresult.getInt("idP");
                            String texte = comresult.getString("texte");
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
                            String dateCStr = comresult.getString("dateC");
                            LocalDate dateC = LocalDate.parse(dateCStr, formatter);
                            int idU = comresult.getInt("#idU");
                            int idW = comresult.getInt("#idW");
                            Users author = Users.getUserByIdBDD(idU);
                            // we finally create the comment with all the informations we have
                            Comments newCommentX = new Comments(idP, author, dateC ,0 , idW, texte ,postI , postI.getidP());
                            // and we add it in the table Comments of the database
                            try (Connection connector = DriverManager.getConnection(URLBDD)) {
                                // idc se fait tt seul
                                String insertComment2Query = "INSERT INTO COMMENTS ( `#idP_Init`, `#idP_Rep`) VALUES ( ?, ?)";
                                // and we display it in the postContainer
                                PreparedStatement statementbis = connecting.prepareStatement(insertComment2Query);
                                Rectangle separatorX = new Rectangle();
                                    separatorX.setWidth(100); // Largeur du rectangle
                                    separatorX.setHeight(5); // Hauteur du rectangle
                                    separatorX.setFill(Color.BLUE); // Couleur de remplissage du rectangle
                                    postContainer.getChildren().add(separatorX);
                                Label oteurC = new Label("author: "+newCommentX.getAuthor().getnametag());
                                oteurC.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                                postContainer.getChildren().add(oteurC);
                                Label DateC = new Label("posted the "+newCommentX.getDate().toString());
                                DateC.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                                postContainer.getChildren().add(DateC);
                                Label TexteC = new Label(newCommentX.getTexte());
                                TexteC.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                                postContainer.getChildren().add(TexteC);
                                HBox likebox4 = new HBox();
                                likebox4.setSpacing(100);
                                Label LikeC = new Label("liked "+ 0 +" times !");
                                LikeC.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                                likebox4.getChildren().add(LikeC);
                                ToggleButton like4 = new ToggleButton();
                                like4.setText("Like");
                                like4.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                                likebox4.getChildren().add(like4);
                                postContainer.getChildren().add(likebox4);
                                BooleanProperty selection4 = like4.selectedProperty();
                                selection4.addListener((observable, ancientV, newV) -> {
                                    if (newV) {
                                        try {
                                            Post.likePost(newCommentX.getidP(), Scene3.currentUser.getiDU());
                                        } catch (SQLException e) {
                                            System.err.println("Error while liking the post"+e.getMessage());
                                        }
                                    } else {
                                        try {
                                            Post.unlikePost(newCommentX.getidP(), Scene3.currentUser.getiDU());
                                        } catch (SQLException e) {
                                            System.err.println("Error while liking the post"+e.getMessage());
                                        }
                                    }
                                    LikeC.setText("Liked "+Integer.toString(newCommentX.getLike())+ " times !");
                                });
                                statementbis.setInt(1, postI.getidP());
                                statementbis.setInt(2, newCommentX.getidP() );
                                try {
                                    statementbis.executeUpdate();
                                } catch (Exception e) {
                                    System.err.println("Error in the insertion of the comment in the database");
                                }
                            } catch (SQLException e) {
                                System.err.println("Error in the connection to the database");
                            }
                        }
                    } catch (SQLException e) {
                        System.err.println("Error in the connection to the database");
                    }
                });
            });
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //THIRD BLOCK : VIDEO POSTS   
        } else if (post instanceof Post_video){
            Post_video postV = (Post_video) post;
            Rectangle separator = new Rectangle();
                        separator.setWidth(500); // Largeur du rectangle
                        separator.setHeight(5); // Hauteur du rectangle
                        separator.setFill(Color.BLACK); // Couleur de remplissage du rectangle
                        dossiervid.getChildren().add(separator);
            Label oteurV = new Label("author: "+postV.getAuthor().getnametag());
            oteurV.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            postContainer.getChildren().add(oteurV);
            Label DateV = new Label("posted the "+postV.getDate().toString());
            DateV.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            postContainer.getChildren().add(DateV);
            Label texteV = new Label(postV.gettexte());
            texteV.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            postContainer.getChildren().add(texteV);
            Label video = new Label(postV.getVideoURL());
            video.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            postContainer.getChildren().add(video);
            Label Time= new Label(postV.getTime());
            Time.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            postContainer.getChildren().add(Time);
            HBox likebox3 = new HBox();
            likebox3.setSpacing(100);
            likebox3.setTranslateY(75);
            int thislikecount;
                try {
                    thislikecount=Post.getLikesForPost(postV.getidP());
                } catch (SQLException e) {
                    thislikecount=0;
                }
            Label LikeV = new Label("liked "+Integer.toString(thislikecount)+" times !");
            LikeV.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            likebox3.getChildren().add(LikeV);
            ToggleButton like3 = new ToggleButton();
            like3.setText("Like");
            like3.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            likebox3.getChildren().add(like3);
            postContainer.getChildren().add(likebox3);
            BooleanProperty selection3 = like3.selectedProperty();
            selection3.addListener((observable, ancientV, newV) -> {
                if (newV) {
                    try {
                        Post.likePost(postV.getidP(), Scene3.currentUser.getiDU());
                    } catch (SQLException e) {
                        System.err.println("Error while liking the post"+e.getMessage());
                    }
                } else {
                    try {
                        Post.unlikePost(postV.getidP(), Scene3.currentUser.getiDU());
                    } catch (SQLException e) {
                        System.err.println("Error while liking the post"+e.getMessage());
                    }
                }
                try {
                    LikeV.setText("Liked "+Integer.toString(Post.getLikesForPost(postV.getidP()))+ " times !");
                } catch (SQLException e) {
                    System.err.println("Error in likes");
                }
            });
            dossiervid.getChildren().add(postContainer);
            postContainers.put(post, postContainer);
            // boutton pour commenter sur le V et tt ce qui s'en suit
            Button comment = new Button();
            comment.setText("Comment");
            comment.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            postContainer.getChildren().add(comment);
            comment.setOnAction(event-> {
                TextField  commentField = new TextField();
                commentField.setPromptText("Enter your comment");
                commentField.setMaxWidth(200);
                postContainer.getChildren().add(commentField);
                Button submitC1 = new Button();
                submitC1.setText("Submit");
                submitC1.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                submitC1.setMaxWidth(100);
                postContainer.getChildren().add(submitC1);
                submitC1.setOnAction(event2 -> {
                    // inscription du commentaire dans la bdd, dans POSTS et dans COMMENTS
                    String URLBDD = Users.URLBDD;
                    try (Connection connecting = DriverManager.getConnection(URLBDD)) {
                        //idP se fait tt seul
                        // insertion dans TABLE POSTS
                        String insertPostQuery = "INSERT INTO POSTS (texte,format,urlIMG,urlVID,duree, dateC, dateM, `#idU` ,`#idW`) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement statement = connecting.prepareStatement(insertPostQuery);
                        LocalDate localDate = LocalDate.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
                        String formatteddate = localDate.format(formatter);
                        statement.setString(1, commentField.getText());
                        statement.setString(2, null);
                        statement.setString(3, null);
                        statement.setString(4, null);
                        statement.setString(5, null);
                        statement.setString(6, formatteddate);
                        statement.setString(7, null);
                        statement.setInt(8, Scene3.currentUser.getiDU());
                        statement.setInt(9, Users.getWallIdForUser(userW.getiDU()));
                        try {
                            statement.executeUpdate();
                        } catch (Exception e) {
                            System.err.println("Error in the insertion of the comment in the database");
                        }
                        postContainer.getChildren().remove(commentField);
                        postContainer.getChildren().remove(submitC1);
                    } catch (SQLException e) {
                        System.err.println( "Error in the connection to the database");
                    }
                    // now we need to recuperate the idP of the comment we just inserted
                    try (Connection connecting = DriverManager.getConnection(URLBDD)) {
                        String selectPostQuery= "SELECT * FROM POSTS ORDER BY idP DESC LIMIT 1";
                        PreparedStatement statement = connecting.prepareStatement(selectPostQuery);
                        ResultSet comresult = statement.executeQuery();
                        while (comresult.next()) {
                            int idP = comresult.getInt("idP");
                            String texte = comresult.getString("texte");
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
                            String dateCStr = comresult.getString("dateC");
                            LocalDate dateC = LocalDate.parse(dateCStr, formatter);
                            int idU = comresult.getInt("#idU");
                            int idW = comresult.getInt("#idW");
                            Users author = Users.getUserByIdBDD(idU);
                            // and we create the comment in intern with everyinfo we have.
                            Comments newCommentX = new Comments(idP, author, dateC ,0 , idW, texte ,postV , postV.getidP());
                            // and we add it in the database but this time in the TABLE COMMENTS
                            try (Connection connector = DriverManager.getConnection(URLBDD)) {
                                // idc se fait tt seul
                                String insertComment2Query = "INSERT INTO COMMENTS ( `#idP_Init`, `#idP_Rep`) VALUES ( ?, ?)";
                                PreparedStatement statementbis = connecting.prepareStatement(insertComment2Query);
                                // we display the Comment in the postContainer
                                Rectangle separatorX = new Rectangle();
                                    separatorX.setWidth(100); // Largeur du rectangle
                                    separatorX.setHeight(5); // Hauteur du rectangle
                                    separatorX.setFill(Color.BLUE); // Couleur de remplissage du rectangle
                                    postContainer.getChildren().add(separatorX);
                                Label oteurC = new Label("author: "+newCommentX.getAuthor().getnametag());
                                oteurC.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                                postContainer.getChildren().add(oteurC);
                                Label DateC = new Label("posted the "+newCommentX.getDate().toString());
                                DateC.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                                postContainer.getChildren().add(DateC);
                                Label TexteC = new Label(newCommentX.getTexte());
                                TexteC.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                                postContainer.getChildren().add(TexteC);
                                HBox likebox4 = new HBox();
                                likebox4.setSpacing(100);
                                Label LikeC = new Label("liked "+ 0 +" times !");
                                LikeC.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                                likebox4.getChildren().add(LikeC);
                                ToggleButton like4 = new ToggleButton();
                                like4.setText("Like");
                                like4.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                                likebox4.getChildren().add(like4);
                                postContainer.getChildren().add(likebox4);
                                BooleanProperty selection4 = like4.selectedProperty();
                                selection4.addListener((observable, ancientV, newV) -> {
                                    if (newV) {
                                        try {
                                            Post.likePost(newCommentX.getidP(), Scene3.currentUser.getiDU());
                                        } catch (SQLException e) {
                                            System.err.println("Error while liking the post"+e.getMessage());
                                        }
                                    } else {
                                        try {
                                            Post.unlikePost(newCommentX.getidP(), Scene3.currentUser.getiDU());
                                        } catch (SQLException e) {
                                            System.err.println("Error while liking the post"+e.getMessage());
                                        }
                                    }
                                    LikeC.setText("Liked "+Integer.toString(newCommentX.getLike())+ " times !");
                                });
                                statementbis.setInt(1, postV.getidP());
                                statementbis.setInt(2, newCommentX.getidP() );
        
                                try {
                                    statementbis.executeUpdate();
                                    statementbis.close();
                                } catch (Exception e) {
                                    System.err.println("Error in the insertion of the comment in the database");
                                }
                            } catch (SQLException e) {
                                System.err.println("Error in the connection to the database");
                            }
                        }
                    } catch (SQLException e) {
                        System.err.println("Error in the connection to the database");
                    }
                });
            });
        }
    }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //FOURTH BLOCK : COMMENTS POSTS
        // here a few things will change and ill explain them
        for (Post post : walling) {
            if (post instanceof Comments) {
                Comments Post_Com= (Comments) post;
                int parentPostId = Post_Com.getParentPostId();
                Post parentofPost = Post.postsbyId.get(parentPostId);
                // we recuperate the parent post of the comment to be able to display the comment in the right place
                VBox thefatherContainer = postContainers.get(parentofPost);
                // we recuperate the container of the parent post to be able to add the comment in it
                // this list will contain all the comments of the parent post
                List<Comments> Post_Call = commentsForPost.getOrDefault(parentofPost, new ArrayList<>());
                // we add our new comment in it
                Post_Call.add(Post_Com);
                commentsForPost.put(parentofPost, Post_Call);
                
                // we add a space between the comments
                Label voidspace = new Label("               ");
                thefatherContainer.getChildren().add(voidspace);
                // Same construction as before
                // rectangle to separate the comments
                Rectangle separator = new Rectangle();
                            separator.setWidth(100); // Largeur du rectangle
                            separator.setHeight(5); // Hauteur du rectangle
                            separator.setFill(Color.BLUE); // Couleur de remplissage du rectangle
                            thefatherContainer.getChildren().add(separator);
                // we display the comment in the parent post container
                Label Com = new Label ("Comment of "+Post_Com.getAuthor().getnametag()+" on the post of "+Post_Com.getParentPost().getAuthor().getnametag());
                Com.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                thefatherContainer .getChildren().add(Com);
                // we display the author of the comment
                Label oteurC = new Label("author: "+Post_Com.getAuthor().getnametag());
                oteurC.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                thefatherContainer .getChildren().add(oteurC);
                // we display the date of the comment
                Label DateC = new Label("posted the "+Post_Com.getDate().toString());
                DateC.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                thefatherContainer .getChildren().add(DateC);
                // we display the text of the comment
                Label TexteC = new Label(Post_Com.getTexte());
                TexteC.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                thefatherContainer .getChildren().add(TexteC);
                // we display the number of likes of the comment
                HBox likebox4 = new HBox();
                likebox4.setSpacing(100);
                int thislikecount;
                try {
                    thislikecount=Post.getLikesForPost(Post_Com.getidP());
                } catch (SQLException e) {
                    thislikecount=0;
                }
                Label LikeC = new Label("liked "+Integer.toString(thislikecount)+" times !");
                LikeC.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                likebox4.getChildren().add(LikeC);
                ToggleButton like4 = new ToggleButton();
                like4.setText("Like");
                like4.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                likebox4.getChildren().add(like4);
                thefatherContainer.getChildren().add(likebox4);
                BooleanProperty selection4 = like4.selectedProperty();
                selection4.addListener((observable, ancientV, newV) -> {
                    if (newV) {
                        Post_Com.Liked();
                    } else {
                        Post_Com.Unliked();
                    }
                    try {
                        LikeC.setText("Liked "+Integer.toString(Post.getLikesForPost(Post_Com.getidP()))+ " times !");
                    } catch (SQLException e) {
                        System.err.println("Error in likes");
                    }
                });
                // a delete button to delete the comment
                Button DeleteC = new Button();
                DeleteC.setText("Delete");
                DeleteC.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
                thefatherContainer.getChildren().add(DeleteC);
                DeleteC.setOnAction(event -> {
                    thefatherContainer.getChildren().remove(Com);
                    thefatherContainer.getChildren().remove(oteurC);
                    thefatherContainer.getChildren().remove(DateC);
                    thefatherContainer.getChildren().remove(TexteC);
                    thefatherContainer.getChildren().remove(likebox4);
                    thefatherContainer.getChildren().remove(DeleteC);
                    thefatherContainer.getChildren().remove(separator);
                    thefatherContainer.getChildren().remove(voidspace);
                    // we are deleting it from POST and COMMENTS TABLES with the correct method
                    try {
                        Comments.deletePostAndCommentsBDD(Post_Com.getidP());
                    } catch (SQLException e) {
                        System.err.println("Error in the deletion of the comment in the database");
                    }
                });
            }
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // COMPLETE CYCLE OVER

        // other importants actions to do
        // Label to introduce the page of the user you are looking at
        Label intru = new Label("Page of Mr/Miss "+userW.getnametag());
        intru.setStyle("-fx-font-size: 80px; -fx-font-weight: bold; -fx-text-fill: black;");
        intru.setTranslateY(-3400);


        // the followers of the idW you are looking at
        Button followB = new Button();
        followB.setText("Follow");
        followB.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: black;");
        followB.setTranslateY(-3200);
        followB.setTranslateX(-600);
        followB.setOnAction(event -> {
            try { // this following method will add the FOLLOW table the current USER for the User's WALL u are looking at
                Users.followBDD(Scene3.currentUser.getiDU(), Users.getWallIdForUser(userW.getiDU()));
            } catch (SQLException e) {
                System.err.println("Error in the following of the user");
            }
        });
        // the unfollow button
        Button unfollowB = new Button();
        unfollowB.setText("Unfollow");
        unfollowB.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: black;");
        unfollowB.setTranslateY(-3200);
        unfollowB.setTranslateX(-300);
        unfollowB.setOnAction(event -> {
            try {   // the unfollow will do the opposite by suppressing it from the TABLE FOLLOWERS
                Users.unfollowBDD(Scene3.currentUser.getiDU(), Users.getWallIdForUser(userW.getiDU()));
            } catch (SQLException e) {
                System.err.println( "Error in the unfollowing of the user");
            }
        });

        // the followers of the userW in a combobox
        Label Followers = new Label("Followers : ");
        Followers.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
        Followers.setTranslateY(-3000);
        Followers.setTranslateX(-600);

        ComboBox<String> FollowersComboBox = new ComboBox<>();
        // we recuperate the followers of the userW using the getFollowersBDD method
        List<Users> followers = Users.getFollowersBDD(Users.getWallIdForUser(useridw));
        for (Users follower : followers) {
            FollowersComboBox.getItems().add(follower.getnametag());
        }
        FollowersComboBox.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
        FollowersComboBox.setTranslateY(-3000);
        FollowersComboBox.setTranslateX(-400);

        // finally we add every component to the content
        content.getChildren().addAll(imagebox, intru, dossiertxt2, dossierimg2, dossiervid2, followB, unfollowB, Followers, FollowersComboBox);
        // the content is added to the scrolling
        ScrollPane scrolling = new ScrollPane(content);
        scrolling.setPannable(true);
        // the scrolling is added to the wallroot
        wallroot.getChildren().add(scrolling);
        // and the wallroot is added to the scene
        Scene wallteacher= new Scene(wallroot, 400, 400);
        // we return the scene
        return wallteacher;
    }
    }
}

