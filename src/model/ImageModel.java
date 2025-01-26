package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The ImageModel class represents a model for handling images.
 * It provides methods to load, save, apply transformations, filters, adjustments,
 * and manipulate images stored in the model's internal map.
 * The images are stored in a map with their associated names, and operations can be
 * performed on these images based on their names.
 */
public class ImageModel implements IModel {

  private final Map<String, ImageADT> images;

  /**
   * Constructs an empty ImageModel object, initializing an empty map to store images.
   */
  public ImageModel() {
    this.images = new HashMap<>();
  }

  /**
   * Loads an image from a BufferedImage and stores it in the model under the specified name.
   *
   * @param bf        the BufferedImage to load.
   * @param imageName the name to associate with the loaded image.
   * @throws IOException if an error occurs while reading the image.
   */
  public void loadImage(BufferedImage bf, String imageName) throws IOException {
    ImageADT image = new RGBImage();
    image.loadImage(bf); // Delegate reading data to the image's loadImage method
    images.put(imageName, image); // Store in model’s map with the associated name
  }

  /**
   * Saves an image to an Appendable (such as a Writer) based on the given image name.
   *
   * @param imageName the name of the image to save.
   * @return the BufferedImage object of the saved image.
   * @throws IOException if an error occurs while saving the image.
   */
  public BufferedImage getImage(String imageName) throws IOException {
    ImageADT image = images.get(imageName);
    if (image == null) {
      System.out.println("Error: Image with name '" + imageName + "' does not exist.");
      return null;  // Return null if the image doesn't exist
    }
    return image.getImage(imageName);
  }


  /**
   * Adjusts the brightness of a stored image by the given value and stores the result
   * under the target image name.
   *
   * @param value           the amount to adjust the brightness.
   * @param sourceImageName the name of the source image.
   * @param targetImageName the name under which the brightened image will be stored.
   */
  public void brightenImage(String value, String sourceImageName, String targetImageName) {
    ImageADT sourceImage = images.get(sourceImageName);
    if (sourceImage == null) {
      // Throw a custom exception or print an error message and return
      System.out.println("Error: Source image '" + sourceImageName + "' not found.");
      return;  // Early return if the image is not found
    }

    int brightnessValue = Integer.parseInt(value); // Parse brightness adjustment value
    ImageADT brightenedImage = sourceImage.brightness(brightnessValue); // Adjust brightness
    images.put(targetImageName, brightenedImage); // Store the brightened image
  }

  /**
   * Applies a filter (e.g., sepia, grayscale) to an image and stores the result.
   *
   * @param filter          the filter command (e.g., "sepia", "grayscale").
   * @param sourceImageName the name of the source image.
   * @param targetImageName the name under which the filtered image will be stored.
   * @param splitPercentage the percentage for split filtering (optional).
   * @throws IllegalArgumentException if the filter command is invalid.
   */
  public void applyFilter(String filter, String sourceImageName, String targetImageName,
                          Integer splitPercentage) throws IllegalArgumentException {
    if (splitPercentage < 0 || splitPercentage > 100) {
      throw new IllegalArgumentException("Percentage must be within 0 - 100");
    }
    ImageADT sourceImage = images.get(sourceImageName);
    if (sourceImage == null) {
      //throw new IllegalArgumentException("Source image '" + sourceImageName + "' not found.");
      System.out.println("Error: Source image '" + sourceImageName + "' not found.");
      return;
    }

    ImageADT filteredImage;
    switch (filter) {
      case "sepia":
        filteredImage = (splitPercentage != null)
                ? sourceImage.convertToSepia(splitPercentage)
                : sourceImage.convertToSepia();
        break;
      case "grayscale":
        filteredImage = (splitPercentage != null)
                ? sourceImage.convertToGreyScale(splitPercentage)
                : sourceImage.convertToGreyScale();
        break;
      case "luma-component":
        filteredImage = (splitPercentage != null)
                ? sourceImage.convertToGreyScale(splitPercentage)
                : sourceImage.convertToGreyScale();
        break;
      case "blur":
        filteredImage = (splitPercentage != null)
                ? sourceImage.blur(splitPercentage)
                : sourceImage.blur();
        break;
      case "sharpen":
        filteredImage = (splitPercentage != null)
                ? sourceImage.sharpen(splitPercentage)
                : sourceImage.sharpen();
        break;
      case "flip":
        filteredImage = sourceImage.flipVertical();
        break;
      case "value-component":
        filteredImage = sourceImage.convertToValue();
        break;
      case "intensity-component":
        filteredImage = sourceImage.convertToIntensity();
        break;
      case "horizontal-flip":
        filteredImage = sourceImage.flipHorizontal();
        break;
      case "red-component":
        filteredImage = sourceImage.createRed();
        break;
      case "green-component":
        filteredImage = sourceImage.createGreen();
        break;
      case "blue-component":
        filteredImage = sourceImage.createBlue();
        break;
      default:
        throw new UnsupportedOperationException("Invalid filter command: " + filter);
    }

    images.put(targetImageName, filteredImage); // Store the filtered image
  }

  /**
   * Generates a histogram for the specified image.
   *
   * @param sourceImageName the name of the source image.
   * @param targetImageName the name under which the histogram will be stored.
   * @throws IOException if an error occurs while generating the histogram.
   */
  public void createHistogram(String sourceImageName, String targetImageName) throws IOException {
    if (!images.containsKey(sourceImageName)) {
      throw new IllegalArgumentException("Source image '" + sourceImageName + "' not found.");
    }

    if (sourceImageName == null) {
      //throw new IllegalArgumentException("Source image '" + sourceImageName + "' not found.");
      System.out.println("Error: Source image '" + sourceImageName + "' not found.");
      return;
    }
    ImageADT image = images.get(sourceImageName);
    ImageADT hist = image.generateHistogram();
    images.put(targetImageName, hist);

    System.out.println("Histogram generated for image: " + sourceImageName);
  }

  /**
   * Applies color correction to the specified image and stores it under a new name.
   *
   * @param sourceImageName the name of the source image.
   * @param targetImageName the name under which the corrected image will be stored.
   * @param splitPercentage the split percentage for color correction.
   */
  public void colorCorrection(String sourceImageName, String targetImageName, int splitPercentage) {

    if (splitPercentage < 0 || splitPercentage > 100) {
      throw new IllegalArgumentException("Percentage must be within 0 - 100");
    }

    if (!images.containsKey(sourceImageName)) {
      System.out.println("Error: Source image '" + sourceImageName + "' not found.");
      return;
    }

    ImageADT inputImage = images.get(sourceImageName);
    ImageADT colorImage;

    if (splitPercentage == 100) {
      colorImage = inputImage.colorCorrection();
    } else {
      colorImage = inputImage.colorCorrection(splitPercentage);
    }

    images.put(targetImageName, colorImage);
    System.out.println("Color correction applied to image: "
            + targetImageName + " with split percentage: " + splitPercentage + "%");
  }

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
  public void adjustLevels(int shadow, int mid, int highlight,
                           String sourceImageName, String targetImageName, int splitPercentage) {
    if (!images.containsKey(sourceImageName)) {
      //throw new IllegalArgumentException("Source image '" + sourceImageName + "' not found.");
      System.out.println("Error: Source image '" + sourceImageName + "' not found.");
      return;
    }
    if (splitPercentage < 0 || splitPercentage > 100) {
      throw new IllegalArgumentException("Percentage must be within 0 - 100");
    }

    RGBImage inputImage = (RGBImage) images.get(sourceImageName);
    ImageADT adjusted;

    inputImage.setLevels(shadow, mid, highlight);

    if (splitPercentage == 100) {
      adjusted = inputImage.adjustLevels();
    } else {
      adjusted = inputImage.adjustLevels(splitPercentage);
    }

    images.put(targetImageName, adjusted);
    System.out.println("Levels adjustment applied to image: "
            + targetImageName + " with split percentage: " + splitPercentage + "%");
  }

  /**
   * Combines the RGB components into a single image and stores the result.
   *
   * @param targetImageName the name under which the combined image will be stored.
   * @param redImageName    the name of the red component image.
   * @param greenImageName  the name of the green component image.
   * @param blueImageName   the name of the blue component image.
   * @throws IllegalArgumentException if any of the component images are not found.
   */
  public void rgbCombine(String targetImageName, String redImageName, String greenImageName,
                         String blueImageName) throws IllegalArgumentException {

    RGBImage redImage = (RGBImage) images.get(redImageName);
    RGBImage greenImage = (RGBImage) images.get(greenImageName);
    RGBImage blueImage = (RGBImage) images.get(blueImageName);

    if (redImage == null || greenImage == null || blueImage == null) {
      //throw new IllegalArgumentException("One or more component images not found.");
      System.out.println("Error: Source image not found.");
      return;
    }

    images.put(targetImageName, RGBImage.rgbCombine(redImage, greenImage, blueImage));
    System.out.println("RGB combine completed.");
  }

  /**
   * Splits an image into its RGB components and stores the red, green, and blue images.
   *
   * @param sourceImageName the name of the source image.
   * @param redImageName    the name under which the red component image will be stored.
   * @param greenImageName  the name under which the green component image will be stored.
   * @param blueImageName   the name under which the blue component image will be stored.
   * @throws IllegalArgumentException if the source image is not found.
   */
  public void rgbSplit(String sourceImageName, String redImageName, String greenImageName,
                       String blueImageName) {
    if (!images.containsKey(sourceImageName)) {
      //throw new IllegalArgumentException("Source image '" + sourceImageName + "' not found.");
      System.out.println("Error: Source image '" + sourceImageName + "' not found.");
      return;
    }
    ImageADT inputImage = images.get(sourceImageName);
    images.put(redImageName, inputImage.createRed());
    images.put(greenImageName, inputImage.createGreen());
    images.put(blueImageName, inputImage.createBlue());
    System.out.println("RGB split completed.");
  }

  /**
   * Compresses an image by a given percentage and stores the result under a specified path.
   *
   * @param percentage the percentage to compress the image by.
   * @param imageName  the name of the image to compress.
   * @param outputPath the path where the compressed image will be saved.
   * @throws IllegalArgumentException if the percentage is out of bounds or if image is not found.
   */
  public void compress(int percentage, String imageName,
                       String outputPath) throws IllegalArgumentException {

    if (illegalArgumentChecks(percentage, imageName)) {
      return;
    }

    ImageADT inputImage = images.get(imageName);
    ImageADT compressed = inputImage.compressImage(percentage);

    images.put(outputPath, compressed);
    System.out.println("Compression applied to image: " + outputPath);
  }

  private boolean illegalArgumentChecks(int percentage, String imageName) {
    if (percentage < 0 || percentage > 100) {
      throw new IllegalArgumentException("Percentage must be within 0 - 100");
    }
    if (!images.containsKey(imageName)) {
      //throw new IllegalArgumentException("Source image '" + imageName + "' not found.");
      System.out.println("Error: Source image '" + imageName + "' not found.");
      return true;
    }
    return false;
  }

  /**
   * Downscales the specified image to the given width and height,
   * and stores the result under a new name.
   * This method retrieves the source image by its name, performs the downscaling operation,
   * and saves the
   * resulting downscaled image under a new name. If the downscaling operation fails,
   * it throws a runtime exception.
   *
   * @param sourceImageName The name of the image to be downscaled.
   * @param targetImageName The name under which the downscaled image will be stored.
   * @param newWidth        The new width for the downscaled image.
   * @param newHeight       The new height for the downscaled image.
   * @throws RuntimeException If an error occurs during the downscaling process.
   */
  public void downscale(String sourceImageName, String targetImageName, int newWidth,
                        int newHeight) {
    try {
      ImageADT toBeDownscaled = images.get(sourceImageName);
      ImageADT downscaledImage = toBeDownscaled.downscale(newWidth, newHeight);
      images.put(targetImageName, downscaledImage);
      System.out.println("Image has been downscaled");
    } catch (Exception e) {
      throw new RuntimeException("Downscaling failed", e);
    }
  }

  @Override
  public void applyPartialTransformation(String filterOperation, String sourceImage,
                                         String maskImage, String destImage) {
    ImageADT inputImage = images.get(sourceImage);
    ImageADT maskImageGiven = images.get(maskImage);
    ImageADT transformed = inputImage.applyPartialWithMasking(maskImageGiven, filterOperation);
    images.put(destImage, transformed);
  }

  public void resetToOriginal(String imageName, String originalImage) {
    images.put(imageName, images.get(originalImage));
  }

}
