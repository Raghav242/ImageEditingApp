package model;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The {@code RGBImage} class represents an image in RGB color space. It extends
 * {@link AbstractImage} and provides methods to load, save, and manipulate RGB images. The class
 * supports various image processing operations such as converting to grayscale, sepia, adjusting
 * brightness, and combining color channels.
 */
public class RGBImage extends AbstractImage {

  private RGBImagePixel[][] image;

  protected int getWidth() {
    return width;
  }

  protected int getHeight() {
    return height;
  }

  private CompressionImage c = new CompressionImage();

  public void setImage(RGBImagePixel[][] image) {
    this.image = image;
  }


  public void setWidth(int width) {
    this.width = width;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  /**
   * Retrieves the pixel at the specified (x, y) coordinates.
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the {@link RGBImagePixel} at the specified coordinates.
   */
  protected RGBImagePixel getPixel(int x, int y) {
    return image[x][y];
  }

  /**
   * Sets the pixel at the specified (x, y) coordinates with the provided pixel object.
   *
   * @param obj the {@link Pixel} to set.
   * @param x   the x-coordinate of the pixel.
   * @param y   the y-coordinate of the pixel.
   * @throws IndexOutOfBoundsException if the coordinates are out of bounds.
   * @throws IllegalArgumentException  if the pixel object is not of type {@link RGBImagePixel}.
   */
  public void setPixel(Pixel obj, int x, int y) {
    if (x < 0 || x >= image.length || y < 0 || y >= image[0].length) {
      throw new IndexOutOfBoundsException("Pixel coordinates are out of bounds");
    }
    if (!(obj instanceof RGBImagePixel)) {
      throw new IllegalArgumentException("Not a RGBPixel data");
    }
    image[x][y] = (RGBImagePixel) obj;
  }

  /**
   * Loads an image from the specified file path.
   *
   * @param bufferedImage the path to the image file.
   */

  @Override
  public void loadImage(BufferedImage bufferedImage) throws IOException {

    width = bufferedImage.getWidth();
    height = bufferedImage.getHeight();
    image = new RGBImagePixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int rgb = bufferedImage.getRGB(j, i);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        this.setPixel(new RGBImagePixel(red, green, blue), i, j);
      }
    }
  }


  public RGBImagePixel[][] getImage() {
    return image;
  }

  /**
   * Saves the current image to the specified destination path.
   *
   * @param imageName the path where the image will be saved.
   */
  @Override
  public BufferedImage getImage(String imageName) throws IllegalStateException {
    if (this.width <= 0 || this.height <= 0) {
      throw new IllegalStateException("Image dimensions must be set before saving");
    }
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        RGBImagePixel pixel = getPixel(i, j);
        int rgbFormatOfBufferedImage =
                (pixel.getRed() << 16) | (pixel.getGreen() << 8) | pixel.getBlue();
        bufferedImage.setRGB(j, i, rgbFormatOfBufferedImage);
      }
    }
    return bufferedImage;
  }

  /**
   * Converts the current image to grayscale.
   *
   * @return a new {@link AbstractImage} that is a grayscale version of the original image.
   */
  public ImageADT convertToGreyScale() {
    RGBImage greyScaleImage = createImage();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        RGBImagePixel pixel = getPixel(i, j);
        int grayY = visualizeLuma(pixel);

        RGBImagePixel greyPixel = new RGBImagePixel(grayY, grayY, grayY);
        greyScaleImage.setPixel(greyPixel, i, j);
      }
    }
    System.out.println("Converted to Grayscale");

    return greyScaleImage;
  }

  /**
   * Converts the image to grayscale up to a specified width percentage.
   *
   * @param p the percentage of the image width to apply grayscale (0-100)
   */
  @Override
  public ImageADT convertToGreyScale(int p) {
    int splitIndex = (int) (width * (p / 100.0));
    ImageADT greyScaleImage = createImage();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        RGBImagePixel pixel = getPixel(i, j);
        if (j <= splitIndex) {
          int grayY = visualizeLuma(pixel);
          greyScaleImage.setPixel(new RGBImagePixel(grayY, grayY, grayY), i, j);
        } else {
          greyScaleImage.setPixel(pixel, i, j);
        }
      }
    }
    System.out.println("Converted to Grayscale " + p + " % of the image");
    return greyScaleImage;
  }

  /**
   * Converts the current image to sepia tone.
   *
   * @return a new {@link AbstractImage} that is a sepia version of the original image.
   */
  public AbstractImage convertToSepia() {
    AbstractImage sepiaImage = createImage();

    float[][] sepiaTone = {{0.393f, 0.769f, 0.189f},
        {0.349f, 0.686f, 0.168f},
        {0.272f, 0.534f, 0.131f}};

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        RGBImagePixel pixel = getPixel(i, j);

        // Apply the sepia matrix to the pixel
        int newRed = Math.round(
                pixel.getRed() * sepiaTone[0][0] + pixel.getGreen() * sepiaTone[0][1]
                        + pixel.getBlue() * sepiaTone[0][2]);
        int newGreen = Math.round(
                pixel.getRed() * sepiaTone[1][0] + pixel.getGreen() * sepiaTone[1][1]
                        + pixel.getBlue() * sepiaTone[1][2]);
        int newBlue = Math.round(
                pixel.getRed() * sepiaTone[2][0] + pixel.getGreen() * sepiaTone[2][1]
                        + pixel.getBlue() * sepiaTone[2][2]);

        newRed = clampValue(newRed);
        newGreen = clampValue(newGreen);
        newBlue = clampValue(newBlue);

        // Create a new pixel with the sepia tone
        RGBImagePixel sepiaPixel = new RGBImagePixel(newRed, newGreen, newBlue);

        sepiaImage.setPixel(sepiaPixel, i, j);
      }
    }

    System.out.println("Applied Sepia");
    return sepiaImage;
  }

  /**
   * Converts the image to sepia tone up to a specified width percentage.
   *
   * @param p the percentage of the image width to apply sepia (0-100)
   * @return a new partially sepia-toned {@link ImageADT} image
   */
  public ImageADT convertToSepia(int p) {
    int splitIndex = (int) (width * (p / 100.0));
    ImageADT sepiaImage = createImage();

    float[][] sepiaTone = {{0.393f, 0.769f, 0.189f},
        {0.349f, 0.686f, 0.168f},
        {0.272f, 0.534f, 0.131f}};

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        RGBImagePixel pixel = getPixel(i, j);
        if (j <= splitIndex) {  // Apply sepia only to the left side
          int newRed = Math.round(
                  pixel.getRed() * sepiaTone[0][0] + pixel.getGreen() * sepiaTone[0][1]
                          + pixel.getBlue() * sepiaTone[0][2]);
          int newGreen = Math.round(
                  pixel.getRed() * sepiaTone[1][0] + pixel.getGreen() * sepiaTone[1][1]
                          + pixel.getBlue() * sepiaTone[1][2]);
          int newBlue = Math.round(
                  pixel.getRed() * sepiaTone[2][0] + pixel.getGreen() * sepiaTone[2][1]
                          + pixel.getBlue() * sepiaTone[2][2]);
          sepiaImage.setPixel(
                  new RGBImagePixel(clampValue(newRed), clampValue(newGreen), clampValue(newBlue)),
                  i, j);
        } else {
          sepiaImage.setPixel(pixel, i, j);
        }
      }
    }
    System.out.println("Applied sepia to " + p + " % of the image.");
    return sepiaImage;
  }

  /**
   * Calculates the luma of the specified pixel.
   *
   * @param pixel the pixel for which to calculate the luma
   * @return the luma value of the pixel
   * @throws IllegalArgumentException if the pixel is not of type {@link RGBImagePixel}
   */
  public int visualizeLuma(Pixel pixel) {
    if (!(pixel instanceof RGBImagePixel)) {
      throw new IllegalArgumentException("Invalid RGB pixel type");
    }
    RGBImagePixel rgbPixel = (RGBImagePixel) pixel;
    return Math.round(
            0.2126f * rgbPixel.getRed()
                    + 0.7152f * rgbPixel.getGreen() + 0.0722f * rgbPixel.getBlue());
  }

  /**
   * Adjusts the brightness of the image by the specified increment.
   *
   * @param increment the value by which to adjust the brightness (can be negative)
   * @return a new {@link AbstractImage} with adjusted brightness
   */
  public AbstractImage brightness(int increment) {
    RGBImage newImage = new RGBImage(); // Create a new RGBImage instance
    // Set dimensions
    newImage.width = this.width; // Set dimensions
    newImage.height = this.height;
    newImage.image = new RGBImagePixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        RGBImagePixel pixel = getPixel(i, j);

        int newRed = Math.min(Math.max(pixel.getRed() + increment, 0), 255);
        int newGreen = Math.min(Math.max(pixel.getGreen() + increment, 0), 255);
        int newBlue = Math.min(Math.max(pixel.getBlue() + increment, 0), 255);

        // Create a new pixel with the adjusted values
        RGBImagePixel transformedPixel = new RGBImagePixel(newRed, newGreen, newBlue);
        newImage.setPixel(transformedPixel, i, j); // Set the pixel in the new image
      }
    }
    System.out.println("Brightened Image");
    return newImage; // Return the new brightened image
  }

  /**
   * Combines three RGB images (red, green, blue) into a single RGB image.
   *
   * @param redImage   the red channel image
   * @param greenImage the green channel image
   * @param blueImage  the blue channel image
   * @return a combined {@link ImageADT} representing the merged RGB image
   * @throws IllegalArgumentException if the images do not have the same dimensions
   */
  public static RGBImage rgbCombine(RGBImage redImage, RGBImage greenImage,
                                    RGBImage blueImage) {

    if (redImage.getWidth() != greenImage.getWidth()
            || redImage.getHeight() != greenImage.getHeight()
            || redImage.getWidth() != blueImage.getWidth()
            || redImage.getHeight() != blueImage.getHeight()) {
      throw new IllegalArgumentException("All images must have the same dimensions.");
    }

    // combined image
    RGBImage combinedImage = new RGBImage();

    combinedImage.width = redImage.width;
    combinedImage.height = redImage.height;
    combinedImage.image = new RGBImagePixel[combinedImage.height][combinedImage.width];

    // Combine pixel values
    for (int i = 0; i < redImage.height; i++) {
      for (int j = 0; j < redImage.width; j++) {
        int redValue = redImage.getPixel(i, j).getRed();
        int greenValue = greenImage.getPixel(i, j).getGreen();
        int blueValue = blueImage.getPixel(i, j).getBlue();

        RGBImagePixel combinedPixel = new RGBImagePixel(redValue, greenValue, blueValue);
        combinedImage.setPixel(combinedPixel, i, j);
      }
    }

    return combinedImage;
  }

  /**
   * Creates a new image of the specified color channel.
   *
   * @param color the color channel to create (red, green, or blue)
   * @return a new {@link AbstractImage} of the specified color channel
   */
  private RGBImage createColorImage(Channel color) {
    AbstractImage colorImage = createImage();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        RGBImagePixel pixel = getPixel(i, j);
        RGBImagePixel newPixel;

        switch (color) {
          case RED:
            newPixel = new RGBImagePixel(pixel.getRed(), pixel.getRed(), pixel.getRed());
            break;
          case GREEN:
            newPixel = new RGBImagePixel(pixel.getGreen(), pixel.getGreen(), pixel.getGreen());
            break;
          case BLUE:
            newPixel = new RGBImagePixel(pixel.getBlue(), pixel.getBlue(), pixel.getBlue());
            break;
          default:
            throw new IllegalArgumentException("Invalid color: " + color);
        }

        colorImage.setPixel(newPixel, i, j);
      }
    }

    return (RGBImage) colorImage;
  }

  /**
   * Creates a new image with only the red channel.
   *
   * @return a new {@link AbstractImage} containing only the red channel
   */
  public RGBImage createRed() {
    System.out.println("Created Red Image");
    return createColorImage(Channel.RED);
  }

  /**
   * Creates a new image with only the green channel.
   *
   * @return a new {@link AbstractImage} containing only the green channel
   */
  public RGBImage createGreen() {
    System.out.println("Created Green Image");
    return createColorImage(Channel.GREEN);
  }

  /**
   * Creates a new image with only the blue channel.
   *
   * @return a new {@link AbstractImage} containing only the blue channel
   */
  public RGBImage createBlue() {
    System.out.println("Created Blue Image");
    return createColorImage(Channel.BLUE);
  }

  /**
   * Creates a new {@link AbstractImage} instance based on the current image dimensions.
   *
   * @return a new {@link AbstractImage}
   */
  @Override
  protected RGBImage createImage() {
    RGBImage newImage = new RGBImage();
    newImage.setWidth(this.width);
    newImage.setHeight(this.height);
    newImage.setImage(new RGBImagePixel[height][width]);
    return newImage;
  }

  /**
   * Clamps the given value to the range [0, 255].
   *
   * @param value the value to clamp
   * @return the clamped value
   */
  public int clampValue(int value) {
    return Math.min(Math.max(value, 0), 255);
  }

  /**
   * Enum representing the primary color channels (RED, GREEN, BLUE).
   */
  private enum Channel {
    RED, GREEN, BLUE
  }

  /**
   * Converts the current image to a value-based image.
   *
   * @return a new {@link AbstractImage} representing the value-based image.
   */
  public AbstractImage convertToValue() {
    AbstractImage greyScaleImage = createImage();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        RGBImagePixel pixel = getPixel(i, j);
        int grayY = visualizeValue(pixel);
        RGBImagePixel greyPixel = new RGBImagePixel(grayY, grayY, grayY);
        greyScaleImage.setPixel(greyPixel, i, j);
      }
    }
    System.out.println("Converted to value");
    return greyScaleImage;
  }

  /**
   * Converts the current image to an intensity-based image.
   *
   * @return a new {@link AbstractImage} representing the intensity-based image.
   */
  public AbstractImage convertToIntensity() {
    AbstractImage greyScaleImage = createImage();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        RGBImagePixel pixel = getPixel(i, j);
        int grayY = visualizeIntensity(pixel);
        RGBImagePixel greyPixel = new RGBImagePixel(grayY, grayY, grayY);
        greyScaleImage.setPixel(greyPixel, i, j);
      }
    }
    System.out.println("Converted Intensity");
    return greyScaleImage;
  }

  /**
   * pixelMatrixToString - is a method to loop through matrix and print it.
   */
  public static String pixelMatrixToString(RGBImagePixel[][] imageMatrix) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < imageMatrix.length; i++) {
      for (int j = 0; j < imageMatrix[i].length; j++) {
        RGBImagePixel pixel = imageMatrix[i][j];
        sb.append(String.format("(%d, %d, %d)", pixel.getRed(), pixel.getGreen(), pixel.getBlue()));
        if (j < imageMatrix[i].length - 1) {
          sb.append(", ");
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }


  /**
   * Checks if this RGBImage is equal to another object.
   *
   * @param obj the object to compare.
   * @return true if this RGBImage is equal to the specified object, false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    // Check if the object is compared with itself
    if (this == obj) {
      return true;
    }

    // Check if obj is an instance of RGBImage
    if (!(obj instanceof RGBImage)) {
      return false;
    }

    RGBImage other = (RGBImage) obj;

    // Check if dimensions are equal
    if (this.getWidth() != other.getWidth() || this.getHeight() != other.getHeight()) {
      return false;
    }

    // Check if pixel values are equal
    for (int i = 0; i < this.getHeight(); i++) {
      for (int j = 0; j < this.getWidth(); j++) {
        if (!this.getPixel(i, j).equals(other.getPixel(i, j))) {
          return false; // Return false if any pixel does not match
        }
      }
    }

    return true;
  }

  /**
   * Computes a hash code for this RGBImage.
   *
   * @return a hash code value for this RGBImage
   */
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 31 * hash + this.getWidth();
    hash = 31 * hash + this.getHeight();

    // Include pixel values in hash calculation
    for (int i = 0; i < this.getHeight(); i++) {
      for (int j = 0; j < this.getWidth(); j++) {
        hash = 31 * hash + this.getPixel(i, j).hashCode();
      }
    }

    return hash;
  }


  /**
   * Converts the image to grayscale up to a specified width percentage.
   *
   * @param p the percentage of the image width to apply grayscale (0-100)
   */
  @Override
  public ImageADT converttoLuma(int p) {
    int splitIndex = (int) (width * (p / 100.0));
    ImageADT greyScaleImage = createImage();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        RGBImagePixel pixel = getPixel(i, j);
        if (j <= splitIndex) {
          int grayY = visualizeLuma(pixel);
          greyScaleImage.setPixel(new RGBImagePixel(grayY, grayY, grayY), i, j);
        } else {
          greyScaleImage.setPixel(pixel, i, j);
        }
      }
    }
    return greyScaleImage;
  }


  private RGBImage applyHaarTransformation(int threshold) {

    int padSize = Math.max(c.paddingSize(this.height), c.paddingSize(this.width));

    int[][] redChannel = new int[padSize][padSize];
    System.out.println(redChannel);
    int[][] greenChannel = new int[padSize][padSize];
    int[][] blueChannel = new int[padSize][padSize];

    // Populate color channel arrays from the image
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        RGBImagePixel pixel = this.getPixel(i, j);
        redChannel[i][j] = pixel.getRed();
        greenChannel[i][j] = pixel.getGreen();
        blueChannel[i][j] = pixel.getBlue();
      }
    }

    // Apply Haar 2D transformation to each color channel
    redChannel = c.haar2DImageTransformation(redChannel, redChannel.length, threshold);
    greenChannel = c.haar2DImageTransformation(greenChannel, greenChannel.length, threshold);
    blueChannel = c.haar2DImageTransformation(blueChannel, blueChannel.length, threshold);

    // Update the image with the transformed color channels
    return createHaarTansformedImage(redChannel, greenChannel, blueChannel);
  }

  private ImageADT applyInverseHaarTransformation() {

    int padSize = Math.max(c.paddingSize(this.height), c.paddingSize(this.width));

    // Initialize 2D arrays for each color channel
    int[][] redChannel = new int[padSize][padSize];
    int[][] greenChannel = new int[padSize][padSize];
    int[][] blueChannel = new int[padSize][padSize];

    // Populate color channel arrays from the image
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        RGBImagePixel pixel = this.getPixel(i, j);
        redChannel[i][j] = pixel.getRed();
        greenChannel[i][j] = pixel.getGreen();
        blueChannel[i][j] = pixel.getBlue();
      }
    }

    redChannel = c.haar2DImageInverse(redChannel, redChannel.length);
    greenChannel = c.haar2DImageInverse(greenChannel, greenChannel.length);
    blueChannel = c.haar2DImageInverse(blueChannel, blueChannel.length);

    return createCompressedFinalImage(redChannel, greenChannel, blueChannel);
  }


  private ImageADT createCompressedFinalImage(int[][] red, int[][] green, int[][] blue) {
    RGBImage newImage = new RGBImage();
    newImage.setWidth(this.width);
    newImage.setHeight(this.height);
    newImage.image = new RGBImagePixel[this.height][this.width];
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        int redValue = clampValue(red[i][j]);
        int greenValue = clampValue(green[i][j]);
        int blueValue = clampValue(blue[i][j]);
        newImage.setPixel(new RGBImagePixel(redValue, greenValue, blueValue), i, j);
      }
    }

    return newImage;
  }


  private RGBImage createHaarTansformedImage(int[][] red, int[][] green, int[][] blue) {

    RGBImage newImage = new RGBImage();
    newImage.setWidth(this.width);
    newImage.setHeight(this.height);
    newImage.image = new RGBImagePixel[this.height][this.width];
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        int redValue = red[i][j];
        int greenValue = green[i][j];
        int blueValue = blue[i][j];
        newImage.setPixel(new RGBImagePixel(redValue, greenValue, blueValue), i, j);
      }
    }

    return newImage;
  }


  public ImageADT compressImage(int threshold) {
    return applyHaarTransformation(90).applyInverseHaarTransformation();
  }

  private int shadow;
  private int mid;
  private int highlight;

  /**
   * Applies color correction to the current RGBImage by aligning color channels.
   */
  public ImageADT colorCorrection() {
    int width = this.getWidth();
    int height = this.getHeight();

    // Create a new RGBImage to store the corrected pixels
    RGBImage correctedImage = new RGBImage();
    correctedImage.width = width;
    correctedImage.height = height;
    correctedImage.image = new RGBImagePixel[height][width];

    // Arrays to store histogram data for red, green, and blue channels
    int[] redHistogram = new int[256];
    int[] greenHistogram = new int[256];
    int[] blueHistogram = new int[256];

    // Calculate the histogram for each color channel
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        RGBImagePixel pixel = image[i][j];
        redHistogram[pixel.getRed()]++;
        greenHistogram[pixel.getGreen()]++;
        blueHistogram[pixel.getBlue()]++;
      }
    }

    // Find the peak values
    int redPeak = findPeak(redHistogram);
    int greenPeak = findPeak(greenHistogram);
    int bluePeak = findPeak(blueHistogram);

    // Calculate the average peak position
    int averagePeak = (redPeak + greenPeak + bluePeak) / 3;

    // Calculate offsets to align each channel's peak with the average peak
    int redOffset = averagePeak - redPeak;
    int greenOffset = averagePeak - greenPeak;
    int blueOffset = averagePeak - bluePeak;

    // Apply offsets to each pixel in the image and store in the new image
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        RGBImagePixel pixel = image[i][j];

        // Apply the offsets and clamp the values
        int newRed = clamp(pixel.getRed() + redOffset);
        int newGreen = clamp(pixel.getGreen() + greenOffset);
        int newBlue = clamp(pixel.getBlue() + blueOffset);

        // Create a new RGBImagePixel for the corrected color
        RGBImagePixel correctedPixel = new RGBImagePixel(newRed, newGreen, newBlue);

        // Set the corrected pixel in the new image
        correctedImage.setPixel(correctedPixel, i, j);
      }
    }

    System.out.println("Color correction applied successfully.");
    return correctedImage; // Return the new corrected image
  }

  /**
   * Applies color correction to the image up to a specified width percentage.
   *
   * @param p the percentage of the image width to apply color correction (0-100)
   * @return a new ImageADT with color correction applied
   */
  @Override
  public ImageADT colorCorrection(int p) {
    int splitIndex = (int) (width * (p / 100.0));
    ImageADT correctedImage = createImage();

    // Arrays to store histogram data for red, green, and blue channels
    int[] redHistogram = new int[256];
    int[] greenHistogram = new int[256];
    int[] blueHistogram = new int[256];

    // Calculate the histogram for each color channel
    for (int i = 0; i < height; i++) {
      for (int j = 0; j <= splitIndex; j++) {
        RGBImagePixel pixel = getPixel(i, j);
        redHistogram[pixel.getRed()]++;
        greenHistogram[pixel.getGreen()]++;
        blueHistogram[pixel.getBlue()]++;
      }
    }

    // Find the peak values and offsets
    int redPeak = findPeak(redHistogram);
    int greenPeak = findPeak(greenHistogram);
    int bluePeak = findPeak(blueHistogram);
    int averagePeak = (redPeak + greenPeak + bluePeak) / 3;
    int redOffset = averagePeak - redPeak;
    int greenOffset = averagePeak - greenPeak;
    int blueOffset = averagePeak - bluePeak;

    // Apply color correction to each pixel up to the split index
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        RGBImagePixel pixel = getPixel(i, j);
        if (j <= splitIndex) {
          int newRed = clamp(pixel.getRed() + redOffset);
          int newGreen = clamp(pixel.getGreen() + greenOffset);
          int newBlue = clamp(pixel.getBlue() + blueOffset);
          correctedImage.setPixel(new RGBImagePixel(newRed, newGreen, newBlue), i, j);
        } else {
          correctedImage.setPixel(pixel, i, j); // Keep original pixel for remaining width
        }
      }
    }

    System.out.println("Color correction applied successfully.");
    return correctedImage;
  }


  /**
   * Helper method to find the peak position in a histogram array.
   *
   * @param histogram the histogram array.
   * @return the index of the highest peak.
   */
  private int findPeak(int[] histogram) {
    int maxCount = 0;
    int peak = 0;
    for (int i = 0; i < histogram.length; i++) {
      if (histogram[i] > maxCount) {
        maxCount = histogram[i];
        peak = i;
      }
    }
    return peak;
  }

  /**
   * Helper method to clamp color values between 0 and 255.
   *
   * @param value the color value to clamp.
   * @return the clamped color value.
   */
  private int clamp(int value) {
    return Math.max(0, Math.min(255, value));
  }

  //LevelsAdjustment method

  /**
   * Sets the shadow, mid, and highlight values used for levels adjustment. These values define the
   * range of shadows, midtones, and highlights to be adjusted.
   *
   * @param shadow    the shadow level, representing the darkest value in the image.
   * @param mid       the midtone level, representing the middle point of the image's brightness
   *                  range.
   * @param highlight the highlight level, representing the lightest value in the image.
   * @throws IllegalArgumentException if highlight is less than or equal to shadow.
   */
  public void setLevels(int shadow, int mid, int highlight) {
    this.shadow = shadow;
    this.mid = mid;
    this.highlight = highlight;
  }

  /**
   * Adjusts the levels of the image based on the set shadow, mid, and highlight values. This method
   * modifies the brightness and contrast of the image according to the specified levels for
   * shadows, midtones, and highlights.
   *
   * @return a new {@link RGBImage} object with the adjusted pixel values.
   * @throws IllegalArgumentException if the highlight value is less than or equal to the shadow
   *                                  value.
   */
  @Override
  public ImageADT adjustLevels() {

    validateHighlightAndShadow();

    RGBImage adjustedImage = new RGBImage();
    adjustedImage.width = width;
    adjustedImage.height = height;
    adjustedImage.image = new RGBImagePixel[height][width];

    double scale = computeScale();
    double midPoint = computeMidPoint(scale);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        RGBImagePixel originalPixel = image[y][x];
        adjustedImage.setPixel(applyLevelsToPixel(originalPixel, scale, midPoint), y, x);
      }
    }

    System.out.println("Levels adjustment applied successfully.");
    return adjustedImage;
  }

  @Override
  public ImageADT adjustLevels(int p) {
    validateHighlightAndShadow(); // Validation for highlight and shadow range

    int splitIndex = (int) (width * (p / 100.0));
    ImageADT adjustedImage = createImage();

    double scale = computeScale();
    double midPoint = computeMidPoint(scale);

    // Adjust levels for each pixel up to split index
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        RGBImagePixel originalPixel = getPixel(y, x);
        if (x <= splitIndex) {
          adjustedImage.setPixel(applyLevelsToPixel(originalPixel, scale, midPoint), y, x);
        } else {
          adjustedImage.setPixel(originalPixel, y, x);
        }
      }
    }

    System.out.println("Levels adjustment applied successfully.");
    return adjustedImage;
  }

  // Helper Methods

  private void validateHighlightAndShadow() {
    if (highlight <= shadow) {
      throw new IllegalArgumentException("Highlight must be greater than shadow for "
              + "levels adjustment.");
    }
  }


  private double computeScale() {
    return 255.0 / (highlight - shadow);
  }

  private double computeMidPoint(double scale) {
    return (mid - shadow) * scale;
  }

  private RGBImagePixel applyLevelsToPixel(RGBImagePixel pixel, double scale, double midPoint) {
    int newRed = applyLevelsAdjustment(pixel.getRed(), scale, midPoint);
    int newGreen = applyLevelsAdjustment(pixel.getGreen(), scale, midPoint);
    int newBlue = applyLevelsAdjustment(pixel.getBlue(), scale, midPoint);

    return new RGBImagePixel(newRed, newGreen, newBlue);
  }



  // Helper method to apply the levels adjustment
  private int applyLevelsAdjustment(int value, double scale, double midPoint) {
    // Adjust the value based on shadow, scale, and mid-point
    double adjustedValue = (value - shadow) * scale;

    // Adjust for the mid-point
    if (adjustedValue < midPoint) {
      adjustedValue = adjustedValue * (midPoint / (midPoint - shadow));
    } else {
      adjustedValue = adjustedValue * (255.0 - midPoint) / (255.0 - mid);
    }

    adjustedValue = Math.min(255, Math.max(0, adjustedValue));
    return (int) adjustedValue;
  }

  /**
   * Downscales the image stored in this RGBImage instance.
   *
   * @param newWidth  The desired width of the downscaled image.
   * @param newHeight The desired height of the downscaled image.
   * @return A new RGBImage object containing the downscaled image.
   */
  @Override
  public ImageADT downscale(int newWidth, int newHeight) {
    if (newWidth <= 0 || newHeight <= 0) {
      throw new IllegalArgumentException("New dimensions must be greater than zero.");
    }

    if (newWidth > getWidth() || newHeight > getHeight()) {
      throw new IllegalArgumentException("New dimensions cannot exceed original image dimensions.");
    }

    RGBImage downscaledImage = new RGBImage();
    downscaledImage.setWidth(newWidth);
    downscaledImage.setHeight(newHeight);
    RGBImagePixel[][] downscaledPixels = new RGBImagePixel[newHeight][newWidth];

    double xProportion = (double) this.getWidth() / newWidth;
    double yProportion = (double) this.getHeight() / newHeight;

    for (int yDash = 0; yDash < newHeight; yDash++) {
      for (int xDash = 0; xDash < newWidth; xDash++) {
        downscaledPixels[yDash][xDash] = computeDownscaledPixel(xDash, yDash, xProportion,
                yProportion);
      }
    }

    downscaledImage.setImage(downscaledPixels);
    return downscaledImage;
  }

  /**
   * Computes the downscaled pixel for the specified position in the new image.
   *
   * @param xDash       The x-coordinate in the downscaled image.
   * @param yDash       The y-coordinate in the downscaled image.
   * @param xProportion The ratio of original width to downscaled width.
   * @param yProportion The ratio of original height to downscaled height.
   * @return An RGBImagePixel representing the interpolated pixel in the downscaled image.
   */
  private RGBImagePixel computeDownscaledPixel(int xDash, int yDash, double xProportion,
                                               double yProportion) {

    double x = xDash * xProportion;
    double y = yDash * yProportion;

    int xFloor = (int) Math.floor(x);
    int xCeil = Math.min((int) x + 1, this.getWidth() - 1);
    int yFloor = (int) Math.floor(y);
    int yCeil = Math.min((int) y + 1, this.getHeight() - 1);

    RGBImagePixel cA = this.getPixel(yFloor, xFloor);
    RGBImagePixel cB = this.getPixel(yFloor, xCeil);
    RGBImagePixel cC = this.getPixel(yCeil, xFloor);
    RGBImagePixel cD = this.getPixel(yCeil, xCeil);

    int red = computePForSingleColor(cA.getRed(), cB.getRed(), cC.getRed(), cD.getRed(), x, y,
            xFloor, xCeil, yFloor, yCeil);
    int green = computePForSingleColor(cA.getGreen(), cB.getGreen(), cC.getGreen(), cD.getGreen(),
            x, y, xFloor, xCeil, yFloor, yCeil);
    int blue = computePForSingleColor(cA.getBlue(), cB.getBlue(), cC.getBlue(), cD.getBlue(), x,
            y, xFloor, xCeil, yFloor, yCeil);

    return new RGBImagePixel(red, green, blue);
  }


  /**
   * Performs soft pixel value calculation for a single color component.
   *
   * @param cA     Color at (xFloor, yFloor)
   * @param cB     Color at (xCeil, yFloor)
   * @param cC     Color at (xFloor, yCeil)
   * @param cD     Color at (xCeil, yCeil)
   * @param x      Mapped x-coordinate in the original image
   * @param y      Mapped y-coordinate in the original image
   * @param xFloor Floor value of x
   * @param xCeil  Ceiling value of x
   * @param yFloor Floor value of y
   * @param yCeil  Ceiling value of y
   * @return Interpolated color value
   */
  private int computePForSingleColor(int cA, int cB, int cC, int cD, double x, double y, int xFloor,
                                     int xCeil, int yFloor, int yCeil) {
    double m = cB * (x - xFloor) + cA * (xCeil - x);
    double n = cD * (x - xFloor) + cC * (xCeil - x);
    double cP = n * (y - yFloor) + m * (yCeil - y);

    return (int) Math.round(cP);
  }
  

  @Override
  public ImageADT applyPartialWithMasking(ImageADT maskingImage, String effectType) {
    // Validate masking image dimensions
    AbstractImage newMask = (AbstractImage) maskingImage;
    if (newMask.getWidth() != this.getWidth() || newMask.getHeight() != this.getHeight()) {
      throw new IllegalArgumentException("Masking image dimensions must match the current "
              + "image dimensions");
    }

    ImageADT transformedImage = getTransformedImage(effectType);
    ImageADT newImage = createImage();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        RGBImagePixel maskPixel = (RGBImagePixel) newMask.getPixel(i, j);

        boolean isMasked = maskPixel.getRed() == 0 && maskPixel.getGreen() == 0
                && maskPixel.getBlue() == 0;
        RGBImagePixel pixelToSet = isMasked
                ? (RGBImagePixel) ((AbstractImage) transformedImage).getPixel(i, j)
                : this.getPixel(i, j);
        // casting needed to fit in the model.
        newImage.setPixel(pixelToSet, i, j);
      }
    }

    System.out.println("Applied " + effectType + " partially using masking.");
    return newImage;
  }

  /**
   * Returns the transformed image based on the specified effect type.
   *
   * @param effectType The type of effect to apply.
   * @return The transformed ImageADT object.
   * @throws IllegalArgumentException if the effect type is unsupported.
   */
  private ImageADT getTransformedImage(String effectType) {
    switch (effectType.toLowerCase()) {
      case "blur":
        return this.blur();
      case "sharpen":
        return this.sharpen();
      case "sepia":
        return this.convertToSepia();
      case "grayscale":
        return this.convertToGreyScale();
      case "red-component":
      case "green-component":
      case "blue-component":
        return this.createColorImage(
                Channel.valueOf(effectType.substring(
                        0, effectType.lastIndexOf('-')).toUpperCase()));
      default:
        throw new IllegalArgumentException("Unsupported effect: " + effectType);
    }
  }


}
