package tim11osa.email.main_app.model;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

    private final String jwt;
    private User user;

    public AuthenticationResponse(String jwt, User user) {
        this.jwt = jwt;
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public User getUser() {
        return user;
    }

    public void setNewLoggedInUser(User user) {
        this.user = user;
    }
}
