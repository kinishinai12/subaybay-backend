package com.kinishinai.contacttracingapp.service;

import com.kinishinai.contacttracingapp.exception.SubaybayException;
import com.kinishinai.contacttracingapp.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MailService {
    private final JavaMailSender MAILSENDER;
    private final MailContentBuilder MAILCONTENTBUILDER;

    @Async
    void sendMail(NotificationEmail notificationEmail){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("subaybay@androidapp.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody());
        };
        try {
            MAILSENDER.send(messagePreparator);
            log.info("activation email sent!");
        }catch (MailException e){log.error("Exception occurred when sending mail", e);
            throw new SubaybayException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);}
    }
}

