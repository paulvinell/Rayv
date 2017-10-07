package com.paul;

import com.paul.camera.ImagePlane;
import java.util.HashSet;
import junit.framework.TestCase;

public class ImagePlaneTest extends TestCase {

  public ImagePlaneTest() {
    super("Image Plane Test");
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testDistance() {
    ImagePlane imagePlaneOne = new ImagePlane(100, 100 ,
        100, 100,
        new double[] {1, 1, 2},
        1D);

    assertTrue(imagePlaneOne.getImagePlaneMidPoint()[2] == 3D);
  }

  public void testPixelPosition() {
    ImagePlane imagePlaneOne = new ImagePlane(5, 5 ,
        5, 5,
        new double[] {0, 0, 0},
        2D);

    assertTrue(imagePlaneOne.getPixelPosition(2, 2)[0] == 0);
    assertTrue(imagePlaneOne.getPixelPosition(2, 2)[1] == 0);
    assertTrue(imagePlaneOne.getPixelPosition(0, 3)[0] == -2);
    assertTrue(imagePlaneOne.getPixelPosition(4, 3)[0] == 2);
  }
}
