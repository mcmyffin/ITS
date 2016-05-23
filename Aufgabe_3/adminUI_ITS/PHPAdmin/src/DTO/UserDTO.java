package DTO;

/**
 * Created by Sasa on 19.05.2016.
 */
public class UserDTO {
    private String id;
    private String email;
    private String passwort;
    private boolean isValid;

    public UserDTO(String id, String email, String passwort, boolean isValid) {
        this.id = id;
        this.email = email;
        this.passwort = passwort;
        this.isValid = isValid;
    }

//    public String toString() {
//
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
