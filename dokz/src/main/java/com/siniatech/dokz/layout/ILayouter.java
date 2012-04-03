package com.siniatech.dokz.layout;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Collection;

public interface ILayouter {

    void doLayout( Collection<? extends Component> components, Dimension size );

    // make tiling layouter implement
    void doLayout( Collection<? extends Component> components, Dimension size, int hGap, int vGap );
}
