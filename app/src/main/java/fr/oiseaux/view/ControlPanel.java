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

public class ControlPanel extends JPanel {

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

          //birdNumber panel
          JPanel birdNumberPanel = new JPanel();
          JLabel birdNumberLabel = new JLabel("Enter bird number : ");
          public JFormattedTextField birdNumberField = new JFormattedTextField(NumberFormat.getIntegerInstance());

      //characteristics panel
      JPanel characteristicsPanel = new JPanel();

        //characteristics title panel
        JPanel charTitlePanel = new JPanel();
        JLabel charTitleLabel = new JLabel("Birds Characteristics");

        //characteristics bird number panel
        JPanel charBirdNumberPanel = new JPanel();
        JLabel charBirdNumberLabel = new JLabel("");

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

            //birdNumber panel
            //bird number text field
            birdNumberField.setColumns(4);
            birdNumberPanel.add(birdNumberLabel);
            birdNumberPanel.add(birdNumberField);
            changementPanel.add(birdNumberPanel, BorderLayout.CENTER);

        corePanel.add(changementPanel);

        //characteristics panel
        characteristicsPanel.setLayout(new BorderLayout());

            //characteristics title panel
            charTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            charTitleLabel.setFont( new Font( "Arial", Font.BOLD, 16));
            charTitlePanel.add(charTitleLabel);
            characteristicsPanel.add(charTitlePanel, BorderLayout.NORTH);

            //characteristics bird number panel
            charBirdNumberLabel.setText("Bird number: " + 5);
            charBirdNumberPanel.add(charBirdNumberLabel);
            characteristicsPanel.add(charBirdNumberPanel, BorderLayout.CENTER);

        corePanel.add(characteristicsPanel);

    //submit panel
    submitPanel.add(submitButton);

    //add to the board
    setLayout(new BorderLayout());
    add(titlePanel, BorderLayout.NORTH);
    add(corePanel, BorderLayout.CENTER);
    add(submitPanel, BorderLayout.SOUTH);
    
  }

  public void updateInfo(int count) {
    charBirdNumberLabel.setText("Bird number: " + count);
  }
}
