package com.siniatech.dokz;

import static com.siniatech.dokz.DokzConstants.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

import com.siniatech.dokz.context.DokzContext;
import com.siniatech.dokz.docking.DockingManager;
import com.siniatech.dokz.layout.DokzLayoutManager;
import com.siniatech.dokz.resize.ResizeManager;
import com.siniatech.siniautils.fn.IResponse0;

public class DokzContainer extends JPanel {

    private final DokzContext dokzContext;
    private final ResizeManager resizeManager;
    private final DockingManager dockingManager;

    public DokzContainer( final DokzContext dokzContext ) {
        this.dokzContext = dokzContext;
        this.resizeManager = new ResizeManager( new IResponse0() {
            @Override
            public void respond() {
                invalidate();
            }
        } );
        this.dockingManager = new DockingManager( this );
        setLayout( new DokzLayoutManager( dokzContext ) );
        setBackground( Color.lightGray );
        addMouseMotionListener( new ResizeMotionListener() );
        addMouseListener( new ResizeListener() );
        addMouseMotionListener( dockingManager );
        addMouseListener( dockingManager );
    }

    private void resetCursor() {
        if ( getCursor() != Cursor.getDefaultCursor() ) {
            setCursor( Cursor.getDefaultCursor() );
        }
    }

    public Component add( DokzPanel comp ) {
        dokzContext.getPanelContext( comp ).setVisibleIn( this );
        return super.add( comp );
    }

    public void remove( DokzPanel comp ) {
        dokzContext.getPanelContext( comp ).setVisibleIn( null );
        super.remove( comp );
    }

    public DokzContext getDokzContext() {
        return dokzContext;
    }

    public DokzPanel getPanelAt( int x, int y ) {
        return getPanelAt( new Point( x, y ) );
    }

    public DokzPanel getPanelAt( Point point ) {
        for ( DokzPanel panel : dokzContext.getPanelsIn( this ) ) {
            if ( panel.getBounds().contains( point ) ) {
                return panel;
            }
        }
        return null;
    }

    private NeighbourContext getPanelsAround( Point point ) {
        return new NeighbourContext( //
            getPanelAt( point.x, point.y - defaultPanelGap ), //
            getPanelAt( point.x, point.y + defaultPanelGap ), //
            getPanelAt( point.x + defaultPanelGap, point.y ), //
            getPanelAt( point.x - defaultPanelGap, point.y ), //
            getPanelAt( point ) //
        );
    }

    private final class ResizeListener extends MouseAdapter {
        @Override
        public void mouseExited( MouseEvent e ) {
            setCursor( Cursor.getDefaultCursor() );
        }

        @Override
        public void mousePressed( MouseEvent e ) {
            NeighbourContext panelsAround = getPanelsAround( e.getPoint() );
            if ( resizeManager.canStartResize( panelsAround ) ) {
                resizeManager.startResize( e.getPoint(), panelsAround, dokzContext.getPanelsIn( DokzContainer.this ) );
            }
        }

        @Override
        public void mouseReleased( MouseEvent e ) {
            if ( resizeManager.isResizeStarted() ) {
                resizeManager.endResize();
            }
        }
    }

    private final class ResizeMotionListener extends MouseMotionAdapter {
        @Override
        public void mouseMoved( MouseEvent e ) {
            NeighbourContext panelsAround = getPanelsAround( e.getPoint() );
            if ( panelsAround.getPoint() != null ) {
                resetCursor();
            } else {
                if ( panelsAround.getEast() != null && panelsAround.getWest() != null ) {
                    Cursor ewResizeCursor = Cursor.getPredefinedCursor( Cursor.E_RESIZE_CURSOR );
                    if ( getCursor() != ewResizeCursor ) {
                        setCursor( ewResizeCursor );
                    }
                } else if ( panelsAround.getNorth() != null && panelsAround.getSouth() != null ) {
                    Cursor nsResizeCursor = Cursor.getPredefinedCursor( Cursor.N_RESIZE_CURSOR );
                    if ( getCursor() != nsResizeCursor ) {
                        setCursor( nsResizeCursor );
                    }
                } else {
                    resetCursor();
                }
            }
        }

        @Override
        public void mouseDragged( MouseEvent e ) {
            if ( resizeManager.isResizeStarted() ) {
                resizeManager.doResize( e.getPoint() );
            }
        }
    }

}
