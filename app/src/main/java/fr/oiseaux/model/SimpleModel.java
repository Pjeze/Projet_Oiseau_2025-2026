package fr.oiseaux.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleModel {
  private List<Bird> birds;
  private int birdNumber = 5;
  private Random random = new Random();

  public SimpleModel() {
    birds = new ArrayList<>();

    initBirds();
  }

  private void initBirds() {
    while (birds.size() < birdNumber) {
      Bird b = new Bird(new Vector3D(50, 50, 0), new Vector3D(2, 2, 0));
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
