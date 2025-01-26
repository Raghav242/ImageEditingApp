import controller.Controller;
import controller.ImageController;
import controller.ScriptRunner;
import model.IModel;
import model.ImageModel;
import view.IView;
import view.ImageProcessingGUI;

import java.util.Scanner;

/**
 * The entry point of the Image Processing application.
 * This application supports three modes of operation:
 * <ul>
 *   <li>Script Mode: Processes commands from a script file specified as command-line argument.</li>
 *   <li>Interactive Text Mode: Allows users to input commands interactively via the console.</li>
 *   <li>GUI Mode: Launches a graphical user interface for image processing.</li>
 * </ul>
 * Command-line usage:
 * <pre>
 * java -jar ImageEditor.jar -file path-of-script-file
 * java -jar ImageEditor.jar -text
 * java -jar ImageEditor.jar
 * </pre>
 * Invalid arguments will display a help message and terminate the program.
 */
public class Main {

  /**
   * The main method of the application. It processes command-line arguments to determine
   * whether to run in script mode, interactive mode, or GUI mode.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    IModel model = new ImageModel();
    IView view = new ImageProcessingGUI();
    Controller controller = new Controller(model, view); // GUI
    ImageController textController = new ImageController(model); // Text

    // Handle different command-line inputs
    if (args.length == 2 && args[0].equals("-file")) {
      // Script file mode
      String scriptFileName = args[1];
      ScriptRunner runner = new ScriptRunner(textController);
      runner.processScriptFile(scriptFileName);

    } else if (args.length == 1 && args[0].equals("-text")) {
      // Interactive text mode
      Scanner scanner = new Scanner(System.in);
      System.out.println("Enter image processing commands (type 'quit' to quit):");
      String command;
      while (!(command = scanner.nextLine()).equalsIgnoreCase("quit")) {
        try {
          textController.executeCommand(command);
        } catch (Exception e) {
          System.out.println("Error: " + e.getMessage());
        }
      }
      scanner.close();

    } else if (args.length == 0) {
      // GUI mode
      controller.setView(view);


    } else {
      System.out.println("Invalid command-line arguments. Use one of the following options:");
      System.out.println("java -jar out/artifacts/ImageEditor_jar/ImageEditor.jar "
              + "-file path-of-script-file");
      System.out.println("java -jar out/artifacts/ImageEditor_jar/ImageEditor.jar -text");
      System.out.println("java -jar out/artifacts/ImageEditor_jar/ImageEditor.jar");
      System.exit(1);
    }
  }
}
