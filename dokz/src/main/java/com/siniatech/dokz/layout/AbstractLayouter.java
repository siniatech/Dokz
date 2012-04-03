package com.siniatech.dokz.layout;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Collection;

abstract public class AbstractLayouter implements ILayouter {

    @Override
    public void doLayout( Collection<? extends Component> components, Dimension size ) {
        doLayout( components, size, 0, 0 );
    }

}
