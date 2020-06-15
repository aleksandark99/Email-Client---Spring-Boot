package tim11osa.email.main_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Base64;

@Table(name = "attachments")
@Entity
public class Attachment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id", unique = true, nullable = false)
    private int id;


    @Lob
    @Column(name = "base_64_data", columnDefinition="LONGBLOB")
    private byte[]  data;

    @Column(name = "mime_type", unique = false, nullable = false)
    private String mime_type;

    @Column(name = "name", unique = false, nullable = false)
    private String name;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "id_message", referencedColumnName = "message_id", nullable = true)
    private  Message message;

    public Attachment(){}

    public Attachment(int id, byte[]  data, String mime_type, String name, Message message) {
        this.id = id;
        this.data = data;
        this.mime_type = mime_type;
        this.name = name;
        this.message = message;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[]  getData() {
        return data;
    }

    public void setData(byte[]  data) {
        this.data = data;
    }

    public String getMime_type() {
        return mime_type;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
