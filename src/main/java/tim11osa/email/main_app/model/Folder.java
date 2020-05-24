package tim11osa.email.main_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "folders")
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_id", unique = true, nullable = false)
    private int id;

    @NonNull
    @Column(name = "name", length = 100, unique = true, nullable = false)
    private String name;


    @OneToMany(cascade= CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(name = "destination_id", nullable = false)
    private Set<Rule> destination = new HashSet<>();


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(name = "sub_folders")
    private Set<Folder> childFolders = new HashSet<>();


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "folder")
    @Column(name = "messages", nullable = true)
    private Set<Message> messages = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    private Account account;

    public Folder() {}

    public Folder(int id, @NonNull String name, Set<Rule> destination, Set<Folder> childFolders, Account account) {
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.childFolders = childFolders;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public Set<Rule> getDestination() {
        return destination;
    }

    public void setDestination(Set<Rule> destination) {
        this.destination = destination;
    }

    public Set<Folder> getChildFolders() {
        return childFolders;
    }

    public void setChildFolders(Set<Folder> childFolders) {
        this.childFolders = childFolders;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
