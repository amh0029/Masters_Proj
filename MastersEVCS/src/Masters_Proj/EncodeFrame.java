package Masters_Proj;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author allisonholt
 */
public class EncodeFrame extends javax.swing.JFrame {

    /**
     * Creates new form EncodeFrame
     */
    public EncodeFrame() {
        initComponents();
    }
    
    public EncodeFrame(EncodeFrame prevState)
    {
        this.secretTextField.setText(prevState.secretTextField.getText());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imageChooser = new javax.swing.JFileChooser();
        directoryChooser = new javax.swing.JFileChooser();
        cancelButton = new javax.swing.JButton();
        encodeButton = new javax.swing.JButton();
        panel1 = new javax.swing.JPanel();
        secretTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        browseButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        innocentTextField1 = new javax.swing.JTextField();
        browseButton2 = new javax.swing.JButton();
        innocentTextField2 = new javax.swing.JTextField();
        browseButton3 = new javax.swing.JButton();
        optionalPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        filename1 = new javax.swing.JTextField();
        filename2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        storageDirectoryTextField = new javax.swing.JTextField();
        browseButton4 = new javax.swing.JButton();

        imageChooser.setDialogTitle("Choose an Image");
        imageChooser.setFileFilter(new ImageCustomFilter());

        directoryChooser.setDialogTitle("Choose a Directory");
        directoryChooser.setFileFilter(new DirectoryCustomFilter());
        directoryChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelPressed(evt);
            }
        });

        encodeButton.setText("Encode");
        encodeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                encodePressed(evt);
            }
        });

        panel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Secret Image"));
        panel1.setToolTipText("Secret Image");

        jLabel1.setText("Please select your secret image file:*");

        browseButton1.setText("Browse");
        browseButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageBrowsePressed(evt);
            }
        });

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panel1Layout.createSequentialGroup()
                .addComponent(secretTextField)
                .addGap(18, 18, 18)
                .addComponent(browseButton1))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(secretTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Innocent Images"));

        jLabel2.setText("Please select your two innocent image files:*");

        browseButton2.setText("Browse");
        browseButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageBrowsePressed(evt);
            }
        });

        browseButton3.setText("Browse");
        browseButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageBrowsePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(innocentTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(innocentTextField1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(browseButton2)
                            .addComponent(browseButton3)))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(innocentTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(innocentTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseButton3))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        optionalPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Optional"));

        jLabel3.setText("Names for your encoded shares (without file extension):");

        jLabel4.setText("File 1:");

        jLabel5.setText("File 2:");

        jLabel6.setText("Directory for Image Shares:");

        browseButton4.setText("Browse");
        browseButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dirBrowsePressed(evt);
            }
        });

        javax.swing.GroupLayout optionalPanelLayout = new javax.swing.GroupLayout(optionalPanel);
        optionalPanel.setLayout(optionalPanelLayout);
        optionalPanelLayout.setHorizontalGroup(
            optionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(optionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(optionalPanelLayout.createSequentialGroup()
                        .addGroup(optionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6))
                        .addContainerGap())
                    .addGroup(optionalPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(optionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(optionalPanelLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(filename2))
                            .addGroup(optionalPanelLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(filename1))
                            .addGroup(optionalPanelLayout.createSequentialGroup()
                                .addGap(0, 3, Short.MAX_VALUE)
                                .addComponent(storageDirectoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(browseButton4))))))
        );
        optionalPanelLayout.setVerticalGroup(
            optionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(optionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(filename1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(optionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(filename2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(optionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(storageDirectoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseButton4))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(encodeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cancelButton))
                    .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(optionalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(optionalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(encodeButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelPressed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelPressed
        // TODO add your handling code here:
        this.setVisible(false);
        new MainFrame().setVisible(true);
    }//GEN-LAST:event_cancelPressed

    private void dirBrowsePressed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dirBrowsePressed
        
        int returnVal = directoryChooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File dir = directoryChooser.getSelectedFile();
            if(evt.getSource() == browseButton4)
            {
                storageDirectoryTextField.setText(dir.getAbsolutePath());
                directoryForStorage = dir.getAbsolutePath();
            }
        }
        
    }//GEN-LAST:event_dirBrowsePressed

    private void imageBrowsePressed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageBrowsePressed
        
        int returnVal = imageChooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File imageFile = imageChooser.getSelectedFile();
            if(evt.getSource() == browseButton1)
            {
                secretTextField.setText(imageFile.getAbsolutePath());
                secretFile = imageFile.getAbsolutePath();
            }
            else if(evt.getSource() == browseButton2)
            {
                innocentTextField1.setText(imageFile.getAbsolutePath());
                innocentFiles[0] = imageFile.getAbsolutePath();
            }
            else if(evt.getSource() == browseButton3)
            {
                innocentTextField2.setText(imageFile.getAbsolutePath());
                innocentFiles[1] = imageFile.getAbsolutePath();
            }
        }
    }//GEN-LAST:event_imageBrowsePressed

    private void encodePressed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_encodePressed
        //Code to encode secret message
        BufferedImage secretImage = null;
        boolean fileFound;
        try
        {
            secretImage = ImageIO.read(new File(secretFile));
            fileFound = true;
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Error reading your secret file",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            fileFound = false;
        }
        
        BufferedImage[] innocentShares = new BufferedImage[0];
        if(fileFound)
        {
            innocentShares = new BufferedImage[2];
            for(int i = 0; i < 2; i++)
            {
                try
                {
                   innocentShares[i] = ImageIO.read(new File(innocentFiles[i]));
                   fileFound = true;
                }
                catch(IOException e)
                {
                    JOptionPane.showMessageDialog(null, 
                        ("Error reading innocent file " + (i + 1)),
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                    fileFound = false;
                }
            }
        }
        
        if(fileFound)
        {
            ExtendedVCS myEVCS = new ExtendedVCS(secretImage, innocentShares);
            myEVCS.encryptImage();
            
            int[][] newInnocentRGB = myEVCS.getRGBPixelsForShares();
            
            if(storageDirectoryTextField.getText().equals(""))
            {
                //Get path to users desktop
                //BUG!!!  Not working.
                directoryForStorage = "C:/Users/allisonholt/Desktop";
                //makeDir = false;
            }
            
            String[] shareFiles = new String[2];
            
            if(filename1.getText().equals(""))
            {
                shareFiles[0] = directoryForStorage + "/share1.png";
            }
            else
            {
                shareFiles[0] = directoryForStorage + "/" + filename1.getText() +".png";
            }
                
            if(filename2.getText().equals(""))
            {
                shareFiles[1] = directoryForStorage + "/share2.png";
            }
            else
            {
                shareFiles[1] = directoryForStorage + "/" + filename2.getText() +".png";
            }
            
            
            try
            {
                BufferedImage tempShare1 = new BufferedImage(myEVCS.getImgWidth(), myEVCS.getImgHeight(), BufferedImage.TYPE_INT_ARGB);
                tempShare1.setRGB(0, 0, myEVCS.getImgWidth(), myEVCS.getImgHeight(), newInnocentRGB[0], 0, myEVCS.getImgWidth());
                File tempOutput1 = new File(shareFiles[0]);
                ImageIO.write(tempShare1, "png", tempOutput1);
                    
                BufferedImage tempShare2 = new BufferedImage(myEVCS.getImgWidth(), myEVCS.getImgHeight(), BufferedImage.TYPE_INT_ARGB);
                tempShare2.setRGB(0, 0, myEVCS.getImgWidth(), myEVCS.getImgHeight(), newInnocentRGB[1], 0, myEVCS.getImgWidth());
                File tempOutput2 = new File(shareFiles[1]);
                ImageIO.write(tempShare2, "png", tempOutput2);
                    
                new MainFrame().setVisible(true);
                this.setVisible(false);
                JOptionPane.showMessageDialog(null, "Your encrypted shares have been created.",
                    "SUCCESS", JOptionPane.PLAIN_MESSAGE);
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(null, "Error encrypting your secret message",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }//GEN-LAST:event_encodePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EncodeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EncodeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EncodeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EncodeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EncodeFrame().setVisible(true);
            }
        });
    }

    //Variables for encoding
    private String secretFile = "";
    private String[] innocentFiles = new String[2];
    private String directoryForStorage = "";
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton1;
    private javax.swing.JButton browseButton2;
    private javax.swing.JButton browseButton3;
    private javax.swing.JButton browseButton4;
    private javax.swing.JButton cancelButton;
    private javax.swing.JFileChooser directoryChooser;
    private javax.swing.JButton encodeButton;
    private javax.swing.JTextField filename1;
    private javax.swing.JTextField filename2;
    private javax.swing.JFileChooser imageChooser;
    private javax.swing.JTextField innocentTextField1;
    private javax.swing.JTextField innocentTextField2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel optionalPanel;
    private javax.swing.JPanel panel1;
    private javax.swing.JTextField secretTextField;
    private javax.swing.JTextField storageDirectoryTextField;
    // End of variables declaration//GEN-END:variables
}
