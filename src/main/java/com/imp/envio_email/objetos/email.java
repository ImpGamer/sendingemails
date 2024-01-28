package com.imp.envio_email.objetos;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
    private final List<File> archivosAdjuntos;
    private String nombres_Archivos;
    public email() {
        this.nombres_Archivos="";
        archivosAdjuntos = new ArrayList<>();
    }
    public void crearCorreo() {
        mProperties = new Properties();

        mProperties.put("mail.smtp.host","smtp.gmail.com");
        mProperties.put("mail.smtp.ssl.trust","smtp.gmail.com");
        mProperties.setProperty("mail.smtp.starttls.enable","true");
        mProperties.setProperty("mail.smtp.port","587");
        mProperties.setProperty("mail.smtp.user",emailFrom);
        mProperties.setProperty("mail.smtp.ssl.protocols","TLSv1.2");
        mProperties.setProperty("mail.smtp.auth","true");

        session = Session.getDefaultInstance(mProperties);

        try {
            MimeMultipart mElementos = new MimeMultipart();
            //Contenido del correo
            MimeBodyPart mContenido = new MimeBodyPart();
            mContenido.setContent(contenido,"text/html; charset=utf-8");
            mElementos.addBodyPart(mContenido);

            //Agregar los archivos adjuntos del usuario
            MimeBodyPart mAdjuntos;

            for (File archivo:archivosAdjuntos) {
                mAdjuntos = new MimeBodyPart();
                mAdjuntos.setDataHandler(new DataHandler(new FileDataSource(archivo.getAbsolutePath())));
                mAdjuntos.setFileName(archivo.getName());
                mElementos.addBodyPart(mAdjuntos);
            }

            mCorreo = new MimeMessage(session);
            mCorreo.setFrom(new InternetAddress(emailFrom));
            mCorreo.setRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(correo)));
            mCorreo.setSubject(asunto);
            mCorreo.setContent(mElementos);

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
    public String addFiles(List<File> listaArchivos) {
        nombres_Archivos ="";
        archivosAdjuntos.addAll(listaArchivos);

        for(File archivos:archivosAdjuntos) {
            nombres_Archivos += archivos.getName() +"\n";
        }
        return nombres_Archivos;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public List<File> getArchivosAdjuntos() {
        return archivosAdjuntos;
    }
}