package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The {@code CompressionImage} class provides functionality for compressing images.
 * by reducing the amount of data needed to represent the image.
 */
public class CompressionImage {

  /**
   * Transforms a given list of doubles by performing a wavelet-like transformation.
   * This method takes an input list and splits it into averages and differences of.
   * adjacent elements, producing a new list that contains all averages followed by.
   * all differences. If the input list's size is not a power of 2, it is padded to.
   * the nearest power of 2 length.
   * represents averages and the second half represents differences of adjacent.
   * elements from the input list.
   */
  public List<Double> transform(List<Double> s) {

    // if there isn't sufficient padding for the sequence to have a length = power of 2
    if (s.size() % 2 != 0) {
      s = paddedSequence(s);
      System.out.println(s);
    }

    List<Double> result = new ArrayList<>();
    ArrayList<Double> a = new ArrayList<>();
    ArrayList<Double> b = new ArrayList<>();
    double avg;
    double diff;
    for (int i = 0; i < s.size() - 1; i += 2) {
      avg = (s.get(i) + s.get(i + 1)) / Math.sqrt(2);
      diff = (s.get(i) - s.get(i + 1)) / Math.sqrt(2);
      a.add(avg);
      b.add(diff);
    }
    result.addAll(a);
    result.addAll(b);
    return result;
  }

  /**
   * Inverts a transformed list of doubles by reconstructing the original list
   * from averaged and differenced values.
   * This method takes a list that has been transformed into averages and differences
   * of adjacent elements (with all averages in the first half and all differences in the
   * second half of the list) and reconstructs the original list. If the input list's
   * length is not a power of 2, it is padded to the nearest power of 2 length.
   *
   * @param s the transformed list of doubles, consisting of averages followed by differences.
   * @return a new list of doubles representing the reconstructed original values.
   */
  public List<Double> invert(List<Double> s) {

    //remove this code post testing.
    // if there isn't sufficient padding.
    if (s.size() % 2 != 0) {
      s = paddedSequence(s);
      System.out.println(s);
    }

    List<Double> result = new ArrayList<>();
    double a;
    double b;
    for (int i = 0, j = s.size() / 2; i < s.size() / 2; i++, j++) {
      a = (s.get(i) + s.get(j)) / Math.sqrt(2);
      b = (s.get(i) - s.get(j)) / Math.sqrt(2);
      result.add(a);
      result.add(b);
    }
    return result;
  }


  /**
   * Calculates the nearest power of 2 that is greater than or equal to the given size.
   * This method takes an integer size and returns the smallest power of 2
   * that is greater than or equal to this size. If the input size is 0,
   * the method returns 0.
   *
   * @param size the initial size to be padded to the nearest power of 2.
   * @return the nearest power of 2 greater than or equal to the specified size, or 0 if size is 0.
   */
  public int paddingSize(int size) {
    if (size == 0) {
      return 0;
    }
    int i = 2;
    while (i < size) {
      i = i * 2;
    }
    return i;
  }

  private List<Double> paddedSequence(List<Double> s) {
    int padSize = paddingSize(s.size());
    List<Double> paddedList = new ArrayList<>(s);
    while (paddedList.size() < padSize) {
      paddedList.add(0.0);
    }
    return paddedList;
  }


  /**
   * Performs a 2D Haar wavelet transformation on an image.
   * This method applies the Haar transformation in both row-wise and column-wise directions
   * iteratively to reduce the size of the region being transformed at each step.
   * The transformation is followed by thresholding to remove small values below certain threshold.
   *
   * @param image     the 2D array representing the image to be transformed.
   * @param size      the size of the image (assuming it is square).
   * @param threshold the percentile threshold used to eliminate.
   *                  small values in the transformed image.
   * @return a 2D array representing the transformed image, with small values thresholded to zero.
   */
  public int[][] haar2DImageTransformation(int[][] image, int size, int threshold) {

    int[][] paddedImage = image;

    int c = size;

    while (c > 1) {
      // Converting int[] to List<double>
      for (int i = 0; i < c; i++) {
        List<Double> row = new ArrayList<>();
        for (int j = 0; j < c; j++) {
          row.add((double) paddedImage[i][j]);
        }
        // Row-wise transformation
        List<Double> transformedRow = transform(row);
        // Converting List<double> to int[]
        for (int j = 0; j < c; j++) {
          paddedImage[i][j] = (int) Math.round(transformedRow.get(j));
        }
      }

      for (int j = 0; j < c; j++) {
        List<Double> column = new ArrayList<>();
        for (int i = 0; i < c; i++) {
          column.add((double) paddedImage[i][j]);
        }

        // Column-wise transformation
        List<Double> transformedColumn = transform(column);

        for (int i = 0; i < c; i++) {
          paddedImage[i][j] = (int) Math.round(transformedColumn.get(i));
        }
      }

      // Reduce the size of the region to be transformed
      c /= 2;
    }

    threshold = calculatePercentile(paddedImage, threshold);
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (Math.abs(paddedImage[i][j]) < threshold) {
          paddedImage[i][j] = 0;
        }
      }
    }

    return paddedImage;
  }

  /**
   * Performs the inverse 2D Haar wavelet transformation on an image.
   * This method applies the inverse Haar transformation iteratively, first in column-wise direction
   * and then in row-wise direction, progressively increasing.
   * the region being transformed at each step.
   * to reconstruct the image from its transformed state.
   *
   * @param image the 2D array representing the transformed image to be inverted.
   * @param size  the size of the image (assuming it is square).
   * @return a 2D array representing the reconstructed image after the inverse Haar transformation.
   */
  public int[][] haar2DImageInverse(int[][] image, int size) {
    int c = 2;

    while (c <= size) {
      // Inverse Column-wise transformation
      for (int j = 0; j < c; j++) {
        List<Double> column = new ArrayList<>();
        for (int i = 0; i < c; i++) {
          column.add((double) image[i][j]);
        }
        // Apply the inverse Haar transformation on this column
        List<Double> invertedColumn = invert(column);
        for (int i = 0; i < c; i++) {
          image[i][j] = (int) Math.round(invertedColumn.get(i));
        }
      }

      // Inverse Row-wise transformation
      for (int i = 0; i < c; i++) {
        List<Double> row = new ArrayList<>();
        for (int j = 0; j < c; j++) {
          row.add((double) image[i][j]);
        }
        // Apply the inverse Haar transformation on this row
        List<Double> invertedRow = invert(row);
        for (int j = 0; j < c; j++) {
          image[i][j] = (int) Math.round(invertedRow.get(j));
        }
      }

      c *= 2;
    }

    return image;  // Return the fully reconstructed image
  }


  //temp

  /**
   * Converts a 2D array representation of an image into a string format.
   */
  public String toString(int[][] image) {
    StringBuilder sb = new StringBuilder();
    for (int[] row : image) {
      for (int pixel : row) {
        sb.append(pixel).append(" ");
      }
      sb.append("\n");  // New line after each row
    }
    return sb.toString();
  }

  private static int calculatePercentile(int[][] matrix, double percentile) {

    List<Integer> values = new ArrayList<>();
    for (int[] row : matrix) {
      for (int value : row) {
        values.add(value);
      }
    }
    Collections.sort(values);

    int index = (int) Math.ceil((percentile / 100.0) * values.size()) - 1;
    index = Math.max(0, Math.min(index, values.size() - 1));

    return values.get(index);
  }

}
