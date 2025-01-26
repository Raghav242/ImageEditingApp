# USEME File

This document provides an overview of the supported commands, their usage, and example scripts to
assist you in executing them correctly.

## Supported Commands

### File Management

- **load**: Loads an image file for processing.
    - Usage: `load <filename> <imageName>`

- **save**: Saves an image under a specified name.
    - Usage: `save <filename> <imageName>`

---

**Note**: Always load an image before processing. If the image is not loaded, the application will
not be able to locate it.

### Basic Commands

- **blur**: Applies a blur effect to an image. The optional `split` parameter allows for splitting
  the effect at a specified percentage width.
    - Usage: `blur <sourceImage> <outputImage> [split <percentage>]`

- **sharpen**: Sharpens the image. The `split` parameter is optional.
    - Usage: `sharpen <sourceImage> <outputImage> [split <percentage>]`

- **sepia**: Converts the image to sepia tone. The `split` parameter is optional.
    - Usage: `sepia <sourceImage> <outputImage> [split <percentage>]`

- **greyscale**: Converts the image to greyscale. The `split` parameter is optional.
    - Usage: `greyscale <sourceImage> <outputImage> [split <percentage>]`

- **color-correct**: Corrects color imbalances in the image. The `split` parameter is optional.
    - Usage: `color-correct <sourceImage> <outputImage> [split <percentage>]`

- **levels-adjust**: Adjusts levels using provided min, mid, and max values. The `split` parameter
  is optional.
    - Usage: `levels-adjust <min> <mid> <max> <sourceImage> <outputImage> [split <percentage>]`

### Component Commands

- **red-component**: Extracts the red color component from the image.
    - Usage: `red-component <sourceImage> <outputImage>`

- **green-component**: Extracts the green color component from the image.
    - Usage: `green-component <sourceImage> <outputImage>`

- **blue-component**: Extracts the blue color component from the image.
    - Usage: `blue-component <sourceImage> <outputImage>`

- **luma-component**: Extracts the luma component (brightness based on RGB weights) from the image.
    - Usage: `luma-component <sourceImage> <outputImage>`

- **value-component**: Extracts the value component (maximum RGB value) from the image.
    - Usage: `value-component <sourceImage> <outputImage>`

- **intensity-component**: Extracts the intensity component (average of RGB values) from the image.
    - Usage: `intensity-component <sourceImage> <outputImage>`

### RGB Split and Combine

- **rgb-split**: Splits the image into separate red, green, and blue components.
    - Usage: `rgb-split <sourceImage> <outputRedImage> <outputGreenImage> <outputBlueImage>`

- **rgb-combine**: Combines separate red, green, and blue images into one.
    - Usage: `rgb-combine <outputImage> <redImage> <greenImage> <blueImage>`

### Flip Commands

- **horizontal-flip**: Flips the image horizontally.
    - Usage: `horizontal-flip <sourceImage> <outputImage>`

- **vertical-flip**: Flips the image vertically.
    - Usage: `vertical-flip <sourceImage> <outputImage>`

### Brighten/Darken

- **brighten**: Brightens or darkens the image by a given amount. Negative values darken the image.
    - Usage: `brighten <value> <sourceImage> <outputImage>`

### Compression

- **compress**: Compresses the image to a specified quality percentage.
    - Usage: `compress <qualityPercentage> <sourceImage> <outputImage>`

### Histogram

- **histogram**: Generates a histogram of the image.
    - Usage: `histogram <sourceImage> <outputImage>`


### Downsclaing is supported only in the GUI

  - Make sure the image is loaded in GUI. 
  - Enter the new width and new height and click on the downscale button to see the downscaled Image.
## Image Transformations with Masked Images

This document provides examples of applying various transformations to an original image and its
masked version, followed by saving the transformed outputs.

### Load Images

- **load**: Loads the original and modified masked images for processing.
    - Usage:
      ```plaintext
      load <filePath> <imageName>
      ```
    - Example:
      ```plaintext
      load res/input/uni.png image1
      load res/input/uni-modified.png maskedImage
      ```

### Masked Transformations

- **blur**: Applies a blur effect to the masked image.
    - Usage: `blur <sourceImage> <maskedImage> <outputImage>`

- **sharpen**: Applies a sharpening effect to the masked image.
    - Usage: `sharpen <sourceImage> <maskedImage> <outputImage>`

- **sepia**: Converts the masked image to sepia tone.
    - Usage: `sepia <sourceImage> <maskedImage> <outputImage>`

- **grayscale**: Converts the masked image to grayscale.
    - Usage: `grayscale <sourceImage> <maskedImage> <outputImage>`

### Color Component Extraction

- **red-component**: Extracts the red color component from the masked image.
    - Usage: `red-component <sourceImage> <maskedImage> <outputImage>`

- **green-component**: Extracts the green color component from the masked image.
    - Usage: `green-component <sourceImage> <maskedImage> <outputImage>`

- **blue-component**: Extracts the blue color component from the masked image.
    - Usage: `blue-component <sourceImage> <maskedImage> <outputImage>`

# GUI Buttons and Usage

## Central Area
- A central area displays the currently loaded image.
- A histogram is displayed below the image, providing visual information about the image's pixel distribution.

## Control Panel
The control panel houses various buttons, dropdown menus, and text fields to control image processing operations.

---

### **Basic Controls**
1. **Load Image**:
    - Click the **Load Image** button.
    - Browse and select the desired image file from the file system.
    - The image will be displayed in the central area.

2. **Save Image**:
    - Click the **Save Image** button.
    - Choose a file name and destination to save the image.

3. **Reset to Original**:
    - Click the **Reset to Original** button to revert the image to its initially loaded state.

4. **Clear Image**:
    - Click the **Clear Image** button to remove the currently loaded image from the display.

---

### **Image Operations**
1. **Filter Application**:
    - Use the **Filter Dropdown** to select a filter (e.g., Luma, Sepia, Blur).
    - Click the **Apply Filter** button to apply the selected filter.

2. **Flip Application**:
    - Use the **Flip Dropdown** to choose either horizontal or vertical flipping.
    - Click the **Apply Flip** button to flip the image accordingly.

3. **Adjust Brightness**:
    - Enter a brightness level (positive for brighter, negative for darker) in the **Brightness Text Field**.
    - Click the **Adjust Brightness** button to modify the image brightness.

4. **Compress Image**:
    - Enter the desired compression percentage (e.g., 50 for 50% quality) in the **Compression Text Field**.
    - Click the **Compress Image** button to reduce the file size.

5. **Adjust Levels**:
    - Input values for **Black**, **Mid**, and **White** levels in their respective text fields (values must be between 0 and 255 and in increasing order).
    - Click the **Adjust Levels** button to apply the level adjustments.

6. **Downscale Image**:
    - Enter the desired dimensions (width and height in pixels) in the respective text fields.
    - Click the **Downscale Image** button to resize the image.

---

### **Preview and Apply Preview**
1. **Preview an Effect**:
    - Select a filter from the **Preview Dropdown**.
    - Enter a percentage in the **Percentage Text Field** to specify the extent of the effect (e.g., 50% for a split effect).
    - **For the Adjust Levels filter**, use the text fields for **Black**, **Mid**, and **White** levels to input the desired values. Ensure they are in increasing order (values between 0 and 255).
    - Click the **Preview** button to see a preview of the filter.

2. **Apply the Previewed Effect**:
    - After previewing, click the **Apply Preview** button to apply the effect permanently to the image.

---


### Script Execution

- **run**: Executes a script file containing commands via the command line or other supported modes.
    - Usage:
        - **Script Execution Mode**:
          ```plaintext
          java -jar Program.jar -file <scriptFilePath>
          ```
          The program will open the specified script file, execute all commands in sequence, and
          shut down automatically.
        - **Interactive Text Mode**:
          ```plaintext
          java -jar Program.jar -text
          ```
          The program will open in an interactive text mode, allowing users to type and execute
          commands line-by-line.
        - **Graphical User Interface (GUI) Mode**:
          ```plaintext
          java -jar Program.jar
          ```
          The program will launch its graphical user interface for interactive usage by default if
          clicked on the jar file.
