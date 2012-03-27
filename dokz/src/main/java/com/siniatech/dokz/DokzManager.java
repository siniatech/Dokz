package com.siniatech.dokz;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.siniatech.siniautils.fn.IResponse0;
import com.siniatech.siniautils.swing.IAmJComponent;

public class DokzManager implements IAmJComponent {

    private DokzContainer container;
    private DokzContainerContext dokzContext;

    public DokzManager() {
        dokzContext = new DokzContainerContext();
        container = new DokzContainer( dokzContext );
        container.setLayout( new DokzLayoutManager( dokzContext ) );
        container.setBackground( Color.white );
        container.addMouseMotionListener( new MouseMotionAdapter() {
            @Override
            public void mouseMoved( MouseEvent e ) {
                Cursor ewResizeCursor = Cursor.getPredefinedCursor( Cursor.E_RESIZE_CURSOR );
                if ( getPanelAt( e.getPoint() ) == null && container.getCursor() != ewResizeCursor ) {
                    container.setCursor( ewResizeCursor );
                } else if ( container.getCursor() != Cursor.getDefaultCursor() ) {
                    container.setCursor( Cursor.getDefaultCursor() );
                }
            }

            @Override
            public void mouseDragged( MouseEvent e ) {
                if ( !isResizeStarted() ) {
                    return;
                }

                int xMove = e.getPoint().x - resizeStartPoint.x;
                List<DokzPanel> keySet = dokzContext.getPanels();
                DokzPanel p1 = keySet.get( 1 );
                DokzPanel p2 = keySet.get( 0 );
                Rectangle p1b = p1.getBounds();
                Rectangle p2b = p2.getBounds();
                p1.setBounds( p1b.x, p1b.y, p1w + xMove, p1b.height );
                p2.setBounds( p2x + xMove, p2b.y, p2w - xMove, p2b.height );
                container.invalidate();
                p1.validate();
                p2.validate();
            }
        } );
        container.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseExited( MouseEvent e ) {
                container.setCursor( Cursor.getDefaultCursor() );
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
                }
            }
        } );
    }

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
        DokzPanel p1 = keySet.get( 1 );
        DokzPanel p2 = keySet.get( 0 );
        p1w = p1.getWidth();
        p2w = p2.getWidth();
        p2x = p2.getX();
    }

    /////////

    JPanel getPanelAt( Point point ) {
        for ( DokzPanel panel : dokzContext.getPanels() ) {
            if ( panel.getBounds().contains( point ) ) {
                return panel;
            }
        }
        return null;
    }

    JComponent createButtonBarFor( final DokzPanel dokzPanel, String title ) {
        assert dokzContext.getPanels().contains( dokzPanel );

        IResponse0 onMax = new IResponse0() {
            @Override
            public void respond() {
                DokzPanelContext context = dokzContext.getPanelContext( dokzPanel );
                if ( context.getState() == DokzPanelState.open ) {
                    context.setState( DokzPanelState.maxed );
                    for ( DokzPanel panel : dokzContext.getPanels() ) {
                        if ( panel != dokzPanel && dokzContext.getPanelContext( panel ).getState() == DokzPanelState.open ) {
                            container.remove( panel );
                        }
                    }
                } else {
                    assert context.getState() == DokzPanelState.maxed;
                    context.setState( DokzPanelState.open );
                    for ( DokzPanel panel : dokzContext.getPanels() ) {
                        if ( panel != dokzPanel && dokzContext.getPanelContext( panel ).getState() == DokzPanelState.open ) {
                            container.add( panel );
                        }
                    }
                }
                container.revalidate();
            }
        };
        IResponse0 onClose = new IResponse0() {
            @Override
            public void respond() {
                close( dokzPanel );
            }
        };
        IResponse0 onMin = new IResponse0() {
            @Override
            public void respond() {
                System.out.println( "MIN" );
            }
        };
        return new DokzButtonBar( title, onMin, onMax, onClose );
    }

    @Override
    public JComponent asJComponent() {
        return container;
    }

    private void close( final DokzPanel dokzPanel ) {
        container.remove( dokzPanel );
        container.revalidate();
        dokzContext.getPanelContext( dokzPanel ).setState( DokzPanelState.closed );
    }

    private void open( final DokzPanel dokzPanel ) {
        container.add( dokzPanel );
        container.validate();
        dokzContext.getPanelContext( dokzPanel ).setState( DokzPanelState.open );
    }

    public void add( JComponent component, String title ) {
        final DokzPanel dokzPanel = new DokzPanel( this, component, title );
        dokzContext.add( dokzPanel, new DokzPanelContext( title, new IResponse0() {
            @Override
            public void respond() {
                open( dokzPanel );
            }
        }, new IResponse0() {
            @Override
            public void respond() {
                close( dokzPanel );
            }
        } ) );
        container.add( dokzPanel );
    }

    public void add( JComponent component ) {
        add( component, null );
    }

    public IDokzContext getDokzContext() {
        return dokzContext;
    }
}
