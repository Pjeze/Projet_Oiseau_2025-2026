package fr.oiseaux.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.net.URL;

public class SimpleModel {
  private List<Bird> birds;
  private int birdNumber = 5;
  private Random random = new Random();
  private BufferedImage birdImg;

  public SimpleModel() {
    birds = new ArrayList<>();

    try {
      URL imageURL = getClass().getResource("/fr/oiseaux/724954.png");
      birdImg = ImageIO.read(imageURL);
    } catch (Exception e) {
      System.err.println("Image introuvable");
    }
    initBirds();
  }

  private void initBirds() {
    while (birds.size() < birdNumber) {
      Bird b = new Bird(new Vector3D(50, 50, 0), new Vector3D(2, 2, 0), 50, 50, birdImg);
      birds.add(b);
    }
    while (birds.size() > birdNumber) {
      birds.remove(birds.size() - 1);
    }
  }

  public void setBirdNumber(int n) {
    this.birdNumber = n;
    initBirds();
  }

  public int getBirdNumber() {
    return birdNumber;
  }

  public List<Bird> getBirds() {
    return birds;
  }

  public void updateMovement() {
    for (Bird b : birds) {
      b.velocity.setX(((random.nextInt(6) - 3) / 5.0));
      b.velocity.setY(((random.nextInt(6) - 3) / 5.0));

      b.pos = Vector3D.add(b.pos, b.velocity);

      if (b.pos.y() > 100)
        b.pos.setY(b.pos.y() - 100);
      if (b.pos.y() < 0)
        b.pos.setY(b.pos.y() + 100);
      if (b.pos.x() > 100)
        b.pos.setX(b.pos.x() - 100);
      if (b.pos.x() < 0)
        b.pos.setX(b.pos.x() + 100);

    }
  }
}
