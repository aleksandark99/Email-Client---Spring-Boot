package tim11osa.email.main_app.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim11osa.email.main_app.model.Account;
import tim11osa.email.main_app.model.Attachment;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

@Service
public class MessagePuller {
    @Autowired
    FolderService folderService;


    public  List<tim11osa.email.main_app.model.Message> getMailForAccount(Account account)
    {
        List<tim11osa.email.main_app.model.Message> messages =new ArrayList<tim11osa.email.main_app.model.Message>();
        String hostval=account.getInServerAddress();
        String mailStrProt=Integer.toString(account.getInServerPort());
        String uname=account.getUsername();
        String pwd=account.getPassword();
        int t=account.getInServerType();
        try {
            String textzaispis="";
            List<String> TO=new ArrayList<String>();
            List<String> CC=new ArrayList<String>();
            String subject="";
            String from="";

            String mime_type="";
            String att_name="";
            String data="";
            //Set property values
            Properties properties = new Properties();
//    properties.put("mail.pop3.starttls.enable", "true");


//            properties.put("mail.smtp.auth", "true");
//            properties.put("mail.smtp.starttls.enable", "true");
//            properties.put("mail.smtp.host", hostval);
//            properties.put("mail.smtp.port", mailStrProt);

            if(t == 1){
                properties.put("mail.pop3.host", account.getInServerAddress());//acc inserver_adress
                properties.put("mail.pop3.port", account.getInServerPort());
                properties.put("mail.pop3.ssl.enable", "true");
                properties.put("mail.pop3.ssl.trust", "*");
            }
            else {
                properties.put("mail.imap.host", account.getInServerAddress());//acc inserver_adress
                properties.put("mail.imap.port", account.getInServerPort());
                properties.put("mail.imap.ssl.enable", "true");
                properties.put("mail.imap.partialfetch", "false");
                properties.setProperty("mail.imap.partialfetch", "false");
                properties.setProperty("mail.imaps.partialfetch", "false");
                properties.put("mail.imaps.ssl.trust", "*");

            }
            Session emailSessionObj = Session.getDefaultInstance(properties);
            //Create POP3 store object and connect with the server
            String s="";
            if(t==1) {
                s="pop3s";
            }else if(t==0) {
                s="imaps";
            }
            Store storeObj = emailSessionObj.getStore(s);
            storeObj.connect(hostval, uname, pwd);
            //Create folder object and open it in read-only mode
            Folder emailFolderObj = storeObj.getFolder("INBOX");
            emailFolderObj.open(Folder.READ_WRITE);
            //Fetch messages from the folder and print in a loop
            Flags seen = new Flags(Flags.Flag.SEEN);
            FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
            //   Message[] messageobjs = emailFolderObj.getMessages();
            Message[] messageobjs= emailFolderObj.search(unseenFlagTerm);
            for (int i = 0, n = messageobjs.length; i < n; i++) {
                Message indvidualmsg = messageobjs[i];
                subject=indvidualmsg.getSubject();
                Address[] a;
                Address[] froms = indvidualmsg.getFrom();
                String email ="";
                List<Attachment> atts=  new ArrayList<Attachment>();
                if ((a = indvidualmsg.getFrom()) != null) {
                    for (int j = 0; j < a.length; j++)
                        email  = froms == null ? null : ((InternetAddress) froms[0]).getAddress();
                    from=email;
                }
                if ((a = indvidualmsg.getRecipients(Message.RecipientType.TO)) != null) {
                    for (int j = 0; j < a.length; j++) {
                        froms=indvidualmsg.getRecipients(Message.RecipientType.TO);
                        email  = froms == null ? null : ((InternetAddress) froms[j]).getAddress();
                        TO.add(email);
                    }
                }
                if ((a = indvidualmsg.getRecipients(Message.RecipientType.CC)) != null) {
                    for (int j = 0; j < a.length; j++) {
                        froms=indvidualmsg.getRecipients(Message.RecipientType.CC);
                        email  = froms == null ? null : ((InternetAddress) froms[j]).getAddress();
                        CC.add(email);
                    }
                }
                if ((a = indvidualmsg.getRecipients(Message.RecipientType.BCC)) != null) {
                    for (int j = 0; j < a.length; j++) {
                        System.out.println("BCC: " + a[j].toString());
                    }
                }

                String contentType = indvidualmsg.getContentType();
                String messageContent = "";
//       String textzaispis;
                if(contentType.contains("multipart")) {
                    Multipart multiPart = (Multipart) indvidualmsg.getContent();
                    int numberOfParts = multiPart.getCount();
                    for (int partCount = 0; partCount < numberOfParts; partCount++) {
                        MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                        if(part.getContentType().contains("text/plain")) {
                            String gg= part.getContent().toString()+"||||||||";
                        }else if (part.isMimeType("text/html")) {
                            String html = (String) part.getContent();
                            textzaispis=html.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
                        }
                        else {
                            if(part.getContentType().contains("multipart")) {
                                MimeMultipart mimeMultipart = (MimeMultipart) indvidualmsg.getContent();
                                textzaispis = getTextFromMimeMultipart(mimeMultipart);
                            }else {
                                InputStream inputStream = part.getInputStream();
                                byte[] attArray = new byte[inputStream.available()];
                                inputStream.read(attArray);
                                String base64Att = Base64.getEncoder().encodeToString(attArray);
                                System.out.println(base64Att);
                                mime_type=part.getContentType().split(";")[0] ;
                                att_name=part.getFileName();
                                data=base64Att;
                                Attachment attachment=new Attachment();
                                attachment.setData(data);
                                attachment.setName(att_name);
                                attachment.setMime_type(mime_type.split("/")[1]);
                                atts.add(attachment);
                            }
                        }
                    }
                }else {
                    textzaispis=indvidualmsg.getContent().toString();
                }

                indvidualmsg.setFlag(Flag.SEEN, true);

                tim11osa.email.main_app.model.Message m=new tim11osa.email.main_app.model.Message(account);
                Date d=indvidualmsg.getSentDate();
                LocalDateTime ld=d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                m.setDate_time(ld);
                m.setTo(TO);
                m.setCc(CC);
                m.setFrom(from);
                m.setSubject(subject);
                m.setContent(textzaispis);
                m.setAccount(account);
                m.setActive(true);
                m.setAttachments(atts);
                m.setUnread(true);
                m.setFolder(folderService.getInboxByAccount(account.getId()));
                for (Attachment attachment:atts
                     ) {
                    attachment.setMessage(m);

                }
                messages.add(m);

            }

            emailFolderObj.close(false);
            storeObj.close();
            return messages;

        } catch (NoSuchProviderException exp) {
            exp.printStackTrace();
        } catch (MessagingException exp) {
            exp.printStackTrace();
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return messages;

    }

    public  String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart)  throws MessagingException, IOException{
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break;//da se ne bi 2 puta isti tekst prikazivao
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }
}
