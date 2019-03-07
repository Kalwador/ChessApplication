package com.chess.spring.communication.mail;

import com.chess.spring.communication.mail.type.AccountActivationMail;
import com.chess.spring.communication.mail.type.ResetPasswordMail;
import com.chess.spring.exceptions.InvalidDataException;
import com.chess.spring.profile.account.EmailValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDate;

/**
 * Class provides Mailing System, contains methods to sendMail and SendMail with attachment
 * Class contains factory to create mail html content
 */
@Slf4j
@Service
public class MailFactory {

    private MailService mailService;

    @Value("${application.project.host-web}")
    private String hostWeb;


    @Autowired
    public MailFactory(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * Method send mail without attachment
     *
     * @param mail full prepared object, need to contain recipent and subject
     */
    public void sendMail(Mail mail) throws InvalidDataException, MessagingException {
        String content;
        String subject;

        if (!EmailValidator.isEmailValid(mail.getRecipient())) {
            throw new InvalidDataException();
        }

        switch (mail.getSubject()) {
            case ACCOUNT_ACTIVATION: {
                AccountActivationMail accountActivationMail = (AccountActivationMail) mail;
                content = this.getAccountActivationMessage(accountActivationMail);
                subject = "Task Manager - account activation";
                log.info("Sending message with account activiation " + mail.toString());
                this.mailService.sendMessage(accountActivationMail.getRecipient(), content, subject);
                break;
            }
            case RESET_PASSWORD: {
                ResetPasswordMail resetPasswordMail = (ResetPasswordMail) mail;
                content = this.getResetPasswordMessage(resetPasswordMail);
                subject = "Task Manager - Password Recovery";
                log.info("Sending message with reset PASSWORD " + mail.toString());
                this.mailService.sendMessage(resetPasswordMail.getRecipient(), content, subject);
                break;
            }
        }
    }

    /**
     * Create content for activation account in registration process
     *
     * @param mail full prepared object, need to contain username and activation code
     * @return content in string format
     */
    private String getAccountActivationMessage(AccountActivationMail mail) {
        StringBuilder content = new StringBuilder();
        content.append("<h1>Hello " + mail.getUsername() + "</h1>");
        content.append("<p>Thank you for registration in our application, now you can activate your account:</p>");
        content.append("<p> <a href=\"" + hostWeb + "/register/activate/" + mail.getUsername() + "/" + mail.getActivationCode() + "\">Aktywuj konto</a></p>");
        content.append("<p>Best regards,</p>");
        content.append("<p>Chess app admin</p>");
        log.info("Creating message ActivationAccount content");
        return content.toString();
    }

    /**
     * Create content for reset code in reset PASSWORD process
     *
     * @param mail full prepared object, need to contain username and activation code and date
     * @return content in string format
     */
    private String getResetPasswordMessage(ResetPasswordMail mail) {
        StringBuilder content = new StringBuilder();
        content.append("<h1>Password Recovery</h1>");
        content.append("<p>You or someone else sent a request to restore the PASSWORD for the " + mail.getUsername() + " account. Use this code to restore PASSWORD</p>");
        content.append("<p><b>" + mail.getActivationCode() + "</b></p>");
        content.append("</br>");
        content.append("<p>Request created on " + LocalDate.now().toString() + "</p>");
        content.append("<p>Best regards,</br>ChessApp admin</p>");
        log.info("Creating message ResetPasswordMessage content");
        return content.toString();
    }
}