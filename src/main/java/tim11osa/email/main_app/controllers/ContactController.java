package tim11osa.email.main_app.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim11osa.email.main_app.model.Contact;
import tim11osa.email.main_app.repository.ContactRepository;
import tim11osa.email.main_app.services.ContactService;

import java.util.*;

@RestController
@RequestMapping("/{user_id}")
public class ContactController {


    @Autowired
    ContactService contactService;



    @PostMapping("/contacts/{idUser}")
    public Contact addNewContact(@RequestBody Contact newContact, @PathVariable("idUser")Integer userId) {

        return contactService.addContact(newContact, userId);
    }




    @GetMapping("/users/{idUser}/contacts")
    public Set<Contact> getAllContactsForUser(@PathVariable("idUser")Integer userId){
        return contactService.getAllContactsForUser(userId);

    }


    @GetMapping("/contacts/{id}")
    public Contact getContactById(@PathVariable("id") Integer idContact){
        return contactService.getContactById(idContact);
    }



    @PutMapping("/users/{idUser}/contacts")
    public Contact updateContact(@RequestBody Contact contact,@PathVariable("idUser")Integer idUser){
        return contactService.updateContact(contact,idUser);
    }



    @DeleteMapping("/users/{idUser}/{contactId}")
    public ResponseEntity<?> deleteContactById(@PathVariable("idUser") Integer userId, @PathVariable("contactId") Integer contactId){
        return contactService.removeContact(userId, contactId);
    }

}
