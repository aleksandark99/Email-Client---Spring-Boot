package tim11osa.email.main_app.crud_interfaces;

import org.springframework.http.ResponseEntity;
import tim11osa.email.main_app.model.Rule;
import tim11osa.email.main_app.model.enums.EOperation;

import java.util.Set;

public interface RuleInterface {

    Set<Rule> getRulesByFolder (int folder_id, int account_id);

    Set<Rule> getRulesByOperationType (int folder_id, int operation);

    Rule createRule (Rule rule, int folder_id, int account_id);

    ResponseEntity<?> removeRule (int rule_id, int folder_id, int account_id);
}
