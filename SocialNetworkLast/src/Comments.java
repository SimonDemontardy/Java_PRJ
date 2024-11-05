import java.time.LocalDate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
// my imports for this class

public class Comments extends Post_texte { // Comments class extends Post_texte class
	//my inputs
    private Post parentPost; // parentPost attribute
	private int parentPostId; // parentPostId attribute
	
	// comments constructor. 
	public Comments(int idP, Users author, LocalDate date, int like, int idWall, String texte,Post parentPost, int parentPostId) { 
		// class heritage of Post_texte
        super(idP,author, date, idWall, like, texte);
        this.parentPost = parentPost; // initialize the parentPost attribute
		this.parentPostId = parentPostId; // initialize the parentPostId attribute
    }

	public static void deletePostAndCommentsBDD(int idP) throws SQLException { // deletePostAndCommentsBDD method
		String url = Users.URLBDD; // URL of the database
	
		try (Connection connecteur = DriverManager.getConnection(url)) { // try to connect to the database
			// SQL statement to delete the post
			String sqlPost = "DELETE FROM POSTS WHERE idP = ?"; 
			try (PreparedStatement pstmtPost = connecteur.prepareStatement(sqlPost)) { // try to prepare the statement
				pstmtPost.setInt(1, idP); // set the idP
				pstmtPost.executeUpdate(); // execute the statement
				pstmtPost.close(); // close the statement
			} catch (SQLException e) { // catch the exception
				System.err.println("Error in deletePostAndCommentsBDD: type 1 " + e.getMessage());
			}
			connecteur.close();
	
			// SQL statement to delete the comments
			String sqlComments = "DELETE FROM COMMENT WHERE `#idP_Rep` = ?"; 
			try (PreparedStatement pstmtComments = connecteur.prepareStatement(sqlComments)) { // try to prepare the statement
				pstmtComments.setInt(1, idP); // set the idP
				pstmtComments.executeUpdate(); // execute the statement
				pstmtComments.close(); // close the statement
			} catch (SQLException e) {
				System.err.println("Error in deletePostAndCommentsBDD: type 2 " + e.getMessage());
			}
			connecteur.close(); // close the connection
		}
	}

	// common getters
	public int getLikeC() {
		return this.like;
	}
	
	public Post getParentPost() {
		return parentPost;
	}

	public int getParentPostId() {
		return parentPostId;
	}
	
	// common setters
	public void setParentPost(Post_texte parentPost) {
		this.parentPost = parentPost;
	}
}