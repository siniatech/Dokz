package com.siniatech.dokz;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.siniatech.siniautils.fn.IResponse0;
import com.siniatech.siniautils.swing.IAmJComponent;

public class DokzContainer implements IAmJComponent {

    private JPanel container;
    private Map<DokzPanel, DokzPanelContext> panels = new HashMap<>();

    public DokzContainer() {
        container = new JPanel();
        container.setLayout( new GridLayout( 1, 3, 2, 2 ) );
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
                ArrayList<DokzPanel> keySet = new ArrayList<DokzPanel>( panels.keySet() );
                DokzPanel p1 = keySet.get( 0 );
                DokzPanel p2 = keySet.get( 1 );
                Rectangle p1b = p1.getBounds();
                Rectangle p2b = p2.getBounds();
                p1.setBounds( p1b.x, p1b.y, p1w - xMove, p1b.height );
                p2.setBounds( p2x + xMove, p2b.y, p2b.width, p2b.height );
                container.invalidate();
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

    //////////// EXTRACT TO RESIZE MANAGER 

    private Point resizeStartPoint = null;
    private int p1w;
    private int p2x;

    private boolean isResizeStarted() {
        return resizeStartPoint != null;
    }

    private void endResize() {
        resizeStartPoint = null;
    }

    private void startResize( Point point ) {
        resizeStartPoint = point;
        ArrayList<DokzPanel> keySet = new ArrayList<DokzPanel>( panels.keySet() );
        DokzPanel p1 = keySet.get( 0 );
        DokzPanel p2 = keySet.get( 1 );
        p1w = p1.getWidth();
        p2x = p2.getX();
    }

    /////////

    private JPanel getPanelAt( Point point ) {
        for ( DokzPanel panel : panels.keySet() ) {
            if ( panel.getBounds().contains( point ) ) {
                return panel;
            }
        }
        return null;
    }

    JComponent createButtonBarFor( final DokzPanel dokzPanel, String title ) {
        assert panels.keySet().contains( dokzPanel );

        IResponse0 onMax = new IResponse0() {
            @Override
            public void respond() {
                DokzPanelContext context = panels.get( dokzPanel );
                if ( context.getState() == DokzPanelState.open ) {
                    context.setState( DokzPanelState.maxed );
                    for ( DokzPanel panel : panels.keySet() ) {
                        if ( panel != dokzPanel && panels.get( panel ).getState() == DokzPanelState.open ) {
                            container.remove( panel );
                        }
                    }
                } else {
                    assert context.getState() == DokzPanelState.maxed;
                    context.setState( DokzPanelState.open );
                    for ( DokzPanel panel : panels.keySet() ) {
                        if ( panel != dokzPanel && panels.get( panel ).getState() == DokzPanelState.open ) {
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
        panels.get( dokzPanel ).setState( DokzPanelState.closed );
    }

    private void open( final DokzPanel dokzPanel ) {
        container.add( dokzPanel );
        container.validate();
        panels.get( dokzPanel ).setState( DokzPanelState.open );
    }

    public void add( JComponent component, String title ) {
        final DokzPanel dokzPanel = new DokzPanel( this, component, title );
        container.add( dokzPanel );
        panels.put( dokzPanel, new DokzPanelContext( title, new IResponse0() {
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
    }

    public void add( JComponent component ) {
        add( component, null );
    }

    public List<JMenuItem> getPanelStateMenuItems() {
        List<JMenuItem> panelStateMenuItems = new ArrayList<>();
        for ( DokzPanelContext dokzPanelContext : panels.values() ) {
            panelStateMenuItems.add( dokzPanelContext.getPanelStateMenuItem() );
        }
        return panelStateMenuItems;
    }
}
