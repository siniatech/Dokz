/*******************************************************************************
 * Dokz
 * Copyright (c) 2011-2 Siniatech Ltd  http://www.siniatech.com/products/dokz
 *
 * All rights reserved. This project and the accompanying materials are made 
 * available under the terms of the MIT License which can be found in the root  
 * of the project, and at http://www.opensource.org/licenses/mit-license.php
 *
 ******************************************************************************/
package com.siniatech.dokz.layout;

import static com.siniatech.siniautils.swing.BoundsHelper.*;
import static java.lang.String.*;

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
        if ( gap.width == size.width ) {
            extendComponentsToFillFullWidthGap( components, gap, size, vGap );
        } else if ( gap.height == size.height ) {
            extendComponentsToFillFullHeightGap( components, gap, size, hGap );
        } else {
            boolean isFilled = tryToFillGapByExtendingOneComponent( components, gap, size, hGap, vGap );
            assert isFilled : format( "Unexpected layout issue in %s", getClass().getSimpleName() );
        }
    }

    private void extendComponentsToFillFullWidthGap( Collection<? extends Component> components, Rectangle gap, Dimension size, int vGap ) {
        if ( gap.y > 0 ) {
            extendComponentsToFillFullWidthGapDown( components, gap, size, vGap );
        } else {
            extendComponentsToFillFullWidthGapUp( components, gap, size, vGap );
        }
    }

    private void extendComponentsToFillFullWidthGapUp( Collection<? extends Component> components, Rectangle gap, Dimension size, int vGap ) {
        for ( Component component : getComponentsCrossingY( components, gap.y + gap.height + vGap ) ) {
            expandUp( gap, vGap, component, component.getBounds() );
        }
    }

    private void extendComponentsToFillFullWidthGapDown( Collection<? extends Component> components, Rectangle gap, Dimension size, int vGap ) {
        for ( Component component : getComponentsCrossingY( components, gap.y - vGap - 1 ) ) {
            expandDown( gap, vGap, component, component.getBounds() );
        }
    }

    private void expandDown( Rectangle gap, int vGap, Component component, Rectangle bounds ) {
        int height = bounds.height + gap.height + vGap;
        component.setBounds( bounds.x, bounds.y, bounds.width, height );
    }

    private void extendComponentsToFillFullHeightGap( Collection<? extends Component> components, Rectangle gap, Dimension size, int hGap ) {
        if ( gap.x > 0 ) {
            extendComponentsToFillFullWidthGapRight( components, gap, size, hGap );
        } else {
            extendComponentsToFillFullWidthGapLeft( components, gap, size, hGap );
        }
    }

    private void extendComponentsToFillFullWidthGapLeft( Collection<? extends Component> components, Rectangle gap, Dimension size, int hGap ) {
        for ( Component component : getComponentsCrossingX( components, gap.x + gap.width + hGap ) ) {
            expandLeft( gap, hGap, component, component.getBounds() );
        }
    }

    private void extendComponentsToFillFullWidthGapRight( Collection<? extends Component> components, Rectangle gap, Dimension size, int hGap ) {
        for ( Component component : getComponentsCrossingX( components, gap.x - hGap - 1 ) ) {
            expandRight( gap, hGap, component, component.getBounds() );
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
                expandDown( gap, vGap, compToTop, compToTop.getBounds() );
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
                expandUp( gap, vGap, compToBottom, compToBottom.getBounds() );
                return true;
            }
        }
        return false;
    }

    private void expandUp( Rectangle gap, int vGap, Component compToBottom, Rectangle bounds ) {
        int height = bounds.height + gap.height + vGap;
        int newY = bounds.y - gap.height - vGap;
        compToBottom.setBounds( bounds.x, newY, bounds.width, height );
    }

    private boolean tryToFillGapByExtendingOneComponentW( Collection<? extends Component> components, Rectangle gap, int hGap ) {
        int x = gap.x - hGap - 1;
        int y = gap.y;
        if ( x >= 0 ) {
            Component compToLeft = getComponentContaining( components, new Point( x, y ) );
            if ( compToLeft != null && alignsHorizontally( compToLeft.getBounds(), gap ) ) {
                expandRight( gap, hGap, compToLeft, compToLeft.getBounds() );
                return true;
            }
        }
        return false;
    }

    private void expandRight( Rectangle gap, int hGap, Component compToLeft, Rectangle bounds ) {
        int width = bounds.width + gap.width + hGap;
        compToLeft.setBounds( bounds.x, bounds.y, width, bounds.height );
    }

    private boolean tryToFillGapByExtendingOneComponentE( Collection<? extends Component> components, Rectangle gap, Dimension size, int hGap ) {
        int x = gap.x + gap.width + hGap;
        int y = gap.y;
        if ( x <= size.width ) {
            Component compToRight = getComponentContaining( components, new Point( x, y ) );
            if ( compToRight != null && alignsHorizontally( compToRight.getBounds(), gap ) ) {
                expandLeft( gap, hGap, compToRight, compToRight.getBounds() );
                return true;
            }
        }
        return false;
    }

    private void expandLeft( Rectangle gap, int hGap, Component compToRight, Rectangle bounds ) {
        int width = bounds.width + gap.width + hGap;
        int newX = bounds.x - gap.width - hGap;
        compToRight.setBounds( newX, bounds.y, width, bounds.height );
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
