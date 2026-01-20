package fr.oiseaux.view;

import java.awt.*;
import java.text.NumberFormat;
import javax.swing.*;

public class ControlPanel extends JPanel {
  public JFormattedTextField birdNumberField;
  public JButton submitButton;
  public JLabel charBirdNumberLabel;

  public ControlPanel() {
    setPreferredSize(new Dimension(275, 690));
    setLayout(new BorderLayout());

    JPanel corePanel = new JPanel(new GridLayout(2, 1));

    birdNumberField = new JFormattedTextField(NumberFormat.getIntegerInstance());
    birdNumberField.setColumns(5);
    JPanel inputPanel = new JPanel();
    inputPanel.add(new JLabel("Nombre d'oiseaux :"));
    inputPanel.add(birdNumberField);

    charBirdNumberLabel = new JLabel("Actuels : 5");

    corePanel.add(inputPanel);
    corePanel.add(charBirdNumberLabel);

    submitButton = new JButton("Valider");
    JPanel submitPanel = new JPanel();
    submitPanel.add(submitButton);

    add(new JLabel("Command Board", SwingConstants.CENTER), BorderLayout.NORTH);
    add(corePanel, BorderLayout.CENTER);
    add(submitPanel, BorderLayout.SOUTH);
  }

  public void updateInfo(int count) {
    charBirdNumberLabel.setText("Actuels :" + count);
  }
}
