/*
 * Circle Model.java
 */

import java.util.ArrayList;
import java.lang.Thread;

/**
 * Models a collection of circles roaming about impacting other circles.
 * @author Amy Larson (with Erik Steinmetz)
 */
public class CircleModel extends Thread {

    private ArrayList<Circle> circles = new ArrayList<>();

    /** Time in ms. "Frame rate" for redrawing the circles. */
    private int stepSize = 200;
    /** Current number of circles visible in the window. */
    private int count = 0;
    /** Pauses simulation so circles do not move */
    private boolean paused = true;
    /** Number of circles created (visible or not). */
    private final int numCircles = 100;
    /** Strength of cohesion; ie the influence of average direction (between 0 & 1) */
    private double cohesionStrength = 0.5;

    private SimulationGUI simulation;

    /** Default constructor. */
    public CircleModel() {
        // All circels that might appear in the graphics window are created, but are not visible.
        for (int i=0; i<numCircles; i++) {
            circles.add(new Circle());
        }
    }

    public void setSim(SimulationGUI sim) {
        simulation = sim;
    }

    @Override
    public void run() {
        // Forever run the simulation
        while(true) {
            // Move things only if the simulation is not paused
            if (!paused) {
                advanceCircles();
                simulation.getContentPane().repaint();
            }
            try {
                Thread.sleep(stepSize);
            } catch (Exception e) {

            }
        }
    }

    /** Pause the simulation - circles freeze. */
    public void pause() {
        paused = true;
    }

    /** Circles move again */
    public void play() {
        System.out.println("Playing now");
        paused = false;
    }

    /** Move circles to next location */
    public void advanceCircles() {
        for (int i=0; i<count; i++) {
            // Set cohesion **Comment this out if cohesion changes are getting in the way**
            cohesion();
            // Advance each circle
            circles.get(i).step();
            // Set the location, which prompts the viewer to newly display the circle
            circles.get(i).setLocation(circles.get(i).getXY().x, circles.get(i).getXY().y);
        }
    }


    public ArrayList<Circle> getCircles() {
        return circles;
    }

    /** Modify the direction of each circle toward average, "pulling" them in the same direction */
    private void cohesion(){
        Point difference;
        Point newDirection;
        int xModified;
        int yModified;
        for(int i=0; i<count; i++){
            difference = calculateDifference(circles.get(i));
            xModified = ((int)((cohesionStrength) * difference.x)) + circles.get(i).getDirection().x;
            yModified = ((int)((cohesionStrength) * difference.y)) + circles.get(i).getDirection().y;
            newDirection = new Point(xModified, yModified);
            circles.get(i).setDirection(newDirection);
        }
    }

    /** Returns a Point with values that represent difference between a circle's direction and the average
     * direction
     */
    private Point calculateDifference(Circle circle){
        
        // Average X and Y values for all visible circle directions
        Point avgDirection = avgDirection();
        
        // Difference between the average direction value, and the given direction value
        int xDifference = avgDirection.x - circle.getDirection().x;
        int yDifference = avgDirection.y - circle.getDirection().y;

        Point difference = new Point(xDifference, yDifference);

        return difference;
    }

    //** Calculates average direction of all visible circles */
    private Point avgDirection(){
        int Xsum = 0;
        int Ysum = 0;
        int Xavg;
        int Yavg;
        Point avgDirection;
        for(int i=0; i<count; i++){
            Xsum += circles.get(i).getDirection().x;
            Ysum += circles.get(i).getDirection().y;
        }
        Xavg = Xsum / count;
        Yavg = Ysum / count;
        avgDirection = new Point(Xavg, Yavg);
        return avgDirection;
    }

    /** Reset circles */
    public void setCount(int circleCount) {
        System.out.println("Making circles!");
        // Must be in bounds. Only 40 circles in the list.
        if (circleCount < 2) {
            circleCount = 2;
        } else if (circleCount > numCircles) {
            circleCount = numCircles;
        }
        // Reset "count" circles, making them visible
        count = circleCount;
        for (int i=0; i<count; i++) {
            circles.get(i).reset();
        }
        // Hide the rest
        for (int i=count; i<numCircles; i++) {
            circles.get(i).hideCircle();
        }
    }

    /** Set speed of simulation from 1 (slow) to 5 (fast) */
    public void setSpeed(int newSpeed) {
        // speed is between 1 (slow) and 5 (fastest)
        // low speed = high step size
        if (newSpeed < 1) {
            newSpeed = 1;
        } else if (newSpeed > 5) {
            newSpeed = 5;
        }
        stepSize = (6-newSpeed)*80; // 80 to 400ms
    }

    
}
