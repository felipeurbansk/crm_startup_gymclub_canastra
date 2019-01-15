/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



/**
 *
 * @author Urbanski
 */
public class MailCanastra {
    
    Properties props = new Properties();
            
    public void enviaEmail(String destintario,String assunto, String mensagem){
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() 
                {
                    return new PasswordAuthentication("canastracontato@gmail.com", "Canastra505050*b");
                }
            });
            
        session.setDebug(true);
            
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("canastracontato@gmail.com"));

            Address[] toUser = InternetAddress.parse(destintario);  
            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject(assunto);
            message.setText(mensagem);
                  
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

