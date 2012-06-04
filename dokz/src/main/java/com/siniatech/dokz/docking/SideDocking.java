/*******************************************************************************
 * Dokz
 * Copyright (c) 2011-2 Siniatech Ltd  http://www.siniatech.com/products/dokz
 *
 * All rights reserved. This project and the accompanying materials are made 
 * available under the terms of the MIT License which can be found in the root  
 * of the project, and at http://www.opensource.org/licenses/mit-license.php
 *
 ******************************************************************************/
package com.siniatech.dokz.docking;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Set;

import com.siniatech.dokz.DokzContainer;
import com.siniatech.dokz.DokzPanel;
import com.siniatech.dokz.context.DokzContext;
import com.siniatech.dokz.layout.DokzLayoutManager;
import com.siniatech.dokz.layout.TranslatingLayoutContext;

abstract public class SideDocking extends AbstractDocking {

    @Override
    public void showPotentialPositioning( DokzContainer dokzContainer, DockingGlassPanel glassPanel, DokzPanel dockingPanel ) {
        glassPanel.setPotentialDockingZone( getDockingZone( dokzContainer, dockingPanel ) );
    }

    abstract protected Rectangle getDockingZone( DokzContainer dokzContainer, DokzPanel dockingPanel );

    @Override
    public void applyDocking( DokzContainer dokzContainer, DokzPanel dockingPanel ) {
        Rectangle oldDockingPanelBounds = dockingPanel.getBounds();
        Rectangle containerBounds = dokzContainer.getBounds();
        dokzContainer.remove( dockingPanel );
        DokzContext dokzContext = dokzContainer.getDokzContext();
        Set<DokzPanel> panels = getPanels( dokzContainer );
        int panelGap = dokzContext.getPanelGap();
        removingLayouter.doLayout( panels, dokzContainer.getSize(), panelGap, panelGap );
        scalingLayouter.doLayout( panels, getScalingBounds( oldDockingPanelBounds, containerBounds, panelGap, panelGap ), panelGap, panelGap );
        translatingLayouter.doLayout( panels, dokzContainer.getSize(), panelGap, panelGap, createTranslatingLayoutContext( oldDockingPanelBounds, panelGap, panelGap ) );
        dokzContainer.add( dockingPanel );
        dockingPanel.setBounds( createDockedBounds( oldDockingPanelBounds, containerBounds, panelGap, panelGap ) );
        applyNewLayout( dokzContainer );
    }

    abstract protected Rectangle createDockedBounds( Rectangle oldDockingPanelBounds, Rectangle containerBounds, int hGap, int vGap );

    abstract protected TranslatingLayoutContext createTranslatingLayoutContext( Rectangle oldDockingPanelBounds, int hGap, int vGap );

    abstract protected Dimension getScalingBounds( Rectangle oldDockingPanelBounds, Rectangle containerBounds, int hGap, int vGap );

    static protected void applyNewLayout( DokzContainer dokzContainer ) {
        DokzLayoutManager layoutManager = (DokzLayoutManager) dokzContainer.getLayout();
        layoutManager.applyCurrentLayout();
    }

}
