package tim11osa.email.main_app.crud_interfaces;

import org.springframework.http.ResponseEntity;
import tim11osa.email.main_app.model.Message;

import java.util.Set;

public interface MessageInterface {

    Set<Message> getAllMessages(int account_id);

    Set<Message> getAllMessagesByRules(int folder_id, int account_id);

    Set<Message> getAllInactiveMessages(int account_id);

    Set<Message> getSentMessagesForAccount(int account_id);

    Set<Message> getDraftMessagesForAccount (int account_id);

    Message moveMessageToFolder(int message_id, int folder_id, int account_id);

    ResponseEntity<?> copyMessageToFolder(int message_id, int folder_id, int account_id);

    Message addNewMessage(Message message);

    ResponseEntity<?> moveMessageToDrafts(Message message, int account_id);

    Message makeMessageRead(Message message);

    Set<Message> getAllMessagesFromBack(int account_id);

    Message deleteMessageSoft(Message message);

    ResponseEntity<?> deleteMessagePhysically(int message_id);
}
