import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.Random;

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

    /** x and y bounds to keep circles in the playAreas */
    private final int xMINRANGE = 60;
    private final int xMAXRANGE = 740;
    private final int yMINRANGE = 160;
    private final int yMAXRANGE = 740;

    /** Fixed size */
    private int radius = 30;

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

    /** Return if circle is visible or not */
    public boolean visible() {
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
    public void step() {
        xy.x += direction.x;
        xy.y += direction.y;
        if (xy.x < xMINRANGE || xy.x > yMAXRANGE) {
            direction.x *= -1;
            randomColor();
        }
        if (xy.y < yMINRANGE || xy.y > yMAXRANGE) {
            direction.y *= -1;
            randomColor();
        }
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


}
