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
    double length = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    if (length == 0) {
      return new Vector3D(0, 0, 0);
    }
    return this.scale(1.0 / length);
  }

  public double length() {
    return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
  }

  public static Vector3D orthogonal(Vector3D v) {
    if (Math.abs(v.x()) < Math.abs(v.y()) && Math.abs(v.x()) < Math.abs(v.z())) {
      return new Vector3D(0, -v.z(), v.y());
    }
    if (Math.abs(v.y()) < Math.abs(v.z())) {
      return new Vector3D(-v.z(), 0, v.x());
    }
    return new Vector3D(-v.y(), v.x(), 0);
  }

  public static Vector3D blendToTangent(Vector3D velocity, Vector3D away, double blend) {
    double speed = Math.sqrt(velocity.norm2());
    if (speed == 0) {
      return velocity;
    }

    Vector3D heading = velocity.scale(1.0 / speed);
    double dot = Vector3D.dot(heading, away);
    if (dot >= 0) {
      return velocity;
    }

    Vector3D tangent = Vector3D.minus(heading, away.scale(dot));
    double tangentLength = tangent.length();
    if (tangentLength < 1e-8) {
      tangent = Vector3D.orthogonal(away).normalize();
    } else {
      tangent = tangent.scale(1.0 / tangentLength);
    }

    double t = Math.max(0.0, Math.min(1.0, blend));
    Vector3D correctedDirection = Vector3D.add(heading.scale(1.0 - t), tangent.scale(t));
    correctedDirection = correctedDirection.normalize();

    return correctedDirection.scale(speed);
  }

  public static Vector3D add(Vector3D u, Vector3D v) {
    return new Vector3D(u.x() + v.x(), u.y() + v.y(), u.z() + v.z());
  }

  public static Vector3D minus(Vector3D u, Vector3D v) {
    return new Vector3D(u.x() - v.x(), u.y() - v.y(), u.z() - v.z());
  }

  public static Vector3D cross(Vector3D u, Vector3D v) {
    return new Vector3D(
        u.y() * v.z() - u.z() * v.y(),
        u.z() * v.x() - u.x() * v.z(),
        u.x() * v.y() - u.y() * v.x());
  }

  public static double dot(Vector3D u, Vector3D v) {
    return u.x * v.x + u.y * v.y + u.z * v.z;
  }

  public double norm2() {
    return this.x * this.x + this.y * this.y + this.z * this.z;
  }
}
