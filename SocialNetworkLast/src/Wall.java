
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.shape.Rectangle;
// imports of the class. 

// this class is displayed only if the user clicked on the choice box in the all user list. 
// it will contain all the posts of the user that the current user clicked on.


public class Wall extends StackPane{
    public Scene createScene(Users userW) {
        if (userW.getblocked().contains(Scene3.currentUser)) { // this page wont be displayed if the user is blocked (in intern, not for the BDD)
            StackPane blockedRoot = new StackPane();
            Label blockedMessage = new Label("You have been blocked by this user.");
            blockedMessage.setStyle("-fx-font-size: 50px; -fx-font-weight: bold; -fx-text-fill: black;");
            blockedMessage.setTranslateY(500);
            Image cheh = new Image("file:SocialNetworkLast/PNGS/sheh.png");
            ImageView Cheh = new ImageView(cheh);
            blockedRoot.getChildren().addAll(blockedMessage, Cheh);
            return new Scene(blockedRoot, 400, 400);
            // instead it will show a blank page with a kind message.
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Now the real Wall page
        else{
        int useridw = userW.getiDU();
        List<Post> Postofhim = new ArrayList<>(); // Initialize the Postofhim variable
        // we will get the posts of the user that the current user clicked on.
        // by going in the BDD with the id of the user using the following method.
        try {
            Postofhim = Users.getPostsFromBDD(useridw);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        userW.setPostlist(Postofhim);
        // and we add the posts in walling
        List<Post> walling = userW.getPostlist();
        
        // we recreate the ugly background
        StackPane wallroot = new StackPane();
        // same setup as scene3 for the background
        Image floral = new Image("file:SocialNetworkLast/PNGS/png.png");
        VBox imagebox = new VBox();
            for (int i =0; i< 5; i++) {
                ImageView Floral = new ImageView(floral);
                Floral.setFitWidth(1900);
                Floral.setPreserveRatio(true);
                imagebox.getChildren().add(Floral);
            }
        imagebox.setSpacing(-5);
        
        //basically now its going to be the same that in Scene but only with this Users posts. 
        // so a Post call cycle
        // introduction of different stackPane that we will fill or not considering the type of post
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // initialize the content of the page
        StackPane content= new StackPane();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // THE CYCLE OF POSTS
        for (Post post : walling) {
        if (post instanceof Post_texte) {
            Post_texte postT = (Post_texte) post;
            Rectangle separator = new Rectangle();
                        separator.setWidth(500); // Largeur du rectangle
                        separator.setHeight(5); // Hauteur du rectangle
                        separator.setFill(Color.BLACK); // Couleur de remplissage du rectangle
                        dossiertxt.getChildren().add(separator);
            Label oteurT = new Label("author: "+postT.getAuthor().getnametag());
            oteurT.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossiertxt.getChildren().add(oteurT);
            Label DateT= new Label("posted the "+postT.getDate().toString());
            DateT.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossiertxt.getChildren().add(DateT);
            Label TexteT = new Label(postT.getTexte());
            TexteT.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossiertxt.getChildren().add(TexteT);
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
            dossiertxt.getChildren().add(likebox);
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
                    System.err.println("error in the like count");
                }
            });
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        } else if (post instanceof Post_image){
            Post_image postI= (Post_image) post;

            Rectangle separator = new Rectangle();
                        separator.setWidth(500); // Largeur du rectangle
                        separator.setHeight(5); // Hauteur du rectangle
                        separator.setFill(Color.BLACK); // Couleur de remplissage du rectangle
                        dossierimg.getChildren().add(separator);
            Label oteurI = new Label("author: "+postI.getAuthor().getnametag());
            oteurI.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossierimg.getChildren().add(oteurI);
            Label DateI = new Label("posted the "+postI.getDate().toString());
            DateI.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossierimg.getChildren().add(DateI);
            Label texteI = new Label(postI.gettexte());
            texteI.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossierimg.getChildren().add(texteI);
            Image picture = new Image(postI.getImageURL());
            ImageView Picture = new ImageView(picture);
            Picture.setPreserveRatio(true);
            Picture.setFitWidth(400);
            dossierimg.getChildren().add(Picture);
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
            ToggleButton like2 = new ToggleButton();
            like2.setText("Like");
            like2.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            likebox2.getChildren().add(like2);
            dossierimg.getChildren().add(likebox2);
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
                    System.err.println("error in the like count");
                }
            });
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////   
        } else if (post instanceof Post_video){
            Post_video postV = (Post_video) post;
            Rectangle separator = new Rectangle();
                        separator.setWidth(500); // Largeur du rectangle
                        separator.setHeight(5); // Hauteur du rectangle
                        separator.setFill(Color.BLACK); // Couleur de remplissage du rectangle
                        dossiervid.getChildren().add(separator);
            Label oteurV = new Label("author: "+postV.getAuthor().getnametag());
            oteurV.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossiervid.getChildren().add(oteurV);
            Label DateV = new Label("posted the "+postV.getDate().toString());
            DateV.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossiervid.getChildren().add(DateV);
            Label texteV = new Label(postV.gettexte());
            texteV.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossiervid.getChildren().add(texteV);
            Label video = new Label(postV.getVideoURL());
            video.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossiervid.getChildren().add(video);
            Label Time= new Label(postV.getTime());
            Time.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            dossiervid.getChildren().add(Time);
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
            dossiervid.getChildren().add(likebox3);
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
                    System.err.println("error in the like count");
                }
            });
        }
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // END OF THE CYCLE OF POSTS
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////////////////////
        // other content of the page//
        // the name of the user 
        Label intru = new Label("Page of Mr/Miss "+userW.getnametag());
        intru.setStyle("-fx-font-size: 80px; -fx-font-weight: bold; -fx-text-fill: black;");
        intru.setTranslateY(-3400);

        // the block button 
        Button blockB = new Button();
        blockB.setText("Block");
        blockB.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: black;");
        blockB.setTranslateY(-3200);
        blockB.setTranslateX(300);
        blockB.setOnAction(event -> { // when you press the Button, the current user will change the status of 
            // this user to blocked. but only if the current user has a wall and if the user is following him.
            try {
                Users.blockBDD(userW.getiDU(), Users.getWallIdForUser(Scene3.currentUser.getiDU()));
            } catch (SQLException e) {
                System.err.println("you dont have a wall, so you cant block a user to see it. or this user inst following u");
            }
        });
        // the unblock button will be use to change the status of the user to unblocked.
        Button Unblock = new Button();
        Unblock.setText("Unblock");
        Unblock.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: black;");
        Unblock.setTranslateY(-3200);
        Unblock.setTranslateX(600);
        Unblock.setOnAction(event -> {
            Scene3.currentUser.unblock(userW);
            try {
                Users.unblockBDD(userW.getiDU(), Users.getWallIdForUser(Scene3.currentUser.getiDU()));
            } catch (SQLException e) {
                System.err.println("you dont have a wall, so you cant unblock a user. or this user inst following u");
            }
        });
        
        // finally we add everything to the content
        content.getChildren().addAll(imagebox, intru, dossiertxt2, dossierimg2, dossiervid2, blockB, Unblock);
        // the content to the scrolling
        ScrollPane scrolling = new ScrollPane(content);
        scrolling.setPannable(true);
        // the scrolling to the root
        wallroot.getChildren().add(scrolling);
        // the root to the scene
        Scene wall= new Scene(wallroot, 400, 400);
        // and we return the scene
        return wall;
    }
    }
}