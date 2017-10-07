package com.paul.camera;

public class Ray {

  private double[] origin;
  private double[] direction;

  public Ray(double[] origin, double[] direction) {
    this.origin = origin;
    this.direction = direction;
  }

  public double[] getOrigin() {
    return origin;
  }

  public double[] getDirection() {
    return direction;
  }
}
