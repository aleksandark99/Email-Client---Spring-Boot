package tim11osa.email.main_app.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim11osa.email.main_app.model.Account;
import tim11osa.email.main_app.model.Contact;
import tim11osa.email.main_app.services.AccountService;

@RestController
@RequestMapping("/")
public class AccountController {

    private static final Logger LOGGER = LogManager.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;

    @PostMapping("/users/{idUser}/accounts")
    public Account addNewContact(@RequestBody Account newAccount, @PathVariable("idUser")Integer userId) {

        LOGGER.info("Method for creating a new account for user " + userId + " has called");
        return accountService.addNewAccount(newAccount, userId);
    }
    @PutMapping("/users/{idUser}/accounts/{idAccount}")
    public Account updateAccount(@RequestBody Account account, @PathVariable("idUser")Integer userId, @PathVariable("idAccount")Integer idAccount){

        LOGGER.info("Method for updating an existing account for user " + userId + " has called");
        return accountService.updateAccount(account, userId, idAccount);
    }

    @DeleteMapping("/users/{idUser}/accounts/{idAccount}")
    public ResponseEntity<?> deleteAccount(@PathVariable("idUser")Integer userId, @PathVariable("idAccount")Integer idAccount){

        LOGGER.info("Method for removing an account for user " + userId + " has called");
        return accountService.removeAccount(userId, idAccount);
    }

}
