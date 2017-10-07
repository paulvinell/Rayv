package com.paul;

import com.paul.camera.ImagePlane;
import com.paul.objects.LightSource;
import com.paul.objects.World;
import com.paul.objects.basic.Material;
import com.paul.output.Print;
import java.awt.Color;

public class Main {

  public static void main(String[] args) {
    ImagePlane imagePlane = new ImagePlane(10, 10,
        100, 100,
        new double[] {0, 20, 0},
        3);
    LightSource lightSource = new LightSource(new Material(Color.WHITE, 1, 0, 0),
        new double[] {0, 70, 50}, 5);

    World world = new World(imagePlane, lightSource);

    //add objects

    world.calculateView();

    Print print = new Print(imagePlane, "home/paul/Downloads/RayV/make.ppm");
    print.make();
  }
}
