package com.siniatech.dokz;

import java.awt.BorderLayout;
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
        buttonPanel.add( min );
        buttonPanel.add( max );
        buttonPanel.add( close );

        JPanel buttonBar = new JPanel( new BorderLayout() );
        buttonBar.add( buttonPanel, BorderLayout.EAST );

        if ( title != null && title.length() > 0 ) {
            buttonBar.add( new JLabel( title ), BorderLayout.WEST );
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
}
