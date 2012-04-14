package com.siniatech.dokz.layout;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.Collection;

import com.siniatech.siniautils.annotations.DefaultForTesting;

/**
 * Presently assumes there's only a single gap - think this will be sufficient
 */
public class RemovingLayouter extends AbstractLayouter {

    @Override
    public <T extends Component> void doLayout( Collection<T> components, Dimension size, int hGap, int vGap ) {
        Rectangle gap = findGap( components, size, hGap, vGap );
    }

    @DefaultForTesting
    Rectangle findGap( Collection<? extends Component> components, Dimension size, int hGap, int vGap ) {
        Area area = new Area( new Rectangle( size ) );
        for ( Component component : components ) {
            Rectangle bounds = component.getBounds();
            area.subtract( new Area( new Rectangle( bounds.x - hGap, bounds.y - vGap, bounds.width + ( hGap * 2 ), bounds.height + ( vGap * 2 ) ) ) );
        }
        return area.getBounds();
    }
}
