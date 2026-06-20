package fr.oiseaux.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

import fr.oiseaux.model.BoundaryMode;



public class VicsekControlPanel extends JPanel {

    //title panel
    JPanel titlePanel = new JPanel();
    JLabel titleLabel = new JLabel("Vicsek Parameters");

    //core panel
    JPanel corePanel = new JPanel(new GridLayout(0, 1, 6, 6));

    //Visual field radius panel
    JPanel radiusPanel = new JPanel(new GridLayout(2, 1));
    JLabel radiusLabel = new JLabel("Visual field radius :");
    public JSlider radiusSlider = new JSlider(JSlider.HORIZONTAL, 0,20, 8);

    //eta (Interference) panel 
    JPanel etaPanel = new JPanel(new GridLayout(2, 1));
    JLabel etaLabel = new JLabel("Random noise (degrees):");
    public JSlider etaSlider = new JSlider(JSlider.HORIZONTAL, 0, 50, 10);

    //Speed panel
    JPanel speedPanel = new JPanel(new GridLayout(2, 1));
    JLabel speedLabel = new JLabel("Bird speed :");
    public JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 1, 101, 20);


    //Obstacle avoidance range panel
    JPanel obstacleAvoidanceRangePanel = new JPanel(new GridLayout(2, 1));
    JLabel obstacleAvoidanceRangeLabel = new JLabel("Obstacle detection range :");
    public JSlider obstacleAvoidanceRangeSlider = new JSlider(JSlider.HORIZONTAL, 2, 25, 16);

    JPanel boundaryModePanel = new JPanel(new GridLayout(2, 1));
    JLabel boundaryModeLabel = new JLabel("Boundary mode : ");
    public JComboBox<BoundaryMode> boundaryModeComboBox = new JComboBox<>(BoundaryMode.values());
    // Elements for obstacle shape choice
JPanel obstacleShapePanel = new JPanel();
JLabel obstacleShapeLabel = new JLabel("Obstacle shape :");
String[] shapes = {"None", "Cube", "Sphere", "Cone"};
public JComboBox<String> obstacleShapeComboBox = new JComboBox<>(new String[]{"None", "Cube", "Sphere", "Cone"});

    public VicsekControlPanel() {
        setLayout(new BorderLayout());

        //title
        titleLabel.setFont( new Font( "Arial", Font.BOLD, 14));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel);

        //radius
        radiusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        radiusPanel.add(radiusLabel);
        radiusSlider.setMajorTickSpacing(5);
        radiusSlider.setMinorTickSpacing(1);
        radiusSlider.setPaintTicks(true);
        radiusSlider.setPaintLabels(false);
        radiusPanel.add(radiusSlider);
        corePanel.add(radiusPanel);

        //eta (noise)
        etaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        etaPanel.add(etaLabel);
        etaSlider.setMajorTickSpacing(5);
        etaSlider.setMinorTickSpacing(1);
        etaSlider.setPaintTicks(true);
        etaSlider.setPaintLabels(false);
        etaPanel.add(etaSlider);
        corePanel.add(etaPanel);

        //speed
        speedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        speedPanel.add(speedLabel);
        speedSlider.setMajorTickSpacing(10);
        speedSlider.setMinorTickSpacing(5);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(false);
        speedPanel.add(speedSlider);
        corePanel.add(speedPanel);

//obstacleAvoidanceRange
        obstacleAvoidanceRangeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        obstacleAvoidanceRangePanel.add(obstacleAvoidanceRangeLabel);
        obstacleAvoidanceRangeSlider.setMajorTickSpacing(5);
        obstacleAvoidanceRangeSlider.setMinorTickSpacing(1);
        obstacleAvoidanceRangeSlider.setPaintTicks(true);
        obstacleAvoidanceRangeSlider.setPaintLabels(false);
        obstacleAvoidanceRangePanel.add(obstacleAvoidanceRangeSlider);
        corePanel.add(obstacleAvoidanceRangePanel);

        boundaryModeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        boundaryModePanel.add(boundaryModeLabel);
        boundaryModePanel.add(boundaryModeComboBox);
        corePanel.add(boundaryModePanel);

        JPanel obstacleShapePanel = new JPanel();
        JLabel obstacleShapeLabel = new JLabel("Obstacle shape :");
        obstacleShapeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        obstacleShapePanel.add(obstacleShapeLabel);
        obstacleShapePanel.add(obstacleShapeComboBox);
        corePanel.add(obstacleShapePanel);

        corePanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        //add to panel
        add(titlePanel, BorderLayout.NORTH);
        add(corePanel, BorderLayout.CENTER);

    }

}
