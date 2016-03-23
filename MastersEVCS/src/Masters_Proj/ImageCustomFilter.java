/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Masters_Proj;

import java.io.File;

/**
 * The custom filter shows only image files (JPEG and PNG).
 * 
 * @author allisonholt
 * @version 03-23-2016
 */
public class ImageCustomFilter extends javax.swing.filechooser.FileFilter {
    
    @Override
    public boolean accept(File file)
    {
        //allow only image files
        return file.isDirectory() || file.getAbsolutePath().endsWith(".png")
                || file.getAbsolutePath().endsWith(".jpeg")
                || file.getAbsolutePath().endsWith(".jpg");
    }
    
    @Override
    public String getDescription()
    {
        return "Image files (*.png, *.jpeg, *.jpg)";
    }
    
}
