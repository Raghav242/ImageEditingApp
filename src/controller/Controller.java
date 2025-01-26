package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.IModel;
import model.ImageUtil;
import view.IView;
import view.ImageType;

/**
 * The {@code Controller} class is responsible for managing the interaction between
 * the model and view in the image processing application. It implements the {@link Features}
 * interface, providing the necessary functionality for image processing operations.
 * The controller mediates user actions from the view and updates the model accordingly.
 * It also ensures that the view is updated with the results of these actions.
 */
public class Controller implements Features {

  private IModel model;
  private IView view;
  private String imageName = "editedImage";
  private String originalImageName = "originalImage";
  private String histogramImage = "histogram";
  private String splitImage = "splitImage";

  /**
   * Constructs a new {@code Controller} with the specified model and view.
   * This constructor establishes the relationship between the model and the view.
   *
   * @param m The model component that handles the image data and processing.
   * @param v The view component that interacts with the user and displays the UI.
   */
  public Controller(IModel m, IView v) {
    this.model = m;
    this.view = v;
  }

  /**
   * Sets the view for the controller and provides the view with the necessary
   * features (callbacks) to interact with the model.
   *
   * @param v The view to be set for the controller.
   */
  public void setView(IView v) {
    this.view = v;
    // Provide view with all the callbacks
    view.addFeatures(this);
    view.setVisibleView();
  }

  @Override
  public void loadImage(String inputPath, String imageNameAsPerFile) throws IOException {

    try {
      BufferedImage bufferedImage;
      if (inputPath.contains(".ppm")) {
        bufferedImage = ImageUtil.readPPM(inputPath);
      } else {
        bufferedImage = ImageIO.read(new File(inputPath));
      }
      // Save the original image in the model
      model.loadImage(bufferedImage, originalImageName);
      // Also save a copy as the editable image
      model.loadImage(bufferedImage, imageName);
      //saving another image for split operations
      model.loadImage(bufferedImage, splitImage);

      BufferedImage imageInsideModelAfterLoading = model.getImage(imageName);
      view.displayImage(imageInsideModelAfterLoading, ImageType.WORKING_IMAGE);
      System.out.println("Loaded image: " + imageNameAsPerFile);

      // Generate and display the histogram
      model.createHistogram(imageName, histogramImage);
      view.displayImage(model.getImage(histogramImage), ImageType.HISTOGRAM_IMAGE);
    } catch (IOException e) {
      throw new IOException("Error loading image from file path: " + inputPath, e);
    }
  }

  @Override
  public void resetToOriginal() {
    try {
      model.resetToOriginal(imageName, originalImageName);
      heplerForDisplayToView(imageName);
      System.out.println("Image reset to original.");
    } catch (Exception e) {
      System.err.println("Failed to reset to original image: " + e.getMessage());
    }
  }

  @Override
  public void saveImage(String outputPath) throws IOException {
    BufferedImage imageInsideModelAfterLoading = model.getImage(imageName);
    if (imageInsideModelAfterLoading == null) {
      sendErrorToView("This image is not loaded");
      return;
    }
    String filetype = outputPath.substring(outputPath.lastIndexOf('.') + 1).toLowerCase();
    if (filetype.contains("ppm")) {
      ImageUtil.savePPM(outputPath.substring(0, outputPath.lastIndexOf(".")) + ".ppm",
              imageInsideModelAfterLoading);
    } else {
      ImageIO.write(imageInsideModelAfterLoading, filetype, new File(outputPath));
    }
    System.out.println("Saved image as: " + outputPath);
  }

  @Override
  public void brighten(String increment) throws IOException {
    model.brightenImage(increment, imageName, imageName);
    heplerForDisplayToView(imageName);
  }

  @Override
  public void blur() throws IOException {
    model.applyFilter("blur", imageName, imageName, 100);
    heplerForDisplayToView(imageName);
  }

  @Override
  public void blur(int percentage) throws IOException {
    model.applyFilter("blur", imageName, splitImage, percentage);
    heplerForDisplayToView(splitImage);
  }

  @Override
  public void sharpen() throws IOException {
    model.applyFilter("sharpen", imageName, imageName, 100);
    heplerForDisplayToView(imageName);
  }

  @Override
  public void sharpen(int percentage) throws IOException {
    model.applyFilter("sharpen", imageName, splitImage, percentage);
    heplerForDisplayToView(splitImage);
  }

  @Override
  public void sepia() throws IOException {
    model.applyFilter("sepia", imageName, imageName, 100);
    heplerForDisplayToView(imageName);
  }

  @Override
  public void sepia(int percentage) throws IOException {
    model.applyFilter("sepia", imageName, splitImage, percentage);
    heplerForDisplayToView(splitImage);
  }

  @Override
  public void luma() throws IOException {
    model.applyFilter("luma-component", imageName, imageName, 100);
    heplerForDisplayToView(imageName);
  }

  @Override
  public void luma(int percentage) throws IOException {
    model.applyFilter("luma-component", imageName, splitImage, percentage);
    heplerForDisplayToView(splitImage);
  }

  @Override
  public void value() throws IOException {
    model.applyFilter("value-component", imageName, imageName, 100);
    heplerForDisplayToView(imageName);
  }

  @Override
  public void intensity() throws IOException {
    model.applyFilter("intensity-component", imageName, imageName, 100);
    heplerForDisplayToView(imageName);
  }

  @Override
  public void horizontalFlip() throws IOException {
    model.applyFilter("horizontal-flip", imageName, imageName, 100);
    heplerForDisplayToView(imageName);
  }

  @Override
  public void verticalFlip() throws IOException {
    model.applyFilter("flip", imageName, imageName, 100);
    heplerForDisplayToView(imageName);
  }

  @Override
  public void redComponent() throws IOException {
    model.applyFilter("red-component", imageName, imageName, 100);
    heplerForDisplayToView(imageName);
  }

  @Override
  public void greenComponent() throws IOException {
    model.applyFilter("green-component", imageName, imageName, 100);
    heplerForDisplayToView(imageName);
  }

  @Override
  public void blueComponent() throws IOException {
    model.applyFilter("blue-component", imageName, imageName, 100);
    heplerForDisplayToView(imageName);
  }

  @Override
  public void colorCorrect() throws IOException {
    model.colorCorrection(imageName, imageName, 100);
    heplerForDisplayToView(imageName);
  }

  @Override
  public void colorCorrect(int percentage) throws IOException {
    //model.applyFilter("color-correct", imageName, imageName, percentage);
    model.colorCorrection(imageName, splitImage, percentage);
    heplerForDisplayToView(splitImage);
  }

  @Override
  public void adjustLevels(int black, int mid, int white) throws IOException {
    model.adjustLevels(black, mid, white, imageName, imageName, 100);
    heplerForDisplayToView(imageName);
  }

  @Override
  public void adjustLevels(int black, int mid, int white, int percentage) throws IOException {
    model.adjustLevels(black, mid, white, imageName, splitImage, percentage);
    heplerForDisplayToView(splitImage);
  }

  @Override
  public void compress(int percentage) throws IOException {
    model.compress(percentage, imageName, imageName);
    heplerForDisplayToView(imageName);
  }

  /**
   * Downscales the image to the specified width and height.
   * This method checks if the new dimensions are valid (i.e., smaller than or equal to
   * the original image size). If the dimensions are invalid, it shows an error message to the view.
   * Otherwise, it calls the model's {@code downscale} method to perform the image resizing and then
   * updates the view to display the result.
   *
   * @param newWidth  The new width of the image.
   * @param newHeight The new height of the image.
   * @throws IOException              If an error occurs during image processing or saving.
   * @throws IllegalArgumentException If the new dimensions are invalid.
   */
  public void downscale(int newWidth, int newHeight) throws IOException, IllegalArgumentException {
    int imageHeight = model.getImage(imageName).getHeight();
    int imageWidth = model.getImage(imageName).getWidth();
    if (newWidth > imageWidth || newWidth < 0 || newHeight > imageHeight || newHeight < 0) {
      view.showError("Image height or width cannot be greater than the existing image");
    }
    model.downscale(imageName, imageName, newWidth, newHeight);
    heplerForDisplayToView(imageName);
  }

  private void heplerForDisplayToView(String imageName) throws IOException {
    view.displayImage(model.getImage(imageName), ImageType.WORKING_IMAGE);
    model.createHistogram(imageName, histogramImage);
    view.displayImage(model.getImage(histogramImage), ImageType.HISTOGRAM_IMAGE);
  }

  private void sendErrorToView(String error) {
    view.showError(error);
  }


}
