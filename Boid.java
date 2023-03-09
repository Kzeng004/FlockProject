import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import javax.swing.JPanel;

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
    /** x and y bounds to keep boids in the playAreas */
    private final int xMINRANGE = 60;
    private final int xMAXRANGE = 740;
    private final int yMINRANGE = 160;
    private final int yMAXRANGE = 740;

    /** Fixed size */
    //public int radius = 20;

    private int height = 20;
    private int width = 20;
    private double area = (height * width)/2.0;
    
    /** Color specified in RGB */
    private Color color = new Color(10, 10, 10);

    /** Location of the JPanel in which the boid is drawn */
    private Point v1 = new Point(0,20);
    private Point v2 = new Point(20,20);
    private Point v3 = new Point(10,0);
    private ArrayList<Point> points = new ArrayList<>();

    /** Delta of location at each timestep */
    private Point direction = new Point(+1, +1);

    /** Circels have many random components */
    private Random random = new Random();

    /** Drawn in window when visible */
    private boolean visible = false;


    /** Reassigns member variables to the circle. */
    public void reset() {
        v1 = randomXY(v1);
        v2 = new Point(v1.x + 20, v1.y);
        v3 = new Point(v1.x + 10, v1.y - 20);
        randomColor();
        setLocation(v1.x, v1.y);
        randomDirection();
        showBoid();
    }

    /** Circle is visible */
    public void showBoid() {
        visible = true;
    }

    /** Circle is not visible */
    public void hideBoid() {
        visible = false;
    }

    /**
     * set the area to (h * w) / 2
     * @param h Desired height
     * @param w Desired width
     */
    public void setArea(int h, int w){
        height = h;
        width = w;
        area = (h * w)/2.0;
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

        this.setArea(v3.y - v1.y,v2.x - v1.x);
        this.setLocation(v1.x, v1.y);

        // Make the box/panel on which the boid is drawn transparent
        this.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));

        // Randomly assign values
        randomDirection();
        randomColor();
    }

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
    }


    
    public ArrayList<Point> getPoints() {
        return points;
    }
    /**
     * Check to see if there is overlap with boids
     * @param other is the other boid
     * @return
     */
    public boolean determineNeighbor(Boid other){
        boolean touched = false;
        Point thisCenter = new Point(v1.x + width/2, v1.y - height/2);
        Point otherCenter = new Point(other.v1.x + other.width/2, other.v1.y - other.height/2);
        double d = Math.sqrt((thisCenter.x - otherCenter.x) * (thisCenter.x - otherCenter.x) + (thisCenter.y - otherCenter.y) * (thisCenter.y - otherCenter.y));
        if(d <= this.height + other.height){
            touched = true;
            
        }
        else{
            touched = false;
        }
        return touched;
    }

    @Override
    public void paintComponent(Graphics g) {
        // This is called every time the circle location is reset in the CircleModel
        // System.out.print(" P"+id);
        super.paintComponent(g);
        if (visible) {
            g.setColor(color);
            Polygon p = new Polygon(new int[] {0,width/2,width},new int[] {0,0,height},3);
            g.drawPolygon(p);
            g.fillPolygon(p);
        }
    }
}