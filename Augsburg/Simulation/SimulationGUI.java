
import javax.swing.*;
import java.awt.Color;
import java.util.ArrayList;

/**
 * The "VIEW" of Model-View-Controller
 * An instance of this gui contains a reference to the Controller and the Model.
 * @author Xeng Yang 
 * Template for code was provided by Amy Larson
 */
public class SimulationGUI extends JFrame {

    /** Label for population-setting slider */
    private final JLabel COUNTLABEL = new JLabel("# of Boids (2-200): ");
    /** Slider to set boid population */
    protected final JSlider COUNT = new JSlider();

    /** Label for speed-setting slider */
    private final JLabel SPEEDLABEL = new JLabel("Speed (1-5): ");
    /** Slider to set boid speed */
    protected final JSlider SPEED = new JSlider();

    /** Label for size-setting slider */
    private final JLabel SIZELABEL = new JLabel("Size (10-40): ");
    /** Slider to set boid size */
    protected final JSlider SIZE = new JSlider();

    /** Label for slider to set "weight" of average position rule */
    private final JLabel AVGPOSLABEL = new JLabel("Average Position Rule Weight (1-5): ");
    /** Slider to set "weight" of average position rule */
    protected final JSlider AVGPOS = new JSlider();

    /** Label for slider to set "weight" of average direction rule */
    private final JLabel AVGDIRLABEL = new JLabel("Average Direction Rule Weight (1-5): ");
    /** Slider to set "weight" of average direction rule */
    protected final JSlider AVGDIR = new JSlider();

    /** Label for slider to set "weight" of separation rule */
    private final JLabel SEPLABEL = new JLabel("Separation Rule Weight (1-5): ");
    /** Slider to set "weight" of separation rule */
    protected final JSlider SEP = new JSlider();

    /** Button to pause the boids */
    private final JButton STOP = new JButton("Stop");
    /** Button to make the boids start moving */
    private final JButton PLAY = new JButton("Play");
    /** Button to set up the simulation */
    private final JButton RESTART = new JButton("Set Up");

    /** List of all created boids */
    private ArrayList<Boid> boids;

    /**
     * Creates a Simulation GUI application.
     * Sets the components and their positions in the gui.
     * Sets the Controller as the buttons' action listener.
     * @param control Controller used for the simulation
     * @param model Boid model used for the simulation
     */
    public SimulationGUI(Controller control, Model model) {

        // Initialize the graphics window
        super("Simulation");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(1000,1000);
        // You control the layout
        this.getContentPane().setLayout(null);

        // Play Area
        BoundingBox playArea = new BoundingBox(800,600);
        playArea.setLocation(50,150);
        getContentPane().add(playArea);

        // The Boids
        boids = model.getBoids();
        for (Boid boid: boids) {
            boid.setLocation((int) boid.getXY().x,(int) boid.getXY().y);
            getContentPane().add(boid);
        }
        
        // Controller Display

        // Boid count label and text box
        this.COUNTLABEL.setBounds(20,20,100,30);
        this.getContentPane().add(this.COUNTLABEL);
        
        this.COUNT.setBounds(115, 20, 80, 30);
        this.getContentPane().add(COUNT);
        
        // Sim speed label and text box
        this.SPEEDLABEL.setBounds( 20, 50, 100, 30);
        this.getContentPane().add(this.SPEEDLABEL);
        
        this.SPEED.setBounds(115, 50, 80, 30);
        this.getContentPane().add(this.SPEED);

        // Size label and the textbox
        this.SIZELABEL.setBounds( 20, 80, 100, 30);
        this.getContentPane().add(this.SIZELABEL);
        
        this.SIZE.setBounds(115, 80, 80, 30);
        this.getContentPane().add(SIZE);

        // avgPos label and text box
        this.AVGPOSLABEL.setBounds( 200, 20, 400, 30);
        this.getContentPane().add(this.AVGPOSLABEL);
        
        this.AVGPOS.setBounds(415, 20, 80, 30);
        this.getContentPane().add(this.AVGPOS);

        // avgDir label and the textbox
        this.AVGDIRLABEL.setBounds( 200, 50, 400, 30);
        this.getContentPane().add(this.AVGDIRLABEL);
        
        this.AVGDIR.setBounds(415, 50, 80, 30);
        this.getContentPane().add(AVGDIR);

        // sep label and the textbox
        this.SEPLABEL.setBounds( 200, 80, 400, 30);
        this.getContentPane().add(this.SEPLABEL);
        
        this.SEP.setBounds(415, 80, 80, 30);
        this.getContentPane().add(SEP);

        

        // Restart button 
        this.RESTART.setBounds(500, 20, 120, 30);
        this.RESTART.addActionListener(control);
        this.getContentPane().add(this.RESTART);
        
        // Play button
        this.PLAY.setBounds(500, 60, 120, 30);
        this.PLAY.addActionListener(control);
        this.getContentPane().add(this.PLAY);
        
        // Stop button
        this.STOP.setBounds(500, 100, 120, 30);
        this.STOP.addActionListener(control);
        this.getContentPane().add(this.STOP);
    }
    
}
