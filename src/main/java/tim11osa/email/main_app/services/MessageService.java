package tim11osa.email.main_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim11osa.email.main_app.crud_interfaces.MessageInterface;
import tim11osa.email.main_app.model.Message;
import tim11osa.email.main_app.repository.MessageRepository;

import java.util.Set;

@Service
public class MessageService implements MessageInterface {

    @Autowired
    MessageRepository messageRepository;

    @Override
    public Set<Message> getAllMessages(int account_id) {
        return messageRepository.getAllmessagesForAccount(account_id);
    }
}
