package tim11osa.email.main_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tim11osa.email.main_app.crud_interfaces.RuleInterface;
import tim11osa.email.main_app.exceptions.ResourceNotFoundException;
import tim11osa.email.main_app.model.Rule;
import tim11osa.email.main_app.model.enums.EOperation;
import tim11osa.email.main_app.repository.FolderRepository;
import tim11osa.email.main_app.repository.RuleRepository;

import java.util.Set;

@Service
public class RuleService implements RuleInterface {

    @Autowired
    FolderRepository folderRepository;


    @Autowired
    RuleRepository ruleRepository;

    @Override
    public Set<Rule> getRulesByFolder(int folder_id, int account_id) {

        if(!folderRepository.getOneByIdAndAccount(folder_id, account_id).isPresent()){

            throw new ResourceNotFoundException("The folder " + folder_id + " for account " + account_id + " does not found!");
        }
        return ruleRepository.getAllRulesByFolder(folder_id);
    }

    @Override
    public Set<Rule> getRulesByOperationType(int folder_id, int operation) {

        if(!folderRepository.existsById(folder_id))

            throw new ResourceNotFoundException("The folder " + folder_id + " does not found!");

        return ruleRepository.getRulesByOperationType(folder_id, operation);
    }

    @Override
    public Rule createRule(Rule rule, int folder_id, int account_id) {

        if(!folderRepository.getOneByIdAndAccount(folder_id, account_id).isPresent())

            throw new ResourceNotFoundException("The folder " + folder_id + " for account " + account_id + " does not found!");


        return folderRepository.findById(folder_id).map(folder -> {

            rule.setFolder(folder);
            rule.setActive(true);
            return ruleRepository.save(rule);

        }).orElseThrow(() -> new ResourceNotFoundException("The folder " + folder_id + " does not exist!"));
    }

    @Override
    public ResponseEntity<?> removeRule(int rule_id, int folder_id, int account_id) {

        if(!folderRepository.getOneByIdAndAccount(folder_id, account_id).isPresent())

            throw new ResourceNotFoundException("The folder " + folder_id + " for account " + account_id + " does not found!");

        return ruleRepository.findById(rule_id).map(rule -> {

            rule.setFolder(null);
            ruleRepository.delete(rule);

            return ResponseEntity.ok().build();

        }).orElseThrow(() -> new ResourceNotFoundException("The rule " + rule_id + " is not found!"));
    }
}
