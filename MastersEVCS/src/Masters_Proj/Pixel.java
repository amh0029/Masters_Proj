/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Masters_Proj;
import java.awt.Color;

/**
 *
 * @author allisonholt
 */
public class Pixel 
{

   private int redVal;
   private int greenVal;
   private int blueVal;
   
   public Pixel(int redIn, int greenIn, int blueIn)
   {
      redVal = redIn;
      greenVal = greenIn;
      blueVal = blueIn;
   }
   
   //Used to determine if pixel is closer to white than black
   public int getConcentration()
   {
      return redVal;
   }
    
}
