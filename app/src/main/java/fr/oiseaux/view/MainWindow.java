package fr.oiseaux.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

public class MainWindow extends JFrame {

  MenuMainWindow menuWindow = new MenuMainWindow();

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

    setJMenuBar(menuWindow);

    add(ctrlPanel, BorderLayout.WEST);
    add(simPanel, BorderLayout.CENTER);

    getRootPane().setDefaultButton(ctrlPanel.submitButton);

    InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap actionMap = getRootPane().getActionMap();

    inputMap.put(KeyStroke.getKeyStroke("Q"), "moveLeft");
    inputMap.put(KeyStroke.getKeyStroke("D"), "moveRight");
    inputMap.put(KeyStroke.getKeyStroke("Z"), "moveUp");
    inputMap.put(KeyStroke.getKeyStroke("S"), "moveDown");

    actionMap.put("moveLeft", new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            simPanel.simCanvas.translate(-2f, 0);
        }
    });
    actionMap.put("moveRight", new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            simPanel.simCanvas.translate(2f, 0);
        }
    });
    actionMap.put("moveUp", new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            simPanel.simCanvas.translate(0, 2f);
        }
    });
    actionMap.put("moveDown", new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            simPanel.simCanvas.translate(0, -2f);
        }
    });

    //pack();
    setLocationRelativeTo(null);
  }

  public MenuMainWindow getMenuWindow() {
    return this.menuWindow;
  }
}
