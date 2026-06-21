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
  private BoundaryMode boundaryMode = BoundaryMode.CLOSED_BOX;

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

  public void setBoundaryMode(BoundaryMode mode) {this.boundaryMode = mode;}
  public BoundaryMode getBoundaryMode() {return this.boundaryMode;}

  @Override
  public void updateMovement() {
    KDTree3D tree = new KDTree3D(birds, boundaryMode);
    List<Vector3D> tabVelocities = new ArrayList<>(birds.size());
    for (int i = 0; i < birds.size(); i++) {
      Bird b = birds.get(i);
      Vector3D vSum = new Vector3D(0, 0, 0);
      int neigborCount = 0;

      for (int neighborIndex : tree.radiusSearch(b.pos.x(), b.pos.y(), b.pos.z(), radius)) {
        vSum = Vector3D.add(vSum, birds.get(neighborIndex).velocity);
        neigborCount++;
      }
      
      if (neigborCount > 0) {
        vSum = vSum.scale(1.0/neigborCount);
      }

      if (boundaryMode == BoundaryMode.CLOSED_BOX) {
        double wallThreshold = 5.0;
        double WALL_FACTOR = 5.0;

        if (b.pos.x() < wallThreshold) {
          double dist = Math.max(0.1, b.pos.x());
          vSum = Vector3D.add(vSum, new Vector3D(WALL_FACTOR/(dist*dist), 0, 0));
        }
        if (100.0 - b.pos.x() < wallThreshold) {
          double dist = Math.max(0.1, 100-b.pos.x());
          vSum = Vector3D.add(vSum, new Vector3D(-WALL_FACTOR/(dist*dist), 0, 0));
        }
        
        if (b.pos.y() < wallThreshold) {
          double dist = Math.max(0.1, b.pos.y());
          vSum = Vector3D.add(vSum, new Vector3D(0, WALL_FACTOR/(dist*dist), 0));
        }
        if (100.0 - b.pos.y() < wallThreshold) {
          double dist = Math.max(0.1, 100-b.pos.y());
          vSum = Vector3D.add(vSum, new Vector3D(0, -WALL_FACTOR/(dist*dist),0));
        }

        if (b.pos.z() < wallThreshold) {
          double dist = Math.max(0.1, b.pos.z());
          vSum = Vector3D.add(vSum, new Vector3D(0, 0, WALL_FACTOR/(dist*dist)));
        }
        if (100.0 - b.pos.z() < wallThreshold) {
          double dist = Math.max(0.1, 100-b.pos.z());
          vSum = Vector3D.add(vSum, new Vector3D(0, 0, -WALL_FACTOR/(dist*dist)));
        }

      }

      Vector3D newVelocity = applyVicsekNoise(vSum, b.velocity).scale(v0);
      tabVelocities.add(newVelocity);
    }
    for (int i = 0; i < birds.size(); i++) {
      Bird birdi = birds.get(i);
      birdi.velocity = tabVelocities.get(i);
      birdi.pos = Vector3D.add(birdi.pos,birdi.velocity); 

      if (boundaryMode == BoundaryMode.CLOSED_BOX) {
        birdi.pos.setX(Math.min(100.0, Math.max(0.0, birdi.pos.x())));
        birdi.pos.setY(Math.min(100.0, Math.max(0.0, birdi.pos.y())));
        birdi.pos.setZ(Math.min(100.0, Math.max(0.0, birdi.pos.z())));
      } else {
        if (birdi.pos.x() > 100.0) birdi.pos.setX(birdi.pos.x() - 100.0);
        if (birdi.pos.x() < 0.0)   birdi.pos.setX(birdi.pos.x() + 100.0);
        if (birdi.pos.y() > 100.0) birdi.pos.setY(birdi.pos.y() - 100.0);
        if (birdi.pos.y() < 0.0)   birdi.pos.setY(birdi.pos.y() + 100.0);
        if (birdi.pos.z() > 100.0) birdi.pos.setZ(birdi.pos.z() - 100.0);
        if (birdi.pos.z() < 0.0)   birdi.pos.setZ(birdi.pos.z() + 100.0);
      }
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
    Vector3D u = Vector3D.cross(direction, reference).normalize();
    Vector3D v = Vector3D.cross(direction, u).normalize();

    double randomAngle = random.nextDouble() * 2.0 *Math.PI;

    Vector3D randomAxis = Vector3D.add(u.scale(Math.cos(randomAngle)), v.scale(Math.sin(randomAngle)));

    return randomAxis.normalize();
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
