package com.siniatech.dokz.layout;

import static com.siniatech.siniautils.swing.BoundsHelper.*;
import static java.lang.Math.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.siniatech.siniautils.fn.IFunction1;

/**
 * Attempts to scale a grid like layout to fit a new dimension. Tries to
 * maintain a specific gap between components regardless of scale. Obviously,
 * there are assumptions made, for example:
 * <ul>
 * <li>components don't overlap and that there are no gaps intended between
 * components.
 * <li>components begin from the 0 point on each axis
 * </ul>
 */
public class ScalingLayouter extends AbstractLayouter {

    @Override
    public void doLayout( Collection<? extends Component> components, Dimension size, int hGap, int vGap ) {
        if ( components.size() > 0 ) {
            doRoughScaledLayout( components, size );
            correctGapsAndBorders( components, size, hGap, vGap );
        }

    }

    private void correctGapsAndBorders( Collection<? extends Component> components, Dimension newSize, int hGap, int vGap ) {
        List<Component> componentsCorrected = new ArrayList<>();
        while ( components.size() != componentsCorrected.size() ) {
            List<Component> componentsNotCorrected = new ArrayList<>( components );
            componentsNotCorrected.removeAll( componentsCorrected );
            Component topLeftComponent = getTopLeftmostComponent( componentsNotCorrected );
            correctGapsAndBorders( topLeftComponent, components, newSize, hGap, vGap );
            componentsCorrected.add( topLeftComponent );
        }
    }

    private void correctGapsAndBorders( Component component, Collection<? extends Component> components, Dimension newSize, int hGap, int vGap ) {
        Rectangle bounds = component.getBounds();
        int right = bounds.x + bounds.width;
        int bottom = bounds.y + bounds.height;
        Rectangle rightBox = new Rectangle( right, bounds.y, newSize.width - right, bottom );
        Rectangle bottomBox = new Rectangle( bounds.x, bottom, right, newSize.height - bottom );
        Component compToRight = getLeftmostComponent( getComponentsIn( components, rightBox ) );
        Component compToBottom = getTopmostComponent( getComponentsIn( components, bottomBox ) );
        int w = compToRight == null ? newSize.width - bounds.x : compToRight.getBounds().x - bounds.x - hGap;
        int h = compToBottom == null ? newSize.height - bounds.y : compToBottom.getBounds().y - bounds.y - vGap;
        component.setBounds( bounds.x, bounds.y, w, h );
    }

    private void doRoughScaledLayout( Collection<? extends Component> components, Dimension newSize ) {
        double xScale = newSize.width / (double) getXExtentOfComponents( components );
        double yScale = newSize.height / (double) getYExtentOfComponents( components );
        IFunction1<Double, Integer> xRoundingFn = createRoundingFn( xScale );
        IFunction1<Double, Integer> yRoundingFn = createRoundingFn( yScale );
        for ( Component component : components ) {
            Rectangle bounds = component.getBounds();
            int x = xRoundingFn.apply( xScale * bounds.x );
            int y = yRoundingFn.apply( yScale * bounds.y );
            int w = xRoundingFn.apply( xScale * bounds.width );
            int h = yRoundingFn.apply( yScale * bounds.height );
            component.setBounds( x, y, w, h );
        }
    }

    private IFunction1<Double, Integer> createRoundingFn( final double scale ) {
        return new IFunction1<Double, Integer>() {
            @Override
            public Integer apply( Double t ) {
                return (int) ( scale < 1.0 ? ceil( t ) : floor( t ) );
            }
        };
    }

}
