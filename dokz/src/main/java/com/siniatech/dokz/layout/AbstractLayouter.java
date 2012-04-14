package com.siniatech.dokz.layout;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Collection;

abstract public class AbstractLayouter implements ILayouter {

    @Override
    public <T extends Component> void doLayout( Collection<T> components, Dimension size ) {
        doLayout( components, size, 0, 0 );
    }

}
