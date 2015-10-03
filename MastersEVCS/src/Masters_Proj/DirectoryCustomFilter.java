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
public class DirectoryCustomFilter extends javax.swing.filechooser.FileFilter{
    
    @Override
    public boolean accept(File file)
    {
        //allow only image file
        return file.isDirectory();
    }
    
    @Override
    public String getDescription()
    {
        return "File Directory";
    }
}
