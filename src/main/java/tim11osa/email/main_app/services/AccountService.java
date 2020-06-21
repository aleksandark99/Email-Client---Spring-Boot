package tim11osa.email.main_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tim11osa.email.main_app.crud_interfaces.AccountInterface;
import tim11osa.email.main_app.exceptions.ResourceNotFoundException;
import tim11osa.email.main_app.model.Account;
import tim11osa.email.main_app.model.Contact;
import tim11osa.email.main_app.model.Folder;
import tim11osa.email.main_app.model.User;
import tim11osa.email.main_app.repository.AccountRepository;
import tim11osa.email.main_app.repository.UserRepository;

import java.util.Optional;

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



        return  accountRepository.save(newAccount);
    }

    @Override
    public Account updateAccount(Account accountToBeUpdated, Integer userId, Integer accountId) {

        if(!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("UserId: " + userId + " not found");
        }

        Optional<Account> acc = accountRepository.findByUsername(accountToBeUpdated.getUsername());
        if (acc.isPresent()){
            if (acc.get().getId() != accountToBeUpdated.getId()){
                return null;
            }
        }

        User accountOwner = userRepository.findById(userId).get();

        return accountRepository.findById(accountToBeUpdated.getId()).map(account -> {

            account.setSmtpAddress(accountToBeUpdated.getSmtpAddress());
            account.setSmtpPort(accountToBeUpdated.getSmtpPort());
            account.setInServerType(accountToBeUpdated.getInServerType());
            account.setInServerAddress(accountToBeUpdated.getInServerAddress());
            account.setInServerPort(accountToBeUpdated.getInServerPort());
            account.setAuthentication(accountToBeUpdated.isAuthentication());
            account.setUsername(accountToBeUpdated.getUsername());
            account.setPassword(accountToBeUpdated.getPassword());
            account.setDisplayName(accountToBeUpdated.getDisplayName());
            account.setUser(accountOwner);
            return accountRepository.save(account);
        }).orElseThrow(() -> new ResourceNotFoundException("AccountId " + accountToBeUpdated.getId() + "not found"));

    }

    @Override
    public ResponseEntity<?> removeAccount(Integer idAccountOwner, Integer accountIdToBeDeleted) {

         return accountRepository.findByIdAndUser_Id(accountIdToBeDeleted, idAccountOwner).map(account -> {
            accountRepository.delete(account);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Account not found with id " + accountIdToBeDeleted + " and account owner id: " + idAccountOwner + "!"));
    }


}
