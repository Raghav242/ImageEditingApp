package mocktest;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * The {@code MockControllerTest} class is responsible for testing the functionality
 * and interactions of the controller in the image processing application.
 * This class uses mock objects to simulate the behavior of the model and view,
 * ensuring that the controller handles various scenarios correctly, including
 * user actions, error handling, and callbacks.
 * The tests verify that the controller properly mediates communication between
 * the model and view without performing actual image processing or UI operations.
 */
public class MockControllerTest {

  private StringBuilder log;
  private MockController mockController;

  @Before
  public void setup() {
    log = new StringBuilder();
    mockController = new MockController(log);
  }

  @Test
  public void testLoadImage() throws IOException {
    mockController.loadImage("res/input/uni.png", "testImage");
    assertTrue(log.toString()
            .contains("loadImage called with path: res/input/uni.png, imageName: testImage"));
  }

  @Test
  public void testSaveImage() throws IOException {
    mockController.saveImage("output/testImage.png");
    assertTrue(log.toString().contains("saveImage called with outputPath: output/testImage.png"));
  }

  @Test
  public void testBrighten() throws IOException {
    mockController.brighten("50");
    assertTrue(log.toString().contains("brighten called with increment: 50"));
  }

  @Test
  public void testBlurWithoutPercentage() throws IOException {
    mockController.blur();
    assertTrue(log.toString().contains("blur called with no percentage"));
  }

  @Test
  public void testBlurWithPercentage() throws IOException {
    mockController.blur(40);
    assertTrue(log.toString().contains("blur called with percentage: 40"));
  }

  @Test
  public void testSharpenWithoutPercentage() throws IOException {
    mockController.sharpen();
    assertTrue(log.toString().contains("sharpen called with no percentage"));
  }

  @Test
  public void testSharpenWithPercentage() throws IOException {
    mockController.sharpen(30);
    assertTrue(log.toString().contains("sharpen called with percentage: 30"));
  }

  @Test
  public void testSepiaWithoutPercentage() throws IOException {
    mockController.sepia();
    assertTrue(log.toString().contains("sepia called with no percentage"));
  }

  @Test
  public void testSepiaWithPercentage() throws IOException {
    mockController.sepia(20);
    assertTrue(log.toString().contains("sepia called with percentage: 20"));
  }

  @Test
  public void testLumaWithoutPercentage() throws IOException {
    mockController.luma();
    assertTrue(log.toString().contains("luma called with no percentage"));
  }

  @Test
  public void testLumaWithPercentage() throws IOException {
    mockController.luma(25);
    assertTrue(log.toString().contains("luma called with percentage: 25"));
  }

  @Test
  public void testValue() throws IOException {
    mockController.value();
    assertTrue(log.toString().contains("value called"));
  }

  @Test
  public void testIntensity() throws IOException {
    mockController.intensity();
    assertTrue(log.toString().contains("intensity called"));
  }

  @Test
  public void testHorizontalFlip() throws IOException {
    mockController.horizontalFlip();
    assertTrue(log.toString().contains("horizontalFlip called"));
  }

  @Test
  public void testVerticalFlip() throws IOException {
    mockController.verticalFlip();
    assertTrue(log.toString().contains("verticalFlip called"));
  }

  @Test
  public void testRedComponent() throws IOException {
    mockController.redComponent();
    assertTrue(log.toString().contains("redComponent called"));
  }

  @Test
  public void testGreenComponent() throws IOException {
    mockController.greenComponent();
    assertTrue(log.toString().contains("greenComponent called"));
  }

  @Test
  public void testBlueComponent() throws IOException {
    mockController.blueComponent();
    assertTrue(log.toString().contains("blueComponent called"));
  }

  @Test
  public void testColorCorrectWithoutPercentage() throws IOException {
    mockController.colorCorrect();
    assertTrue(log.toString().contains("colorCorrect called with no percentage"));
  }

  @Test
  public void testColorCorrectWithPercentage() throws IOException {
    mockController.colorCorrect(10);
    assertTrue(log.toString().contains("colorCorrect called with percentage: 10"));
  }

  @Test
  public void testAdjustLevelsWithoutPercentage() throws IOException {
    mockController.adjustLevels(10, 20, 30);
    assertTrue(log.toString().contains("adjustLevels called with black: 10, mid: 20, white: 30"));
  }

  @Test
  public void testAdjustLevelsWithPercentage() throws IOException {
    mockController.adjustLevels(5, 15, 25, 50);
    assertTrue(log.toString().contains(
            "adjustLevels called with black: 5, mid: 15, white: 25, percentage: 50"));
  }

  @Test
  public void testCompress() throws IOException {
    mockController.compress(60);
    assertTrue(log.toString().contains("compress called with percentage: 60"));
  }

  @Test
  public void testResetToOriginal() {
    mockController.resetToOriginal();
    assertTrue(log.toString().contains("resetToOriginal called"));
  }
}
