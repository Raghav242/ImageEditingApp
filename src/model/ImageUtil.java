package model;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * The ImageUtil class provides utility methods for reading and saving PPM image files.
 * It handles the reading of PPM files into a 3D array of pixel values and writing the pixel data
 * from a 3D array into a PPM file. The methods support working
 * with the "P3" format of the PPM image.
 */
public class ImageUtil {

  /**
   * Reads a PPM image from the specified file and returns the pixel data as a 3D integer array.
   * Each pixel is represented by an array of three integers for the RGB values.
   *
   * @param filename the name of the PPM file to read.
   * @return a 3D array representing the pixel data of the image.
   * @throws IOException              if there is an error reading the file.
   * @throws IllegalArgumentException if the PPM file format is incorrect.
   */
  public static BufferedImage readPPM(String filename) throws IOException {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File " + filename + " not found!");
    }

    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine().trim();
      if (!s.isEmpty() && s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());

    // Validate PPM header
    if (!sc.next().equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    // Create BufferedImage
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    // Read pixel data
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        int rgb = (r << 16) | (g << 8) | b;

        image.setRGB(y, x, rgb);
      }
    }

    return image;
  }

  /**
   * Saves the provided pixel data into a PPM file with the specified filename.
   * The data is saved in the "P3" format (plain RAW format) with RGB values for each pixel.
   *
   * @param filename the name of the file where the image will be saved.
   * @param image    a buffered Image.
   * @throws IOException if there is an error saving the file.
   */
  public static void savePPM(String filename, BufferedImage image) throws IOException {
    int width = image.getWidth();
    int height = image.getHeight();
    int maxValue = 255;

    try (PrintWriter writer = new PrintWriter(new FileOutputStream(filename))) {

      writer.println("P3");
      writer.println(width + " " + height);
      writer.println(maxValue);

      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {

          int rgb = image.getRGB(x, y);
          int r = (rgb >> 16) & 0xFF;
          int g = (rgb >> 8) & 0xFF;
          int b = rgb & 0xFF;

          writer.print(r + " ");
          writer.print(g + " ");
          writer.print(b + " ");
        }
        writer.println();
      }
      System.out.println("Saved ppm file");
    } catch (IOException e) {
      throw new IOException("Error writing file: " + e.getMessage());
    }
  }

  /**
   * Main method for testing the reading and saving of PPM files.
   * This will read an image from a specified filename and save it to another location.
   *
   * @param args command-line arguments (not used).
   */
  public static void main(String[] args) {
    try {
      String filename = "src/model/sample.ppm";
      BufferedImage image = readPPM(filename);
      savePPM("res/output.ppm", image);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
