package ShellManager.DTO;

/**
 * Created by Sasa on 19.05.2016.
 */
public class UserDTO {

    private int id;
    private String email;
    private String passwordHash;
    private boolean isValid;

    public UserDTO(int id, String email, String passwordHash, boolean isValid) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.isValid = isValid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }


    public boolean isValid() {
        return isValid;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

}
