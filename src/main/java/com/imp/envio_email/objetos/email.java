package com.imp.envio_email.objetos;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class email {
    private static final String emailFrom = ""; //Correo electronico quien envia el email
    /*
    * Contraseña del correo proporcionado
    * Se recomienda que se genere una contraseña de aplicacion o temporal
    * para no pasar datos completos de la cuenta.
     */
    private static final String passwordFrom= "";
    private String correo;
    private String asunto;
    private String contenido;
    private Properties mProperties;
    private Session session;
    private MimeMessage mCorreo;

    public email(String correo,String asunto,String contenido) {
        mProperties = new Properties();
        this.correo = correo;
        this.asunto = asunto;
        this.contenido = contenido;

        mProperties.put("mail.smtp.host","smtp.gmail.com");
        mProperties.put("mail.smtp.ssl.trust","smtp.gmail.com");
        mProperties.setProperty("mail.smtp.starttls.enable","true");
        mProperties.setProperty("mail.smtp.port","587");
        mProperties.setProperty("mail.smtp.user",emailFrom);
        mProperties.setProperty("mail.smtp.ssl.protocols","TLSv1.2");
        mProperties.setProperty("mail.smtp.auth","true");

        session = Session.getDefaultInstance(mProperties);

        try {
            mCorreo = new MimeMessage(session);
            mCorreo.setFrom(new InternetAddress(emailFrom));
            mCorreo.setRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(correo)));
            mCorreo.setSubject(asunto);
            mCorreo.setText(contenido, "ISO-8859-1","html");

        }catch (MessagingException e) {
            System.err.println("Error al tratar de instanciar el correo");
            System.err.println(e.getMessage());
        }
    }
    public email(String correo,String contenido) {
        mProperties = new Properties();
        this.correo = correo;
        this.contenido = contenido;

        mProperties.put("mail.smtp.host","smtp.gmail.com");
        mProperties.put("mail.smtp.ssl.trust","smtp.gmail.com");
        mProperties.setProperty("mail.smtp.starttls.enable","true");
        mProperties.setProperty("mail.smtp.port","587");
        mProperties.setProperty("mail.smtp.user",emailFrom);
        mProperties.setProperty("mail.smtp.ssl.protocols","TLSv1.2");
        mProperties.setProperty("mail.smtp.auth","true");

        session = Session.getDefaultInstance(mProperties);

        try {
            mCorreo = new MimeMessage(session);
            mCorreo.setFrom(new InternetAddress(emailFrom));
            mCorreo.setRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(correo)));
            mCorreo.setSubject(asunto);
            mCorreo.setText(contenido, "ISO-8859-1","html");

        }catch (MessagingException e) {
            System.err.println("Error al tratar de instanciar el correo");
            System.err.println(e.getMessage());
        }
    }
    public boolean enviar() {
        try {
            Transport mTransport = session.getTransport("smtp");
            mTransport.connect(emailFrom,passwordFrom);
            mTransport.sendMessage(mCorreo,mCorreo.getRecipients(Message.RecipientType.TO));
            mTransport.close();
            return true;

        }catch (MessagingException e) {
            System.err.println("Error al tratar de enviar el correo\n"
            +"verifique si la informacion proporcianda es correcta");
            System.err.println(e.getMessage());
            return false;
        }
    }
}