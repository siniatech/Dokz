package com.siniatech.dokz.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JMenuItem;

import com.siniatech.dokz.DokzConstants;
import com.siniatech.dokz.DokzContainer;
import com.siniatech.dokz.DokzPanel;
import com.siniatech.dokz.api.IDokzContext;

public class DokzContext implements IDokzContext {

    private Map<DokzPanel, DokzPanelContext> panels = new HashMap<>();
    private Set<DokzContainer> containers = new HashSet<>();
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

    public void add( DokzPanel panel, DokzContainer container, DokzPanelContext context ) {
        containers.add( container );
        panels.put( panel, context );
        container.add( panel );
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

    public Set<DokzContainer> getContainers() {
        return Collections.unmodifiableSet( containers );
    }

    public Set<DokzPanel> getPanelsIn( DokzContainer container ) {
        Set<DokzPanel> panels = new HashSet<>();
        for ( DokzPanel panel : getPanels() ) {
            if ( getPanelContext( panel ).isVisibleIn( container ) ) {
                panels.add( panel );
            }
        }
        return panels;
    }

    public int getPanelGap() {
        return DokzConstants.defaultPanelGap;
    }
}
