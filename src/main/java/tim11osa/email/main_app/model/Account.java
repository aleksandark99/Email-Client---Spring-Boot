package tim11osa.email.main_app.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    private int id;

    @Column(name = "smtp_Address", unique = false, nullable = false)
    private String smtpAddress;

    @Column(name = "smtp_Port", unique = false, nullable = false)
    private int smtpPort;

    @Column(name = "inServer_Type", unique = false, nullable = false)
    private int inServerType;

    @Column(name = "inServer_Address", unique = false, nullable = false)
    private String inServerAddress;

    @Column(name = "inServer_Port", unique = false, nullable = false)
    private int inServerPort;

    @Column(name = "username_col", unique = true, nullable = false)
    private String username;

    @Column(name = "password_col", unique = true, nullable = false)
    private String password;

    @Column(name = "display_Name", unique = false, nullable = false)
    private String displayName;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    public Account() {
    }

    public Account(int id, String smtpAddress, int smtpPort, int inServerType, String inServerAddress, int inServerPort, String username, String password, String displayName, User user) {
        this.id = id;
        this.smtpAddress = smtpAddress;
        this.smtpPort = smtpPort;
        this.inServerType = inServerType;
        this.inServerAddress = inServerAddress;
        this.inServerPort = inServerPort;
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSmtpAddress() {
        return smtpAddress;
    }

    public void setSmtpAddress(String smtpAddress) {
        this.smtpAddress = smtpAddress;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public int getInServerType() {
        return inServerType;
    }

    public void setInServerType(int inServerType) {
        this.inServerType = inServerType;
    }

    public String getInServerAddress() {
        return inServerAddress;
    }

    public void setInServerAddress(String inServerAddress) {
        this.inServerAddress = inServerAddress;
    }

    public int getInServerPort() {
        return inServerPort;
    }

    public void setInServerPort(int inServerPort) {
        this.inServerPort = inServerPort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
