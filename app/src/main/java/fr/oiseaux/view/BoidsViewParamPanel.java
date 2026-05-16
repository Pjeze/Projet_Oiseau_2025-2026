package fr.oiseaux.view;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import fr.oiseaux.model.VicsekModel;

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

    public BoidsViewParamPanel() {
        setLayout(new GridLayout(6, 1));

        //title
        titleLabel.setText("Boids Parameters");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont( new Font( "Arial", Font.BOLD, 14));
        add(titleLabel);

        //radius
        radiusLabel.setText("Field View Radius : 10");
        radiusPanel.add(radiusLabel);
        add(radiusPanel);

        //Separation radius
        separationRadiusLabel.setText("Separation radius : 10");
        separationRadiusPanel.add(separationRadiusLabel);
        add(separationRadiusPanel);

        //separationWeight
        separationWeightLabel.setText("Separation weight : 10");
        separationWeightPanel.add(separationWeightLabel);
        add(separationWeightPanel);

        //alignmentWeight
        alignmentWeightLabel.setText("alignment weight : 10");
        alignmentWeightPanel.add(alignmentWeightLabel);
        add(alignmentWeightPanel);

        //cohesionWeight
        cohesionWeightLabel.setText("cohesion weight : 10");
        cohesionWeightPanel.add(cohesionWeightLabel);
        add(cohesionWeightPanel);
    }

    public void updateBoidsRadius(double r) {
        radiusLabel.setText("Field View Radius :" + r);
    }
    
    public void updateSeparationRadius(double rs) {
        radiusLabel.setText("Field View Radius :" + rs);
    }

    public void updateBoidsSeparationWeight(double ws) {
        radiusLabel.setText("Separation weight :" + ws);
    }

    public void updateBoidsAlignmentWeight(double wa) {
        radiusLabel.setText("Alignment weight :" + wa);
    }

    public void updateBoidsCohesionWeight(double wc) {
        radiusLabel.setText("Cohesion weight :" + wc);
    }
}
