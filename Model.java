/*
 * Circle Model.java
 */

import java.util.ArrayList;
import java.lang.Thread;

/**
 * Models a collection of circles roaming about impacting other circles.
 * @author Xeng Yang 
 * Template for code was provided by Amy Larson
 */
public class Model extends Thread {

    private ArrayList<Boid> boids = new ArrayList<>();

    /** Time in ms. "Frame rate" for redrawing the circles. */
    private int stepSize = 200;
    /** Current number of circles visible in the window. */
    private int count = 0;
    /** Pauses simulation so circles do not move */
    private boolean paused = true;

    private int size = 0;
    private int avgPosPrecedence = 1;
    private int avgDirPrecedence = 1;
    private int moveAwayPrecedence = 1;
    private SimulationGUI simulation;

    /** Default constructor. */
    public Model() {
        // All circels that might appear in the graphics window are created, but are not visible.
        for (int i=0; i<200; i++) {
            boids.add(new Boid());
        }
    }

    public void setSim(SimulationGUI sim) {
        simulation = sim;
    }

    @Override
    public void run() {
        // Forever run the simulation
        while(true) {
            // Move things only if the simulation is not paused
            //If circles are overlapping, change the color of the circles and then change the coordinate of both circles
            if (!paused) {
                advanceBoids();
                simulation.getContentPane().repaint();
                /* 
                for(int i = 4; i < boids.size()-4; i++){
                    if(boids.get(i).overlaps(boids.get(i-1)) == true){
                        boids.get(i).randomXY();
                        boids.get(i-1).randomXY();
                        boids.get(i).randomColor();
                        boids.get(i-1).randomColor();
                    }
                    else if(boids.get(i).overlaps(boids.get(i+1)) == true){
                        boids.get(i).randomXY();
                        boids.get(i+1).randomXY();
                        boids.get(i).randomColor();
                        boids.get(i+1).randomColor();
                    }
                    if(boids.get(i).overlaps(boids.get(i-2)) == true){
                        boids.get(i).randomXY();
                        boids.get(i-2).randomXY();
                        boids.get(i).randomColor();
                        boids.get(i-2).randomColor();
                    }
                    else if(circles.get(i).overlaps(circles.get(i+2)) == true){
                        circles.get(i).randomXY();
                        circles.get(i+2).randomXY();
                        circles.get(i).randomColor();
                        circles.get(i+2).randomColor();
                    }
                    if(circles.get(i).overlaps(circles.get(i-3)) == true){
                        circles.get(i).randomXY();
                        circles.get(i-3).randomXY();
                        circles.get(i).randomColor();
                        circles.get(i-3).randomColor();
                    }
                    else if(circles.get(i).overlaps(circles.get(i+3)) == true){
                        circles.get(i).randomXY();
                        circles.get(i+3).randomXY();
                        circles.get(i).randomColor();
                        circles.get(i+3).randomColor();
                    }
                    if(circles.get(i).overlaps(circles.get(i-4)) == true){
                        circles.get(i).randomXY();
                        circles.get(i-4).randomXY();
                        circles.get(i).randomColor();
                        circles.get(i-4).randomColor();
                    }
                    else if(circles.get(i).overlaps(circles.get(i+4)) == true){
                        circles.get(i).randomXY();
                        circles.get(i+4).randomXY();
                        circles.get(i).randomColor();
                        circles.get(i+4).randomColor();
                    } */
                    
                
            }
            try {
                Thread.sleep(stepSize);
            } catch (Exception e) {

            }
        }
    }

    /** Pause the simulation - circles freeze. */
    public void pause() {
        paused = true;
    }

    /** Circles move again */
    public void play() {
        System.out.println("Playing now");
        paused = false;

    }

    /** Move circles to next location */
    public void advanceBoids() {
        for (int i=0; i<count; i++) {
            // Advance each circle
            boids.get(i).step();
            // Set the location, which prompts the viewer to newly display the circle
            boids.get(i).setLocation(boids.get(i).getXY().x, boids.get(i).getXY().y);
        }
    }


    public ArrayList<Boid> getBoids() {
        return boids;
    }

    /** Reset circles */
    public void setCount(int boidCount) {
        System.out.println("Making circles!");
        // Must be in bounds. Only 20 circles in the list.
        if (boidCount < 2) {
            boidCount = 2;
        } else if (boidCount > 200) {
            boidCount = 200;
        }
        // Reset "count" circles, making them visible
        count = boidCount;
        for (int i=0; i<count; i++) {
            boids.get(i).reset();
        }
        // Hide the rest
        for (int i=count; i<20; i++) {
            boids.get(i).hideBoid();
        }
    }

    /** Set speed of simulation from 1 (slow) to 5 (fast) */
    public void setSpeed(int newSpeed) {
        // speed is between 1 (slow) and 5 (fastest)
        // low speed = high step size
        if (newSpeed < 1) {
            newSpeed = 1;
        } else if (newSpeed > 5) {
            newSpeed = 5;
        }
        stepSize = (6-newSpeed)*80; // 80 to 400ms
    }
    /**
     * Set the shape of the circles
     * @param newShape the amount that will be cut from the circle
     */

    public void setShape(int newShape) {
        if(newShape < 10){
            newShape = 10;
        } else if(newShape > 40){
            newShape = 40;
        }
        size = newShape;
        for (int i=0; i<boids.size(); i++) {
            boids.get(i).setSize(size, size);
        }

    }
    public double calcAvgPosition(){
        double pos = 0;
        return pos;
    }
    public double calcAvgDirection(){
        double dir = 0;
        return dir;
    }
    public void setRule1(int rule1){
        if (rule1 < 1) {
            rule1 = 1;
        } else if (rule1 > 5) {
            rule1 = 5;
        }
    }
    public void setRule2(int rule2){
        if (rule2 < 1) {
            rule2 = 1;
        } else if (rule2 > 5) {
            rule2 = 5;
        }
    }
    public void setRule3(int rule3){
        if (rule3 < 1) {
            rule3 = 1;
        } else if (rule3 > 5) {
            rule3 = 5;
        }
    }
}

