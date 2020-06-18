package tim11osa.email.main_app.services;

import com.sun.istack.ByteArrayDataSource;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim11osa.email.main_app.crud_interfaces.MessageInterface;
import tim11osa.email.main_app.model.Attachment;
import tim11osa.email.main_app.model.Message;
import tim11osa.email.main_app.repository.MessageRepository;

import java.net.URLConnection;
import java.util.*;

import com.sun.mail.smtp.SMTPAddressSucceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import java.security.GeneralSecurityException;
import java.util.*;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import tim11osa.email.main_app.model.Account;
import tim11osa.email.main_app.repository.AccountRepository;

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

    @Override
    public Set<Message> getAllMessages(int account_id) {
        return messageRepository.getAllmessagesForAccount(account_id);
    }

    @Override
    public Message addNewMessage(Message message) {

        return messageRepository.save(message);
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
                    String decoded = new String(Base64.getDecoder().decode(att.getData().getBytes()));

                    helper.addAttachment(att.getName(), new ByteArrayDataSource(decoded.getBytes(), createMimeType(att)));
                    att.setMessage(newMessage);
                    //+String.valueOf(new Random().nextInt(10) + 100)
//                    helper.addAttachment(att.getName(), new ByteArrayDataSource(att.getData(), createMimeType(att)));
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

            addNewMessage(newMessage);



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


}
