package tim11osa.email.main_app.services;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.sun.istack.ByteArrayDataSource;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tim11osa.email.main_app.crud_interfaces.MessageInterface;
import tim11osa.email.main_app.exceptions.ResourceNotFoundException;
import tim11osa.email.main_app.model.Attachment;
import tim11osa.email.main_app.model.Message;
import tim11osa.email.main_app.model.Rule;
import tim11osa.email.main_app.repository.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.*;
import java.util.Base64;


import com.sun.mail.smtp.SMTPAddressSucceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import java.security.GeneralSecurityException;
import java.util.*;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import tim11osa.email.main_app.model.Account;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.stream.Collectors;


@Service
public class MessageService implements MessageInterface {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    AccountRepository accountRepository;


    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    MessagePuller messagePuller;

    @Autowired
    FolderService folderService;

    @Autowired
    FolderRepository folderRepository;


    @Override
    public Set<Message> getAllMessages(int account_id) {

        Account acc=accountRepository.findById(account_id).orElse(null);

        fetchAndSaveAllMessages(acc);
        int folderInbox=folderService.getInboxByAccount(account_id).getId();
        return messageRepository.getAllmessagesForAccount(account_id,folderInbox);
    }

    @Override
    public Set<Message> getAllMessagesByRules(int folder_id, int account_id) {

        Set<Message> folderMessagesForAllRules = new HashSet<>();
        int folderInbox = folderService.getInboxByAccount(account_id).getId();

        Set<Message> folderMessagesTO = messageRepository.getAllMessageByTO(folderInbox, folder_id, account_id);
        Set<Message> folderMessagesCC = messageRepository.getAllMessageByCC(folderInbox, folder_id, account_id);
        Set<Message> folderMessagesFROM = messageRepository.getAllMessageByFROM(folderInbox, folder_id, account_id);
        Set<Message> folderMessagesSUBJECT = messageRepository.getAllMessageBySUBJECT(folderInbox, folder_id, account_id);

        Set<Message> folderMessagesManuallySet = messageRepository.getAllmessagesForAccount(account_id, folder_id);

        folderMessagesForAllRules.addAll(folderMessagesTO);
        folderMessagesForAllRules.addAll(folderMessagesCC);
        folderMessagesForAllRules.addAll(folderMessagesFROM);
        folderMessagesForAllRules.addAll(folderMessagesSUBJECT);
        folderMessagesForAllRules.addAll(folderMessagesManuallySet);

        return folderMessagesForAllRules;
    }

    @Override
    public Set<Message> getAllInactiveMessages(int account_id) {

        if(!accountRepository.existsById(account_id))

            throw new ResourceNotFoundException("The account " + account_id + " is not found!");

        return messageRepository.getAllInactiveMessages(account_id);
    }

    @Override
    public Set<Message> getSentMessagesForAccount(int account_id) {

        if(!accountRepository.existsById(account_id))

            throw new ResourceNotFoundException("The account " + account_id + " is not found!");

        return messageRepository.getAllSentMessages(account_id);
    }

    @Override
    public Message moveMessageToFolder(int message_id, int folder_id, int account_id) {

        if(!accountRepository.existsById(account_id))
            throw new ResourceNotFoundException("The account " + account_id + " is not found!");


        if(!messageRepository.existsById(message_id)){
            throw new ResourceNotFoundException("The message " + message_id + " is not found!");
        }

        return folderRepository.findById(folder_id).map(folder -> {

            Message message = messageRepository.findById(message_id).get();

            message.setFolder(folder);

            return messageRepository.save(message);

        }).orElseThrow(() -> new ResourceNotFoundException("The folder " + folder_id + " is not found!"));
    }

    @Override
    public ResponseEntity<?> copyMessageToFolder(int message_id, int folder_id, int account_id) {

        if(!accountRepository.existsById(account_id))
            throw new ResourceNotFoundException("The account " + account_id + " is not found!");


        if(!messageRepository.existsById(message_id)){
            throw new ResourceNotFoundException("The message " + message_id + " is not found!");
        }

        return folderRepository.findById(folder_id).map(folder -> {

            Message message = messageRepository.findById(message_id).get();

            Message copy = new Message(message);

            copy.setActive(true);
            copy.setFolder(folder);

            messageRepository.save(copy);

            return ResponseEntity.ok().build();

        }).orElseThrow(() -> new ResourceNotFoundException("The folder " + folder_id + " is not found!"));
    }


    @Override
    public Message addNewMessage(Message message) {

        return messageRepository.save(message);
    }

    @Override
    public Message makeMessageRead(Message message) {
        return messageRepository.findById(message.getId()).map(m -> {
            m.setUnread(false);
            return  messageRepository.save(m);
        }).orElseThrow(() -> new ResourceNotFoundException("The folder " + message.getId() + "is not found!"));
    }

    @Override
    public Set<Message> getAllMessagesFromBack(int account_id) {
        int folderInbox=folderService.getInboxByAccount(account_id).getId();
        return messageRepository.getAllmessagesForAccount(account_id,folderInbox);
    }

    @Override
    public Message deleteMessageSoft(Message message) {
        return messageRepository.findById(message.getId()).map(m -> {
            m.setActive(false);
            return  messageRepository.save(m);
        }).orElseThrow(() -> new ResourceNotFoundException("The folder " + message.getId() + "is not found!"));
    }

    @Override
    public ResponseEntity<?> deleteMessagePhysically(int message_id) {

        return messageRepository.findById(message_id).map(message -> {

            messageRepository.delete(message);

            return ResponseEntity.ok().build();

        }).orElseThrow(() -> new ResourceNotFoundException("The message " + message_id + " is not found!"));
    }


    public boolean sendNewMessage(Message newMessage, int idAccount)   {

        boolean messageSent = true;

        Account acc = accountRepository.findById(idAccount).get();

        boolean isAuthenticationRequired = acc.isAuthentication();


        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            //Properties props = mailSender.getJavaMailProperties();
        Properties props = new Properties();
            //SimpleMailMessage message = new SimpleMailMessage();

        mailSender.setHost(acc.getSmtpAddress());
        mailSender.setPort(acc.getSmtpPort()); //465 ...

        mailSender.setUsername(acc.getUsername());
        mailSender.setPassword(acc.getPassword());

        messageSent = setPropertiesBasedOnSMTPPort(props, acc, messageSent);

        if (!messageSent) return false;

        /*props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.trust", acc.getSmtpAddress());*/

        if (isAuthenticationRequired) {
            props.put("mail.smtp.auth", "true");
        } else {
                //ovo bi trebalo za uns mejl
        }
        mailSender.setJavaMailProperties(props);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        boolean hasAttachments = false;
        if (newMessage.getAttachments().size() > 0) hasAttachments = true;
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, hasAttachments);
            if (hasAttachments){
                for (Attachment att : newMessage.getAttachments()){
                    byte[] bb=Base64.getDecoder().decode(att.getData().getBytes());
                   helper.addAttachment(att.getName(), new ByteArrayDataSource(bb, createMimeType(att)));
                    att.setMessage(newMessage);

                }
            }

            helper.setFrom(newMessage.getFrom());
            helper.setTo(newMessage.getTo().stream().toArray(String[]::new));
            helper.setCc(newMessage.getCc().stream().toArray(String[]::new));
            helper.setBcc(newMessage.getBcc().stream().toArray(String[]::new));

            helper.setSubject(newMessage.getSubject());
            helper.setText(newMessage.getContent());

            mailSender.send(mimeMessage);

            newMessage.setDate_time(LocalDateTime.now());
            newMessage.setAccount(acc);
            newMessage.setFolder(folderService.getSentByAccount(acc.getId()));
            newMessage.setActive(true);
            addNewMessage(newMessage);

            System.out.println("System out test");


        } catch (MessagingException e) {
            e.printStackTrace();
            messageSent = false;

        } catch (Exception e) {
            e.printStackTrace();
            messageSent = false;
        }

        return messageSent;

    }


    private String createMimeType(Attachment att){

        return URLConnection.guessContentTypeFromName(att.getName()+"."+att.getMime_type());

    }


    public void fetchAndSaveAllMessages(Account account){
        saveAllNewMessages(messagePuller.getMailForAccount(account));

    }

    public void saveAllNewMessages(List<Message> messages){
        messageRepository.saveAll(messages);
        for (Message m:messages
             ) {
           if(m.getAttachments()!=null){
               attachmentRepository.saveAll(m.getAttachments());
           }

        }
    }



    private boolean setPropertiesBasedOnSMTPPort(Properties props, Account acc, boolean messageSent)  {
        boolean messageSentChanged = messageSent;
        switch (String.valueOf(acc.getSmtpPort())){

            case "25":
            case "587":
                props.put("mail.transport.protocol", "smtp");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.debug", "true");
                props.put("mail.smtp.ssl.trust", acc.getSmtpAddress());
                break;
            case "465":
                MailSSLSocketFactory sf = null;
                try {
                    sf =  new MailSSLSocketFactory();
                    sf.setTrustAllHosts(true);
                } catch (GeneralSecurityException e){
                    e.printStackTrace();
                    messageSentChanged = !messageSent;
                } catch (Exception e){
                    e.printStackTrace();
                    messageSentChanged = !messageSent;
                }

                props.put("mail.transport.protocol", "smtp");
                props.put("mail.smtp.ssl.enable", "true");
                //props.put("mail.smtp.starttls.enable", "true");
                //props.put("mail.smtp.host", "smtp.gmail.com");
               // props.put("mail.smtp.port", "465");
                //props.put("mail.smtp.auth", "true");
                //props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.ssl.socketFactory", sf);
                props.put("mail.debug", "true");
                break;
            default: return messageSentChanged;


        }
        return messageSentChanged;
    }


    public int pullFromServerAndGetCount(int idAccount){
        Optional<Account> acc = accountRepository.findById(idAccount);
        if(!acc.isPresent()) throw  new ResourceNotFoundException("Account with id " + idAccount + " not found!!");

        List<Message> messages = new MessagePuller().getMailForAccount(acc.get());

        this.saveAllNewMessages(messages);

        return messages.size();
    }


}
