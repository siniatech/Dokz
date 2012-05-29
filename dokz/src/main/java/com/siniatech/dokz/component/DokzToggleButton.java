/*******************************************************************************
 * Dokz
 * Copyright (c) 2011-2 Siniatech Ltd  http://www.siniatech.com/products/dokz
 *
 * All rights reserved. This project and the accompanying materials are made 
 * available under the terms of the MIT License which can be found in the root  
 * of the project, and at http://www.opensource.org/licenses/mit-license.php
 *
 ******************************************************************************/
package com.siniatech.dokz.component;

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
