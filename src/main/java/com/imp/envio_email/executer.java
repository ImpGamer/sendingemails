package com.imp.envio_email;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class executer extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Image image = new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/8/8c/Gmail_Icon_%282013-2020%29.svg/1280px-Gmail_Icon_%282013-2020%29.svg.png");
        FXMLLoader fxmlLoader = new FXMLLoader(executer.class.getResource("send.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 530, 740);
        stage.getIcons().add(image);
        stage.setTitle("Envio de Email's");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}