package com.siniatech.dokz.resize;

import static com.siniatech.dokz.DokzConstants.*;
import static com.siniatech.siniautils.collection.CollectionHelper.*;
import static com.siniatech.siniautils.collection.SetHelper.*;
import static com.siniatech.siniautils.swing.BoundsHelper.*;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.siniatech.dokz.DokzNeighbourContext;
import com.siniatech.dokz.DokzPanel;
import com.siniatech.siniautils.fn.IResponse0;
import com.siniatech.siniautils.fn.IResponse1;

public class DokzResizeManager {

    private final IResponse0 invalidateResponse;
    private final Map<DokzPanel, Rectangle> westNorthPanels = new HashMap<>();
    private final Map<DokzPanel, Rectangle> eastSouthPanels = new HashMap<>();

    private Point startPoint;
    private Direction direction;

    public DokzResizeManager( IResponse0 invalidateResponse ) {
        this.invalidateResponse = invalidateResponse;
    }

    public boolean isResizeStarted() {
        return startPoint != null;
    }

    public void endResize() {
        startPoint = null;
        direction = null;
        westNorthPanels.clear();
        eastSouthPanels.clear();
    }

    public void startResize( Point startPoint, DokzNeighbourContext panelsAround, Set<DokzPanel> panels ) {
        assert canStartResize( panelsAround );
        this.startPoint = startPoint;
        this.direction = isEwResize( panelsAround ) ? Direction.ew : Direction.ns;

        if ( direction == Direction.ew ) {
            addAdjacentPanelsOnNsAxis( panelsAround.getWest(), Direction.e, panels, westNorthPanels );
            addAdjacentPanelsOnNsAxis( panelsAround.getEast(), Direction.w, panels, eastSouthPanels );
        } else {
            addAdjacentPanelsOnEwAxis( panelsAround.getNorth(), Direction.n, panels, westNorthPanels );
            addAdjacentPanelsOnEwAxis( panelsAround.getSouth(), Direction.s, panels, eastSouthPanels );
        }
    }

    private void addAdjacentPanelsOnNsAxis( DokzPanel panel, Direction side, Set<DokzPanel> panels, Map<DokzPanel, Rectangle> panelToBounds ) {
        panelToBounds.put( panel, panel.getBounds() );
        addAdjacentPanelsOnNAxis( panel, side, panels, panelToBounds );
        addAdjacentPanelsOnSAxis( panel, side, panels, panelToBounds );
    }

    private void addAdjacentPanelsOnEwAxis( DokzPanel panel, Direction side, Set<DokzPanel> panels, Map<DokzPanel, Rectangle> panelToBounds ) {
        panelToBounds.put( panel, panel.getBounds() );
        addAdjacentPanelsOnEAxis( panel, side, panels, panelToBounds );
        addAdjacentPanelsOnWAxis( panel, side, panels, panelToBounds );
    }

    private void addAdjacentPanelsOnEAxis( DokzPanel panel, final Direction side, final Set<DokzPanel> panels, final Map<DokzPanel, Rectangle> panelToBounds ) {
        int x = getCoordOfPanelSide( panel, Direction.e ) + defaultPanelGap + 1;
        int y = getCoordOfPanelSide( panel, side );
        addAdjacentPanels( side, panels, panelToBounds, x, y, y, new IResponse1<DokzPanel>() {
            @Override
            public void respond( DokzPanel adjacentPanel ) {
                addAdjacentPanelsOnEAxis( adjacentPanel, side, panels, panelToBounds );
            }
        } );
    }

    private void addAdjacentPanelsOnWAxis( DokzPanel panel, final Direction side, final Set<DokzPanel> panels, final Map<DokzPanel, Rectangle> panelToBounds ) {
        int x = getCoordOfPanelSide( panel, Direction.w ) - defaultPanelGap - 1;
        int y = getCoordOfPanelSide( panel, side );
        addAdjacentPanels( side, panels, panelToBounds, x, y, y, new IResponse1<DokzPanel>() {
            @Override
            public void respond( DokzPanel adjacentPanel ) {
                addAdjacentPanelsOnWAxis( adjacentPanel, side, panels, panelToBounds );
            }
        } );
    }

    private void addAdjacentPanelsOnSAxis( DokzPanel panel, final Direction side, final Set<DokzPanel> panels, final Map<DokzPanel, Rectangle> panelToBounds ) {
        int x = getCoordOfPanelSide( panel, side );
        int y = getCoordOfPanelSide( panel, Direction.s ) + defaultPanelGap + 1;
        addAdjacentPanels( side, panels, panelToBounds, x, y, x, new IResponse1<DokzPanel>() {
            @Override
            public void respond( DokzPanel adjacentPanel ) {
                addAdjacentPanelsOnSAxis( adjacentPanel, side, panels, panelToBounds );
            }
        } );
    }

    private void addAdjacentPanelsOnNAxis( DokzPanel panel, final Direction side, final Set<DokzPanel> panels, final Map<DokzPanel, Rectangle> panelToBounds ) {
        int x = getCoordOfPanelSide( panel, side );
        int y = getCoordOfPanelSide( panel, Direction.n ) - defaultPanelGap - 1;
        addAdjacentPanels( side, panels, panelToBounds, x, y, x, new IResponse1<DokzPanel>() {
            @Override
            public void respond( DokzPanel adjacentPanel ) {
                addAdjacentPanelsOnNAxis( adjacentPanel, side, panels, panelToBounds );
            }
        } );
    }

    private void addAdjacentPanels( final Direction side, final Set<DokzPanel> panels, final Map<DokzPanel, Rectangle> panelToBounds, int x, int y, int z, IResponse1<DokzPanel> response ) {
        Collection<DokzPanel> componentsContaining = getComponentsContaining( panels, new Point( x, y ) );
        if ( !componentsContaining.isEmpty() ) {
            assert componentsContaining.size() == 1;
            DokzPanel adjacentPanel = getArbitraryMember( componentsContaining );
            if ( getCoordOfPanelSide( adjacentPanel, side ) == z ) {
                panelToBounds.put( adjacentPanel, adjacentPanel.getBounds() );
                response.respond( adjacentPanel );
            }
        }
    }

    private int getCoordOfPanelSide( DokzPanel panel, Direction side ) {
        Rectangle bounds = panel.getBounds();
        switch ( side ) {
            case w :
                return bounds.x;
            case e :
                return (int) ( bounds.getMaxX() - 1 );
            case n :
                return bounds.y;
            case s :
                return (int) ( bounds.getMaxY() - 1 );
            default:
                throw new IllegalStateException( "Direction must be singular" );
        }
    }

    public void doResize( Point point ) {
        if ( direction == Direction.ew ) {
            doEwBoundsCalc( point );
        } else {
            doNsBoundsCalc( point );
        }
        invalidateResponse.respond();
        for ( DokzPanel panel : union( westNorthPanels.keySet(), eastSouthPanels.keySet() ) ) {
            panel.validate();
        }
    }

    private void doNsBoundsCalc( Point point ) {
        if ( canDoNsResize( point ) ) {
            int yMove = point.y - startPoint.y;
            for ( DokzPanel panel : westNorthPanels.keySet() ) {
                Rectangle origBounds = westNorthPanels.get( panel );
                panel.setBounds( origBounds.x, origBounds.y, origBounds.width, origBounds.height + yMove );
            }
            for ( DokzPanel panel : eastSouthPanels.keySet() ) {
                Rectangle origBounds = eastSouthPanels.get( panel );
                panel.setBounds( origBounds.x, origBounds.y + yMove, origBounds.width, origBounds.height - yMove );
            }
        }
    }

    private boolean canDoNsResize( Point point ) {
        int yMove = point.y - startPoint.y;
        boolean canDo = true;
        for ( DokzPanel panel : westNorthPanels.keySet() ) {
            canDo &= ( westNorthPanels.get( panel ).height + yMove ) > minPanelHeight;
        }
        for ( DokzPanel panel : eastSouthPanels.keySet() ) {
            canDo &= ( eastSouthPanels.get( panel ).height - yMove ) > minPanelHeight;
        }
        return canDo;
    }

    private boolean canDoEwResize( Point point ) {
        int xMove = point.x - startPoint.x;
        boolean canDo = true;
        for ( DokzPanel panel : westNorthPanels.keySet() ) {
            canDo &= ( westNorthPanels.get( panel ).width + xMove ) > minPanelWidth;
        }
        for ( DokzPanel panel : eastSouthPanels.keySet() ) {
            canDo &= ( eastSouthPanels.get( panel ).width - xMove ) > minPanelWidth;
        }
        return canDo;
    }

    private void doEwBoundsCalc( Point point ) {
        if ( canDoEwResize( point ) ) {
            int xMove = point.x - startPoint.x;
            for ( DokzPanel panel : westNorthPanels.keySet() ) {
                Rectangle origBounds = westNorthPanels.get( panel );
                panel.setBounds( origBounds.x, origBounds.y, origBounds.width + xMove, origBounds.height );
            }
            for ( DokzPanel panel : eastSouthPanels.keySet() ) {
                Rectangle origBounds = eastSouthPanels.get( panel );
                panel.setBounds( origBounds.x + xMove, origBounds.y, origBounds.width - xMove, origBounds.height );
            }
        }
    }

    public boolean canStartResize( DokzNeighbourContext panelsAround ) {
        return panelsAround.getPoint() == null && //
            ( isEwResize( panelsAround ) || isNsResize( panelsAround ) );
    }

    private boolean isNsResize( DokzNeighbourContext panelsAround ) {
        return panelsAround.getNorth() != null && panelsAround.getSouth() != null;
    }

    private boolean isEwResize( DokzNeighbourContext panelsAround ) {
        return panelsAround.getEast() != null && panelsAround.getWest() != null;
    }

    private enum Direction {
        ew,
        ns,
        n,
        s,
        e,
        w
    }
}
