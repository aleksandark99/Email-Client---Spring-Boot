package tim11osa.email.main_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_tab")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    private int id;

    @Column(name = "first_name", unique = false, nullable = false)
    private String firstName;

    @Column(name = "last_name", unique = false, nullable = false)
    private String lastName;

    @Column(name = "user_name", unique = true, nullable = false)
    private String username;

    @Column(name = "pass_word", unique = false, nullable = false)
    private String password;

    @Column(name = "roles", unique = false, nullable = false)
    private String roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Contact> contacts = new HashSet<Contact>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Account> accounts = new HashSet<Account>();

//    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Tag> tags = new HashSet<Tag>();

    public User(){

    }

    public User(int id, String firstName, String lastName, String username, String password, String roles, Set<Contact> contacts, Set<Account> accounts, Set<Tag> tags) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.contacts = contacts;
        this.accounts = accounts;
        this.tags = tags;
    }

    public void add(Contact contact){
        if (contact.getUser() != null){
            contact.getUser().getContacts().remove(contact);
        }
        getContacts().add(contact);
        contact.setUser(this);
    }

    public void remove(Contact contact){
        contact.setUser(null);
        getContacts().remove(contact);
    }


    public void add(Account account){
        if (account.getUser() != null){
            account.getUser().getContacts().remove(account);
        }
        getAccounts().add(account);
        account.setUser(this);
    }

    public void remove(Account account){
        account.setUser(null);
        getAccounts().remove(account);
    }

    /////
    public void add(Tag tag){
        if (tag.getUser() != null){
            tag.getUser().getTags().remove(tag);
        }
        getTags().add(tag);
        tag.setUser(this);
    }

    public void remove(Tag tag){
        tag.setUser(null);
        getTags().remove(tag);
    }

    public void removeSoft(Tag tag){
        getTags().remove(tag);
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles='" + roles + '\'' +
                ", contacts=" + contacts +
                ", accounts=" + accounts +
                ", tags=" + tags +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
