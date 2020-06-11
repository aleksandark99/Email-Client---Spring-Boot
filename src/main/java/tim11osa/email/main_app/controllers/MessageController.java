package tim11osa.email.main_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tim11osa.email.main_app.model.Message;
import tim11osa.email.main_app.services.MessageService;

import java.util.Set;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping("/messages/{account_id}")
    public Set<Message> getAllMessages(@PathVariable("account_id") int account_id){
        return messageService.getAllMessages(account_id);

    }




    @PostMapping("/messages/send/{idAccount}")
    public boolean addNewContact(@RequestBody Message newMessage, @PathVariable("idAccount")Integer idAccount) {

        return messageService.sendNewMessage(newMessage, idAccount);
    }
}
