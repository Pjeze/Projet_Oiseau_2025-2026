package fr.oiseaux.model;

public class Obstacles {
    private double x, y, z, size;
    private int type; // 0: Cube, 1: Sphere, 2: Cone/Point

    public Obstacles(double x, double y, double z, double size, int type) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
        this.type = type;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }
    public double getSize() { return size; }
    public int getType() { return type; }

    public void setSize(double size) { this.size = size; }
}