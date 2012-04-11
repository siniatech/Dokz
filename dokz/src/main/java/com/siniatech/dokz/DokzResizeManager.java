package com.siniatech.dokz;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.siniatech.siniautils.fn.IResponse0;
import com.siniatech.siniautils.set.SetHelper;

public class DokzResizeManager {

    private final IResponse0 invalidateResponse;
    private final Map<DokzPanel, Rectangle> westNorthPanels = new HashMap<>();
    private final Map<DokzPanel, Rectangle> eastSouthPanels = new HashMap<>();

    private Point startPoint;
    private ResizeDirection direction;

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

    public void startResize( Point startPoint, DokzNeighbourContext panelsAround, Set<DokzPanel> panelsIn ) {
        this.startPoint = startPoint;
        this.direction = isEwResize( panelsAround ) ? ResizeDirection.ew : ResizeDirection.ns;

        // need to get all along axis
        if ( direction == ResizeDirection.ew ) {
            westNorthPanels.put( panelsAround.getWest(), panelsAround.getWest().getBounds() );
            eastSouthPanels.put( panelsAround.getEast(), panelsAround.getEast().getBounds() );
        } else {
            westNorthPanels.put( panelsAround.getNorth(), panelsAround.getNorth().getBounds() );
            eastSouthPanels.put( panelsAround.getSouth(), panelsAround.getSouth().getBounds() );
        }
    }

    public void doResize( Point point ) {
        if ( direction == ResizeDirection.ew ) {
            doEwBoundsCalc( point );
        } else {
            doNsBoundsCalc( point );
        }
        invalidateResponse.respond();
        for ( DokzPanel panel : SetHelper.union( westNorthPanels.keySet(), eastSouthPanels.keySet() ) ) {
            panel.validate();
        }
    }

    private void doNsBoundsCalc( Point point ) {
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

    private void doEwBoundsCalc( Point point ) {
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

    private enum ResizeDirection {
        ew,
        ns
    }
}
