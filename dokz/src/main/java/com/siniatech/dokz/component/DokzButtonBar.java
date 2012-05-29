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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JLabel;

import com.siniatech.dokz.DokzConstants;
import com.siniatech.siniautils.fn.IResponse0;

public class DokzButtonBar extends JComponent {

    private DokzButton min;
    private DokzToggleButton max;
    private DokzButton close;
    private JLabel label;

    public DokzButtonBar( String title, final IResponse0 onMin, final IResponse0 onMax, final IResponse0 onClose ) {
        min = new DokzButton( this.getClass().getResource( "../icons/dokzMin.png" ), this.getClass().getResource( "../icons/dokzMinOver.png" ) );
        max = new DokzToggleButton( this.getClass().getResource( "../icons/dokzMax.png" ), this.getClass().getResource( "../icons/dokzMaxOver.png" ), //
            this.getClass().getResource( "../icons/dokzRestore.png" ), this.getClass().getResource( "../icons/dokzRestoreOver.png" ) );
        close = new DokzButton( this.getClass().getResource( "../icons/dokzClose.png" ), this.getClass().getResource( "../icons/dokzCloseOver.png" ) );
        add( min );
        add( max );
        add( close );

        if ( title != null && title.length() > 0 ) {
            label = new JLabel( title );
            label.setFont( label.getFont().deriveFont( 10f ) );
            label.setOpaque( false );
            add( label );
        }

        min.addActionListener( new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                onMin.respond();
            }
        } );
        max.addActionListener( new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                onMax.respond();
            }
        } );
        close.addActionListener( new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                onClose.respond();
            }
        } );

        setLayout( null );
    }

    @Override
    public void setBounds( int x, int y, int width, int height ) {
        super.setBounds( x, y, width, height );
        if ( label != null ) {
            label.setBounds( 10, 1, width - 66, height );
        }
        close.setBounds( width - 22, 2, 12, 12 );
        max.setBounds( width - 36, 2, 12, 12 );
        min.setBounds( width - 50, 2, 12, 12 );
    }

    @Override
    protected void paintComponent( Graphics g ) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        g2.setPaint( new LinearGradientPaint( new Point( 0, 0 ), new Point( 0, 16 ), new float[] { 0.2f, 0.8f }, new Color[] { Color.lightGray, new Color( 228, 228, 228 ) } ) );
        g2.fillRect( 8, 0, getWidth() - 16, 16 );
        g2.fillArc( 0, 0, 16, 32, 90, 180 );
        g2.fillArc( getWidth() - 16, 0, 16, 32, 180, 270 );
        g2.setColor( DokzConstants.borderColor );
        g2.drawArc( 0, 0, 16, 32, 90, 180 );
        g2.drawArc( getWidth() - 16, 0, 16, 32, 180, 270 );
        g2.drawLine( 8, 0, getWidth() - 8, 0 );
        g2.drawLine( 0, 11, 0, 16 );
        g2.drawLine( getWidth() - 1, 11, getWidth() - 1, 16 );
        g2.dispose();
    }

}
