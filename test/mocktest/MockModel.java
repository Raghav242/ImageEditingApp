package mocktest;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import model.IModel;

/**
 * The {@code MockModel} class is a mock implementation of the {@link IModel} interface
 * used for testing purposes.
 * This class simulates the behavior of a real model in the image processing application.
 * It allows testing the controller and view interactions without relying on the actual
 * implementation of the model. The mock model can be configured to return specific data
 * or throw exceptions to simulate various scenarios.
 */
public class MockModel implements IModel {

  private final Map<String, BufferedImage> images;
  private String lastOperation;
  private String sourceImageName;
  private String targetImageName;
  private int enteredPercentage;

  public MockModel(Map<String, BufferedImage> images) {
    this.images = new HashMap<>();
  }

  @Override
  public void loadImage(BufferedImage bf, String imageName) throws IOException {
    images.put(imageName, bf);
    lastOperation = "loadImage";
    sourceImageName = imageName;
  }

  @Override
  public BufferedImage getImage(String imageName) throws IOException {
    lastOperation = "getImage";
    sourceImageName = imageName;
    return images.get(imageName);
  }

  @Override
  public void brightenImage(String value, String sourceImageName, String targetImageName) {
    lastOperation = "brightenImage";
    this.sourceImageName = sourceImageName;
    this.targetImageName = targetImageName;
    this.enteredPercentage = Integer.parseInt(value);
  }

  @Override
  public void applyFilter(String filter, String sourceImageName, String targetImageName,
      Integer splitPercentage) throws IllegalArgumentException {
    lastOperation = "applyFilter";
    this.sourceImageName = sourceImageName;
    this.targetImageName = targetImageName;
    this.enteredPercentage = splitPercentage;
  }

  @Override
  public void createHistogram(String sourceImageName, String targetImageName) throws IOException {
    lastOperation = "createHistogram";
    this.sourceImageName = sourceImageName;
    this.targetImageName = targetImageName;
    images.put(targetImageName, new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB));
  }

  @Override
  public void colorCorrection(String sourceImageName, String targetImageName, int splitPercentage) {
    lastOperation = "colorCorrection";
    this.sourceImageName = sourceImageName;
    this.targetImageName = targetImageName;
    this.enteredPercentage = splitPercentage;
  }

  @Override
  public void adjustLevels(int shadow, int mid, int highlight, String sourceImageName,
      String targetImageName, int splitPercentage) {
    lastOperation = "adjustLevels";
    this.sourceImageName = sourceImageName;
    this.targetImageName = targetImageName;
    this.enteredPercentage = splitPercentage;
  }

  @Override
  public void rgbCombine(String targetImageName, String redImageName, String greenImageName,
      String blueImageName) {
    lastOperation = "rgbCombine";
    this.targetImageName = targetImageName;
  }

  @Override
  public void rgbSplit(String sourceImageName, String redImageName, String greenImageName,
      String blueImageName) {
    lastOperation = "rgbSplit";
    this.sourceImageName = sourceImageName;
  }

  @Override
  public void compress(int percentage, String imageName, String outputPath) {
    lastOperation = "compress";
    this.sourceImageName = imageName;
    this.targetImageName = outputPath;
    this.enteredPercentage = percentage;
  }

  @Override
  public void downscale(String sourceImageName, String targetImageName, int newWidth,
      int newHeight) {
    lastOperation = "downscale";
    this.sourceImageName = sourceImageName;
    this.targetImageName = targetImageName;
  }

  @Override
  public void applyPartialTransformation(String filterOperation, String sourceImage,
      String maskImage, String destImage) {
    lastOperation = "applyPartialTransformation";
    this.sourceImageName = sourceImage;
    this.targetImageName = destImage;
  }

  @Override
  public void resetToOriginal(String resetImage, String originalImage) {
    lastOperation = "resetToOriginal";
    this.sourceImageName = originalImage;
    this.targetImageName = resetImage;
    images.put(resetImage, images.get(originalImage));
  }

  // Utility methods to verify the last operation
  public String getLastOperation() {
    return lastOperation;
  }

  public String getSourceImageName() {
    return sourceImageName;
  }

  public String getTargetImageName() {
    return targetImageName;
  }

  public int getEnteredPercentage() {
    return enteredPercentage;
  }
}
