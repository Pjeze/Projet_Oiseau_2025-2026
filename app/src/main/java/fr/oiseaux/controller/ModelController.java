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
    this.model = bdsModel;
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
    initBoidsObstacleAvoidanceRangeSliderListener();
    initBoidsBoundaryModeComboBoxListener();

    //VicsekListener
    initVicsekObstacleAvoidanceRangeSliderListener();

  }

  //Root Button
  private void initRootButtonListeners() {

    viewCtrl.submitButton.addActionListener(e -> {
        // --- NEW CODE: OBSTACLE SHAPE CHANGE ---
                int selectedShape = 0; // Default to Cube

                // Try to read the shape from the visible panel (preferred),
                // then fall back to the current model if needed.
                try {
                  if (viewCtrl.getVicsekControlPanel() != null && viewCtrl.getVicsekControlPanel().isShowing()) {
                    selectedShape = viewCtrl.getVicsekControlPanel().obstacleShapeComboBox.getSelectedIndex();
                  } else if (viewCtrl.getBoidsControlPanel() != null && viewCtrl.getBoidsControlPanel().isShowing()) {
                    selectedShape = viewCtrl.getBoidsControlPanel().obstacleShapeComboBox.getSelectedIndex();
                  } else {
                    // Fallback: use current model state
                    if (model instanceof fr.oiseaux.model.VicsekModel) {
                      selectedShape = viewCtrl.getVicsekControlPanel().obstacleShapeComboBox.getSelectedIndex();
                    } else {
                      selectedShape = viewCtrl.getBoidsControlPanel().obstacleShapeComboBox.getSelectedIndex();
                    }
                  }
                } catch (Exception ex) {
                  // In case of unexpected error, keep default value and log
                  System.err.println("Error reading obstacle shape: " + ex.getMessage());
                  selectedShape = 0;
                }
              
             
              
                // Update obstacle: if "None" (index 0) clear the list,
                // otherwise add the chosen shape (model types start at 0 for Cube)
                if (model.getObstacles() != null) {
                  model.getObstacles().clear();
                  if (selectedShape != 0) {
                    int type = selectedShape - 1; // shift index because 0 == None
                    double obsX = 50.0;
                    double obsY = 50.0;
                    double obsZ = 50.0;
                    if (type == 2) {
                      // Cone: position 50, 50, 0
                      obsZ = 0.0;
                    }
                    model.getObstacles().add(new fr.oiseaux.model.Obstacles(obsX, obsY, obsZ, 30.0, type));
                  }
                }
              // --------------------------------------------------------
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
        System.out.println("Invalid number");
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

  private void initVicsekObstacleAvoidanceRangeSliderListener() {
    this.viewVicsekCtrl.obstacleAvoidanceRangeSlider.addChangeListener((ChangeEvent e) -> {
      JSlider source = (JSlider) e.getSource();
      if (!source.getValueIsAdjusting()) {
        double val = this.viewVicsekCtrl.obstacleAvoidanceRangeSlider.getValue();
        this.vicsekModel.setObstacleAvoidanceRange(val);
        this.viewVicsekViewParam.updateVicsekObstacleAvoidanceRange(this.vicsekModel.getObstacleAvoidanceRange());
        viewSim.repaint();
      }
    });
    this.viewVicsekViewParam.updateVicsekObstacleAvoidanceRange(this.vicsekModel.getObstacleAvoidanceRange());
  }

  //BoundaryMode Vicsek
  private void initVicsekBoundaryModeComboBoxListener() {

    this.viewVicsekCtrl.boundaryModeComboBox.addActionListener(e -> {
        // 1. Get the selected mode
        BoundaryMode mode = (BoundaryMode) this.viewVicsekCtrl.boundaryModeComboBox.getSelectedItem();
        
        // 2. Apply it to the Vicsek model
        this.vicsekModel.setBoundaryMode(mode);
        
        // 3. Update the text display
        this.viewVicsekViewParam.updateBoundaryMode(this.vicsekModel.getBoundaryMode());
        
        // 4. Refresh the 3D view
        viewSim.repaint();
    });

    // Sync text display at startup
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

  private void initBoidsObstacleAvoidanceRangeSliderListener() {
    this.viewBoidsCtrl.obstacleAvoidanceRangeSlider.addChangeListener((ChangeEvent e) -> {
      JSlider source = (JSlider) e.getSource();
      if (!source.getValueIsAdjusting()) {
        double val = this.viewBoidsCtrl.obstacleAvoidanceRangeSlider.getValue();
        this.boidsModel.setObstacleAvoidanceRange(val);
        this.viewBoidsViewParam.updateBoidsObstacleAvoidanceRange(this.boidsModel.getObstacleAvoidanceRange());
        viewSim.repaint();
      }
    });
    this.viewBoidsViewParam.updateBoidsObstacleAvoidanceRange(this.boidsModel.getObstacleAvoidanceRange());
  }

//BoundaryMode
private void initBoidsBoundaryModeComboBoxListener() {

    this.viewBoidsCtrl.boundaryModeComboBox.addActionListener(e -> {
        // 1. Get the selected boundary mode
        BoundaryMode mode = (BoundaryMode) this.viewBoidsCtrl.boundaryModeComboBox.getSelectedItem();
        
        // 2. Update the model
        this.boidsModel.setBoundaryMode(mode);
        
        // 3. Update text display
        this.viewBoidsViewParam.updateBoundaryMode(this.boidsModel.getBoundaryMode());
        
        // 4. Redraw
        viewSim.repaint();
    });

    // Initialize display at startup
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
