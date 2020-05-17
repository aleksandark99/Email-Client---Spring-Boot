package tim11osa.email.main_app.crud_interfaces;

import tim11osa.email.main_app.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface ContactInterface {

    Set<Contact> getAllContactsForUser(int idUser);

    Contact getContactById(int idContact);

    Integer addContact(Contact newContact, Integer userId);

    void removeContact(int idContact);

    void updateContact(Contact contact);


}
