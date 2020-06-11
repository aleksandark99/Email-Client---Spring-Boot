package tim11osa.email.main_app.crud_interfaces;

import tim11osa.email.main_app.model.Message;

import java.util.Set;

public interface MessageInterface {



    Set<Message> getAllMessages(int account_id);

}
