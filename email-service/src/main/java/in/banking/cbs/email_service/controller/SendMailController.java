package in.banking.cbs.email_service.controller;


import in.banking.cbs.email_service.service.SendMailService;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/mail/send")
public class SendMailController {

    private final SendMailService sendMailService;

    SendMailController(SendMailService sendMailService){
        this.sendMailService = sendMailService;
    }

    @PostMapping("/simple")
    public ResponseEntity<String> sendSimpleMail(@RequestParam("to") String to,
                                                 @RequestParam("subject") String subject,
                                                 @RequestParam("body") String body)
    {
        return ResponseEntity.ok(sendMailService.sendSimpleMail(to, subject, body));
    }


    @PostMapping("/simple-with-attachment")
    public ResponseEntity<String> sendSimpleMailWithAttachment(@RequestParam("to") String to,
                                                         @RequestParam("subject") String subject,
                                                         @RequestParam("body") String body,
                                                         @RequestParam("files") List<MultipartFile> attachments) throws MessagingException {
        return ResponseEntity.ok(sendMailService.sendSimpleMailWithAttachment(to, subject, body, attachments));
    }


    @PostMapping("/html")
    public ResponseEntity<String> sendHtmlMail(@RequestParam("to") String to,
                                               @RequestParam("subject") String subject,
                                               @RequestParam("htmlBody") String htmlBody,
                                               @RequestParam(value = "files", required = false) List<MultipartFile> attachments) throws MessagingException
    {

        return ResponseEntity.ok(sendMailService.sendHtmlMail(to, subject, htmlBody, attachments));
    }


    @PostMapping("/inline")
    public ResponseEntity<String> sendInlineImage(@RequestParam("to") String to,
                                               @RequestParam("subject") String subject,
                                               @RequestParam("htmlBody") String htmlBody,
                                               @RequestParam(value = "files") List<MultipartFile> attachments) throws MessagingException, IOException
    {
        return ResponseEntity.ok(sendMailService.sendInlineImage(to, subject, htmlBody, attachments));
    }



    /// //////////////////////////// ASYNC /////////////////////////////////////////

    @PostMapping("/async/simple")
    public ResponseEntity<String> sendSimpleMailAsync(@RequestParam("to") String to,
                                                 @RequestParam("subject") String subject,
                                                 @RequestParam("body") String body) throws InterruptedException {
        sendMailService.sendSimpleMailAsync(to, subject, body);
        Thread.sleep(500);
        return ResponseEntity.ok("will be send shortly");
    }


    @PostMapping("/async/simple-with-attachment")
    public ResponseEntity<String> sendSimpleMailWithAttachmentAsync(@RequestParam("to") String to,
                                                               @RequestParam("subject") String subject,
                                                               @RequestParam("body") String body,
                                                               @RequestParam("files") List<MultipartFile> attachments) throws MessagingException, IOException, InterruptedException {
        sendMailService.sendSimpleMailWithAttachmentAsync(to, subject, body, attachments);
        Thread.sleep(500);
        return ResponseEntity.ok("will be send shortly");
    }


    @PostMapping("/async/html")
    public ResponseEntity<String> sendHtmlMailAsync(@RequestParam("to") String to,
                                               @RequestParam("subject") String subject,
                                               @RequestParam("htmlBody") String htmlBody,
                                               @RequestParam(value = "files", required = false) List<MultipartFile> attachments) throws MessagingException, IOException, InterruptedException {

        sendMailService.sendHtmlMailAsync(to, subject, htmlBody, attachments);
        Thread.sleep(500);
        return ResponseEntity.ok("will be send shortly");
    }


    @PostMapping("/async/inline")
    public ResponseEntity<String> sendInlineImageAsync(@RequestParam("to") String to,
                                                  @RequestParam("subject") String subject,
                                                  @RequestParam("htmlBody") String htmlBody,
                                                  @RequestParam(value = "files") List<MultipartFile> attachments) throws MessagingException, IOException, InterruptedException {

        sendMailService.sendInlineImageAsync(to, subject, htmlBody, attachments);
        Thread.sleep(500);
        return ResponseEntity.ok("will be send shortly");
    }



}
