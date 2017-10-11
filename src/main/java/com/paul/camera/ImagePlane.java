package com.paul.camera;

import com.paul.Vector;
import java.awt.Color;

public class ImagePlane {

  private final double imagePlaneWidth, imagePlaneHeight;
  private final int imagePlanePixelWidth, imagePlanePixelHeight;
  private final double[] imagePlaneMidPoint;
  private final double[] vantagePoint;
  private final double distance;

  public Color[][] imagePlane;

  public ImagePlane(double imagePlaneWidth, double imagePlaneHeight,
      int imagePlanePixelWidth, int imagePlanePixelHeight,
      double[] vantagePoint,
      double distance) {
    this.imagePlaneWidth = imagePlaneWidth;
    this.imagePlaneHeight = imagePlaneHeight;
    this.imagePlanePixelWidth = imagePlanePixelWidth;
    this.imagePlanePixelHeight = imagePlanePixelHeight;
    this.vantagePoint = vantagePoint;
    this.distance = distance;

    this.imagePlaneMidPoint = new double[3];
    this.imagePlaneMidPoint[0] = this.vantagePoint[0];
    this.imagePlaneMidPoint[1] = this.vantagePoint[1];
    this.imagePlaneMidPoint[2] = this.vantagePoint[2] + distance;

    this.imagePlane = new Color[imagePlanePixelWidth][imagePlanePixelHeight];

    for (int i = 0; i < imagePlanePixelWidth; i++) {
      for (int j = 0; j < imagePlanePixelHeight; j++) {
        this.imagePlane[i][j] = Color.BLACK;
      }
    }
  }

  public double[] getVantagePoint() {
    return vantagePoint;
  }

  public int getImagePlanePixelWidth() {
    return this.imagePlanePixelWidth;
  }

  public int getImagePlanePixelHeight() {
    return this.imagePlanePixelHeight;
  }

  public double[] getImagePlaneMidPoint() {
    return imagePlaneMidPoint;
  }

  public double[] getPixelPosition(int x, int y) {
    double relDistanceX = imagePlaneWidth / imagePlanePixelWidth;
    double relDistanceY = imagePlaneHeight / imagePlanePixelHeight;

    double posX = 0;
    double posY = 0;

    if (x % 2 == 0) {
      posX = imagePlaneMidPoint[0] + relDistanceX * ((double) x - (imagePlanePixelWidth / 2));
    } else {
      if (x < imagePlanePixelWidth / 2) {
        posX = imagePlaneMidPoint[0] + relDistanceX * ((double) x - (imagePlanePixelWidth / 2) - 0.5D);
      } else {
        posX = imagePlaneMidPoint[0] + relDistanceX * ((double) x - (imagePlanePixelWidth / 2) + 0.5D);
      }
    }

    if (y % 2 == 0) {
      posY = imagePlaneMidPoint[1] + relDistanceY * ((double) y - (imagePlanePixelHeight / 2));
    } else {
      if (y < imagePlanePixelHeight / 2) {
        posY = imagePlaneMidPoint[1] + relDistanceY * ((double) y - (imagePlanePixelHeight / 2) - 0.5D);
      } else {
        posY = imagePlaneMidPoint[1] + relDistanceY * ((double) y - (imagePlanePixelHeight / 2) + 0.5D);
      }
    }

    return new double[] {posX, posY, imagePlaneMidPoint[2]};
  }

  public Ray getRayFromPixel(int x, int y) {
    double[] pixelPos = this.getPixelPosition(x, y);
    double distance = Vector.length(Vector.subtract(pixelPos, vantagePoint));

    return new Ray(pixelPos, Vector.normalize(Vector.subtract(pixelPos, vantagePoint)));
  }
}
