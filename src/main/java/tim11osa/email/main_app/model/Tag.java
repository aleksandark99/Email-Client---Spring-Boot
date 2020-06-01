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

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

//    @ManyToOne()
//    @JsonIgnore
//    @JoinColumn(name = "id_message", referencedColumnName = "message_id", nullable = true) // proveriti jel ok za nullable
    @ManyToMany(mappedBy = "tags")
    private Set<Message> message;

    public Tag(){

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
}
