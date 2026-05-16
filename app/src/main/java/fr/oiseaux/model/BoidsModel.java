package fr.oiseaux.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoidsModel implements BirdModel {
    public double radius;       // neighborhood radius
    public double separationRadius; // radius for separation rule
    public double v0;           // base speed

    // Rule weights
    public double separationWeight = 1.5;
    public double alignmentWeight = 1.0;
    public double cohesionWeight = 1.0;

    private int birdNumber = 5;
    private List<Bird> birds;
    private Random random = new Random();

    public BoidsModel(double radius, double separationRadius, double v0) {
        this.radius = radius;
        this.separationRadius = separationRadius;
        this.v0 = v0;
        birds = new ArrayList<>();

        initBirds();
    }

    private void initBirds() {
        while (birds.size() < birdNumber) {
            Vector3D v = new Vector3D(random.nextDouble()-0.5, random.nextDouble()-0.5, 0);
            Bird b = new Bird(
                new Vector3D(random.nextInt(100), random.nextInt(100), 0),
                v.normalize().scale(v0)
            );
            birds.add(b);
        }
        while (birds.size() > birdNumber) {
            birds.remove(birds.size() - 1);
        }
    }

    //setter and getter for radius
    public void setBoidsRadius(double n) {
        this.radius = n;
    }
    public double getBoidsRadius() {
        return this.radius;
    }

    //setter and getter for separationradius
    public void setSeparationRadius(double n) {
        this.separationRadius = n;
    }
    public double getSeparationRadius() {
        return this.separationRadius;
    }

    //setter and getter for separationWeight
    public void setSeparationWeight(double n) {
        this.separationWeight = n;
    }
    public double getSeparationWeight() {
        return this.separationWeight;
    }

    //setter and getter for alignmentWeight
    public void setAlignmentWeight(double n) {
        this.alignmentWeight = n;
    }
    public double getAlignmentWeight() {
        return this.alignmentWeight;
    }

    //setter and getter for cohesionWeight
    public void setCohesionWeight(double n) {
        this.cohesionWeight = n;
    }
    public double getCohesionWeight() {
        return this.cohesionWeight;
    }



    @Override
    public int getBirdNumber() { return this.birdNumber; }

    @Override
    public List<Bird> getBirds() { return this.birds; }

    @Override
    public void setBirdNumber(int n) {
        this.birdNumber = n;
        initBirds();
    }

    @Override
    public void updateMovement() {

        double maxForce = 0.06;
        double maxSpeed = 0.8;
        double dt = 1.0;

        for (int i = 0; i < birds.size(); i++) {
            Bird birdi = birds.get(i);

            double sepX = 0, sepY = 0;
            double alignX = 0, alignY = 0;
            double cohX = 0, cohY = 0;
            int total = 0;

            for (int j = 0; j < birds.size(); j++) {
                if (j == i) continue;
                Bird other = birds.get(j);

                // Wrapped difference
                Vector3D diff = wrappedDiff(birdi.pos, other.pos);
                double dist = Math.sqrt(diff.norm2());

                if (dist < radius && dist > 0) {
                    // Alignment: steer toward average velocity
                    alignX += other.velocity.x();
                    alignY += other.velocity.y();

                    // Cohesion: steer toward average position
                    cohX += other.pos.x();
                    cohY += other.pos.y();

                    // Separation: avoid crowding
                    if (dist < separationRadius) {
                        sepX += (birdi.pos.x() - other.pos.x()) / dist;
                        sepY += (birdi.pos.y() - other.pos.y()) / dist;
                    }

                    total++;
                }
            }

            if (total > 0) {
                // Average alignment and cohesion
                alignX /= total; alignY /= total;
                cohX /= total;   cohY /= total;

                // Cohesion becomes direction toward center of mass
                cohX = cohX - birdi.pos.x();
                cohY = cohY - birdi.pos.y();
            }

            // Compute combined force
            double forceX = sepX * separationWeight
                        + (alignX - birdi.velocity.x()) * alignmentWeight
                        + cohX * cohesionWeight;
            double forceY = sepY * separationWeight
                        + (alignY - birdi.velocity.y()) * alignmentWeight
                        + cohY * cohesionWeight;

            // Small random perturbation
            forceX += (random.nextDouble() - 0.5) * 0.01;
            forceY += (random.nextDouble() - 0.5) * 0.01;

            // Limit force magnitude
            double fmag = Math.sqrt(forceX * forceX + forceY * forceY);
            if (fmag > maxForce && fmag > 0) {
                forceX = forceX / fmag * maxForce;
                forceY = forceY / fmag * maxForce;
            }

            // Integrate velocity (acceleration = force, mass = 1)
            double newVx = birdi.velocity.x() + forceX * dt;
            double newVy = birdi.velocity.y() + forceY * dt;

            // Limit speed
            double speed = Math.sqrt(newVx * newVx + newVy * newVy);
            if (speed > maxSpeed && speed > 0) {
                newVx = newVx / speed * maxSpeed;
                newVy = newVy / speed * maxSpeed;
            }

            birdi.velocity = new Vector3D(newVx, newVy, 0);

            // Update position
            birdi.pos = Vector3D.add(birdi.pos, birdi.velocity);

            // Wrap around borders
            if (birdi.pos.x() > 100) birdi.pos.setX(birdi.pos.x() - 100);
            if (birdi.pos.x() < 0)   birdi.pos.setX(birdi.pos.x() + 100);
            if (birdi.pos.y() > 100) birdi.pos.setY(birdi.pos.y() - 100);
            if (birdi.pos.y() < 0)   birdi.pos.setY(birdi.pos.y() + 100);
        }
    }

    private Vector3D wrappedDiff(Vector3D a, Vector3D b) {
        Vector3D diff = Vector3D.minus(a, b);
        if (diff.x() > 50)  diff.setX(diff.x() - 100);
        if (diff.x() < -50) diff.setX(diff.x() + 100);
        if (diff.y() > 50)  diff.setY(diff.y() - 100);
        if (diff.y() < -50) diff.setY(diff.y() + 100);
        return diff;
    }
}