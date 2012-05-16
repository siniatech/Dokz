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

    public DokzLayoutManager( DokzContext dokzContainerContext ) {
        this.dokzContext = dokzContainerContext;
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
        DokzContainer dokzContainer = (DokzContainer) parent;
        if ( hasMaximizedChild( dokzContainer ) ) {
            layoutWithMaximizedChild( dokzContainer );
        } else {
            restoreMaxedChildIfPresent( dokzContainer );
            layoutWithoutMaximizedChild( dokzContainer );
        }
    }

    private void restoreMaxedChildIfPresent( DokzContainer dokzContainer ) {
        if ( lastMaxedPanel != null ) {
            lastMaxedPanel._1().setBounds( lastMaxedPanel._2().getBounds() );
            dokzContainer.setComponentZOrder( lastMaxedPanel._1(), 5 );
            lastMaxedPanel = null;
        }
    }

    private boolean hasMaximizedChild( DokzContainer dokzContainer ) {
        return CollectionHelper.exists( getPanels( dokzContainer ), isMaximized() );
    }

    private IConditional<DokzPanel> isMaximized() {
        return new IConditional<DokzPanel>() {
            @Override
            public Boolean apply( DokzPanel t ) {
                return dokzContext.getPanelContext( t ).getState() == DokzPanelState.maxed;
            }
        };
    }

    private void layoutWithMaximizedChild( DokzContainer dokzContainer ) {
        DokzPanel maxedPanel = CollectionHelper.find( getPanels( dokzContainer ), isMaximized() );
        lastMaxedPanel = new Tuple2<>( maxedPanel, maxedPanel.getBounds() );
        maxedPanel.setBounds( 0, 0, dokzContainer.getWidth(), dokzContainer.getHeight() );
        dokzContainer.setComponentZOrder( maxedPanel, 0 );
    }

    private void layoutWithoutMaximizedChild( DokzContainer dokzContainer ) {
        Set<DokzPanel> currentComponents = getPanels( dokzContainer );
        if ( lastLaidOutComponents.equals( currentComponents ) ) {
            layoutSameComponents( dokzContainer );
        } else {
            layoutRemainingComponents( dokzContainer, currentComponents );
        }
        lastLaidOutComponents = currentComponents;
    }

    private void layoutRemainingComponents( Container parent, Set<DokzPanel> currentComponents ) {
        if ( currentComponents.size() > lastLaidOutComponents.size() ) {
            assert lastLaidOutComponents.isEmpty();
            tilingLayouter.doLayout( currentComponents, parent.getSize(), dokzContext.getPanelGap(), dokzContext.getPanelGap() );
        } else {
            removingLayouter.doLayout( currentComponents, parent.getSize(), dokzContext.getPanelGap(), dokzContext.getPanelGap() );
        }
    }

    private void layoutSameComponents( Container parent ) {
        if ( getExtentOfComponents( lastLaidOutComponents ) != parent.getSize() ) {
            scalingLayouter.doLayout( lastLaidOutComponents, parent.getSize(), dokzContext.getPanelGap(), dokzContext.getPanelGap() );
        }
        // else do nothing
    }

    private Set<DokzPanel> getPanels( DokzContainer parent ) {
        Set<DokzPanel> panels = new HashSet<>();
        for ( DokzPanel panel : dokzContext.getPanels() ) {
            if ( dokzContext.getPanelContext( panel ).isVisibleIn( parent ) ) {
                panels.add( panel );
            }
        }
        return panels;
    }

}
