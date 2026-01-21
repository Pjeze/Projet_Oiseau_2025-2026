package fr.oiseaux.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import fr.oiseaux.model.Bird;
import fr.oiseaux.model.SimpleModel;

public class SimulationPanel extends JPanel {
  private SimpleModel model;

  double xMin = 0, xMax = 100, yMin = 0, yMax = 100;
  int margin = 50;

  public SimulationPanel(SimpleModel model) {
    this.model = model;
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

    if (model != null) {
      for (Bird b : model.getBirds()) {
        int px = toScreenX(b.pos.x());
        int py = toScreenY(b.pos.y());

        if (b.img != null) {
          int width = b.img.getWidth();
          int height = b.img.getHeight();

          double orientationAngle = Math
              .acos(b.velocity.y() / Math.sqrt(b.velocity.x() * b.velocity.x() + b.velocity.y() * b.velocity.y()));
          int newWidth = (int) Math.abs(width * Math.cos(orientationAngle))
              + (int) Math.abs(height * Math.sin(orientationAngle));
          int newHeight = (int) Math.abs(height * Math.cos(orientationAngle))
              + (int) Math.abs(width * Math.sin(orientationAngle));

          BufferedImage outputImage = new BufferedImage(newWidth, newHeight, b.img.getType());

          if (b.velocity.x() > 0)
            orientationAngle = orientationAngle + Math.PI;

          AffineTransform transform = new AffineTransform();
          transform.rotate(orientationAngle, newWidth / 2, newHeight / 2);
          transform.translate((newWidth - width) / 2, (newHeight - height) / 2);

          Graphics2D g2d = outputImage.createGraphics();
          g2d.setTransform(transform);

          g2d.drawImage(b.img, px, py, b.width, b.height, null);
          g2d.dispose();
        } else {
          g.setColor(Color.RED);
          g.fillRect(px, py, b.width, b.height);
        }
      }
    }
  }
}
