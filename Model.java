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
        // Must be in bounds. Only 200 boids in the list.
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
        // speed is between 1 (slow) and 5 (fastest)
        // low speed = high step size
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
    public void setAvgPos(int rule1){
        if (rule1 < 1) {
            rule1 = 1;
        } else if (rule1 > 5) {
            rule1 = 5;
        }
        avgPosWeight = rule1 * 20;
    }

    /** Current "weight" of the model's average direction rule */
    private int avgDirWeight = 1;
    public void setAvgDir(int rule2){
        if (rule2 < 1) {
            rule2 = 1;
        } else if (rule2 > 5) {
            rule2 = 5;
        }
        avgDirWeight = rule2 * 20;
    }

    /** Current "weight" of the model's separation rule */
    private int sepWeight = 1;
    public void setSep(int rule3){
        if (rule3 < 1) {
            rule3 = 1;
        } else if (rule3 > 5) {
            rule3 = 5;
        }
        sepWeight = rule3 * 20;
    }

    private SimulationGUI simulation;
    public void setSim(SimulationGUI sim) {
        simulation = sim;
    }
    /** Average position of all boids */
    private Vec position;
    /** Average direction of all boids */
    private Vec direction;

    /** Default constructor. */
    public Model() {
        // All boids that might appear in the graphics window are created, but are not visible.
        for (int i=0; i<200; i++) {
            boids.add(new Boid());
        }
        //position = new Vec(rand.nextInt(50,650),rand.nextInt(150,850));
        position = new Vec(0,0);
        //direction = new Vec(rand.nextInt(-1,1),rand.nextInt(-1,1));
        direction = new Vec(0, 0);
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
            //If boids are overlapping, change the color of the boids and then change the coordinate of both boids
            if (!paused) {
                advanceBoids();
                Vec avgP = calcAvgPosition();
                //System.out.println("Position: " + avgP.toString());
                Vec avgD = calcAvgDirection();
                //System.out.println("Direction: " + avgD.toString());
                for (Boid b: boids){
                    Vec sep = b.separation(boids);
                    b.setForce(avgP,avgPosWeight,avgD,avgDirWeight,sep,sepWeight);
                }
                simulation.getContentPane().repaint();
                boids.get(0).repaint();
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
            b.setLocation(b.getXY().x,b.getXY().y);
            // If any boid hits the side of the bounding box, make it warp to the other side
            if (b.getX() < 50){
                b.setLocation(650,b.getXY().y);
            }else if (b.getX() > 650){
                b.setLocation(50,b.getXY().y);
            }
        }
    }

    /**
     * Calculates the average position of all boids
     * @return Position Vec with average position of all boids
     */
    public Vec calcAvgPosition(){
        int posxCount = 0;
        int posx = 0;
        int posyCount = 0;
        int posy = 0;
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
        Vec posVec = new Vec(posx,posy);
        posVec.normalize();
        //Put results into position Vec
        position.add(posVec);
        return position;
    }

    /**
     * Calculates the average direction of all boids
     * @return Direction Vec with average direction of all boids
     */
    public Vec calcAvgDirection(){
        int dirxCount = 0;
        int dirx = 0;
        int diryCount = 0;
        int diry = 0;
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
        //Put results into direction Vec
        Vec dirVec = new Vec(dirx,diry);
        dirVec.normalize();
        direction.add(dirVec);
        return direction;
    }
}

