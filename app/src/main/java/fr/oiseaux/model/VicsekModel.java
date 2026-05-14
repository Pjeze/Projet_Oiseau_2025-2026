package fr.oiseaux.model;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class VicsekModel implements BirdModel {
  public double radius;
  public double eta;
  public double v0;
  private int birdNumber = 5;
  private BufferedImage birdImg;
  private List<Bird> birds;
  private Random random = new Random();

  public VicsekModel(double r, double eta, double v0) {
    this.radius = r;
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
      Vector3D v = new Vector3D(random.nextDouble()-0.5,random.nextDouble()-0.5, 0);
      Bird b = new Bird(new Vector3D(random.nextInt(100), random.nextInt(100), 0),
          v.normalize().scale(v0), 50, 50, birdImg);
      birds.add(b);
    }
    while (birds.size() > birdNumber) {
      birds.remove(birds.size() - 1);
    }
  }

  //setter and getter for bird
  @Override
  public int getBirdNumber() {
    return this.birdNumber;
  }

  @Override
  public List<Bird> getBirds() {
    return this.birds;
  }

  @Override
  public void setBirdNumber(int n) {
    this.birdNumber = n;
    initBirds();
  }

  //setter and getter for radius
  public void setRadius(double n) {
    this.radius = n;
  }

  public double getRadius() {
    return this.radius;
  }

  //setter and getter for eta
  public void setEta(double n) {
    this.eta = n;
  }

  public double getEta() {
    return this.eta;
  }

  //setter and getter for speed
  public void setSpeed(double n) {
    this.v0 = n;
  }

  public double getSpeed() {
    return this.v0;
  }

  @Override
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

        if (diff.norm2() <= radius * radius) {
          vSum = Vector3D.add(vSum, c.velocity);
        }
      }
      Vector3D randomNoise = new Vector3D((random.nextDouble()-0.5)*2, (random.nextDouble()-0.5)*2, 0);
      while (randomNoise.norm2() > 1.0) {
          randomNoise = new Vector3D((random.nextDouble()-0.5)*2, (random.nextDouble()-0.5)*2, 0);
      }
      randomNoise = randomNoise.normalize();
      Vector3D newVelocity = Vector3D.add(vSum, randomNoise.scale(eta)).normalize().scale(v0);
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
