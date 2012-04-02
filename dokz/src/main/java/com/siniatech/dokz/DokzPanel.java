package com.siniatech.dokz;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class DokzPanel extends JPanel {

    private final JComponent buttonBar;
    private final JComponent contentPane;

    public DokzPanel( DokzManager dokzContainer, JComponent contentPane, String title ) {
        this.contentPane = contentPane;
        this.buttonBar = dokzContainer.createButtonBarFor( this, title );

        setLayout( null );
        setOpaque( false );
        add( buttonBar );
        add( contentPane );
        contentPane.setBorder( new LineBorder( Color.darkGray, 1 ) );
    }

    @Override
    public void setBounds( int x, int y, int width, int height ) {
        super.setBounds( x, y, width, height );
        buttonBar.setBounds( 0, 0, getWidth(), 16 );
        contentPane.setBounds( 0, 16, getWidth(), getHeight() - 16 );
    }
}
