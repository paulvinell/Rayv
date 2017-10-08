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
  }

  public void calculateView() {
    for (int x = 0; x < imagePlane.getImagePlanePixelWidth(); x++) {
      for (int y = 0; y < imagePlane.getImagePlanePixelHeight(); y++) {
        Ray ray = imagePlane.getRayFromPixel(x, y);

        for (Item item : items) {
          double[][] collision = item.getCollisionPoints(ray);

          if (collision.length > 0) {
            double[] lightRayDirection = Vector.normalize(Vector.subtract(lightSource.center, collision[0]));
            Ray lightRay = new Ray(collision[0], lightRayDirection);

            if (lightSource.getCollisionPoints(lightRay).length > 0) {
              Color c = imagePlane.imagePlane[x][y];

              imagePlane.imagePlane[x][y] = new Color((int) (item.material.getColor().getRed() * item.material.getReflect()),
                  (int) (item.material.getColor().getBlue() * item.material.getReflect()),
                  (int) (item.material.getColor().getGreen() * item.material.getReflect()));
            }
          }
        }
      }
    }
  }

  public List<Item> getItems() {
    return items;
  }

  public void addItem(Item item) {
    if (items.size() == 0) {
      items.add(item);
    } else {
      double newDistanceSquare = Math.pow((item.center[0] - imagePlane.getVantagePoint()[0]), 2)
          + Math.pow((item.center[1] - imagePlane.getVantagePoint()[1]), 2)
          + Math.pow((item.center[2] - imagePlane.getVantagePoint()[2]), 2);

      int lastGood = 0;
      for (int i = 0; i < items.size(); i++) {
        Item curItem = items.get(i);
        double curItemDistanceSquare = Math.pow((curItem.center[0] - imagePlane.getVantagePoint()[0]), 2)
            + Math.pow((curItem.center[1] - imagePlane.getVantagePoint()[1]), 2)
            + Math.pow((curItem.center[2] - imagePlane.getVantagePoint()[2]), 2);

        if (newDistanceSquare > curItemDistanceSquare) {
          lastGood = i;
        } else {
          break;
        }
      }

      items.add(lastGood, item);
    }
  }
}
