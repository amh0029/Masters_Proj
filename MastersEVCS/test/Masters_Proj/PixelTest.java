/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Masters_Proj;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author allisonholt
 */
public class PixelTest {
    
    /*
    public PixelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    */

    /**
     * Testing the retrieval of the red concentration of a Pixel.
     */
    @Test public void testGetConcentrationRed() 
    {
        char color = 'r';
        Pixel pR = new Pixel(100, 200, 150);
        int expected = 100;
        int actual = pR.getConcentration(color);
        assertEquals("Error returning the red concentration of a Pixel",
                expected, actual);
    }
    
    /**
     * Testing the retrieval of the green concentration of a Pixel.
     */
    @Test public void testGetConcentrationGreen() 
    {
        char color = 'g';
        Pixel pG = new Pixel(100, 200, 150);
        int expected = 200;
        int actual = pG.getConcentration(color);
        assertEquals("Error returning the green concentration of a Pixel",
                expected, actual);
    }
    
    /**
     * Testing the retrieval of the blue concentration of a Pixel.
     */
    @Test public void testGetConcentrationBlue() 
    {
        char color = 'b';
        Pixel pB = new Pixel(100, 200, 150);
        int expected = 150;
        int actual = pB.getConcentration(color);
        assertEquals("Error returning the blue concentration of a Pixel",
                expected, actual);
    }
    
}
