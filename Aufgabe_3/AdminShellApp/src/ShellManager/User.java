package ShellManager;

/**
 * Created by sasa on 10.05.16.
 */
public class User {
	
	private String name;
	private String pw;
	private String email;
	private String salt;
	private String emailVerifyToken;
	private String emailVerifyTokenTimestamp;
	private String pwVerifyToken;
	private String pwVerifyTokenTimestamp;
	private boolean isMailAuth;
	private boolean isPWUsed;

	public User(String pw, String email) {
		super();
	//			this.name = name;
		this.pw = pw;
		this.email = email;
	//			this.salt = salt;
}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isMailAuth() {
		return isMailAuth;
	}

	public void setMailAuth(boolean isMailAuth) {
		this.isMailAuth = isMailAuth;
	}

	public boolean isPWUsed() {
		return isPWUsed;
	}

	public void setPWUsed(boolean isPWUsed) {
		this.isPWUsed = isPWUsed;
//
//		public String getSalt() {
//			return salt;
//		}

//		public void setSalt(String salt) {
//			this.salt = salt;
//		}
	}



//	public String getEmailVerifyToken() {
//		return emailVerifyToken;
//	}
//
//	public void setEmailVerifyToken(String emailVerifyToken) {
//		this.emailVerifyToken = emailVerifyToken;
//	}
//
//	public String getEmailVerifyTokenTimestamp() {
//		return emailVerifyTokenTimestamp;
//	}
//
//	public void setEmailVerifyTokenTimestamp(String emailVerifyTokenTimestamp) {
//		this.emailVerifyTokenTimestamp = emailVerifyTokenTimestamp;
//	}

//	public String getPwVerifyToken() {
//		return pwVerifyToken;
//	}

//	public void setPwVerifyToken(String pwVerifyToken) {
//		this.pwVerifyToken = pwVerifyToken;
//	}

//	public String getPwVerifyTokenTimestamp() {
//		return pwVerifyTokenTimestamp;
//	}

//	public void setPwVerifyTokenTimestamp(String pwVerifyTokenTimestamp) {
//		this.pwVerifyTokenTimestamp = pwVerifyTokenTimestamp;
//	}
}
