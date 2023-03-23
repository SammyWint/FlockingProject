/*
 * Circle Model.java
 */

import java.util.ArrayList;
import java.lang.Thread;

/**
 * Models a collection of circles roaming about impacting other circles.
 * 
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

    // Strength of dynamic effects
    
    /** Strength of cohesion behavior */
    private double cohesionStr = 100;
    /** Strength of separation behavior */
    private double separationStr = 100;
    /** Strength of alignment behavior */
    private double alignmentStr = 100;

    private SimulationGUI simulation;

    /** Default constructor. */
    public CircleModel() {
        // All circels that might appear in the graphics window are created, but are not
        // visible.
        for (int i = 0; i < numCircles; i++) {
            circles.add(new Circle());
        }
    }

    public void setSim(SimulationGUI sim) {
        simulation = sim;
    }

    @Override
    public void run() {
        // Forever run the simulation
        while (true) {
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
        for (int i = 0; i < count; i++) {
            // Advance each circle
            //circles.get(i).step(circles);
            circles.get(i).step(circles, cohesionStr, separationStr, alignmentStr);

            // Check for collision with other circles
            for (int j = i + 1; j < count; j++) {
                if (circles.get(i).overlaps(circles.get(j))) {
                    handleCollision(circles.get(i), circles.get(j));
                }
            }
            // Set the location, which prompts the viewer to newly display the circle
            circles.get(i).setLocation(circles.get(i).getXY().x, circles.get(i).getXY().y);
        }
    }
    /** Handle collision between two circles */
    private void handleCollision(Circle c1, Circle c2) {
        // Get current velocity and position for each circle
        Point v1 = c1.getDirection();
        Point v2 = c2.getDirection();
        Point p1 = c1.getXY();
        Point p2 = c2.getXY();

        // Calculate new velocity for each circle after collision
        Point newV1 = new Point(v2.x, v2.y);
        Point newV2 = new Point(v1.x, v1.y);

        // Update velocity for each circle
        c1.setDirection(newV1);
        c2.setDirection(newV2);
    }


    public ArrayList<Circle> getCircles() {
        return circles;
    }

    /**
     * Returns a Point with values that represent difference between a circle's
     * direction and the average
     * direction
     */
    private Point calculateDifference(Circle circle) {

        // Average X and Y values for all visible circle directions
        Point avgDirection = avgDirection();

        // Difference between the average direction value, and the given direction value
        int xDifference = avgDirection.x - circle.getDirection().x;
        int yDifference = avgDirection.y - circle.getDirection().y;

        Point difference = new Point(xDifference, yDifference);

        return difference;
    }

    // ** Calculates average direction of all visible circles */
    private Point avgDirection() {
        int Xsum = 0;
        int Ysum = 0;
        int Xavg;
        int Yavg;
        Point avgDirection;
        for (int i = 0; i < count; i++) {
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
        for (int i = 0; i < count; i++) {
            circles.get(i).reset();
        }
        // Hide the rest
        for (int i = count; i < numCircles; i++) {
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
        stepSize = (6 - newSpeed) * 80; // 80 to 400ms
    }

    /** Set strength of dynamic cohesion */
    public void setCoStr(double v) {cohesionStr = v;}
    /** Set strength of dynamic separation */
    public void setSepStr(double v) {separationStr = v;}
    /** Set strength of dynamic alignment */
    public void setAlignStr(double v) {alignmentStr = v;}

}
