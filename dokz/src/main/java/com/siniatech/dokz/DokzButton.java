package com.siniatech.dokz;

import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class DokzButton extends JButton {

    public DokzButton( URL icon, URL rolloverIcon ) {
        setOpaque( false );
        setContentAreaFilled( false );
        setIcon( new ImageIcon( Toolkit.getDefaultToolkit().getImage( icon ) ) );
        setRolloverEnabled( true );
        setRolloverIcon( new ImageIcon( Toolkit.getDefaultToolkit().getImage( rolloverIcon ) ) );
        setBorder( new EmptyBorder( 0, 1, 0, 1 ) );
    }

}
