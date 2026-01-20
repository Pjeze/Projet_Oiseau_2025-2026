package fr.oiseaux.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Image;
import javax.swing.ImageIcon;

public class SimpleModel {
  private List<Bird> birds;
  private int birdNumber = 5;
  private Random random = new Random();
  private Image birdImg;

  private double margin = 50;

  public SimpleModel() {
    birds = new ArrayList<>();

    try {
      birdImg = new ImageIcon(getClass().getResource("/fr/oiseaux/724954.png")).getImage();
    } catch (Exception e) {
      System.err.println("Image introuvable");
    }
    initBirds();
  }

  private void initBirds() {
    while (birds.size() < birdNumber) {
      Bird b = new Bird(50, 50, 50, 50, birdImg);
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
      b.velocityX = (random.nextInt(6) - 3) / 5.0;
      b.velocityY = (random.nextInt(6) - 3) / 5.0;

      b.x += b.velocityX;
      b.y += b.velocityY;

      if (b.y > 100)
        b.y -= 100;
      if (b.y < 0)
        b.y += 100;
      if (b.x > 100)
        b.x -= 100;
      if (b.x < 0)
        b.x += 100;
    }
  }
}
