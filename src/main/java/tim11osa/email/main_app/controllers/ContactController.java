package tim11osa.email.main_app.controllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim11osa.email.main_app.model.Contact;
import tim11osa.email.main_app.repository.ContactRepository;
import tim11osa.email.main_app.services.ContactService;

import java.util.*;

@RestController
@RequestMapping("/")
public class ContactController {

    private static final Logger LOGGER = LogManager.getLogger(ContactController.class);

    @Autowired
    ContactService contactService;



    @PostMapping("/contacts/{idUser}")
    public Contact addNewContact(@RequestBody Contact newContact, @PathVariable("idUser")Integer userId) {

        LOGGER.info("Method for creating a new contact for user " + userId + " has called");
        return contactService.addContact(newContact, userId);
    }


    @GetMapping("/users/{idUser}/contacts")
    public Set<Contact> getAllContactsForUser(@PathVariable("idUser")Integer userId){

        LOGGER.info("Method who returns set of contacts for user " + userId + " has called");
        return contactService.getAllContactsForUser(userId);
    }


    @GetMapping("/{idUser}/contacts/{id}")
    public Contact getContactById(@PathVariable("id") Integer idContact){

        LOGGER.info("Method who returns contact by id " + idContact + " has called");
        return contactService.getContactById(idContact);
    }


    @PutMapping("/users/{idUser}/contacts")
    public Contact updateContact(@RequestBody Contact contact,@PathVariable("idUser")Integer idUser){

        LOGGER.info("Method for updating an existing contact for user " + idUser + " has called");
        return contactService.updateContact(contact,idUser);
    }



    @DeleteMapping("/users/{idUser}/{contactId}")
    public ResponseEntity<?> deleteContactById(@PathVariable("idUser") Integer userId, @PathVariable("contactId") Integer contactId){

        LOGGER.info("Method for removing an existing contact for user " + userId + " has called");
        return contactService.removeContact(userId, contactId);
    }

}
