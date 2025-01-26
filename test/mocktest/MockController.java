package mocktest;

import java.io.IOException;

import controller.Features;

/**
 * A mock implementation of the {@link Features} interface used for testing purposes.
 * This class logs method calls instead of performing actual image manipulation tasks.
 */
public class MockController implements Features {

  private StringBuilder log;

  /**
   * Constructs a MockController instance with the specified log container.
   *
   * @param log the StringBuilder to which method calls will be logged
   */
  public MockController(StringBuilder log) {
    this.log = log;
  }

  /**
   * Logs the loading of an image.
   *
   * @param path      the file path of the image to be loaded
   * @param imageName the name of the image to be loaded
   * @throws IOException if an I/O error occurs while loading the image
   */
  @Override
  public void loadImage(String path, String imageName) throws IOException {
    log.append("loadImage called with path: ").append(path)
            .append(", imageName: ").append(imageName).append("\n");
  }

  /**
   * Logs the saving of an image.
   *
   * @param outputPath the output path where the image will be saved
   * @throws IOException if an I/O error occurs while saving the image
   */
  @Override
  public void saveImage(String outputPath) throws IOException {
    log.append("saveImage called with outputPath: ").append(outputPath).append("\n");
  }

  /**
   * Logs the brightening of the image.
   *
   * @param increment the increment value to brighten the image
   * @throws IOException if an I/O error occurs while brightening the image
   */
  @Override
  public void brighten(String increment) throws IOException {
    log.append("brighten called with increment: ").append(increment).append("\n");
  }

  /**
   * Logs the blurring of the image without a specified percentage.
   *
   * @throws IOException if an I/O error occurs while blurring the image
   */
  @Override
  public void blur() throws IOException {
    log.append("blur called with no percentage\n");
  }

  /**
   * Logs the blurring of the image with the specified percentage.
   *
   * @param percentage the percentage by which to blur the image
   * @throws IOException if an I/O error occurs while blurring the image
   */
  @Override
  public void blur(int percentage) throws IOException {
    log.append("blur called with percentage: ").append(percentage).append("\n");
  }

  /**
   * Logs the sharpening of the image without a specified percentage.
   *
   * @throws IOException if an I/O error occurs while sharpening the image
   */
  @Override
  public void sharpen() throws IOException {
    log.append("sharpen called with no percentage\n");
  }

  /**
   * Logs the sharpening of the image with the specified percentage.
   *
   * @param percentage the percentage by which to sharpen the image
   * @throws IOException if an I/O error occurs while sharpening the image
   */
  @Override
  public void sharpen(int percentage) throws IOException {
    log.append("sharpen called with percentage: ").append(percentage).append("\n");
  }

  /**
   * Logs the application of sepia filter without a specified percentage.
   *
   * @throws IOException if an I/O error occurs while applying sepia filter
   */
  @Override
  public void sepia() throws IOException {
    log.append("sepia called with no percentage\n");
  }

  /**
   * Logs the application of sepia filter with the specified percentage.
   *
   * @param percentage the percentage of sepia filter to apply
   * @throws IOException if an I/O error occurs while applying sepia filter
   */
  @Override
  public void sepia(int percentage) throws IOException {
    log.append("sepia called with percentage: ").append(percentage).append("\n");
  }

  /**
   * Logs the application of luma filter without a specified percentage.
   *
   * @throws IOException if an I/O error occurs while applying luma filter
   */
  @Override
  public void luma() throws IOException {
    log.append("luma called with no percentage\n");
  }

  /**
   * Logs the application of luma filter with the specified percentage.
   *
   * @param percentage the percentage of luma filter to apply
   * @throws IOException if an I/O error occurs while applying luma filter
   */
  @Override
  public void luma(int percentage) throws IOException {
    log.append("luma called with percentage: ").append(percentage).append("\n");
  }

  /**
   * Logs the application of value adjustment to the image.
   *
   * @throws IOException if an I/O error occurs while adjusting value
   */
  @Override
  public void value() throws IOException {
    log.append("value called\n");
  }

  /**
   * Logs the application of intensity adjustment to the image.
   *
   * @throws IOException if an I/O error occurs while adjusting intensity
   */
  @Override
  public void intensity() throws IOException {
    log.append("intensity called\n");
  }

  /**
   * Logs the horizontal flip of the image.
   *
   * @throws IOException if an I/O error occurs while flipping the image
   */
  @Override
  public void horizontalFlip() throws IOException {
    log.append("horizontalFlip called\n");
  }

  /**
   * Logs the vertical flip of the image.
   *
   * @throws IOException if an I/O error occurs while flipping the image
   */
  @Override
  public void verticalFlip() throws IOException {
    log.append("verticalFlip called\n");
  }

  /**
   * Logs the extraction of the red component from the image.
   *
   * @throws IOException if an I/O error occurs while extracting the red component
   */
  @Override
  public void redComponent() throws IOException {
    log.append("redComponent called\n");
  }

  /**
   * Logs the extraction of the green component from the image.
   *
   * @throws IOException if an I/O error occurs while extracting the green component
   */
  @Override
  public void greenComponent() throws IOException {
    log.append("greenComponent called\n");
  }

  /**
   * Logs the extraction of the blue component from the image.
   *
   * @throws IOException if an I/O error occurs while extracting the blue component
   */
  @Override
  public void blueComponent() throws IOException {
    log.append("blueComponent called\n");
  }

  /**
   * Logs the color correction of the image without a specified percentage.
   *
   * @throws IOException if an I/O error occurs while correcting colors
   */
  @Override
  public void colorCorrect() throws IOException {
    log.append("colorCorrect called with no percentage\n");
  }

  /**
   * Logs the color correction of the image with the specified percentage.
   *
   * @param percentage the percentage of color correction to apply
   * @throws IOException if an I/O error occurs while correcting colors
   */
  @Override
  public void colorCorrect(int percentage) throws IOException {
    log.append("colorCorrect called with percentage: ").append(percentage).append("\n");
  }

  /**
   * Logs the adjustment of levels with the specified black, mid, and white points.
   *
   * @param black the black point of the adjustment
   * @param mid   the mid point of the adjustment
   * @param white the white point of the adjustment
   * @throws IOException if an I/O error occurs while adjusting levels
   */
  @Override
  public void adjustLevels(int black, int mid, int white) throws IOException {
    log.append("adjustLevels called with black: ").append(black)
            .append(", mid: ").append(mid)
            .append(", white: ").append(white).append("\n");
  }

  /**
   * Logs the adjustment of levels with the specified black, mid, white points, and percentage.
   *
   * @param black      the black point of the adjustment
   * @param mid        the mid point of the adjustment
   * @param white      the white point of the adjustment
   * @param percentage the percentage of adjustment
   * @throws IOException if an I/O error occurs while adjusting levels
   */
  @Override
  public void adjustLevels(int black, int mid, int white, int percentage) throws IOException {
    log.append("adjustLevels called with black: ").append(black)
            .append(", mid: ").append(mid)
            .append(", white: ").append(white)
            .append(", percentage: ").append(percentage).append("\n");
  }

  /**
   * Logs the compression of the image with the specified percentage.
   *
   * @param percentage the percentage of compression to apply
   * @throws IOException if an I/O error occurs while compressing the image
   */
  @Override
  public void compress(int percentage) throws IOException {
    log.append("compress called with percentage: ").append(percentage).append("\n");
  }

  /**
   * Logs the downscaling of the image with the specified width and height.
   *
   * @param newWidth  the new width of the image
   * @param newHeight the new height of the image
   * @throws IOException              if an I/O error occurs while downscaling the image
   * @throws IllegalArgumentException if the width or height is less than or equal to zero
   */
  @Override
  public void downscale(int newWidth, int newHeight) throws IOException, IllegalArgumentException {
    if (newWidth <= 0 || newHeight <= 0) {
      throw new IllegalArgumentException("Width and height must be greater than zero.");
    }
    log.append("downscale called with newWidth: ").append(newWidth)
            .append(", newHeight: ").append(newHeight).append("\n");
  }

  /**
   * Logs the resetting of the image to its original state.
   */
  @Override
  public void resetToOriginal() {
    log.append("resetToOriginal called\n");
  }
}
