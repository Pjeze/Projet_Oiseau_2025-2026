package fr.oiseaux.controller;

import fr.oiseaux.model.VicsekModel;
import fr.oiseaux.view.ControlPanel;
import fr.oiseaux.view.SimulationPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;

public class ModelController {
  private VicsekModel model;
  private SimulationPanel viewSim;
  private ControlPanel viewCtrl;
  private Timer timer;

  public ModelController(VicsekModel model, SimulationPanel viewSim, ControlPanel viewCtrl) {
    this.model = model;
    this.viewSim = viewSim;
    this.viewCtrl = viewCtrl;

    initListeners();
    startModelLoop();
  }

  private void initListeners() {
    viewCtrl.submitButton.addActionListener(e -> {
      try {
        int val = Integer.parseInt(viewCtrl.birdNumberField.getText());
        model.setBirdNumber(val);
        viewCtrl.updateInfo(model.getBirdNumber());
        viewCtrl.birdNumberField.setText("");
        viewSim.repaint();
      } catch (NumberFormatException ex) {
        System.out.println("Nombre Invalide");
      }
    });

    viewCtrl.updateInfo(model.getBirdNumber());
  }

  private void startModelLoop() {
    timer = new Timer(100, (ActionEvent e) -> {
      model.updateMovement();
      viewSim.repaint();
    });
    timer.start();
  }
}
