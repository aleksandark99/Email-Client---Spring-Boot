package tim11osa.email.main_app.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "account")
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", unique = true, nullable = false)
    private int id;

    @Column(name = "smtp_address", unique = false, nullable = false)
    private String smtpAddress;

    @Column(name = "smtp_port", unique = false, nullable = false)
    private int smtpPort;

    @Column(name = "inserver_type", unique = false, nullable = false)
    private int inServerType;

    @Column(name = "inserver_address", unique = false, nullable = false)
    private String inServerAddress;

    @Column(name = "inserver_port", unique = false, nullable = false)
    private int inServerPort;

    @Column(name = "authentication", unique = false, nullable = false)
    private boolean authentication;

    @Column(name = "username_col", unique = true, nullable = false)
    private String username;

    @Column(name = "password_col", unique = false, nullable = false)
    private String password;

    @Column(name = "display_name", unique = false, nullable = false)
    private String displayName;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Folder> folders;

    //odavde dodajes set poruka
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Message> messages = new HashSet<Message>();

    public Account() {
    }

    public Account(int id, String smtpAddress, int smtpPort, int inServerType, String inServerAddress, int inServerPort, boolean authentication, String username, String password, String displayName, User user, Set<Message> messages) {
        this.id = id;
        this.smtpAddress = smtpAddress;
        this.smtpPort = smtpPort;
        this.inServerType = inServerType;
        this.inServerAddress = inServerAddress;
        this.inServerPort = inServerPort;
        this.authentication = authentication;
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.user = user;
        this.messages = messages;
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

    public boolean isAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
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

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", smtpAddress='" + smtpAddress + '\'' +
                ", smtpPort=" + smtpPort +
                ", inServerType=" + inServerType +
                ", inServerAddress='" + inServerAddress + '\'' +
                ", inServerPort=" + inServerPort +
                ", authentication=" + authentication +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
