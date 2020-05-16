package tim11osa.email.main_app.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tim11osa.email.main_app.model.Contact;
import tim11osa.email.main_app.services.ContactService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class ContactController {

    @Autowired
    ContactService contactService;



    @PostMapping("/contacts")
    public Integer addContact(@RequestBody Contact newContact) {

        return contactService.addContact(newContact);
    }

    @GetMapping("/user_contacts/{idUser}")
    public ArrayList<Contact> getAllContactsForUser(@PathVariable("idUser")Integer userId){
        return contactService.getAllContactsForUser(userId);
    }

    @GetMapping("/contacts/{id}")
    public Contact getContactById(@PathVariable("id") Integer idContact){
        return contactService.getContactById(idContact);
    }



    @PutMapping("/contacts")
    public void updateContact(@RequestBody Contact contact){
        contactService.updateContact(contact);
    }

    @DeleteMapping("/contact/{id}")
    public void deleteContactById(@PathVariable("id") int id){
        contactService.removeContact(id);
    }

}
