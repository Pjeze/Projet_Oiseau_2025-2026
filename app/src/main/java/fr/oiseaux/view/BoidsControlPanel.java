package fr.oiseaux.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

public class BoidsControlPanel extends JPanel {

    //title panel
    JPanel titlePanel = new JPanel();
    JLabel titleLabel = new JLabel("Boids Parameters");

    //core panel
    JPanel corePanel = new JPanel(new GridLayout(5, 1));

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

    //separationWeight panel
    JPanel cohesionWeightPanel = new JPanel(new GridLayout(2, 1));
    JLabel cohesionWeightLabel = new JLabel("Cohesion weight :");
    public JSlider cohesionWeightSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 4);

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


        //add to panel
        add(titlePanel, BorderLayout.NORTH);
        add(corePanel, BorderLayout.CENTER);

    }

}

