package com.paul;

import com.paul.camera.ImagePlane;
import com.paul.objects.LightSource;
import com.paul.objects.Sphere;
import com.paul.objects.World;
import com.paul.objects.basic.Material;
import com.paul.output.Print;
import java.awt.Color;

public class Main {

  public static void main(String[] args) {
    ImagePlane imagePlane = new ImagePlane(11, 11,
        512, 512,
        new double[] {0, 20, 0},
        5);
    LightSource lightSource = new LightSource(
        new Material(1, new double[] {0, 0, 0}, new double[] {1, 1, 1}, 0,1, 0),
        new double[] {0, 70, 50}, 3);

    World world = new World(imagePlane, lightSource);

    Sphere sphereOne = new Sphere(new Material(new double[] {0, 1, 0}, 80,2),
        new double[] {-10, 30, 60}, 20);
    world.getItems().add(sphereOne);

    Sphere sphereTwo = new Sphere(new Material(new double[] {0, 1, 0}, 80,2),
        new double[] {20, 20, 45}, 10);
    world.getItems().add(sphereTwo);

    Sphere sphereThree = new Sphere(new Material(1, new double[] {0, 1, 0}, new double[] {0, 0, 0},
        2, 60, 2),
        new double[] {-15, 17, 40}, 7);
    world.getItems().add(sphereThree);

    world.calculateView();

    Print print = new Print(imagePlane, "/home/paul/Downloads/RayV/make.ppm");
    print.make();
  }
}
