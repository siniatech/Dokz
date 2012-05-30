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
import com.siniatech.dokz.layout.TranslatingLayoutContext;

public class NorthSideDocking extends SideDocking {

    @Override
    protected Rectangle getDockingZone( DokzContainer dokzContainer, DokzPanel dockingPanel ) {
        Rectangle bounds = dokzContainer.getBounds();
        return new Rectangle( 0, 0, bounds.width, dockingPanel.getHeight() );
    }

    @Override
    protected Rectangle createDockedBounds( Rectangle oldDockingPanelBounds, Rectangle containerBounds ) {
        return new Rectangle( 0, 0, containerBounds.width, oldDockingPanelBounds.height );
    }

    @Override
    protected TranslatingLayoutContext createTranslatingLayoutContext( Rectangle oldDockingPanelBounds ) {
        return new TranslatingLayoutContext( 0, oldDockingPanelBounds.height );
    }

    @Override
    protected Dimension getScalingBounds( Rectangle oldDockingPanelBounds, Rectangle containerBounds ) {
        return new Dimension( containerBounds.width, containerBounds.height - oldDockingPanelBounds.height );
    }
}
