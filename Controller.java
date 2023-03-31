/*
 * Controller.java
 */
import java.awt.event.*;
import javax.swing.event.*;

import java.text.DecimalFormat;
/**
 * The controller class for the temp converter application.
 * @author Xeng Yang 
 * Template for code was provided by Amy Larson
 */
public class Controller implements ActionListener {
    

    /** The model of the circles. */
    private final Model MODEL = new Model();
    /** The gui for the simulation. */
    private final SimulationGUI VIEW;
    
    /** Default constructor to set up the viewer
     */
    public Controller() {
        VIEW = new SimulationGUI(this,MODEL);
        MODEL.setSim(VIEW);
        VIEW.setVisible(true);
        MODEL.start();
        
    }
    
    /**
     * Performs the actions for each of the JButtons
     * @param ae The event which occurred, identifying which button was pushed.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if( ae.getActionCommand().equals("Set Up")) { // tied to the label of the button

            // Set the model on pause
            MODEL.pause();
            
            // Create the boids based on count
            Integer count = Integer.valueOf(this.VIEW.COUNT.getValue());
            MODEL.setCount(count);

            // Set the speed of the simulation
            Integer speed = Integer.valueOf(this.VIEW.SPEED.getValue());
            MODEL.setSpeed(speed);

            // Set size of the boids
            Integer size = Integer.valueOf(this.VIEW.SIZE.getValue());
            MODEL.setSize(size);

            // Set "weight" of the average position rule
            Integer posiWeight = Integer.valueOf(this.VIEW.AVGPOS.getValue());
            MODEL.setAvgPos(posiWeight);

            // Set "weight" of the average distance rule
            Integer direcWeight = Integer.valueOf(this.VIEW.AVGDIR.getValue());
            MODEL.setAvgDir(direcWeight);

            // Set "weight" of the separation rule
            Integer sepWeight = Integer.valueOf(this.VIEW.SEP.getValue());
            MODEL.setSep(sepWeight);

        } else if( ae.getActionCommand().equals( "Stop")) {
            MODEL.pause();
        } else if( ae.getActionCommand().equals( "Play")) {
            MODEL.play();
        }
    }
}
