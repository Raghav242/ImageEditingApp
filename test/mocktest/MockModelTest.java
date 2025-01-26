package mocktest;

import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * The {@code MockModelTest} class is responsible for testing the behavior and functionality
 * of the {@link MockModel}, a mock implementation of the IModel interface.
 * This test class ensures that the mock model behaves as expected during controller and view
 * interactions, verifying that it returns appropriate responses or exceptions based on different
 * test scenarios. It is used to validate the controller and view logic in isolation from the
 * actual model implementation.
 */
public class MockModelTest {

  private MockModel mockModel;
  private Map<String, BufferedImage> images;

  @Before
  public void setUp() {
    images = new HashMap<>();
    mockModel = new MockModel(images);
  }

  @Test
  public void testLoadImage() throws IOException {
    BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    mockModel.loadImage(image, "testImage");
    assertEquals("loadImage", mockModel.getLastOperation());
    assertEquals("testImage", mockModel.getSourceImageName());
  }

  @Test
  public void testGetImage() throws IOException {
    BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    images.put("testImage", image);
    BufferedImage retrievedImage = mockModel.getImage("testImage");
    assertEquals("getImage", mockModel.getLastOperation());
    assertEquals("testImage", mockModel.getSourceImageName());
    assertNotNull(retrievedImage);
  }

  @Test
  public void testBrightenImage() {
    mockModel.brightenImage("50", "testImage", "brightenedImage");
    assertEquals("brightenImage", mockModel.getLastOperation());
    assertEquals("testImage", mockModel.getSourceImageName());
    assertEquals("brightenedImage", mockModel.getTargetImageName());
    assertEquals(50, mockModel.getEnteredPercentage());
  }

  @Test
  public void testApplyFilter() {
    mockModel.applyFilter("blur", "testImage", "blurredImage", 50);
    assertEquals("applyFilter", mockModel.getLastOperation());
    assertEquals("testImage", mockModel.getSourceImageName());
    assertEquals("blurredImage", mockModel.getTargetImageName());
    assertEquals(50, mockModel.getEnteredPercentage());
  }

  @Test
  public void testCreateHistogram() throws IOException {
    mockModel.createHistogram("testImage", "histogramImage");
    assertEquals("createHistogram", mockModel.getLastOperation());
    assertEquals("testImage", mockModel.getSourceImageName());
    assertEquals("histogramImage", mockModel.getTargetImageName());
    assertNotNull(images.get("histogramImage"));
  }

  @Test
  public void testColorCorrection() {
    mockModel.colorCorrection("testImage", "correctedImage", 30);
    assertEquals("colorCorrection", mockModel.getLastOperation());
    assertEquals("testImage", mockModel.getSourceImageName());
    assertEquals("correctedImage", mockModel.getTargetImageName());
    assertEquals(30, mockModel.getEnteredPercentage());
  }

  @Test
  public void testAdjustLevels() {
    mockModel.adjustLevels(10, 20, 30, "testImage", "adjustedImage", 40);
    assertEquals("adjustLevels", mockModel.getLastOperation());
    assertEquals("testImage", mockModel.getSourceImageName());
    assertEquals("adjustedImage", mockModel.getTargetImageName());
    assertEquals(40, mockModel.getEnteredPercentage());
  }

  @Test
  public void testRgbCombine() {
    mockModel.rgbCombine("combinedImage", "redImage", "greenImage", "blueImage");
    assertEquals("rgbCombine", mockModel.getLastOperation());
    assertEquals("combinedImage", mockModel.getTargetImageName());
  }

  @Test
  public void testRgbSplit() {
    mockModel.rgbSplit("testImage", "redImage", "greenImage", "blueImage");
    assertEquals("rgbSplit", mockModel.getLastOperation());
    assertEquals("testImage", mockModel.getSourceImageName());
  }

  @Test
  public void testCompress() {
    mockModel.compress(50, "testImage", "compressedImage");
    assertEquals("compress", mockModel.getLastOperation());
    assertEquals("testImage", mockModel.getSourceImageName());
    assertEquals("compressedImage", mockModel.getTargetImageName());
    assertEquals(50, mockModel.getEnteredPercentage());
  }

  @Test
  public void testDownscale() {
    mockModel.downscale("testImage", "downscaledImage", 200, 200);
    assertEquals("downscale", mockModel.getLastOperation());
    assertEquals("testImage", mockModel.getSourceImageName());
    assertEquals("downscaledImage", mockModel.getTargetImageName());
  }

  @Test
  public void testApplyPartialTransformation() {
    mockModel.applyPartialTransformation("blur", "testImage", "maskImage", "transformedImage");
    assertEquals("applyPartialTransformation", mockModel.getLastOperation());
    assertEquals("testImage", mockModel.getSourceImageName());
    assertEquals("transformedImage", mockModel.getTargetImageName());
  }

  @Test
  public void testResetToOriginal() {
    BufferedImage originalImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    images.put("originalImage", originalImage);
    mockModel.resetToOriginal("imageToReset", "originalImage");
    assertEquals("resetToOriginal", mockModel.getLastOperation());
    assertEquals("originalImage", mockModel.getSourceImageName());
    assertEquals("imageToReset", mockModel.getTargetImageName());
    assertEquals(originalImage, images.get("imageToReset"));
  }
}
