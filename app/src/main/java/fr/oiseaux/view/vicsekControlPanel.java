package fr.oiseaux.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;



public class vicsekControlPanel extends JPanel {

    //title panel
    JPanel titlePanel = new JPanel();
    JLabel titleLabel = new JLabel("Vicsek Parameters");

    //core panel
    JPanel corePanel = new JPanel(new GridLayout(3, 1));

    //Visual field radius panel
    JPanel radiusPanel = new JPanel(new GridLayout(2, 1));
    JLabel radiusLabel = new JLabel("Visual field radius :");
    public JSlider radiusSlider = new JSlider(JSlider.HORIZONTAL, 1,21, 8);

    //eta (Interference) panel 
    JPanel etaPanel = new JPanel(new GridLayout(2, 1));
    JLabel etaLabel = new JLabel("Random noise (x 1E-5):");
    public JSlider etaSlider = new JSlider(JSlider.HORIZONTAL, 1, 51, 25);

    //Speed panel
    JPanel speedPanel = new JPanel(new GridLayout(2, 1));
    JLabel speedLabel = new JLabel("Bird speed :");
    public JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 1, 101, 20);

    public vicsekControlPanel() {
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
        radiusSlider.setPaintLabels(true);
        radiusPanel.add(radiusSlider);
        corePanel.add(radiusPanel);

        //eta (bruit)
        etaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        etaPanel.add(etaLabel);
        etaSlider.setMajorTickSpacing(5);
        etaSlider.setMinorTickSpacing(1);
        etaSlider.setPaintTicks(true);
        etaSlider.setPaintLabels(true);
        etaPanel.add(etaSlider);
        corePanel.add(etaPanel);

        //speed (bruit)
        speedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        speedPanel.add(speedLabel);
        speedSlider.setMajorTickSpacing(10);
        speedSlider.setMinorTickSpacing(5);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedPanel.add(speedSlider);
        corePanel.add(speedPanel);


        //add to panel
        add(titlePanel, BorderLayout.NORTH);
        add(corePanel, BorderLayout.CENTER);

    }

}
