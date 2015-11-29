/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Masters_Proj;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author allisonholt
 */
public class ExtendedVCS 
{
    private int k;
   private int n;
   private int imgWidth;
   private int imgHeight;
   private int numColumns;
   private BufferedImage secretMsg;
   private BufferedImage[] innocentShares;
   //private int[2][] shareOrigRGBPixels;
   private int[][] encryptedShareRGB;
   private int[][] secretSharesRGB;
   
   private int numSharesToDecrypt;
   private BufferedImage[] sharesToDecrypt;
   private int[] secretMsgPixels;
   
   
   //For encryption purposes
   ExtendedVCS(BufferedImage secretMsgIn, BufferedImage[] innocentSharesIn)
   {
      k = 2;
      n = 2;
      secretMsg = secretMsgIn;
      imgWidth = secretMsg.getWidth();
      imgHeight = secretMsg.getHeight();
      innocentShares = innocentSharesIn;
   }
   
   //For decryption purposes
   ExtendedVCS(BufferedImage[] shareImgs)
   {
      numSharesToDecrypt = 2;
      sharesToDecrypt = shareImgs;
      imgWidth = shareImgs[0].getWidth();
      imgHeight = shareImgs[0].getHeight();
   }
   
   int getImgWidth()
   {
      return imgWidth;
   }
   
   int getImgHeight()
   {
      return imgHeight;
   }
   
   int[][] getRGBPixelsForShares()
   {
      return encryptedShareRGB;
   }
   
   int[] getDecryptImgPixels()
   {
      return secretMsgPixels;
   }
   
   void encryptImage()
   {
      int[] secretRGB = new int[imgWidth * imgHeight];
      int[][] shareOrigRGB = new int[2][imgWidth * imgHeight];
      secretMsg.getRGB(0, 0, imgWidth, imgHeight, secretRGB, 0, imgWidth);
      innocentShares[0].getRGB(0, 0, imgWidth, imgHeight, shareOrigRGB[0], 0, imgWidth);
      innocentShares[1].getRGB(0, 0, imgWidth, imgHeight, shareOrigRGB[1], 0, imgWidth);
      createPixelsOfShares(secretRGB, shareOrigRGB);
   }
   
   /**
    * 
    * @param secretImgRGB The RGB values of the secret image
    * @param shareOriginalRGB The RGB values of the innocent images
    */
   void createPixelsOfShares(int[] secretImgRGB, int[][] shareOriginalRGB)
   {
      //Used to store the embedded RGB values
       encryptedShareRGB = new int[2][imgWidth * imgHeight];
       
       //Used to bring the secret image up using a size invarint-ish technique
       secretSharesRGB = new int[2][imgWidth * imgHeight];
      
      for(int i = 0; i < secretImgRGB.length; i++)
      {
         int redVal = (secretImgRGB[i] & 0x00ff0000) >> 16;
         int greenVal = (secretImgRGB[i] & 0x0000ff00) >> 8;
         int blueVal = (secretImgRGB[i] & 0x000000ff);
         Pixel orig = new Pixel(redVal, greenVal, blueVal);
         
         redVal = (shareOriginalRGB[0][i] & 0x00ff0000) >> 16;
         greenVal = (shareOriginalRGB[0][i] & 0x0000ff00) >> 8;
         blueVal = (shareOriginalRGB[0][i] & 0x000000ff);
         Pixel innocent0 = new Pixel(redVal, greenVal, blueVal);
         
         redVal = (shareOriginalRGB[1][i] & 0x00ff0000) >> 16;
         greenVal = (shareOriginalRGB[1][i] & 0x0000ff00) >> 8;
         blueVal = (shareOriginalRGB[1][i] & 0x000000ff);
         Pixel innocent1 = new Pixel(redVal, greenVal, blueVal);
         
         Random randomGen = new Random();
         int maxGrayCon = orig.getConcentration();
         int grayCon1 = randomGen.nextInt(maxGrayCon + 1);
         int grayCon2 = maxGrayCon - grayCon1;
         
         Color secretGray1 = new Color(grayCon1, grayCon1, grayCon1);
         secretSharesRGB[0][i] = secretGray1.getRGB();
         
         Color secretGray2 = new Color(grayCon2, grayCon2, grayCon2);
         secretSharesRGB[1][i] = secretGray2.getRGB();
         
         int innocent1Con = innocent0.getConcentration();
         int embedded1Con = innocent1Con + grayCon1;
         if(embedded1Con > 255)
             embedded1Con = 255;
         Color embedded1 = new Color(embedded1Con, embedded1Con, embedded1Con);
         encryptedShareRGB[0][i] = embedded1.getRGB();
         
         int innocent2Con = innocent1.getConcentration();
         int embedded2Con = innocent2Con + grayCon2;
         if(embedded2Con > 255)
             embedded2Con = 255;
         Color embedded2 = new Color(embedded2Con, embedded2Con, embedded2Con);
         encryptedShareRGB[1][i] = embedded2.getRGB();
      }
   }
   
   void decryptImage()
   {
      //Make a 2d array of pixel arrays
      int[][] embeddedPixels = new int[numSharesToDecrypt][imgWidth * imgHeight];
      secretMsgPixels = new int[imgWidth * imgHeight];
                  
      //getRGB pixels of BufferedImages
      for(int i = 0; i < numSharesToDecrypt; i++)
      {
         sharesToDecrypt[i].getRGB(0, 0, imgWidth, imgHeight, embeddedPixels[i], 0, imgWidth);
      }
                  
      //Logical OR pixel with all three share values
      int numOfPixels = embeddedPixels[0].length;
      for(int i = 0; i < numOfPixels; i++)
      {
         int redVal = (embeddedPixels[0][i] & 0x00ff0000) >> 16;
         int greenVal = (embeddedPixels[0][i] & 0x0000ff00) >> 8;
         int blueVal = (embeddedPixels[0][i] & 0x000000ff);
         Pixel embedded1 = new Pixel(redVal, greenVal, blueVal);
         int share1Con = embedded1.getConcentration();
         
         redVal = (embeddedPixels[1][i] & 0x00ff0000) >> 16;
         greenVal = (embeddedPixels[1][i] & 0x0000ff00) >> 8;
         blueVal = (embeddedPixels[1][i] & 0x000000ff);
         Pixel embedded2 = new Pixel(redVal, greenVal, blueVal);
         int share2Con = embedded2.getConcentration();
         
         int totalCon = share1Con + share2Con;
         if(totalCon > 255)
             totalCon = 255;
         
         Color decryptedColor = new Color(totalCon, totalCon, totalCon);
         secretMsgPixels[i] = decryptedColor.getRGB();
      }
   }

}
