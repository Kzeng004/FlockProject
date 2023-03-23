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
    private final int xMINRANGE = 60;
    private final int xMAXRANGE = 740;
    private final int yMINRANGE = 160;
    private final int yMAXRANGE = 740;

    /** Measurements of each triangular boid */
    private int height = 20;
    private int width = 20;
    private double area = 200.0;
    public void setArea(int h, int w){
        height = h;
        width = w;
        area = (h * w)/2.0;
    }
    
    /** Color specified in RGB */
    private Color color = new Color(10, 10, 10);

    /** Location of the JPanel in which the boid is drawn */
    private Point v1 = new Point(0,20);
    private Point v2 = new Point(20,20);
    private Point v3 = new Point(10,0);

    /** List containing all the points making up the JPanel's location */
    private ArrayList<Point> points = new ArrayList<>();
    public ArrayList<Point> getPoints() {
        return points;
    }

    /** Center of the JPanel */
    private Point center;
    public void setCenter(){
        center = new Point(v1.x + width/2, v1.y - height/2);
    }
    public Point getCenter(){
        return center;
    }

    /** Delta of location at each timestep */
    private Point direction = new Point(+1, +1);
    public Point getDirection(){
        return direction;
    }

    /** Boids have many random components */
    private Random random = new Random();

    /** Location of boid */
    private Vec location;

    /** Drawn in window when visible */
    private boolean visible = false;


    /** Reassigns member variables to the boid. */
    public void reset() {
        //System.out.println("resetting boid!");
        v1 = randomXY(v1);
        v2 = new Point(v1.x + 20, v1.y);
        v3 = new Point(v1.x + 10, v1.y - 20);
        randomColor();
        setArea(v3.y - v1.y, v2.x - v1.x);
        location = new Vec(v1.x,v1.y);
        //setLocation(v1.x, v1.y);
        randomDirection();
        //pointInDirection();
        showBoid();
        //System.out.println("Boid shown!!!");
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

        v1 = randomXY(v1);
        v2 = new Point(v1.x + 20, v1.y);
        v3 = new Point(v1.x + 10, v1.y + 20);

        points.add(v1);
        points.add(v2);
        points.add(v3);

        this.setCenter();
        this.setArea(v3.y - v1.y,v2.x - v1.x);
        location = new Vec(v1.x,v1.y);
        //this.setLocation(v1.x, v1.y);
        //this.pointInDirection();

        // Make the box/panel on which the boid is drawn transparent
        this.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));

        // Randomly assign values
        randomDirection();
        randomColor();
    }

    /** Make boid point in the direction that the boid is moving */
    /*public void pointInDirection(){
        if (direction.x > 0){
            v1.x = center.x + direction.x;
        }else{
            v1.x = center.x - direction.x;
        }
        if (direction.y > 0){
            v1.y = center.y + direction.y;
        }else{
            v1.y = center.y - direction.y;
        }
        //ADD v2 & v3!!!
    }*/

    /** Randomly assign its location based on the fixed ranges. */
    public Point randomXY(Point v) {
        // place at random location
        v.x = random.nextInt(xMAXRANGE - xMINRANGE) + xMINRANGE;
        v.y = random.nextInt(yMAXRANGE - yMINRANGE) + yMINRANGE;
        return v;
    }

    /** Randomly point it in a direction with random "speed" */
    public void randomDirection() {
        // set in a random direction
        direction.x = random.nextInt(6) - 3;
        direction.y = random.nextInt(6) - 3;
        //pointInDirection();
    }

    /** Randomly assign the RGB components */
    public void randomColor() {
        // color randomly
        color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    /** Move the boid the "delta" for 1 timestep */
    public void step() {
        int x1New = (v1.x + direction.x);
        int y1New = (v1.y + direction.y);
        int x2New = (v2.x + direction.x);
        int y2New = (v2.y + direction.y);
        int x3New = (v3.x + direction.x);
        int y3New = (v3.y + direction.y);

        if (x1New < xMINRANGE) {
            v1.x = xMAXRANGE - 21;
            v2.x = xMAXRANGE - 1;
            v3.x = xMAXRANGE - 11;
        }
        else if (x2New > xMAXRANGE) {
            v1.x = xMINRANGE + 1;
            v2.x = xMINRANGE + 21;
            v3.x = xMINRANGE + 11;
        }
        else {
            for (Point v: points){
                v.x += direction.x;
            }
        }
        if (y3New < yMINRANGE) {
            for (Point v: points){
                v.y -= direction.y;
            }
            direction.y *= -1;
        }
        else if (y1New > yMAXRANGE) {
            for (Point v: points){
                v.y -= direction.y;
            }
            direction.y *= -1;
        }
        else {
            for (Point v: points){
                v.y += direction.y;
            }
        }

        //pointInDirection();
    }

    /**
     * Check to see if there is overlap with boids
     * @param other The other boid
     */
    public void determineNeighbor(Boid other){ //Return type Vec?
        double d = Math.sqrt((this.center.x - other.center.x) * (this.center.x - other.center.x) + (this.center.y - other.center.y) * (this.center.y - other.center.y));
        if(d <= this.height + other.height){
            touching = true;
            
        }
        else{
            touching = false;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        // This is called every time the boid location is reset in the Model
        // System.out.print(" P"+id);
        System.out.println("paintComponent called!");
        super.paintComponent(g);
        if (visible) {
            System.out.println("Visible!");
            g.setColor(color);
            Polygon p = new Polygon(new int[] {0,width/2,width},new int[] {0,0,height},3);
            g.drawPolygon(p);
            g.fillPolygon(p);
        }
    }

    /**
     * Returns the vec that will be used to steer the boid in the correct direction
     * @param target The vec that the boid should move to
     * @return Vec that will be used to steer boid in the correct direction
     */
    public Vec seek(Vec target){
        Vec steer = Vec.subtract(target, location);
        return steer;
    }

    /**
     * Sets and gives a cohesion vector
     * @param boids List of boids in the Model
     * @return Cohesion vector
     */
    public Vec cohesion(ArrayList<Boid> boids){
        int distance = 15;
        Vec target = new Vec(0,0);
        int count = 0;
        for(Boid b : boids){
            int dist = (int) Vec.distance(location, b.location);
            if((dist > 0) && (dist < distance)){
                target.add(b.location);
                count++;
            }
        }
        if(count > 0){
            target.divide(count);
            return seek(target);
        }
        return target; 
    }

    /**
     * Sets and gives a separation vector
     * @param boids List of boids in the Model
     * @return Separation vector
     */
    public Vec separation(ArrayList<Boid> boids){
        double amountSeparated = 10;
        Vec steer = new Vec(0, 0);
        int count = 0;
        for(Boid b: boids){
            double d = Vec.distance(location, b.location);
            if((d > 0) && (d < amountSeparated)){
                Vec difference = Vec.subtract(location, b.location);
                difference.divide((int)d);
                steer.add(difference);
                count++;
            }
        }
        if(count > 0){
            steer.divide(count);
        }
        return steer;
    }

    /**
     * Combines the vecs into one
     * @param pos Average position vec
     * @param newPos Current value on average position slider
     * @param dir Average direction vec
     * @param newDir Current value on average direction slider
     * @param sep Separation vec
     * @param newSep Current value on separation slider
     */
    public void setForce(Vec pos,int newPos,Vec dir,int newDir,Vec sep,int newSep){
        int newX = direction.x + (pos.x / newPos) + (dir.x / newDir) + (sep.x / newSep);
        int newY = direction.y + (pos.y / newPos) + (dir.y / newDir) + (sep.y / newSep);
        direction = new Point(newX,newY);
    }
}
