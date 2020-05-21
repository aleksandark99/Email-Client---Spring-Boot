package tim11osa.email.main_app.crud_interfaces;

import tim11osa.email.main_app.model.Account;

public interface AccountInterface {

    Account addNewAccount(Account account,Integer userId);
}
