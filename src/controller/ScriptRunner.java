package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The {@code ScriptRunner} class is responsible for processing a script file containing a series
 * of commands for image manipulation. Each command in the script is
 * executed by the {@link ImageController}.
 */
public class ScriptRunner {

  private final ImageController controller;

  /**
   * Constructs a {@code ScriptRunner} object with the specified {@link ImageController}.
   *
   * @param controller the {@code ImageController} instance that will be used to execute commands.
   */
  public ScriptRunner(ImageController controller) {
    this.controller = controller;
  }

  /**
   * Processes the script file at the given file path, reading each line, and executing
   * the corresponding command using the {@link ImageController}.
   *
   * <p>Empty lines and lines starting with "#" (comments) are ignored.
   *
   * @param filePath the file path of the script file to be processed.
   */
  public void processScriptFile(String filePath) {

    // using BufferedReader class to read the script file
    try {
      BufferedReader reader = new BufferedReader(new FileReader(filePath));
      String line = "";
      while ((line = reader.readLine()) != null) {
        line = line.trim();

        // if the line is empty, or if it consists of a comment, skip the line
        if (line.isEmpty() || line.startsWith("#")) {
          continue;
        }

        // Execute the command using the ImageController
        controller.executeCommand(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
