package com.chess.spring.communication.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Class provides actions to send diffrent types of messages
 * Class contains methods building object of type Message from javax library
 * Works under MailFactory interface
 */
@Slf4j
@Service
class MailServiceImpl implements MailService {

    private MailingSystemConfiguration mailingSystemConfiguration;

    @Autowired
    public MailServiceImpl(MailingSystemConfiguration mailingSystemConfiguration) {
        this.mailingSystemConfiguration = mailingSystemConfiguration;
    }

    /**
     * Message without attachment
     *
     * @param recipient EMAIL address of recipient
     * @param content   message content
     * @param subject   message subject
     */
    @Override
    public void sendMessage(String recipient, String content, String subject) throws MessagingException {
        Properties prop = getPropertiesOfMailingSystem();

        Session session = Session.getDefaultInstance(prop);
        MimeMessage message = new MimeMessage(session);
            setMessageDetails(recipient, subject, message);
            message.setContent(content, "text/html; charset=utf-8");
            send(message, session);
    }

    /**
     * Message with attachment
     *
     * @param recipient EMAIL address of recipient
     * @param content   message content
     * @param subject   message subject
     * @param file      file tat will be sent as attachment
     */
    @Override
    public void sendMessageWithAttachment(String recipient, String content, String subject, File file) {
        Properties properties = getPropertiesOfMailingSystem();
        Session session = Session.getDefaultInstance(properties);
        try {
            MimeMessage message = new MimeMessage(session);
            setMessageDetails(recipient, subject, message);

            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(content, "text/html");

            MimeBodyPart attachPart = new MimeBodyPart();
            attachPart.attachFile(file);

            multipart.addBodyPart(attachPart);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            send(message, session);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            log.error("messagign exception message = " + recipient + content + subject + file.toString());
        }
    }

    private void setMessageDetails(String recipient, String subject, MimeMessage message) throws MessagingException {
        message.setFrom(new InternetAddress(mailingSystemConfiguration.getEmail()));
        InternetAddress toAddress = new InternetAddress(recipient);
        message.setRecipient(Message.RecipientType.TO, toAddress);
        message.setSubject(subject);
    }

    /**
     * Send Preparded message using serwer and configuration from MailingSystemConfiguration
     *
     * @param msng    sending message
     * @param session default object from javax mailing library
     */
    private void send(MimeMessage msng, Session session) throws MessagingException {
            Transport transport = session.getTransport("smtp");
            transport.connect(mailingSystemConfiguration.getHost(),
                    mailingSystemConfiguration.getEmail(),
                    mailingSystemConfiguration.getPassword());
            transport.sendMessage(msng, msng.getAllRecipients());
            transport.close();
            log.info("Message sent succesfully");
    }

    /**
     * @return Properties object containing all data bout smtp server, ready to use
     */
    private Properties getPropertiesOfMailingSystem() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.starttls.enable", mailingSystemConfiguration.getStart_tsl());
        properties.put("mail.smtp.HOST", mailingSystemConfiguration.getHost());
        properties.put("mail.smtp.ssl.trust", mailingSystemConfiguration.getSsl_trust());
        properties.put("mail.smtp.user", mailingSystemConfiguration.getEmail());
        properties.put("mail.smtp.PASSWORD", mailingSystemConfiguration.getPassword());
        properties.put("mail.smtp.port", mailingSystemConfiguration.getPort());
        properties.put("mail.smtp.auth", mailingSystemConfiguration.getAuth());
        log.info("Message properties set successful");
        return properties;
    }
}
