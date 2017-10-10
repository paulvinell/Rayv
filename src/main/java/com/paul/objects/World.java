package com.paul.objects;

import com.paul.Vector;
import com.paul.camera.ImagePlane;
import com.paul.camera.Ray;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class World {

  private ImagePlane imagePlane;
  private LightSource lightSource;
  private List<Item> items = new ArrayList<>();

  public World(ImagePlane imagePlane, LightSource lightSource) {
    this.imagePlane = imagePlane;
    this.lightSource = lightSource;

    items.add(lightSource);
  }

  public void calculateView() {
    for (int y = 0; y < imagePlane.getImagePlanePixelHeight(); y++) {
      for (int x = 0; x < imagePlane.getImagePlanePixelWidth(); x++) {
        Ray ray = imagePlane.getRayFromPixel(x, y);

        double[] color = getColorRay(ray, 5);

        imagePlane.imagePlane[x][y] = new Color((int) (255D * color[0]),
            (int) (255D * color[1]),
            (int) (255D * color[2]));
      }
    }
  }

  public double[] getColorRay(Ray mainRay, int die) {
    if (die <= 0) {
      return new double[] {0, 0, 0};
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
      return new double[] {0, 0, 0};
    }

    double[] rgbToImagePlane = new double[] {0, 0, 0};
    double[] norm = Vector.normalize(Vector.subtract(collisionPoint, collideItem.center));

    // Standard illumination
    double[] lightRayDirection = Vector.normalize(Vector.subtract(lightSource.center, collisionPoint));
    Ray lightRay = new Ray(collisionPoint, lightRayDirection);

    if (lightSource.getCollisionPoints(lightRay).length > 0) {
      double intensity = 0.5 + Vector.dot(norm, lightRayDirection) * 0.5 * collideItem.material.getReflect();

      rgbToImagePlane = Vector.scale(collideItem.material.surfaceColor, intensity);
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

      double[] reflectionColor = getColorRay(reflectionRay, die);


//      double[] refractionColor = new double[] {0, 0, 0};
//      if (collideItem.material.getTransmit() > 0) {
//        double ior = 1.1;
//        double eta = (inside) ? ior : 1 / ior;
//        double cosi = -Vector.dot(norm, mainRay.getDirection());
//        double k = 1 - eta * eta * (1 - cosi * cosi);
//
//        double[] refractionDirection = Vector.add(Vector.scale(mainRay.getDirection(), eta),
//            Vector.scale(norm, (eta *  cosi - Math.sqrt(k))));
//        refractionDirection = Vector.normalize(refractionDirection);
//
//        Ray refractionRay = new Ray(Vector.subtract(collisionPoint, Vector.scale(refractionDirection, bias)), refractionDirection);
//
//        refractionColor = getColorRay(refractionRay, die);
//
//        double[] test = Vector.scale(refractionColor, (1 - fresnel) * collideItem.material.getTransmit());
//        //System.out.println(test[0] + " " + test[1] + " " + test[2]);
//      }

//      rgbToImagePlane = Vector.add(rgbToImagePlane,
//          Vector.multiply(collideItem.material.surfaceColor,
//          Vector.add(Vector.scale(reflectionColor, fresnel * collideItem.material.getReflect()),
//              Vector.scale(refractionColor, (1 - fresnel) * collideItem.material.getTransmit())
//          )));

      rgbToImagePlane = Vector.add(rgbToImagePlane,
          Vector.multiply(collideItem.material.surfaceColor, Vector.scale(reflectionColor, fresnel)));
    }

    while (rgbToImagePlane[0] > 1 || rgbToImagePlane[1] > 1 || rgbToImagePlane[2] > 1) {
      rgbToImagePlane[0] = rgbToImagePlane[0] * 0.99;
      rgbToImagePlane[1] = rgbToImagePlane[1] * 0.99;
      rgbToImagePlane[2] = rgbToImagePlane[2] * 0.99;
    }

    return Vector.add(rgbToImagePlane, collideItem.material.emissionColor);
  }

  private double mix(double a, double b, double mix) {
    return b * mix + a * (1 - mix);
  }

  public List<Item> getItems() {
    return items;
  }
}
