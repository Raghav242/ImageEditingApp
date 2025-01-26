package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static model.RGBImage.pixelMatrixToString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The {@code AbstractImageTest} class contains unit tests for the abstract class
 * {@code AbstractImage}. It ensures that implementations of {@code AbstractImage}, such as
 * {@code RGBImage}, adhere to expected behaviors. The tests focus on the general image processing
 * functionality like creation, pixel retrieval, and transformations.
 */
public class AbstractImageTest {

  private RGBImage originalImage;

  /**
   * setup will initialise the image.
   */
  @BeforeEach
  public void setUp() {
    originalImage = new RGBImage();
    originalImage.setWidth(2);
    originalImage.setHeight(2);

    RGBImagePixel[][] pixels = {
            {new RGBImagePixel(255, 0, 0), new RGBImagePixel(0, 255, 0)},
            {new RGBImagePixel(0, 0, 255), new RGBImagePixel(255, 255, 0)}
    };
    originalImage.setImage(pixels);
  }

  @Test
  public void testBlur() {
    RGBImage blurredImage = (RGBImage) originalImage.blur();
    assertNotNull(blurredImage, "Blurred image should not be null");

    RGBImagePixel[][] actualPixels = blurredImage.getImage();
    System.out.println("Blurred Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(80, 48, 32), new RGBImagePixel(64, 96, 16)},
            {new RGBImagePixel(64, 48, 64), new RGBImagePixel(80, 96, 32)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testBlurWithPercentage() {
    int percentage = 50;
    RGBImage blurredImage = (RGBImage) originalImage.blur(percentage);
    assertNotNull(blurredImage, "Blurred image should not be null");

    RGBImagePixel[][] actualPixels = blurredImage.getImage();
    System.out.println("Blurred Image Pixels (Percentage: " + percentage + "):");
    System.out.println(pixelMatrixToString(actualPixels));
    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(80, 48, 32), new RGBImagePixel(64, 96, 16)},
            {new RGBImagePixel(64, 48, 64), new RGBImagePixel(80, 96, 32)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }


  @Test
  public void testSharpen() {
    RGBImage sharpenedImage = (RGBImage) originalImage.sharpen();
    assertNotNull(sharpenedImage, "Sharpened image should not be null");

    RGBImagePixel[][] actualPixels = sharpenedImage.getImage();
    System.out.println("Sharpened Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(255, 128, 64), new RGBImagePixel(128, 255, 64)},
            {new RGBImagePixel(128, 128, 255), new RGBImagePixel(255, 255, 64)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testSharpenWithPercentage() {
    int sharpenPercentage = 50;
    RGBImage sharpenedImage = (RGBImage) originalImage.sharpen(sharpenPercentage);
    assertNotNull(sharpenedImage, "Sharpened image should not be null");

    RGBImagePixel[][] actualPixels = sharpenedImage.getImage();
    System.out.println("Sharpened Image Pixels with " + sharpenPercentage + "% sharpening:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(255, 128, 64), new RGBImagePixel(128, 255, 64)},
            {new RGBImagePixel(128, 128, 255), new RGBImagePixel(255, 255, 64)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }


  @Test
  public void testBrighten() {
    RGBImage brightenedImage = (RGBImage) originalImage.brightness(50);
    assertNotNull(brightenedImage, "Brightened image should not be null");

    RGBImagePixel[][] actualPixels = brightenedImage.getImage();
    System.out.println("Brightened Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(255, 50, 50), new RGBImagePixel(50, 255, 50)},
            {new RGBImagePixel(50, 50, 255), new RGBImagePixel(255, 255, 50)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testDarkening() {
    RGBImage darkenedImage = (RGBImage) originalImage.brightness(-50);
    assertNotNull(darkenedImage, "Darkened image should not be null");

    RGBImagePixel[][] actualPixels = darkenedImage.getImage();
    System.out.println("Darkened Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(205, 0, 0), new RGBImagePixel(0, 205, 0)},
            {new RGBImagePixel(0, 0, 205), new RGBImagePixel(205, 205, 0)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testFlipHorizontal() {
    RGBImage flippedImage = (RGBImage) originalImage.flipHorizontal();
    assertNotNull(flippedImage, "Flipped image should not be null");

    RGBImagePixel[][] actualPixels = flippedImage.getImage();
    System.out.println("Flipped Horizontal Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(0, 255, 0), new RGBImagePixel(255, 0, 0)},
            {new RGBImagePixel(255, 255, 0), new RGBImagePixel(0, 0, 255)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testFlipVertical() {
    RGBImage flippedImage = (RGBImage) originalImage.flipVertical();
    assertNotNull(flippedImage, "Flipped image should not be null");

    RGBImagePixel[][] actualPixels = flippedImage.getImage();
    System.out.println("Flipped Vertical Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(0, 0, 255), new RGBImagePixel(255, 255, 0)},
            {new RGBImagePixel(255, 0, 0), new RGBImagePixel(0, 255, 0)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testSepia() {
    RGBImage sepiaImage = (RGBImage) originalImage.convertToSepia();
    assertNotNull(sepiaImage, "Sepia image should not be null");

    RGBImagePixel[][] actualPixels = sepiaImage.getImage();
    System.out.println("Sepia Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(100, 89, 69), new RGBImagePixel(196, 175, 136)},
            {new RGBImagePixel(48, 43, 33), new RGBImagePixel(255, 255, 206)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testSepiaWithPercentage() {
    int sepiaPercentage = 70;
    RGBImage sepiaImage = (RGBImage) originalImage.convertToSepia(sepiaPercentage);
    assertNotNull(sepiaImage, "Sepia image should not be null");

    RGBImagePixel[][] actualPixels = sepiaImage.getImage();
    System.out.println("Sepia Image Pixels with " + sepiaPercentage + "% sepia effect:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(100, 89, 69), new RGBImagePixel(196, 175, 136)},
            {new RGBImagePixel(48, 43, 33), new RGBImagePixel(255, 255, 206)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }


  @Test
  public void testGrayscale() {
    RGBImage grayscaleImage = (RGBImage) originalImage.convertToGreyScale();
    assertNotNull(grayscaleImage, "Grayscale image should not be null");

    RGBImagePixel[][] actualPixels = grayscaleImage.getImage();
    System.out.println("Grayscale Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(54, 54, 54), new RGBImagePixel(182, 182, 182)},
            {new RGBImagePixel(18, 18, 18), new RGBImagePixel(237, 237, 237)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testValue() {
    RGBImage valueImage = (RGBImage) originalImage.convertToValue();
    assertNotNull(valueImage, "Value image should not be null");

    RGBImagePixel[][] actualPixels = valueImage.getImage();
    System.out.println("Value Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(255, 255, 255)},
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(255, 255, 255)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testIntensity() {
    RGBImage intensityImage = (RGBImage) originalImage.convertToIntensity();
    assertNotNull(intensityImage, "Intensity image should not be null");

    RGBImagePixel[][] actualPixels = intensityImage.getImage();
    System.out.println("Intensity Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(85, 85, 85), new RGBImagePixel(85, 85, 85)},
            {new RGBImagePixel(85, 85, 85), new RGBImagePixel(170, 170, 170)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }


  @Test
  public void testRGBCombine() {
    RGBImage redImage = new RGBImage();
    redImage.setWidth(2);
    redImage.setHeight(2);
    redImage.setImage(new RGBImagePixel[][]{
            {new RGBImagePixel(255, 0, 0), new RGBImagePixel(255, 0, 0)},
            {new RGBImagePixel(255, 0, 0), new RGBImagePixel(255, 0, 0)}
    });

    RGBImage greenImage = new RGBImage();
    greenImage.setWidth(2);
    greenImage.setHeight(2);
    greenImage.setImage(new RGBImagePixel[][]{
            {new RGBImagePixel(0, 255, 0), new RGBImagePixel(0, 255, 0)},
            {new RGBImagePixel(0, 255, 0), new RGBImagePixel(0, 255, 0)}
    });

    RGBImage blueImage = new RGBImage();
    blueImage.setWidth(2);
    blueImage.setHeight(2);
    blueImage.setImage(new RGBImagePixel[][]{
            {new RGBImagePixel(0, 0, 255), new RGBImagePixel(0, 0, 255)},
            {new RGBImagePixel(0, 0, 255), new RGBImagePixel(0, 0, 255)}
    });

    RGBImage combinedImage = RGBImage.rgbCombine(redImage, greenImage, blueImage);
    assertNotNull(combinedImage, "Combined RGB image should not be null");

    RGBImagePixel[][] actualPixels = combinedImage.getImage();
    System.out.println("RGB Combined Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(255, 255, 255)},
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(255, 255, 255)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testSplitImages() {

    RGBImage[] splitImages = new RGBImage[]{
            originalImage.createRed(),
            originalImage.createGreen(),
            originalImage.createBlue()
    };

    assertEquals(3, splitImages.length,
            "There should be three split images for RGB channels");

    for (int i = 0; i < splitImages.length; i++) {
      assertNotNull(splitImages[i], "Channel image " + i + " should not be null");
    }

    RGBImagePixel[][] redPixels = splitImages[0].getImage();
    assertRedChannelOnly(redPixels);
    RGBImagePixel[][] greenPixels = splitImages[1].getImage();
    assertGreenChannelOnly(greenPixels);

    RGBImagePixel[][] bluePixels = splitImages[2].getImage();
    assertBlueChannelOnly(bluePixels);
  }

  @Test
  public void testCreateRed() {
    RGBImage redImage = originalImage.createRed();
    assertNotNull(redImage, "Red image should not be null");

    RGBImagePixel[][] actualPixels = redImage.getImage();
    RGBImagePixel[][] expectedPixels = {
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(0, 0, 0)},
            {new RGBImagePixel(0, 0, 0), new RGBImagePixel(255, 255, 255)}
    };

    assertPixelMatrixEquals(expectedPixels, actualPixels);
  }


  @Test
  public void testCreateGreen() {
    RGBImage greenImage = originalImage.createGreen();
    assertNotNull(greenImage, "Green image should not be null");

    RGBImagePixel[][] actualPixels = greenImage.getImage();
    RGBImagePixel[][] expectedPixels = {
            {new RGBImagePixel(0, 0, 0), new RGBImagePixel(255, 255, 255)},
            {new RGBImagePixel(0, 0, 0), new RGBImagePixel(255, 255, 255)}
    };

    assertPixelMatrixEquals(expectedPixels, actualPixels);
  }


  @Test
  public void testCreateBlue() {
    RGBImage blueImage = originalImage.createBlue();
    assertNotNull(blueImage, "Blue image should not be null");

    RGBImagePixel[][] actualPixels = blueImage.getImage();
    RGBImagePixel[][] expectedPixels = {
            {new RGBImagePixel(0, 0, 0), new RGBImagePixel(0, 0, 0)},
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(0, 0, 0)}
    };

    assertPixelMatrixEquals(expectedPixels, actualPixels);
  }


  private void assertRedChannelOnly(RGBImagePixel[][] pixels) {
    for (RGBImagePixel[] row : pixels) {
      for (RGBImagePixel pixel : row) {
        assertNotNull(pixel, "Pixel should not be null");
        int redValue = pixel.getRed();
        assertEquals(redValue, pixel.getGreen(), "Green value should match Red value");
        assertEquals(redValue, pixel.getBlue(), "Blue value should match Red value");
      }
    }
  }

  private void assertGreenChannelOnly(RGBImagePixel[][] pixels) {
    for (RGBImagePixel[] row : pixels) {
      for (RGBImagePixel pixel : row) {
        assertNotNull(pixel, "Pixel should not be null");
        int greenValue = pixel.getGreen();
        assertEquals(greenValue, pixel.getRed(), "Red value should match Green value");
        assertEquals(greenValue, pixel.getBlue(), "Blue value should match Green value");
      }
    }
  }


  private void assertBlueChannelOnly(RGBImagePixel[][] pixels) {
    for (RGBImagePixel[] row : pixels) {
      for (RGBImagePixel pixel : row) {
        assertNotNull(pixel, "Pixel should not be null");
        int blueValue = pixel.getBlue();
        assertEquals(blueValue, pixel.getRed(), "Red value should match Blue value");
        assertEquals(blueValue, pixel.getGreen(), "Green value should match Blue value");
      }
    }
  }


  @Test
  public void testMaxBrighten() {
    RGBImage brightenedImage = (RGBImage) originalImage.brightness(255);
    assertNotNull(brightenedImage);

    RGBImagePixel[][] actualPixels = brightenedImage.getImage();
    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(255, 255, 255)},
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(255, 255, 255)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testMaxDarken() {
    RGBImage darkenedImage = (RGBImage) originalImage.brightness(-255);
    assertNotNull(darkenedImage);

    RGBImagePixel[][] actualPixels = darkenedImage.getImage();
    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(0, 0, 0), new RGBImagePixel(0, 0, 0)},
            {new RGBImagePixel(0, 0, 0), new RGBImagePixel(0, 0, 0)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testFlipHorizontalOddWidth() {
    originalImage.setWidth(3);
    originalImage.setHeight(2);
    RGBImagePixel[][] pixels = {
            {new RGBImagePixel(255, 0, 0),
            new RGBImagePixel(0, 255, 0), new RGBImagePixel(0, 0, 255)},
            {new RGBImagePixel(255, 255, 0), new RGBImagePixel(0, 255, 255),
            new RGBImagePixel(255, 0, 255)}
    };
    originalImage.setImage(pixels);

    RGBImage flippedImage = (RGBImage) originalImage.flipHorizontal();
    assertNotNull(flippedImage);

    RGBImagePixel[][] actualPixels = flippedImage.getImage();
    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(0, 0, 255), new RGBImagePixel(0, 255, 0),
            new RGBImagePixel(255, 0, 0)},
            {new RGBImagePixel(255, 0, 255), new RGBImagePixel(0, 255, 255),
            new RGBImagePixel(255, 255, 0)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testFlipVerticalOddHeight() {
    originalImage.setWidth(2);
    originalImage.setHeight(3);
    RGBImagePixel[][] pixels = {
            {new RGBImagePixel(255, 0, 0), new RGBImagePixel(0, 255, 0)},
            {new RGBImagePixel(0, 0, 255), new RGBImagePixel(255, 255, 0)},
            {new RGBImagePixel(0, 255, 255), new RGBImagePixel(255, 0, 255)}
    };
    originalImage.setImage(pixels);

    RGBImage flippedImage = (RGBImage) originalImage.flipVertical();
    assertNotNull(flippedImage);

    RGBImagePixel[][] actualPixels = flippedImage.getImage();
    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(0, 255, 255), new RGBImagePixel(255, 0, 255)},
            {new RGBImagePixel(0, 0, 255), new RGBImagePixel(255, 255, 0)},
            {new RGBImagePixel(255, 0, 0), new RGBImagePixel(0, 255, 0)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testGrayscaleAllWhite() {
    originalImage.setWidth(2);
    originalImage.setHeight(2);
    RGBImagePixel[][] whitePixels = {
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(255, 255, 255)},
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(255, 255, 255)}
    };
    originalImage.setImage(whitePixels);

    RGBImage grayscaleImage = (RGBImage) originalImage.convertToGreyScale();
    assertNotNull(grayscaleImage);

    RGBImagePixel[][] actualPixels = grayscaleImage.getImage();
    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(255, 255, 255)},
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(255, 255, 255)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testValueMaxChannelDifference() {
    originalImage.setWidth(2);
    originalImage.setHeight(2);
    RGBImagePixel[][] extremePixels = {
            {new RGBImagePixel(255, 0, 0), new RGBImagePixel(0, 255, 255)},
            {new RGBImagePixel(0, 0, 0), new RGBImagePixel(255, 255, 255)}
    };
    originalImage.setImage(extremePixels);

    RGBImage valueImage = (RGBImage) originalImage.convertToValue();
    assertNotNull(valueImage);

    RGBImagePixel[][] actualPixels = valueImage.getImage();
    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(255, 255, 255)},
            {new RGBImagePixel(0, 0, 0), new RGBImagePixel(255, 255, 255)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testExcessiveDarkening() {
    RGBImage darkenedImage = (RGBImage) originalImage.brightness(-300);
    assertNotNull(darkenedImage, "Darkened image should not be null");

    RGBImagePixel[][] actualPixels = darkenedImage.getImage();
    System.out.println("Excessive Darkened Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(0, 0, 0), new RGBImagePixel(0, 0, 0)},
            {new RGBImagePixel(0, 0, 0), new RGBImagePixel(0, 0, 0)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testNoBrightnessChange() {
    RGBImage brightenedImage = (RGBImage) originalImage.brightness(0);
    assertNotNull(brightenedImage, "Brightened image should not be null");

    RGBImagePixel[][] actualPixels = brightenedImage.getImage();
    System.out.println("No Brightness Change Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = originalImage.getImage();

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }


  @Test
  public void testMirrorImageOddDimensions() {
    originalImage.setWidth(3);
    originalImage.setHeight(3);
    RGBImagePixel[][] pixels = {
            {new RGBImagePixel(255, 0, 0),
            new RGBImagePixel(0, 255, 0), new RGBImagePixel(0, 0, 255)},
            {new RGBImagePixel(255, 255, 0),
            new RGBImagePixel(0, 255, 255),
            new RGBImagePixel(255, 0, 255)},
            {new RGBImagePixel(0, 0, 0),
            new RGBImagePixel(255, 255, 255),
            new RGBImagePixel(128, 128, 128)}
    };
    originalImage.setImage(pixels);

    RGBImage mirroredImage = (RGBImage) originalImage.flipHorizontal();
    assertNotNull(mirroredImage, "Mirrored image should not be null");

    RGBImagePixel[][] actualPixels = mirroredImage.getImage();
    System.out.println("Mirrored Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(0, 0, 255),
            new RGBImagePixel(0, 255, 0), new RGBImagePixel(255, 0, 0)},
            {new RGBImagePixel(255, 0, 255),
            new RGBImagePixel(0, 255, 255),
            new RGBImagePixel(255, 255, 0)},
            {new RGBImagePixel(128, 128, 128),
            new RGBImagePixel(255, 255, 255), new RGBImagePixel(0, 0, 0)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testMaxSepia() {
    originalImage.setWidth(2);
    originalImage.setHeight(2);
    RGBImagePixel[][] whitePixels = {
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(255, 255, 255)},
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(255, 255, 255)}
    };
    originalImage.setImage(whitePixels);

    RGBImage sepiaImage = (RGBImage) originalImage.convertToSepia();
    assertNotNull(sepiaImage, "Sepia image should not be null");

    RGBImagePixel[][] actualPixels = sepiaImage.getImage();
    System.out.println("Max Sepia Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(255, 255, 239), new RGBImagePixel(255, 255, 239)},
            {new RGBImagePixel(255, 255, 239), new RGBImagePixel(255, 255, 239)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testGrayscaleOddRGBValues() {
    RGBImagePixel[][] oddPixels = {
            {new RGBImagePixel(123, 87, 56), new RGBImagePixel(44, 255, 128)},
            {new RGBImagePixel(32, 45, 67), new RGBImagePixel(255, 192, 203)}
    };
    originalImage.setImage(oddPixels);

    RGBImage grayscaleImage = (RGBImage) originalImage.convertToGreyScale();
    assertNotNull(grayscaleImage, "Grayscale image should not be null");

    RGBImagePixel[][] actualPixels = grayscaleImage.getImage();
    System.out.println("Grayscale Odd RGB Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(92, 92, 92), new RGBImagePixel(201, 201, 201)},
            {new RGBImagePixel(44, 44, 44), new RGBImagePixel(206, 206, 206)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }


  @Test
  public void testBlurThenGrayscale() {
    RGBImage transformedImage = (RGBImage) originalImage.blur().convertToGreyScale();
    assertNotNull(transformedImage, "Transformed image should not be null");

    RGBImagePixel[][] actualPixels = transformedImage.getImage();
    System.out.println("Blur + Grayscale Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(54, 54, 54), new RGBImagePixel(83, 83, 83)},
            {new RGBImagePixel(53, 53, 53), new RGBImagePixel(88, 88, 88)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testSharpenThenSepia() {
    RGBImage transformedImage = (RGBImage) originalImage.sharpen().convertToSepia();
    assertNotNull(transformedImage, "Transformed image should not be null");

    RGBImagePixel[][] actualPixels = transformedImage.getImage();
    System.out.println("Sharpen + Sepia Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(211, 188, 146), new RGBImagePixel(255, 230, 179)},
            {new RGBImagePixel(197, 175, 137), new RGBImagePixel(255, 255, 214)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testBrightenThenFlipHorizontal() {
    RGBImage transformedImage = (RGBImage) originalImage.brightness(50).flipHorizontal();
    assertNotNull(transformedImage, "Transformed image should not be null");

    RGBImagePixel[][] actualPixels = transformedImage.getImage();
    System.out.println("Brighten + Flip Horizontal Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(50, 255, 50), new RGBImagePixel(255, 50, 50)},
            {new RGBImagePixel(255, 255, 50), new RGBImagePixel(50, 50, 255)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testFlipVerticalThenDarken() {
    RGBImage transformedImage = (RGBImage) originalImage.flipVertical().brightness(-50);
    assertNotNull(transformedImage, "Transformed image should not be null");

    RGBImagePixel[][] actualPixels = transformedImage.getImage();
    System.out.println("Flip Vertical + Darken Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(0, 0, 205), new RGBImagePixel(205, 205, 0)},
            {new RGBImagePixel(205, 0, 0), new RGBImagePixel(0, 205, 0)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testSepiaThenSharpenThenGrayscale() {
    RGBImage transformedImage = (RGBImage)
            originalImage.convertToSepia().sharpen().convertToGreyScale();
    assertNotNull(transformedImage, "Transformed image should not be null");

    RGBImagePixel[][] actualPixels = transformedImage.getImage();
    System.out.println("Sepia + Sharpen + Grayscale Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(208, 208, 208), new RGBImagePixel(252, 252, 252)},
            {new RGBImagePixel(173, 173, 173), new RGBImagePixel(255, 255, 255)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testFlipHorizontalThenBlurThenBrighten() {
    RGBImage transformedImage = (RGBImage)
            originalImage.flipHorizontal().blur().brightness(30);
    assertNotNull(transformedImage, "Transformed image should not be null");

    RGBImagePixel[][] actualPixels = transformedImage.getImage();
    System.out.println("Flip Horizontal + Blur + Brighten Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(94, 126, 46), new RGBImagePixel(110, 78, 62)},
            {new RGBImagePixel(110, 126, 62), new RGBImagePixel(94, 78, 94)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testDarkenThenFlipVerticalThenSharpen() {
    RGBImage transformedImage =
            (RGBImage) originalImage.brightness(-50).flipVertical().sharpen();
    assertNotNull(transformedImage, "Transformed image should not be null");

    RGBImagePixel[][] actualPixels = transformedImage.getImage();
    System.out.println("Darken + Flip Vertical + Sharpen Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(103, 103, 205), new RGBImagePixel(255, 255, 51)},
            {new RGBImagePixel(255, 103, 51), new RGBImagePixel(103, 255, 51)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testBlurThenSepiaThenFlipHorizontal() {
    RGBImage transformedImage = (RGBImage) originalImage.blur().convertToSepia().flipHorizontal();
    assertNotNull(transformedImage, "Transformed image should not be null");

    RGBImagePixel[][] actualPixels = transformedImage.getImage();
    System.out.println("Blur + Sepia + Flip Horizontal Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(102, 91, 71), new RGBImagePixel(74, 66, 52)},
            {new RGBImagePixel(111, 99, 77), new RGBImagePixel(74, 66, 51)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testCompressImage() throws IOException {

    originalImage.setWidth(2);
    originalImage.setHeight(2);

    RGBImagePixel[][] pixels = {
            {new RGBImagePixel(100, 200, 70), new RGBImagePixel(0, 200, 0)},
            {new RGBImagePixel(0, 90, 255), new RGBImagePixel(255, 160, 0)}
    };
    originalImage.setImage(pixels);
    RGBImage compressedImage = (RGBImage) originalImage.compressImage(90);

    RGBImagePixel[][] actualPixels = compressedImage.getImage();
    System.out.println("Compressed Image Pixels:");

    RGBImagePixel[][] compressedPixels = {
            {new RGBImagePixel(177, 163, 163), new RGBImagePixel(0, 163, 0)},
            {new RGBImagePixel(0, 163, 163), new RGBImagePixel(177, 163, 0)}
    };

    assertPixelMatrixEquals(compressedPixels, actualPixels);
  }


  @Test
  public void testColorCorrection() {

    originalImage = new RGBImage();
    originalImage.setWidth(2);
    originalImage.setHeight(2);

    RGBImagePixel[][] pixels = {
            {new RGBImagePixel(255, 124, 70), new RGBImagePixel(0, 60, 0)},
            {new RGBImagePixel(0, 50, 26), new RGBImagePixel(54, 93, 0)}
    };

    originalImage.setImage(pixels);
    RGBImage correctedImage = (RGBImage) originalImage.colorCorrection();

    RGBImagePixel[][] correctedPixels = correctedImage.getImage();
    System.out.println("Color Corrected Image Pixels:");
    System.out.println(pixelMatrixToString(correctedPixels));

    RGBImagePixel[][] expectedCorrectedPixels = {
            {new RGBImagePixel(255, 90, 86), new RGBImagePixel(16, 26, 16)},
            {new RGBImagePixel(16, 16, 42), new RGBImagePixel(70, 59, 16)},
    };

    assertPixelMatrixEquals(expectedCorrectedPixels, correctedPixels);
  }

  @Test
  public void testColorCorrectionWithPercentage() {
    int adjustmentPercentage = 50;
    originalImage = new RGBImage();
    originalImage.setWidth(2);
    originalImage.setHeight(2);

    RGBImagePixel[][] pixels = {
            {new RGBImagePixel(255, 124, 70), new RGBImagePixel(0, 60, 0)},
            {new RGBImagePixel(0, 50, 26), new RGBImagePixel(54, 93, 0)}
    };

    originalImage.setImage(pixels);
    RGBImage colorAdjustedImage = (RGBImage) originalImage.colorCorrection(adjustmentPercentage);
    assertNotNull(colorAdjustedImage, "Color adjusted image should not be null");

    RGBImagePixel[][] actualPixels = colorAdjustedImage.getImage();
    System.out.println("Color Adjusted Image Pixels with "
            + adjustmentPercentage + "% adjustment:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(255, 90, 86), new RGBImagePixel(16, 26, 16)},
            {new RGBImagePixel(16, 16, 42), new RGBImagePixel(70, 59, 16)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }


  @Test
  public void testLevelAdjustment() {

    originalImage = new RGBImage();
    originalImage.setWidth(2);
    originalImage.setHeight(2);

    RGBImagePixel[][] pixels = {
            {new RGBImagePixel(255, 124, 70), new RGBImagePixel(0, 60, 0)},
            {new RGBImagePixel(0, 50, 26), new RGBImagePixel(54, 93, 0)}
    };

    originalImage.setImage(pixels);
    originalImage.setLevels(24, 67, 100);
    RGBImage correctedImage = (RGBImage) originalImage.adjustLevels();

    RGBImagePixel[][] correctedPixels = correctedImage.getImage();
    System.out.println("Levels Adjusted for Image Pixels:");
    System.out.println(pixelMatrixToString(correctedPixels));

    RGBImagePixel[][] expectedCorrectedPixels = {
            {new RGBImagePixel(255, 197, 90), new RGBImagePixel(0, 144, 0)},
            {new RGBImagePixel(0, 104, 8), new RGBImagePixel(120, 136, 0)},
    };

    assertPixelMatrixEquals(expectedCorrectedPixels, correctedPixels);
  }

  @Test
  public void testLevelAdjustmentWithPercentage() {

    originalImage = new RGBImage();
    originalImage.setWidth(2);
    originalImage.setHeight(2);

    RGBImagePixel[][] pixels = {
            {new RGBImagePixel(255, 124, 70), new RGBImagePixel(0, 60, 0)},
            {new RGBImagePixel(0, 50, 26), new RGBImagePixel(54, 93, 0)}
    };

    originalImage.setImage(pixels);
    originalImage.setLevels(24, 67, 100);

    int adjustmentPercentage = 50;
    RGBImage correctedImage = (RGBImage) originalImage.adjustLevels(adjustmentPercentage);

    RGBImagePixel[][] correctedPixels = correctedImage.getImage();
    System.out.println("Levels Adjusted with " + adjustmentPercentage + "% for Image Pixels:");
    System.out.println(pixelMatrixToString(correctedPixels));

    RGBImagePixel[][] expectedCorrectedPixels = {
            {new RGBImagePixel(255, 197, 90), new RGBImagePixel(0, 144, 0)},
            {new RGBImagePixel(0, 104, 8), new RGBImagePixel(120, 136, 0)}
    };

    assertPixelMatrixEquals(expectedCorrectedPixels, correctedPixels);
  }

  // New Test

  @Test
  public void testDownscale() {
    int newWidth = 1;
    int newHeight = 1;

    RGBImage downscaledImage = (RGBImage) originalImage.downscale(newWidth, newHeight);
    assertNotNull(downscaledImage, "Downscaled image should not be null");
    assertEquals(newWidth, downscaledImage.getWidth(),
            "Width should match the specified new width");
    assertEquals(newHeight, downscaledImage.getHeight(),
            "Height should match the specified new height");

    RGBImagePixel[][] actualPixels = downscaledImage.getImage();
    System.out.println("Downscaled Image Pixels:");
    System.out.println(pixelMatrixToString(actualPixels));

    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(255, 0, 0)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testDownscaleNew() {
    // original image with 3x3 dimensions
    RGBImagePixel[][] pixels = {
            {new RGBImagePixel(10, 20, 30), new RGBImagePixel(40, 50, 60),
            new RGBImagePixel(70, 80, 90)},
            {new RGBImagePixel(15, 25, 35), new RGBImagePixel(45, 55, 65),
            new RGBImagePixel(75, 85, 95)},
            {new RGBImagePixel(20, 30, 40), new RGBImagePixel(50, 60, 70),
            new RGBImagePixel(80, 90, 100)}
    };
    originalImage.setWidth(3);
    originalImage.setHeight(3);
    originalImage.setImage(pixels);

    RGBImage downscaledImage = (RGBImage) originalImage.downscale(2, 2);
    assertEquals(2, downscaledImage.getWidth(),
            "Width of downscaled image should be 2");
    assertEquals(2, downscaledImage.getHeight(),
            "Height of downscaled image should be 2");

    RGBImagePixel[][] expectedPixels = {
            {new RGBImagePixel(10, 20, 30), new RGBImagePixel(55, 65, 75)},
            {new RGBImagePixel(18, 28, 38), new RGBImagePixel(63, 73, 83)}
    };

    RGBImagePixel[][] actualPixels = downscaledImage.getImage();
    assertPixelMatrixEquals(expectedPixels, actualPixels);
  }


  @Test
  public void testDownscaleInvalidDimensions() {
    assertThrows(IllegalArgumentException.class,
        () -> originalImage.downscale(0, 1),
            "Width of zero should throw an exception");
    assertThrows(IllegalArgumentException.class,
        () -> originalImage.downscale(2, 3),
            "Height exceeding original dimensions should throw an exception");
    assertThrows(IllegalArgumentException.class,
        () -> originalImage.downscale(4, 4),
            "Height exceeding original dimensions should throw an exception");
  }

  @Test
  public void testDownscaleEdgeCase() {
    int newWidth = 1;
    int newHeight = 2;
    RGBImage downscaledImage = (RGBImage) originalImage.downscale(newWidth, newHeight);

    assertNotNull(downscaledImage, "Downscaled image should not be null");
    assertEquals(newWidth, downscaledImage.getWidth(), "Downscaled width should match");
    assertEquals(newHeight, downscaledImage.getHeight(), "Downscaled height should match");

    RGBImagePixel[][] actualPixels = downscaledImage.getImage();

    RGBImagePixel[][] expectedPixels = {
            {new RGBImagePixel(255, 0, 0)},
            {new RGBImagePixel(0, 0, 0)}
    };

    assertPixelMatrixEquals(expectedPixels, actualPixels);
  }

  @Test
  public void testApplyPartialGrayscaleWithMasking() {
    RGBImage maskImage = new RGBImage();
    maskImage.setWidth(2);
    maskImage.setHeight(2);

    RGBImagePixel[][] maskPixels = {
            {new RGBImagePixel(0, 0, 0), new RGBImagePixel(255, 255, 255)},
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(0, 0, 0)}
    };
    maskImage.setImage(maskPixels);

    String effectType = "grayscale";
    RGBImage partiallyMaskedImage = (RGBImage) originalImage.applyPartialWithMasking(maskImage,
            effectType);
    RGBImagePixel[][] actualPixels = partiallyMaskedImage.getImage();
    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(54, 54, 54), new RGBImagePixel(0, 255, 0)},
            {new RGBImagePixel(0, 0, 255), new RGBImagePixel(237, 237, 237)}
    };

    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testApplyPartialSepiaWithMasking() {
    RGBImage maskImage = new RGBImage();
    maskImage.setWidth(2);
    maskImage.setHeight(2);

    RGBImagePixel[][] maskPixels = {
            {new RGBImagePixel(0, 0, 0), new RGBImagePixel(255, 255, 255)},
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(0, 0, 0)}
    };
    maskImage.setImage(maskPixels);

    String effectType = "sepia";
    RGBImage partiallyMaskedImage = (RGBImage) originalImage.applyPartialWithMasking(maskImage,
            effectType);
    RGBImagePixel[][] actualPixels = partiallyMaskedImage.getImage();
    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(100, 89, 69), new RGBImagePixel(0, 255, 0)},
            {new RGBImagePixel(0, 0, 255), new RGBImagePixel(255, 255, 206)}
    };
    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testApplyPartialBlurWithMasking() {
    RGBImage maskImage = new RGBImage();
    maskImage.setWidth(2);
    maskImage.setHeight(2);

    RGBImagePixel[][] maskPixels = {
            {new RGBImagePixel(0, 0, 0), new RGBImagePixel(255, 255, 255)},
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(0, 0, 0)}
    };
    maskImage.setImage(maskPixels);

    String effectType = "blur";
    RGBImage partiallyMaskedImage = (RGBImage) originalImage.applyPartialWithMasking(maskImage,
            effectType);
    RGBImagePixel[][] actualPixels = partiallyMaskedImage.getImage();
    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(80, 48, 32), new RGBImagePixel(0, 255, 0)},
            {new RGBImagePixel(0, 0, 255), new RGBImagePixel(80, 96, 32)}
    };
    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testApplyPartialSharpenWithMasking() {
    RGBImage maskImage = new RGBImage();
    maskImage.setWidth(2);
    maskImage.setHeight(2);

    RGBImagePixel[][] maskPixels = {
            {new RGBImagePixel(0, 0, 0), new RGBImagePixel(255, 255, 255)},
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(0, 0, 0)}
    };
    maskImage.setImage(maskPixels);

    String effectType = "sharpen";
    RGBImage partiallyMaskedImage = (RGBImage) originalImage.applyPartialWithMasking(maskImage,
            effectType);
    RGBImagePixel[][] actualPixels = partiallyMaskedImage.getImage();
    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(255, 128, 64), new RGBImagePixel(0, 255, 0)},
            {new RGBImagePixel(0, 0, 255), new RGBImagePixel(255, 255, 64)}
    };
    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testApplyPartialRedComponentWithMasking() {
    RGBImage maskImage = new RGBImage();
    maskImage.setWidth(2);
    maskImage.setHeight(2);

    RGBImagePixel[][] maskPixels = {
            {new RGBImagePixel(0, 0, 0), new RGBImagePixel(255, 255, 255)},
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(0, 0, 0)}
    };
    maskImage.setImage(maskPixels);

    String effectType = "red-component";
    RGBImage partiallyMaskedImage = (RGBImage) originalImage.applyPartialWithMasking(maskImage,
            effectType);
    RGBImagePixel[][] actualPixels = partiallyMaskedImage.getImage();
    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(0, 255, 0)},
            {new RGBImagePixel(0, 0, 255), new RGBImagePixel(255, 255, 255)}
    };
    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testApplyPartialGreenComponentWithMasking() {
    RGBImage maskImage = new RGBImage();
    maskImage.setWidth(2);
    maskImage.setHeight(2);

    RGBImagePixel[][] maskPixels = {
            {new RGBImagePixel(0, 0, 0), new RGBImagePixel(255, 255, 255)},
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(0, 0, 0)}
    };
    maskImage.setImage(maskPixels);

    String effectType = "green-component";
    RGBImage partiallyMaskedImage = (RGBImage) originalImage.applyPartialWithMasking(maskImage,
            effectType);
    RGBImagePixel[][] actualPixels = partiallyMaskedImage.getImage();
    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(0, 0, 0), new RGBImagePixel(0, 255, 0)},
            {new RGBImagePixel(0, 0, 255), new RGBImagePixel(255, 255, 255)}
    };
    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testApplyPartialBlueComponentWithMasking() {
    RGBImage maskImage = new RGBImage();
    maskImage.setWidth(2);
    maskImage.setHeight(2);

    RGBImagePixel[][] maskPixels = {
            {new RGBImagePixel(0, 0, 0), new RGBImagePixel(255, 255, 255)},
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(0, 0, 0)}
    };
    maskImage.setImage(maskPixels);

    String effectType = "blue-component";
    RGBImage partiallyMaskedImage = (RGBImage) originalImage.applyPartialWithMasking(maskImage,
            effectType);
    RGBImagePixel[][] actualPixels = partiallyMaskedImage.getImage();
    RGBImagePixel[][] expectedImage = {
            {new RGBImagePixel(0, 0, 0), new RGBImagePixel(0, 255, 0)},
            {new RGBImagePixel(0, 0, 255), new RGBImagePixel(0, 0, 0)}
    };
    assertPixelMatrixEquals(expectedImage, actualPixels);
  }

  @Test
  public void testApplyPartialWithMaskingInvalidMaskDimensions() {

    RGBImage invalidMask = new RGBImage();
    invalidMask.setWidth(1);
    invalidMask.setHeight(1);
    invalidMask.setImage(new RGBImagePixel[][]{{new RGBImagePixel(0, 0, 0)}});

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> originalImage.applyPartialWithMasking(invalidMask, "blur"));
    assertEquals("Masking image dimensions must match the current image dimensions",
            exception.getMessage());
  }

  @Test
  public void testApplyPartialWithMaskingUnsupportedEffect() {

    RGBImage maskImage = new RGBImage();
    maskImage.setWidth(2);
    maskImage.setHeight(2);
    maskImage.setImage(new RGBImagePixel[][]{
            {new RGBImagePixel(0, 0, 0), new RGBImagePixel(255, 255, 255)},
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(0, 0, 0)}
    });

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> originalImage.applyPartialWithMasking(maskImage, "unknownEffect"));
    assertEquals("Unsupported effect: unknownEffect", exception.getMessage());
  }

  @Test
  public void testApplyPartialWithMaskingInvalidHeightAndWidth() {

    RGBImage maskImage = new RGBImage();
    maskImage.setWidth(10);
    maskImage.setHeight(10);
    maskImage.setImage(new RGBImagePixel[][]{
            {new RGBImagePixel(0, 0, 0), new RGBImagePixel(255, 255, 255)},
            {new RGBImagePixel(255, 255, 255), new RGBImagePixel(0, 0, 0)}
    });

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> originalImage.applyPartialWithMasking(maskImage, "sepia"));
    assertEquals("Masking image dimensions must match the current image dimensions",
            exception.getMessage());
  }


  private void assertPixelMatrixEquals(RGBImagePixel[][] expected, RGBImagePixel[][] actual) {

    assertEquals(expected.length, actual.length, "Image heights do not match");
    assertEquals(expected[0].length, actual[0].length, "Image widths do not match");

    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getRed(), actual[i][j].getRed(),
                "Red value at (" + i + ", " + j + ") does not match");
        assertEquals(expected[i][j].getGreen(), actual[i][j].getGreen(),
                "Green value at (" + i + ", " + j + ") does not match");
        assertEquals(expected[i][j].getBlue(), actual[i][j].getBlue(),
                "Blue value at (" + i + ", " + j + ") does not match");
      }
    }
  }
}

