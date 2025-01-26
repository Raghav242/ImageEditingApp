package model;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * The {@code AbstractImage} class serves as an abstract representation of an image and provides
 * common functionality for manipulating images, such as flipping, blurring, and sharpening.
 * It implements the {@link ImageADT} interface and is intended
 * to be extended by concrete image classes.
 */
public abstract class AbstractImage implements ImageADT {

  protected int width;
  protected int height;

  protected abstract int getWidth();

  protected abstract int getHeight();

  /**
   * Creates a new image instance. This method should be implemented by subclasses to create an
   * appropriate image object.
   */
  protected abstract ImageADT createImage();

  /**
   * Retrieves the pixel located at the specified (x, y) coordinates.
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the {@link Pixel} at the given coordinates.
   */
  protected abstract Pixel getPixel(int x, int y);

  /**
   * Sets the pixel at the specified (x, y) coordinates to the given {@link Pixel} object.
   *
   * @param obj the pixel to be set.
   * @param x   the x-coordinate where the pixel will be set.
   * @param y   the y-coordinate where the pixel will be set.
   * @throws IndexOutOfBoundsException if the specified coordinates are out of the image bounds.
   */
  public abstract void setPixel(Pixel obj, int x, int y);

  /**
   * Visualizes the value of the given pixel.
   */
  public int visualizeValue(Pixel pixel) {
    return pixel.getPixelData().stream().max(Integer::compareTo)
            .orElseThrow(() -> new IllegalArgumentException(
                    "Value of this pixel could not be calculated."));
  }

  /**
   * Visualizes the intensity of the given pixel.
   */
  public int visualizeIntensity(Pixel pixel) {
    int totalSum = pixel.getPixelData().stream().reduce(0, Integer::sum);
    return Math.round((float) totalSum / pixel.getPixelData().size());
  }

  /**
   * Visualizes the luma of the given pixel.
   */
  public abstract int visualizeLuma(Pixel pixel);

  /**
   * Flips the image horizontally.
   */
  public ImageADT flipHorizontal() {
    ImageADT newImage = createImage();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel pixel = getPixel(i, j);
        newImage.setPixel(pixel, i, width - j - 1);
      }
    }
    System.out.println("Image flipped Horizontally");
    return newImage;
  }

  /**
   * Flips the image vertically.
   */
  public ImageADT flipVertical() {
    ImageADT newImage = createImage();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel pixel = getPixel(i, j);
        newImage.setPixel(pixel, height - i - 1, j);
      }
    }
    System.out.println("Image flipped vertically");
    return newImage;
  }


  @Override
  public ImageADT blur(int p) {
    double[][] blurKernel = {
            {0.0625, 0.125, 0.0625},
            {0.125, 0.25, 0.125},
            {0.0625, 0.125, 0.0625}
    };
    return applyEffectWithSplit(blurKernel, p);
  }

  /**
   * Applies a blurring effect to the entire image using a Gaussian blur kernel.
   * This method applies a full blur effect across the entire image without any
   * split percentage, using a 3x3 kernel to smooth the image.
   *
   * @return a new {@link ImageADT} instance representing the blurred image.
   */
  public ImageADT blur() {
    double[][] blurKernel = {
            {0.0625, 0.125, 0.0625},
            {0.125, 0.25, 0.125},
            {0.0625, 0.125, 0.0625}
    };
    return applyEffectWithSplit(blurKernel, 100); // Full blur without split
  }

  /**
   * Applies a sharpening effect to the entire image using a sharpening kernel.
   * This method applies a full sharpen effect across the entire image without
   * any split percentage, using a 5x5 kernel to enhance edges and details.
   *
   * @return a new {@link ImageADT} instance representing the sharpened image.
   */
  public ImageADT sharpen() {
    double[][] sharpenKernel = {
            {-0.125, -0.125, -0.125, -0.125, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, 0.25, 1, 0.25, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, -0.125, -0.125, -0.125, -0.125}
    };
    return applyEffectWithSplit(sharpenKernel, 100); // Full sharpen without split
  }

  //New Code
  @Override
  public ImageADT sharpen(int p) {
    double[][] sharpenKernel = {
            {-0.125, -0.125, -0.125, -0.125, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, 0.25, 1, 0.25, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, -0.125, -0.125, -0.125, -0.125}
    };
    return applyEffectWithSplit(sharpenKernel, p);
  }

  /**
   * Applies a specified kernel effect on the image, optionally splitting by a percentage.
   *
   * @param kernel the kernel matrix for the effect.
   * @param p      the percentage of the width to apply the effect.
   * @return a new ImageADT instance with the effect applied.
   */
  private ImageADT applyEffectWithSplit(double[][] kernel, int p) {
    int splitIndex = (int) (width * (p / 100.0));
    ImageADT newImage = createImage();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (j <= splitIndex) {
          applyKernelAtPixel(i, j, kernel, newImage);
        } else {
          newImage.setPixel(getPixel(i, j), i, j);
        }
      }
    }
    //System.out.println("Applied filter Successfully for " + p + " % of the image");
    return newImage;
  }

  /**
   * Applies a given kernel at a specific pixel location in the image.
   *
   * @param i        the row index of the pixel.
   * @param j        the column index of the pixel.
   * @param kernel   the kernel matrix to apply.
   * @param newImage the new image where the processed pixel will be set.
   */
  private void applyKernelAtPixel(int i, int j, double[][] kernel, ImageADT newImage) {
    double redSum = 0;
    double greenSum = 0;
    double blueSum = 0;
    int kernelRadius = kernel.length / 2;

    for (int ki = -kernelRadius; ki <= kernelRadius; ki++) {
      for (int kj = -kernelRadius; kj <= kernelRadius; kj++) {
        int ni = i + ki;
        int nj = j + kj;

        if (ni >= 0 && ni < height && nj >= 0 && nj < width) {
          Pixel neighborPixel = getPixel(ni, nj);
          double kernelValue = kernel[ki + kernelRadius][kj + kernelRadius];
          redSum += neighborPixel.getPixelData().get(0) * kernelValue;
          greenSum += neighborPixel.getPixelData().get(1) * kernelValue;
          blueSum += neighborPixel.getPixelData().get(2) * kernelValue;
        }
      }
    }

    int newRed = (int) Math.round(Math.min(Math.max(redSum, 0), 255));
    int newGreen = (int) Math.round(Math.min(Math.max(greenSum, 0), 255));
    int newBlue = (int) Math.round(Math.min(Math.max(blueSum, 0), 255));

    newImage.setPixel(new RGBImagePixel(newRed, newGreen, newBlue), i, j);
  }


  /**
   * Adjusts the brightness of the image by a specified increment.
   */
  public abstract ImageADT brightness(int increment);

  // HISTOGRAM --->  RGB
  private RGBImage convertBufferedImageToRGBImage(BufferedImage bufferedImage) {
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();

    // Create an RGBImage with the same dimensions
    RGBImage rgbImage = new RGBImage();
    rgbImage.height = height;
    rgbImage.width = width;
    rgbImage.setImage(new RGBImagePixel[height][width]);

    // Loop through each pixel in the BufferedImage
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        // Get the RGB value of the pixel
        int rgb = bufferedImage.getRGB(x, y);

        // Extract the red, green, and blue components
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        // Create a new RGBImagePixel with the RGB values
        RGBImagePixel pixel = new RGBImagePixel(red, green, blue);

        // Set this pixel in the RGBImage's pixel array
        rgbImage.setPixel(pixel, y, x); // Use y as the row and x as the column
      }
    }

    return rgbImage;
  }


  // Histogram

  /**
   * Generates a histogram for the current image.
   *
   * @return a BufferedImage representing the histogram.
   */
  // In RGBImage (or AbstractImage)
  public ImageADT generateHistogram() {
    int histogramWidth = 256;  // Width of the histogram canvas
    int histogramHeight = 256; // Height of the histogram canvas

    int imageWidth = getWidth();
    int imageHeight = getHeight();
    System.out.println("Image Dimensions: " + imageWidth + "x" + imageHeight);

    // Calculate histograms for red, green, and blue channels
    int[] redHistogram = calculateChannelHistogram("red", imageWidth, imageHeight);
    int[] greenHistogram = calculateChannelHistogram("green", imageWidth, imageHeight);
    int[] blueHistogram = calculateChannelHistogram("blue", imageWidth, imageHeight);

    // Normalize histograms
    normalizeHistogram(redHistogram, histogramHeight);
    normalizeHistogram(greenHistogram, histogramHeight);
    normalizeHistogram(blueHistogram, histogramHeight);

    // Generate histogram image
    BufferedImage histogramImage = createHistogramImage(histogramWidth, histogramHeight,
            redHistogram, greenHistogram, blueHistogram);

    // Convert BufferedImage to RGBImage and return
    return convertBufferedImageToRGBImage(histogramImage);
  }

  // Helper method to calculate a specific channel histogram
  private int[] calculateChannelHistogram(String channel, int imageWidth, int imageHeight) {
    int[] histogram = new int[256];
    for (int y = 0; y < imageWidth; y++) {
      for (int x = 0; x < imageHeight; x++) {
        RGBImagePixel pixel = (RGBImagePixel) getPixel(x, y);
        int value = 0;
        switch (channel) {
          case "red":
            value = pixel.getRed();
            break;
          case "green":
            value = pixel.getGreen();
            break;
          case "blue":
            value = pixel.getBlue();
            break;
          default:
            System.out.println("Error in histogram");
            break;
        }
        histogram[value]++;
      }
    }
    return histogram;
  }

  // Helper method to normalize histogram values
  private void normalizeHistogram(int[] histogram, int maxHeight) {
    int maxFrequency = getMaxValue(histogram);
    for (int i = 0; i < histogram.length; i++) {
      histogram[i] = (histogram[i] * maxHeight) / (maxFrequency == 0 ? 1 : maxFrequency);
    }
  }

  // Helper method to create histogram image
  private BufferedImage createHistogramImage(int width, int height, int[] redHistogram,
                                             int[] greenHistogram, int[] blueHistogram) {
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = image.createGraphics();

    // Fill background and draw grid
    drawBackgroundAndGrid(g, width, height);

    // Draw histograms for each channel
    drawChannelHistogram(g, Color.RED, redHistogram, height);
    drawChannelHistogram(g, Color.GREEN, greenHistogram, height);
    drawChannelHistogram(g, Color.BLUE, blueHistogram, height);

    g.dispose();
    return image;
  }

  // Helper method to draw background and grid
  private void drawBackgroundAndGrid(Graphics2D g, int width, int height) {
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, width, height);

    g.setColor(Color.LIGHT_GRAY);
    for (int i = 0; i <= width; i += 10) {
      g.drawLine(i, 0, i, height);
      g.drawLine(0, i, width, i);
    }
  }

  // Helper method to draw a specific channel histogram
  private void drawChannelHistogram(Graphics2D g, Color color, int[] histogram, int height) {
    g.setColor(color);
    for (int i = 0; i < 255; i++) {
      g.drawLine(i, height - histogram[i], i + 1, height - histogram[i + 1]);
    }
  }


  // Utility method to find the maximum value in an array
  private static int getMaxValue(int[] histogramArray) {
    int max = 0;
    for (int value : histogramArray) {
      if (value > max) {
        max = value;
      }
    }
    return max;
  }


  public abstract ImageADT converttoLuma(int p);

  public abstract ImageADT colorCorrection(int p);

  public abstract ImageADT adjustLevels(int p);
}
