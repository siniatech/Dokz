package com.siniatech.dokz;

import java.awt.Component;

import javax.swing.JPanel;

public class DokzContainer extends JPanel {

    private final DokzContainerContext dokzContext;

    public DokzContainer( DokzContainerContext dokzContext ) {
        this.dokzContext = dokzContext;
    }

    public Component add( DokzPanel comp ) {
        dokzContext.getPanelContext( comp ).setVisibleIn( this );
        return super.add( comp );
    }

    public void remove( DokzPanel comp ) {
        dokzContext.getPanelContext( comp ).setVisibleIn( null );
        super.remove( comp );
    }

}
