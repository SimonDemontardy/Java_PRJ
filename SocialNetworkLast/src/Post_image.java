import java.time.LocalDate;
// imports


//  Post_image class extends Post class
public class Post_image extends Post {
    private String imageURL; // imageURL attribute

    // Post_image constructor :
    public Post_image(int idP, Users author, LocalDate date, String texte, int like, int idWall, String imageURL) {
        // class heritage of Post
        super(idP,author, date, texte, like, idWall);
        this.imageURL = imageURL; // initialize the imageURL attribute
    }

    //getters
    public String getImageURL() {
        return imageURL;
    }
    //setters
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}