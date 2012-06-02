/*******************************************************************************
 * Dokz
 * Copyright (c) 2011-2 Siniatech Ltd  http://www.siniatech.com/products/dokz
 *
 * All rights reserved. This project and the accompanying materials are made 
 * available under the terms of the MIT License which can be found in the root  
 * of the project, and at http://www.opensource.org/licenses/mit-license.php
 *
 ******************************************************************************/
package com.siniatech.dokz;

import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class DokzPanel extends JPanel {

    private final JComponent buttonBar;
    private final JComponent contentPane;
    private final String title;

    public DokzPanel( DokzManager dokzContainer, JComponent contentPane, String title ) {
        this.contentPane = contentPane;
        this.title = title;
        this.buttonBar = createButtonBar( dokzContainer, title );

        setLayout( null );
        setOpaque( false );
        add( buttonBar );
        add( contentPane );
        contentPane.setBorder( new LineBorder( DokzConstants.borderColor, 1 ) );
    }

    protected JComponent createButtonBar( DokzManager dokzContainer, String title ) {
        return dokzContainer.createButtonBarFor( this, title );
    }

    @Override
    public void setBounds( int x, int y, int width, int height ) {
        super.setBounds( x, y, width, height );
        buttonBar.setBounds( 0, 0, getWidth(), 16 );
        contentPane.setBounds( 0, 16, getWidth(), getHeight() - 16 );
    }

    @Override
    public String toString() {
        return String.format( "{ DokzPanel : %s (%d) x%d,y%d,w%d,h%d }", title, hashCode() % 1000, getX(), getY(), getWidth(), getHeight() );
    }

    public boolean isPointInButtonBar( Point p ) {
        return buttonBar.getBounds().contains( p );
    }

}
