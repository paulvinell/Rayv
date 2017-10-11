package com.paul.objects;

import com.paul.camera.Ray;
import com.paul.objects.basic.Material;

public class Wall extends Item {

  private double[] normal;
  private int plane;

  public Wall(Material material, double[] normal, double position, int plane) {
    super(material, new double[] {0, 0, 0});
    center[plane] = position;

    this.plane = plane;
    this.normal = normal;
  }

  @Override
  public double[] getNormal(double[] collisionPoint) {
    return normal;
  }

  @Override
  public double[][] getCollisionPoints(Ray ray) {
    double distanceToCollision = (center[plane] - ray.getOrigin()[plane]) / ray.getDirection()[plane];

    if (distanceToCollision > 0) {
      double[][] result = new double[1][3];

      for (int i = 0; i < 3; i++) {
        result[0][i] = ray.getOrigin()[i] + (ray.getDirection()[i] * distanceToCollision);
      }

      return result;
    }

    return new double[0][0];
  }
}
