package com.siniatech.dokz;

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

import com.siniatech.siniautils.fn.IResponse0;

public class DokzButtonBar extends JComponent {

    private DokzButton min;
    private DokzButton max;
    private DokzButton close;
    private JLabel label;

    public DokzButtonBar( String title, final IResponse0 onMin, final IResponse0 onMax, final IResponse0 onClose ) {
        min = new DokzButton( this.getClass().getResource( "icons/min.png" ) );
        max = new DokzButton( this.getClass().getResource( "icons/max.png" ) );
        close = new DokzButton( this.getClass().getResource( "icons/close.png" ) );
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
        label.setBounds( 10, 1, width - 66, height );
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
        g2.dispose();
    }

}
