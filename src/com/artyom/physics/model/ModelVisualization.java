package com.artyom.physics.model;

import javafx.util.Pair;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ModelVisualization extends PApplet {
    public static ModelVisualization INSTANCE;
    public static PApplet processing;
    private final ModelConfig config;
    List<Pair<Integer, Integer>> pairs = new ArrayList<>();


    public ModelVisualization(ModelConfig config){
        this.config = config;
        String[] arg = {"com.artyom.physics.model.ModelVisualization"};
        PApplet.runSketch(arg, this);
        INSTANCE = this;
    }

    @Override
    public void settings() {
        size(config.getWidth(), config.getHeight());
        frameRate = 300;
        frameRate(frameRate);
    }

    List<Point> points;

    public void setup(){
        points = new ArrayList<>();
        if(config.isRandom_pos()) {
            for (int i = 0; i < config.getPoints_count(); i++) {
                points.add(new Point(random(0, width / 2.0f), random(0, height), new PVector(config.getVx(), config.getVy()), 2.5f, config.getBoundaryType()));
            }
        }else {
            float k = 0;
            for (int i = 0; i < floor(sqrt(config.getPoints_count())); i++) {
                for (int j = 0; j < floor(sqrt(config.getPoints_count())); j++) {
                    k++;
                    System.out.printf("%.2f\n",k/config.getPoints_count()*100);
                    points.add(new Point(i * 10 + width / 2.0f - (float) floor(sqrt(config.getPoints_count())) / 2.0f, j * 10 + width / 2.0f - (float) floor(sqrt(config.getPoints_count())) / 2.0f, new PVector(config.getVx(), config.getVy()), 2.5f, config.getBoundaryType()));
                }
            }
        }

        List<Pair<Integer, Integer>> pairs = new ArrayList<>();
        for(int i = 0; i < config.getPoints_count(); i++){
            for(int j = 0; j < config.getPoints_count(); j++){
                if(i!=j) pairs.add(new Pair<>(i, j));
            }
        }
        //int comb = utils.combination(config.getPoints_count(), 2);

        for(int i = 0; i < pairs.size(); i++){
            Pair<Integer, Integer> t = pairs.get(i);
            t = new Pair<>(t.getValue(), t.getKey());
            for(int j = 0; j < pairs.size();j++){
                if (pairs.get(j).equals(t)){
                    pairs.remove(j);
                    break;
                }
            }
        }
        System.out.println(pairs);
        this.pairs = pairs;

        processing = this;
    }

    public void draw(){
        background(51);
        config.getBoundaryType().drawBounds();
        for (Point p : points) {
            p.update();
            p.display();
            p.checkBoundaryCollision();
        }
        pairs.forEach(p -> points.get(p.getKey()).checkCollision(points.get(p.getValue())));
    }

    public List<Point> getPoints() {
        return points;
    }
}
