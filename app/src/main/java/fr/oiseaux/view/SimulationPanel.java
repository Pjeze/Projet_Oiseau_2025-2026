package fr.oiseaux.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import fr.oiseaux.model.Bird;
import fr.oiseaux.model.BirdModel;
import fr.oiseaux.model.VicsekModel;

public class SimulationPanel extends JPanel {
  //Model
  private BirdModel model;

  //Dimension
  //main window
  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  int screenWidth = (int) screenSize.getWidth();
  int screenHeight = (int) screenSize.getHeight();

  //simBoard
  int simWidth = (int) (4*screenWidth/5);
  int simHeight = (int) (screenHeight*0.9);

  double xMin = 0, xMax = 100, yMin = 0, yMax = 100;
  int margin = 50;

  public SimulationPanel() {
    setSize(simWidth, simHeight);
  }


  public void setModel(BirdModel mdl) {
    this.model = mdl;
  }

  
  int toScreenX(double x) {
    return margin + (int) ((x - xMin) / (xMax - xMin) * (getWidth() - 2 * margin));
  }

  int toScreenY(double y) {
    return margin + (int) ((y - yMin) / (yMax - yMin) * (getHeight() - 2 * margin));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D gp = (Graphics2D) g;

    int drawableWidth = getWidth() - 2 * margin;
    int drawableHeight = getHeight() - 2 * margin;

    gp.setColor(Color.WHITE);
    gp.fillRect(margin, margin, drawableWidth, drawableHeight);
    gp.setColor(Color.BLACK);
    gp.drawRect(margin, margin, drawableWidth, drawableHeight);

    if (this.model instanceof VicsekModel) {
      for (Bird b : model.getBirds()) {
        int px = toScreenX(b.pos.x());
        int py = toScreenY(b.pos.y());

        if (b.img != null) {
          AffineTransform originalTransform = gp.getTransform();

          double angle = Math.atan2(b.velocity.y(), b.velocity.x());

          gp.translate(px, py);

          gp.rotate(angle);

          gp.drawImage(b.img, -b.width / 2, -b.height / 2, b.width, b.height, null);

          gp.setTransform(originalTransform);
        } else {
          g.setColor(Color.RED);
          g.fillRect(px, py, b.width, b.height);
        }
      }
    } else {
      for (Bird b : model.getBirds()) {
        int px = toScreenX(b.pos.x());
        int py = toScreenY(b.pos.y());

        if (b.img != null) {
          AffineTransform originalTransform = gp.getTransform();

          double angle = Math.atan2(b.velocity.y(), b.velocity.x());

          gp.translate(px, py);

          gp.rotate(angle);

          gp.drawImage(b.img, -b.width / 2, -b.height / 2, b.width, b.height, null);

          gp.setTransform(originalTransform);
        } else {
          g.setColor(Color.RED);
          g.fillRect(px, py, b.width, b.height);
        }
      }
    }
  }
}
