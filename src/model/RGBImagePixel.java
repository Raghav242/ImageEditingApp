package model;

import java.util.List;

/**
 * The {@code RGBImagePixel} class represents a pixel in an RGB image.
 * Each pixel is defined by its red, green, and blue color components.
 * This class implements the {@link Pixel} interface.
 */
public class RGBImagePixel implements Pixel {

  private int r; // Red component of the pixel
  private int g; // Green component of the pixel
  private int b; // Blue component of the pixel

  /**
   * Constructs a new {@code RGBImagePixel} with the specified color components.
   *
   * @param r the red component (0-255).
   * @param g the green component (0-255).
   * @param b the blue component (0-255).
   */
  public RGBImagePixel(int r, int g, int b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  @Override
  public List<Integer> getPixelData() {
    return List.of(r, g, b);
  }

  @Override
  public void setPixelData(List<Integer> channels) {
    if (channels == null || channels.size() != 3) {
      throw new IllegalArgumentException("RGB pixel requires exactly 3 color channel values.");
    }
    this.r = channels.get(0);
    this.g = channels.get(1);
    this.b = channels.get(2);
  }

  /**
   * Returns the red component of this pixel.
   *
   * @return the red component (0-255).
   */
  public int getRed() {
    return r;
  }

  /**
   * Returns the green component of this pixel.
   *
   * @return the green component (0-255).
   */
  public int getGreen() {
    return g;
  }

  /**
   * Returns the blue component of this pixel.
   *
   * @return the blue component (0-255).
   */
  public int getBlue() {
    return b;
  }

  @Override
  public String toString() {
    return String.format("[%d, %d, %d]", r, g, b);
  }
}
