/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Masters_Proj;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the methods that do not require randomness in the ExtendedVCS class.
 * 
 * @author allisonholt
 * @version 03-23-2016
 */
public class ExtendedVCSTest 
{

    /*
    Some methods were not tested because they are either private helper methods
    or the methods involve generating a random number.
    */
    
    /**
     * Testing the retrieval of the image width.
     * 
     * @throws IOException Occurs when there is an error reading the file
     */
    @Test public void testGetImgWidth() throws IOException
    {
        BufferedImage secretImage = ImageIO.read(this.getClass().getResource("blackPixel.png"));
        BufferedImage[] innocents = new BufferedImage[2];
        innocents[0] = ImageIO.read(this.getClass().getResource("redPixel.png"));
        innocents[1] = ImageIO.read(this.getClass().getResource("greenPixel.png"));
        
        ExtendedVCS evcs = new ExtendedVCS(secretImage, innocents);
        int expected = 1;
        int actual = evcs.getImgWidth();
        assertEquals("The ExtendedVCS class did not return the correct image width"
                + "The test expected 1 but actually got " + actual, 
                expected, actual);
    }

    /**
     * Testing the retrieval of the image height.
     * 
     * @throws IOException Occurs when there is an error reading the file
     */
    @Test public void testGetImgHeight() throws IOException
    {
        BufferedImage secretImage = ImageIO.read(this.getClass().getResource("blackPixel.png"));
        BufferedImage[] innocents = new BufferedImage[2];
        innocents[0] = ImageIO.read(this.getClass().getResource("redPixel.png"));
        innocents[1] = ImageIO.read(this.getClass().getResource("greenPixel.png"));
        
        ExtendedVCS evcs = new ExtendedVCS(secretImage, innocents);
        int expected = 1;
        int actual = evcs.getImgHeight();
        assertEquals("The ExtendedVCS class did not return the correct image width"
                + "The test expected 1 but actually got " + actual, 
                expected, actual);
    }

    /**
     * Testing the retrieval of the encoded share pixels.  By default they should
     * be set to zero.
     * 
     * @throws IOException Occurs when there is an error reading the file
     */
    @Test public void testGetRGBPixelsForShares() throws IOException
    {
        BufferedImage secretImage = ImageIO.read(this.getClass().getResource("blackPixel.png"));
        BufferedImage[] innocents = new BufferedImage[2];
        innocents[0] = ImageIO.read(this.getClass().getResource("redPixel.png"));
        innocents[1] = ImageIO.read(this.getClass().getResource("greenPixel.png"));
        
        ExtendedVCS evcs = new ExtendedVCS(secretImage, innocents);
        int[][] expected = new int[2][1];
        int[][] actual = evcs.getRGBPixelsForShares();
        boolean passed = (expected[0][0] == actual[0][0]) 
                && (expected[1][0] == actual[1][0]);
        assertEquals("The ExtendedVCS class did not return the correct integer"
                + "array for the encoded shares", 
                true, passed);
    }

    /**
     * Testing the retrieval of the decoded pixels.
     * 
     * @throws IOException Occurs when there is an error reading the file
     */
    @Test public void testGetDecryptImgPixels() throws IOException
    {
        BufferedImage[] encoded = new BufferedImage[2];
        encoded[0] = ImageIO.read(this.getClass().getResource("redPixel.png"));
        encoded[1] = ImageIO.read(this.getClass().getResource("greenPixel.png"));
        
        ExtendedVCS evcs = new ExtendedVCS(encoded);
        int[] expected = new int[1];
        int[] actual = evcs.getDecryptImgPixels();
        boolean passed = (expected[0] == actual[0]);
        assertEquals("The ExtendedVCS class did not return the correct integer"
                + "array for the decoded image pixels", 
                true, passed);
    }
    
}
