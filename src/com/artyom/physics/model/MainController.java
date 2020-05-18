package com.artyom.physics.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private CheckBox stats;

    @FXML
    private Button start = new Button();

    @FXML
    private TextField width_f;

    @FXML
    private TextField height_f;

    @FXML
    private TextField point_count_f;

    @FXML
    private TextField vx_f;

    @FXML
    private TextField vy_f;

    @FXML
    private CheckBox r_pos;

    @FXML
    private ChoiceBox<String> b_type;

    int width;
    int height;
    int point_count;
    float vx;
    float vy;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(BoundaryType type: BoundaryType.values()) {
            b_type.getItems().add(type.name());
        }
        b_type.setValue(b_type.getItems().get(0));

        System.out.println("start");
        start.setOnAction(event -> {
            System.out.println(123);
            System.out.println("start");
            width = Integer.parseInt(width_f.getText());
            height = Integer.parseInt(height_f.getText());
            point_count = Integer.parseInt(point_count_f.getText());
            vx = Float.parseFloat(vx_f.getText());
            vy = Float.parseFloat(vy_f.getText());

            ModelConfig config = new ModelConfig();
            config.setWidth(width);
            config.setHeight(height);
            config.setPoints_count(point_count);
            config.setVx(vx);
            config.setVy(vy);
            config.setRandom_pos(r_pos.isSelected());
            config.setBoundaryType(BoundaryType.valueOf(b_type.getValue()));
            //config.setBoundaryType(BoundaryType.CIRCLE);
            new ModelVisualization(config);

            if(stats.isSelected()) {
                try {
                    Parent root = FXMLLoader.load(new URL("http://213.200.50.96/stats.fxml")/*getClass().getResource("sample.fxml")*/);
                    Stage stage = new Stage();
                    stage.setTitle("speed statistics");
                    stage.setScene(new Scene(root, 600, 400));
                    stage.show();
                } catch (Exception ignored) {

                }
            }
        });
    }
}
