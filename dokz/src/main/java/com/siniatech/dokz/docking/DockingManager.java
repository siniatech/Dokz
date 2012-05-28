package com.siniatech.dokz.docking;

import static javax.swing.SwingUtilities.*;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import com.siniatech.dokz.DokzConstants;
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

    private void alterDocking( Point p ) {
        determinePotentialDocking( p ).showPotentialPositioning( dokzContainer, dockingGlassPanel );
    }

    private IDocking determinePotentialDocking( Point p ) {
        if ( canDockNorth( p ) && canDockWest( p ) ) {
            return new NorthWestCornerDocking();
        } else if ( canDockNorth( p ) && canDockEast( p ) ) {
            return new NorthEastCornerDocking();
        } else if ( canDockSouth( p ) && canDockWest( p ) ) {
            return new SouthWestCornerDocking();
        } else if ( canDockSouth( p ) && canDockEast( p ) ) {
            return new SouthEastCornerDocking();
        } else if ( canDockNorth( p ) ) {
            return new NorthSideDocking();
        } else if ( canDockEast( p ) ) {
            return new EastSideDocking();
        } else if ( canDockWest( p ) ) {
            return new WestSideDocking();
        } else if ( canDockSouth( p ) ) {
            return new SouthSideDocking();
        } else {
            return new NoPossibleDocking();
        }
    }

    private boolean canDockNorth( Point p ) {
        return p.y < getBorderBoundary();
    }

    private int getBorderBoundary() {
        return DokzConstants.borderDockBoundary;
    }

    private boolean canDockSouth( Point p ) {
        return p.y > ( dokzContainer.getHeight() - getBorderBoundary() );
    }

    private boolean canDockWest( Point p ) {
        return p.x < getBorderBoundary();
    }

    private boolean canDockEast( Point p ) {
        return p.y > ( dokzContainer.getWidth() - getBorderBoundary() );
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
            alterDocking( e.getPoint() );
        }
    }

}