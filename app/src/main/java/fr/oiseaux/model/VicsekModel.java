package fr.oiseaux.model;

import fr.oiseaux.model.Vector3D;
import fr.oiseaux.model.Bird;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.net.URL;

public class VicsekModel {
  public double r;
  public double eta;
  public double v0;
  private int birdNumber = 5;
  private BufferedImage birdImg;
  private List<Bird> birds;
  private Random random = new Random();

  public VicsekModel(double r, double eta, double v0) {
    this.r = r;
    this.eta = eta;
    this.v0 = v0;
    birds = new ArrayList<>();

    try {
      URL imageURL = this.getClass().getResource("/fr/oiseaux/724954.png");
      this.birdImg = ImageIO.read(imageURL);
    } catch (Exception var2) {
      System.err.println("Image introuvable");
    }
    initBirds();
  }

  private void initBirds() {
    while (birds.size() < birdNumber) {
      double vx = random.nextDouble() * v0;
      Bird b = new Bird(new Vector3D(random.nextInt(100), random.nextInt(100), 0),
          new Vector3D(vx, Math.sqrt(v0 * v0 - vx * vx), 0), 50, 50, birdImg);
      birds.add(b);
    }
    while (birds.size() > birdNumber) {
      birds.remove(birds.size() - 1);
    }
  }

  public int getBirdNumber() {
    return this.birdNumber;
  }

  public List<Bird> getBirds() {
    return this.birds;
  }

  public void setBirdNumber(int n) {
    this.birdNumber = n;
    initBirds();
  }

  public void updateMovement() {
    List<Vector3D> tabVelocities = new ArrayList<>(birds.size());
    for (Bird b : birds) {
      Vector3D vSum = new Vector3D(0, 0, 0);
      for (Bird c : birds) {
        Vector3D diff = Vector3D.minus(b.pos, c.pos);
        if (diff.x() > 50)
          diff.setX(100 - diff.x());
        if (diff.x() < -50)
          diff.setX(diff.x() + 100);

        if (diff.y() > 50)
          diff.setY(100 - diff.y());
        if (diff.y() < -50)
          diff.setY(diff.y() + 100);

        if (diff.norm2() <= r * r) {
          vSum = Vector3D.add(vSum, c.velocity);
        }
      }
      Vector3D newVelocity = vSum.normalize().scale(v0);
      tabVelocities.add(newVelocity);
      b.pos = Vector3D.add(b.pos, newVelocity);
      if (b.pos.x() > 100)
        b.pos.setX(b.pos.x() - 100);
      if (b.pos.x() < 0)
        b.pos.setX(b.pos.x() + 100);
      if (b.pos.y() > 100)
        b.pos.setY(b.pos.y() - 100);
      if (b.pos.y() < 0)
        b.pos.setY(b.pos.y() + 100);
    }
    for (int i = 0; i < birds.size(); i++) {
      birds.get(i).velocity = tabVelocities.get(i);
    }
  }
}
