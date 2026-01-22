package fr.oiseaux.model;

import java.lang.Math;

public class Vector3D {
  private double x;
  private double y;
  private double z;

  public double x() {
    return this.x;
  }

  public double y() {
    return this.y;
  }

  public double z() {
    return this.z;
  }

  public void setX(double x) {
    this.x = x;
  }

  public void setY(double y) {
    this.y = y;
  }

  public void setZ(double z) {
    this.z = z;
  }

  public Vector3D(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Vector3D scale(double t) {
    return new Vector3D(t * this.x, t * this.y, t * this.z);
  }

  public Vector3D normalize() {
    double n2 = 1/Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    if (n2 == Double.NaN)
      throw new ArithmeticException("Division par 0");
    else
      return this.scale(n2);
  }

  public static Vector3D add(Vector3D u, Vector3D v) {
    return new Vector3D(u.x() + v.x(), u.y() + v.y(), u.z() + v.z());
  }

  public static Vector3D minus(Vector3D u, Vector3D v) {
    return new Vector3D(u.x() - v.x(), u.y() - v.y(), u.z() - v.z());
  }

  public double norm2() {
    return this.x * this.x + this.y * this.y + this.z * this.z;
  }
}
