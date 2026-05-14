package fr.oiseaux.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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
            public JFormattedTextField birdNumberField = new JFormattedTextField(NumberFormat.getIntegerInstance());

            //vicsekParameter panel
            vicsekControlPanel vicsekParameterPanel;

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

          //Model Panel
          VicsekViewParamPanel viewVicsekViewParamPanel;

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
    corePanel.setLayout(new GridLayout(2,1));

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
              birdNumberField.setColumns(4);
              birdNumberPanel.add(birdNumberLabel);
              birdNumberPanel.add(birdNumberField);
              coreChangementPanel.add(birdNumberPanel, BorderLayout.NORTH);

              //vicsekParameter panel
              vicsekParameterPanel = new vicsekControlPanel();
              coreChangementPanel.add(vicsekParameterPanel, BorderLayout.CENTER);

            changementPanel.add(coreChangementPanel);

        corePanel.add(changementPanel);

        //characteristics panel
        characteristicsPanel.setLayout(new BorderLayout());

            //characteristics title panel
            charTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            charTitleLabel.setFont( new Font( "Arial", Font.BOLD, 16));
            charTitlePanel.add(charTitleLabel);
            characteristicsPanel.add(charTitlePanel, BorderLayout.NORTH);

            //core characteristics Panel
            coreCharacteristicsPanel.setLayout(new GridLayout(2, 1));

              //characteristics bird number panel
              charBirdNumberLabel.setText("Bird number: " + 5);
              charBirdNumberPanel.add(charBirdNumberLabel);
              coreCharacteristicsPanel.add(charBirdNumberPanel);

              //Model Panel
              viewVicsekViewParamPanel = new VicsekViewParamPanel();
              viewVicsekViewParamPanel.setControlPanel(this);
              coreCharacteristicsPanel.add(viewVicsekViewParamPanel);
            
            characteristicsPanel.add(coreCharacteristicsPanel, BorderLayout.CENTER);


        corePanel.add(characteristicsPanel);

    //submit panel
    submitPanel.add(submitButton);

    //model visibility
    if (this.model instanceof VicsekModel) {
      vicsekParameterPanel.setVisible(true);
      viewVicsekViewParamPanel.setVisible(true);
    } else {
      vicsekParameterPanel.setVisible(false);
      viewVicsekViewParamPanel.setVisible(false);
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
      vicsekParameterPanel.setVisible(true);
      viewVicsekViewParamPanel.setVisible(true);
    } else {
      vicsekParameterPanel.setVisible(false);
      viewVicsekViewParamPanel.setVisible(false);
    }
  }

  //getter for child panel
  public VicsekViewParamPanel getVicsekViewParamPanel() {
      return this.viewVicsekViewParamPanel;
  }
  public vicsekControlPanel getVicsekControlPanel() {
      return this.vicsekParameterPanel;
  }

  //update
  public void updateBirdNumber(int count) {
    charBirdNumberLabel.setText("Bird number : " + count);
  }
}
