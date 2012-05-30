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
import java.awt.Rectangle;
import java.util.Collection;

public class TranslatingLayouter extends AbstractLayouter {

    @Override
    public <T extends Component> void doLayout( Collection<T> components, Dimension size, int hGap, int vGap, ILayoutContext layoutContext ) {
        TranslatingLayoutContext translatingLayoutContext = getTranslatingLayoutContext( layoutContext );
        for ( T component : components ) {
            Rectangle oldBounds = component.getBounds();
            checkTranslatedBounds( size, translatingLayoutContext, oldBounds );
            component.setBounds( //
                oldBounds.x + translatingLayoutContext.getXTranslation(), //
                oldBounds.y + translatingLayoutContext.getYTranslation(), //
                oldBounds.width, //
                oldBounds.height //
                );
        }
    }

    private TranslatingLayoutContext getTranslatingLayoutContext( ILayoutContext layoutContext ) {
        if ( ! ( layoutContext instanceof TranslatingLayoutContext ) ) {
            throw new IllegalStateException( "Translating layouter requires suitable context" );
        }
        TranslatingLayoutContext translatingLayoutContext = (TranslatingLayoutContext) layoutContext;
        return translatingLayoutContext;
    }

    static private void checkTranslatedBounds( Dimension size, TranslatingLayoutContext translatingLayoutContext, Rectangle oldBounds ) {
        if ( oldBounds.getMinX() + translatingLayoutContext.getXTranslation() < 0 || //
            oldBounds.getMaxX() + translatingLayoutContext.getXTranslation() > size.width || //
            oldBounds.getMinY() + translatingLayoutContext.getYTranslation() < 0 || //
            oldBounds.getMaxY() + translatingLayoutContext.getYTranslation() > size.height //
        ) {
            throw new IllegalStateException( "Component translated beyond allowable bounds" );
        }
    }

}
