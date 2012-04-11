package com.siniatech.dokz;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

import javax.swing.JPanel;

import com.siniatech.dokz.context.DokzContext;
import com.siniatech.dokz.layout.DokzLayoutManager;

public class DokzContainer extends JPanel {

    private final DokzContext dokzContext;

    private Point resizeStartPoint = null;
    private int p1w;
    private int p2w;
    private int p2x;

    boolean isResizeStarted() {
        return resizeStartPoint != null;
    }

    void endResize() {
        resizeStartPoint = null;
    }

    void startResize( Point point ) {
        resizeStartPoint = point;
        List<DokzPanel> keySet = dokzContext.getPanels();
        DokzPanel p1 = keySet.get( 0 );
        DokzPanel p2 = keySet.get( 1 );
        p1w = p1.getWidth();
        p2w = p2.getWidth();
        p2x = p2.getX();
    }
    
    JPanel getPanelAt( Point point ) {
        for ( DokzPanel panel : dokzContext.getPanels() ) {
            if ( panel.getBounds().contains( point ) ) {
                return panel;
            }
        }
        return null;
    }
    
    public DokzContainer( final DokzContext dokzContext ) {
        this.dokzContext = dokzContext;
        setLayout( new DokzLayoutManager( dokzContext ) );
        setBackground( Color.lightGray );
        addMouseMotionListener( new MouseMotionAdapter() {
            @Override
            public void mouseMoved( MouseEvent e ) {
                Cursor ewResizeCursor = Cursor.getPredefinedCursor( Cursor.E_RESIZE_CURSOR );
                if ( getPanelAt( e.getPoint() ) == null && getCursor() != ewResizeCursor ) {
                    setCursor( ewResizeCursor );
                } else if ( getPanelAt( e.getPoint() ) != null && getCursor() != Cursor.getDefaultCursor() ) {
                    setCursor( Cursor.getDefaultCursor() );
                }
            }

            @Override
            public void mouseDragged( MouseEvent e ) {
                if ( !isResizeStarted() ) {
                    return;
                }

                int xMove = e.getPoint().x - resizeStartPoint.x;
                List<DokzPanel> keySet = dokzContext.getPanels();
                DokzPanel p1 = keySet.get( 0 );
                DokzPanel p2 = keySet.get( 1 );
                Rectangle p1b = p1.getBounds();
                Rectangle p2b = p2.getBounds();
                p1.setBounds( p1b.x, p1b.y, p1w + xMove, p1b.height );
                p2.setBounds( p2x + xMove, p2b.y, p2w - xMove, p2b.height );
                invalidate();
                p1.validate();
                p2.validate();
            }
        } );
        addMouseListener( new MouseAdapter() {
            @Override
            public void mouseExited( MouseEvent e ) {
                setCursor( Cursor.getDefaultCursor() );
            }

            @Override
            public void mousePressed( MouseEvent e ) {
                if ( getPanelAt( e.getPoint() ) == null ) {
                    startResize( e.getPoint() );
                }
            }

            @Override
            public void mouseReleased( MouseEvent e ) {
                if ( isResizeStarted() ) {
                    endResize();
                } else if ( e.getClickCount() > 1 ) {
                    System.out.println( "POP" );
                    // these listeners probably need be in the container

                }
            }
        } );
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
    
    
    

}
