package tim11osa.email.main_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

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

    //Ovde dodaj vezu ka porukama

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
