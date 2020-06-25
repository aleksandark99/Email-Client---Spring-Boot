package tim11osa.email.main_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Table
@Entity(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id", unique = true, nullable = false)
    private int id;

    @Column(name = "name", unique = false, nullable = false)
    private String name;

    @Column(name = "active")
    boolean active;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private Set<Message> message;

    public Tag(){

    }

    public Tag(int id, String name, User user, Set<Message> message,boolean active) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.message = message;
        this.active=active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Message> getMessage() {
        return message;
    }

    public void setMessage(Set<Message> message) {
        this.message = message;
    }

    public Tag(Tag original){
        this.name = original.getName();
        this.user = original.getUser();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
