
import java.time.LocalDate;
// imports

public class Post_texte extends Post{ // Post_texte class extends Post class
    private String texte; // texte attribute
    
    // Post_texte constructor :
    public Post_texte(int idP,Users author, LocalDate date, int like,int idWall, String texte) {
        super(idP,author, date, texte,like,idWall); // class heritage of Post
        this.texte = texte; // initialize the texte attribute
    }
    
    //getters
    public String getTexte() {
        return texte;
    }
    //setters
    public void setTexte(String texte) {
        this.texte = texte;
    }
}

