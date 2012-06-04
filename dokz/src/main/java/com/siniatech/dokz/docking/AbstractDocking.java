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
