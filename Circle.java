import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.Vector;
import java.util.Random;
import java.util.List;

/**
 * Circle for drawing in a JFrame
 *
 * @author Amy Larson
 */
public class Circle extends JPanel {
    

    /** Unique id (for debugging) */
    static int nextId = 0;

    static int getId() {
        return nextId++;
    }

    private int id;

    private SimulationGUI noSim = new SimulationGUI();

    /** Fixed size */
    private int radius = 15;

    /** x and y bounds to keep circles in the playAreas */
    private final int xMINRANGE = noSim.playBoxBounds[0];
    private final int xMAXRANGE = noSim.playBoxBounds[2] + noSim.playBoxBounds[0] - radius;
    private final int yMINRANGE = noSim.playBoxBounds[1];
    public final int yMAXRANGE = noSim.playBoxBounds[3] + noSim.playBoxBounds[1] - radius;

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
        showCircle();
    }

    /** Circle is visible */
    public void showCircle() {
        visible = true;
    }

    /** Circle is not visible */
    public void hideCircle() {
        visible = false;
    }

    public boolean visible(){
        return visible;
    }

    /** Default constructor */
    public Circle() {
        id = getId(); // for debugging

        this.setSize(radius, radius);

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
    /** Move the robot the "delta" for 1 timestep */
    public void step(List<Circle> circles) {
        Point separation = new Point(0, 0);
        Point alignment = new Point(0, 0);
        Point cohesion = new Point(0, 0);
        int count = 0;

        for (Circle circle : circles) {
            if (circle == this || !circle.visible()) {
                continue;
            }

            int distance = (int) Math.sqrt(Math.pow(xy.x - circle.xy.x, 2) + Math.pow(xy.y - circle.xy.y, 2));
            if (distance < radius + circle.radius) {
                separation.x -= (circle.xy.x - xy.x);
                separation.y -= (circle.xy.y - xy.y);
                count++;
            }

            alignment.x += circle.direction.x;
            alignment.y += circle.direction.y;

            cohesion.x += circle.xy.x;
            cohesion.y += circle.xy.y;
            count++;
        }
        if (count > 0) {
            separation.x /= count;
            separation.y /= count;
            separation.x *= -1;
            separation.y *= -1;

            alignment.x /= count;
            alignment.y /= count;

            cohesion.x /= count;
            cohesion.y /= count;
            cohesion.x = (cohesion.x - xy.x) / 100;
            cohesion.y = (cohesion.y - xy.y) / 100;
        }

        direction.x += separation.x + alignment.x + cohesion.x;
        direction.y += separation.y + alignment.y + cohesion.y;

        
        xy.x += direction.x;
        xy.y += direction.y;

        if (xy.x < xMINRANGE || xy.x > xMAXRANGE) {
            direction.x *= -1;
        }
        if (xy.y < yMINRANGE || xy.y > yMAXRANGE) {
            direction.y *= -1;
        }

    }

    /** Secondary method for advancing circles - This one weights the 3 aspects of flocking behavior*/
    public void step(List<Circle> circles, double coStr, double sepStr, double alignStr) {
        Point separation = new Point(0, 0);
        Point alignment = new Point(0, 0);
        Point cohesion = new Point(0, 0);
        
        // New Stuff
        coStr /= 100;
        sepStr /= 100;
        alignStr /= 100;
        int forX;
        int forY;
        int limit = 5;
        // End New Stuff
        int count = 0;

        for (Circle circle : circles) {
            if (circle == this || !circle.visible()) {
                continue;
            }

            int distance = (int) Math.sqrt(Math.pow(xy.x - circle.xy.x, 2) + Math.pow(xy.y - circle.xy.y, 2));
            if (distance < radius + circle.radius) {
                separation.x -= (circle.xy.x - xy.x);
                separation.y -= (circle.xy.y - xy.y);
                count++;
            }

            alignment.x += circle.direction.x;
            alignment.y += circle.direction.y;

            cohesion.x += circle.xy.x;
            cohesion.y += circle.xy.y;
            count++;
        }
        if (count > 0) {
            separation.x /= count;
            separation.y /= count;
            separation.x *= -1;
            separation.y *= -1;

            alignment.x /= count;
            alignment.y /= count;

            cohesion.x /= count;
            cohesion.y /= count;
            cohesion.x = (cohesion.x - xy.x) / 100;
            cohesion.y = (cohesion.y - xy.y) / 100;
        }

        
        // New Stuff - Limiting speed (Delta values)
        forX = (int)((separation.x * sepStr) + (alignment.x * alignStr) + (cohesion.x * coStr));
        forY = (int)((separation.y * sepStr) + (alignment.y * alignStr) + (cohesion.y * coStr));
        if(forX > limit) {forX = limit;}
        if(forX < -limit) {forX = -limit;}
        if(forY > limit) {forY = limit;}
        if(forY < -limit) {forY = -limit;}
        // End New Stuff

        direction.x += forX;
        direction.y += forY;

        xy.x += direction.x;
        xy.y += direction.y;

        // TODO: Reset to edge
        if (xy.x < xMINRANGE || xy.x > xMAXRANGE) {
            if(xy.x < xMINRANGE) {xy.x = xMINRANGE;}
            if(xy.x > xMAXRANGE) {xy.x = xMAXRANGE;}
            direction.x *= -1;
        }
        if (xy.y < yMINRANGE || xy.y > yMAXRANGE) {
            if(xy.y < yMINRANGE) {xy.y = yMINRANGE;}
            if(xy.y > yMAXRANGE) {xy.y = yMAXRANGE;}
            direction.y *= -1;
        }

        /*
         * if (xy.x < xMINRANGE || xy.x > xMAXRANGE) {
            if(xy.x < xMINRANGE){xy.x = xMINRANGE;}
            if(xy.x > xMAXRANGE){xy.x = xMAXRANGE;}
        }
        if (xy.y < yMINRANGE || xy.y > yMAXRANGE) {
            if(xy.y < yMINRANGE){xy.y = yMINRANGE;}
            if(xy.y > yMAXRANGE){xy.y = yMAXRANGE;}
        }
         */

    }

    public boolean overlaps(Circle other) {
        int distance = (int) Math.sqrt(Math.pow(xy.x - other.xy.x, 2) + Math.pow(xy.y - other.xy.y, 2));
        return distance < radius + other.radius;
    }

    public Point getXY() {
        return xy;
    }


    @Override
    public void paintComponent(Graphics g) {
        // This is called every time the circle location is reset in the CircleModel
        // System.out.print(" P"+id);
        super.paintComponent(g);
        if (visible) {
            g.setColor(color);
            g.fillOval(0, 0, radius, radius);
        }
    }
    public Vector<Double> averagePosition(List<Circle> circles) {
        int count = 0;
        double averageX = 0.0;
        double averageY = 0.0;

        // Init vector with fixed size
        Vector<Double> sum = new Vector<>(2);
        // sum.add(1, 0.0);
        // sum.add(2, 0.0);
        sum.add(0, 0.0);
        sum.add(1, 0.0);
        for (Circle circle : circles) {
            if (circle.visible()) {
                averageX += circle.getXY().x;
                averageY = circle.getXY().y;
                count++;
            }
        }
        if (count > 0) {
            double averageCircleX = averageX / count;
            double averageCircleY = averageY / count;
            sum.add(0, averageCircleX);
            sum.add(1, averageCircleY);
            // return sum.get(1,2);
        }
        return sum;
    }

    public Point getDirection() {
        return direction;
    }
    
    public void setDirection(Point newDirection) {
        direction = newDirection;
    }
}

