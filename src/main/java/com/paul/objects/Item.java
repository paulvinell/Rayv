package com.paul.objects;

import com.paul.camera.Ray;
import com.paul.objects.basic.Material;

public abstract class Item {

  protected Material material;
  protected double[] center;

  public Item(Material material, double[] center) {
    this.material = material;
    this.center = center;
  }

  public Material getMaterial() {
    return this.material;
  }

  public double[] getCenter() {
    return center;
  }

  public abstract double[] getNormal(double[] collisionPoint);

  public abstract double[][] getCollisionPoints(Ray ray);
}
