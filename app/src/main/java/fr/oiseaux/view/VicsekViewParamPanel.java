package fr.oiseaux.view;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import fr.oiseaux.model.BoundaryMode;
import fr.oiseaux.model.VicsekModel;

public class VicsekViewParamPanel extends JPanel {

    private VicsekModel model;
    private ControlPanel ctrlPanel;

    //title
    JLabel titleLabel = new JLabel();

    //radius panel
    JPanel radiusPanel = new JPanel();
    JLabel radiusLabel = new JLabel();

    //eta panel
    JPanel etaPanel = new JPanel();
    JLabel etaLabel = new JLabel();

    //speed panel
    JPanel speedPanel = new JPanel();
    JLabel speedLabel = new JLabel();

    JPanel boundaryModePanel = new JPanel();
    JLabel boundaryModeLabel = new JLabel();

    public VicsekViewParamPanel() {
        setLayout(new GridLayout(5, 1));

        //title
        titleLabel.setText("Vicsek Parameters");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont( new Font( "Arial", Font.BOLD, 14));
        add(titleLabel);

        //radius
        radiusLabel.setText("Field View Radius : 10");
        radiusPanel.add(radiusLabel);
        add(radiusPanel);

        //eta
        etaLabel.setText("Random noise : 10°");
        etaPanel.add(etaLabel);
        add(etaPanel);

        //speed
        speedLabel.setText("Bird speed : 10");
        speedPanel.add(speedLabel);
        add(speedPanel);

        boundaryModeLabel.setText("Boundary mode : CLOSED_BOX");
        boundaryModePanel.add(boundaryModeLabel);
        add(boundaryModePanel);
    }

    public void setControlPanel (ControlPanel controlPanel) {
        this.ctrlPanel = controlPanel;
    }

    public void updateVicsekRadius(double r) {
        radiusLabel.setText("Visual field radius : " + r);
    }

    public void updateVicsekEta(double eta) {
        etaLabel.setText(String.format("Random noise : %.0f°", Math.toDegrees(eta)));
    }

    public void updateVicsekSpeed(double speed) {
        speedLabel.setText("Bird speed : " + speed);
    }

    public void updateBoundaryMode(BoundaryMode mode) {
        boundaryModeLabel.setText("Boundary mode : " + mode.name());
    }

}
