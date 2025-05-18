/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.kafkatransferwindow;

import java.awt.Dimension;
import java.awt.Window;
import javax.swing.JFrame;

/**
 *
 * @author dede
 */
public class KafkaTransferWindow {

    public static void main(String[] args) {
        MainJFrame mainJFrame = new MainJFrame();
        mainJFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainJFrame.setDefaultLookAndFeelDecorated(false);
        mainJFrame.setResizable(false);
        mainJFrame.setSize(800, 600);       
        mainJFrame.setVisible(true);
    }
}
