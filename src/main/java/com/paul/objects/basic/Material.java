package com.paul.objects.basic;

import java.awt.Color;

public class Material {

  private Color color;
  private int reflect, transmit, absorb;

  public Material(Color color, int reflect, int transmit, int absorb) {
    this.color = color;
    this.reflect = reflect;
    this.transmit = transmit;
    this.absorb = absorb;
  }

  private int total() {
    return reflect + transmit + absorb;
  }

  public Color getColor() {
    return this.color;
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
