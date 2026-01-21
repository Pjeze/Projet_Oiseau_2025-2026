package fr.oiseaux.model;

public class Vector3D {
  private double x;
  private double y;
  private double z;

  public Vector3D(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public double x() {
    return this.x;
  }

  public double y() {
    return this.y;
  }

  public double z() {
    return this.z;
  }

  public void add(Vector3D v) {
    this.x += v.x();
    this.y += v.y();
    this.z += v.z();
  }

  public void substract(Vector3D v) {
    this.x -= v.x();
    this.y -= v.y();
    this.z -= v.y();
  }

  public void mulBy(double t) {
    this.x *= t;
    this.y *= t;
    this.z *= t;
  }
}
