package com.siniatech.dokz.docking;

import static javax.swing.SwingUtilities.*;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import com.siniatech.dokz.DokzContainer;
import com.siniatech.dokz.DokzPanel;

public class DockingManager extends MouseAdapter implements MouseMotionListener {

    private final DokzContainer dokzContainer;
    private boolean dockingStarted;
    private final DockingGlassPanel dockingGlassPanel;

    public DockingManager( DokzContainer dokzContainer ) {
        this.dokzContainer = dokzContainer;
        this.dockingGlassPanel = new DockingGlassPanel();
        dokzContainer.add( dockingGlassPanel );
    }

    public boolean canStartDocking( MouseEvent e ) {
        DokzPanel panel = dokzContainer.getPanelAt( e.getPoint() );
        return panel != null && //
            panel.isPointInButtonBar( convertPoint( dokzContainer, e.getPoint(), panel ) );
    }

    public DockingGlassPanel getDockingGlassPanel() {
        return dockingGlassPanel;
    }

    public void startDocking() {
        setCursor( Cursor.getPredefinedCursor( Cursor.MOVE_CURSOR ) );
        dockingStarted = true;
        dockingGlassPanel.setVisible( true );
        dokzContainer.revalidate();
    }

    private void endDocking() {
        dokzContainer.resetCursor();
        dockingStarted = false;
        dockingGlassPanel.setVisible( false );
        dokzContainer.revalidate();
    }

    private void alterDocking() {
    }

    private void setCursor( Cursor c ) {
        dokzContainer.setCursor( c );
    }

    @Override
    public void mouseExited( MouseEvent e ) {
        setCursor( Cursor.getDefaultCursor() );
    }

    @Override
    public void mousePressed( MouseEvent e ) {
        if ( canStartDocking( e ) ) {
            startDocking();
        }
    }

    @Override
    public void mouseReleased( MouseEvent e ) {
        if ( dockingStarted ) {
            endDocking();
        }
    }

    @Override
    public void mouseMoved( MouseEvent e ) {
    }

    @Override
    public void mouseDragged( MouseEvent e ) {
        if ( dockingStarted ) {
            alterDocking();
        }
    }

}
