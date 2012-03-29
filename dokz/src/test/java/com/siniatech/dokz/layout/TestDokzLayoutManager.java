package com.siniatech.dokz.layout;

import static junit.framework.Assert.*;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

import com.siniatech.dokz.DokzContainer;
import com.siniatech.dokz.DokzManager;
import com.siniatech.dokz.DokzPanel;
import com.siniatech.dokz.context.DokzContext;

public class TestDokzLayoutManager {

    private DokzContainer mainContainer;
    private DokzLayoutManager layoutManager;

    @Before
    public void setUp() {
        DokzManager dokz = new DokzManager();
        dokz.add( new JPanel() );
        dokz.add( new JPanel() );
        dokz.add( new JPanel() );
        DokzContext dokzContext = (DokzContext) dokz.getDokzContext();
        List<DokzPanel> panels = dokzContext.getPanels();
        dokzContext.getPanelContext( panels.get( 0 ) ).setBounds( new Rectangle( 0, 0, 500, 600 ) );
        dokzContext.getPanelContext( panels.get( 1 ) ).setBounds( new Rectangle( 500, 0, 500, 200 ) );
        dokzContext.getPanelContext( panels.get( 2 ) ).setBounds( new Rectangle( 500, 200, 500, 400 ) );
        mainContainer = dokzContext.getMainContainer();
        layoutManager = new DokzLayoutManager( dokzContext );
    }

    @Test
    public void canAddLayoutComponent() {
        // smoke test
        layoutManager.addLayoutComponent( "test", new JPanel() );
    }

    @Test
    public void canRemoveLayoutComponent() {
        // smoke test - note it doesn't matter that it's not been added
        layoutManager.removeLayoutComponent( new JPanel() );
    }

    @Test
    public void checkPreferredLayoutSize() {
        assertEquals( new Dimension( 1000, 600 ), layoutManager.preferredLayoutSize( mainContainer ) );
    }

    @Test
    public void checkLayoutOfMainContainer() {
        layoutManager.layoutContainer( mainContainer );
        DokzContext dokzContext = mainContainer.getDokzContext();
        for ( DokzPanel panel : dokzContext.getPanels() ) {
            assertEquals( dokzContext.getPanelContext( panel ).getBounds(), panel.getBounds() );
        }
    }

    // TODO - test popped out panels are not laid out
}
