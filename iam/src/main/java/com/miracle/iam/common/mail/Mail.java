package com.miracle.iam.common.mail;

import com.miracle.iam.domain.entity.EmailObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Component
@Slf4j
@Configuration
public class Mail {

    private static final String MAIL_TYPE_ALL="ALL";
    private static final String MAIL_TYPE_NEW="NEW";
    private static final String MAIL_TYPE_UNREAD="UNREAD";

    @Value("${mail.inbox.username}")
    private String userName;

    @Value("${mail.inbox.password}")
    private String password;

    @Value("${mail.inbox.protocol}")
    private String protocol;

    @Value("${mail.inbox.host}")
    private String host;

    @Value("${mail.inbox.port}")
    private int port;

    @Value("${mail.out.host}")
    private String sendHost;

    @Value("${mail.out.port}")
    private int sendPort;

    @Value("${mail.out.from}")
    private String from;

    private Folder folder;

    @Bean
    public JavaMailSender mailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl() ;
        javaMailSender.setHost(sendHost);
        javaMailSender.setPort(sendPort);
        return javaMailSender;
    }

    public Object getNewMails(String mailBox) throws MessagingException, IOException {
        List<EmailObject> emails = new ArrayList<>(4);
        Optional<Store> store = Optional.ofNullable(this.connectMailServer());
        if(!store.isPresent()){
            return null;
        }
        folder = store.get().getDefaultFolder().getFolder(mailBox);
        folder.open(Folder.READ_ONLY);

        Message[] messages = getMessages(mailBox);
        if(null != messages) {
            for (Message message : messages) {
                if(null != message) {
                    Flags flags = message.getFlags();
                    if (flags.contains(Flags.Flag.SEEN) || flags.contains(Flags.Flag.DELETED)) {
                        continue;
                    }
                    EmailObject emailObject = convertMessageToEmailObject(message);
                    emails.add(emailObject);
                }
            }
        }
        return emails;
    }

    public void sendEmail(EmailObject emailObject) throws MessagingException, IOException {
        this.connectMailServer();
        JavaMailSender javaMailSender = this.mailSender();
        MimeMessage mimeMessage = convertEmailObjectToMessage(emailObject,javaMailSender.createMimeMessage());
        javaMailSender.send(mimeMessage);
    }

    private Message[] getMessages(String mailBox) throws MessagingException {
        Message[] messages = null;

        int totalNum = folder.getMessageCount();
        int unreadNum = folder.getUnreadMessageCount();
        int newNum = folder.getNewMessageCount();

        switch(mailBox) {
            case MAIL_TYPE_NEW:
                messages = folder.getMessages(totalNum - newNum + 1, totalNum);
                break;
            case MAIL_TYPE_UNREAD:
                messages = folder.getMessages(totalNum - unreadNum + 1, totalNum);
                break;
            default:
                messages = folder.getMessages();
        }
        return messages;
    }

    private MimeMessage convertEmailObjectToMessage(EmailObject emailObject,MimeMessage mm) throws MessagingException, IOException {
        Address[] addresses = InternetAddress.parse(emailObject.getTo());
        mm.setFrom(emailObject.getFrom());
        mm.setRecipients(Message.RecipientType.TO,addresses);
        mm.setSubject(emailObject.getSubject());
        if(null != emailObject.getBody()){
            mm.setContent(emailObject.getBody(),"text/html;charset=utf-8");
        }
        Multipart multipart= new MimeMultipart();
        if(null != emailObject.getAttachFiles()) {
            for(File o: emailObject.getAttachFiles()){
                BodyPart bodyPart = new MimeBodyPart();
                DataSource dataSource = new FileDataSource(o);
                bodyPart.setDataHandler(new DataHandler(dataSource));
                bodyPart.setFileName(MimeUtility.encodeWord(o.getName()));
                multipart.addBodyPart(bodyPart);
            }
            mm.setContent(multipart);
        }
        return mm;
    }

    private EmailObject convertMessageToEmailObject(Message message) throws MessagingException, IOException {
        MimeMessage mm = (MimeMessage) message;
        List<File> files = new ArrayList<>(3);
        if(mm.isMimeType("multipart/*")){
            files = getAttachments(mm);
        }
        return EmailObject.builder()
                .from(mm.getFrom().toString())
                .date(mm.getSentDate())
                .subject(mm.getSubject())
                .to(mm.getAllRecipients().toString())
                .attachFiles(files)
                .body(mm.getContent().toString())
                .build();
    }


    private List<File> getAttachments(MimeMessage mm) throws IOException, MessagingException {
        List<File> files = new ArrayList<>(3);
        final Multipart multipart = (Multipart) mm.getContent();
        final int count = multipart.getCount();
        for(int i=0;i<count;i++){
            BodyPart bodyPart = multipart.getBodyPart(i);
            String dispositon = bodyPart.getDisposition();
            if(null != dispositon && dispositon.equals(Part.ATTACHMENT)){
                String fileName = MimeUtility.decodeText(bodyPart.getFileName());
                File file = new File(fileName);
                FileOutputStream fos = new FileOutputStream(file);
                InputStream is = bodyPart.getInputStream();
                int b = -1;
                while ((b = is.read()) != -1) {
                    fos.write(b);
                }
                is.close();
                fos.close();
                files.add(file);
            }
        }
        return files;
    }

    private Store connectMailServer() throws MessagingException {
        Session session = this.createSession();
        Store store = session.getStore(this.protocol);
        if(!store.isConnected()){
            store.connect(this.host,this.userName,this.password);
        }
        return store;
    }


    private Session createSession() {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol",this.protocol);
        props.setProperty("mail.imap.port",String.valueOf(this.port));
        props.setProperty("mail.imap.host",this.host);
        props.setProperty("mail.imap.ssl.enable","true");
        props.setProperty("mail.imap.usesocketchannels","true");
        return Session.getInstance(props);
    }
}
