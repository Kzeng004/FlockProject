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
public class CircleModel extends Thread {

    private ArrayList<Circle> circles = new ArrayList<>();

    /** Time in ms. "Frame rate" for redrawing the circles. */
    private int stepSize = 200;
    /** Current number of circles visible in the window. */
    private int count = 0;
    /** Pauses simulation so circles do not move */
    private boolean paused = true;

    private int size = 0;

    private SimulationGUI simulation;

    /** Default constructor. */
    public CircleModel() {
        // All circels that might appear in the graphics window are created, but are not visible.
        for (int i=0; i<200; i++) {
            circles.add(new Circle());
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
                advanceCircles();
                simulation.getContentPane().repaint();
                for(int i = 4; i < circles.size()-4; i++){
                    if(circles.get(i).overlaps(circles.get(i-1)) == true){
                        circles.get(i).randomXY();
                        circles.get(i-1).randomXY();
                        circles.get(i).randomColor();
                        circles.get(i-1).randomColor();
                    }
                    else if(circles.get(i).overlaps(circles.get(i+1)) == true){
                        circles.get(i).randomXY();
                        circles.get(i+1).randomXY();
                        circles.get(i).randomColor();
                        circles.get(i+1).randomColor();
                    }
                    if(circles.get(i).overlaps(circles.get(i-2)) == true){
                        circles.get(i).randomXY();
                        circles.get(i-2).randomXY();
                        circles.get(i).randomColor();
                        circles.get(i-2).randomColor();
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
                    }
                    
                }
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
    public void advanceCircles() {
        for (int i=0; i<count; i++) {
            // Advance each circle
            circles.get(i).step();
            // Set the location, which prompts the viewer to newly display the circle
            circles.get(i).setLocation(circles.get(i).getXY().x, circles.get(i).getXY().y);
        }
    }


    public ArrayList<Circle> getCircles() {
        return circles;
    }

    /** Reset circles */
    public void setCount(int circleCount) {
        System.out.println("Making circles!");
        // Must be in bounds. Only 20 circles in the list.
        if (circleCount < 2) {
            circleCount = 2;
        } else if (circleCount > 200) {
            circleCount = 200;
        }
        // Reset "count" circles, making them visible
        count = circleCount;
        for (int i=0; i<count; i++) {
            circles.get(i).reset();
        }
        // Hide the rest
        for (int i=count; i<20; i++) {
            circles.get(i).hideCircle();
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
        for (int i=0; i<circles.size(); i++) {
            circles.get(i).setSize(size, size);
        }

    }


}
