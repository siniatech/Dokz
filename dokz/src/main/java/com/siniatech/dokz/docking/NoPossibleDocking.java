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

import com.siniatech.dokz.DokzContainer;

public class NoPossibleDocking implements IDocking {

    @Override
    public void showPotentialPositioning( DokzContainer dokzContainer, DockingGlassPanel glassPanel ) {
        // do nothing
    }

    @Override
    public void applyDocking( DokzContainer dokzContainer ) {
        // do nothing
    }

}
