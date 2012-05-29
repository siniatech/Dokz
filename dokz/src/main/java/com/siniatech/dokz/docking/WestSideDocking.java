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

import com.siniatech.dokz.DokzContainer;
import com.siniatech.dokz.DokzPanel;
import com.siniatech.dokz.context.DokzContext;
import com.siniatech.dokz.layout.ILayoutContext;
import com.siniatech.dokz.layout.TranslatingLayoutContext;

public class WestSideDocking extends SideDocking {

    @Override
    protected Rectangle getDockingZone( DokzContainer dokzContainer, DokzPanel dockingPanel ) {
        Rectangle bounds = dokzContainer.getBounds();
        return new Rectangle( 0, 0, dockingPanel.getWidth(), bounds.height );
    }

    @Override
    public void applyDocking( DokzContainer dokzContainer, DokzPanel dockingPanel ) {
        Rectangle oldDockingPanelBounds = dockingPanel.getBounds();
        Rectangle containerBounds = dokzContainer.getBounds();
        dokzContainer.remove( dockingPanel );
        DokzContext dokzContext = dokzContainer.getDokzContext();
        removingLayouter.doLayout( getPanels( dokzContainer ), dokzContainer.getSize(), dokzContext.getPanelGap(), dokzContext.getPanelGap() );
        scalingLayouter.doLayout( getPanels( dokzContainer ), new Dimension( containerBounds.width - oldDockingPanelBounds.width, containerBounds.height ) );
        ILayoutContext layoutContext = new TranslatingLayoutContext( oldDockingPanelBounds.width, 0 );
        translatingLayouter.doLayout( getPanels( dokzContainer ), dokzContainer.getSize(), dokzContext.getPanelGap(), dokzContext.getPanelGap(), layoutContext );
        dokzContainer.add( dockingPanel );
        dockingPanel.setBounds( 0, 0, oldDockingPanelBounds.width, containerBounds.height );
        applyNewLayout( dokzContainer );

    }
}
