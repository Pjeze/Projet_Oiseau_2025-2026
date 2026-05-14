package fr.oiseaux.controller;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;

import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;

import fr.oiseaux.model.BirdModel;
import fr.oiseaux.model.BoidsModel;
import fr.oiseaux.model.VicsekModel;
import fr.oiseaux.view.ControlPanel;
import fr.oiseaux.view.MainWindow;
import fr.oiseaux.view.SimulationPanel;
import fr.oiseaux.view.VicsekViewParamPanel;
import fr.oiseaux.view.vicsekControlPanel;

public class ModelController {
  //model
  public BirdModel model;
  private VicsekModel vicsekModel;
  private BoidsModel boidsModel;

  //window
  private MainWindow window;
  private SimulationPanel viewSim;
  private ControlPanel viewCtrl;
  private vicsekControlPanel viewVicsekCtrl;
  private VicsekViewParamPanel viewVicsekViewParam;
  private MenuItem menuBoids;
  private MenuItem menuVicsek;

  //Logic
  private Timer timer;

  public ModelController(VicsekModel vicModel, BoidsModel bdsModel, MainWindow wdw, SimulationPanel viewSim, ControlPanel viewCtrl) {
    //model
    this.model = vicModel;
    this.vicsekModel = vicModel;
    this.boidsModel = bdsModel;

    //window
    this.window = wdw;
    this.viewSim = viewSim;
    this.viewCtrl = viewCtrl;
    this.viewVicsekCtrl = viewCtrl.getVicsekControlPanel();
    this.viewVicsekViewParam = viewCtrl.getVicsekViewParamPanel();

    //Logic
    this.viewSim.setModel(this.model);
    this.viewCtrl.setModel(this.model);
    initListeners();
    startModelLoop();
  }

  private void initListeners() {

    initRootButtonListeners();
    initMenuBoidsListeners();
    initMenuVicsekListeners();

    if (this.model instanceof VicsekModel) {
      initVicsekRadiusSliderListener();
      initVicsekEtaSliderListener();
      initVicsekSpeedSliderListener();
    }
    

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

  ////////////////////////Menu Bar Listeners////////////////////////
  //Menu Boids
  private void initMenuBoidsListeners () {
    this.menuBoids = this.window.getMenuWindow().getMenuBoids();
    this.menuBoids.addActionListener(e -> {
      this.switchModel(1);
      this.viewCtrl.updateControlPanel(1);
    });
  }

  //Menu Vicsek
  private void initMenuVicsekListeners () {
    this.menuVicsek = this.window.getMenuWindow().getMenuVicsek();
    this.menuVicsek.addActionListener(e -> {
      this.switchModel(0);
      this.viewCtrl.updateControlPanel(0);
    });
  }


  ////////////////////////Vicsek Listeners//////////////////////////

  //Radius
  private void initVicsekRadiusSliderListener() {

    this.viewVicsekCtrl.radiusSlider.addChangeListener((ChangeEvent e) -> {
      JSlider source = (JSlider) e.getSource();

      if (!source.getValueIsAdjusting()) {
        double val = this.viewVicsekCtrl.radiusSlider.getValue();
        this.vicsekModel.setRadius(val);
        this.viewVicsekViewParam.updateVicsekRadius(this.vicsekModel.getRadius());
        viewSim.repaint();
      }
      
    });

    this.viewVicsekViewParam.updateVicsekRadius(this.vicsekModel.getRadius());

  }

  //eta
  private void initVicsekEtaSliderListener() {

    this.viewVicsekCtrl.etaSlider.addChangeListener((ChangeEvent e) -> {
      JSlider source = (JSlider) e.getSource();

      if (!source.getValueIsAdjusting()) {
        double val = this.viewVicsekCtrl.etaSlider.getValue();
        this.vicsekModel.setEta(val * 1E-5);
        this.viewVicsekViewParam.updateVicsekEta(this.vicsekModel.getEta());
        viewSim.repaint();
      }
      
    });

    this.viewVicsekViewParam.updateVicsekEta(this.vicsekModel.getEta());
  }

  //speed
  private void initVicsekSpeedSliderListener() {

    this.viewVicsekCtrl.speedSlider.addChangeListener((ChangeEvent e) -> {
      JSlider source = (JSlider) e.getSource();

      if (!source.getValueIsAdjusting()) {
        double val = this.viewVicsekCtrl.speedSlider.getValue();
        this.vicsekModel.setSpeed(val/100);
        this.viewVicsekViewParam.updateVicsekSpeed(this.vicsekModel.getSpeed());
        viewSim.repaint();
      }
      
    });

    this.viewVicsekViewParam.updateVicsekSpeed(this.vicsekModel.getSpeed());
  }

  //Logic
  private void startModelLoop() {
    timer = new Timer(1000/60, (ActionEvent e) -> {
      model.updateMovement();
      viewSim.repaint();
    });
    timer.start();
  }

  private void switchModel(int modelType) {
    timer.stop();
    if (modelType == 0) {
      this.model = this.vicsekModel;
    } else {
      this.model = this.boidsModel;
    }

    viewSim.setModel(this.model);
    viewCtrl.setModel(this.model);

    timer.start();
  }

}
