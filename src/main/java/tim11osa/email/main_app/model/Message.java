package tim11osa.email.main_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "message")
@Entity
@SQLDelete(sql = "UPDATE message SET active = false WHERE message_id = ?")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", unique = true, nullable = false)
    private int id;

    @Column(name = "active",nullable = false)
    private boolean active;

    @Column(name = "from_col", unique = false, nullable = false)
    private String from;

    @ManyToOne()
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    private Account account;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Folder folder;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attachment> attachments;

    @ManyToMany()
    @JoinTable(name = "tags_messages",
            joinColumns = @JoinColumn(name = "message_id", referencedColumnName = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "tag_id"))
    private Set<Tag> tags=new HashSet<>();


    @ElementCollection
    @CollectionTable(name = "recipient_to", joinColumns = @JoinColumn(name = "message_id"))
    @Column(name="recipient_to")
    private List<String>to;

    @ElementCollection
    @CollectionTable(name = "recipient_cc", joinColumns = @JoinColumn(name = "message_id"))
    @Column(name = "recipient_cc")   //i to i cc i bccc mogu biti null ali mora se proveriti da bar
    private List<String>cc;                                        // jedan od njih nije kada se salje mail

    @ElementCollection
    @CollectionTable(name = "recipient_bcc", joinColumns = @JoinColumn(name = "message_id"))
    @Column(name = "recipient_bcc", unique = false, nullable = true)
    private List<String>bcc;

    @Column(name = "date_time", unique = false, nullable = true)  // promeniti posle da se ne zezas sa testiranjem
    private LocalDateTime date_time;

    @Column(name = "subject", unique = false, nullable = true)
    private String subject;

    @Lob
    @Column(name = "content", unique = false, nullable = true)
    private String content;

    @Column(name = "unread", unique = false, nullable = false)
    private boolean unread;

    public Message(){

    }
    public Message(Account account){
        this.account=account;
        this.cc=new ArrayList<String>();
        this.to=new ArrayList<String>();

    }

    public Message(int id, boolean active, String from, Account account, Folder folder, List<Attachment> attachments, Set<Tag> tags, List<String> to, List<String> cc, List<String> bcc, LocalDateTime date_time, String subject, String content, boolean unread) {
        this.id = id;
        this.active = active;
        this.from = from;
        this.account = account;
        this.folder = folder;
        this.attachments = attachments;
        this.tags = tags;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.date_time = date_time;
        this.subject = subject;
        this.content = content;
        this.unread = unread;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Folder getFolderr() {
        return folder;
    }

    public void setFolder(Folder  folder) {
        this.folder = folder;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }

    public LocalDateTime getDate_time() {
        return date_time;
    }

    public void setDate_time(LocalDateTime date_time) {
        this.date_time = date_time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    //DEEP COPY of Message object used for copy message from one folder to another :)
    public Message(Message original){
        this.content = original.getContent();
        this.from = original.getFrom();
        this.subject = original.getSubject();
        this.content = original.getContent();
        this.account = original.getAccount();
        this.date_time = original.getDate_time();
        this.tags = new HashSet<>();
        for(Tag t : original.getTags()){
            this.tags.add(new Tag(t));
        }
        this.attachments = new ArrayList<>();
        for(Attachment a : original.getAttachments()){
            this.attachments.add(new Attachment(a));
        }
        this.cc = new ArrayList<>();
        for(String s : original.getCc()){
            this.cc.add(s);
        }
        this.to = new ArrayList<>();
        for(String s : original.getTo()){
            this.to.add(s);
        }
        this.bcc = new ArrayList<>();
        for(String s : original.getBcc()){
            this.bcc.add(s);
        }

    }
}
