package com.siniatech.dokz;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class DokzButton extends JButton {

    public DokzButton( URL resource ) {
        setOpaque( false );
        setContentAreaFilled( false );
        Image image = Toolkit.getDefaultToolkit().getImage( resource );
        setIcon( new ImageIcon( image ) );
        setBorder( new EmptyBorder( 0, 1, 0, 1 ) );
    }

}
