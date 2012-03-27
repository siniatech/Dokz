package com.siniatech.dokz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JMenuItem;

public class DokzContainerContext implements IDokzContext {

    private Map<DokzPanel, DokzPanelContext> panels = new HashMap<>();

    public List<DokzPanel> getPanels() {
        return new ArrayList<>( panels.keySet() );
    }

    public DokzPanelContext getPanelContext( DokzPanel panel ) {
        return panels.get( panel );
    }

    public void add( DokzPanel panel, DokzPanelContext context ) {
        panels.put( panel, context );
    }

    @Override
    public List<JMenuItem> getPanelStateMenuItems() {
        List<JMenuItem> panelStateMenuItems = new ArrayList<>();
        for ( DokzPanelContext dokzPanelContext : panels.values() ) {
            panelStateMenuItems.add( dokzPanelContext.getPanelStateMenuItem() );
        }
        return panelStateMenuItems;
    }
}
