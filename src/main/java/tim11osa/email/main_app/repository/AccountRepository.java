package tim11osa.email.main_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tim11osa.email.main_app.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    boolean existsAccountByUsername(String username);

}
