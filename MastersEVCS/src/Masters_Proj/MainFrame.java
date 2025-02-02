/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Masters_Proj;

/**
 * The MainFrame is the first window users see of the Holt Visual Cryptography
 * Tool.  It explains how the tool works and lets the users choose if they wish
 * to encrypt/encode or decrypt/decode.
 * 
 * @author allisonholt
 * @version 03-23-2016
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame which is the first frame of the program.
     */
    public MainFrame() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        welcomeBanner = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        directionsArea = new javax.swing.JTextArea();
        encodeButton = new javax.swing.JButton();
        decodeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Holt Visual Cryptography");

        welcomeBanner.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        welcomeBanner.setText("Welcome to the Holt Visual Cryptography Tool!");

        descriptionArea.setEditable(false);
        descriptionArea.setColumns(20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setRows(5);
        descriptionArea.setText("The Holt Cryptography Tool allows you to encrypt or decrypt a secret image using extended visual cryptography.  The secret image gets embedded into two innocent images that must be superimposed in order to reveal the secret information.");
        descriptionArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(descriptionArea);

        directionsArea.setColumns(20);
        directionsArea.setLineWrap(true);
        directionsArea.setRows(5);
        directionsArea.setText("If you wish to encrypt a secret image, then select the encode button.  If you wish to decrypt a secret message, then select the decode button.");
        directionsArea.setWrapStyleWord(true);
        jScrollPane2.setViewportView(directionsArea);

        encodeButton.setText("Encode");
        encodeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                encodePressed(evt);
            }
        });

        decodeButton.setText("Decode");
        decodeButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        decodeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decodePressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(welcomeBanner, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(encodeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(decodeButton)
                .addGap(66, 66, 66))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(welcomeBanner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(encodeButton)
                    .addComponent(decodeButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Displays the EncodeFrame when the user hits the encode button.
     * 
     * @param evt Event of the user hitting the encode button
     */
    private void encodePressed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_encodePressed
        // TODO add your handling code here:
        new EncodeFrame().setVisible(true);
        this.setVisible(false);
        
    }//GEN-LAST:event_encodePressed

    /**
     * Displays the DecodeFrame when the user hits the decode button.
     * 
     * @param evt Event of the user hitting the decode button
     */
    private void decodePressed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_decodePressed
        // TODO add your handling code here:
        new DecodeFrame().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_decodePressed

    /**
     * Main method for the Holt Visual Cryptography Tool.
     * 
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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton decodeButton;
    private javax.swing.JTextArea descriptionArea;
    private javax.swing.JTextArea directionsArea;
    private javax.swing.JButton encodeButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel welcomeBanner;
    // End of variables declaration//GEN-END:variables
}
