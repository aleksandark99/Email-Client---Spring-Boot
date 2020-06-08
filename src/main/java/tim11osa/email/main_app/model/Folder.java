package tim11osa.email.main_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.persister.walking.internal.FetchStrategyHelper;
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


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent_folder", orphanRemoval = true)
    @Column(name = "sub_folders")
    private Set<Folder> childFolders = new HashSet<>();


//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "folder")
//    @Column(name = "messages", nullable = true)
    @ManyToMany(mappedBy = "folders")
    private Set<Message> messages ;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "parent_folder_id", referencedColumnName = "folder_id", nullable = true)
    private Folder parent_folder;

    public Folder() {}

    public Folder(int id, @NonNull String name, Set<Rule> destination, Set<Folder> childFolders, Account account, Folder parent_folder) {
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.childFolders = childFolders;
        this.account = account;
        this.parent_folder = parent_folder;
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
}
