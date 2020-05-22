package tim11osa.email.main_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Table(name = "message")
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", unique = true, nullable = false)
    private int id;

    @Column(name = "from_col", unique = false, nullable = false)
    private String from;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    private Account account;


    //odavde dodajes dalje sta ti treba..

    public Message(){

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
}
