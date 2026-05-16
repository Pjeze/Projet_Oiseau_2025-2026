package fr.oiseaux.model;

public class Bird{
  public Vector3D pos;
  public Vector3D velocity;

  public Bird(Vector3D pos, Vector3D velocity) {
    this.velocity = velocity;
    this.pos = pos;
  }
}
