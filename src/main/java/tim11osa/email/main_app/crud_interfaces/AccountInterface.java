package tim11osa.email.main_app.crud_interfaces;

import org.springframework.http.ResponseEntity;
import tim11osa.email.main_app.model.Account;

public interface AccountInterface {

    Account addNewAccount(Account account,Integer userId);

    Account updateAccount(Account account,Integer userId,Integer accountId);

    ResponseEntity<?> removeAccount(Integer idUser, Integer idAccount);
}
