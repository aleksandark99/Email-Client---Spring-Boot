package tim11osa.email.main_app.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim11osa.email.main_app.model.Rule;
import tim11osa.email.main_app.model.enums.EOperation;
import tim11osa.email.main_app.services.RuleService;

import java.util.Set;

@RestController
@RequestMapping("/{user_id}")
public class RuleController {

    private static final Logger LOGGER = LogManager.getLogger(RuleController.class);

    @Autowired
    RuleService ruleService;

    @GetMapping("/rules/{folder_id}/{account_id}")
    public Set<Rule> getAllRules(@PathVariable("folder_id") int folder_id, @PathVariable("account_id") int account_id){

        LOGGER.info("Method who returns set of rules for folder " + folder_id + " has called");
        return ruleService.getRulesByFolder(folder_id, account_id);
    }

    @GetMapping("/rules_operation/{folder_id}/{operation}")
    public Set<Rule> getRulesByOperationType(@PathVariable("folder_id") int folder_id, @PathVariable("operation") int operation){

        return ruleService.getRulesByOperationType(folder_id, operation);
    }

    @PostMapping("/rule/{folder_id}/{account_id}")
    public Rule createRule(@RequestBody Rule rule, @PathVariable("folder_id") int folder_id, @PathVariable("account_id") int acc_id){

        LOGGER.info("Method for creating a new rule for folder " + folder_id + " has called");
        return ruleService.createRule(rule, folder_id, acc_id);
    }

    @DeleteMapping("/rule/{rule_id}/{folder_id}/{account_id}")
    public ResponseEntity<?> removeRule(@PathVariable("rule_id") int rule_id, @PathVariable("folder_id") int folder_id, @PathVariable("account_id") int acc_id){

        LOGGER.info("Method for removing an existing rule for folder " + folder_id + " has called");
        return ruleService.removeRule(rule_id, folder_id, acc_id);
    }

}
