package com.siniatech.dokz.layout;

import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

public interface ILayouter {

    void doLayout(List<Component> components, Dimension size);

    // make tiling layouter implement
    void doLayout(List<Component> components, Dimension size, int hGap, int vGap);
}
