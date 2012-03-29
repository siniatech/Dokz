package com.siniatech.dokz.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.siniatech.dokz.DokzContainer;
import com.siniatech.dokz.DokzPanel;
import com.siniatech.dokz.context.DokzContext;

public class DokzLayoutManager implements LayoutManager {

    private final DokzContext dokzContainerContext;

    public DokzLayoutManager( DokzContext dokzContainerContext ) {
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
        int maxX = 0, maxY = 0;
        for ( DokzPanel panel : getPanels( (DokzContainer) parent ) ) {
            Rectangle bounds = dokzContainerContext.getPanelContext( panel ).getBounds();
            maxX = ( bounds.x + bounds.width ) > maxX ? bounds.x + bounds.width : maxX;
            maxY = ( bounds.y + bounds.height ) > maxY ? bounds.y + bounds.height : maxY;
        }
        return new Dimension( maxX, maxY );
    }

    @Override
    public Dimension minimumLayoutSize( Container parent ) {
        return new Dimension( 600, 600 );
    }

    @Override
    public void layoutContainer( Container parent ) {
        for ( DokzPanel panel : getPanels( (DokzContainer) parent ) ) {
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
