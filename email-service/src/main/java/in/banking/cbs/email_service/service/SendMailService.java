package in.banking.cbs.email_service.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class SendMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public String sendSimpleMail(String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);

        return "success";
    }

    public String sendSimpleMailWithAttachment(String to, String subject, String body, List<MultipartFile> attachments) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);

        for (MultipartFile attachment : attachments) {
            helper.addAttachment(attachment.getOriginalFilename(), attachment);
        }

        javaMailSender.send(message);

        return "success";
    }


    public String sendHtmlMail(String to, String subject, String htmlBody, List<MultipartFile> attachments) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        System.out.println(attachments);

        if (attachments != null && !attachments.isEmpty()) {
            for (MultipartFile attachment : attachments) {
                helper.addAttachment(attachment.getOriginalFilename(), attachment);
            }
        }

        javaMailSender.send(message);

        return "success";
    }

    public String sendInlineImage(String to, String subject, String htmlBody, List<MultipartFile> attachments) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        if (attachments != null) {
            for (MultipartFile attachment : attachments) {
                if(htmlBody.contains("{cid}")) {
                    String cid = UUID.randomUUID().toString();
                    helper.addInline(cid, attachment, attachment.getContentType());
                    htmlBody = htmlBody.replaceFirst("\\{cid\\}", cid);
                }else{
                    helper.addAttachment(attachment.getOriginalFilename(), attachment);
                }
            }
        }

        helper.setText(htmlBody, true);

        javaMailSender.send(message);

        return "success";
    }



    /// //////////////////// ASYNC /////////////////////////////////

    @Async
    public void sendSimpleMailAsync(String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);

    }

    @Async
    public void sendSimpleMailWithAttachmentAsync(
            String to, String subject, String body, List<MultipartFile> attachments
    ) throws MessagingException, IOException {

        List<byte[]> list = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();

        for(MultipartFile attachment : attachments){
            list.add(attachment.getBytes());
            fileNames.add(attachment.getOriginalFilename());
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);



        for(int i=0; i<list.size(); i++){
            helper.addAttachment(fileNames.get(i), new ByteArrayResource(list.get(i)));
        }

        javaMailSender.send(message);
    }



    @Async
    public void sendHtmlMailAsync(String to, String subject, String htmlBody, List<MultipartFile> attachments) throws MessagingException, IOException {

        List<byte[]> list = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();

        for(MultipartFile attachment : attachments){
            list.add(attachment.getBytes());
            fileNames.add(attachment.getOriginalFilename());
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        for(int i=0; i<list.size(); i++){
            helper.addAttachment(fileNames.get(i), new ByteArrayResource(list.get(i)));
        }

        javaMailSender.send(message);
    }

    @Async
    public void sendInlineImageAsync(String to, String subject, String htmlBody, List<MultipartFile> attachments) throws MessagingException, IOException {

        List<byte[]> list = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();
        List<String> contentType = new ArrayList<>();

        for(MultipartFile attachment : attachments){
            list.add(attachment.getBytes());
            fileNames.add(attachment.getOriginalFilename());
            contentType.add(attachment.getContentType());
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);


        for(int i=0; i<list.size(); i++){
            if(htmlBody.contains("{cid}")) {
                String cid = UUID.randomUUID().toString();
                helper.addInline(cid, new ByteArrayResource(list.get(i)), contentType.get(i));
                htmlBody = htmlBody.replaceFirst("\\{cid\\}", cid);
            }else{
                helper.addAttachment(fileNames.get(i), new ByteArrayResource(list.get(i)));
            }
        }

        helper.setText(htmlBody, true);

        javaMailSender.send(message);

    }

}
