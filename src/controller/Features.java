package controller;

import java.io.IOException;

/**
 * This interface defines various image processing operations that can be performed on an image.
 * It provides methods to load, save, and apply transformations such as brightening, blurring,
 * sharpening, flipping, and adjusting levels. Some methods include percentage-based adjustments
 * for finer control.
 */
public interface Features {

  /**
   * Loads an image from the specified file path and assigns it a unique name.
   *
   * @param path      the path of the image file to load
   * @param imageName the unique name to assign to the loaded image
   * @throws IOException if an error occurs while reading the file
   */
  void loadImage(String path, String imageName) throws IOException;

  /**
   * Saves the current image to the specified output file path.
   *
   * @param outputPath the path to save the image file
   * @throws IOException if an error occurs while writing the file
   */
  void saveImage(String outputPath) throws IOException;

  /**
   * Adjusts the brightness of the image by the specified increment.
   *
   * @param increment the amount to brighten the image, can be positive or negative
   * @throws IOException if an error occurs during the operation
   */
  void brighten(String increment) throws IOException;

  /**
   * Applies a blur effect to the image with default intensity.
   *
   * @throws IOException if an error occurs during the operation
   */
  void blur() throws IOException;

  /**
   * Applies a blur effect to the image with the specified intensity.
   *
   * @param percentage the intensity of the blur effect, in percentage
   * @throws IOException if an error occurs during the operation
   */
  void blur(int percentage) throws IOException;

  /**
   * Applies a sharpening effect to the image with default intensity.
   *
   * @throws IOException if an error occurs during the operation
   */
  void sharpen() throws IOException;

  /**
   * Applies a sharpening effect to the image with the specified intensity.
   *
   * @param percentage the intensity of the sharpening effect, in percentage
   * @throws IOException if an error occurs during the operation
   */
  void sharpen(int percentage) throws IOException;

  /**
   * Applies a sepia tone to the image with default intensity.
   *
   * @throws IOException if an error occurs during the operation
   */
  void sepia() throws IOException;

  /**
   * Applies a sepia tone to the image with the specified intensity.
   *
   * @param percentage the intensity of the sepia tone, in percentage
   * @throws IOException if an error occurs during the operation
   */
  void sepia(int percentage) throws IOException;

  /**
   * Applies a luma transformation to the image with default intensity.
   *
   * @throws IOException if an error occurs during the operation
   */
  void luma() throws IOException;

  /**
   * Applies a luma transformation to the image with the specified intensity.
   *
   * @param percentage the intensity of the luma transformation, in percentage
   * @throws IOException if an error occurs during the operation
   */
  void luma(int percentage) throws IOException;

  /**
   * Adjusts the image's value channel.
   *
   * @throws IOException if an error occurs during the operation
   */
  void value() throws IOException;

  /**
   * Adjusts the image's intensity channel.
   *
   * @throws IOException if an error occurs during the operation
   */
  void intensity() throws IOException;

  /**
   * Flips the image horizontally.
   *
   * @throws IOException if an error occurs during the operation
   */
  void horizontalFlip() throws IOException;

  /**
   * Flips the image vertically.
   *
   * @throws IOException if an error occurs during the operation
   */
  void verticalFlip() throws IOException;

  /**
   * Extracts the red color component from the image.
   *
   * @throws IOException if an error occurs during the operation
   */
  void redComponent() throws IOException;

  /**
   * Extracts the green color component from the image.
   *
   * @throws IOException if an error occurs during the operation
   */
  void greenComponent() throws IOException;

  /**
   * Extracts the blue color component from the image.
   *
   * @throws IOException if an error occurs during the operation
   */
  void blueComponent() throws IOException;

  /**
   * Applies color correction to the image with default intensity.
   *
   * @throws IOException if an error occurs during the operation
   */
  void colorCorrect() throws IOException;

  /**
   * Applies color correction to the image with the specified intensity.
   *
   * @param percentage the intensity of the color correction, in percentage
   * @throws IOException if an error occurs during the operation
   */
  void colorCorrect(int percentage) throws IOException;

  /**
   * Adjusts the levels of the image using the specified black, mid, and white points.
   *
   * @param black the black point (shadow value)
   * @param mid   the mid-tone value
   * @param white the white point (highlight value)
   * @throws IOException if an error occurs during the operation
   */
  void adjustLevels(int black, int mid, int white) throws IOException;

  /**
   * Adjusts the levels of the image using the specified black, mid, and white points,
   * with an additional intensity parameter.
   *
   * @param black      the black point (shadow value)
   * @param mid        the mid-tone value
   * @param white      the white point (highlight value)
   * @param percentage the intensity of the adjustment
   * @throws IOException if an error occurs during the operation
   */
  void adjustLevels(int black, int mid, int white, int percentage) throws IOException;

  /**
   * Compresses the image to the specified percentage.
   *
   * @param percentage the compression level, in percentage
   * @throws IOException if an error occurs during the operation
   */
  void compress(int percentage) throws IOException;

  /**
   * Downscales the image to the specified width and height.
   *
   * @param newWidth  the new width of the image
   * @param newHeight the new height of the image
   * @throws IOException              if an error occurs during the operation
   * @throws IllegalArgumentException if the new dimensions are invalid
   */
  void downscale(int newWidth, int newHeight) throws IOException, IllegalArgumentException;

  /**
   * Resets the image to its original state, discarding all transformations.
   */
  void resetToOriginal();
}
