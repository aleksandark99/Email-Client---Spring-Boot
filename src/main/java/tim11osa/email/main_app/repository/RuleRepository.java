package tim11osa.email.main_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tim11osa.email.main_app.model.Rule;
import tim11osa.email.main_app.model.enums.EOperation;

import java.util.Set;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Integer> {

    @Query(value = "Select * from rules r where r.folder_id = ?1 and r.active = true", nativeQuery = true)
    Set<Rule> getAllRulesByFolder(int folder_id);

    @Query(value = "Select * from rules r where r.folder_id = ?1 and r.operation_ = ?2 and r.active = true", nativeQuery = true)
    Set<Rule> getRulesByOperationType(int folder_id, int operation);

}