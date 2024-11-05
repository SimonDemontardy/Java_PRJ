
import java.time.LocalDate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Post {
	protected Users author;
	protected String description;
	protected String texte;
	protected String format;
	protected int like;
	protected LocalDate date;
	protected int idWall;
	protected int idP;
	public static int counterP = 8;
	public static int counterW = 3;
	public static Map<Integer, Post> postsbyId = new HashMap<>();

	public Post(int idP, Users author, LocalDate date, String texte, int like, int idWall) {
		this.author = author;
		this.date = date;
		 // Create a new instance of Date
		this.like = like;
		this.texte = texte;
		this.idWall = (idWall== 0) ? generateNewIdW() : idWall;
		this.idP = (idP == 0) ? generateNewIdP() : idP;
	}

	public static void deletePostBDD(int idP) throws SQLException {
		String url = Users.URLBDD;
		Connection connecteur = DriverManager.getConnection(url);
		String sql = "DELETE FROM POSTS WHERE idP = ?";
		PreparedStatement pstmt = connecteur.prepareStatement(sql);
		pstmt.setInt(1, idP);
		pstmt.executeUpdate();
		
	}

		// Cette méthode récupère un poste par son ID, le crée et le place dans un type de poste.
		public static Post getPostByID(int idP) {
			Post post = null;
			
			try (Connection connecting = DriverManager.getConnection(Users.URLBDD)) {
				String query = "SELECT * FROM POSTS WHERE `idP` = " + idP;
				Statement statement = connecting.createStatement();
				ResultSet resultSet = statement.executeQuery(query);
			
				if (resultSet.next()) {		
					
					String content = resultSet.getString("texte");
			
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
					LocalDate date = LocalDate.parse(resultSet.getString("dateC"), formatter);
					int authorId = resultSet.getInt("#idU"); // récupère l'idU de l'auteur du post
					Users author = Users.getUserByIdBDD(authorId); // obtient l'auteur en utilisant l'idU récupéré
					//int likeBDD = result.getInt("like");
					String duree= resultSet.getString("duree");
					String urlIMG = resultSet.getString("urlIMG");
					String urlVID = resultSet.getString("urlVID");
					int idWall = resultSet.getInt("#idW");
					int postID = resultSet.getInt("idP");
			
					if (urlIMG != null) {
						post = new Post_image(postID,author, date, content, 1, idWall, urlIMG);
					} else if (urlVID != null) {
						post = new Post_video(postID,author, date, content, 2, idWall, urlVID, duree);
					} else {
						post = new Post_texte(postID,author, date, 3, idWall, content);
					}
				}
			
				resultSet.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return post;
		}
	
	// Cette méthode récupère les postes du mur d'un utilisateur.
	public static List<Post> getPostsFromUserWall(int idU) {
		List<Post> postsw = new ArrayList<>();
		
		try (Connection connecting = DriverManager.getConnection(Users.URLBDD)) {
			String requetwall = "SELECT * FROM POSTS WHERE `#idW` = (SELECT idW FROM WALLS WHERE `#idU` ="+idU+")";
			Statement statement = connecting.createStatement();
			ResultSet resultSet = statement.executeQuery(requetwall);

			while (resultSet.next()) {
				String content = resultSet.getString("texte");
	
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
				LocalDate date = LocalDate.parse(resultSet.getString("dateC"), formatter);

				int authorId = resultSet.getInt("#idU");
				Users author = Users.getUserByIdBDD(authorId);
				//int likeBDD = result.getInt("like");
				String duree= resultSet.getString("duree");
				String urlIMG = resultSet.getString("urlIMG");
				String urlVID = resultSet.getString("urlVID");
				int idWall = resultSet.getInt("#idW");

				int postID = resultSet.getInt("idP");

				String getLikesQuery = "SELECT COUNT(*) AS likeCount FROM LIKES WHERE `#idP` = " + postID;
				Statement statementLikes = connecting.createStatement();
				ResultSet resultSetLikes = statementLikes.executeQuery(getLikesQuery);
				int likeCount=0;
				if (resultSetLikes.next()) {
					likeCount = resultSetLikes.getInt("likeCount");
				}

				Post post;
				if (urlIMG != null) {
					post = new Post_image(postID,author, date, content, likeCount ,idWall, urlIMG);
				} else if (urlVID != null) {
					post = new Post_video(postID,author, date, content, likeCount,idWall, urlVID, duree);
				} else {
					// there ill make a request to see if the post is a comment or not before using it in my if/else
					String checkComment = "SELECT * FROM COMMENTS WHERE `#idP_Rep` = "+postID;
					Statement statementcom = connecting.createStatement();
					ResultSet resultSetcom = statementcom.executeQuery(checkComment);
					if (resultSetcom.next()){
						// pour chercher les likes d'un com
						int commentID = resultSetcom.getInt("#idP_Rep");
						String getCommentLikesQuery = "SELECT COUNT(*) AS likeCount FROM LIKES WHERE `#idP` = (SELECT idP FROM POSTS WHERE `#idP` ="+commentID+")";
						Statement statementCommentLikes = connecting.createStatement();
						ResultSet resultSetCommentLikes = statementCommentLikes.executeQuery(getCommentLikesQuery);
						int commentLikeCount = 0;
						if (resultSetCommentLikes.next()) {
							commentLikeCount = resultSetCommentLikes.getInt("likeCount");
						}

						int parentPostId = resultSetcom.getInt("#idP_Init");
						Post parentPost = getPostByID(parentPostId);
						post = new Comments(postID,author, date, commentLikeCount, idWall, content, parentPost, parentPostId);
					} else{ 
						post = new Post_texte(postID,author,date,likeCount,idWall, content);
					}
				}
				postsbyId.put(postID, post);
				postsw.add(post);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return postsw;
	}


	// Cette méthode récupère les Posts du mur d'un utilisateur à partir de l'idW et les places dans les différents types de Post.
	public List<Post> getPostsOnWall(int wallId) throws SQLException {
		List<Post> posts = new ArrayList<>();
		try (Connection connecting = DriverManager.getConnection(Users.URLBDD)) {
			String query = "SELECT * FROM POSTS WHERE `#idW` = ?";
			PreparedStatement statement = connecting.prepareStatement(query);
			statement.setInt(1, wallId);

			ResultSet result = statement.executeQuery();
			while (result.next()) {
				String content = result.getString("texte");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
				LocalDate date = LocalDate.parse(result.getString("dateC"), formatter);
				Users author = Users.getUserByIdBDD(result.getInt("#idU"));
				//int likeBDD = result.getInt("like");
				String duree= result.getString("duree");
				String urlIMG = result.getString("urlIMG");
				String urlVID = result.getString("urlVID");

				int idWall = result.getInt("#idW");
				int postID = result.getInt("idP");

				Post post;
					if (urlIMG != null) {
							post = new Post_image(postID,author, date, content, 1, idWall, urlIMG);
					} else if (urlVID != null) {
							post = new Post_video(postID,author, date, content, 2,idWall, urlVID, duree);

					} else {
							post = new Post_texte(postID,author,date,3,idWall, content);


					}
				posts.add(post);
			}
		}
		return posts;
	}

		// cetthe méthode rédcupère les likes d'un post à partir de l'idP.
		public static int getLikesForPost(int postId) throws SQLException {
			int likeCount = 0;
		
			try (Connection connecting = DriverManager.getConnection(Users.URLBDD)) {
				String query = "SELECT COUNT(*) AS likeCount FROM LIKES WHERE `#idP` = ?";
				PreparedStatement statement = connecting.prepareStatement(query);
				statement.setInt(1, postId);
		
				ResultSet resultSet = statement.executeQuery();
				if (resultSet.next()) {
					likeCount = resultSet.getInt("likeCount");
				}
				statement.close();
			}
			return likeCount;
		}

	// cette méthode augmente les likes lorsqu'un utilisateur aime un poste.
	public static void likePost(int postId, int userId) throws SQLException {
		try (Connection connecting = DriverManager.getConnection(Users.URLBDD)) {
			String query = "INSERT INTO LIKES (`#idP`, `#idU`) VALUES (?, ?)";
			PreparedStatement statement = connecting.prepareStatement(query);
			statement.setInt(1, postId);
			statement.setInt(2, userId);
			statement.executeUpdate();
			statement.close();
		}
	}

	// cette méthode diminue les likes lorsqu'un utilisateur n'aime plus un poste.
	public static void unlikePost(int postId, int userId) throws SQLException {
		try (Connection connecting = DriverManager.getConnection(Users.URLBDD)) {
			String query = "DELETE FROM LIKES WHERE `#idP` = ? AND `#idU` = ?";
			PreparedStatement statement = connecting.prepareStatement(query);
			statement.setInt(1, postId);
			statement.setInt(2, userId);
			statement.executeUpdate();
			statement.close();
		}
	}

	public static int generateNewIdP(){
		counterP++;
		return counterP;
	}
	public static int generateNewIdW(){
		counterW++;
		return counterW;
	}



	public void Liked() {
		this.like++;
	}

	public void Unliked(){
		if (this.like>0) {
			this.like--;
		}
	}
	public int getLikes(){
		return this.like;
	}



		//setters
		public void setauthor(Users newauthor) {
			this.author=newauthor;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public void settexte(String texte) {
			this.texte = texte;
		}
		public void setLike(int like) {
			this.like = like;
		}
		public void setDate(LocalDate date) {
			this.date = date;
		}
		public void setFormat(String format) {
			this.format = format;
		}
		public void setIdWall(int idWall) {
			this.idWall = idWall;
		}
		public void setidP(int idP) {
			this.idWall = idP;
		}


		//getters
		public String getdescription() {
			return this.description;
		}
		public Users getAuthor() {
			return author;
		}
		public String getDescription() {
			return description;
		}
		public String gettexte() {
			return texte;
		}
		public LocalDate getDate() {
			return this.date;
		}
		public String getFormat() {
			return format;
		}
		public int getLike() {
			return like;
		}
		public int getIdWall() {
			return idWall;
		}
		public int getidP() {
			return idP;
		}
}