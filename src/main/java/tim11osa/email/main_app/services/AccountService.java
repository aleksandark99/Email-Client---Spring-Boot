package tim11osa.email.main_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim11osa.email.main_app.crud_interfaces.AccountInterface;
import tim11osa.email.main_app.exceptions.ResourceNotFoundException;
import tim11osa.email.main_app.model.Account;
import tim11osa.email.main_app.model.Contact;
import tim11osa.email.main_app.model.User;
import tim11osa.email.main_app.repository.AccountRepository;
import tim11osa.email.main_app.repository.UserRepository;

@Service
public class AccountService implements AccountInterface {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Account addNewAccount(Account newAccount, Integer userId) {


        User u = null;

        if (!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("UserId " + userId + " not found!");
        }

        if (accountRepository.existsAccountByUsername(newAccount.getUsername())) return null;

        u = userRepository.findById(userId).get();

        newAccount.setUser(u);

        //Account newA = accountRepository.save(newAccount);


        return accountRepository.save(newAccount);
    }
}
