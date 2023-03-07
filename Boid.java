import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

import java.util.Random;

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

    /** x and y bounds to keep circles in the playAreas */
    private final int xMINRANGE = 60;
    private final int xMAXRANGE = 740;
    private final int yMINRANGE = 160;
    private final int yMAXRANGE = 740;

    /** Fixed size */
    public int radius = 20;
    
    /** Color specified in RGB */
    private Color color = new Color(10, 10, 10);

    /** Location of the JPanel in which the circle is drawn */
    private Point xy = new Point(0, 0);

    /** Delta of location at each timestep */
    private Point direction = new Point(+1, +1);

    /** Circels have many random components */
    private Random random = new Random();

    /** Drawn in window when visible */
    private boolean visible = false;


    /** Reassigns member variables to the circle. */
    public void reset() {
        randomXY();
        randomDirection();
        randomColor();
        setLocation(xy.x, xy.y);
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
     * set the radius to i * radius
     * @param i
     */
    public void setRadius(int i){
        radius = i * radius;
    }


    /** Default constructor */
    public Boid() {
        id = getId();   // for debugging

        this.setSize(2*radius, 2*radius);

        // Make the box/panel on which the circle is drawn transparent
        this.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));

        // Randomly assign values
        randomXY();
        randomDirection();
        randomColor();
    }

    /** Randomly assign its location based on the fixed ranges. */
    public void randomXY() {
        // place at random location
        xy.x = random.nextInt(xMAXRANGE - xMINRANGE) + xMINRANGE;
        xy.y = random.nextInt(yMAXRANGE - yMINRANGE) + yMINRANGE;
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

    /** Move the robot the "delta" for 1 timestep */
    public void step() {
        int xNew = (xy.x + direction.x);
        int yNew = (xy.y + direction.y);
        if (xNew < xMINRANGE) {
            xy.x -= direction.x;
            direction.x *= -1;
        }
        else if (xNew > xMAXRANGE) {
            xy.x -= direction.x;
            direction.x *= -1;
        }
        else {
            xy.x += direction.x;
        }
        if (yNew < yMINRANGE) {
            xy.y -= direction.y;
            direction.y *= -1;
        }
        else if (yNew > yMAXRANGE) {
            xy.y -= direction.y;
            direction.y *= -1;
        }
        else {
            xy.y += direction.y;
        }
    }


    
    public Point getXY() {
        return xy;
    }
    /**
     * Check to see if there is overlap with circles
     * @param other is the other circle
     * @return
     */
    public boolean overlaps(Boid other){
        boolean touched = false;
        double d = Math.sqrt((this.xy.x - other.xy.x) * (this.xy.x - other.xy.x) + (this.xy.y - other.xy.y) * (this.xy.y - other.xy.y));
        if(d <= this.radius + other.radius){
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
            g.fillOval(0, 0, 2*radius, 2*radius);
        }
    }
}