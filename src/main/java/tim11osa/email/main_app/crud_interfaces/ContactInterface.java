package tim11osa.email.main_app.crud_interfaces;

import org.springframework.http.ResponseEntity;
import tim11osa.email.main_app.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface ContactInterface {

    Set<Contact> getAllContactsForUser(int idUser);

    Contact getContactById(int idContact);

    Contact addContact(Contact newContact, Integer userId);

    ResponseEntity<?> removeContact(Integer idUser, Integer idContact);

    Contact updateContact(Contact contact, Integer idUser);


}
