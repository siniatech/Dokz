package com.siniatech.dokz.layout;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.siniatech.dokz.DokzContainer;
import com.siniatech.dokz.DokzPanel;
import com.siniatech.dokz.context.DokzContext;

public class BoundsInitializer {

    private static TileLayouter layouter = new TileLayouter();

    public void initalizeBounds( DokzContext dokzContext ) {
        for ( DokzContainer container : dokzContext.getContainers() ) {
            initializeContainer( container, dokzContext );
        }

    }

    private void initializeContainer( DokzContainer container, DokzContext dokzContext ) {
        Set<DokzPanel> panels = dokzContext.getPanelsIn( container );
        // arbitary order at the moment
        List<Component> sortedPanels = new ArrayList<Component>( panels );
        layouter.doLayout( sortedPanels, container.getSize(), 3, 3 );
        for ( DokzPanel panel : panels ) {
            dokzContext.getPanelContext( panel ).setBounds( panel.getBounds() );
        }

    }
}
