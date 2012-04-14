package com.siniatech.dokz.layout;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Collection;

public interface ILayouter {

    <T extends Component> void doLayout( Collection<T> components, Dimension size );

    <T extends Component> void doLayout( Collection<T> components, Dimension size, int hGap, int vGap );
}
