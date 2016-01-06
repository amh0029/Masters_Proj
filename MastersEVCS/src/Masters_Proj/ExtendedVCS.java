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
      //A cover image is the same as an innocent image
      int[][] coverRGB = new int[2][imgWidth * imgHeight];
      
      //Process the gathered innocent images and the secret image
      secretMsg.getRGB(0, 0, imgWidth, imgHeight, secretRGB, 0, imgWidth);
      innocentShares[0].getRGB(0, 0, imgWidth, imgHeight, coverRGB[0], 0, imgWidth);
      innocentShares[1].getRGB(0, 0, imgWidth, imgHeight, coverRGB[1], 0, imgWidth);
      
      //Half-tone Innocent Images
      errorDiffusion(coverRGB[0]);
      errorDiffusion(coverRGB[1]);
      
      //Split secret image into three images
      int[] secretRed = new int[secretRGB.length];
      int[] secretGreen = new int[secretRGB.length];
      int[] secretBlue = new int[secretRGB.length];
      splitSecretRGB(secretRGB, secretRed, secretGreen, secretBlue);
      
      //VIP synchronization
      vipSynchronization(secretRed, secretGreen, secretBlue, coverRGB);
      
      //Perform error diffusion on cover images with secret encoded
      errorDiffusion(encryptedShareRGB[0]);
      errorDiffusion(encryptedShareRGB[1]);
      
      //OLD WAY:  createPixelsOfShares(secretRGB, coverRGB);
   }
   
   void errorDiffusion(int[] image)
   {
       
   }
   
   void splitSecretRGB(int[] secret, int[] red, int[] green, int[] blue)
   {
       for(int i = 0; i < secret.length; i++)
       {
         int redVal = (secret[i] & 0x00ff0000) >> 16;
         int greenVal = (secret[i] & 0x0000ff00) >> 8;
         int blueVal = (secret[i] & 0x000000ff);
         
         Pixel redPix = new Pixel(redVal, 0, 0);
         Pixel greenPix = new Pixel(0, greenVal, 0);
         Pixel bluePix = new Pixel (0, 0, blueVal);
         
         int redCon = redPix.getConcentration('r');
         int greenCon = greenPix.getConcentration('g');
         int blueCon = bluePix.getConcentration('b');
         
         Color redColor = new Color(redCon, 0, 0);
         Color greenColor = new Color(0, greenCon, 0);
         Color blueColor = new Color(0, 0, blueCon);
         
         red[i] = redColor.getRGB();
         green[i] = greenColor.getRGB();
         blue[i] = blueColor.getRGB();
       }
   }
   
   void vipSynchronization(int[] red, int[] green, int[] blue, int[][] cover)
   {
       //place combo into encryptedShareRGB[][]
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
         int maxGrayCon = orig.getConcentration('r');
         int grayCon1 = randomGen.nextInt(maxGrayCon + 1);
         int grayCon2 = maxGrayCon - grayCon1;
         
         Color secretGray1 = new Color(grayCon1, grayCon1, grayCon1);
         secretSharesRGB[0][i] = secretGray1.getRGB();
         
         Color secretGray2 = new Color(grayCon2, grayCon2, grayCon2);
         secretSharesRGB[1][i] = secretGray2.getRGB();
         
         int innocent1Con = innocent0.getConcentration('r');
         int embedded1Con = (innocent1Con + grayCon1) / 2;
         if(embedded1Con < 0)
             embedded1Con = 0;
         Color embedded1 = new Color(embedded1Con, embedded1Con, embedded1Con);
         encryptedShareRGB[0][i] = embedded1.getRGB();
         
         int innocent2Con = innocent1.getConcentration('r');
         int embedded2Con = (innocent2Con + grayCon2) / 2;
         if(embedded2Con < 0)
             embedded2Con = 0;
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
                 
      int numOfPixels = embeddedPixels[0].length;
      for(int i = 0; i < numOfPixels; i++)
      {
         
         int redVal1 = (embeddedPixels[0][i] & 0x00ff0000) >> 16;
         int greenVal1 = (embeddedPixels[0][i] & 0x0000ff00) >> 8;
         int blueVal1 = (embeddedPixels[0][i] & 0x000000ff);
         
         int redVal2 = (embeddedPixels[1][i] & 0x00ff0000) >> 16;
         int greenVal2 = (embeddedPixels[1][i] & 0x0000ff00) >> 8;
         int blueVal2 = (embeddedPixels[1][i] & 0x000000ff);
         
         //Need to XOR the color concentrations
         //XORing mimics stacking transparencies
         int redVal = (int)(redVal1 ^ redVal2);
         int greenVal = (int)(greenVal1 ^ greenVal2);
         int blueVal = (int)(blueVal1 ^ blueVal2);
         
         Color decryptedColor = new Color(redVal, greenVal, blueVal);
         secretMsgPixels[i] = decryptedColor.getRGB();
      }
   }

}
