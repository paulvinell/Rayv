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
    double A = Vector.dot(ray.getDirection(), ray.getDirection());

        /* We need a vector representing the distance between the start of
         * the ray and the position of the circle.
         * This is the term (p0 - c)
         */
    double[] dist = Vector.subtract(ray.getOrigin(), this.center);

         /* 2d.(p0 - c) */
    double B = 2 * Vector.dot(ray.getDirection(), dist);

         /* (p0 - c).(p0 - c) - r^2 */
    double C = Vector.dot(dist, dist) - (radius * radius);

        /* Solving the discriminant */
    double discr = (B * B) - (4 * A * C);

        /* If the discriminant is negative, there are no real roots.
        * Return false in that case as the ray misses the sphere.
        * Return true in all other cases (can be one or two intersections)
        */

    return !(discr < 0);
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

      double[][] result = new double[2][3];

      result[0][0] = ray.getOrigin()[0] + (t0 * ray.getDirection()[0]);
      result[0][1] = ray.getOrigin()[1] + (t0 * ray.getDirection()[1]);
      result[0][2] = ray.getOrigin()[2] + (t0 * ray.getDirection()[2]);

      result[1][0] = ray.getOrigin()[0] + (t1 * ray.getDirection()[0]);
      result[1][1] = ray.getOrigin()[1] + (t1 * ray.getDirection()[1]);
      result[1][2] = ray.getOrigin()[2] + (t1 * ray.getDirection()[2]);

      return result;
    }
  }
}
