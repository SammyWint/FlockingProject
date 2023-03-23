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
 * @author Amy Larson (with Erik Steinmetz)
 */
public class SimulationGUI extends JFrame {

    // Playbox bounds {x, y, w, h}
    protected final int[] playBoxBounds = {50, 150, 1050, 600};

    // {R,G,B} values from 0-255 each
    protected Color backGroundColor = new Color(60, 60, 60);
    protected Color textColor = new Color(192, 96, 0);
    protected Color buttonColor = new Color(200, 200, 200);
    protected Color secondaryColor = new Color(51, 102, 255);
    protected Color dotColor = new Color(0, 255, 0);

    // For debugging and placement
    private final JLabel dot = new JLabel("HERE!");

    // Controller GUI Components
    private final JLabel countLabel = new JLabel("Circles (2-100): ");
    protected final JTextField count = new JTextField(10);

    private final JLabel speedLabel = new JLabel("Speed (1-5): ");
    protected final JTextField speed = new JTextField(10);

    // Boid Sliders
    private final JLabel cohesionLabel = new JLabel("Cohesion (0 - 100): ");
    protected final JSlider cohesion = new JSlider(0, 100);

    private final JLabel separationLabel = new JLabel("Separation (0 - 100): ");
    protected final JSlider separation = new JSlider(0, 100);

    private final JLabel alignmentLabel = new JLabel("Alignment (0 - 100): ");
    protected final JSlider alignment = new JSlider(0, 100);

    private final JButton stop = new JButton("Stop");
    private final JButton play = new JButton("Play");
    private final JButton restart = new JButton("Set Up");

    private ArrayList<Circle> circles;


    public SimulationGUI() {

    }

    /**
     * Creates a Simulation GUI application.
     * Sets the components and their positions in the gui.
     * Sets the Controller as the buttons' action listener.
     */
    public SimulationGUI(Controller control, CircleModel model) {

        // Initialize the graphics window
        super("Simulation");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1200,1200);
        this.getContentPane().setBackground(backGroundColor);

        // You control the layout
        this.getContentPane().setLayout(null);

        // Play Area
        BoundingBox playArea = new BoundingBox(playBoxBounds[2], playBoxBounds[3]);
        playArea.setLocation(playBoxBounds[0], playBoxBounds[1]);
        playArea.setForeground(textColor);
        getContentPane().add(playArea);

        // The Circles
        circles = model.getCircles();
        for (Circle circle: circles) {
            circle.setLocation(circle.getXY().x,circle.getXY().y);
            getContentPane().add(circle);
        }

        // For debugging bounds!
        this.dot.setBounds(50, 150, 20, 10);
        this.dot.setForeground(dotColor);
        this.getContentPane().add(this.dot);
        
        // Controller Display

        // Place the circle count label and text box
        this.countLabel.setBounds(20,20,100,30);
        this.countLabel.setForeground(textColor);
        this.getContentPane().add(this.countLabel);
        
        this.count.setBounds(115, 20, 80, 30);
        this.count.setBackground(backGroundColor);
        this.count.setForeground(textColor);
        this.getContentPane().add(count);

        // Sliders -------------------------------------------------------------------------------------------
        
        // Place the alignment label and slider
        this.alignmentLabel.setBounds(800, 100, 120, 30);
        this.alignmentLabel.setForeground(textColor);
        this.getContentPane().add(alignmentLabel);

        this.alignment.setBounds(925, 100, 100, 50);
        this.alignment.setBackground(backGroundColor);
        this.alignment.setForeground(textColor);
        this.getContentPane().add(alignment);

        // Place the separation label and slider
        this.separationLabel.setBounds(550, 100, 120, 30);
        this.separationLabel.setForeground(textColor);
        this.getContentPane().add(separationLabel);

        this.separation.setBounds(680, 100, 100, 50);
        this.separation.setBackground(backGroundColor);
        this.separation.setForeground(textColor);
        this.getContentPane().add(separation);

        // Place the cohesion label and slider
        this.cohesionLabel.setBounds(310, 100, 120, 30);
        this.cohesionLabel.setForeground(textColor);
        this.getContentPane().add(cohesionLabel);

        this.cohesion.setBounds(430, 100, 100, 50);
        this.cohesion.setBackground(backGroundColor);
        this.cohesion.setForeground(textColor);
        this.getContentPane().add(cohesion);

        // End Sliders ---------------------------------------------------------------------------------------
        
        // place the sim speed label and text box
        this.speedLabel.setBounds( 20, 50, 100, 30);
        this.speedLabel.setForeground(textColor);
        this.getContentPane().add(this.speedLabel);
        
        this.speed.setBounds(115, 50, 80, 30);
        this.speed.setBackground(backGroundColor);
        this.speed.setForeground(textColor);
        this.getContentPane().add(this.speed);

        // place the restart button 
        this.restart.setBounds(200, 20, 120, 30);
        this.restart.addActionListener(control);
        this.restart.setForeground(secondaryColor);
        //this.restart.setBackground(buttonColor);
        this.getContentPane().add(this.restart);
        
        // place the play and stop buttons
        this.play.setBounds(40, 100, 120, 30);
        this.play.addActionListener(control);
        this.play.setForeground(secondaryColor);
        //this.play.setBackground(buttonColor);
        this.getContentPane().add(this.play);
        
        this.stop.setBounds(150, 100, 120, 30);
        this.stop.addActionListener(control);
        this.stop.setForeground(secondaryColor);
        //this.stop.setBackground(buttonColor);
        this.getContentPane().add(this.stop);
    }

}