/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Masters_Proj;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * This class contains all the methods needed to
 * perform the necessary operations for visual
 * cryptography.
 * 
 * @author Allison Holt
 * @version 02-21-2016
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
   
   
   /**
   * This version of the constructor is meant to perform the encryption process.
   * 
   * @param secretMsgIn The image to be encoded into the cover images.
   * @param innocentSharesIn The array contains the two cover images for the encryption process.
   */
   public ExtendedVCS(BufferedImage secretMsgIn, BufferedImage[] innocentSharesIn)
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
   /**
   * This version of the constructor is meant to perform the decryption process.
   * 
   * @param shareImgs The array contains two encoded images to be stacked and the secret decrypted.
   */
   public ExtendedVCS(BufferedImage[] shareImgs)
   {
      numSharesToDecrypt = 2;
      sharesToDecrypt = shareImgs;
      imgWidth = shareImgs[0].getWidth();
      imgHeight = shareImgs[0].getHeight();
   }
   
   /**
   * Method returns the width of the images.
   *
   * @return The width of the images used in the encryption/decryption.
   */
   public int getImgWidth()
   {
      return imgWidth;
   }
   
   /**
   * Method returns the height of the images.
   *
   * @return The height of the images used in the encryption/decryption.
   */
   public int getImgHeight()
   {
      return imgHeight;
   }
   
   /**
   * Method returns the pixels for the encoded images.
   *
   * @return The 2D array containing the pixel information for both encoded images.
   */
   public int[][] getRGBPixelsForShares()
   {
      return encryptedShareRGB;
   }
   
   /**
   * Method returns the pixels of the decrypted secret.
   *
   * @return The array containing the pixel information for the decrypted secret image.
   */
   public int[] getDecryptImgPixels()
   {
      return secretMsgPixels;
   }
   
   /**
   * Method that orchestrates the encryption process and calls the helper
   * methods necessary.
   */
   public void encryptImage()
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
   
   /**
   * Method utilizes the Floyd-Steinberg dithering technique for blending
   * the pixels together for a more continuous look.
   *
   * @param secret The array containing the pixels of an image.
   */
   private void errorDiffusion(int[] image)
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
   
   /**
   * Method breaks the secret image is broken up into three separate images
   * based on the red, green, and blue concentrations.
   *
   * @param secret The 2D array containing the pixels of the secret images.
   * @param red The red concentration of each pixel in the secret message.
   * @param green The green concentration of each pixel in the secret message.
   * @param blue The blue concentration of each pixel in the secret message.
   */
   private void splitSecretRGB(int[] secret, int[] red, int[] green, int[] blue)
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
   
   /**
   * Method takes the color shares of the secret message and the pixels of the
   * cover images and combines them so the cover images don't lose their meaning
   * while encoding the secret message.  The process does perform pixel expansion,
   * i.e. a single pixel gets represented by four in the encoded image.  The order
   * of the pixels (red, green, blue, or cover) get shuffled with every pixel.
   *
   * @param red The red concentration of each pixel in the secret message.
   * @param green The green concentration of each pixel in the secret message.
   * @param blue The blue concentration of each pixel in the secret message.
   * @param cover The 2D array containing the pixels of the cover images.
   */
   private void vipSynchronization(int[] red, int[] green, int[] blue, int[][] cover)
   {
      int[][] cover1 = new int[imgHeight][imgWidth];
      int[][] cover2 = new int[imgHeight][imgWidth];
       
      int[][] encoded1 = new int[imgHeight * 2][imgWidth * 2];
      int[][] encoded2 = new int[imgHeight * 2][imgWidth * 2];
       
      ArrayList<String> colorOrder = new ArrayList<String>();
      Collections.addAll(colorOrder, "red", "green", "blue", "cover");
       
      int n = 0;
      for(int i = 0; i < imgHeight; i++)
      {
         for(int j = 0; j < imgWidth; j++)
         {
            cover1[i][j] = cover[0][n];
            cover2[i][j] = cover[1][n];
            n++;
         }
      }
       
      for(int i = 0; i < cover[0].length; i++)
      {
         int c1Red = (cover[0][i] & 0x00ff0000) >> 16;
         int c2Red = (cover[1][i] & 0x00ff0000) >> 16;
         int c1OrigRed = c1Red;
         int c2OrigRed = c2Red;
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
           
         int c1Green = (cover[0][i] & 0x0000ff00) >> 8;
         int c2Green = (cover[1][i] & 0x0000ff00) >> 8;
         int c1OrigGreen = c1Green;
         int c2OrigGreen = c2Green;
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
           
         int c1Blue = (cover[0][i] & 0x000000ff);
         int c2Blue = (cover[1][i] & 0x000000ff);
         int c1OrigBlue = c1Blue;
         int c2OrigBlue = c2Blue;
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
           
         int row = i / imgWidth;
         int column = i % imgWidth;

         Collections.shuffle(colorOrder);
         
         int c1RedAlpha = Math.abs(c1OrigRed - Integer.parseInt(c1RedBinary, 2));
         int c2RedAlpha = Math.abs(c2OrigRed - Integer.parseInt(c2RedBinary, 2));
         int c1GreenAlpha = Math.abs(c1OrigGreen - Integer.parseInt(c1GreenBinary, 2));
         int c2GreenAlpha = Math.abs(c2OrigGreen - Integer.parseInt(c2GreenBinary, 2));
         int c1BlueAlpha = Math.abs(c1OrigBlue - Integer.parseInt(c1BlueBinary, 2));
         int c2BlueAlpha = Math.abs(c2OrigBlue - Integer.parseInt(c2BlueBinary, 2));
           
         if(colorOrder.get(0).equals("red"))
         {
            encoded1[2*row][2*column] = (255 - c1RedAlpha) << 24;
            encoded1[2*row][2*column] += (Integer.parseInt(c1RedBinary, 2)) << 16; 
            encoded1[2*row][2*column] += (Integer.parseInt("01111111", 2)) << 8;
            encoded1[2*row][2*column] += (Integer.parseInt("01111111", 2));
            
            encoded2[2*row][2*column] = (255 - c2RedAlpha) << 24;
            encoded2[2*row][2*column] += (Integer.parseInt(c2RedBinary, 2)) << 16; 
            encoded2[2*row][2*column] += (Integer.parseInt("01111111", 2)) << 8;
            encoded2[2*row][2*column] += (Integer.parseInt("01111111", 2));
         }
         else if(colorOrder.get(0).equals("green"))
         {
            encoded1[2*row][2*column] = (255 - c1GreenAlpha) << 24;
            encoded1[2*row][2*column] += (Integer.parseInt("01111111", 2)) << 16;
            encoded1[2*row][2*column] += (Integer.parseInt(c1GreenBinary, 2)) << 8;
            encoded1[2*row][2*column] += (Integer.parseInt("01111111", 2));
            
            encoded2[2*row][2*column] = (255 - c2GreenAlpha) << 24;
            encoded2[2*row][2*column] += (Integer.parseInt("01111111", 2)) << 16;
            encoded2[2*row][2*column] += (Integer.parseInt(c2GreenBinary, 2)) << 8;
            encoded2[2*row][2*column] += (Integer.parseInt("01111111", 2));
         }
         else if(colorOrder.get(0).equals("blue"))
         {
            encoded1[2*row][2*column] = (255 - c1BlueAlpha) << 24;
            encoded1[2*row][2*column] += (Integer.parseInt("01111111", 2)) << 16;
            encoded1[2*row][2*column] += (Integer.parseInt("01111111", 2)) << 8;
            encoded1[2*row][2*column] += (Integer.parseInt(c1BlueBinary, 2));
               
            encoded2[2*row][2*column] = (255 - c2BlueAlpha) << 24;
            encoded2[2*row][2*column] += (Integer.parseInt("01111111", 2)) << 16;
            encoded2[2*row][2*column] += (Integer.parseInt("01111111", 2)) << 8;
            encoded2[2*row][2*column] += (Integer.parseInt(c2BlueBinary, 2));
         }
         else
         {
            encoded1[2*row][2*column] = 255 << 24;
            encoded1[2*row][2*column] += cover1[row][column];
            
            encoded2[2*row][2*column] = 255 << 24;
            encoded2[2*row][2*column] += cover2[row][column];
         }
           
         if(colorOrder.get(1).equals("red"))
         {
            encoded1[2*row][2*column + 1] = (255 - c1RedAlpha) << 24;
            encoded1[2*row][2*column + 1] += (Integer.parseInt(c1RedBinary, 2)) << 16; 
            encoded1[2*row][2*column + 1] += (Integer.parseInt("01111111", 2)) << 8;
            encoded1[2*row][2*column + 1] += (Integer.parseInt("01111111", 2));
            
            encoded2[2*row][2*column + 1] = (255 - c2RedAlpha) << 24;
            encoded2[2*row][2*column + 1] += (Integer.parseInt(c2RedBinary, 2)) << 16; 
            encoded2[2*row][2*column + 1] += (Integer.parseInt("01111111", 2)) << 8;
            encoded2[2*row][2*column + 1] += (Integer.parseInt("01111111", 2));
         }
         else if(colorOrder.get(1).equals("green"))
         {
            encoded1[2*row][2*column + 1] = (255 - c1GreenAlpha) << 24;
            encoded1[2*row][2*column + 1] += (Integer.parseInt("01111111", 2)) << 16;
            encoded1[2*row][2*column + 1] += (Integer.parseInt(c1GreenBinary, 2)) << 8;
            encoded1[2*row][2*column + 1] += (Integer.parseInt("01111111", 2));
            
            encoded2[2*row][2*column + 1] = (255 - c2GreenAlpha) << 24;
            encoded2[2*row][2*column + 1] += (Integer.parseInt("01111111", 2)) << 16;
            encoded2[2*row][2*column + 1] += (Integer.parseInt(c2GreenBinary, 2)) << 8;
            encoded2[2*row][2*column + 1] += (Integer.parseInt("01111111", 2));
         }
         else if(colorOrder.get(1).equals("blue"))
         {
            encoded1[2*row][2*column + 1] = (255 - c1BlueAlpha) << 24;
            encoded1[2*row][2*column + 1] += (Integer.parseInt("01111111", 2)) << 16;
            encoded1[2*row][2*column + 1] += (Integer.parseInt("01111111", 2)) << 8;
            encoded1[2*row][2*column + 1] += (Integer.parseInt(c1BlueBinary, 2));
            
            encoded2[2*row][2*column + 1] = (255 - c2BlueAlpha) << 24;
            encoded2[2*row][2*column + 1] += (Integer.parseInt("01111111", 2)) << 16;
            encoded2[2*row][2*column + 1] += (Integer.parseInt("01111111", 2)) << 8;
            encoded2[2*row][2*column + 1] += (Integer.parseInt(c2BlueBinary, 2));
         }
         else
         {
            encoded1[2*row][2*column + 1] = 255 << 24;
            encoded1[2*row][2*column + 1] += cover1[row][column];
            
            encoded2[2*row][2*column + 1] = 255 << 24;
            encoded2[2*row][2*column + 1] += cover2[row][column];
         }
           
         if(colorOrder.get(2).equals("red"))
         {
            encoded1[2*row + 1][2*column] = (255 - c1RedAlpha) << 24;
            encoded1[2*row + 1][2*column] += (Integer.parseInt(c1RedBinary, 2)) << 16; 
            encoded1[2*row + 1][2*column] += (Integer.parseInt("01111111", 2)) << 8;
            encoded1[2*row + 1][2*column] += (Integer.parseInt("01111111", 2));
            
            encoded2[2*row + 1][2*column] = (255 - c2RedAlpha) << 24;
            encoded2[2*row + 1][2*column] += (Integer.parseInt(c2RedBinary, 2)) << 16; 
            encoded2[2*row + 1][2*column] += (Integer.parseInt("01111111", 2)) << 8;
            encoded2[2*row + 1][2*column] += (Integer.parseInt("01111111", 2));
         }
         else if(colorOrder.get(2).equals("green"))
         {
            encoded1[2*row + 1][2*column] = (255 - c1GreenAlpha) << 24;
            encoded1[2*row + 1][2*column] += (Integer.parseInt("01111111", 2)) << 16;
            encoded1[2*row + 1][2*column] += (Integer.parseInt(c1GreenBinary, 2)) << 8;
            encoded1[2*row + 1][2*column] += (Integer.parseInt("01111111", 2));
            
            encoded2[2*row + 1][2*column] = (255 - c2GreenAlpha) << 24;
            encoded2[2*row + 1][2*column] += (Integer.parseInt("01111111", 2)) << 16;
            encoded2[2*row + 1][2*column] += (Integer.parseInt(c2GreenBinary, 2)) << 8;
            encoded2[2*row + 1][2*column] += (Integer.parseInt("01111111", 2));
         }
         else if(colorOrder.get(2).equals("blue"))
         {
            encoded1[2*row + 1][2*column] = (255 - c1BlueAlpha) << 24;
            encoded1[2*row + 1][2*column] += (Integer.parseInt("01111111", 2)) << 16;
            encoded1[2*row + 1][2*column] += (Integer.parseInt("01111111", 2)) << 8;
            encoded1[2*row + 1][2*column] += (Integer.parseInt(c1BlueBinary, 2));
            
            encoded2[2*row + 1][2*column] = (255 - c2BlueAlpha) << 24;
            encoded2[2*row + 1][2*column] += (Integer.parseInt("01111111", 2)) << 16;
            encoded2[2*row + 1][2*column] += (Integer.parseInt("01111111", 2)) << 8;
            encoded2[2*row + 1][2*column] += (Integer.parseInt(c2BlueBinary, 2));
         }
         else
         {
            encoded1[2*row + 1][2*column] = 255 << 24;
            encoded1[2*row + 1][2*column] += cover1[row][column];
            
            encoded1[2*row + 1][2*column] = 255 << 24;
            encoded2[2*row + 1][2*column] += cover2[row][column];
         }
           
         if(colorOrder.get(3).equals("red"))
         {
            encoded1[2*row + 1][2*column + 1] = (255 - c1RedAlpha) << 24; 
            encoded1[2*row + 1][2*column + 1] += (Integer.parseInt(c1RedBinary, 2)) << 16; 
            encoded1[2*row + 1][2*column + 1] += (Integer.parseInt("01111111", 2)) << 8;
            encoded1[2*row + 1][2*column + 1] += (Integer.parseInt("01111111", 2));
            
            encoded2[2*row + 1][2*column + 1] = (255 - c2RedAlpha) << 24;
            encoded2[2*row + 1][2*column + 1] += (Integer.parseInt(c2RedBinary, 2)) << 16; 
            encoded2[2*row + 1][2*column + 1] += (Integer.parseInt("01111111", 2)) << 8;
            encoded2[2*row + 1][2*column + 1] += (Integer.parseInt("01111111", 2));
         }
         else if(colorOrder.get(3).equals("green"))
         {
            encoded1[2*row + 1][2*column + 1] = (255 - c1GreenAlpha) << 24;
            encoded1[2*row + 1][2*column + 1] += (Integer.parseInt("01111111", 2)) << 16;
            encoded1[2*row + 1][2*column + 1] += (Integer.parseInt(c1GreenBinary, 2)) << 8;
            encoded1[2*row + 1][2*column + 1] += (Integer.parseInt("01111111", 2));
            
            encoded2[2*row + 1][2*column + 1] = (255 - c2GreenAlpha) << 24;
            encoded2[2*row + 1][2*column + 1] += (Integer.parseInt("01111111", 2)) << 16;
            encoded2[2*row + 1][2*column + 1] += (Integer.parseInt(c2GreenBinary, 2)) << 8;
            encoded2[2*row + 1][2*column + 1] += (Integer.parseInt("01111111", 2));
         }
         else if(colorOrder.get(3).equals("blue"))
         {
            encoded1[2*row + 1][2*column + 1] = (255 - c1BlueAlpha) << 24;
            encoded1[2*row + 1][2*column + 1] += (Integer.parseInt("01111111", 2)) << 16;
            encoded1[2*row + 1][2*column + 1] += (Integer.parseInt("01111111", 2)) << 8;
            encoded1[2*row + 1][2*column + 1] += (Integer.parseInt(c1BlueBinary, 2));
            
            encoded2[2*row + 1][2*column + 1] = (255 - c2BlueAlpha) << 24;
            encoded2[2*row + 1][2*column + 1] += (Integer.parseInt("01111111", 2)) << 16;
            encoded2[2*row + 1][2*column + 1] += (Integer.parseInt("01111111", 2)) << 8;
            encoded2[2*row + 1][2*column + 1] += (Integer.parseInt(c2BlueBinary, 2));
         }
         else
         {
            encoded1[2*row + 1][2*column + 1] = 255 << 24;
            encoded1[2*row + 1][2*column + 1] += cover1[row][column];
            
            encoded2[2*row + 1][2*column + 1] = 255 << 24;
            encoded2[2*row + 1][2*column + 1] += cover2[row][column];
         }
           
      }
       
      n = 0;
      imgHeight *= 2;
      imgWidth *= 2;
      encryptedShareRGB = new int[2][imgHeight * imgWidth];
      for(int k = 0; k < imgHeight; k++)
      {
         for(int j = 0; j < imgWidth; j++)
         {
            encryptedShareRGB[0][n] = encoded1[k][j];
            encryptedShareRGB[1][n] = encoded2[k][j];
            n += 1;
         }
      }
   }
      
   /**
   * Reverses the encryption method so as to decrypt to encoded images.
   * The final secret image is half the width and height of the encoded
   * images because it condenses the pixel expansion used during encryption.
   */
   public void decryptImage()
   {
      //Make a 2d array of pixel arrays
      int[][] embeddedPixels = new int[numSharesToDecrypt][imgWidth * imgHeight];
      
      //getRGB pixels of BufferedImages
      for(int i = 0; i < numSharesToDecrypt; i++)
      {
         sharesToDecrypt[i].getRGB(0, 0, imgWidth, imgHeight, embeddedPixels[i], 0, imgWidth);
      }
      
      secretMsgPixels = new int[(imgWidth / 2) * (imgHeight / 2)];
      int[][] secretImg = new int[imgHeight / 2][imgWidth / 2];
      int[][] encoded1 = new int[imgHeight][imgWidth];
      int[][] encoded2 = new int[imgHeight][imgWidth];
       
      int n = 0;
      for(int i = 0; i < imgHeight; i++)
      {
         for(int j = 0; j < imgWidth; j++)
         {
            encoded1[i][j] = embeddedPixels[0][n];
            encoded2[i][j] = embeddedPixels[1][n];
            n++;
         }
      }
      
      imgHeight = imgHeight / 2;
      imgWidth = imgWidth / 2;
      for(int r = 0; r < imgHeight; r++)
      {
         for(int c = 0; c < imgWidth; c++)
         {
            int redConcentration = 0;
            int greenConcentration = 0;
            int blueConcentration = 0;
            
            int[] red1 = new int[4];
            int[] green1 = new int[4];
            int[] blue1 = new int[4];
            
            int[] red2 = new int[4];
            int[] green2 = new int[4];
            int[] blue2 = new int[4];
              
            red1[0] = (encoded1[2 * r][2 * c] & 0x00ff0000) >> 16;
            red1[1] = (encoded1[2 * r][2 * c + 1] & 0x00ff0000) >> 16;
            red1[2] = (encoded1[2 * r + 1][2 * c] & 0x00ff0000) >> 16;
            red1[3] = (encoded1[2 * r + 1][2 * c + 1] & 0x00ff0000) >> 16;
              
            red2[0] = (encoded2[2 * r][2 * c] & 0x00ff0000) >> 16;
            red2[1] = (encoded2[2 * r][2 * c + 1] & 0x00ff0000) >> 16;
            red2[2] = (encoded2[2 * r + 1][2 * c] & 0x00ff0000) >> 16;
            red2[3] = (encoded2[2 * r + 1][2 * c + 1] & 0x00ff0000) >> 16;
              
            green1[0] = (encoded1[2 * r][2 * c] & 0x0000ff00) >> 8;
            green1[1] = (encoded1[2 * r][2 * c + 1] & 0x0000ff00) >> 8;
            green1[2] = (encoded1[2 * r + 1][2 * c] & 0x0000ff00) >> 8;
            green1[3] = (encoded1[2 * r + 1][2 * c + 1] & 0x0000ff00) >> 8;
              
            green2[0] = (encoded2[2 * r][2 * c] & 0x0000ff00) >> 8;
            green2[1] = (encoded2[2 * r][2 * c + 1] & 0x0000ff00) >> 8;
            green2[2] = (encoded2[2 * r + 1][2 * c] & 0x0000ff00) >> 8;
            green2[3] = (encoded2[2 * r + 1][2 * c + 1] & 0x0000ff00) >> 8;
              
            blue1[0] = (encoded1[2 * r][2 * c] & 0x000000ff);
            blue1[1] = (encoded1[2 * r][2 * c + 1] & 0x000000ff);
            blue1[2] = (encoded1[2 * r + 1][2 * c] & 0x000000ff);
            blue1[3] = (encoded1[2 * r + 1][2 * c + 1] & 0x000000ff);
              
            blue2[0] = (encoded2[2 * r][2 * c] & 0x000000ff);
            blue2[1] = (encoded2[2 * r][2 * c + 1] & 0x000000ff);
            blue2[2] = (encoded2[2 * r + 1][2 * c] & 0x000000ff);
            blue2[3] = (encoded2[2 * r + 1][2 * c + 1] & 0x000000ff);
            
            for(int i = 0; i < 4; i++)
            {
                if(Math.abs(green1[i] - 127) < 15 && Math.abs(blue1[i] - 127) < 15
                   && Math.abs(green2[i] - 127) < 15 && Math.abs(blue2[i] - 127) < 15)
                {
                    //redConcentration = (red1[i] + red2[i]) / 2;
                    //redConcentration = (red1[i] ^ red2[i]);
                    String e1Red = String.format("%8s", Integer.toBinaryString(red1[i])).replace(" ", "0");
                    String e2Red = String.format("%8s", Integer.toBinaryString(red2[i])).replace(" ", "0");
                    String srRed = "00000000";
                    
                    for(int j = 0; j < srRed.length(); j++)
                    {
                        if(e1Red.charAt(j) != e2Red.charAt(j))
                        {
                            char[] srTemp = srRed.toCharArray();
                            srTemp[j] = '1';
                            srRed = new String(srTemp);
                        }
                        else
                        {
                            Random rand = new Random();
                            int temp = rand.nextInt(20) % 2;
                            
                            char[] srTemp = srRed.toCharArray();
                            
                            if(temp == 0)
                                srTemp[j] = '0';
                            else
                                srTemp[j] = '1';
                    
                            srRed = new String(srTemp);
                        }
                    }
                    
                    redConcentration = Integer.parseInt(srRed, 2);
                }
                else if(Math.abs(red1[i] - 127) < 15 && Math.abs(blue1[i] - 127) < 15
                        && Math.abs(red2[i] - 127) < 15 && Math.abs(blue2[i] - 127) < 15)
                {
                    //greenConcentration = (green1[i] + green2[i]) / 2;
                    //greenConcentration = (green1[i] ^ green2[i]);
                    String e1Green = String.format("%8s", Integer.toBinaryString(green1[i])).replace(" ", "0");
                    String e2Green = String.format("%8s", Integer.toBinaryString(green2[i])).replace(" ", "0");
                    String srGreen = "00000000";
                    
                    for(int j = 0; j < srGreen.length(); j++)
                    {
                        if(e1Green.charAt(j) != e2Green.charAt(j))
                        {
                            char[] srTemp = srGreen.toCharArray();
                            srTemp[j] = '1';
                            srGreen = new String(srTemp);
                        }
                        else
                        {
                            Random rand = new Random();
                            int temp = rand.nextInt(20) % 2;
                            
                            char[] srTemp = srGreen.toCharArray();
                            
                            if(temp == 0)
                                srTemp[j] = '0';
                            else
                                srTemp[j] = '1';
                            
                            srGreen = new String(srTemp);
                        }
                    }
                    
                    greenConcentration = Integer.parseInt(srGreen, 2);
                }
                else if(Math.abs(red1[i] - 127) < 15 && Math.abs(green1[i] - 127) < 15
                        && Math.abs(red2[i] - 127) < 15 && Math.abs(green2[i] - 127) < 15)
                {
                    //blueConcentration = (blue1[i] + blue2[i]) / 2;
                    //blueConcentration = (blue1[i] ^ blue2[i]);
                    String e1Blue = String.format("%8s", Integer.toBinaryString(blue1[i])).replace(" ", "0");
                    String e2Blue = String.format("%8s", Integer.toBinaryString(blue2[i])).replace(" ", "0");
                    String srBlue = "00000000";
                    
                    for(int j = 0; j < srBlue.length(); j++)
                    {
                        if(e1Blue.charAt(j) != e2Blue.charAt(j))
                        {
                            char[] srTemp = srBlue.toCharArray();
                            srTemp[j] = '1';
                            srBlue = new String(srTemp);
                        }
                        else
                        {
                            Random rand = new Random();
                            int temp = rand.nextInt(20) % 2;
                            
                            char[] srTemp = srBlue.toCharArray();
                            
                            if(temp == 0)
                                srTemp[j] = '0';
                            else
                                srTemp[j] = '1';
                            
                            srBlue = new String(srTemp);
                        }
                    }
                    
                    blueConcentration = Integer.parseInt(srBlue, 2);
                }
                else
                {
                    //do nothing because it is cover pixel
                }
            }
             
            Color decryptedColor = new Color(redConcentration, greenConcentration, blueConcentration);
            secretImg[r][c] = decryptedColor.getRGB();
         }
      }
      
      int secretIndex = 0;
      for(int i = 0; i < imgHeight; i++)
      {
         for(int j = 0; j < imgWidth; j++)
         {
            secretMsgPixels[secretIndex] = secretImg[i][j];
            secretIndex += 1;
         }
      }
      
      errorDiffusion(secretMsgPixels);
      
   }
   
   /**
   * Method decrypts two encoded images by XOR-ing the binary color values together.
   * The XOR technique decrypts the encoded images as if they were printed on
   * transparencies and physically stacked.
   */
   public void decryptImageTransparencyMethod()
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
