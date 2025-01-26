package view;

import java.awt.image.BufferedImage;

import controller.Features;

/**
 * Represents the view component of the application responsible for user interaction.
 * This interface defines methods for displaying images, communicating with the user,
 * and integrating controller features.
 */
public interface IView {

  /**
   * Displays the given image in the user interface.
   *
   * @param image the image to be displayed
   * @param type  the type of image (e.g., working image or histogram image)
   */
  void displayImage(BufferedImage image, ImageType type);

  /**
   * Adds the specified features to the view to handle user interactions.
   *
   * @param features the controller features to be added
   */
  void addFeatures(Features features);

  /**
   * Displays an error message to the user.
   *
   * @param message the error message to display
   */
  void showError(String message);

  /**
   * Clears any displayed image from the view and resets the interface state.
   */
  void clearImage();

  void setVisibleView();
}
