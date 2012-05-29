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

public class TranslatingLayoutContext implements ILayoutContext {

    private final int xTranslation;
    private final int yTranslation;

    public TranslatingLayoutContext( int xTranslation, int yTranslation ) {
        this.xTranslation = xTranslation;
        this.yTranslation = yTranslation;
    }

    public int getXTranslation() {
        return xTranslation;
    }

    public int getYTranslation() {
        return yTranslation;
    }
}
