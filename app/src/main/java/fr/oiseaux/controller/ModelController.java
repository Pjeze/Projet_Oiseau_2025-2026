package fr.oiseaux.controller;

import java.awt.event.ActionEvent;

import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;

import fr.oiseaux.model.VicsekModel;
import fr.oiseaux.view.ControlPanel;
import fr.oiseaux.view.SimulationPanel;
import fr.oiseaux.view.VicsekViewParamPanel;
import fr.oiseaux.view.vicsekControlPanel;

public class ModelController {
  private VicsekModel model;
  private SimulationPanel viewSim;
  private ControlPanel viewCtrl;
  private vicsekControlPanel viewVicsekCtrl;
  private VicsekViewParamPanel viewVicsekViewParam;
  private Timer timer;

  public ModelController(VicsekModel model, SimulationPanel viewSim, ControlPanel viewCtrl, vicsekControlPanel viewVicsekCtrl) {
    this.model = model;
    this.viewSim = viewSim;
    this.viewCtrl = viewCtrl;
    this.viewVicsekCtrl = viewCtrl.getVicsekControlPanel();
    this.viewVicsekViewParam = viewCtrl.getVicsekViewParamPanel();

    initListeners();
    startModelLoop();
  }

  private void initListeners() {

    initRootButtonListeners();
    initVicsekRadiusSliderListener();
    initVicsekEtaSliderListener();
    initVicsekSpeedSliderListener();

  }

  //Root Button
  private void initRootButtonListeners() {

    viewCtrl.submitButton.addActionListener(e -> {
      try {
        int val = Integer.parseInt(viewCtrl.birdNumberField.getText());
        model.setBirdNumber(val);
        viewCtrl.updateBirdNumber(model.getBirdNumber());
        viewCtrl.birdNumberField.setText("");
        viewSim.repaint();
      } catch (NumberFormatException ex) {
        System.out.println("Nombre Invalide");
      }
    });

    viewCtrl.updateBirdNumber(model.getBirdNumber());

  }

  //Radius
  private void initVicsekRadiusSliderListener() {

    this.viewVicsekCtrl.radiusSlider.addChangeListener((ChangeEvent e) -> {
      JSlider source = (JSlider) e.getSource();

      if (!source.getValueIsAdjusting()) {
        double val = this.viewVicsekCtrl.radiusSlider.getValue();
        model.setRadius(val);
        this.viewVicsekViewParam.updateVicsekRadius(model.getRadius());
        viewSim.repaint();
      }
      
    });

    this.viewVicsekViewParam.updateVicsekRadius(model.getRadius());

  }

  //eta
  private void initVicsekEtaSliderListener() {

    this.viewVicsekCtrl.etaSlider.addChangeListener((ChangeEvent e) -> {
      JSlider source = (JSlider) e.getSource();

      if (!source.getValueIsAdjusting()) {
        double val = this.viewVicsekCtrl.etaSlider.getValue();
        model.setEta(val * 1E-5);
        this.viewVicsekViewParam.updateVicsekEta(model.getEta());
        viewSim.repaint();
      }
      
    });

    this.viewVicsekViewParam.updateVicsekEta(model.getEta());
  }

  //speed
  private void initVicsekSpeedSliderListener() {

    this.viewVicsekCtrl.speedSlider.addChangeListener((ChangeEvent e) -> {
      JSlider source = (JSlider) e.getSource();

      if (!source.getValueIsAdjusting()) {
        double val = this.viewVicsekCtrl.speedSlider.getValue();
        model.setSpeed(val);
        this.viewVicsekViewParam.updateVicsekSpeed(model.getSpeed());
        viewSim.repaint();
      }
      
    });

    this.viewVicsekViewParam.updateVicsekSpeed(model.getSpeed());
  }

  private void startModelLoop() {
    timer = new Timer(1000/60, (ActionEvent e) -> {
      model.updateMovement();
      viewSim.repaint();
    });
    timer.start();
  }


}
