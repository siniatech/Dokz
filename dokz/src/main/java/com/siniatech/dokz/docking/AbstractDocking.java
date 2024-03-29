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

import java.util.HashSet;
import java.util.Set;

import com.siniatech.dokz.DokzContainer;
import com.siniatech.dokz.DokzPanel;
import com.siniatech.dokz.context.DokzContext;
import com.siniatech.dokz.layout.ILayouter;
import com.siniatech.dokz.layout.RemovingLayouter;
import com.siniatech.dokz.layout.ScalingLayouter;
import com.siniatech.dokz.layout.TranslatingLayouter;

abstract public class AbstractDocking implements IDocking {

    static protected ILayouter removingLayouter = new RemovingLayouter();
    static protected ILayouter scalingLayouter = new ScalingLayouter();
    static protected ILayouter translatingLayouter = new TranslatingLayouter();

    static protected Set<DokzPanel> getPanels( DokzContainer dokzContainer ) {
        Set<DokzPanel> panels = new HashSet<>();
        DokzContext dokzContext = dokzContainer.getDokzContext();
        for ( DokzPanel panel : dokzContext.getPanels() ) {
            if ( dokzContext.getPanelContext( panel ).isVisibleIn( dokzContainer ) ) {
                panels.add( panel );
            }
        }
        return panels;
    }
}
