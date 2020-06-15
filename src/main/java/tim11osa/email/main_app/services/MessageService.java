package tim11osa.email.main_app.services;

import com.sun.istack.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim11osa.email.main_app.crud_interfaces.MessageInterface;
import tim11osa.email.main_app.model.Message;
import tim11osa.email.main_app.repository.MessageRepository;

import java.net.URLConnection;
import java.util.Set;
import com.sun.mail.smtp.SMTPAddressSucceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tim11osa.email.main_app.crud_interfaces.MessageInterface;
import tim11osa.email.main_app.model.Account;
import tim11osa.email.main_app.model.Message;
import tim11osa.email.main_app.repository.AccountRepository;

import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class MessageService implements MessageInterface {

    @Autowired
    MessageRepository messageRepository;

    @Override
    public Set<Message> getAllMessages(int account_id) {
        return messageRepository.getAllmessagesForAccount(account_id);
    }
        @Autowired
        AccountRepository accountRepository;

        public boolean sendNewMessage(Message newMessage, int idAccount) {

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

            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");
            props.put("mail.smtp.ssl.trust", acc.getSmtpAddress());

            if (isAuthenticationRequired) {
                props.put("mail.smtp.auth", "true");
            } else {
                //ovo bi trebalo za uns mejl
            }

            mailSender.setJavaMailProperties(props);

            MimeMessage mimeMessage = mailSender.createMimeMessage();

            try {
                boolean hasAttachments = false;
                if (newMessage.getAttachments() == null  ) hasAttachments = false;
                if ( newMessage.getAttachments() != null && newMessage.getAttachments().size() > 0) hasAttachments = true;
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, hasAttachments);

                helper.setFrom(newMessage.getFrom());
                helper.setTo(newMessage.getTo().stream().toArray(String[]::new));
                helper.setCc(newMessage.getCc().stream().toArray(String[]::new));
                helper.setBcc(newMessage.getBcc().stream().toArray(String[]::new));

                helper.setSubject(newMessage.getSubject());
                helper.setText(newMessage.getContent());

                String filename=newMessage.getAttachments().get(0).getName();
                System.out.println(filename+"DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");

                String type= URLConnection.guessContentTypeFromName(filename+"."+newMessage.getAttachments().get(0).getMime_type());

                System.out.println(type+"DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
                DataSource dataSource = new ByteArrayDataSource(newMessage.getAttachments().get(0).getData(),type );



                helper.addAttachment("asdsdaasd",dataSource);

                //FileSystemResource file = new FileSystemResource("C:\\log.txt");

                //helper.addAttachment(file.getFilename(), file);
                //helper.addAttachment("aaa", new File("C://Users//Miljan//Desktop//download.png"));
                mailSender.send(mimeMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
                messageSent = false;

            } catch (Exception e) {
                e.printStackTrace();
                messageSent = false;
            }


            return messageSent;


        }






}
