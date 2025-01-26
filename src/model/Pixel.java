package model;

import java.util.List;

/**
 * The {@code Pixel} interface defines the structure for a pixel in an image.
 * It provides methods to retrieve pixel data and represent the pixel as a string.
 */
public interface Pixel {

  /**
   * Retrieves the pixel data as a list of integers.
   * Each integer represents the intensity of a color channel (e.g., red, green, blue)
   * depending on the pixel's representation.
   *
   * @return a {@link List} of integers representing the pixel's color channel values.
   */
  public List<Integer> getPixelData();


  /**
   * Sets the pixel data with the specified list of integers.
   * Each integer in the list represents the intensity of a color channel.
   *
   * @param channels a {@link List} of integers representing the new color channel values.
   * @throws IllegalArgumentException if the number of channels is incompatible with pixel type.
   */
  void setPixelData(List<Integer> channels);


  /**
   * Returns a string representation of the pixel. The exact format of the string is
   * implementation-dependent but should provide useful information about the pixel.
   *
   * @return a {@code String} representing the pixel.
   */
  @Override
  public String toString();
}
