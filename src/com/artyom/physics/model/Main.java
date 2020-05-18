package com.artyom.physics.model;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(new URL("http://213.200.50.96/main.fxml")/*getClass().getResource("sample.fxml")*/);
        stage.setTitle("Config Model");
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(new URL("http://213.200.50.96/main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
