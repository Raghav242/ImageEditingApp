package model;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The {@code IModel} interface represents the model component in the MVC architecture.
 * It defines the operations related to image processing and management that can be performed
 * by the model. The model is responsible for holding and manipulating image data.
 * The implementing class should provide concrete methods for interacting with images,
 * such as loading, saving, and applying various processing operations.
 */
public interface IModel {

  /**
   * Loads an image from a BufferedImage and stores it in the model under the specified name.
   *
   * @param bf        the BufferedImage to load.
   * @param imageName the name to associate with the loaded image.
   * @throws IOException if an error occurs while reading the image.
   */
  void loadImage(BufferedImage bf, String imageName) throws IOException;

  /**
   * Saves an image to an Appendable based on the given image name.
   *
   * @param imageName the name of the image to save.
   * @return the BufferedImage object of the saved image.
   * @throws IOException if an error occurs while saving the image.
   */
  BufferedImage getImage(String imageName) throws IOException;

  /**
   * Adjusts the brightness of a stored image by the given value.
   *
   * @param value           the amount to adjust the brightness.
   * @param sourceImageName the name of the source image.
   * @param targetImageName the name under which the brightened image will be stored.
   */
  void brightenImage(String value, String sourceImageName, String targetImageName);

  /**
   * Applies a filter to an image and stores the result.
   *
   * @param filter          the filter command (e.g., "sepia", "grayscale").
   * @param sourceImageName the name of the source image.
   * @param targetImageName the name under which the filtered image will be stored.
   * @param splitPercentage the percentage for split filtering (optional).
   * @throws IllegalArgumentException if the filter command is invalid.
   */
  void applyFilter(String filter, String sourceImageName, String targetImageName,
                   Integer splitPercentage)
          throws IllegalArgumentException;

  /**
   * Generates a histogram for the specified image.
   *
   * @param sourceImageName the name of the source image.
   * @param targetImageName the name under which the histogram will be stored.
   * @throws IOException if an error occurs while generating the histogram.
   */
  void createHistogram(String sourceImageName, String targetImageName) throws IOException;

  /**
   * Applies color correction to the specified image and stores it under a new name.
   *
   * @param sourceImageName the name of the source image.
   * @param targetImageName the name under which the corrected image will be stored.
   * @param splitPercentage the split percentage for color correction.
   */
  void colorCorrection(String sourceImageName, String targetImageName, int splitPercentage);

  /**
   * Applies levels adjustment to the specified image and stores it under the target name.
   *
   * @param shadow          the shadow value for the levels adjustment.
   * @param mid             the mid-point value for the levels adjustment.
   * @param highlight       the highlight value for the levels adjustment.
   * @param sourceImageName the name of the source image.
   * @param targetImageName the name under which the adjusted image will be stored.
   * @param splitPercentage the split percentage for levels adjustment.
   */
  void adjustLevels(int shadow, int mid, int highlight, String sourceImageName,
                    String targetImageName, int splitPercentage);

  /**
   * Combines the RGB components into a single image and stores the result.
   *
   * @param targetImageName the name under which the combined image will be stored.
   * @param redImageName    the name of the red component image.
   * @param greenImageName  the name of the green component image.
   * @param blueImageName   the name of the blue component image.
   */
  void rgbCombine(String targetImageName, String redImageName, String greenImageName,
                  String blueImageName);

  /**
   * Splits an image into its RGB components and stores the red, green, and blue images.
   *
   * @param sourceImageName the name of the source image.
   * @param redImageName    the name under which the red component image will be stored.
   * @param greenImageName  the name under which the green component image will be stored.
   * @param blueImageName   the name under which the blue component image will be stored.
   */
  void rgbSplit(String sourceImageName, String redImageName, String greenImageName,
                String blueImageName);

  /**
   * Compresses an image by a given percentage and stores the result under a specified path.
   *
   * @param percentage the percentage to compress the image by.
   * @param imageName  the name of the image to compress.
   * @param outputPath the path where the compressed image will be saved.
   */
  void compress(int percentage, String imageName, String outputPath);

  void downscale(String sourceImageName, String targetImageName, int newWidth,
                 int newHeight);

  void applyPartialTransformation(String filterOperation, String sourceImage,
                                  String maskImage, String destImage);

  void resetToOriginal(String imageName, String originalImage);
}
