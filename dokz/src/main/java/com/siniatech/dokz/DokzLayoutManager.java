package com.siniatech.dokz;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

class DokzLayoutManager implements LayoutManager {

    private final DokzContainerContext dokzContainerContext;

    public DokzLayoutManager( DokzContainerContext dokzContainerContext ) {
        this.dokzContainerContext = dokzContainerContext;
    }

    @Override
    public void addLayoutComponent( String name, Component comp ) {
    }

    @Override
    public void removeLayoutComponent( Component comp ) {
    }

    @Override
    public Dimension preferredLayoutSize( Container parent ) {
        return new Dimension( 100, 100 );
    }

    @Override
    public Dimension minimumLayoutSize( Container parent ) {
        return new Dimension( 100, 100 );
    }

    @Override
    public void layoutContainer( Container parent ) {
        List<DokzPanel> panels = getPanels( (DokzContainer) parent );
        for ( DokzPanel panel : panels ) {
            panel.setBounds( dokzContainerContext.getPanelContext( panel ).getBounds() );
        }
    }

    private List<DokzPanel> getPanels( DokzContainer parent ) {
        List<DokzPanel> panels = new ArrayList<>();
        for ( DokzPanel panel : dokzContainerContext.getPanels() ) {
            if ( dokzContainerContext.getPanelContext( panel ).isVisibleIn( parent ) ) {
                panels.add( panel );
            }
        }
        return panels;
    }

}
