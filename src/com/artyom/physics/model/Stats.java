package com.artyom.physics.model;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Stats implements Initializable {
    @FXML
    private BarChart<String, Number> barChart;
    private CategoryAxis xAxis = new CategoryAxis();

    private NumberAxis yAxis = new NumberAxis();
    private ModelVisualization model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = ModelVisualization.INSTANCE;
        new Thread(() -> {
            while (!Thread.interrupted()) {
                XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
                dataSeries.setName("");
                Map<Integer, Double> statistic = new HashMap<>();
                for (int i = 0; i < 100; i++)
                    statistic.put(i, 1.0 - 1.0);

                model.getPoints().forEach(point -> {
                    double speed = Math.hypot(point.velocity.x, point.velocity.y) * 3;
                    int id = (int) (Math.floor(speed));
                    try {
                        statistic.put(id, (statistic.get(id) + 1.0));
                    } catch (Exception ignored) {
                    }
                });

                dataSeries.getData().clear();
                for (int i = 0; i < 100; i++) {
                    double t1 = i / 3.0d;
                    double t2 = ((i + 1) / 3.0d);
                    dataSeries.getData().add(new XYChart.Data<>(String.format("%.1f - %.1f", t1, t2), statistic.get(i)));
                }

                Platform.runLater(() -> {
                    barChart.getData().clear();
                    barChart.getData().add(dataSeries);
                });

                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
