package tim11osa.email.main_app.model;

import tim11osa.email.main_app.model.enums.ECondition;
import tim11osa.email.main_app.model.enums.EOperation;

import javax.persistence.*;

@Entity
@Table(name = "rules")
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_id", nullable = false, unique = true)
    private int rule_id;


    @Enumerated
    @Column(name = "condition_", nullable = false, columnDefinition = "smallint")
    private ECondition condition;

    @Enumerated
    @Column(name = "operation_", nullable = false, columnDefinition = "smallint")
    private EOperation operation;


    public Rule() {}

    public Rule(int rule_id, ECondition condition, EOperation operation) {
        this.rule_id = rule_id;
        this.condition = condition;
        this.operation = operation;
    }

    public int getRule_id() {
        return rule_id;
    }

    public void setRule_id(int rule_id) {
        this.rule_id = rule_id;
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

}
