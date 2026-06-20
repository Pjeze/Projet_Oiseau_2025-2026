package fr.oiseaux.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.lang.Math;

public class VicsekModel implements BirdModel {
  public double radius;
  public double eta;
  public double v0;
  private int birdNumber = 5;
  private List<Bird> birds;
  private Random random = new Random();

  public VicsekModel(double r, double eta, double v0) {
    this.radius = r;
    this.eta = eta;
    this.v0 = v0;
    birds = new ArrayList<>();

    initBirds();
  }

  private void initBirds() {
    while (birds.size() < birdNumber) {
      Vector3D v = new Vector3D(
              random.nextDouble()-0.5,
              random.nextDouble()-0.5,
              random.nextDouble()-0.5);
      Bird b = new Bird(new Vector3D(
              random.nextInt(100),
              random.nextInt(100),
              random.nextInt(100)),
          v.normalize().scale(v0)
        );
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
  public void setRadius(double n) { this.radius = n; }
  public double getRadius() { return this.radius; }

  //setter and getter for eta
  public void setEta(double n) { this.eta = n; }
  public double getEta() { return this.eta; }

  //setter and getter for speed
  public void setSpeed(double n) { this.v0 = n; }
  public double getSpeed() { return this.v0; }

  @Override
  public void updateMovement() {
    KDTree3D tree = new KDTree3D(birds);
    List<Vector3D> tabVelocities = new ArrayList<>(birds.size());
    for (int i = 0; i < birds.size(); i++) {
      Bird b = birds.get(i);
      Vector3D vSum = new Vector3D(0, 0, 0);
      for (int neighborIndex : tree.radiusSearch(b.pos.x(), b.pos.y(), b.pos.z(), radius)) {
        vSum = Vector3D.add(vSum, birds.get(neighborIndex).velocity);
      }
      Vector3D newVelocity = applyVicsekNoise(vSum, b.velocity).scale(v0);
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
      if (b.pos.z() > 100)
        b.pos.setZ(b.pos.z() - 100);
      if (b.pos.z() < 0)
        b.pos.setZ(b.pos.z() + 100);
    }
    for (int i = 0; i < birds.size(); i++) {
      birds.get(i).velocity = tabVelocities.get(i);
    }
  }

  private Vector3D applyVicsekNoise(Vector3D neighborVelocitySum, Vector3D currentVelocity) {
    Vector3D baseDirection = neighborVelocitySum.norm2() > 1e-12
        ? neighborVelocitySum
        : currentVelocity;
    if (baseDirection.norm2() < 1e-12) {
      baseDirection = randomUnitVector();
    } else {
      baseDirection = baseDirection.normalize();
    }

    if (eta <= 0.0) {
      return baseDirection;
    }

    double angle = (random.nextDouble() * 2.0 - 1.0) * eta;
    Vector3D axis = randomPerpendicularUnit(baseDirection);
    return rotateAroundAxis(baseDirection, axis, angle);
  }

  private Vector3D randomUnitVector() {
    Vector3D vector;
    do {
      vector = new Vector3D(
          random.nextDouble() * 2.0 - 1.0,
          random.nextDouble() * 2.0 - 1.0,
          random.nextDouble() * 2.0 - 1.0);
    } while (vector.norm2() > 1.0 || vector.norm2() < 1e-12);
    return vector.normalize();
  }

  private Vector3D randomPerpendicularUnit(Vector3D direction) {
    Vector3D reference = Math.abs(direction.x()) < 0.9
        ? new Vector3D(1, 0, 0)
        : new Vector3D(0, 1, 0);
    return Vector3D.cross(direction, reference).normalize();
  }

  private Vector3D rotateAroundAxis(Vector3D vector, Vector3D axis, double angle) {
    double cos = Math.cos(angle);
    double sin = Math.sin(angle);
    Vector3D rotated = Vector3D.add(
        vector.scale(cos),
        Vector3D.cross(axis, vector).scale(sin));
    return rotated.normalize();
  }
}
