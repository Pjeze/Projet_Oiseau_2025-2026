package fr.oiseaux.model;

import java.awt.Image;

public class Bird{
  public double x, y;
  public double velocityX, velocityY;
  public int width, height;
  public Image img;

  public Bird(double x, double y, int width, int height, Image img) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.img = img;
}
}
