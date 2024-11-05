
import java.time.LocalDate;
// imports

public class Post_video extends Post{ // Post_video class extends Post class
    private String VideoURL; // VideoURL attribute
    private String Time; // Time attribute

    // Post_video constructor :
    public Post_video(int idP,Users author, LocalDate Date, String texte, int like,int idWall, String VideoURL, String Time) {
        super(idP,author, Date, texte, like, idWall); // class heritage of Post
        this.VideoURL = VideoURL; // initialize the VideoURL attribute
        this.Time = Time; // initialize the Time attribute
    }

    //getters
    public String getVideoURL() {
        return VideoURL;
    }
    public String getTime() {
        return Time;
    }
    //setters
    public void setVideoURL(String videoURL) {
        VideoURL = videoURL;
    }
    public void setTime(String time) {
        Time = time;
    }
    
    
}