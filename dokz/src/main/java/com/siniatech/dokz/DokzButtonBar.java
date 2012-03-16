package com.siniatech.dokz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.siniatech.siniautils.fn.IResponse0;

public class DokzButtonBar extends JComponent {

    public DokzButtonBar( String title, final IResponse0 onMin, final IResponse0 onMax, final IResponse0 onClose ) {
        JButton min = new JButton( "Min" );
        JButton max = new JButton( "Max" );
        JButton close = new JButton( "Close" );

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque( false );
        buttonPanel.add( min );
        buttonPanel.add( max );
        buttonPanel.add( close );

        JPanel buttonBar = new JPanel( new BorderLayout() );
        buttonBar.setOpaque( false );
        buttonBar.add( buttonPanel, BorderLayout.EAST );

        if ( title != null && title.length() > 0 ) {
            JLabel label = new JLabel( title );
            label.setOpaque( false );
            buttonBar.add( label, BorderLayout.WEST );
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

        setLayout( new BorderLayout() );
        add( buttonBar, BorderLayout.CENTER );
    }

    @Override
    protected void paintComponent( Graphics g ) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint( new LinearGradientPaint( new Point( 0, 0 ), new Point( 0, 16 ), new float[] { 0.2f, 0.8f }, new Color[] { Color.lightGray, new Color( 228, 228, 228 ) } ) );
        g2.fillRect( 8, 0, getWidth() - 16, 16 );
        g2.fillArc( 0, 0, 16, 32, 90, 180 );
        g2.fillArc( getWidth() - 16, 0, 16, 32, 180, 270 );
    }

}
