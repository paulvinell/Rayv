package com.paul;

import com.paul.objects.basic.Material;
import java.awt.Color;
import junit.framework.TestCase;

public class MaterialTest extends TestCase {

  public MaterialTest() {
    super("Material Test");
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testMaterials() {
    Material superGlass = new Material(1, new double[] {0, 0, 0}, new double[] {0, 0, 0}, 0, 5, 0);
    Material superMirror = new Material(new double[] {0, 0, 0}, 7, 0);
    Material superBody = new Material(new double[] {0, 0, 0}, 0, 9);

    assertTrue(superGlass.getTransmit() == 1D);
    assertTrue(superMirror.getReflect() == 1D);
    assertTrue(superBody.getAbsorb() == 1D);

    Material strange = new Material(1, new double[] {0, 0, 0}, new double[] {0, 0, 0}, 20, 50, 30);

    assertTrue(strange.getReflect() == 0.2D);
    assertTrue(strange.getTransmit() == 0.5D);
    assertTrue(strange.getAbsorb() == 0.3D);
  }
}
