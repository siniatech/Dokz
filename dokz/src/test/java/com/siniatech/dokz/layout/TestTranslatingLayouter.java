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
import java.util.Arrays;

import org.junit.Test;

public class TestTranslatingLayouter {

    private TranslatingLayouter layouter = new TranslatingLayouter();

    static private TranslatingLayoutContext context( int x, int y ) {
        return new TranslatingLayoutContext( x, y );
    }

    @Test
    public void shouldLayoutEmptyContainer() {
        layouter.doLayout( Arrays.<Component> asList(), new Dimension( 200, 200 ), 0, 0, context( 0, 0 ) );
    }
}
