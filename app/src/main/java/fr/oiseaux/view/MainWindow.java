package fr.oiseaux.view;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
  public MainWindow(SimulationPanel simPanel, ControlPanel ctrlPanel) {
    setTitle("Oiseaux");
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    add(ctrlPanel, BorderLayout.WEST);
    add(simPanel, BorderLayout.CENTER);

    getRootPane().setDefaultButton(ctrlPanel.submitButton);
    pack();
    setLocationRelativeTo(null);
  }
}
