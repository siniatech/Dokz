package com.siniatech.dokz;

import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

public class DokzToggleButton extends JToggleButton {

    public DokzToggleButton( URL icon, URL rolloverIcon, URL selectedIcon, URL rolloverSelectedIcon ) {
        setOpaque( false );
        setContentAreaFilled( false );
        setIcon( new ImageIcon( Toolkit.getDefaultToolkit().getImage( icon ) ) );
        setRolloverEnabled( true );
        setRolloverIcon( new ImageIcon( Toolkit.getDefaultToolkit().getImage( rolloverIcon ) ) );
        setBorder( new EmptyBorder( 0, 1, 0, 1 ) );
        setSelectedIcon( new ImageIcon( Toolkit.getDefaultToolkit().getImage( selectedIcon ) ) );
        setRolloverSelectedIcon( new ImageIcon( Toolkit.getDefaultToolkit().getImage( rolloverSelectedIcon ) ) );
    }
}
