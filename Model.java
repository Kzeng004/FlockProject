/*
 * Model.java
 */

import java.util.ArrayList;
import java.lang.Thread;
import java.util.Vector;

/**
 * Models a collection of boids roaming about impacting other boids.
 * @author Xeng Yang 
 * Template for code was provided by Amy Larson
 */
public class Model extends Thread {

    private ArrayList<Boid> boids = new ArrayList<>();

    /** Time in ms. "Frame rate" for redrawing the boids. */
    private int stepSize = 200;
    /** Current number of boids visible in the window. */
    private int count = 2;
    /** Current speed of boids */
    private int speed = 1;
    /** Pauses simulation so boids do not move */
    private boolean paused = true;
    private int size = 0;
    private int avgPosPrecedence = 1;
    private int avgDirPrecedence = 1;
    private int moveAwayPrecedence = 1;
    private SimulationGUI simulation;
    private Vector position = new Vector();
    private Vector direction = new Vector();

    /** Default constructor. */
    public Model() {
        // All boids that might appear in the graphics window are created, but are not visible.
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
            //If boids are overlapping, change the color of the boids and then change the coordinate of both boids
            if (!paused) {
                advanceBoids();
                calcAvgDirection();
                calcAvgPosition();
                simulation.getContentPane().repaint();  
            }
            try {
                Thread.sleep(stepSize);
            } catch (Exception e) {

            }
        }
    }

    /** Pause the simulation - boids freeze. */
    public void pause() {
        System.out.println("Paused now");
        paused = true;
    }

    /** Boids move again */
    public void play() {
        System.out.println("Playing now");
        paused = false;

    }

    /** Move boids to next location */
    public void advanceBoids() {
        for (Boid b: boids) {
            // Advance each boid
            b.step();
            // Set the location, which prompts the viewer to newly display the boid
            b.setLocation(b.getPoints().get(0).x,b.getPoints().get(0).y);
            // If any boid hits the side of the bounding box, make it warp to the other side
            if (b.getX() < 50){
                b.setLocation(650,b.getPoints().get(0).y);
            }else if (b.getX() > 650){
                b.setLocation(50,b.getPoints().get(0).y);
            }
        }
    }


    public ArrayList<Boid> getBoids() {
        return boids;
    }

    /** Reset boids */
    public void setCount(int boidCount) {
        System.out.println("Making boids!");
        // Must be in bounds. Only 20 boids in the list.
        if (boidCount < 2) {
            boidCount = 2;
        } else if (boidCount > 200) {
            boidCount = 200;
        }
        // Reset "count" boids, making them visible
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
     * Set the shape of the boids
     * @param newShape the amount that will be cut from the boid
     */

    public void setShape(int newShape) {
        if(newShape < 10){
            newShape = 10;
        } else if(newShape > 40){
            newShape = 40;
        }
        size = newShape;
        for (int i=0; i<boids.size(); i++) {
            boids.get(i).setArea(boids.get(i).getHeight(),boids.get(i).getWidth());
        }

    }
    //Calculate the average position by taking the sum of all of the x position and the y position from the boids and dividing it
    //Put results into position vector.
    public Vector calcAvgPosition(){
        int posxCount = 0;
        int posx = 0;
        int posyCount = 0;
        int posy = 0;
        for (int i=0; i<boids.size(); i++) {
            posx = (posx + boids.get(i).getCenter().x);
            posxCount += 1;
            posy = posy + boids.get(i).getCenter().y; 
            posyCount += 1;
        }
        posx = posx/posxCount;
        posy = posy/posyCount;
        position.add(posx);
        position.add(posy);
        return position;
    }

    //Calculate the average direction by taking the sum of all of the x direction and the y direction from the boids and dividing it
    //Put results into direction vector.
    public Vector calcAvgDirection(){
        int dirxCount = 0;
        int dirx = 0;
        int diryCount = 0;
        int diry = 0;
        for(int i =0; i< boids.size(); i++){
            dirx = (dirx + boids.get(i).getDirection().x);
            dirxCount += 1;
            diry = (diry + boids.get(i).getDirection().y);
            diryCount += 1;
        }
        dirx = dirx/dirxCount;
        diry = diry/diryCount;
        direction.add(dirx);
        direction.add(diry);
        return direction;
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

