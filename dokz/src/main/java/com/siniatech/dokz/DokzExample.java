package com.siniatech.dokz;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DokzExample extends JFrame {

    public DokzExample() {
        setSize( 800, 600 );
        setVisible( true );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        getContentPane().setLayout( new BorderLayout() );
        DokzContainer dokzContainer = new DokzContainer();

        getContentPane().add( dokzContainer.asJComponent(), BorderLayout.CENTER );
        dokzContainer.add( new JPanel(), "Pane" );
        dokzContainer.add( new JPanel(), "Pane2" );
        dokzContainer.add( new JPanel(), "Pane3" );

        JMenu menu = new JMenu( "Windows" );
        for ( JMenuItem menuItem : dokzContainer.getPanelStateMenuItems() ) {
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
