package tim11osa.email.main_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.persister.walking.internal.FetchStrategyHelper;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "folders")
@SQLDelete(sql = "UPDATE folders SET active = false WHERE folder_id = ?")
@Where(clause = "active=true")
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_id", unique = true, nullable = false)
    private int id;

    @Column(name = "active", nullable = false)
    private boolean isActive;

    @NonNull
    @Column(name = "name", length = 100, nullable = false)
    private String name;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "folder", orphanRemoval = true)
    @Column(name = "destination_id", nullable = false)
    private Set<Rule> destination = new HashSet<>();


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent_folder", orphanRemoval = true)
    @Column(name = "sub_folders")
    private Set<Folder> childFolders = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "folder")
//    @Column(name = "messages", nullable = true)
//    @ManyToMany(mappedBy = "folders", fetch = FetchType.EAGER)
    @Column(nullable = false)
    private Set<Message> messages = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "parent_folder_id", referencedColumnName = "folder_id", nullable = true)
    private Folder parent_folder;


    public Folder() {}

    public Folder(int id, boolean isActive, @NonNull String name, Set<Rule> destination, Set<Folder> childFolders, Account account, Folder parent_folder) {
        this.id = id;
        this.isActive = isActive;
        this.name = name;
        this.destination = destination;
        this.childFolders = childFolders;
        this.account = account;
        this.parent_folder = parent_folder;
    }

    public Folder (boolean isActive, @NonNull String name, Account account){

        this.isActive = isActive;
        this.name = name;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Folder getParent_folder() {
        return parent_folder;
    }

    public void setParent_folder(Folder parent_folder) {
        this.parent_folder = parent_folder;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", destination=" + destination +
                ", childFolders=" + childFolders +
                ", messages=" + messages +
                ", account=" + account +
                ", parent_folder=" + parent_folder +
                '}';
    }
}
