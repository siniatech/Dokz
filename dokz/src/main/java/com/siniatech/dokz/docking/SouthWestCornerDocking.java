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

import static com.siniatech.siniautils.swing.BoundsHelper.*;

import java.awt.Component;
import java.awt.Rectangle;

import com.siniatech.dokz.DokzContainer;
import com.siniatech.dokz.DokzPanel;

public class SouthWestCornerDocking extends CornerDocking {

    @Override
    protected Rectangle getDockingZone( DokzContainer dokzContainer, DokzPanel dockingPanel ) {
        Component bottomLeftmostComponent = getBottomLeftmostComponent( getPanels( dokzContainer ) );
        return bottomLeftmostComponent.getBounds();
    }

}
