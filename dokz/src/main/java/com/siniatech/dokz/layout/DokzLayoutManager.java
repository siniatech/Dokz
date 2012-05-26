package com.siniatech.dokz.layout;

import static com.siniatech.siniautils.swing.BoundsHelper.*;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.siniatech.dokz.DokzContainer;
import com.siniatech.dokz.DokzPanel;
import com.siniatech.dokz.DokzPanelState;
import com.siniatech.dokz.context.DokzContext;
import com.siniatech.siniautils.collection.CollectionHelper;
import com.siniatech.siniautils.fn.IConditional;
import com.siniatech.siniautils.fn.Tuple2;

public class DokzLayoutManager implements LayoutManager {

    static private final ILayouter scalingLayouter = new ScalingLayouter();
    static private final ILayouter tilingLayouter = new TilingLayouter();
    static private final ILayouter removingLayouter = new RemovingLayouter();

    private final DokzContext dokzContext;

    private Collection<DokzPanel> lastLaidOutComponents;
    private Tuple2<DokzPanel, Rectangle> lastMaxedPanel;
    private final DokzContainer dokzContainer;

    public DokzLayoutManager( DokzContainer dokzContainer, DokzContext dokzContext ) {
        this.dokzContainer = dokzContainer;
        this.dokzContext = dokzContext;
        this.lastLaidOutComponents = new HashSet<>();
    }

    @Override
    public void addLayoutComponent( String name, Component comp ) {
    }

    @Override
    public void removeLayoutComponent( Component comp ) {
    }

    @Override
    public Dimension preferredLayoutSize( Container parent ) {
        return getExtentOfComponents( lastLaidOutComponents );
    }

    @Override
    public Dimension minimumLayoutSize( Container parent ) {
        return new Dimension( 600, 600 );
    }

    @Override
    public void layoutContainer( Container parent ) {
        assert dokzContainer == parent;
        if ( hasMaximizedChild() ) {
            layoutWithMaximizedChild();
        } else {
            restoreMaxedChildIfPresent();
            layoutWithoutMaximizedChild();
        }
    }

    private void restoreMaxedChildIfPresent() {
        if ( lastMaxedPanel != null ) {
            lastMaxedPanel._1().setBounds( lastMaxedPanel._2().getBounds() );
            dokzContainer.setComponentZOrder( lastMaxedPanel._1(), 5 );
            lastMaxedPanel = null;
        }
    }

    private boolean hasMaximizedChild() {
        return CollectionHelper.exists( getPanels(), isMaximized() );
    }

    private IConditional<DokzPanel> isMaximized() {
        return new IConditional<DokzPanel>() {
            @Override
            public Boolean apply( DokzPanel t ) {
                return dokzContext.getPanelContext( t ).getState() == DokzPanelState.maxed;
            }
        };
    }

    private void layoutWithMaximizedChild() {
        DokzPanel maxedPanel = CollectionHelper.find( getPanels(), isMaximized() );
        lastMaxedPanel = new Tuple2<>( maxedPanel, maxedPanel.getBounds() );
        maxedPanel.setBounds( 0, 0, dokzContainer.getWidth(), dokzContainer.getHeight() );
        dokzContainer.setComponentZOrder( maxedPanel, 0 );
    }

    private void layoutWithoutMaximizedChild() {
        Set<DokzPanel> currentComponents = getPanels();
        if ( lastLaidOutComponents.equals( currentComponents ) ) {
            layoutSameComponents();
        } else {
            layoutRemainingComponents( currentComponents );
        }
        lastLaidOutComponents = currentComponents;
    }

    private void layoutRemainingComponents( Set<DokzPanel> currentComponents ) {
        if ( currentComponents.size() > lastLaidOutComponents.size() ) {
            assert lastLaidOutComponents.isEmpty();
            tilingLayouter.doLayout( currentComponents, dokzContainer.getSize(), dokzContext.getPanelGap(), dokzContext.getPanelGap() );
        } else {
            removingLayouter.doLayout( currentComponents, dokzContainer.getSize(), dokzContext.getPanelGap(), dokzContext.getPanelGap() );
        }
    }

    private void layoutSameComponents() {
        if ( getExtentOfComponents( lastLaidOutComponents ) != dokzContainer.getSize() ) {
            scalingLayouter.doLayout( lastLaidOutComponents, dokzContainer.getSize(), dokzContext.getPanelGap(), dokzContext.getPanelGap() );
        }
        // else do nothing
    }

    private Set<DokzPanel> getPanels() {
        Set<DokzPanel> panels = new HashSet<>();
        for ( DokzPanel panel : dokzContext.getPanels() ) {
            if ( dokzContext.getPanelContext( panel ).isVisibleIn( dokzContainer ) ) {
                panels.add( panel );
            }
        }
        return panels;
    }

}
