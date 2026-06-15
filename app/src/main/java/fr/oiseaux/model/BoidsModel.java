package fr.oiseaux.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoidsModel implements BirdModel {
    public double radius;       // neighborhood radius
    public double separationRadius; // radius for separation rule
    public double v0;           // base speed

    // Rule weights
    public double separationWeight = 1.2;
    public double alignmentWeight = 0.6;
    public double cohesionWeight = 0.4;

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
            Vector3D v = new Vector3D(
                    random.nextDouble()-0.5,
                    random.nextDouble()-0.5,
                    random.nextDouble()-0.5);
            Bird b = new Bird(
                new Vector3D(
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

            double sepX = 0, sepY = 0, sepZ = 0;
            double alignX = 0, alignY = 0, alignZ = 0;
            double cohX = 0, cohY = 0, cohZ = 0;
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
                    alignZ += other.velocity.z();

                    // Cohesion: steer toward average position
                    cohX += other.pos.x();
                    cohY += other.pos.y();
                    cohZ += other.pos.z();


                    // Separation: avoid crowding
                    if (dist < separationRadius) {
                        sepX += (birdi.pos.x() - other.pos.x()) / dist;
                        sepY += (birdi.pos.y() - other.pos.y()) / dist;
                        sepZ += (birdi.pos.z() - other.pos.z()) / dist;
                    }

                    total++;
                }
            }

            if (total > 0) {
                // Average alignment and cohesion
                alignX /= total; alignY /= total; alignZ /= total;
                cohX /= total;   cohY /= total; cohZ /= total;

                // Cohesion becomes direction toward center of mass
                cohX = cohX - birdi.pos.x();
                cohY = cohY - birdi.pos.y();
                cohZ = cohZ - birdi.pos.z();
            }

            // Compute combined force
            double forceX = sepX * separationWeight
                        + (alignX - birdi.velocity.x()) * alignmentWeight
                        + cohX * cohesionWeight;
            double forceY = sepY * separationWeight
                        + (alignY - birdi.velocity.y()) * alignmentWeight
                        + cohY * cohesionWeight;
            double forceZ = sepZ * separationWeight
                        + (alignZ - birdi.velocity.z()) * alignmentWeight
                        + cohZ * cohesionWeight;

            // Small random perturbation
            forceX += (random.nextDouble() - 0.5) * 0.01;
            forceY += (random.nextDouble() - 0.5) * 0.01;
            forceZ += (random.nextDouble() - 0.5) * 0.01;

            // edges force
            double wallThreshold = 20.0; // only activate within 20 units of a wall
            double WALL = 50.0;
            // X axis
            if (birdi.pos.x() < wallThreshold)
                forceX += WALL / Math.max(birdi.pos.x() * birdi.pos.x(), 1);
            if (100 - birdi.pos.x() < wallThreshold)
                forceX -= WALL / Math.max((100 - birdi.pos.x()) * (100 - birdi.pos.x()), 1);
            // Y axis
            if (birdi.pos.y() < wallThreshold)
                forceY += WALL / Math.max(birdi.pos.y() * birdi.pos.y(), 1);
            if (100 - birdi.pos.y() < wallThreshold)
                forceY -= WALL / Math.max((100 - birdi.pos.y()) * (100 - birdi.pos.y()), 1);
            // Z axis
            if (birdi.pos.z() < wallThreshold)
                forceZ += WALL / Math.max(birdi.pos.z() * birdi.pos.z(), 1);
            if (100 - birdi.pos.z() < wallThreshold)
                forceZ -= WALL / Math.max((100 - birdi.pos.z()) * (100 - birdi.pos.z()), 1);      


            // Limit force magnitude
            double fmag = Math.sqrt(forceX * forceX + forceY * forceY + forceZ * forceZ);
            if (fmag > maxForce && fmag > 0) {
                forceX = forceX / fmag * maxForce;
                forceY = forceY / fmag * maxForce;
                forceZ = forceZ / fmag * maxForce;
            }

            // Integrate velocity (acceleration = force, mass = 1)
            double newVx = birdi.velocity.x() + forceX * dt;
            double newVy = birdi.velocity.y() + forceY * dt;
            double newVz = birdi.velocity.z() + forceZ * dt;

            // Limit speed
            double speed = Math.sqrt(newVx * newVx + newVy * newVy + newVz * newVz);
            if (speed > maxSpeed && speed > 0) {
                newVx = newVx / speed * maxSpeed;
                newVy = newVy / speed * maxSpeed;
                newVz = newVz / speed * maxSpeed;
            }

            birdi.velocity = new Vector3D(newVx, newVy, newVz);

            // Update position
            birdi.pos = Vector3D.add(birdi.pos, birdi.velocity);

            //stop at borders
            birdi.pos.setX(Math.min(100, birdi.pos.x()));
            birdi.pos.setX(Math.max(0, birdi.pos.x()));
            birdi.pos.setY(Math.min(100, birdi.pos.y()));
            birdi.pos.setY(Math.max(0, birdi.pos.y()));
            birdi.pos.setZ(Math.min(100, birdi.pos.z()));
            birdi.pos.setZ(Math.max(0, birdi.pos.z()));

            /* Wrap around borders
            if (birdi.pos.x() > 100) birdi.pos.setX(birdi.pos.x() - 100);
            if (birdi.pos.x() < 0)   birdi.pos.setX(birdi.pos.x() + 100);
            if (birdi.pos.y() > 100) birdi.pos.setY(birdi.pos.y() - 100);
            if (birdi.pos.y() < 0)   birdi.pos.setY(birdi.pos.y() + 100);
            if (birdi.pos.z() > 100) birdi.pos.setZ(birdi.pos.z() - 100);
            if (birdi.pos.z() < 0)   birdi.pos.setZ(birdi.pos.z() + 100);
            */
        }
    }

    private Vector3D wrappedDiff(Vector3D a, Vector3D b) {
        Vector3D diff = Vector3D.minus(a, b);
        if (diff.x() > 50)  diff.setX(diff.x() - 100);
        if (diff.x() < -50) diff.setX(diff.x() + 100);
        if (diff.y() > 50)  diff.setY(diff.y() - 100);
        if (diff.y() < -50) diff.setY(diff.y() + 100);
        if (diff.z() > 50)  diff.setY(diff.z() - 100);
        if (diff.z() < -50) diff.setY(diff.z() + 100);
        return diff;
    }
}