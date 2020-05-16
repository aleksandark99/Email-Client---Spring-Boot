package tim11osa.email.main_app.crud_interfaces;

import tim11osa.email.main_app.model.Contact;

import java.util.ArrayList;
import java.util.List;

public interface ContactInterface {

    ArrayList<Contact> getAllContacts();

    Contact getContactById(int idContact);

    Integer addContact(Contact newContact);

    void removeContact(int idContact);

    void updateContact(Contact contact);


}
