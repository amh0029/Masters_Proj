/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Masters_Proj;

import java.awt.Color;

/**
 * The Pixel class is used by the ExtendedVCS class to help split the secret image
 * into three images based on each pixel's red, green, and blue values.
 * 
 * @author allisonholt
 * @version 03-23-2016
 */
public class Pixel 
{

   private int redVal;
   private int greenVal;
   private int blueVal;
   
   /**
    * Creates a new Pixel.
    * 
    * @param redIn The red level of the pixel
    * @param greenIn The green level of the pixel
    * @param blueIn The blue level of the pixel
    */
   public Pixel(int redIn, int greenIn, int blueIn)
   {
      redVal = redIn;
      greenVal = greenIn;
      blueVal = blueIn;
   }
   
   /**
    * Given the color code (character) the method returns the pixel's color
    * concentration for either the red, green, or blue level.
    * 
    * @param color The character that determines if the red, green, or blue
    * concentration is returned.
    * @return Returns the appropriate concentration (either red, green, or blue)
    */
   public int getConcentration(char color)
   {
      if(color == 'r')
         return redVal;
      else if(color == 'g')
         return greenVal;
      else
         return blueVal;
   }
    
}
