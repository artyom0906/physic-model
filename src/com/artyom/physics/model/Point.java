package com.artyom.physics.model;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.Random;

public class Point {
    PVector position;
    PVector velocity;

    float radius, m;
    int color;
    BoundaryType collisionType;

    public Point(float x, float y, PVector velocity, float r, BoundaryType collisionType) {
        this.collisionType = collisionType;
        this.position = new PVector(x, y);
        this.velocity = velocity;//PVector.random2D();
        this.velocity.mult(5);
        this.radius = r;
        this.m = (float) (radius*.1);
        color = new Random().nextInt();
    }
    public Point(float x, float y, float vx, float vy, float r, BoundaryType collisionType) {
        this(x, y, new PVector(vx, vy), r, collisionType);
    }
    public void display(){
        ModelVisualization.processing.noStroke();
        ModelVisualization.processing.fill(color%256);
        ModelVisualization.processing.ellipse(position.x, position.y, radius*2, radius*2);
    }

    void update() {
        position.add(velocity);
    }

    void checkBoundaryCollision() {
        collisionType.checkCollision(this);
        /*if (position.x > ModelVisualization.processing.width-radius) {
            position.x = ModelVisualization.processing.width-radius;
            velocity.x *= -1;
        } else if (position.x < radius) {
            position.x = radius;
            velocity.x *= -1;
        } else if (position.y > ModelVisualization.processing.height-radius) {
            position.y = ModelVisualization.processing.height-radius;
            velocity.y *= -1;
        } else if (position.y < radius) {
            position.y = radius;
            velocity.y *= -1;
        }*/
    }
   /* void checkBoundaryCollisionC(){
        float x1 = this.position.x;
        float y1 = this.position.y;
        float Vx = -this.velocity.x;
        float Vy = -this.velocity.y;
        float x = 200, y = 200, R = 100;
        float RO = R;
        if  (Math.hypot(x1-x, y1-y) >= R) {
            float V = (float) Math.hypot(Vx, Vy);
            float cost = (Vx * (x - x1) + Vy * (y - y1)) / (RO * V);
            if(Math.abs(cost-1)<0.1){
                velocity.y *= -1;
                velocity.x *= -1;
            }
            float cos2t = ModelVisualization.processing.cos(2 * ModelVisualization.processing.acos(cost));
            float sin2t = ModelVisualization.processing.sqrt(1 - (cos2t * cos2t));
            this.velocity.x = Vx * cos2t + Vy * sin2t;
            this.velocity.y = Vy * cos2t - Vx * sin2t;
        }
    }*/

    void checkCollision(Point other) {

        // Get distances between the balls components
        PVector distanceVect = PVector.sub(other.position, position);

        // Calculate magnitude of the vector separating the balls
        float distanceVectMag = distanceVect.mag();

        // Minimum distance before they are touching
        float minDistance = radius + other.radius;

        if (distanceVectMag < minDistance) {
            float distanceCorrection = (float) ((minDistance - distanceVectMag) / 2.0);
            PVector d = distanceVect.copy();
            PVector correctionVector = d.normalize().mult(distanceCorrection);
            other.position.add(correctionVector);
            position.sub(correctionVector);

            // get angle of distanceVect
            float theta = distanceVect.heading();
            // precalculate trig values
            float sine = PApplet.sin(theta);
            float cosine = PApplet.cos(theta);

              /* bTemp will hold rotated ball positions. You
               just need to worry about bTemp[1] position*/
            PVector[] bTemp = {
                    new PVector(), new PVector()
            };

              /* this ball's position is relative to the other
               so you can use the vector between them (bVect) as the
               reference point in the rotation expressions.
               bTemp[0].position.x and bTemp[0].position.y will initialize
               automatically to 0.0, which is what you want
               since b[1] will rotate around b[0] */
            bTemp[1].x = cosine * distanceVect.x + sine * distanceVect.y;
            bTemp[1].y = cosine * distanceVect.y - sine * distanceVect.x;

            // rotate Temporary velocities
            PVector[] vTemp = {
                    new PVector(), new PVector()
            };

            vTemp[0].x = cosine * velocity.x + sine * velocity.y;
            vTemp[0].y = cosine * velocity.y - sine * velocity.x;
            vTemp[1].x = cosine * other.velocity.x + sine * other.velocity.y;
            vTemp[1].y = cosine * other.velocity.y - sine * other.velocity.x;

              /* Now that velocities are rotated, you can use 1D
               conservation of momentum equations to calculate
               the final velocity along the x-axis. */
            PVector[] vFinal = {
                    new PVector(), new PVector()
            };

            // final rotated velocity for b[0]
            vFinal[0].x = ((m - other.m) * vTemp[0].x + 2 * other.m * vTemp[1].x) / (m + other.m);
            vFinal[0].y = vTemp[0].y;

            // final rotated velocity for b[0]
            vFinal[1].x = ((other.m - m) * vTemp[1].x + 2 * m * vTemp[0].x) / (m + other.m);
            vFinal[1].y = vTemp[1].y;

            // hack to avoid clumping
            bTemp[0].x += vFinal[0].x;
            bTemp[1].x += vFinal[1].x;

              /* Rotate ball positions and velocities back
               Reverse signs in trig expressions to rotate
               in the opposite direction */
            // rotate balls
            PVector[] bFinal = {
                    new PVector(), new PVector()
            };

            bFinal[0].x = cosine * bTemp[0].x - sine * bTemp[0].y;
            bFinal[0].y = cosine * bTemp[0].y + sine * bTemp[0].x;
            bFinal[1].x = cosine * bTemp[1].x - sine * bTemp[1].y;
            bFinal[1].y = cosine * bTemp[1].y + sine * bTemp[1].x;

            // update balls to screen position
            other.position.x = position.x + bFinal[1].x;
            other.position.y = position.y + bFinal[1].y;

            position.add(bFinal[0]);

            // update velocities
            velocity.x = cosine * vFinal[0].x - sine * vFinal[0].y;
            velocity.y = cosine * vFinal[0].y + sine * vFinal[0].x;
            other.velocity.x = cosine * vFinal[1].x - sine * vFinal[1].y;
            other.velocity.y = cosine * vFinal[1].y + sine * vFinal[1].x;
        }
    }
}
