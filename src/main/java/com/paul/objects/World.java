package com.paul.objects;

import com.paul.Vector;
import com.paul.camera.ImagePlane;
import com.paul.camera.Ray;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class World {

  private ImagePlane imagePlane;
  private List<Item> items = new ArrayList<>();

  public World(ImagePlane imagePlane) {
    this.imagePlane = imagePlane;
  }

  public void calculateView() {
    for (int y = 0; y < imagePlane.getImagePlanePixelHeight(); y++) {
      for (int x = 0; x < imagePlane.getImagePlanePixelWidth(); x++) {
        Ray ray = imagePlane.getRayFromPixel(x, y);

        double[] color = getColorRay(ray, 7)[0];

        imagePlane.imagePlane[x][y] = new Color((int) (255D * color[0]),
            (int) (255D * color[1]),
            (int) (255D * color[2]));
      }
    }
  }

  public double[][] getColorRay(Ray mainRay, int die) {
    if (die <= 0) {
      return new double[][] {{0, 0, 0}, {0, 0, 0}};
    } else {
      die--;
    }

    Item collideItem = null;
    double[] collisionPoint = null;
    double distance = Double.MAX_VALUE;

    for (Item item : items) {
      double[][] collisionPoints = item.getCollisionPoints(mainRay);

      if (collisionPoints.length > 0) {
        double curDistance = Vector.length(collisionPoints[0]);

        if (curDistance < distance) {
          collideItem = item;
          collisionPoint = collisionPoints[0];
          distance = curDistance;
        }
      }
    }

    if (collideItem == null) {
      return new double[][] {{0, 0, 0}, {0, 0, 0}};
    }

    double[] rgb = new double[] {0, 0, 0};
    double[] norm = collideItem.getNormal(collisionPoint);

    // Standard illumination
    for (Item item : items) {
      if (item.material.isLightSource() && item != collideItem) {
        double[] lightRayDirection = Vector.normalize(Vector.subtract(item.center, collisionPoint));
        Ray lightRay = new Ray(collisionPoint, lightRayDirection);

        if (item.getCollisionPoints(lightRay).length > 0) {
          double intensity = 0.5 + Vector.dot(norm, lightRayDirection) * 0.5;

          rgb = Vector.add(rgb, Vector.scale(item.material.emissionColor, intensity));
        }
      }
    }

    // Reflection and refraction
    double bias = 1e-4;
    boolean inside = false;

    if (Vector.dot(mainRay.getDirection(), norm) > 0) {
      norm = Vector.invert(norm);
      inside = true;
    }

    if (collideItem.material.getReflect() > 0) {
      double facingRatio = -Vector.dot(mainRay.getDirection(), norm);

      double fresnel = mix(Math.pow(1 - facingRatio, 3), 1, 0.1D);

      double[] reflectionDirection = Vector.subtract(mainRay.getDirection(),
          Vector.scale(norm,2 * Vector.dot(mainRay.getDirection(), norm)));
      reflectionDirection = Vector.normalize(reflectionDirection);

      Ray reflectionRay = new Ray(Vector.add(collisionPoint, Vector.scale(reflectionDirection, bias)), reflectionDirection);

      double[][] reflectionColors = getColorRay(reflectionRay, die);
      double[] reflectionColor = Vector.add(reflectionColors[0], reflectionColors[1]);

      rgb = Vector.add(rgb, Vector.scale(reflectionColor, fresnel));
    }

    double[][] result = new double[2][3];
    result[0] = Vector.multiply(Vector.scale(rgb, collideItem.material.getReflect()),
        collideItem.material.surfaceColor);

    while (result[0][0] > 1 + collideItem.material.emissionColor[0]
        || result[0][1] > 1 + collideItem.material.emissionColor[1]
        || result[0][2] > 1 + collideItem.material.emissionColor[2]) {
      result[0] = Vector.scale(result[0], 0.99);
    }

    result[1] = Vector.subtract(rgb, result[0]);
    result[0] = Vector.add(result[0], collideItem.material.emissionColor);

    return result;
  }

  private double mix(double a, double b, double mix) {
    return b * mix + a * (1 - mix);
  }

  public List<Item> getItems() {
    return items;
  }
}
