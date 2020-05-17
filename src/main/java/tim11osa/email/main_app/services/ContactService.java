package tim11osa.email.main_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim11osa.email.main_app.crud_interfaces.ContactInterface;
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
       Optional<User> u = userRepository.findById(idUser);
       //(ArrayList<Contact>) contactRepository.findByUser(u.get());
        return contactRepository.findByUser(u.get());
        //return contactRepository.findByUser(idUser);
    }

    @Override
    public Contact getContactById(int idContact) {
        return contactRepository.getOne(idContact);
    }

    @Override
    public Integer addContact(Contact newContact, Integer userId) {

        User u = userRepository.findById(userId).get();

        newContact.setUser(u);
        Contact newC = contactRepository.save(newContact);

        //u.add(newC);


        Optional<Contact> c = contactRepository.findTopByOrderById();
        return c.isPresent() ? c.get().getId() : 0;

    }

    @Override
    public void removeContact(int idContact) {
        contactRepository.deleteById(idContact);
    }

    @Override
    public void updateContact(Contact contact) {
        contactRepository.save(contact);

    }
}
