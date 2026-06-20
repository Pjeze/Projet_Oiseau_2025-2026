package fr.oiseaux.controller;

import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;

import fr.oiseaux.model.BirdModel;
import fr.oiseaux.model.BoidsModel;
import fr.oiseaux.model.VicsekModel;
import fr.oiseaux.view.BoidsControlPanel;
import fr.oiseaux.view.BoidsViewParamPanel;
import fr.oiseaux.view.ControlPanel;
import fr.oiseaux.view.MainWindow;
import fr.oiseaux.view.SimulationPanel;
import fr.oiseaux.view.VicsekControlPanel;
import fr.oiseaux.view.VicsekViewParamPanel;

import fr.oiseaux.model.BoundaryMode;

public class ModelController {
  //model
  public BirdModel model;
  private VicsekModel vicsekModel;
  private BoidsModel boidsModel;

  //window
  private MainWindow window;
  private SimulationPanel viewSim;
  private ControlPanel viewCtrl;
  private VicsekControlPanel viewVicsekCtrl;
  private VicsekViewParamPanel viewVicsekViewParam;
  private BoidsControlPanel viewBoidsCtrl;
  private BoidsViewParamPanel viewBoidsViewParam;
  private JMenuItem menuBoids;
  private JMenuItem menuVicsek;

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
    this.viewBoidsCtrl = viewCtrl.getBoidsControlPanel();
    this.viewBoidsViewParam = viewCtrl.getBoidsViewParamPanel();

    //Logic
    this.viewSim.simCanvas.setModel(this.model);
    this.viewCtrl.setModel(this.model);
    initListeners();
    startModelLoop();
  }

  private void initListeners() {

    initRootButtonListeners();
    initMenuBoidsListeners();
    initMenuVicsekListeners();

    //VicsekListener
    initVicsekRadiusSliderListener();
    initVicsekEtaSliderListener();
    initVicsekSpeedSliderListener();
    initVicsekBoundaryModeComboBoxListener();

    //BoidsListener
    initBoidsRadiusSliderListener();
    initBoidsSeparationRadiusSliderListener();
    initBoidsSeparationWeightSliderListener();
    initBoidsAlignmentWeightSliderListener();
    initBoidsCohesionWeightSliderListener();
    initBoidsBoundaryModeComboBoxListener();

  }

  //Root Button
  private void initRootButtonListeners() {

    viewCtrl.submitButton.addActionListener(e -> {
      try {
        String raw = viewCtrl.birdNumberField.getText().trim();
        if (raw.isEmpty()) {
          return;
        }
        int val = Integer.parseInt(raw.replaceAll("\\s", ""));
        if (val < 1) {
          return;
        }
        model.setBirdNumber(val);
        viewCtrl.updateBirdNumber(model.getBirdNumber());
        viewCtrl.birdNumberField.setValue(null);
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
        this.vicsekModel.setEta(etaFromSlider(this.viewVicsekCtrl.etaSlider.getValue()));
        this.viewVicsekViewParam.updateVicsekEta(this.vicsekModel.getEta());
        viewSim.repaint();
      }
      
    });

    this.vicsekModel.setEta(etaFromSlider(this.viewVicsekCtrl.etaSlider.getValue()));
    this.viewVicsekViewParam.updateVicsekEta(this.vicsekModel.getEta());
  }

  private static double etaFromSlider(int sliderValue) {
    return Math.toRadians(sliderValue);
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
  
  //BoundaryMode Vicsek
  private void initVicsekBoundaryModeComboBoxListener() {

    this.viewVicsekCtrl.boundaryModeComboBox.addActionListener(e -> {
        // 1. Récupérer le mode sélectionné
        BoundaryMode mode = (BoundaryMode) this.viewVicsekCtrl.boundaryModeComboBox.getSelectedItem();
        
        // 2. L'appliquer au modèle Vicsek
        this.vicsekModel.setBoundaryMode(mode);
        
        // 3. Mettre à jour l'affichage textuel
        this.viewVicsekViewParam.updateBoundaryMode(this.vicsekModel.getBoundaryMode());
        
        // 4. Rafraîchir la vue 3D
        viewSim.repaint();
    });

    // Synchronisation de l'affichage textuel à l'allumage
    this.viewVicsekViewParam.updateBoundaryMode(this.vicsekModel.getBoundaryMode());
  }

  /////////////////////////Boids Listeners///////////////////////////
  
  //boidsRadius
  private void initBoidsRadiusSliderListener() {

    this.viewBoidsCtrl.radiusSlider.addChangeListener((ChangeEvent e) -> {
      JSlider source = (JSlider) e.getSource();

      if (!source.getValueIsAdjusting()) {
        double val = this.viewBoidsCtrl.radiusSlider.getValue();
        this.boidsModel.setBoidsRadius(val);
        this.viewBoidsViewParam.updateBoidsRadius(this.boidsModel.getBoidsRadius());
        viewSim.repaint();
      }
      
    });

    this.viewBoidsViewParam.updateBoidsRadius(this.boidsModel.getBoidsRadius());
  }

  //separationRadius
  private void initBoidsSeparationRadiusSliderListener() {

    this.viewBoidsCtrl.separationRadiusSlider.addChangeListener((ChangeEvent e) -> {
      JSlider source = (JSlider) e.getSource();

      if (!source.getValueIsAdjusting()) {
        double val = this.viewBoidsCtrl.separationRadiusSlider.getValue();
        this.boidsModel.setSeparationRadius(val/10);
        this.viewBoidsViewParam.updateSeparationRadius(this.boidsModel.getSeparationRadius());
        viewSim.repaint();
      }
      
    });

    this.viewBoidsViewParam.updateSeparationRadius(this.boidsModel.getSeparationRadius());
  }

  //separationWeight
  private void initBoidsSeparationWeightSliderListener() {

    this.viewBoidsCtrl.separationWeightSlider.addChangeListener((ChangeEvent e) -> {
      JSlider source = (JSlider) e.getSource();

      if (!source.getValueIsAdjusting()) {
        double val = this.viewBoidsCtrl.separationWeightSlider.getValue();
        this.boidsModel.setSeparationWeight(val/10);
        this.viewBoidsViewParam.updateBoidsSeparationWeight(this.boidsModel.getSeparationWeight());
        viewSim.repaint();
      }
      
    });

    this.viewBoidsViewParam.updateBoidsSeparationWeight(this.boidsModel.getSeparationWeight());
  }

  //AlignmentWeight
  private void initBoidsAlignmentWeightSliderListener() {

    this.viewBoidsCtrl.alignmentWeightSlider.addChangeListener((ChangeEvent e) -> {
      JSlider source = (JSlider) e.getSource();

      if (!source.getValueIsAdjusting()) {
        double val = this.viewBoidsCtrl.alignmentWeightSlider.getValue();
        this.boidsModel.setAlignmentWeight(val/10);
        this.viewBoidsViewParam.updateBoidsAlignmentWeight(this.boidsModel.getAlignmentWeight());
        viewSim.repaint();
      }
      
    });

    this.viewBoidsViewParam.updateBoidsAlignmentWeight(this.boidsModel.getAlignmentWeight());
  }

  //CohesionWeight
  private void initBoidsCohesionWeightSliderListener() {

    this.viewBoidsCtrl.cohesionWeightSlider.addChangeListener((ChangeEvent e) -> {
      JSlider source = (JSlider) e.getSource();

      if (!source.getValueIsAdjusting()) {
        double val = this.viewBoidsCtrl.cohesionWeightSlider.getValue();
        this.boidsModel.setCohesionWeight(val/10);
        this.viewBoidsViewParam.updateBoidsCohesionWeight(this.boidsModel.getCohesionWeight());
        viewSim.repaint();
      }
      
    });

    this.viewBoidsViewParam.updateBoidsCohesionWeight(this.boidsModel.getCohesionWeight());
  }

//BoundaryMode
private void initBoidsBoundaryModeComboBoxListener() {

    this.viewBoidsCtrl.boundaryModeComboBox.addActionListener(e -> {
        // 1. Récupérer la valeur choisie dans la liste déroulante
        BoundaryMode mode = (BoundaryMode) this.viewBoidsCtrl.boundaryModeComboBox.getSelectedItem();
        
        // 2. Mettre à jour le modèle
        this.boidsModel.setBoundaryMode(mode);
        
        // 3. Mettre à jour l'affichage texte
        this.viewBoidsViewParam.updateBoundaryMode(this.boidsModel.getBoundaryMode());
        
        // 4. Redessiner
        viewSim.repaint();
    });

    // Initialisation de l'affichage au démarrage
    this.viewBoidsViewParam.updateBoundaryMode(this.boidsModel.getBoundaryMode());
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

    viewSim.simCanvas.setModel(this.model);
    viewCtrl.setModel(this.model);

    timer.start();
  }

}
