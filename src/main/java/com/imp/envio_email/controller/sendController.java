package com.imp.envio_email.controller;

import com.imp.envio_email.objetos.email;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class sendController {
    @FXML
    public TextField correo = new TextField();
    @FXML
    public TextField asunto = new TextField();
    @FXML
    public TextArea contenido = new TextArea();
    @FXML
    Label errorMail = new Label();
    @FXML
    public void limpiarCampos() {
        this.correo.setText("");
        this.asunto.setText("");
        this.contenido.setText("");
        errorMail.setText("");
    }
    public void initialize() {
        errorMail.setTextFill(Color.RED);
        errorMail.setAlignment(Pos.CENTER);
        errorMail.setFont(new Font(16));
    }
    @FXML
    public void enviarCorreo() {
        Alert alerta;

        if (this.contenido.getText().isEmpty() && this.correo.getText().isEmpty()) {
            errorMail.setText("No se proporcionado correo y contenido");
        } else if(this.correo.getText().isEmpty()) {
            errorMail.setText("No se a proporcionado un correo");
        } else {
            errorMail.setText("");
            if (confirmarEnvio()) {
                if (this.asunto.getText().isEmpty()) {
                    email correo = new email(this.correo.getText(), this.contenido.getText());
                    if (correo.enviar()) {
                        alerta = new Alert(Alert.AlertType.CONFIRMATION);
                        alerta.setTitle("Correo enviado");
                        alerta.setHeaderText(null);
                        alerta.setContentText("El correo se ha enviado correctamente");
                        alerta.showAndWait();
                    } else {
                        alerta = new Alert(Alert.AlertType.ERROR);
                        alerta.setTitle("Error al enviar correo");
                        alerta.setHeaderText(null);
                        alerta.setContentText("Ha ocurrido un error al enviar el correo\n" +
                                "verifica si los datos proporcionados son correctos");
                        alerta.showAndWait();
                    }
                } else {
                    email correo = new email(this.correo.getText(), this.asunto.getText(), this.contenido.getText());
                    if (correo.enviar()) {
                        alerta = new Alert(Alert.AlertType.CONFIRMATION);
                        alerta.setTitle("Correo enviado");
                        alerta.setHeaderText(null);
                        alerta.setContentText("El correo se ha enviado correctamente");
                        alerta.showAndWait();
                    } else {
                        alerta = new Alert(Alert.AlertType.ERROR);
                        alerta.setTitle("Error al enviar correo");
                        alerta.setHeaderText(null);
                        alerta.setContentText("Ha ocurrido un error al enviar el correo\n" +
                                "verifica si los datos proporcionados son correctos");
                        alerta.showAndWait();
                    }
                }

            }
        }

    }
    private boolean confirmarEnvio() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Estas seguro que deseas enviar el correo?");

        return alert.showAndWait().get() == ButtonType.OK;
    }

}