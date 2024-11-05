import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Statement;
// my imports for this class

public class Users { // Users class 
	// input names 
	protected String nametag;
	protected String login;
	protected String password;
	protected LocalDate Birthdate;
	private static int counter = 11;
	protected int iDU;
	protected boolean Banned; //unused
	protected List<Post> postlist;
	public static List<Users> userList = new ArrayList<>();
	protected List<Users> following = new ArrayList<>();
	protected List<Users> followers = new ArrayList<>();
	protected List<Users> blocked = new ArrayList<>();
	public static final String URLBDD = "jdbc:sqlite:/home/simon/myLink.db"; // URL of the database
	

	// first user constructor
	public Users(int iDU,String nametag,String login,String password, LocalDate Birthdate) {
		this.iDU = iDU; 
		this.nametag=nametag;
		this.login=login;
		this.password=password;
		this.Birthdate=Birthdate;
		this.postlist= new ArrayList<Post>(); // initialize the postlist attribute to store the posts of the user
	}

	// methode to generate a new id if needed 
	public static int generateNewId(){
		counter++; // increment the counter
		return counter; // return the new id
	}
//////////////////////////BDD//////////////////////////////////////////////////////////////////////////////////////

	// Cette méthode récupère les utilisateurs à partir de la base de données et les stocke dans une liste.
	public static void usercompletecreation(List<Users> userList) throws SQLException {
		userList.clear(); // because i had doubles
		try (Connection connecting = DriverManager.getConnection(URLBDD)){
			String requeteuser="SELECT * FROM USERS";
			PreparedStatement statement = connecting.prepareStatement(requeteuser);
	
			ResultSet result = statement.executeQuery();
			while (result.next()){
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
				LocalDate dateX = LocalDate.parse(result.getString("dateNaiss"), formatter);
				Users importU = new Users(result.getInt("idU"),result.getString("nom")+" "+result.getString("prenom"), result.getString("login"), result.getString("mdp"), dateX);
				userList.add(importU);
			}
		}
	}
	// Cette méthode récupère les utilisateurs qui ont un mur et les stocke dans une liste d'utilisateurs.
	public static void userCompleteCreationconWall(List<Users> userList) throws SQLException {
		userList.clear();
		try (Connection connecting = DriverManager.getConnection(URLBDD)) {
			String requeteuser = "SELECT USERS.* FROM USERS JOIN WALLS ON USERS.idU = WALLS.`#idU`";
			PreparedStatement statement = connecting.prepareStatement(requeteuser);
	
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
				LocalDate dateX = LocalDate.parse(result.getString("dateNaiss"), formatter);
				Users importU = new Users(result.getInt("idU"), result.getString("nom") + " " + result.getString("prenom"), result.getString("login"), result.getString("mdp"), dateX);
				userList.add(importU);
			}
		}
	}

	// Cette méthode récupère les Posts à partir de la base de données et les places dans les différents types de Post.
	public static List<Post> getPostsFromBDD(int userid) throws SQLException {
		List<Post> posts = new ArrayList<>();

		try (Connection connecting = DriverManager.getConnection(Users.URLBDD)){
			String requetePosts = "SELECT * FROM Posts WHERE `#idU` = ?";
			PreparedStatement statement = connecting.prepareStatement(requetePosts);
			statement.setInt(1, userid);

			ResultSet result = statement.executeQuery();
			while (result.next()){

				String content = result.getString("texte");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
				LocalDate date = LocalDate.parse(result.getString("dateC"), formatter);
				Users author = Users.getUserByIdBDD(userid);
				//int likeBDD = result.getInt("like");
				String duree= result.getString("duree");
				String urlIMG = result.getString("urlIMG");
				String urlVID = result.getString("urlVID");
				int idWall = result.getInt("#idW");
				int postID = result.getInt("idP");

				Post post;
				if (urlIMG != null) {
						post = new Post_image(postID,author, date, content, 1,idWall, urlIMG);
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
	

	
	
	// Cette méthode récupère les Users à partir de leur login
	public static Users getuseronloginBDD(String login) throws SQLException{
		Users importU = null;
	
		try (Connection connecting = DriverManager.getConnection(URLBDD)){
			String requeteuser="SELECT * FROM Users WHERE login = ?";
			PreparedStatement statement = connecting.prepareStatement(requeteuser);
			statement.setString(1, login);
			ResultSet result = statement.executeQuery();
			if (result.next()){
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
				LocalDate dateX = LocalDate.parse(result.getString("dateNaiss"), formatter);
				importU = new Users(result.getInt("idU"),result.getString("nom")+" "+result.getString("prenom"), result.getString("login"), result.getString("mdp"), dateX);
			}
		}
		return importU;
	}

	// Cette méthode récupère les Users à partir de leur IDU
	public static Users getUserByIdBDD(int id) throws SQLException {
		Users usernouv = null;
	
		try (Connection connecting = DriverManager.getConnection(URLBDD)){
			String query = "SELECT * FROM Users WHERE idU = ?";
			PreparedStatement statement = connecting.prepareStatement(query);
			statement.setInt(1, id);
	
			ResultSet result = statement.executeQuery();
			if (result.next()){
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
				LocalDate dateX = LocalDate.parse(result.getString("dateNaiss"), formatter);
				usernouv = new Users(result.getInt("idU"),result.getString("nom")+" "+result.getString("prenom"), result.getString("login"), result.getString("mdp"), dateX);
			}
		}
		return usernouv;
	}


	// Cette méthode récupère les Followers de la BDD à partir de l'idW et les places dans une liste d'utilisateurs.
	public static List<Users> getFollowersBDD(int idwall) throws SQLException {
		List<Users> followers = new ArrayList<>();
		Connection connecting = DriverManager.getConnection(URLBDD);
		String getFollowersQuery = "SELECT USERS.* FROM USERS JOIN FOLLOWERS ON USERS.idU = FOLLOWERS.`#idU` WHERE FOLLOWERS.`#idW` = " + idwall + " AND FOLLOWERS.blocked = 0";

		Statement statement = connecting.createStatement();
		ResultSet resultSet = statement.executeQuery(getFollowersQuery);

		while (resultSet.next()) {
			int followerID = resultSet.getInt("idU");
			Users follower = getUserByIdBDD(followerID);
			followers.add(follower);
		}

		return followers;
	}

	// Cette méthode récupère le idW de l'utilisateur à partir de l'idU.
	public static Integer getWallIdForUser(int idU) throws SQLException {
		Integer idWall = null;

		try (Connection connecting = DriverManager.getConnection(URLBDD)) {
			String query = "SELECT idW FROM WALLS WHERE `#idU` = ?";
			PreparedStatement statement = connecting.prepareStatement(query);
			statement.setInt(1, idU);

			ResultSet result = statement.executeQuery();
			if (result.next()) {
				idWall = result.getInt("idW");
			}
		}

		return idWall;
	}

	// cette méthode verifie si un Post est un commentaire ou non.
	public static boolean isComment(int idP_init) throws SQLException {
		Connection connection = DriverManager.getConnection(URLBDD);
	
		String query = "SELECT COUNT(*) FROM COMMENTS WHERE `#idP_Rep` = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, idP_init);
	
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			int count = resultSet.getInt(1);
			if (count > 0 ){
				return true;
			}
			statement.close();
		}
		return false;
	}

	// follow an user using the bdd :
	public static void followBDD(int idU, int idW) throws SQLException {
		try (Connection connecting = DriverManager.getConnection(URLBDD)) {
			String query = "INSERT INTO FOLLOWERS( `#idW`,`#idU`, blocked) VALUES(?, ?, 0)";
			PreparedStatement statement = connecting.prepareStatement(query);
			statement.setInt(1, idW);
			statement.setInt(2, idU);
			statement.executeUpdate();
		}
	}

	// unfollow an user using the bdd :
	public static void unfollowBDD(int idU, int idW) throws SQLException {
		try (Connection connecting = DriverManager.getConnection(URLBDD)) {
			String query = "DELETE FROM FOLLOWERS WHERE `#idW` = ? AND `#idU` = ?";
			PreparedStatement statement = connecting.prepareStatement(query);
			statement.setInt(1, idW);
			statement.setInt(2, idU);
			statement.executeUpdate();
		}
	}

	// block an user using the bdd :
	public static void blockBDD(int idU, int idW) throws SQLException {
		try (Connection connecting = DriverManager.getConnection(URLBDD)) {
			String query = "UPDATE FOLLOWERS SET blocked = 1 WHERE `#idW` = ? AND `#idU` = ?";
			PreparedStatement statement = connecting.prepareStatement(query);
			statement.setInt(1, idW);
			statement.setInt(2, idU);
			statement.executeUpdate();
		}
	}

	// unblock an user using the bdd :
	public static void unblockBDD(int idU, int idW) throws SQLException {
		try (Connection connecting = DriverManager.getConnection(URLBDD)) {
			String query = "UPDATE FOLLOWERS SET blocked = 0 WHERE `#idW` = ? AND `#idU` = ?";
			PreparedStatement statement = connecting.prepareStatement(query);
			statement.setInt(1, idW);
			statement.setInt(2, idU);
			statement.executeUpdate();
		}
	}

	// cette méthode me permet de récupérer l'état bloqué ou non d'un utilisateur.
	public static boolean isblockedBDD(int idU, int idW) throws SQLException {
		boolean isBlocked = false;
		try (Connection connecting = DriverManager.getConnection(URLBDD)) {
			String query = "SELECT blocked FROM FOLLOWERS WHERE `#idW` = ? AND `#idU` = ?";
			PreparedStatement statement = connecting.prepareStatement(query);
			statement.setInt(1, idW);
			statement.setInt(2, idU);

			ResultSet result = statement.executeQuery();
			if (result.next()) {
				isBlocked = result.getBoolean("blocked");
			}
		}
		return isBlocked;
	}






	///////////////// code iterne //////////////////////////////////////////////////////////////////
	// ces méthodes permettent de récupérer les informations des utilisateurs sans BDD, elles furent utilisées pour des tests.
	


	public void addPost(Post ajout){
		postlist.add(ajout);
	}
	//Banned list and counter
	public void setBanned (Users user, boolean Banned){
		user.Banned = Banned;
	}
	public boolean thatsbanned(){
		return this.Banned;
	}
	//Followers and Following and Blocked
	public void follow(Users user){
		if (this != user) {
		this.following.add(user);
		user.followers.add(this);
		}
	}
	public void unfollow(Users user){
		if (this != user) {
		this.following.remove(user);
		user.followers.remove(this);
		}
	}
	public void block(Users user){
		blocked.add(user);
	}
	public void unblock(Users user){
		blocked.remove(user);
	}
	// obtain the user by his login.
	public static Users getuseronlogin(String login){
		for (Users user: userList){
			if (user.login.equals(login)){
				return user;
			}
		}
		return null;
	}


	// Common setters for users:
	public void setnametag(String newnametag) {
		this.nametag=newnametag;
	}
	public void setlogin(String newlogin) {
		this.login=newlogin;
	}
	public void setpassword(String newpassword) {
		this.password=newpassword;
	}
	public void setBirthdate(LocalDate birthdate) {
		this.Birthdate = birthdate;
	}
	public void setPostlist(List<Post> postlist) {
		this.postlist = postlist;
	}
	public void setiDU(int iDU) {
		this.iDU = iDU;
	}

	
	// common getters for users:
	public String getnametag() {
		return this.nametag;
	}
	public List<Users> getfollowing() {
		return following;
	}
	public List<Users> getfollowers() {
		return followers;
	}
	public List<Users> getblocked() {
		return blocked;
	}
	public String getlogin() {
		return this.login;
	}
	public String getpassword() {
		return this.password;
	}
	public LocalDate getBirthdate() {
		return this.Birthdate;
	}
	public List<Post> getPostlist() {
		return postlist;
	}
	public static List<Users> getUserList() {
        return userList;
    }
	public int getiDU() {
		return this.iDU;
	}


	//to String:
	public String toString() {
			return "current Users: \n Nametag: "+ this.nametag +" associated login: " + this.login+") \n";
	}
}