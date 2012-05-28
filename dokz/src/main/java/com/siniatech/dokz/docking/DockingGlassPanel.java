package com.siniatech.dokz.docking;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;

import javax.swing.JPanel;

public class DockingGlassPanel extends JPanel {

    private Rectangle potentialDockingZone;

    public DockingGlassPanel() {
        this.potentialDockingZone = null;
        setVisible( false );
    }

    public void setPotentialDockingZone( Rectangle potentialDockingZone ) {
        this.potentialDockingZone = potentialDockingZone;
    }

    @Override
    protected void paintComponent( Graphics g ) {
        Graphics2D g2 = (Graphics2D) g.create();
        if ( potentialDockingZone == null ) {
            g2.setColor( new Color( 0, 0, 128, 10 ) );
            g2.fill( new Rectangle( getWidth(), getHeight() ) );
        } else {
            paintPotentialDockingZone( g2 );
        }
    }

    private void paintPotentialDockingZone( Graphics2D g2 ) {
        g2.setColor( new Color( 0, 0, 128, 100 ) );
        g2.fill( potentialDockingZone );
        g2.setColor( new Color( 0, 0, 128, 10 ) );
        Area remainingArea = new Area( new Rectangle( getWidth(), getHeight() ) );
        remainingArea.subtract( new Area( potentialDockingZone ) );
        g2.fill( remainingArea );
    }
}
