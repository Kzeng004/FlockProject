/*
 * Model.java
 */

import java.util.ArrayList;
import java.lang.Thread;
import java.util.Random;

import javax.xml.stream.Location;

/**
 * Models a collection of boids roaming about impacting other boids.
 * @author Xeng Yang 
 * Template for code was provided by Amy Larson
 */
public class Model extends Thread {

    Random rand = new Random();

    /** List of all boids in the model */
    private ArrayList<Boid> boids = new ArrayList<>();
    public ArrayList<Boid> getBoids() {
        return boids;
    }

    /** Time in ms. "Frame rate" for redrawing the boids. */
    private int stepSize = 200;
    /** Current number of boids visible in the window. */
    private int count = 2;
    public void setCount(int boidCount) {
        // No fewer than 2 boids in the list; no more than 200 boids in the list
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
        for (int i=count; i<200; i++) {
            boids.get(i).hideBoid();
        }
    }

    /** Current speed of boids */
    private int speed = 1;
    public void setSpeed(int newSpeed) {
        // Speed is between 1 (slow) and 5 (fastest)
        // Low speed = high step size
        if (newSpeed < 1) {
            newSpeed = 1;
        } else if (newSpeed > 5) {
            newSpeed = 5;
        }
        stepSize = (6-newSpeed)*80; // 80 to 400ms
        speed = newSpeed;
    }

    /** Current size of boids */
    private int size = 0;
    public void setSize(int newSize) {
        // Size is between 10 (very small) and 40 (very big)
        if(newSize < 10){
            newSize = 10;
        } else if(newSize > 40){
            newSize = 40;
        }
        size = newSize;
        for (Boid b: boids) {
            b.setRadius(newSize);
        }
    }

    /** Pauses simulation so boids do not move */
    private boolean paused = true;

    /** Current "weight" of the model's average position rule */
    private int avgPosWeight = 1;
    public void setAvgPos(int posw){
        // Significance of average position rule is between 1 (insignificant) and 5 (very significant)
        if (posw < 1) {
            posw = 1;
        } else if (posw > 5) {
            posw = 5;
        }
        avgPosWeight = posw * 20;
    }

    /** Current "weight" of the model's average direction rule */
    private int avgDirWeight = 1;
    public void setAvgDir(int dirw){
        // Significance of average direction rule is between 1 (insignificant) and 5 (very significant)
        if (dirw < 1) {
            dirw = 1;
        } else if (dirw > 5) {
            dirw = 5;
        }
        avgDirWeight = dirw * 20;
    }

    /** Current "weight" of the model's separation rule */
    private int sepWeight = 1;
    public void setSep(int sepw){
        // Significance of separation rule is between 1 (insignificant) and 5 (very significant)
        if (sepw < 1) {
            sepw = 1;
        } else if (sepw > 5) {
            sepw = 5;
        }
        sepWeight = sepw * 20;
    }

    private SimulationGUI simulation;
    public void setSim(SimulationGUI sim) {
        simulation = sim;
    }
    /** Average position of all boids */
    private Point avgPosition;
    /** Average direction of all boids */
    private Point avgDirection;

    /** Default constructor. */
    public Model() {
        // All boids that might appear in the graphics window are created, but are not visible.
        for (int i=0; i<200; i++) {
            boids.add(new Boid());
        }
        avgPosition = new Point(0,0);
        avgDirection = new Point(0, 0);
        // Boids will not show up until Set Up button is pressed
        for (Boid b: boids){
            b.hideBoid();
        }
    }

    /** Run the model */
    @Override
    public void run() {
        // Forever run the simulation
        while(true) {
            // Move things only if the simulation is not paused
            if (!paused) {
                advanceBoids();
                // Calculate and set all vectors
                Point avgP = calcAvgPosition();
                Point avgD = calcAvgDirection();
                for (Boid b: boids){
                    Point sep = b.separation(boids);
                    b.setForce(avgP,avgPosWeight,avgD,avgDirWeight,sep,sepWeight);
                }
                // Paint the boids onto the model
                simulation.getContentPane().repaint();
                boids.get(0).repaint();
            }
            try {
                //Thread.sleep(stepSize);
                Thread.sleep(10);
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
            b.cohesion(boids);
            b.separation(boids);
            for (Boid b2: boids){
                b2.determineNeighbor(b);
                // Makes overlapping boids separate
                if(b.getLocation().equals(b2.getLocation())){
                    b.separation(boids);
                }
            }
            // Set the location, which prompts the viewer to newly display the boid
            b.setLocation((int) b.getXY().x,(int) b.getXY().y);
        }
    }

    /**
     * Calculates the average position of all boids
     * @return Position vector with average position of all boids
     */
    public Point calcAvgPosition(){
        int posxCount = 0;
        double posx = 0;
        int posyCount = 0;
        double posy = 0;
        //Calculate the sum of all x and y positions
        for (Boid b: boids) {
            posx = (posx + b.getXY().x);
            posxCount += 1;
            posy = posy + b.getXY().y; 
            posyCount += 1;
        }
        //Divide sum of positions by number of boids
        posx = posx/posxCount;
        posy = posy/posyCount;
        Point posVec = new Point(posx,posy);
        posVec.normalize();
        //Put results into position vector
        avgPosition.add(posVec);
        avgPosition.limit(1);
        return avgPosition;
    }

    /**
     * Calculates the average direction of all boids
     * @return Direction vector with average direction of all boids
     */
    public Point calcAvgDirection(){
        int dirxCount = 0;
        double dirx = 0;
        int diryCount = 0;
        double diry = 0;
        //Calculate the sum of all x and y directions
        for(int i =0; i< boids.size(); i++){
            dirx = (dirx + boids.get(i).getDirection().x);
            dirxCount += 1;
            diry = (diry + boids.get(i).getDirection().y);
            diryCount += 1;
        }
        //Divide sum of directions by number of boids
        dirx = dirx/dirxCount;
        diry = diry/diryCount;
        //Put results into direction vector
        Point dirVec = new Point(dirx,diry);
        dirVec.normalize();
        avgDirection.add(dirVec);
        avgDirection.limit(1);
        return avgDirection;
    }
}

