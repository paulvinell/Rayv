package com.paul;

public class Matrix {

  public static double dot(double[] a, double[] b) {
    double sum = 0.0;

    for (int i = 0; i < a.length; i++) {
      sum += a[i] * b[i];
    }

    return sum;
  }
}
