package com.service;

import com.data.MemberRepository;
import com.model.Member;

import java.util.List;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * Created by szkolenie on 2016-08-25.
 */
@Stateless
public class SendMailTLS {

    @Inject
    private MemberRepository repository;

    public void prepareMailService(){
        final String username = "a69290b918729a87d0f1cff9b01990@gmail.com";
        final String password = "fc041ed05ac319d5fe613877a7b1cfd2";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        collectMails(session);             //After set up start sending mails
        //System.out.println("Email service prepared!");
    }

    private void collectMails(Session session) {
        Long membersNumber = repository.getMembersNumber();
        List<Member> members = repository.findAllOrderedByName();

        for(int i = 0; i < membersNumber; i++){
            System.out.println("Sending to: " + members.get(i).getEmail());
            System.out.println("Member " + i + " of " + members.size());
            sendMail(members.get(i).getEmail(), session);
        }
    }

    private void sendMail(String subscriberEmailAddress, Session session){
        System.out.println("SendMailTLS.sendMails()");

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from-email@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(subscriberEmailAddress));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler,"
                    + "\n\n No spam to my email, please!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


}