package tim11osa.email.main_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tim11osa.email.main_app.model.Account;
import tim11osa.email.main_app.model.Message;
import tim11osa.email.main_app.services.MessageService;

@RestController
@RequestMapping("/")
public class MessageController {

    @Autowired
    MessageService messageService;

    @PostMapping("/messages/send/{idAccount}")
    public boolean addNewContact(@RequestBody Message newMessage, @PathVariable("idAccount")Integer idAccount) {

        return messageService.sendNewMessage(newMessage, idAccount);
    }
}
