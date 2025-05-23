package com.akgulberk.fullstackdevelopercasespringboot.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${sendgrid.api-key}")
    private String sendGridApiKey;

    @Value("${sendgrid.from-email}")
    private String fromEmail;

    public void sendEmail(String to, String subject, String message) throws IOException {
        logger.info("E-posta gönderme işlemi başlatıldı");
        logger.info("Gönderen: {}", fromEmail);
        logger.info("Alıcı: {}", to);
        logger.info("Konu: {}", subject);
        
        try {
            Email from = new Email(fromEmail);
            Email toEmail = new Email(to);
            Content content = new Content("text/plain", message);
            Mail mail = new Mail(from, subject, toEmail, content);

            SendGrid sg = new SendGrid(sendGridApiKey);
            Request request = new Request();

            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            logger.info("SendGrid API'ye istek gönderiliyor...");
            Response response = sg.api(request);
            
            logger.info("SendGrid yanıt kodu: {}", response.getStatusCode());
            logger.info("SendGrid yanıt başlıkları: {}", response.getHeaders());
            logger.info("SendGrid yanıt gövdesi: {}", response.getBody());
            
            if (response.getStatusCode() != 202) {
                logger.error("E-posta gönderilemedi. Durum kodu: {}", response.getStatusCode());
                throw new IOException("E-posta gönderilemedi. Durum kodu: " + response.getStatusCode());
            }
            
            logger.info("E-posta başarıyla gönderildi");
        } catch (Exception e) {
            logger.error("E-posta gönderimi sırasında hata oluştu", e);
            throw e;
        }
    }
} 