module com.imp.envio_email {
    requires javafx.controls;
    requires javafx.fxml;
    requires mail;


    opens com.imp.envio_email to javafx.fxml;
    exports com.imp.envio_email;
    exports com.imp.envio_email.controller;
    opens com.imp.envio_email.controller to javafx.fxml;
}