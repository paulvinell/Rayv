package com.paul.objects.basic;

import java.awt.Color;

public class Material {

  public double refractionIndex;
  public double[] surfaceColor, emissionColor;
  private int reflect, transmit, absorb;

  public Material(double[] surfaceColor, int reflect, int absorb) {
    this(1, surfaceColor, new double[] {0D, 0D, 0D}, reflect, 0, absorb);
  }

  public Material(double refractionIndex, double[] surfaceColor, double[] emissionColor, int reflect, int transmit, int absorb) {
    this.refractionIndex = refractionIndex;
    this.surfaceColor = surfaceColor;
    this.emissionColor = emissionColor;
    this.reflect = reflect;
    this.transmit = transmit;
    this.absorb = absorb;
  }

  private int total() {
    return reflect + transmit + absorb;
  }

  public double getRefractionIndex() {
    return this.refractionIndex;
  }

  public double getReflect() {
    return (double) reflect / total();
  }

  public double getTransmit() {
    return (double) transmit / total();
  }

  public double getAbsorb() {
    return (double) absorb / total();
  }
}
