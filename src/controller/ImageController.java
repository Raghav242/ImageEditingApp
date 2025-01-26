package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.IModel;
import model.ImageUtil;


/**
 * The {@code ImageController} class handles image operations by coordinating with the model.
 * It uses BufferedReader and FileWriter to read and write from the file system.
 */
public class ImageController implements ImageProcessingInterface {

  private final IModel model;

  /**
   * Constructs an {@code ImageController} object, initializing it with a given model.
   *
   * @param model the image model that handles the actual image processing and storage.
   */
  public ImageController(IModel model) {
    this.model = model;
  }

  /**
   * Executes the given command by identifying the appropriate action and applying it
   * to the specified image or set of images.
   *
   * @param command a string containing the command to be executed,
   *                including arguments such as image names.
   * @throws UnsupportedOperationException if an invalid or unsupported command is provided.
   */
  public void executeCommand(String command) throws IOException {
    String[] processedCommand = command.split(" ");
    String actionWord = processedCommand[0].toLowerCase();

    switch (actionWord) {
      case "load":
        loadImage(processedCommand[1], processedCommand[2]);
        break;
      case "save":
        saveImage(processedCommand[1], processedCommand[2]);
        break;
      case "brighten":
        model.brightenImage(processedCommand[1], processedCommand[2], processedCommand[3]);
        break;
      case "sepia":
      case "grayscale":
      case "luma-component":
      case "blur":
      case "sharpen":
      case "value-component":
      case "intensity-component":
      case "flip":
      case "horizontal-flip":
      case "red-component":
      case "green-component":
      case "blue-component":
        // Check if a split percentage is provided (4th parameter)
        if (processedCommand.length == 5) {
          int splitPercentage = Integer.parseInt(processedCommand[4]);
          model.applyFilter(actionWord, processedCommand[1], processedCommand[2], splitPercentage);
        }
        // Handle mask-based operation
        else if (processedCommand.length == 4) {
          model.applyPartialTransformation(actionWord, processedCommand[1],
                  processedCommand[2], processedCommand[3]);
        } else {
          model.applyFilter(actionWord, processedCommand[1], processedCommand[2], 100);
        }
        break;
      case "rgb-split":
        model.rgbSplit(processedCommand[1], processedCommand[2],
                processedCommand[3], processedCommand[4]);
        break;
      case "rgb-combine":
        model.rgbCombine(processedCommand[1], processedCommand[2],
                processedCommand[3], processedCommand[4]);
        break;
      case "histogram":
        model.createHistogram(processedCommand[1], processedCommand[2]);
        break;
      case "color-correct":
        if (processedCommand.length == 5) {
          int splitPercentage = Integer.parseInt(processedCommand[4]);
          model.colorCorrection(processedCommand[1], processedCommand[2], splitPercentage);
        } else {
          model.colorCorrection(processedCommand[1], processedCommand[2], 100);
        }
        break;
      case "levels-adjust":
        // Check if a split percentage is provided (6th parameter)
        if (processedCommand.length == 8) {
          int splitPercentage = Integer.parseInt(processedCommand[7]);
          model.adjustLevels(
                  Integer.parseInt(processedCommand[1]),
                  Integer.parseInt(processedCommand[2]),
                  Integer.parseInt(processedCommand[3]),
                  processedCommand[4], processedCommand[5], splitPercentage
          );
        } else {
          model.adjustLevels(
                  Integer.parseInt(processedCommand[1]),
                  Integer.parseInt(processedCommand[2]),
                  Integer.parseInt(processedCommand[3]),
                  processedCommand[4], processedCommand[5], 100
          );
        }
        break;
      case "compress":
        model.compress(Integer.parseInt(processedCommand[1]),
                processedCommand[2], processedCommand[3]);
        break;
      default:
        System.out.println("Invalid Command :" + actionWord);
    }
  }

  /**
   * Loads an image from the given file path and associates it with the provided image name.
   * Uses BufferedReader for file access.
   *
   * @param filePath  the file path of the image to be loaded.
   * @param imageName the name to associate with the loaded image.
   * @throws IOException if the file cannot be read.
   */
  public void loadImage(String filePath, String imageName) throws IOException {
    BufferedImage bufferedImage;
    try {
      if (filePath.contains(".ppm")) {
        bufferedImage = ImageUtil.readPPM(filePath);
      } else {
        bufferedImage = ImageIO.read(new File(filePath));
      }
      model.loadImage(bufferedImage, imageName); // Delegate to the model for loading the image data
      System.out.println("Loaded image: " + imageName);
    } catch (IOException e) {
      throw new IOException("Error loading image from file path: " + filePath, e);
    }
  }

  /**
   * Saves the specified image to the given output path.
   * Uses FileWriter for file access.
   *
   * @param outputPath the file path where the image will be saved.
   * @param imageName  the name of the image to be saved.
   * @throws IOException if the file cannot be written to.
   */
  public void saveImage(String outputPath, String imageName) throws IOException {
    BufferedImage saved = model.getImage(imageName);
    if (saved == null) {
      return;
    }
    String filetype = outputPath.substring(
            outputPath.lastIndexOf('.') + 1).toLowerCase();
    ImageIO.write(saved, filetype, new File(outputPath));
    System.out.println("Saved image as: " + outputPath);
  }


}
