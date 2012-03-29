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
