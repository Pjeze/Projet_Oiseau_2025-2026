package fr.oiseaux.view;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import fr.oiseaux.model.VicsekModel;
import fr.oiseaux.model.BoundaryMode;

public class BoidsViewParamPanel extends JPanel {

    private VicsekModel model;
    private ControlPanel ctrlPanel;

    //title
    JLabel titleLabel = new JLabel();

    //radius panel
    JPanel radiusPanel = new JPanel();
    JLabel radiusLabel = new JLabel();

    //separationRadius panel
    JPanel separationRadiusPanel = new JPanel();
    JLabel separationRadiusLabel = new JLabel();

    //separationWeight panel
    JPanel separationWeightPanel = new JPanel();
    JLabel separationWeightLabel = new JLabel();

    //alignmentWeight panel
    JPanel alignmentWeightPanel = new JPanel();
    JLabel alignmentWeightLabel = new JLabel();

    //cohesionWeight panel
    JPanel cohesionWeightPanel = new JPanel();
    JLabel cohesionWeightLabel = new JLabel();

    //obstacle avoidance range panel
    JPanel obstacleAvoidanceRangePanel = new JPanel();
    JLabel obstacleAvoidanceRangeLabel = new JLabel();

    JPanel boundaryModePanel = new JPanel();
    JLabel boundaryModeLabel = new JLabel();

    public BoidsViewParamPanel() {
        setLayout(new GridLayout(9, 1));

        //title
        titleLabel.setText("Boids Parameters");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont( new Font( "Arial", Font.BOLD, 14));
        add(titleLabel);

        //radius
        radiusLabel.setText("Field View Radius : 12");
        radiusPanel.add(radiusLabel);
        add(radiusPanel);

        //Separation radius
        separationRadiusLabel.setText("Separation radius : 4");
        separationRadiusPanel.add(separationRadiusLabel);
        add(separationRadiusPanel);

        //separationWeight
        separationWeightLabel.setText("Separation weight : 1.2");
        separationWeightPanel.add(separationWeightLabel);
        add(separationWeightPanel);

        //alignmentWeight
        alignmentWeightLabel.setText("Alignment weight : 0.6");
        alignmentWeightPanel.add(alignmentWeightLabel);
        add(alignmentWeightPanel);

        //cohesionWeight
        cohesionWeightLabel.setText("Cohesion weight : 0.4");
        cohesionWeightPanel.add(cohesionWeightLabel);
        add(cohesionWeightPanel);

        //obstacle detection range
        obstacleAvoidanceRangeLabel.setText("Obstacle detection range : 25.0");
        obstacleAvoidanceRangePanel.add(obstacleAvoidanceRangeLabel);
        add(obstacleAvoidanceRangePanel);

        boundaryModeLabel.setText("Boundary mode : CLOSED_BOX");
        boundaryModePanel.add(boundaryModeLabel);
        add(boundaryModePanel);
    }

    public void updateBoidsRadius(double r) {
        radiusLabel.setText("Visual Field Radius : " + r);
    }
    
    public void updateSeparationRadius(double rs) {
        separationRadiusLabel.setText("Separation Radius : " + rs);
    }

    public void updateBoidsSeparationWeight(double ws) {
        separationWeightLabel.setText("Separation weight : " + ws);
    }

    public void updateBoidsAlignmentWeight(double wa) {
        alignmentWeightLabel.setText("Alignment weight : " + wa);
    }

    public void updateBoidsCohesionWeight(double wc) {
        cohesionWeightLabel.setText("Cohesion weight : " + wc);
    }

    public void updateBoidsObstacleAvoidanceRange(double range) {
        obstacleAvoidanceRangeLabel.setText("Obstacle detection range : " + range);
    }

    public void updateBoundaryMode(BoundaryMode mode) {
        boundaryModeLabel.setText("Boundary mode : " + mode.name());
    }
}
