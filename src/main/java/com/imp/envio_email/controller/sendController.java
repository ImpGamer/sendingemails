package com.imp.envio_email.controller;

import com.imp.envio_email.objetos.email;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

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
    ImageView archivos = new ImageView();
    email correoFormado = new email();
    @FXML
    public Label lblArchivos = new Label();
    @FXML
    public void limpiarCampos() {
        this.correo.setText("");
        this.asunto.setText("");
        this.contenido.setText("");
        errorMail.setText("");
        this.lblArchivos.setText("");
        correoFormado.getArchivosAdjuntos().clear();
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
                correoFormado.setCorreo(this.correo.getText());
                correoFormado.setAsunto(this.asunto.getText());
                correoFormado.setContenido(this.contenido.getText());
                correoFormado.crearCorreo();

                if(correoFormado.enviar()) {
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
    @FXML
    public void agregarArchivo() {
        this.lblArchivos.setText("");
        FileChooser fileChooser = new FileChooser();

        // Agregar un filtro para permitir solo archivos
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos", "*.*");
        fileChooser.getExtensionFilters().add(extFilter);

        Stage stage = (Stage) archivos.getScene().getWindow();
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);

        for(File archivos:selectedFiles) {
            System.out.println(archivos.getAbsolutePath());
        }
        lblArchivos.setText(correoFormado.addFiles(selectedFiles));
    }
    @FXML
    public void titleArchivo() {
        Tooltip title = new Tooltip("Agregar Archivo");
        Tooltip.install(archivos,title);
    }
    private boolean confirmarEnvio() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Estas seguro que deseas enviar el correo?");

        return alert.showAndWait().get() == ButtonType.OK;
    }

}