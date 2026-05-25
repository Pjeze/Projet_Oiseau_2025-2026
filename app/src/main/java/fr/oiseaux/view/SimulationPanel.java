package fr.oiseaux.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class SimulationPanel extends JPanel {
  //Model
  //private BirdModel model;

  public Simulation3DCanvas simCanvas;

  //Dimension
  //main window
  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  int screenWidth = (int) screenSize.getWidth();
  int screenHeight = (int) screenSize.getHeight();

  //simBoard
  int simWidth = (int) (4*screenWidth/5);
  int simHeight = (int) (screenHeight*0.9);

  //double xMin = 0, xMax = 100, yMin = 0, yMax = 100;
  //int margin = 50;

  public SimulationPanel() {
    setSize(simWidth, simHeight);
    simCanvas = new Simulation3DCanvas();
    //simCanvas.setSize(simWidth, simHeight);
    setLayout(new BorderLayout());
    add(simCanvas, BorderLayout.CENTER);
  }

  /*
  public void setModel(BirdModel mdl) {
    this.model = mdl;
  }
  
  public int toScreenX(double x) {
    return margin + (int) ((x - xMin) / (xMax - xMin) * (getWidth() - 2 * margin));
  }

  public int toScreenY(double y) {
    return margin + (int) ((y - yMin) / (yMax - yMin) * (getHeight() - 2 * margin));
  }

  private void drawBird (Graphics2D g2d, Bird b) {
    int px = toScreenX(b.pos.x());
    int py = toScreenY(b.pos.y());
    Color color = new Color((int)(b.pos.z() * 255 / 100), 255 - (int)(b.pos.z() * 255 / 100), 0);

    int length = (getWidth() - 2 * margin) / 100;
    int width  = length / 2;                       

    int[] xPoints = {  length,  -length/2, -length/2 };
    int[] yPoints = {  0,       -width,     width     };

    AffineTransform originalTransform = g2d.getTransform();

    double angle = Math.atan2(b.velocity.y(), b.velocity.x());
    g2d.translate(px, py);
    g2d.rotate(angle);

    g2d.setColor(color);
    g2d.fillPolygon(xPoints, yPoints, 3);
    g2d.setColor(color.darker());
    g2d.drawPolygon(xPoints, yPoints, 3); // outline

    g2d.setTransform(originalTransform);
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

    if (model != null) {
      for (Bird b : this.model.getBirds()) {
        drawBird(gp, b);
      }
    }
  }
    */
}
