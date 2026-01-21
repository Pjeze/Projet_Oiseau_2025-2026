package fr.oiseaux.view;

import fr.oiseaux.model.Bird;
import fr.oiseaux.model.SimpleModel;
import java.awt.*;
import javax.swing.JPanel;

public class SimulationPanel extends JPanel {
  private SimpleModel model;

  double xMin = 0, xMax = 100, yMin = 0, yMax = 100;
  int margin = 50;

  public SimulationPanel(SimpleModel model) {
    this.model = model;
  }

  int toScreenX(double x) {return margin + (int) ((x - xMin) / (xMax - xMin) * (getWidth() - 2 * margin));}
  int toScreenY(double y) {return margin + (int) ((y - yMin) / (yMax - yMin) * (getHeight() - 2 * margin));}
  
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    int drawableWidth = getWidth() - 2 * margin;
    int drawableHeight = getHeight() - 2 * margin;

    g2d.setColor(Color.WHITE);
    g2d.fillRect(margin, margin, drawableWidth, drawableHeight);
    g2d.setColor(Color.BLACK);
    g2d.drawRect(margin, margin, drawableWidth, drawableHeight);

    if (model != null) {
      for (Bird b : model.getBirds()) {
        int px = toScreenX(b.pos.x());
        int py = toScreenY(b.pos.y());
        if (b.img != null) {
          g.drawImage(b.img, px, py, b.width, b.height, null);
        } else {
          g.setColor(Color.RED);
          g.fillRect(px, py, b.width, b.height);
        }
      }
    }
  }
}




