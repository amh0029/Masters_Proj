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
 * Test cases for the 
 * @author allisonholt
 */
public class ExtendedVCSTest {
    
    public ExtendedVCSTest() {
    }
    
    /*
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    */
    
    /**
     * Test of getImgWidth method, of class ExtendedVCS.
     */
    @Test
    public void testGetImgWidth() {
        System.out.println("getImgWidth");
        ExtendedVCS instance = null;
        int expResult = 0;
        int result = instance.getImgWidth();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getImgHeight method, of class ExtendedVCS.
     */
    @Test
    public void testGetImgHeight() {
        System.out.println("getImgHeight");
        ExtendedVCS instance = null;
        int expResult = 0;
        int result = instance.getImgHeight();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRGBPixelsForShares method, of class ExtendedVCS.
     */
    @Test
    public void testGetRGBPixelsForShares() {
        System.out.println("getRGBPixelsForShares");
        ExtendedVCS instance = null;
        int[][] expResult = null;
        int[][] result = instance.getRGBPixelsForShares();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDecryptImgPixels method, of class ExtendedVCS.
     */
    @Test
    public void testGetDecryptImgPixels() {
        System.out.println("getDecryptImgPixels");
        ExtendedVCS instance = null;
        int[] expResult = null;
        int[] result = instance.getDecryptImgPixels();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of encryptImage method, of class ExtendedVCS.
     */
    @Test
    public void testEncryptImage() {
        System.out.println("encryptImage");
        ExtendedVCS instance = null;
        instance.encryptImage();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decryptImage method, of class ExtendedVCS.
     */
    @Test
    public void testDecryptImage() {
        System.out.println("decryptImage");
        ExtendedVCS instance = null;
        instance.decryptImage();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decryptImageTransparencyMethod method, of class ExtendedVCS.
     */
    @Test
    public void testDecryptImageTransparencyMethod() {
        System.out.println("decryptImageTransparencyMethod");
        ExtendedVCS instance = null;
        instance.decryptImageTransparencyMethod();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
