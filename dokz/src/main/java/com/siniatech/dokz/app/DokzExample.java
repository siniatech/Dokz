/*******************************************************************************
 * Dokz
 * Copyright (c) 2011-2 Siniatech Ltd  http://www.siniatech.com/products/dokz
 *
 * All rights reserved. This project and the accompanying materials are made 
 * available under the terms of the MIT License which can be found in the root  
 * of the project, and at http://www.opensource.org/licenses/mit-license.php
 *
 ******************************************************************************/
package com.siniatech.dokz.app;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.siniatech.dokz.DokzManager;

public class DokzExample extends JFrame {

    public DokzExample() {
        setSize( 800, 600 );
        setVisible( true );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        getContentPane().setLayout( new BorderLayout() );
        DokzManager dokzContainer = new DokzManager();

        getContentPane().add( dokzContainer.asJComponent(), BorderLayout.CENTER );
        JPanel component = new JPanel();
        component.setBackground( Color.red );
        dokzContainer.add( component, "Pane" );
        dokzContainer.add( new JPanel(), "Pane2" );
        dokzContainer.add( new JPanel(), "Pane3" );
        dokzContainer.add( new JPanel(), "Pane4" );
        dokzContainer.add( new JPanel(), "Pane5" );
        dokzContainer.add( new JPanel(), "Pane6" );
        dokzContainer.add( new JPanel(), "Pane7" );
        dokzContainer.add( new JPanel(), "Pane8" );

        JMenu menu = new JMenu( "Windows" );
        for ( JMenuItem menuItem : dokzContainer.getDokzContext().getPanelStateMenuItems() ) {
            menu.add( menuItem );
        }
        JMenuBar menubar = new JMenuBar();
        menubar.add( menu );
        setJMenuBar( menubar );
    }

    public static void main( String[] args ) {
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run() {
                new DokzExample();
            }
        } );
    }
}
