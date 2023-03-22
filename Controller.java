/*
 * Controller.java
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import java.text.DecimalFormat;
/**
 * The controller class for the temp converter application.
 * @author Xeng Yang 
 * Template for code was provided by Amy Larson
 */
public class Controller implements ActionListener {
    

    /** The model of the circles. */
    private final Model model = new Model();
    /** The gui for the simulation. */
    private final SimulationGUI view;
    
    /** Default constructor to set up the viewer
     */
    public Controller() {
        view = new SimulationGUI(this,model);
        model.setSim(view);
        view.setVisible(true);
        model.start();
        
    }
    
    /**
     * Performs the actions for each of the JButtons
     * @param ae The event which occurred, identifying which button was pushed.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        //System.out.println("Action called: " + ae);
        if( ae.getActionCommand().equals("Set Up")) { // tied to the label of the button

            // Set the model on pause
            model.pause();
            
            // Create the boids based on count
            Integer count = Integer.valueOf(this.view.count.getValue());
            model.setCount(count);

            // Set the speed of the simulation
            Integer speed = Integer.valueOf(this.view.speed.getValue());
            model.setSpeed(speed);

            // Set shape of the boids
            Integer size = Integer.valueOf(this.view.size.getValue());
            model.setShape(size);

            // Set "weight" of the average position rule
            Integer posiWeight = Integer.valueOf(this.view.avgPos.getValue());
            model.setAvgPos(posiWeight);

            // Set "weight" of the average distance rule
            Integer direcWeight = Integer.valueOf(this.view.avgDir.getValue());
            model.setAvgDir(direcWeight);

            // Set "weight" of the separation rule
            Integer sepWeight = Integer.valueOf(this.view.sep.getValue());
            model.setSep(sepWeight);

        } else if( ae.getActionCommand().equals( "Stop")) {
            model.pause();
        } else if( ae.getActionCommand().equals( "Play")) {
            model.play();
        }
    }
}
