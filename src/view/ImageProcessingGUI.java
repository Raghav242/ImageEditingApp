package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Cursor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


import controller.Features;

/**
 * This class represents the Graphical User Interface (GUI) for the image processing application.
 * It extends {@code JFrame} and implements the {@code IView} interface to provide user interaction
 * and visualization of image processing features.
 */
public class ImageProcessingGUI extends JFrame implements IView {

  private JLabel imageLabel;
  private JLabel histogramLabel;

  private JButton loadButton;
  private JButton saveButton;
  private JButton adjustBrightnessButton;
  private JButton compressButton;
  private JButton clearImage;
  private JButton adjustLevelsButton;
  private JButton resetButton;
  private JButton applyFilterButton;
  private JButton applyFlip;
  private JButton previewOperationsButton;
  private JButton applyPreviewButton;
  private JButton downscaleButton;

  private String selectedFilter;
  private String selectedFlip;
  private String selectedPreview;

  private JComboBox<String> filtersDropdown;
  private JComboBox<String> flipDropdown;
  private JComboBox<String> splitPreviewDropDown;

  private JTextField brightnessInput;
  private JTextField compressionInput;
  private JTextField blackInput;
  private JTextField midInput;
  private JTextField highlightsInput;
  private JTextField previewPercentageInput;
  private JTextField downscaleHeightInput;
  private JTextField downscaleWidthInput;

  /**
   * Constructs a new instance of the image processing GUI. This initializes the window, sets up
   * the layout, and adds components for user interaction.
   */
  public ImageProcessingGUI() {
    setTitle("Image Editing Application");
    setSize(1600, 850);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Main layout
    setLayout(new BorderLayout());

    // Left Panel: Image and Histogram
    JPanel leftPanel = new JPanel(new BorderLayout());
    imageLabel = new JLabel("", SwingConstants.CENTER);
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);

    histogramLabel = new JLabel("", SwingConstants.CENTER);
    histogramLabel.setPreferredSize(new Dimension(400, 100));

    JSplitPane imageHistogramSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            imageScrollPane, histogramLabel);
    imageHistogramSplit.setResizeWeight(0.7);
    imageHistogramSplit.setDividerSize(2);
    leftPanel.add(imageHistogramSplit, BorderLayout.CENTER);

    // Right Panel: Controls
    JPanel controlPanel = new JPanel();
    controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
    controlPanel.setPreferredSize(new Dimension(150, 600));
    controlPanel.setMinimumSize(new Dimension(150, 600));
    controlPanel.setMaximumSize(new Dimension(150, Integer.MAX_VALUE));

    // Add spacing at the top
    controlPanel.add(Box.createVerticalGlue());

    // Center alignment helper
    controlPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Add a centered label for "Basic Controls"
    JLabel basicControlsLabel = new JLabel("Basic Controls");
    basicControlsLabel.setFont(new Font("Arial", Font.BOLD, 14));
    basicControlsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    controlPanel.add(basicControlsLabel);
    controlPanel.add(Box.createVerticalStrut(10));

    // Load and Save Buttons
    JPanel loadSavePanel = createCenteredPanel();
    loadButton = createCustomButton("Load Image");
    saveButton = createCustomButton("Save Image");
    saveButton.setEnabled(false);
    loadSavePanel.add(loadButton);
    loadSavePanel.add(saveButton);
    controlPanel.add(loadSavePanel);

    controlPanel.add(Box.createVerticalStrut(0));

    // Reset and Clear Buttons
    JPanel resetClearPanel = createCenteredPanel();
    resetButton = createCustomButton("Reset to Original");
    resetButton.setEnabled(false);
    clearImage = createCustomButton("Clear Image");
    clearImage.setEnabled(false);
    resetClearPanel.add(resetButton);
    resetClearPanel.add(clearImage);
    controlPanel.add(resetClearPanel);

    JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
    separator.setMaximumSize(new Dimension(400, 2));
    controlPanel.add(separator);


    // Add a centered label for "Image Operations"
    JLabel imageOperationsLabel = new JLabel("Image Operations");
    imageOperationsLabel.setFont(new Font("Arial", Font.BOLD, 14));
    imageOperationsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    controlPanel.add(imageOperationsLabel);
    controlPanel.add(Box.createVerticalStrut(10));

    // Filter Dropdown
    JPanel filterPanel = createCenteredPanel();
    filtersDropdown = createCustomDropdown(new String[]{"Luma", "Sepia", "Blur", "Sharpen",
        "Value", "Intensity", "Color Correction", "Create Red", "Create Green", "Create Blue"});
    applyFilterButton = createCustomButton("Apply Filter");
    filtersDropdown.setSelectedIndex(0);
    applyFilterButton.setEnabled(false);
    filtersDropdown.setEnabled(false);

    filterPanel.add(filtersDropdown);
    filterPanel.add(applyFilterButton);
    controlPanel.add(filterPanel);

    // Flip Dropdown
    JPanel flipPanel = createCenteredPanel();
    flipDropdown = createCustomDropdown(new String[]{"Flip Horizontally", "Flip Vertically"});
    applyFlip = createCustomButton("Flip Image");
    applyFlip.setEnabled(false);
    flipDropdown.setEnabled(false);

    flipPanel.add(flipDropdown);
    flipPanel.add(applyFlip);
    controlPanel.add(flipPanel);
    controlPanel.add(Box.createVerticalStrut(-10));

    // Adjust Brightness
    JPanel brightnessPanel = createCenteredPanel();
    adjustBrightnessButton = createCustomButton("Adjust Brightness");
    adjustBrightnessButton.setEnabled(false);
    brightnessInput = createCustomTextField();
    brightnessInput.setToolTipText("Brightness level (-100 to 100)");
    brightnessPanel.add(adjustBrightnessButton);
    brightnessPanel.add(brightnessInput);
    controlPanel.add(brightnessPanel);
    controlPanel.add(Box.createVerticalStrut(-10));

    // Compression
    JPanel compressionPanel = createCenteredPanel();
    compressButton = createCustomButton("Compress Image");
    compressButton.setEnabled(false);
    compressionInput = createCustomTextField();
    compressionInput.setToolTipText("Compression percentage (0-100)");
    compressionPanel.add(compressButton);
    compressionPanel.add(compressionInput);
    controlPanel.add(compressionPanel);
    controlPanel.add(Box.createVerticalStrut(-10));

    // Adjust Levels
    JPanel levelsPanel = createCenteredPanel();
    adjustLevelsButton = createCustomButton("Adjust Levels");
    adjustLevelsButton.setEnabled(false);

    blackInput = createCustomTextField();
    blackInput.setToolTipText("Black level (0-255)");

    midInput = createCustomTextField();
    midInput.setToolTipText("Mid level (0-255)");

    highlightsInput = createCustomTextField();
    highlightsInput.setToolTipText("Highlights level(0-255)");

    levelsPanel.add(adjustLevelsButton);
    levelsPanel.add(new JLabel("Black: "));
    levelsPanel.add(blackInput);
    levelsPanel.add(new JLabel("Mid: "));
    levelsPanel.add(midInput);
    levelsPanel.add(new JLabel("Highlights: "));
    levelsPanel.add(highlightsInput);
    controlPanel.add(levelsPanel);
    controlPanel.add(Box.createVerticalStrut(-10));

    adjustLevelsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    adjustLevelsButton.setPreferredSize(new Dimension(100, 30));
    controlPanel.add(adjustLevelsButton);
    controlPanel.add(Box.createVerticalStrut(10));

    // Downscale Operation
    JPanel downscalePanel = createCenteredPanel();
    downscaleButton = createCustomButton("Downscale Image");
    downscaleButton.setEnabled(false);
    downscaleWidthInput = createCustomTextField();
    downscaleWidthInput.setToolTipText("Enter the width of the downscaled image");
    downscaleHeightInput = createCustomTextField();
    downscaleHeightInput.setToolTipText("Enter the height of the downscaled image");

    downscalePanel.add(downscaleButton);
    downscalePanel.add(new JLabel("Width: "));
    downscalePanel.add(downscaleWidthInput);
    downscalePanel.add(new JLabel("Height: "));
    downscalePanel.add(downscaleHeightInput);
    controlPanel.add(downscalePanel);
    controlPanel.add(Box.createVerticalStrut(-10));

    JSeparator separator2 = new JSeparator(SwingConstants.HORIZONTAL);
    separator2.setMaximumSize(new Dimension(400, 2));
    controlPanel.add(separator2);
    controlPanel.add(Box.createVerticalStrut(5));


    // Add a centered label for "Image Preview"
    JLabel imagePreviewLabel = new JLabel("Image Operations Preview");
    imagePreviewLabel.setFont(new Font("Arial", Font.BOLD, 14));
    imagePreviewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    controlPanel.add(imagePreviewLabel);
    controlPanel.add(Box.createVerticalStrut(10));

    // Filter Dropdown
    // Preview Panel Layout Adjustment
    JPanel previewPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    splitPreviewDropDown = createCustomDropdown(new String[]{"Blur", "Sharpen", "Sepia",
        "Greyscale", "Color Correction", "Levels Adjustment"});
    previewPercentageInput = createCustomTextField();
    previewPercentageInput.setPreferredSize(new Dimension(50, 25));
    splitPreviewDropDown.setEnabled(false);

    previewOperationsButton = createCustomButton("Preview");
    previewOperationsButton.setEnabled(false);
    //resetPreviewOperation = createCustomButton("Reset");
    applyPreviewButton = createCustomButton("Apply Preview");
    applyPreviewButton.setEnabled(false);

    // Add components to previewPanel
    previewPanel.add(new JLabel("Filter:"));
    previewPanel.add(splitPreviewDropDown);
    previewPanel.add(new JLabel("Percentage:"));
    previewPanel.add(previewPercentageInput);
    previewPercentageInput.setToolTipText("Enter split percentage (0-100)");
    previewPanel.add(previewOperationsButton);
    //previewPanel.add(resetPreviewOperation);
    previewPanel.add(applyPreviewButton);

    // Add the previewPanel to the controlPanel
    controlPanel.add(previewPanel);
    //controlPanel.add(Box.createVerticalStrut(10));

    // Add spacing at the bottom
    controlPanel.add(Box.createVerticalGlue());

    // Add the controlPanel to the Split Pane
    JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, controlPanel);
    mainSplitPane.setResizeWeight(0.75); // Adjusts the ratio between left and right panels
    mainSplitPane.setDividerSize(2);    // Divider size
    mainSplitPane.setContinuousLayout(true); // Smooth resizing
    add(mainSplitPane, BorderLayout.CENTER);

  }

  /**
   * Displays the processed image within the GUI. This updates the interface with the newly
   * processed image.
   */
  @Override
  public void displayImage(BufferedImage image, ImageType type) {
    if (type == ImageType.WORKING_IMAGE) {
      imageLabel.setIcon(new ImageIcon(image));
    } else {
      histogramLabel.setIcon(new ImageIcon(image));
    }
    revalidate();
    repaint();
  }

  /**
   * Adds features to the GUI by connecting the provided {@code Features} object with the
   * corresponding event listeners. This enables the GUI to interact with the controller
   * and perform image processing operations.
   *
   * @param features the {@code Features} object containing the controller's operations
   */
  @Override
  public void addFeatures(Features features) {

    //listner for load button
    loadButton.addActionListener(evt -> {

      JFileChooser fileChooser = new JFileChooser("./res/input");
      if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        try {
          File file = fileChooser.getSelectedFile();
          String fileName = file.getName().substring(0, file.getName().lastIndexOf('.'));
          features.loadImage(file.getAbsolutePath(), fileName);


          //enable all the buttons once the image is loaded
          saveButton.setEnabled(true);
          applyFilterButton.setEnabled(true);
          applyFlip.setEnabled(true);
          adjustLevelsButton.setEnabled(true);
          adjustBrightnessButton.setEnabled(true);
          compressButton.setEnabled(true);
          previewOperationsButton.setEnabled(true);
          resetButton.setEnabled(true);
          clearImage.setEnabled(true);
          downscaleButton.setEnabled(true);
          filtersDropdown.setEnabled(true);
          flipDropdown.setEnabled(true);
          splitPreviewDropDown.setEnabled(true);


        } catch (Exception ex) {
          showError("Error loading image: " + ex.getMessage());
        }
      }
    });

    //listener for clearing image
    clearImage.addActionListener(evt -> clearImage());

    //listener to open a pop up window for previewing
    previewOperationsButton.addActionListener(e -> openPreviewWindow());

    //listener for save button
    saveButton.addActionListener(evt -> {
      JFileChooser fileChooser = new JFileChooser();
      if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
        try {
          File file = fileChooser.getSelectedFile();
          String outputPath = file.getAbsolutePath();

          features.saveImage(outputPath);
        } catch (IOException ex) {
          showError("Error saving image: " + ex.getMessage());
        }
      }
    });

    //listener for flipping the image
    flipDropdown.addActionListener(evt -> {
      selectedFlip = flipDropdown.getSelectedItem().toString();
      applyFlip.setEnabled(true);
      System.out.println(selectedFlip);
    });
    //action listener for apply flip button
    applyFlip.addActionListener(evt -> {
      switch (selectedFlip) {
        case "Flip Horizontally":
          try {
            features.horizontalFlip();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
          break;
        case "Flip Vertically":
          try {
            features.verticalFlip();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
          break;
        default:
          System.out.println("Unknown component selected");
          break;
      }
    });

    //Listeners for apply filers
    filtersDropdown.addActionListener(evt -> {
      selectedFilter = filtersDropdown.getSelectedItem().toString();
      applyFilterButton.setEnabled(true);
      System.out.println(selectedFilter);
    });
    //action listener for apply filter button
    applyFilterButton.addActionListener(evt -> {
      switch (selectedFilter) {
        case "Luma":
          try {
            features.luma();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
          break;
        case "Sepia":
          try {
            features.sepia();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
          break;
        case "Blur":
          try {
            features.blur();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
          break;
        case "Sharpen":
          try {
            features.sharpen();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
          break;
        case "Value":
          try {
            features.value();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
          break;
        case "Intensity":
          try {
            features.intensity();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
          break;
        case "Color Correction":
          try {
            features.colorCorrect();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
          break;
        case "Create Red":
          try {
            features.redComponent();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
          break;
        case "Create Green":
          try {
            features.greenComponent();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
          break;
        case "Create Blue":
          try {
            features.blueComponent();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
          break;
        default:
          System.out.println("Unknown component selected");
          break;
      }
    });

    //listener for adjusting brightness
    adjustBrightnessButton.addActionListener(evt -> {
      try {
        String inputText = brightnessInput.getText();
        int value = Integer.parseInt(inputText);

        if (value >= -100 && value <= 100) {
          brightnessInput.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
          features.brighten(inputText); // Perform the bright operation
        } else {
          brightnessInput.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
          showError("Please enter a value between -100 and 100.");
        }
      } catch (NumberFormatException e) {
        brightnessInput.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        showError("Invalid input. Please enter a numerical value.");
      } catch (IOException e) {
        showError("Error adjusting brightness: " + e.getMessage());
      }
    });

    //Listener for adjust levels
    adjustLevelsButton.addActionListener(evt -> {
      try {
        int black = Integer.parseInt(blackInput.getText());
        int mid = Integer.parseInt(midInput.getText());
        int highlights = Integer.parseInt(highlightsInput.getText());

        if (black < 0 || black > 255 || mid < 0 || mid > 255 || highlights < 0
                || highlights > 255) {
          throw new IllegalArgumentException("Values must be between 0 and 255.");
        }
        if (black >= mid || mid >= highlights) {
          throw new IllegalArgumentException("Ensure Black < Mid < Highlights.");
        }

        // Reset borders to default
        resetBorders(blackInput, midInput, highlightsInput);

        // Pass valid values to the controller
        features.adjustLevels(black, mid, highlights);
      } catch (NumberFormatException ex) {
        showError("Please enter numeric values.");
        highlightBorders(Color.RED, blackInput, midInput, highlightsInput);
      } catch (IllegalArgumentException ex) {
        showError(ex.getMessage());
        highlightBorders(Color.RED, blackInput, midInput, highlightsInput);
      } catch (IOException ex) {
        showError("Error adjusting levels: " + ex.getMessage());
      }
    });


    compressButton.addActionListener(evt -> {
      try {
        int compressionValue = Integer.parseInt(compressionInput.getText());
        if (compressionValue < 1 || compressionValue > 100) {
          throw new IllegalArgumentException("Compression value must be between 1 and 100.");
        }
        compressionInput.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        features.compress(compressionValue);
      } catch (NumberFormatException ex) {
        compressionInput.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        showError("Please enter a numeric value for compression.");
      } catch (IllegalArgumentException ex) {
        compressionInput.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        showError(ex.getMessage());
      } catch (IOException ex) {
        showError("Error compressing image: " + ex.getMessage());
      }
    });

    // Listener for Preview Operations Button
    previewOperationsButton.addActionListener(evt -> {
      try {
        // Retrieve the selected operation from the dropdown
        selectedPreview = splitPreviewDropDown.getSelectedItem().toString();

        // Check if percentage is provided or set a default value
        String percentageText = previewPercentageInput.getText().trim();
        int percentage = percentageText.isEmpty() ? 100 : Integer.parseInt(percentageText);

        // Validate percentage input if entered
        if (percentage < 0 || percentage > 100) {
          throw new IllegalArgumentException("Percentage must be between 0 and 100.");
        }

        // Reset borders to indicate valid inputs
        resetBorders(previewPercentageInput);

        // Check if "Levels Adjustment" is selected
        if (selectedPreview.equals("Levels Adjustment")) {
          // Retrieve values from text fields
          String blackText = blackInput.getText().trim();
          String midText = midInput.getText().trim();
          String highlightsText = highlightsInput.getText().trim();

          // Ensure no fields are empty
          if (blackText.isEmpty() || midText.isEmpty() || highlightsText.isEmpty()) {
            throw new IllegalArgumentException("Please enter values for Black, Mid, and Highlights.");
          }

          int black = Integer.parseInt(blackText);
          int mid = Integer.parseInt(midText);
          int highlights = Integer.parseInt(highlightsText);

          // Validate the input ranges
          if (black < 0 || black > 255 || mid < 0 || mid > 255 || highlights < 0 || highlights > 255) {
            throw new IllegalArgumentException("Black, Mid, and Highlights values must be between 0 and 255.");
          }
          if (black >= mid || mid >= highlights) {
            throw new IllegalArgumentException("Ensure Black < Mid < Highlights.");
          }

          resetBorders(blackInput, midInput, highlightsInput);

          // Call the levels adjustment preview method
          features.adjustLevels(black, mid, highlights, percentage);
        } else {
          // Handle other preview operations
          switch (selectedPreview) {
            case "Blur":
              features.blur(percentage);
              break;
            case "Sharpen":
              features.sharpen(percentage);
              break;
            case "Color Correction":
              features.colorCorrect(percentage);
              break;
            case "Sepia":
              features.sepia(percentage);
              break;
            case "Greyscale":
              features.luma(percentage);
              break;
            default:
              throw new UnsupportedOperationException("Unknown operation: " + selectedPreview);
          }
        }

        // Enable the apply button once the preview is applied
        applyPreviewButton.setEnabled(true);

      } catch (NumberFormatException ex) {
        showError("Invalid input. Please enter numeric values for Percentage.");
        highlightBorders(Color.RED, previewPercentageInput);
      } catch (IllegalArgumentException ex) {
        showError(ex.getMessage());
        highlightBorders(Color.RED, blackInput, midInput, highlightsInput, previewPercentageInput);
      } catch (UnsupportedOperationException ex) {
        showError(ex.getMessage());
      } catch (IOException ex) {
        showError("Error generating preview: " + ex.getMessage());
      }
    });



    // Listener for Apply Preview Button
    applyPreviewButton.addActionListener(evt -> {
      try {
        // Validate the percentage input for all operations
        String percentageText = previewPercentageInput.getText().trim();
        if (percentageText.isEmpty()) {
          throw new IllegalArgumentException("Please enter a percentage value.");
        }

        int percentage = Integer.parseInt(percentageText);

        // Ensure percentage is within the valid range
        if (percentage < 0 || percentage > 100) {
          throw new IllegalArgumentException("Percentage must be between 0 and 100.");
        }

        // Apply the selected operation
        switch (selectedPreview) {
          case "Blur":
            features.blur(percentage);
            break;
          case "Sharpen":
            features.sharpen(percentage);
            break;
          case "Sepia":
            features.sepia(percentage);
            break;
          case "Greyscale":
            features.luma(percentage);
            break;
          case "Color Correction":
            features.colorCorrect(percentage);
            break;
          case "Levels Adjustment":
            // Validate and parse input fields for Levels Adjustment
            String blackText = blackInput.getText().trim();
            String midText = midInput.getText().trim();
            String highlightsText = highlightsInput.getText().trim();

            // Ensure no fields are empty
            if (blackText.isEmpty() || midText.isEmpty() || highlightsText.isEmpty()) {
              throw new IllegalArgumentException("Please enter values for Black, Mid, and Highlights.");
            }

            int black = Integer.parseInt(blackText);
            int mid = Integer.parseInt(midText);
            int highlights = Integer.parseInt(highlightsText);

            // Validate ranges and relationships
            if (black < 0 || black > 255 || mid < 0 || mid > 255 || highlights < 0 || highlights > 255) {
              throw new IllegalArgumentException("Black, Mid, and Highlights values must be between 0 and 255.");
            }
            if (black >= mid || mid >= highlights) {
              throw new IllegalArgumentException("Ensure Black < Mid < Highlights.");
            }

            features.adjustLevels(black, mid, highlights, percentage);
            break;
          default:
            System.out.println("Unknown operation selected");
            break;
        }
      } catch (NumberFormatException ex) {
        showError("Invalid input. Please enter numeric values for Percentage.");
        highlightBorders(Color.RED, previewPercentageInput);
      } catch (IllegalArgumentException ex) {
        showError(ex.getMessage());
        highlightBorders(Color.RED, blackInput, midInput, highlightsInput, previewPercentageInput);
      } catch (IOException ex) {
        showError("Error applying filter: " + ex.getMessage());
      }
    });



    //listner for reset
    resetButton.addActionListener(evt -> {
      features.resetToOriginal();
    });

    //action listener for downscale button, it must accept width and height of the image
    downscaleButton.addActionListener(evt -> {
      try {
        int width = Integer.parseInt(downscaleWidthInput.getText());
        int height = Integer.parseInt(downscaleHeightInput.getText());

        if (!(height < 0 || width < 0)) {
          downscaleHeightInput.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
          downscaleWidthInput.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
          features.downscale(width, height);
        } else {
          downscaleWidthInput.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
          downscaleHeightInput.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
          showError("Please enter a valid value");
        }
      } catch (NumberFormatException e) {
        downscaleWidthInput.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        downscaleHeightInput.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        showError("Invalid input. Please enter a numerical value.");
      } catch (IOException e) {
        showError("Error downscaling image: " + e.getMessage());
      }

    });
  }


  /**
   * Displays an error message in a dialog box.
   *
   * @param message the error message to be displayed
   */
  @Override
  public void showError(String message) {
    JOptionPane.showMessageDialog(this, message, "Error",
            JOptionPane.ERROR_MESSAGE);
  }


  /**
   * Clears the currently displayed image and histogram from the view.
   * Disables all image-related buttons and dropdown menus until a new image is loaded.
   */
  @Override
  public void clearImage() {
    imageLabel.setIcon(null);
    histogramLabel.setIcon(null);

    //disable buttons when clear image is selected
    saveButton.setEnabled(false);
    resetButton.setEnabled(false);
    applyPreviewButton.setEnabled(false);
    applyFilterButton.setEnabled(false);
    adjustLevelsButton.setEnabled(false);
    adjustBrightnessButton.setEnabled(false);
    compressButton.setEnabled(false);
    previewOperationsButton.setEnabled(false);
    applyFlip.setEnabled(false);
    clearImage.setEnabled(false);
    downscaleButton.setEnabled(false);
    filtersDropdown.setEnabled(false);
    flipDropdown.setEnabled(false);
    splitPreviewDropDown.setEnabled(false);
  }

  private JButton createCustomButton(String text) {
    JButton button = new JButton(text);
    button.setPreferredSize(new Dimension(150, 30));
    button.setFont(new Font("Arial", Font.BOLD, 12));
    button.setBackground(new Color(200, 200, 200));
    button.setForeground(Color.BLACK);
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    return button;
  }

  private JComboBox<String> createCustomDropdown(String[] items) {
    JComboBox<String> dropdown = new JComboBox<>(items);
    dropdown.setPreferredSize(new Dimension(150, 30));
    return dropdown;
  }

  private JTextField createCustomTextField() {
    JTextField textField = new JTextField(3);
    textField.setPreferredSize(new Dimension(150, 30));
    return textField;
  }

  private JPanel createHorizontalPanel() {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
    return panel;
  }

  private JPanel createCenteredPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5)); // Center alignment
    panel.setOpaque(false); // Make it transparent
    return panel;
  }

  private void resetBorders(JTextField... fields) {
    for (JTextField field : fields) {
      field.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    }
  }

  private void highlightBorders(Color color, JTextField... fields) {
    for (JTextField field : fields) {
      field.setBorder(BorderFactory.createLineBorder(color, 2));
    }
  }

  //code for preview window
  private void openPreviewWindow() {
    // Create the preview window
    JFrame previewWindow = new JFrame("Preview Operations");
    previewWindow.setSize(800, 600);
    previewWindow.setLayout(new BorderLayout());

    // Image Panel to display the preview
    JPanel imagePanel = new JPanel();
    JLabel imageLabel = new JLabel(); // Label to hold the preview image
    imagePanel.add(imageLabel);
    previewWindow.add(imagePanel, BorderLayout.CENTER);

    // Control Panel for dropdown, percentage input, and buttons
    JPanel controlPanel = new JPanel();
    controlPanel.setLayout(new FlowLayout());

    // Dropdown for filter selection
    JComboBox<String> filterDropdown = new JComboBox<>(
            new String[]{"Blur", "Sharpen", "Sepia", "Greyscale", "Color Correction",
                "Levels Adjustment"}
    );
    controlPanel.add(new JLabel("Filter: "));
    controlPanel.add(filterDropdown);

  }

  //method to enable or disable buttons based on image loaded or not
  private void toggleOperationButtons(boolean isEnabled) {
    saveButton.setEnabled(isEnabled);
    resetButton.setEnabled(isEnabled);
    clearImage.setEnabled(isEnabled);
    applyFilterButton.setEnabled(isEnabled);
    applyFlip.setEnabled(isEnabled);
    adjustBrightnessButton.setEnabled(isEnabled);
    compressButton.setEnabled(isEnabled);
    adjustLevelsButton.setEnabled(isEnabled);
    previewOperationsButton.setEnabled(isEnabled);
    applyPreviewButton.setEnabled(isEnabled);

    // Disable text fields when no image is loaded
    brightnessInput.setEnabled(isEnabled);
    compressionInput.setEnabled(isEnabled);
    blackInput.setEnabled(isEnabled);
    midInput.setEnabled(isEnabled);
    highlightsInput.setEnabled(isEnabled);
    previewPercentageInput.setEnabled(isEnabled);

    // Disable dropdowns when no image is loaded
    filtersDropdown.setEnabled(isEnabled);
    flipDropdown.setEnabled(isEnabled);
    splitPreviewDropDown.setEnabled(isEnabled);
  }


  public void setVisibleView() {
    this.setVisible(true);
  }

  /**
   * The entry point of the application that launches the ImageProcessingGUI.
   * This method is invoked when the application starts.
   *
   * @parm args Command line arguments (not used in this implementation).
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(ImageProcessingGUI::new);
  }
}
