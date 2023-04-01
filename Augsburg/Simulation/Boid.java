
import java.awt.*;
import javax.swing.*;

import java.util.*;

/** Circle for drawing in a JFrame
 *
 * @author Xeng Yang 
 * Template for code was provided by Amy Larson
 */
public class Boid extends JPanel {

    /** Unique id (for debugging) */
    static int nextId = 0;
    static int getId() {
        return nextId++;
    }
    private int id;
    /** Whether the boid is touching another boid */
    private boolean touching = false;
    /** x and y bounds to keep boids in the playAreas */
    private final int XMINRANGE = 50;
    private final int XMAXRANGE = 850;
    private final int YMINRANGE = 150;
    private final int YMAXRANGE = 750;

    /** Radius of each circular boid */
    private int radius = 20;
    public void setRadius(int r){
        radius = r;
        this.setSize(2*radius,2*radius);

    }
    public int getRadius(){
        return radius;
    }
    
    /** Color specified in RGB */
    private Color color = new Color(10, 10, 10);

    /** Location of the JPanel in which the boid is drawn */
    private Point xy = new Point(0,0);
    public Point getXY(){
        return xy;
    }

    /** Delta of location at each timestep */
    private Point direction = new Point(+1, +1);
    public Point getDirection(){
        return direction;
    }

    /** Boids have many random components */
    private Random random = new Random();

    /** Location of boid */
    private Point location;

    /** Drawn in window when visible */
    private boolean visible = false;


    /** Reassigns member variables to the boid. */
    public void reset() {
        randomXY();
        randomColor();
        setRadius(radius);
        setLocation((int) xy.x, (int) xy.y);
        randomDirection();
        showBoid();
    }

    /** Makes boid visible */
    public void showBoid() {
        visible = true;
    }

    /** Makes boid invisible */
    public void hideBoid() {
        visible = false;
    }

    /** Default constructor */
    public Boid() {
        id = getId();   // for debugging

        randomXY();
        
        this.setRadius(radius);
        location = new Point(xy.x,xy.y);
        this.setSize(2*radius,2*radius);

        // Make the box/panel on which the boid is drawn transparent
        this.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));

        // Randomly assign values
        randomDirection();
        randomColor();
    }

    /** Randomly assign its location based on the fixed ranges. */
    public void randomXY() {
        // place at random location
        xy.x = random.nextInt(XMAXRANGE - XMINRANGE) + XMINRANGE;
        xy.y = random.nextInt(YMAXRANGE - YMINRANGE) + YMINRANGE;
    }

    /** Randomly point it in a direction with random "speed" */
    public void randomDirection() {
        // set in a random direction
        direction.x = random.nextInt(6) - 3;
        direction.y = random.nextInt(6) - 3;
    }

    /** Randomly assign the RGB components */
    public void randomColor() {
        // color randomly
        color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    /** Move the boid the "delta" for 1 timestep */
    public void step() {
        // If any boid hits the side of the bounding box, make it bounce off the side
        if (xy.x > XMAXRANGE || xy.x < XMINRANGE){
            direction.x *= -1;
        }
        if (xy.y > YMAXRANGE || xy.y < YMINRANGE){
            direction.y *= -1;
        }
        // Make boid move in current set direction
        xy.x += direction.x;
        xy.y += direction.y;
    }

    /**
     * Check to see if there is overlap with boids
     * @param other The other boid
     */
    public void determineNeighbor(Boid other){
        // Calculate distance between centers of two boids
        double d = Math.sqrt((this.xy.x - other.xy.x) * (this.xy.x - other.xy.x) + (this.xy.y - other.xy.y) * (this.xy.y - other.xy.y));
        if(d <= this.radius + other.radius){
            touching = true;
            
        }else{
            touching = false;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        // This is called every time the boid location is reset in the Model
        // System.out.print(" P"+id);
        super.paintComponent(g);
        if (visible) {
            //System.out.println("paintComponent called! - Visible!");
            g.setColor(color);
            g.fillOval(0,0,2*radius,2*radius);
        }
    }

    /**
     * Returns the vector that will be used to steer the boid in the correct direction
     * @param target The point that the boid should move to
     * @return Vector that will be used to steer boid in the correct direction
     */
    public Point seek(Point target){
        target.normalize();
        location.normalize();
        Point steer = Point.subtract(target, location);
        steer.normalize();
        return steer;
    }

    /**
     * Sets and gives a cohesion vector
     * @param boids List of boids in the Model
     * @return Cohesion vector
     */
    public Point cohesion(ArrayList<Boid> boids){
        int distance = 15;
        Point target = new Point(0,0);
        int count = 0;
        for(Boid b : boids){
            int dist = (int) Point.distance(location, b.location);
            if((dist > 0) && (dist < distance)){
                b.location.normalize();
                target.add(b.location);
                target.limit(1);
                count++;
            }
        }
        if(count > 0){
            target.divide(count);
            target.normalize();
            return seek(target);
        }
        return target; 
    }

    /**
     * Sets and gives a separation vector
     * @param boids List of boids in the Model
     * @return Separation vector
     */
    public Point separation(ArrayList<Boid> boids){
        double amountSeparated = 10;
        Point steer = new Point(0, 0);
        int count = 0;
        location.normalize();
        for(Boid b: boids){
            b.location.normalize();
            double d = Point.distance(location, b.location);
            if((d > 0) && (d < amountSeparated)){
                Point difference = Point.subtract(location, b.location);
                difference.divide((int)d);
                difference.normalize();
                steer.add(difference);
                steer.limit(1);
                count++;
            }
        }
        if(count > 0){
            steer.divide(count);
        }
        return steer;
    }

    /**
     * Combines the vectors into one
     * @param pos Average position vector
     * @param newPos Current value on average position slider
     * @param dir Average direction vectpr
     * @param newDir Current value on average direction slider
     * @param sep Separation vector
     * @param newSep Current value on separation slider
     */
    public void setForce(Point pos,int newPos,Point dir,int newDir,Point sep,int newSep){
        double newX = direction.x + (pos.x / newPos) + (dir.x / newDir) + (sep.x / newSep);
        double newY = direction.y + (pos.y / newPos) + (dir.y / newDir) + (sep.y / newSep);
        direction = new Point(newX,newY);
        direction.limit(1);
    }
}
