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

import static com.siniatech.dokz.layout.NoLayoutContext.*;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Collection;

abstract public class AbstractLayouter implements ILayouter {

    @Override
    public <T extends Component> void doLayout( Collection<T> components, Dimension size ) {
        doLayout( components, size, 0, 0, NoLayoutContext );
    }

    @Override
    public <T extends Component> void doLayout( Collection<T> components, Dimension size, int hGap, int vGap ) {
        doLayout( components, size, hGap, vGap, NoLayoutContext );
    }

}
