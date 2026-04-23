package fr.oiseaux.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MainWindow extends JFrame {
  public MainWindow(SimulationPanel simPanel, ControlPanel ctrlPanel) {
    
    //Dimension
    //main window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = (int) screenSize.getWidth();
    int screenHeight = (int) screenSize.getHeight();

    setTitle("Oiseaux");
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setSize(screenWidth, screenHeight);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    add(ctrlPanel, BorderLayout.WEST);
    add(simPanel, BorderLayout.CENTER);

    getRootPane().setDefaultButton(ctrlPanel.submitButton);
    //pack();
    setLocationRelativeTo(null);
  }
}
