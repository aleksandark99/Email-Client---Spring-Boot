package tim11osa.email.main_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tim11osa.email.main_app.model.Account;
import tim11osa.email.main_app.model.Contact;
import tim11osa.email.main_app.services.AccountService;

@RestController
@RequestMapping("/")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/users/{idUser}/accounts")
    public Account addNewContact(@RequestBody Account newAccount, @PathVariable("idUser")Integer userId) {

        return accountService.addNewAccount(newAccount, userId);
    }

    @PutMapping("/users/{idUser}/accounts/{idAccount}")
    public Account updateAccount(@RequestBody Account account, @PathVariable("idUser")Integer userId, @PathVariable("idAccount")Integer idAccount){

        return accountService.updateAccount(account, userId, idAccount);
    }
}
