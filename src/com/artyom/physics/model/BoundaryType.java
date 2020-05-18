package com.artyom.physics.model;

import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

public enum BoundaryType {
    SQUARE {
        public void checkCollision(Point point) {
            if (point.position.x > ModelVisualization.processing.width - point.radius) {
                point.position.x = ModelVisualization.processing.width - point.radius;
                point.velocity.x *= -1;
            } else if (point.position.x < point.radius) {
                point.position.x = point.radius;
                point.velocity.x *= -1;
            } else if (point.position.y > ModelVisualization.processing.height - point.radius) {
                point.position.y = ModelVisualization.processing.height - point.radius;
                point.velocity.y *= -1;
            } else if (point.position.y < point.radius) {
                point.position.y = point.radius;
                point.velocity.y *= -1;
            }
        }

        @Override
        public void drawBounds() {

        }
    },
    CIRCLE {
        public void checkCollision(Point point) {
            float x1 = point.position.x;
            float y1 = point.position.y;
            float Vx = -point.velocity.x;
            float Vy = -point.velocity.y;
            float x = ModelVisualization.processing.width / 2.0f;
            float y = ModelVisualization.processing.height / 2.0f;
            float R = PApplet.min(x, y);
            if (Math.hypot(x1 - x, y1 - y) >= R) {
                float V = (float) Math.hypot(Vx, Vy);
                float cost = (Vx * (x - x1) + Vy * (y - y1)) / (R * V);
                //float A = PApplet.atan(point.position.y/point.position.x) - PApplet.PI;
                //point.position.x = x + PApplet.sin(A) * R;
                //point.position.y = y + PApplet.cos(A) * R;
                if (Math.abs(cost - 1) < 0.1) {
                    point.velocity.y *= -1;
                    point.velocity.x *= -1;
                    return;
                }
                float cos2t = PApplet.cos(2 * PApplet.acos(cost));
                float sin2t = PApplet.sqrt(1 - (cos2t * cos2t));
                point.velocity.x = Vx * cos2t + Vy * sin2t;
                point.velocity.y = Vy * cos2t - Vx * sin2t;
            }
        }

        @Override
        public void drawBounds() {
            float x = ModelVisualization.processing.width / 2.0f;
            float y = ModelVisualization.processing.height / 2.0f;
            float r = PApplet.min(x, y);
            ModelVisualization.processing.fill(Color.RED.getRGB());
            ModelVisualization.processing.ellipse(x, y, r*2, r*2);
            ModelVisualization.processing.fill(Color.GREEN.getRGB());
            ModelVisualization.processing.ellipse(x, y, 5, 5);
        }
    };

    public abstract void checkCollision(Point point);
    public abstract void drawBounds();
}
