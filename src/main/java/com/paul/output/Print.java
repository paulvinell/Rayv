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
      bw.write(imagePlane.getImagePlanePixelWidth() + " " + imagePlane.getImagePlanePixelHeight());
      bw.newLine();
      bw.write(255);
      bw.newLine();

      for (int i = 0; i < imagePlane.getImagePlanePixelWidth(); i++) {
        for (int j = 0; j < imagePlane.getImagePlanePixelHeight(); j++) {
          Color c = imagePlane.getImagePlane()[i][j];
          bw.write(c.getRed() + " " + c.getBlue() + " " + c.getGreen());
          bw.newLine();
        }
      }

      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
