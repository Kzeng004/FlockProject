Planning Document Link:
https://docs.google.com/document/d/1n_8b3t7i42_2JzQcKiWK9j6iOqrzB8hCL9ffSwjsGe8/edit

---------------------------------------------------------------------------------------------

3 Resources:

1) https://brainyloop.com/how-to-simulate-flocking-behavior/
2) https://faculty.cc.gatech.edu/~turk/bio_sim/hw2.html
3) https://processing.org/examples/flocking.html
---------------------------------------------------------------------------------------------
Description:
 In this project, we want to create a simulation of the flocking behavior with boids. This end result contains a GUI that will allow the user to select the number of boids, the speed as well as some other options that the user to control using a slider.

 ----------------------------------------------------------------------------------------------
 How-to-use:

 1) Pull the java files
 2) run command prompt
 3) navigate to the location of the file by using "cd" command
 4) compile the .java files by typing "javac *.java"
 5) run the java simulation by entering "java Simulation"



----------------------------------------------------------------------------------------------
Files 
Boid.java - This java file contains methods that will affect the boids such as the separation method, the cohesion method, getLocation method, getDirection,  etc. 



BoundingBox.java - This java file creates the box in which the simulation will be shown on.



Controller.java - This java file allows for the the sliders to pass in the value to show the 



Model.java - This java file contains methods that can be controlled by the end user and the values will be read by the controller to allow for the end user to control the simulation.



Point.java - This java file contains the point object allowing the use of points to determine location.



Simulation.java - This java file contains a call to the controller class to run the simulation when "java simulation" is called on the command prompt window.



SimulationGUI.java - This java file contains the sliders and the buttons for the GUI. 



Vec.java - This java file allows for the creation of vector objects to use in the Boid class to determine cohesion, separation, etc.


