package tim11osa.email.main_app.crud_interfaces;

import tim11osa.email.main_app.model.Message;

import java.util.Set;

public interface MessageInterface {

    Set<Message> getAllMessages(int account_id);

    Set<Message> getAllMessagesByRules(int folder_id, int account_id);

    Set<Message> getAllInactiveMessages(int account_id);

    Message addNewMessage(Message message);

    Message makeMessageRead(Message message);

    Set<Message> getAllMessagesFromBack(int account_id);

    Message deleteMessageSoft(Message message);


}
