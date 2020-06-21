package tim11osa.email.main_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.lang.NonNull;
import tim11osa.email.main_app.model.enums.ECondition;
import tim11osa.email.main_app.model.enums.EOperation;

import javax.persistence.*;

@Entity
@Table(name = "rules")
@SQLDelete(sql = "UPDATE rules SET active = false WHERE rule_id = ?")
@Where(clause = "active = true")
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_id", nullable = false, unique = true)
    private int rule_id;

    @NonNull
    @Column(name = "value_", length = 100, nullable = false)
    private String value;

    @Enumerated
    @Column(name = "condition_", nullable = false, columnDefinition = "smallint")
    private ECondition condition;

    @Enumerated
    @Column(name = "operation_", nullable = false, columnDefinition = "smallint")
    private EOperation operation;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "folder_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Folder folder;

    @Column(name = "active", nullable = false)
    private boolean isActive;


    public Rule() {}

    public Rule(int rule_id, @NonNull String value, ECondition condition, EOperation operation, Folder folder, boolean isActive) {
        this.rule_id = rule_id;
        this.value = value;
        this.condition = condition;
        this.operation = operation;
        this.folder = folder;
        this.isActive = isActive;
    }

    public int getRule_id() {
        return rule_id;
    }

    public void setRule_id(int rule_id) {
        this.rule_id = rule_id;
    }

    @NonNull
    public String getValue() {
        return value;
    }

    public void setValue(@NonNull String value) {
        this.value = value;
    }

    public ECondition getCondition() {
        return condition;
    }

    public void setCondition(ECondition condition) {
        this.condition = condition;
    }

    public EOperation getOperation() {
        return operation;
    }

    public void setOperation(EOperation operation) {
        this.operation = operation;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
