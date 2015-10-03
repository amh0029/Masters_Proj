/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Masters_Proj;

import java.io.File;

/**
 *
 * @author allisonholt
 */
public class ImageCustomFilter extends javax.swing.filechooser.FileFilter {
    
    @Override
    public boolean accept(File file)
    {
        //allow only image file
        return file.isDirectory() || file.getAbsolutePath().endsWith(".png")
                || file.getAbsolutePath().endsWith(".jpeg");
    }
    
    @Override
    public String getDescription()
    {
        return "Image files (*.png, *.jpeg)";
    }
    
}
