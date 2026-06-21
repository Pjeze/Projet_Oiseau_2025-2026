package fr.oiseaux.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import fr.oiseaux.model.BirdModel;
import fr.oiseaux.model.VicsekModel;

public class ControlPanel extends JPanel {
  //model
  private BirdModel model;

  //Dimension
  //main window
  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  int screenWidth = (int) screenSize.getWidth();
  int screenHeight = (int) screenSize.getHeight();
  //ctrlBoard
  int ctrlWidth = (int) (screenWidth/5);
  int ctrlHeight = (int) (screenHeight*0.9);

  //title panel
  JPanel titlePanel = new JPanel();
  JLabel titleLabel = new JLabel("Commandboard");

  //core panel
  JPanel corePanel = new JPanel();

      //changement panel
      JPanel changementPanel = new JPanel();

          //changement title panel
          JPanel changeTitlePanel = new JPanel();
          JLabel changeTitleLabel = new JLabel("Change Birds characteristics");

          //core changement panel
          JPanel coreChangementPanel = new JPanel();

            //birdNumber panel
            JPanel birdNumberPanel = new JPanel();
            JLabel birdNumberLabel = new JLabel("Enter bird number : ");
            public JFormattedTextField birdNumberField = new JFormattedTextField(createBirdNumberFormat());

            //modelControlPanel
            JPanel modelControlPanel = new JPanel();
            VicsekControlPanel vicsekControlPanel;
            BoidsControlPanel boidsControlPanel;

      //characteristics panel
      JPanel characteristicsPanel = new JPanel();

        //characteristics title panel
        JPanel charTitlePanel = new JPanel();
        JLabel charTitleLabel = new JLabel("Birds Characteristics");

        //core characteristics panel
        JPanel coreCharacteristicsPanel = new JPanel();

          //characteristics bird number panel
          JPanel charBirdNumberPanel = new JPanel();
          JLabel charBirdNumberLabel = new JLabel("");

          //ModelViewPanel
          JPanel modelViewPanel = new JPanel();
          VicsekViewParamPanel viewVicsekParamPanel;
          BoidsViewParamPanel viewBoidsParamPanel;

  //submit pannel
  JPanel submitPanel = new JPanel();
  public JButton submitButton = new JButton("Submit");

  public ControlPanel() {
    setPreferredSize(new Dimension(ctrlWidth, ctrlHeight));
    setLayout(new BorderLayout());


    //title panel
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    titleLabel.setFont( new Font( "Arial", Font.BOLD, 20));
    titlePanel.add(titleLabel);

    //core panel
    corePanel.setLayout(new BorderLayout());

        //changement panel
        changementPanel.setLayout(new BorderLayout());

            //changement title panel
            changeTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            changeTitleLabel.setFont( new Font( "Arial", Font.BOLD, 16));
            changeTitlePanel.add(changeTitleLabel);
            changementPanel.add(changeTitlePanel, BorderLayout.NORTH);

            //core changement panel
            coreChangementPanel.setLayout(new BorderLayout());

              //birdNumber panel
              birdNumberField.setColumns(6);
              birdNumberPanel.add(birdNumberLabel);
              birdNumberPanel.add(birdNumberField);
              coreChangementPanel.add(birdNumberPanel, BorderLayout.NORTH);

              //modelControlPanel
              vicsekControlPanel = new VicsekControlPanel();
              boidsControlPanel = new BoidsControlPanel();
              coreChangementPanel.add(modelControlPanel, BorderLayout.CENTER);

            changementPanel.add(coreChangementPanel, BorderLayout.CENTER);

        corePanel.add(changementPanel, BorderLayout.NORTH);

        //characteristics panel
        characteristicsPanel.setLayout(new BorderLayout());

            //characteristics title panel
            charTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            charTitleLabel.setFont( new Font( "Arial", Font.BOLD, 16));
            charTitlePanel.add(charTitleLabel);
            characteristicsPanel.add(charTitlePanel, BorderLayout.NORTH);

            //core characteristics Panel
            coreCharacteristicsPanel.setLayout(new BorderLayout());

              //characteristics bird number panel
              charBirdNumberLabel.setText("Bird number : " + 5);
              charBirdNumberPanel.add(charBirdNumberLabel);
              coreCharacteristicsPanel.add(charBirdNumberPanel, BorderLayout.NORTH);

              //ModelViewPanel
              viewVicsekParamPanel = new VicsekViewParamPanel();
              viewVicsekParamPanel.setControlPanel(this);
              viewBoidsParamPanel = new BoidsViewParamPanel();
              coreCharacteristicsPanel.add(modelViewPanel, BorderLayout.CENTER);
            
            characteristicsPanel.add(coreCharacteristicsPanel, BorderLayout.CENTER);


        corePanel.add(characteristicsPanel, BorderLayout.CENTER);

    //submit panel
    submitPanel.add(submitButton);

    //model visibility
    if (this.model instanceof VicsekModel) {
      modelControlPanel.add(vicsekControlPanel);
      modelViewPanel.add(viewVicsekParamPanel);
    } else {
      modelControlPanel.add(boidsControlPanel);
      modelViewPanel.add(viewBoidsParamPanel);
    }
    //add to the board
    setLayout(new BorderLayout());
    add(titlePanel, BorderLayout.NORTH);
    add(corePanel, BorderLayout.CENTER);
    add(submitPanel, BorderLayout.SOUTH);
    
  }

  //setter for model
  public void setModel(BirdModel mdl) {
    this.model = mdl;
  }

  public void updateControlPanel(int modelType) {
    if (this.model instanceof VicsekModel) {
      this.modelControlPanel.removeAll();
      this.modelViewPanel.removeAll();
      this.modelControlPanel.add(this.vicsekControlPanel);
      this.modelViewPanel.add(this.viewVicsekParamPanel);
    } else {
      this.modelControlPanel.removeAll();
      this.modelViewPanel.removeAll();
      this.modelControlPanel.add(this.boidsControlPanel);
      this.modelViewPanel.add(this.viewBoidsParamPanel);
    }
    this.changementPanel.revalidate();
    this.changementPanel.repaint();
    this.characteristicsPanel.revalidate();
    this.characteristicsPanel.repaint();
  }

  //getter for child panel
  public VicsekViewParamPanel getVicsekViewParamPanel() {
      return this.viewVicsekParamPanel;
  }
  public VicsekControlPanel getVicsekControlPanel() {
      return this.vicsekControlPanel;
  }
  public BoidsViewParamPanel getBoidsViewParamPanel() {
      return this.viewBoidsParamPanel;
  }
  public BoidsControlPanel getBoidsControlPanel() {
      return this.boidsControlPanel;
  }

  //update
  public void updateBirdNumber(int count) {
    charBirdNumberLabel.setText("Bird number : " + count);
  }

  private static NumberFormat createBirdNumberFormat() {
    NumberFormat format = NumberFormat.getIntegerInstance();
    format.setGroupingUsed(false);
    return format;
  }

}
