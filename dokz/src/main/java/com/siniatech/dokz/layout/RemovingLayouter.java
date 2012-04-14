package com.siniatech.dokz.layout;

import static com.siniatech.siniautils.swing.BoundsHelper.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
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
        boolean isFilled = tryToFillGapByExtendingOneComponent( components, gap, size, hGap, vGap );
        if ( !isFilled ) {
            // try something else
        }
    }

    @DefaultForTesting
    boolean tryToFillGapByExtendingOneComponent( Collection<? extends Component> components, Rectangle gap, Dimension size, int hGap, int vGap ) {
        return tryToFillGapByExtendingOneComponentW( components, gap, hGap ) || //
            tryToFillGapByExtendingOneComponentE( components, gap, size, hGap ) || //
            tryToFillGapByExtendingOneComponentN( components, gap, vGap ) || //
            tryToFillGapByExtendingOneComponentS( components, gap, size, vGap );
    }

    private boolean tryToFillGapByExtendingOneComponentN( Collection<? extends Component> components, Rectangle gap, int vGap ) {
        int x = gap.x;
        int y = gap.y - vGap - 1;
        if ( y >= 0 ) {
            Component compToTop = getComponentContaining( components, new Point( x, y ) );
            if ( compToTop != null && alignsVertically( compToTop.getBounds(), gap ) ) {
                Rectangle bounds = compToTop.getBounds();
                int height = bounds.height + gap.height + vGap;
                compToTop.setBounds( bounds.x, bounds.y, bounds.width, height );
                return true;
            }
        }
        return false;
    }

    private boolean tryToFillGapByExtendingOneComponentS( Collection<? extends Component> components, Rectangle gap, Dimension size, int vGap ) {
        int x = gap.x;
        int y = gap.y + gap.height + vGap;
        if ( x <= size.height ) {
            Component compToBottom = getComponentContaining( components, new Point( x, y ) );
            if ( compToBottom != null && alignsVertically( compToBottom.getBounds(), gap ) ) {
                Rectangle bounds = compToBottom.getBounds();
                int height = bounds.height + gap.height + vGap;
                int newY = bounds.y - gap.height - vGap;
                compToBottom.setBounds( bounds.x, newY, bounds.width, height );
                return true;
            }
        }
        return false;
    }

    private boolean tryToFillGapByExtendingOneComponentW( Collection<? extends Component> components, Rectangle gap, int hGap ) {
        int x = gap.x - hGap - 1;
        int y = gap.y;
        if ( x >= 0 ) {
            Component compToLeft = getComponentContaining( components, new Point( x, y ) );
            if ( compToLeft != null && alignsHorizontally( compToLeft.getBounds(), gap ) ) {
                Rectangle bounds = compToLeft.getBounds();
                int width = bounds.width + gap.width + hGap;
                compToLeft.setBounds( bounds.x, bounds.y, width, bounds.height );
                return true;
            }
        }
        return false;
    }

    private boolean tryToFillGapByExtendingOneComponentE( Collection<? extends Component> components, Rectangle gap, Dimension size, int hGap ) {
        int x = gap.x + gap.width + hGap;
        int y = gap.y;
        if ( x <= size.width ) {
            Component compToRight = getComponentContaining( components, new Point( x, y ) );
            if ( compToRight != null && alignsHorizontally( compToRight.getBounds(), gap ) ) {
                Rectangle bounds = compToRight.getBounds();
                int width = bounds.width + gap.width + hGap;
                int newX = bounds.x - gap.width - hGap;
                compToRight.setBounds( newX, bounds.y, width, bounds.height );
                return true;
            }
        }
        return false;
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
