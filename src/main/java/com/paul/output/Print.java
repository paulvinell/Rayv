package com.paul.output;

import com.paul.camera.ImagePlane;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Print {

  private ImagePlane imagePlane;
  private String filename;

  public Print(ImagePlane imagePlane, String filename) {
    this.imagePlane = imagePlane;
    this.filename = filename;
  }

  public void make() {
    File file = new File(filename);

    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(file));

      bw.write("P3");
      bw.newLine();
      bw.write(imagePlane.getImagePlanePixelWidth() + " " + imagePlane.getImagePlanePixelHeight() + " 255");
      bw.newLine();

      for (int i = imagePlane.getImagePlanePixelHeight() - 1; i >= 0; i--) {
        for (int j = 0; j < imagePlane.getImagePlanePixelWidth(); j++) {
          Color c = imagePlane.imagePlane[j][i];
          bw.write(c.getRed() + " " + c.getGreen() + " " + c.getBlue());
          bw.newLine();
        }
      }

      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
