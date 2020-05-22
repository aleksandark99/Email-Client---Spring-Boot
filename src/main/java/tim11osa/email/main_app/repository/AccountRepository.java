package tim11osa.email.main_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tim11osa.email.main_app.model.Account;
import tim11osa.email.main_app.model.Contact;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    boolean existsAccountByUsername(String username);

    Optional<Account> findByUsername(String username);

    Optional<Account> findByIdAndUser_Id(Integer accountIdToBeDeleted, Integer userId);


}
