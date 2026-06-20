package fr.oiseaux.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import fr.oiseaux.model.BoundaryMode;

public class BoidsControlPanel extends JPanel {

    //title panel
    JPanel titlePanel = new JPanel();
    JLabel titleLabel = new JLabel("Boids Parameters");

    //core panel
    JPanel corePanel = new JPanel(new GridLayout(0, 1, 6, 6));

    //Visual field radius panel
    JPanel radiusPanel = new JPanel(new GridLayout(2, 1));
    JLabel radiusLabel = new JLabel("Visual field radius :");
    public JSlider radiusSlider = new JSlider(JSlider.HORIZONTAL, 0, 20, 12);

    //Separation radius panel 
    JPanel separationRadiusPanel = new JPanel(new GridLayout(2, 1));
    JLabel separationRadiusLabel = new JLabel("Separation radius :");
    public JSlider separationRadiusSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 40);

    //separationWeight panel
    JPanel separationWeightPanel = new JPanel(new GridLayout(2, 1));
    JLabel separationWeightLabel = new JLabel("Separation weight :");
    public JSlider separationWeightSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 12);

    //alignementWeight panel
    JPanel alignmentWeightPanel = new JPanel(new GridLayout(2, 1));
    JLabel alignmentWeightLabel = new JLabel("Alignment weight :");
    public JSlider alignmentWeightSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 6);

    //cohesionWeight panel
    JPanel cohesionWeightPanel = new JPanel(new GridLayout(2, 1));
    JLabel cohesionWeightLabel = new JLabel("Cohesion weight :");
    public JSlider cohesionWeightSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 4);


    //Obstacle detection range panel
    JPanel obstacleAvoidanceRangePanel = new JPanel(new GridLayout(2, 1));
    JLabel obstacleAvoidanceRangeLabel = new JLabel("Obstacle detection range :");
    public JSlider obstacleAvoidanceRangeSlider = new JSlider(JSlider.HORIZONTAL, 2, 25, 18);

    //BoundaryMode panel
    JPanel boundaryModePanel = new JPanel(new GridLayout(2, 1));
    JLabel boundaryModeLabel = new JLabel("Boundary Mode : ");
    public JComboBox<BoundaryMode> boundaryModeComboBox = new JComboBox<>(BoundaryMode.values());
   // New elements for obstacle shape choice
    JPanel obstacleShapePanel = new JPanel();
    JLabel obstacleShapeLabel = new JLabel("Obstacle shape :");
    public JComboBox<String> obstacleShapeComboBox = new JComboBox<>(new String[]{"None", "Cube", "Sphere", "Cone"});

    public BoidsControlPanel() {
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

        //separationRadius
        separationRadiusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        separationRadiusPanel.add(separationRadiusLabel);
        separationRadiusSlider.setMajorTickSpacing(10);
        separationRadiusSlider.setMinorTickSpacing(5);
        separationRadiusSlider.setPaintTicks(true);
        separationRadiusSlider.setPaintLabels(false);
        separationRadiusPanel.add(separationRadiusSlider);
        corePanel.add(separationRadiusPanel);

        //separationWeight
        separationWeightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        separationWeightPanel.add(separationWeightLabel);
        separationWeightSlider.setMajorTickSpacing(10);
        separationWeightSlider.setMinorTickSpacing(5);
        separationWeightSlider.setPaintTicks(true);
        separationWeightSlider.setPaintLabels(false);
        separationWeightPanel.add(separationWeightSlider);
        corePanel.add(separationWeightPanel);

        //alignmentWeight
        alignmentWeightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        alignmentWeightPanel.add(alignmentWeightLabel);
        alignmentWeightSlider.setMajorTickSpacing(10);
        alignmentWeightSlider.setMinorTickSpacing(5);
        alignmentWeightSlider.setPaintTicks(true);
        alignmentWeightSlider.setPaintLabels(false);
        alignmentWeightPanel.add(alignmentWeightSlider);
        corePanel.add(alignmentWeightPanel);

        //cohesionWeight
        cohesionWeightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cohesionWeightPanel.add(cohesionWeightLabel);
        cohesionWeightSlider.setMajorTickSpacing(10);
        cohesionWeightSlider.setMinorTickSpacing(5);
        cohesionWeightSlider.setPaintTicks(true);
        cohesionWeightSlider.setPaintLabels(false);
        cohesionWeightPanel.add(cohesionWeightSlider);
        corePanel.add(cohesionWeightPanel);

        //obstacleAvoidanceRange
        obstacleAvoidanceRangeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        obstacleAvoidanceRangePanel.add(obstacleAvoidanceRangeLabel);
        obstacleAvoidanceRangeSlider.setMajorTickSpacing(5);
        obstacleAvoidanceRangeSlider.setMinorTickSpacing(1);
        obstacleAvoidanceRangeSlider.setPaintTicks(true);
        obstacleAvoidanceRangeSlider.setPaintLabels(false);
        obstacleAvoidanceRangePanel.add(obstacleAvoidanceRangeSlider);
        corePanel.add(obstacleAvoidanceRangePanel);

        //boundaryMode
        boundaryModeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        boundaryModePanel.add(boundaryModeLabel);
        boundaryModePanel.add(boundaryModeComboBox);
        corePanel.add(boundaryModePanel);

JPanel obstacleShapePanel = new JPanel();
JLabel obstacleShapeLabel = new JLabel("Obstacle shape :");
obstacleShapePanel.add(obstacleShapeLabel);
obstacleShapePanel.add(obstacleShapeComboBox);

// Add the panel to the main Boids control panel
        corePanel.add(obstacleShapePanel);

        corePanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        //add to panel
        add(titlePanel, BorderLayout.NORTH);
        add(corePanel, BorderLayout.CENTER);

    }

}

