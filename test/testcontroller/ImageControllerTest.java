package testcontroller;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import controller.ImageController;
import model.ImageModel;

import static org.junit.Assert.assertTrue;

/**
 * The {@code ImageControllerTest} class contains unit tests for the {@code ImageController} class.
 * It ensures that various image processing operations like loading, saving, and applying filters
 * are performed correctly by the controller.
 * Each test verifies specific functionalities of the {@code ImageController},
 * such as loading valid images, handling invalid commands, and verifying exceptional cases.
 */
public class ImageControllerTest {

  private ImageController controller;

  @Before
  public void setUp() {
    ImageModel model = new ImageModel();
    controller = new ImageController(model);
  }

  @Test
  public void testLoadValidImage() throws IOException {

    controller.executeCommand("load res/input/uni.png image1");
    File testFile = new File("res/input/uni.png");
    assertTrue(testFile.exists());
  }


  @Test
  public void testSaveImage() throws IOException {

    controller.executeCommand("load res/input/manhattan-original.png image1");
    controller.executeCommand("save res/manhattan-original.png image1");

    File savedFile = new File("res/manhattan-original.png");
    assertTrue("The saved image file should exist.", savedFile.exists());
  }


  @Test
  public void testBrightenImage() throws IOException {

    controller.executeCommand("load res/input/uni.png image1");
    controller.executeCommand("brighten 20 image1 brightenedImage");
    controller.executeCommand("save res/brightened.png brightenedImage");

    File compressedFile20 = new File("res/brightened.png");
    assertTrue(compressedFile20.exists());

  }

  @Test
  public void testApplySepia_NonExistentImage_PrintsErrorMessage() throws IOException {
    ImageController controller = new ImageController(new ImageModel());
    String nonExistentImageName = "nonExistentImage";

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    controller.executeCommand("sepia " + nonExistentImageName + " image1");

    String expectedMessage = "Error: Source image '" + nonExistentImageName + "' not found.";
    assertTrue(outContent.toString().contains(expectedMessage));

    System.setOut(System.out);
  }

  @Test
  public void testApplyGrayscale_NonExistentImage_PrintsErrorMessage() throws IOException {
    ImageController controller = new ImageController(new ImageModel());
    String nonExistentImageName = "nonExistentImage";

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    controller.executeCommand("grayscale " + nonExistentImageName + " outputImage");

    String expectedMessage = "Error: Source image '" + nonExistentImageName + "' not found.";
    assertTrue(outContent.toString().contains(expectedMessage));

    System.setOut(System.out);
  }

  @Test
  public void testApplyGreyScaleExistentImage() throws IOException {

    controller.executeCommand("load res/input/manhattan-original.png image1");
    controller.executeCommand("grayscale image1 grayscaleOutput");
    controller.executeCommand("save res/grayscaleOutput.jpg grayscaleOutput");
    File savedImage = new File("res/grayscaleOutput.jpg");
    assertTrue(savedImage.exists());

  }

  @Test
  public void testRgbSplit_NonExistentImage_PrintsErrorMessage() throws IOException {
    ImageController controller = new ImageController(new ImageModel());
    String nonExistentImageName = "nonExistentImage";

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    controller.executeCommand("rgb-split " + nonExistentImageName
        + " redImage greenImage blueImage");

    String expectedMessage = "Error: Source image '" + nonExistentImageName + "' not found.";
    assertTrue(outContent.toString().contains(expectedMessage));

    System.setOut(System.out);
  }

  @Test
  public void testRgbSplitValidImage() throws IOException {

    controller.executeCommand("load res/input/manhattan-original.png validImage");
    controller.executeCommand("rgb-split validImage redImage greenImage blueImage");

    controller.executeCommand("save res/split/redImage.jpg redImage");
    controller.executeCommand("save res/split/greenImage.jpg greenImage");
    controller.executeCommand("save res/split/blueImage.jpg blueImage");

    File redImageFile = new File("res/split/redImage.jpg");
    File greenImageFile = new File("res/split/greenImage.jpg");
    File blueImageFile = new File("res/split/blueImage.jpg");

    assertTrue(redImageFile.exists());
    assertTrue(greenImageFile.exists());
    assertTrue(blueImageFile.exists());


  }

  @Test
  public void testRgbCombine_MissingComponent_PrintsErrorMessage() throws IOException {
    ImageController controller = new ImageController(new ImageModel());

    controller.executeCommand("load res/split/redImage.jpg redImage");
    controller.executeCommand("load res/split/greenImage.jpg greenImage");


    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    controller.executeCommand("rgb-combine validImage redImage greenImage missedBlueImage");

    String expectedMessage = "Error: Source image not found.";
    assertTrue(outContent.toString().contains(expectedMessage));

    System.setOut(System.out);
  }

  @Test
  public void testMultipleFilters() throws IOException {

    controller.executeCommand("load res/input/manhattan-original.png image1");
    controller.executeCommand("sepia image1 sepiaImage");
    controller.executeCommand("blur sepiaImage blurredImage");
    controller.executeCommand("save res/blurredSepiaImage.png blurredImage");

    File savedFile = new File("res/blurredSepiaImage.png");
    assertTrue(savedFile.exists());

  }

  @Test
  public void testRgbCombineValidComponents() throws IOException {
    controller.executeCommand("load res/split/redImage.jpg redImage");
    controller.executeCommand("load res/split/greenImage.jpg greenImage");
    controller.executeCommand("load res/split/blueImage.jpg blueImage");

    controller.executeCommand("rgb-combine combinedImage redImage greenImage blueImage");
    controller.executeCommand("save res/combined.png combinedImage");
    File savedFile = new File("res/combined.png");
    assertTrue(savedFile.exists());

  }

  @Test
  public void testSaveAfterFilter() throws IOException {
    controller.executeCommand("load res/input/manhattan-original.png image1");
    controller.executeCommand("sepia image1 sepiaImage");
    controller.executeCommand("save res/sepiaImage.png sepiaImage");

    File savedFile = new File("res/sepiaImage.png");
    assertTrue(savedFile.exists());
  }


  @Test
  public void testSharpenImage() throws IOException {
    controller.executeCommand("load res/input/manhattan-original.png tobeSharpened");
    controller.executeCommand("sharpen tobeSharpened sharpenedImage");
    controller.executeCommand("save res/sharpenedOutput.jpg sharpenedImage");
    File savedFile = new File("res/sharpenedOutput.jpg");
    assertTrue(savedFile.exists());
  }

  @Test
  public void testValueComponent() throws IOException {
    controller.executeCommand("load res/input/manhattan-original.png toBeValuedImage");
    controller.executeCommand("value-component toBeValuedImage valueComponentImage");
    controller.executeCommand("save res/valueComponentOutput.jpg valueComponentImage");
    File savedFile = new File("res/valueComponentOutput.jpg");
    assertTrue(savedFile.exists());
  }

  // Intensity check
  @Test
  public void testIntensityComponent() throws IOException {
    controller.executeCommand("load res/input/manhattan-original.png intensityInput");
    controller.executeCommand("intensity-component intensityInput intensityImage");
    controller.executeCommand("save res/intensityOutput.jpg intensityImage");
    File savedFile = new File("res/intensityOutput.jpg");
    assertTrue(savedFile.exists());
  }

  @Test
  public void testHorizontalFlip() throws IOException {
    controller.executeCommand("load res/input/manhattan-original.png makehorizontal");
    controller.executeCommand("horizontal-flip makehorizontal flippedHorizontalImage");
    controller.executeCommand("save res/flippedHorizontalOutput.jpg flippedHorizontalImage");
    File savedFile = new File("res/flippedHorizontalOutput.jpg");
    assertTrue(savedFile.exists());
  }

  @Test
  public void testRedComponent() throws IOException {

    controller.executeCommand("load res/input/manhattan-original.png tobeMadeRed");
    controller.executeCommand("red-component tobeMadeRed redImage");
    controller.executeCommand("save res/redOutput.jpg redImage");
    File savedFile = new File("res/redOutput.jpg");
    assertTrue(savedFile.exists());
  }

  @Test
  public void testGreenComponent() throws IOException {

    controller.executeCommand("load res/input/manhattan-original.png tobeMadeGreen");
    controller.executeCommand("green-component tobeMadeGreen greenImage");
    controller.executeCommand("save res/greenOutput.jpg greenImage");
    File savedFile = new File("res/greenOutput.jpg");
    assertTrue(savedFile.exists());
  }

  @Test
  public void testBlueComponent() throws IOException {

    controller.executeCommand("load res/input/manhattan-original.png toBlue");
    controller.executeCommand("blue-component toBlue blueImage");
    controller.executeCommand("save res/blueOutput.jpg blueImage");
    File savedFile = new File("res/blueOutput.jpg");
    assertTrue(savedFile.exists());
  }


  @Test
  public void testConvertToGrayScaleWithSplit() throws IOException {

    controller.executeCommand("load res/input/manhattan-original.png image1");
    controller.executeCommand("compress 50 image1 compressedImage");
    controller.executeCommand("save res/compressedres.jpg compressedImage");

    File grayFile = new File("res/compressedres.jpg");
    assertTrue(grayFile.exists());
  }

  @Test
  public void testConvertToSepiaWithSplit() throws IOException {

    controller.executeCommand("load res/input/manhattan-original.png image1");
    controller.executeCommand("sepia image1 sepiaImage split 50");
    controller.executeCommand("save res/sepia50.png sepiaImage");

    File sepiaFile = new File("res/sepia50.png");
    assertTrue(sepiaFile.exists());
  }

  @Test
  public void testBlurImageSplit50() throws IOException {
    ImageModel model = new ImageModel();
    ImageController controller = new ImageController(model);
    controller.loadImage("res/input/manhattan-original.png", "image1");
    controller.executeCommand("blur image1 blurredImage split 50");
    controller.executeCommand("save res/blurred50.png blurredImage");

    File blurredFile = new File("res/blurred50.png");
    assertTrue(blurredFile.exists());
  }

  @Test
  public void testSharpenImageSplit50() throws IOException {

    controller.loadImage("res/input/manhattan-original.png", "image1");
    controller.executeCommand("sharpen image1 sharpenedImage split 50");
    controller.executeCommand("save res/sharpened50.png sharpenedImage");

    File sharpenedFile = new File("res/sharpened50.png");
    assertTrue(sharpenedFile.exists());
  }

  @Test
  public void testCompressImage() throws IOException {

    controller.loadImage("res/input/manhattan-original.png", "image1");
    controller.executeCommand("compress 90 image1 compressedImage");
    controller.executeCommand("save res/compress-1.png compressedImage");

    File compressedFile = new File("res/compress-1.png");
    assertTrue(compressedFile.exists());
  }

  @Test
  public void testLevelAdjustmentWithPercentage() throws IOException {

    controller.loadImage("res/input/manhattan-original.png", "image1");
    controller.executeCommand("levels-adjust 200 200 205 image1 split 50");
    controller.executeCommand("save res/compress-1.png compressedImage");

    File compressedFile = new File("res/compress-1.png");
    assertTrue(compressedFile.exists());
  }

  @Test
  public void testLevelAdjustment() throws IOException {

    controller.loadImage("res/input/manhattan-original.png", "image1");
    controller.executeCommand("levels-adjust 100 100 105 image1 levelAdjust");
    controller.executeCommand("save res/compress-1.png compressedImage");

    File compressedFile = new File("res/compress-1.png");
    assertTrue(compressedFile.exists());
  }

  @Test
  public void testColorCorrection() throws IOException {

    controller.loadImage("res/input/manhattan-original.png", "image1");
    controller.executeCommand("color-correct image1 splitColorCorrect");
    controller.executeCommand("save res/compress-1.png compressedImage");

    File compressedFile = new File("res/compress-1.png");
    assertTrue(compressedFile.exists());
  }

  @Test
  public void testColorCorrectionWithPercentage() throws IOException {

    controller.loadImage("res/input/manhattan-original.png", "image1");
    controller.executeCommand("color-correct image1 splitColorCorrect split 50");
    controller.executeCommand("save res/compress-1.png compressedImage");

    File compressedFile = new File("res/compress-1.png");
    assertTrue(compressedFile.exists());
  }

  @Test
  public void testCompressImage10() throws IOException {

    controller.executeCommand("load res/input/manhattan-original.png image1");
    controller.executeCommand("compress 10 image1 compressedImage");
    controller.executeCommand("save res/compress20.png compressedImage");

    File compressedFile20 = new File("res/compress20.png");
    assertTrue(compressedFile20.exists());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCompressPecentage() throws IOException {

    controller.executeCommand("load res/input/manhattan-original.png image1");
    controller.executeCommand("compress -10 image1 compressedImage");
    controller.executeCommand("save res/compress20.png compressedImage");

    File compressedFile20 = new File("res/compress20.png");
    assertTrue(compressedFile20.exists());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPecentageForSepia() throws IOException {

    controller.executeCommand("load res/input/manhattan-original.png image1");
    controller.executeCommand("sepia image1 compressedImage split -400");
    controller.executeCommand("save res/compress20.png compressedImage");

    File compressedFile20 = new File("res/compress20.png");
    assertTrue(compressedFile20.exists());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPecentageForBlur() throws IOException {

    controller.executeCommand("load res/input/manhattan-original.png image1");
    controller.executeCommand("blur image1 compressedImage split -400");
    controller.executeCommand("save res/compress20.png compressedImage");

    File compressedFile20 = new File("res/compress20.png");
    assertTrue(compressedFile20.exists());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPecentageForColorCorrection() throws IOException {

    controller.executeCommand("load res/input/manhattan-original.png image1");
    controller.executeCommand("color-correct image1 colorCorrect split -10");
    controller.executeCommand("save res/colorCorrect.png colorCorrect");

    File compressedFile20 = new File("res/compress20.png");
    assertTrue(compressedFile20.exists());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPecentageForLevelAdjustment() throws IOException {

    controller.executeCommand("load res/input/manhattan-original.png image1");
    controller.executeCommand("levels-adjust 100 100 105 image1 levelAdjust split -70");
    controller.executeCommand("save res/colorCorrect.png colorCorrect");

    File compressedFile20 = new File("res/compress20.png");
    assertTrue(compressedFile20.exists());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPecentageForSharpen() throws IOException {

    controller.executeCommand("load res/input/manhattan-original.png image1");
    controller.executeCommand("sharpen image1 splitSharpenedImage split -30");
    controller.executeCommand("save res/colorCorrect.png colorCorrect");

    File compressedFile20 = new File("res/compress20.png");
    assertTrue(compressedFile20.exists());
  }


  @Test
  public void testAdjustLevels_SourceImageNotFound_PrintsErrorMessage() throws IOException {

    ImageController controller = new ImageController(new ImageModel());
    String nonExistentImageName = "nonExistentImage";

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    controller.executeCommand("levels-adjust 200 200 205 nonExistentImage split 50");

    String expectedMessage = "Error: Source image '" + nonExistentImageName + "' not found.";
    assertTrue(outContent.toString().contains(expectedMessage));

    System.setOut(System.out);
  }

  @Test
  public void testExecuteCommand_InvalidCommand_PrintsErrorMessage() throws IOException {
    ImageController controller = new ImageController(new ImageModel());

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    String invalidCommand = "invalidCommand";
    controller.executeCommand(invalidCommand);

    // Expected output for an unrecognized command.
    String expectedMessage = "Invalid Command ";
    assertTrue(outContent.toString().contains(expectedMessage));

    // Restore standard output.
    System.setOut(System.out);
  }

  @Test
  public void testSaveCommand_NonExistentImage_PrintsErrorMessage() throws IOException {

    ImageController controller = new ImageController(new ImageModel());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    controller.executeCommand("save res/output/nonExistentImage.png nonExistentImage");

    String expectedMessage = "Error: Image with name 'nonExistentImage' does not exist.";
    assertTrue(outContent.toString().contains(expectedMessage));

    System.setOut(System.out);
  }


  // New Tests

  @Test
  public void testPartialBlurOperation() throws IOException {
    controller.executeCommand("load res/input/uni.png image1");
    controller.executeCommand("load res/input/uni-modified.png maskedImage");
    controller.executeCommand("blur image1 maskedImage transformedImage");
    controller.executeCommand("save res/partial/partialBlur.png transformedImage");

    File blurredFile = new File("res/partial/partialBlur.png");
    assertTrue(blurredFile.exists());
  }

  @Test
  public void testPartialSharpenOperation() throws IOException {
    controller.executeCommand("load res/input/uni.png image1");
    controller.executeCommand("load res/input/uni-modified.png maskedImage");
    controller.executeCommand("sharpen image1 maskedImage transformedImage");
    controller.executeCommand("save res/partial/partialSharpen.png transformedImage");

    File sharpenedFile = new File("res/partial/partialSharpen.png");
    assertTrue(sharpenedFile.exists());
  }

  @Test
  public void testPartialSepiaOperation() throws IOException {
    controller.executeCommand("load res/input/uni.png image1");
    controller.executeCommand("load res/input/uni-modified.png maskedImage");
    controller.executeCommand("sepia image1 maskedImage transformedImage");
    controller.executeCommand("save res/partial/partialSepia.png transformedImage");

    File sepiaFile = new File("res/partial/partialSepia.png");
    assertTrue(sepiaFile.exists());
  }

  @Test
  public void testPartialGrayscaleOperation() throws IOException {
    controller.executeCommand("load res/input/uni.png image1");
    controller.executeCommand("load res/input/uni-modified.png maskedImage");
    controller.executeCommand("grayscale image1 maskedImage transformedImage");
    controller.executeCommand("save res/partial/partialGray.png transformedImage");

    File grayFile = new File("res/partial/partialGray.png");
    assertTrue(grayFile.exists());
  }

  @Test
  public void testPartialRedComponentOperation() throws IOException {
    controller.executeCommand("load res/input/uni.png image1");
    controller.executeCommand("load res/input/uni-modified.png maskedImage");
    controller.executeCommand("red-component image1 maskedImage transformedImage");
    controller.executeCommand("save res/partial/partialRed.png transformedImage");

    File redComponentFile = new File("res/partial/partialRed.png");
    assertTrue(redComponentFile.exists());
  }

  @Test
  public void testPartialGreenComponentOperation() throws IOException {
    controller.executeCommand("load res/input/uni.png image1");
    controller.executeCommand("load res/input/uni-modified.png maskedImage");
    controller.executeCommand("green-component image1 maskedImage transformedImage");
    controller.executeCommand("save res/partial/partialGreen.png transformedImage");

    File greenComponentFile = new File("res/partial/partialGreen.png");
    assertTrue(greenComponentFile.exists());
  }

  @Test
  public void testPartialBlueComponentOperation() throws IOException {
    controller.executeCommand("load res/input/uni.png image1");
    controller.executeCommand("load res/input/uni-modified.png maskedImage");
    controller.executeCommand("blue-component image1 maskedImage transformedImage");
    controller.executeCommand("save res/partial/partialBlue.png transformedImage");

    File blueComponentFile = new File("res/partial/partialBlue.png");
    assertTrue(blueComponentFile.exists());
  }




}
