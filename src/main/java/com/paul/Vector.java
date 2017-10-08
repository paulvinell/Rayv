package com.paul;

public class Vector {

  public static double length(double[] a) {
    double result = 0D;

    for (int i = 0; i < a.length; i++) {
      result += Math.pow(a[i], 2);
    }

    return Math.sqrt(result);
  }

  public static double[] normalize(double[] a) {
    double[] b = new double[a.length];
    double length = Vector.length(a);

    for (int i = 0; i < a.length; i++) {
      b[i] = a[i] / length;
    }

    return b;
  }

  public static double dot(double[] a, double[] b) {
    double sum = 0D;

    for (int i = 0; i < a.length; i++) {
      sum += a[i] * b[i];
    }

    return sum;
  }

  public static double[] scale(double[] a, double scale) {
    double[] b = new double[a.length];

    for (int i = 0; i < a.length; i++) {
      b[i] = a[i] * scale;
    }

    return b;
  }

  public static double[] add(double[] a, double[] b) {
    double[] c = new double[a.length];

    for (int i = 0; i < a.length; i++) {
      c[i] = a[i] + b[i];
    }

    return c;
  }

  public static double[] subtract(double[] a, double[] b) {
    double[] c = new double[a.length];

    for (int i = 0; i <  a.length; i++) {
      c[i] = a[i] - b[i];
    }

    return c;
  }
}
