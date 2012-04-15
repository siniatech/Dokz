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
import com.siniatech.dokz.layout.DokzLayoutManager;
import com.siniatech.dokz.resize.DokzResizeManager;
import com.siniatech.siniautils.fn.IResponse0;

public class DokzContainer extends JPanel {

    private final DokzContext dokzContext;
    private final DokzResizeManager dokzResizeManager;

    public DokzContainer( final DokzContext dokzContext ) {
        this.dokzContext = dokzContext;
        this.dokzResizeManager = new DokzResizeManager( new IResponse0() {
            @Override
            public void respond() {
                invalidate();
            }
        } );
        setLayout( new DokzLayoutManager( dokzContext ) );
        setBackground( Color.lightGray );
        addMouseMotionListener( new MouseMotionAdapter() {
            @Override
            public void mouseMoved( MouseEvent e ) {
                DokzNeighbourContext panelsAround = getPanelsAround( e.getPoint() );
                if ( panelsAround.getPoint() != null ) {
                    resetCursor();
                } else {
                    Cursor nsResizeCursor = Cursor.getPredefinedCursor( Cursor.N_RESIZE_CURSOR );
                    Cursor ewResizeCursor = Cursor.getPredefinedCursor( Cursor.E_RESIZE_CURSOR );
                    if ( panelsAround.getEast() != null && panelsAround.getWest() != null ) {
                        if ( getCursor() != ewResizeCursor ) {
                            setCursor( ewResizeCursor );
                        }
                    } else if ( panelsAround.getNorth() != null && panelsAround.getSouth() != null ) {
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
                if ( dokzResizeManager.isResizeStarted() ) {
                    dokzResizeManager.doResize( e.getPoint() );
                }
            }
        } );
        addMouseListener( new MouseAdapter() {
            @Override
            public void mouseExited( MouseEvent e ) {
                setCursor( Cursor.getDefaultCursor() );
            }

            @Override
            public void mousePressed( MouseEvent e ) {
                DokzNeighbourContext panelsAround = getPanelsAround( e.getPoint() );
                if ( dokzResizeManager.canStartResize( panelsAround ) ) {
                    dokzResizeManager.startResize( e.getPoint(), panelsAround, dokzContext.getPanelsIn( DokzContainer.this ) );
                }
            }

            @Override
            public void mouseReleased( MouseEvent e ) {
                if ( dokzResizeManager.isResizeStarted() ) {
                    dokzResizeManager.endResize();
                } else if ( e.getClickCount() > 1 ) {
                    System.out.println( "POP" );
                }
            }
        } );
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

    DokzPanel getPanelAt( int x, int y ) {
        return getPanelAt( new Point( x, y ) );
    }

    DokzPanel getPanelAt( Point point ) {
        for ( DokzPanel panel : dokzContext.getPanelsIn( this ) ) {
            if ( panel.getBounds().contains( point ) ) {
                return panel;
            }
        }
        return null;
    }

    private DokzNeighbourContext getPanelsAround( Point point ) {
        return new DokzNeighbourContext( //
            getPanelAt( point.x, point.y - defaultPanelGap ), //
            getPanelAt( point.x, point.y + defaultPanelGap ), //
            getPanelAt( point.x + defaultPanelGap, point.y ), //
            getPanelAt( point.x - defaultPanelGap, point.y ), //
            getPanelAt( point ) //
        );
    }
}
