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
      encryptedShareRGB = new int[2][imgWidth * imgHeight];
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
      
   }
   
   void errorDiffusion(int[] image)
   {
       int x[][] = new int[imgHeight][imgWidth];
       int u[][] = new int [imgHeight][imgWidth];
       
       int i = 0;
       for(int n = 0; n < imgHeight; n++)
       {
           for(int m = 0; m < imgWidth; m++)
           {
               x[n][m] = image[i];
               i += 1;
           }
       }
       
       for(int n = 0; n < imgHeight; n++)
       {
           for(int m = 0; m < imgWidth; m++)
           {
               u[n][m] += x[n][m];
               
               int xRed = (x[n][m] & 0x00ff0000) >> 16;
               int xGreen = (x[n][m] & 0x0000ff00) >> 8;
               int xBlue = (x[n][m] & 0x000000ff);
               
               int uRed = (u[n][m] & 0x00ff0000) >> 16;
               int uGreen = (u[n][m] & 0x0000ff00) >> 8;
               int uBlue = (u[n][m] & 0x000000ff);
               
               int quantErrorRed = uRed - xRed;
               int quantErrorGreen = uGreen - xGreen;
               int quantErrorBlue = uBlue - xBlue;
               
               if(xRed > 127)
               {
                   if((m + 1) < imgWidth)
                   {
                       int temp = quantErrorRed * 7 / 16;
                       temp = temp << 16;
                       u[n][m + 1] += temp;
                   }
                   if((m - 1) >= 0 && (n + 1) < imgHeight)
                   {
                       int temp = quantErrorRed * 3 / 16;
                       temp = temp << 16;
                       u[n + 1][m - 1] += temp;
                   }
                   if((n + 1) < imgHeight)
                   {
                       int temp = quantErrorRed * 5 / 16;
                       temp = temp << 16;
                       u[n + 1][m] += temp;
                   }
                   if((m + 1) < imgWidth && (n + 1) < imgHeight)
                   {
                       int temp = quantErrorRed * 1 / 16;
                       temp = temp << 16;
                       u[n + 1][m + 1] += temp;
                   }
               }
               if(xGreen > 127)
               {
                   if((m + 1) < imgWidth)
                   {
                       int temp = quantErrorGreen * 7 / 16;
                       temp = temp << 8;
                       u[n][m + 1] += temp;
                   }
                   if((m - 1) >= 0 && (n + 1) < imgHeight)
                   {
                       int temp = quantErrorGreen * 3 / 16;
                       temp = temp << 8;
                       u[n + 1][m - 1] += temp;
                   }
                   if((n + 1) < imgHeight)
                   {
                       int temp = quantErrorGreen * 5 / 16;
                       temp = temp << 8;
                       u[n + 1][m] += temp;
                   }
                   if((m + 1) < imgWidth && (n + 1) < imgHeight)
                   {
                       int temp = quantErrorGreen * 1 / 16;
                       temp = temp << 8;
                       u[n + 1][m + 1] += temp;
                   }
               }
               if(xBlue > 127)
               {
                   if((m + 1) < imgWidth)
                   {
                       int temp = quantErrorBlue * 7 / 16;
                       u[n][m + 1] += temp;
                   }
                   if((m - 1) >= 0 && (n + 1) < imgHeight)
                   {
                       int temp = quantErrorBlue * 3 / 16;
                       u[n + 1][m - 1] += temp;
                   }
                   if((n + 1) < imgHeight)
                   {
                       int temp = quantErrorBlue * 5 / 16;
                       u[n + 1][m] += temp;
                   }
                   if((m + 1) < imgWidth && (n + 1) < imgHeight)
                   {
                       int temp = quantErrorBlue * 1 / 16;
                       u[n + 1][m + 1] += temp;
                   }
               }
           }
       }
       
       int j = 0;
       for(int n = 0; n < imgHeight; n++)
       {
           for(int m = 0; m < imgWidth; m++)
           {
               image[j] = u[n][m];
               j += 1;
           }
       }
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
       for(int i = 0; i < cover[0].length; i++)
       {
           int c1Red = (cover[0][i] & 0x00ff0000) >> 16;
           int c2Red = (cover[1][i] & 0x00ff0000) >> 16;
           int secretRed = (red[i] & 0x00ff0000) >> 16;
           String c1RedBinary = String.format("%8s", Integer.toBinaryString(c1Red)).replace(" ", "0");
           String c2RedBinary = String.format("%8s", Integer.toBinaryString(c2Red)).replace(" ", "0");
           String secretRedBinary = String.format("%8s",Integer.toBinaryString(secretRed)).replace(" ", "0");
           
           for(int j = 0; j < secretRedBinary.length(); j++)
           {
               if(secretRedBinary.charAt(j) == '1'
                       && c1RedBinary.charAt(j) == c2RedBinary.charAt(j))
               {
                   Random rand = new Random();
                   int temp = rand.nextInt(20) % 2;
                   /*
                   if temp == 0 then c1 stays the same and c2 is flipped
                   if temp == 1 then c2 stays the same and c1 is flipped
                   */
                   if(temp == 0 && c2RedBinary.charAt(j) == '1')
                   {
                       char[] c2Array = c2RedBinary.toCharArray();
                       c2Array[j] = '0';
                       c2RedBinary = new String(c2Array);
                   }
                   else if(temp == 0 && c2RedBinary.charAt(j) == '0')
                   {
                       char[] c2Array = c2RedBinary.toCharArray();
                       c2Array[j] = '1';
                       c2RedBinary = new String(c2Array);
                   }
                   else if(temp == 1 && c1RedBinary.charAt(j) == '1')
                   {
                       char[] c1Array = c1RedBinary.toCharArray();
                       c1Array[j] = '0';
                       c1RedBinary = new String(c1Array);
                   }
                   else
                   {
                       char[] c1Array = c1RedBinary.toCharArray();
                       c1Array[j] = '1';
                       c1RedBinary = new String(c1Array);
                   }
               }
               else
               {
                   Random rand = new Random();
                   int temp = rand.nextInt(20) % 2;
                   /*
                   if temp == 0 then c2 bit is set to c1 bit
                   if temp == 1 then c1 bit is set to c2 bit
                   */
                   if(temp == 0)
                   {
                       char[] c2Array = c2RedBinary.toCharArray();
                       c2Array[j] = c1RedBinary.charAt(j);
                       c2RedBinary = new String(c2Array);
                   }
                   else
                   {
                       char[] c1Array = c1RedBinary.toCharArray();
                       c1Array[j] = c2RedBinary.charAt(j);
                       c1RedBinary = new String(c1Array);
                   }
               }
           }
           
           encryptedShareRGB[0][i] = (Integer.parseInt(c1RedBinary, 2)) << 16;
           encryptedShareRGB[1][i] = (Integer.parseInt(c2RedBinary, 2)) << 16;
           
           int c1Green = (cover[0][i] & 0x0000ff00) >> 8;
           int c2Green = (cover[1][i] & 0x0000ff00) >> 8;
           int secretGreen = (green[i] & 0x0000ff00) >> 8;
           String c1GreenBinary = String.format("%8s", Integer.toBinaryString(c1Green)).replace(" ", "0");
           String c2GreenBinary = String.format("%8s", Integer.toBinaryString(c2Green)).replace(" ", "0");
           String secretGreenBinary = String.format("%8s", Integer.toBinaryString(secretGreen)).replace(" ", "0");
           
           for(int j = 0; j < secretGreenBinary.length(); j++)
           {
               if(secretGreenBinary.charAt(j) == '1'
                       && c1GreenBinary.charAt(j) == c2GreenBinary.charAt(j))
               {
                   Random rand = new Random();
                   int temp = rand.nextInt(20) % 2;
                   /*
                   if temp == 0 then c1 stays the same and c2 is flipped
                   if temp == 1 then c2 stays the same and c1 is flipped
                   */
                   if(temp == 0 && c2GreenBinary.charAt(j) == '1')
                   {
                       char[] c2Array = c2GreenBinary.toCharArray();
                       c2Array[j] = '0';
                       c2GreenBinary = new String(c2Array);
                   }
                   else if(temp == 0 && c2GreenBinary.charAt(j) == '0')
                   {
                       char[] c2Array = c2GreenBinary.toCharArray();
                       c2Array[j] = '1';
                       c2GreenBinary = new String(c2Array);
                   }
                   else if(temp == 1 && c1GreenBinary.charAt(j) == '1')
                   {
                       char[] c1Array = c1GreenBinary.toCharArray();
                       c1Array[j] = '0';
                       c1GreenBinary = new String(c1Array);
                   }
                   else
                   {
                       char[] c1Array = c1GreenBinary.toCharArray();
                       c1Array[j] = '1';
                       c1GreenBinary = new String(c1Array);
                   }
               }
               else
               {
                   Random rand = new Random();
                   int temp = rand.nextInt(20) % 2;
                   /*
                   if temp == 0 then c2 bit is set to c1 bit
                   if temp == 1 then c1 bit is set to c2 bit
                   */
                   if(temp == 0)
                   {
                       char[] c2Array = c2GreenBinary.toCharArray();
                       c2Array[j] = c1GreenBinary.charAt(j);
                       c2GreenBinary = new String(c2Array);
                   }
                   else
                   {
                       char[] c1Array = c1GreenBinary.toCharArray();
                       c1Array[j] = c2GreenBinary.charAt(j);
                       c1GreenBinary = new String(c1Array);
                   }
               }
           }
           
           encryptedShareRGB[0][i] += (Integer.parseInt(c1GreenBinary, 2)) << 8;
           encryptedShareRGB[1][i] += (Integer.parseInt(c2GreenBinary, 2)) << 8;
           
           int c1Blue = (cover[0][i] & 0x000000ff);
           int c2Blue = (cover[1][i] & 0x000000ff);
           int secretBlue = (blue[i] & 0x000000ff);
           String c1BlueBinary = String.format("%8s", Integer.toBinaryString(c1Blue)).replace(" ", "0");
           String c2BlueBinary = String.format("%8s", Integer.toBinaryString(c2Blue)).replace(" ", "0");
           String secretBlueBinary = String.format("%8s", Integer.toBinaryString(secretBlue)).replace(" ", "0");
           
           for(int j = 0; j < secretBlueBinary.length(); j++)
           {
               if(secretBlueBinary.charAt(j) == '1'
                       && c1BlueBinary.charAt(j) == c2BlueBinary.charAt(j))
               {
                   Random rand = new Random();
                   int temp = rand.nextInt(20) % 2;
                   /*
                   if temp == 0 then c1 stays the same and c2 is flipped
                   if temp == 1 then c2 stays the same and c1 is flipped
                   */
                   if(temp == 0 && c2BlueBinary.charAt(j) == '1')
                   {
                       char[] c2Array = c2BlueBinary.toCharArray();
                       c2Array[j] = '0';
                       c2BlueBinary = new String(c2Array);
                   }
                   else if(temp == 0 && c2BlueBinary.charAt(j) == '0')
                   {
                       char[] c2Array = c2BlueBinary.toCharArray();
                       c2Array[j] = '1';
                       c2BlueBinary = new String(c2Array);
                   }
                   else if(temp == 1 && c1BlueBinary.charAt(j) == '1')
                   {
                       char[] c1Array = c1BlueBinary.toCharArray();
                       c1Array[j] = '0';
                       c1BlueBinary = new String(c1Array);
                   }
                   else
                   {
                       char[] c1Array = c1BlueBinary.toCharArray();
                       c1Array[j] = '1';
                       c1BlueBinary = new String(c1Array);
                   }
               }
               else
               {
                   Random rand = new Random();
                   int temp = rand.nextInt(20) % 2;
                   /*
                   if temp == 0 then c2 bit is set to c1 bit
                   if temp == 1 then c1 bit is set to c2 bit
                   */
                   if(temp == 0)
                   {
                       char[] c2Array = c2BlueBinary.toCharArray();
                       c2Array[j] = c1BlueBinary.charAt(j);
                       c2BlueBinary = new String(c2Array);
                   }
                   else
                   {
                       char[] c1Array = c1BlueBinary.toCharArray();
                       c1Array[j] = c2BlueBinary.charAt(j);
                       c1BlueBinary = new String(c1Array);
                   }
               }
           }
           
           encryptedShareRGB[0][i] += (Integer.parseInt(c1BlueBinary, 2));
           encryptedShareRGB[1][i] += (Integer.parseInt(c2BlueBinary, 2));
       }
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
