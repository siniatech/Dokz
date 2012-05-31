/*******************************************************************************
 * Dokz
 * Copyright (c) 2011-2 Siniatech Ltd  http://www.siniatech.com/products/dokz
 *
 * All rights reserved. This project and the accompanying materials are made 
 * available under the terms of the MIT License which can be found in the root  
 * of the project, and at http://www.opensource.org/licenses/mit-license.php
 *
 ******************************************************************************/
package com.siniatech.dokz.docking;

import java.awt.Rectangle;

import org.junit.Before;

import com.siniatech.dokz.DokzContainer;
import com.siniatech.dokz.DokzPanel;
import com.siniatech.dokz.context.DokzContext;
import com.siniatech.dokz.context.DokzPanelContext;
import com.siniatech.dokz.test.fixtures.MockDokzPanel;
import com.siniatech.siniautils.fn.ResponseHelper;

abstract public class TestDocking {

    protected IDocking docking;
    protected DokzPanel p1;
    protected DokzPanel p2;
    protected DokzPanel p3;
    protected DokzPanel p4;
    protected DokzContainer dokzContainer;

    @Before
    public void init() {
        p1 = new MockDokzPanel( new Rectangle( 0, 0, 50, 50 ) );
        p2 = new MockDokzPanel( new Rectangle( 50 + getPanelGap(), 0, 50, 50 ) );
        p3 = new MockDokzPanel( new Rectangle( 0, 50 + getPanelGap(), 50, 50 ) );
        p4 = new MockDokzPanel( new Rectangle( 50 + getPanelGap(), 50 + getPanelGap(), 50, 50 ) );
        DokzContext dokzContext = new DokzContext() {
            @Override
            public int getPanelGap() {
                return TestDocking.this.getPanelGap();
            }
        };
        dokzContainer = new DokzContainer( dokzContext );
        dokzContext.add( p1, dokzContainer, dokzPanelContext() );
        dokzContext.add( p2, dokzContainer, dokzPanelContext() );
        dokzContext.add( p3, dokzContainer, dokzPanelContext() );
        dokzContext.add( p4, dokzContainer, dokzPanelContext() );
        dokzContainer.setSize( 100 + getPanelGap(), 100 + getPanelGap() );
        dokzContext.setMainContainer( dokzContainer );
        docking = createDocking();
    }

    protected int getPanelGap() {
        return 0;
    }

    private DokzPanelContext dokzPanelContext() {
        return new DokzPanelContext( "", ResponseHelper.emptyResponse0, ResponseHelper.emptyResponse0 );
    }

    abstract protected IDocking createDocking();

}
