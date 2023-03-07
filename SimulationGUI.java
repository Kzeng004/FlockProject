import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JSlider;

import java.awt.Color;
import java.util.ArrayList;

/**
 * The "VIEW" of Model-View-Controller
 * An instance of this gui contains a reference to the Controller and the Model.
 * @author Xeng Yang 
 * Template for code was provided by Amy Larson
 */
public class SimulationGUI extends JFrame {

    // Controller GUI Components
    private final JLabel countLabel = new JLabel("Circles (2-200): ");
    protected final JSlider count = new JSlider();

    private final JLabel speedLabel = new JLabel("Speed (1-5): ");
    protected final JSlider speed = new JSlider();

    private final JLabel sizeLabel = new JLabel("Shape (10-40): ");
    protected final JSlider size = new JSlider();

    private final JButton stop = new JButton("Stop");
    private final JButton play = new JButton("Play");
    private final JButton restart = new JButton("Set Up");

    private ArrayList<Boid> boids;

    /**
     * Creates a Simulation GUI application.
     * Sets the components and their positions in the gui.
     * Sets the Controller as the buttons' action listener.
     */
    public SimulationGUI(Controller control, BoidModel model) {

        // Initialize the graphics window
        super("Simulation");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(1000,1000);
        // You control the layout
        this.getContentPane().setLayout(null);

        // Play Area
        BoundingBox playArea = new BoundingBox(700,600);
        playArea.setLocation(50,150);
        getContentPane().add(playArea);

        // The Circles
        boids = model.getBoids();
        for (Boid boid: boids) {
            boid.setLocation(boid.getXY().x,boid.getXY().y);
            getContentPane().add(boid);
        }
        
        // Controller Display

        // Place the circle count label and text box
        this.countLabel.setBounds(20,20,100,30);
        this.getContentPane().add(this.countLabel);
        
        this.count.setBounds(115, 20, 80, 30);
        this.getContentPane().add(count);
        
        // place the sim speed label and text box
        this.speedLabel.setBounds( 20, 50, 100, 30);
        this.getContentPane().add(this.speedLabel);
        
        this.speed.setBounds(115, 50, 80, 30);
        this.getContentPane().add(this.speed);
        //shape label and the textbox
        this.sizeLabel.setBounds( 20, 80, 100, 30);
        this.getContentPane().add(this.sizeLabel);
        
        this.size.setBounds(115, 80, 80, 30);
        this.getContentPane().add(size);

        // place the restart button 
        this.restart.setBounds(200, 20, 120, 30);
        this.restart.addActionListener(control);
        this.getContentPane().add(this.restart);
        
        // place the play and stop buttons
        this.play.setBounds(40, 110, 120, 30);
        this.play.addActionListener(control);
        this.getContentPane().add(this.play);
        
        this.stop.setBounds(150, 110, 120, 30);
        this.stop.addActionListener(control);
        this.getContentPane().add(this.stop);
    }
    
}
