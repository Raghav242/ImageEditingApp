package controller;

import java.io.IOException;

/**
 * This interface defines the methods required for image processing operations
 * such as executing commands, loading images, and saving images.
 */
public interface ImageProcessingInterface {

  /**
   * Executes a specified command to process the image.
   *
   * @param command the command to execute, represented as a String
   * @throws IOException if an I/O error occurs during command execution
   */
  public void executeCommand(String command) throws IOException;

  /**
   * Loads an image from the specified file path and associates it with a given name.
   *
   * @param filePath  the path of the file to load
   * @param imageName the name to associate with the loaded image
   * @throws IOException if an I/O error occurs during loading
   */
  public void loadImage(String filePath, String imageName) throws IOException;

  /**
   * Saves the specified image to the given output path.
   *
   * @param outputPath the file path where the image should be saved
   * @param imageName  the name of the image to save
   * @throws IOException if an I/O error occurs during saving
   */
  public void saveImage(String outputPath, String imageName) throws IOException;


}
