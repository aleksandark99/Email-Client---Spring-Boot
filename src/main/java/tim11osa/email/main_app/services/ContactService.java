package tim11osa.email.main_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import tim11osa.email.main_app.crud_interfaces.ContactInterface;
import tim11osa.email.main_app.exceptions.ResourceNotFoundException;
import tim11osa.email.main_app.model.Contact;
import tim11osa.email.main_app.model.User;
import tim11osa.email.main_app.repository.ContactRepository;
import tim11osa.email.main_app.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ContactService implements ContactInterface {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public Set<Contact> getAllContactsForUser(int idUser) {

        return contactRepository.findByUser_Id(idUser);

    }

    @Override
    public Contact getContactById(int idContact) {

        return contactRepository.getOne(idContact);
    }

    @Override
    public Contact addContact(Contact newContact, Integer userId) {

        User u = null;

        if (!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("UserId " + userId + " not found!");
        }

        u = userRepository.findById(userId).get();

        newContact.setUser(u);
        Contact newC = contactRepository.save(newContact);

        u.add(newC);

        return newC;

    }

    @Override
    public ResponseEntity<?> removeContact(Integer userId, Integer contactIdToBeDeleted) {

        return contactRepository.findByIdAndUser_Id(contactIdToBeDeleted, userId).map(contact -> {
            contactRepository.delete(contact);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Contact not found with id " + contactIdToBeDeleted + " and userId " + userId + "!"));
    }




    @Override
    public Contact updateContact(Contact contactToBeUpdated, Integer idUser) {

        final User u;

        if (!userRepository.existsById(idUser)){
            throw new ResourceNotFoundException("UserId " + idUser + " not found!");
        }
        u = userRepository.findById(idUser).get();

        return contactRepository.findById(contactToBeUpdated.getId()).map(contact -> {
            contact.setFirstName(contactToBeUpdated.getFirstName());
            contact.setLastName(contactToBeUpdated.getLastName());
            contact.setDisplayName(contactToBeUpdated.getDisplayName());
            contact.setEmail(contactToBeUpdated.getEmail());
            contact.setPhotoPath(contactToBeUpdated.getPhotoPath());
            contact.setNote(contactToBeUpdated.getNote());
            contact.setUser(u);
            return contactRepository.save(contact);
        }).orElseThrow(() -> new ResourceNotFoundException("ContactId " + contactToBeUpdated.getId() + "not found"));

    }
}
