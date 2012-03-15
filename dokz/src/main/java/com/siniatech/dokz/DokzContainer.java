package com.siniatech.dokz;

import java.awt.GridLayout;
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
        container.setLayout( new GridLayout() );
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
