package com.siniatech.dokz.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JMenuItem;

import com.siniatech.dokz.DokzContainer;
import com.siniatech.dokz.DokzPanel;

public class DokzContext implements IDokzContext {

    private Map<DokzPanel, DokzPanelContext> panels = new HashMap<>();
    private DokzContainer mainContainer;

    public void setMainContainer( DokzContainer mainContainer ) {
        this.mainContainer = mainContainer;
    }

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

    public DokzContainer getMainContainer() {
        return mainContainer;
    }
}
