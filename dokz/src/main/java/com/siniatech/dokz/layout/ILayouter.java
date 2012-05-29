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

import java.awt.Component;
import java.awt.Dimension;
import java.util.Collection;

public interface ILayouter {

    <T extends Component> void doLayout( Collection<T> components, Dimension size );

    <T extends Component> void doLayout( Collection<T> components, Dimension size, int hGap, int vGap );
    
    <T extends Component> void doLayout( Collection<T> components, Dimension size, int hGap, int vGap, ILayoutContext layoutContext );
}
