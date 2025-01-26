package model;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The {@code ImageADT} interface defines a set of operations that can be performed on an image.
 * Implementations of this interface are expected to provide concrete implementations for loading,
 * saving, and applying various transformations to images.
 */
public interface ImageADT {

  /**
   * Sets the pixel at the specified (x, y) coordinates to the given {@link Pixel} object.
   *
   * @param obj the pixel to be set.
   * @param x   the x-coordinate where the pixel will be set.
   * @param y   the y-coordinate where the pixel will be set.
   */
  void setPixel(Pixel obj, int x, int y);

  /**
   * Loads an image from the specified source path.
   *
   * @param bufferedImage the path to the image file to be loaded.
   */
  void loadImage(BufferedImage bufferedImage) throws IOException;

  /**
   * Saves the current image to the specified destination path.
   *
   * @param imageName the path to the file where the image will be saved.
   */
  public BufferedImage getImage(String imageName) throws IOException;

  /**
   * Visualizes the value of the specified pixel. The value is typically the highest intensity
   * among the red, green, and blue channels.
   *
   * @param pixel the {@link Pixel} whose value is to be visualized.
   * @return the value of the pixel.
   */
  public int visualizeValue(Pixel pixel);

  /**
   * Visualizes the intensity of the specified pixel. The intensity is calculated as the average
   * intensity across the red, green, and blue channels.
   *
   * @param pixel the {@link Pixel} whose intensity is to be visualized.
   * @return the intensity of the pixel.
   */
  public int visualizeIntensity(Pixel pixel);

  /**
   * Converts the image to grayscale by applying a specific grayscale filter to each pixel.
   *
   * @return a new {@code ImageADT} instance representing the grayscale version of the image.
   */
  public ImageADT convertToGreyScale();

  /**
   * Converts a specific percentage of the image to grayscale.
   *
   * @param p the percentage of the image to apply the grayscale filter to.
   * @return a new {@code ImageADT2} instance representing the partially grayscale image.
   */
  public ImageADT convertToGreyScale(int p);

  /**
   * Converts the image to sepia tone by applying a sepia filter to each pixel.
   *
   * @return a new {@code ImageADT} instance representing the sepia-toned version of the image.
   */
  public ImageADT convertToSepia();

  /**
   * Converts a specific percentage of the image to sepia tone.
   *
   * @param p the percentage of the image to apply the sepia filter to.
   * @return a new {@code ImageADT2} instance representing the partially sepia-toned image.
   */
  public ImageADT convertToSepia(int p);

  /**
   * Converts the image to its value component by applying a filter that visualizes the value
   * (maximum intensity of each pixel's color channels).
   *
   * @return a new {@code ImageADT} instance representing the value-converted image.
   */
  public ImageADT convertToValue();

  /**
   * Converts the image to its intensity component by applying a filter that visualizes the
   * average intensity of each pixel's color channels.
   *
   * @return a new {@code ImageADT} instance representing the intensity-converted image.
   */
  public ImageADT convertToIntensity();

  /**
   * Applies a blur effect to the image using a Gaussian blur filter.
   *
   * @return a new {@code ImageADT} instance representing the blurred image.
   */
  public ImageADT blur();

  /**
   * Applies a blur effect to a specific percentage of the image.
   *
   * @param p the percentage of the image to apply the blur effect to.
   * @return a new {@code ImageADT2} instance representing the blurred image.
   */
  public ImageADT blur(int p);

  /**
   * Sharpens the image by applying a sharpening filter.
   *
   * @return a new {@code ImageADT} instance representing the sharpened image.
   */
  public ImageADT sharpen();

  /**
   * Applies a sharpen effect to a specific percentage of the image.
   *
   * @param p the percentage of the image to apply the sharpen effect to.
   * @return a new {@code ImageADT2} instance representing the sharpened image.
   */
  public ImageADT sharpen(int p);

  /**
   * Adjusts the brightness of the image by a specified increment.
   *
   * @param increment the value to adjust the brightness by. Positive values increase brightness,
   *                  while negative values decrease it.
   * @return a new {@code ImageADT} instance representing the image with adjusted brightness.
   */
  public ImageADT brightness(int increment);

  /**
   * Flips the image horizontally, producing a mirror image along the vertical axis.
   *
   * @return a new {@code ImageADT} instance representing the horizontally flipped image.
   */
  public ImageADT flipHorizontal();

  /**
   * Flips the image vertically, producing a mirror image along the horizontal axis.
   *
   * @return a new {@code ImageADT} instance representing the vertically flipped image.
   */
  public ImageADT flipVertical();

  /**
   * Creates a version of the image where only the red channel is retained, while the green
   * and blue channels are discarded.
   *
   * @return a new {@code ImageADT} instance representing the red channel of the image.
   */
  public ImageADT createRed();

  /**
   * Creates a version of the image where only the green channel is retained, while the red
   * and blue channels are discarded.
   *
   * @return a new {@code ImageADT} instance representing the green channel of the image.
   */
  public ImageADT createGreen();

  /**
   * Creates a version of the image where only the blue channel is retained, while the red
   * and green channels are discarded.
   *
   * @return a new {@code ImageADT} instance representing the blue channel of the image.
   */
  public ImageADT createBlue();


  /**
   * Compresses the image based on a given threshold.
   *
   * @param threshold the compression threshold.
   * @return a new {@code ImageADT2} instance representing the compressed image.
   */
  public ImageADT compressImage(int threshold);

  /**
   * Applies color correction to the image by adjusting the color balance
   * across all channels (red, green, and blue) to produce a more accurate or
   * visually appealing color profile. This may involve modifying brightness,
   * contrast, and individual channel levels.
   *
   * @return a new {@code ImageADT2} instance representing the color-corrected image.
   */
  public ImageADT colorCorrection();

  // New method that accepts an integer parameter (splitPercentage)
  public ImageADT colorCorrection(int p);

  /**
   * Generates a histogram for the image, which represents the distribution of
   * intensity values across the red, green, and blue channels.
   *
   * @return a new {@code ImageADT2} instance containing the histogram data of the image.
   */
  public ImageADT generateHistogram();

  /**
   * Adjusts the levels of the image by mapping its current intensity range to
   * a desired range, which can enhance contrast or bring out details in the
   * shadows and highlights.
   *
   * @return a new {@code ImageADT2} instance representing the image with adjusted levels.
   */
  public ImageADT adjustLevels();

  public ImageADT adjustLevels(int p);

  public ImageADT downscale(int newWidth, int newHeight);

  public ImageADT applyPartialWithMasking(ImageADT maskingImage, String effectType);
}
