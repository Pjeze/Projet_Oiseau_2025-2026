package fr.oiseaux.model;

import java.awt.Image;
import fr.oiseaux.model.Vector3D;

public class Bird{
  public Vector3D pos;
  public Vector3D velocity;
  public int width, height;
  public Image img;

  public Bird(Vector3D pos, Vector3D velocity, int width, int height, Image img) {
    this.velocity = velocity;
    this.pos = pos;
    this.width = width;
    this.height = height;
    this.img = img;
  }
}
