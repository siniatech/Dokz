package com.siniatech.dokz;

import java.awt.Component;

import javax.swing.JPanel;

import com.siniatech.dokz.context.DokzContext;

public class DokzContainer extends JPanel {

    private final DokzContext dokzContext;

    public DokzContainer( DokzContext dokzContext ) {
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
    
    public DokzContext getDokzContext() {
        return dokzContext;
    }

}
