package com.paul.objects;

import com.paul.Vector;
import com.paul.camera.Ray;
import com.paul.objects.basic.Material;

public class Sphere extends Item {

  private double radius;

  public Sphere(Material material, double[] center, double radius) {
    super(material, center);

    this.radius = radius;
  }

  public boolean isColliding(Ray ray){
    return !(getDiscriminant(ray) < 0);
  }

  public boolean isInside(Ray ray) {
    return getDiscriminant(ray) > 0;
  }

  private double getDiscriminant(Ray ray) {
    double A = Vector.dot(ray.getDirection(), ray.getDirection());
    double[] dist = Vector.subtract(ray.getOrigin(), this.center);
    double B = 2 * Vector.dot(ray.getDirection(), dist);
    double C = Vector.dot(dist, dist) - (radius * radius);
    return (B * B) - (4 * A * C);
  }

  // https://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-sphere-intersection
  @Override
  public double[][] getCollisionPoints(Ray ray) {
    double[] L = Vector.subtract(center, ray.getOrigin());
    double tca = Vector.dot(L, ray.getDirection());

    if (tca < 0) {
      return new double[0][0];
    }

    double dSquared = Vector.dot(L, L) - (tca * tca);
    double d = Math.sqrt(dSquared);

    if (d < 0 || d > radius) {
      return new double[0][0];
    }

    double thc = Math.sqrt((radius * radius) - dSquared);
    double t0 = tca - thc;

    if (radius == d) {
      double[][] result = new double[1][3];

      result[0][0] = ray.getOrigin()[0] + (t0 * ray.getDirection()[0]);
      result[0][1] = ray.getOrigin()[1] + (t0 * ray.getDirection()[1]);
      result[0][2] = ray.getOrigin()[2] + (t0 * ray.getDirection()[2]);

      return result;
    } else {
      double t1 = tca + thc;

      double[][] result;

      if (t0 < 0) {
        result = new double[1][3];

        result[0][0] = ray.getOrigin()[0] + (t1 * ray.getDirection()[0]);
        result[0][1] = ray.getOrigin()[1] + (t1 * ray.getDirection()[1]);
        result[0][2] = ray.getOrigin()[2] + (t1 * ray.getDirection()[2]);
      } else {
        result = new double[2][3];

        result[0][0] = ray.getOrigin()[0] + (t0 * ray.getDirection()[0]);
        result[0][1] = ray.getOrigin()[1] + (t0 * ray.getDirection()[1]);
        result[0][2] = ray.getOrigin()[2] + (t0 * ray.getDirection()[2]);

        result[1][0] = ray.getOrigin()[0] + (t1 * ray.getDirection()[0]);
        result[1][1] = ray.getOrigin()[1] + (t1 * ray.getDirection()[1]);
        result[1][2] = ray.getOrigin()[2] + (t1 * ray.getDirection()[2]);
      }

      return result;
    }
  }
}
