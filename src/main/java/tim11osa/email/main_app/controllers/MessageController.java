package tim11osa.email.main_app.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tim11osa.email.main_app.model.Folder;
import tim11osa.email.main_app.model.Message;
import tim11osa.email.main_app.services.MessageService;

import java.security.GeneralSecurityException;
import java.util.Set;

import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Track;


@RestController
@RequestMapping("/{user_id}")
public class MessageController {
    private static final Logger logger = LogManager.getLogger(MessageController.class);

    @Autowired
    MessageService messageService;

    @GetMapping("/messages/{account_id}")
    public Set<Message> getAllMessages(@PathVariable("user_id") int user_id,@PathVariable("account_id") int account_id){
        logger.info("TESSSSSSSSSSSSSSSSSS");
        return messageService.getAllMessages(account_id);

    }

    @GetMapping("/messagesfromback/{account_id}")
    public Set<Message> getAllMessagesFromBack(@PathVariable("account_id") int account_id){
        return messageService.getAllMessagesFromBack(account_id);

    }

    @GetMapping("/messages/{folder_id}/{account_id}")
    public Set<Message> getAllMessagesByRules(@PathVariable("folder_id") int folder_id, @PathVariable("account_id") int acc_id){

        return messageService.getAllMessagesByRules(folder_id, acc_id);
    }

    @GetMapping("/inactive_messages/{account_id}")
    public Set<Message> getAllInactiveMessages(@PathVariable("account_id") int acc_id){

        return messageService.getAllInactiveMessages(acc_id);
    }

    @GetMapping("/sent_messages/{account_id}")
    public Set<Message> getAllSentMessages(@PathVariable("account_id") int acc_id){

        return messageService.getSentMessagesForAccount(acc_id);
    }

    @GetMapping("/drafts_messages/{account_id}")
    public Set<Message> getDraftsMessages(@PathVariable("account_id") int acc_id){

        return messageService.getDraftMessagesForAccount(acc_id);
    }

    @PutMapping("/messages/")
    public boolean readMessage(@RequestBody Message message){
        if( messageService.makeMessageRead(message)!=null){
            return true;

        }
        return false;
    }

    @PutMapping("/messages/addTag/{tagId}")
    public boolean addTagToMessage(@RequestBody Message message,@PathVariable("tagId") int tag_id){
        if(messageService.addTagToMessage(message,tag_id)!=null){
            return true;
        }

        return false;
    }
    @PutMapping("/messages/removeTag/{tagId}")
    public boolean removeTag(@RequestBody Message message,@PathVariable("tagId") int tag_id){
        if(messageService.removeTagForMessagee(message,tag_id)!=null){
            return true;
        }

        return false;
    }


    @PutMapping("/messages/delete")
    public boolean deleteMessage(@RequestBody Message message){
        messageService.deleteMessageSoft(message);
        return true;
    }

    @DeleteMapping("message/delete/{message_id}")
    public ResponseEntity<?> deleteMessageFromTrash(@PathVariable("message_id") int message_id){

        return messageService.deleteMessagePhysically(message_id);
    }

    @PostMapping("/messages/send/{idAccount}")
    public boolean sendNewMessage(@RequestBody Message newMessage, @PathVariable("idAccount")Integer idAccount)  {

        return messageService.sendNewMessage(newMessage, idAccount);
    }


    @GetMapping("messages/notify/{idAccount}")
    public String numberOfMessages(@PathVariable("idAccount")int idAccount) {
        return String.valueOf(messageService.pullFromServerAndGetCount(idAccount));
    }

    @PutMapping("/message/{message_id}/{folder_id}/{account_id}")
    public Message moveMessageToFolder(@PathVariable("message_id") int message_id, @PathVariable("folder_id") int folder_id, @PathVariable("account_id") int acc_id){

        return messageService.moveMessageToFolder(message_id, folder_id, acc_id);

    }

    @PostMapping("/message/drafts/{account_id}")
    public ResponseEntity<?> moveMessageToDrafts(@RequestBody Message message, @PathVariable("account_id") int account_id){

        return messageService.moveMessageToDrafts(message, account_id);
    }


    @PostMapping("/message/{message_id}/{folder_id}/{account_id}/copy")
    public ResponseEntity<?> copyMessageToFolder(@PathVariable("message_id") int message_id, @PathVariable("folder_id") int folder_id, @PathVariable("account_id") int acc_id){

        return messageService.copyMessageToFolder(message_id, folder_id, acc_id);
    }


}
