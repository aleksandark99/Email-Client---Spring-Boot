package tim11osa.email.main_app.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim11osa.email.main_app.model.Contact;
import tim11osa.email.main_app.services.ContactService;

import java.util.*;

@RestController
@RequestMapping("/")
public class ContactController {

    @Autowired
    ContactService contactService;



    @PostMapping("/contacts/{user_id}")
    public Integer addContact(@RequestBody Contact newContact, @PathVariable("user_id")Integer userId) {

        return contactService.addContact(newContact, userId);
    }

    @GetMapping("/user_contacts/{idUser}")
    public ResponseEntity<Set<Contact>> getAllContactsForUser(@PathVariable("idUser")Integer userId){
       try {
           Set<Contact> test = contactService.getAllContactsForUser(userId);
           return new ResponseEntity<Set<Contact>>(test, HttpStatus.OK);
       } catch (Exception e){
           e.printStackTrace();
       }
       return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/contacts/{id}")
    public Contact getContactById(@PathVariable("id") Integer idContact){
        return contactService.getContactById(idContact);
    }



    @PutMapping("/contacts/{idUser}")
    public void updateContact(@RequestBody Contact contact,@PathVariable("idUser")Integer idUser){
        contactService.updateContact(contact,idUser);
    }

    @DeleteMapping("/contact/{id}")
    public void deleteContactById(@PathVariable("id") int id){
        contactService.removeContact(id);
    }

}
