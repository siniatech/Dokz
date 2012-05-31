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

import static junit.framework.Assert.*;

import java.awt.Rectangle;

import org.junit.Test;

public class TestNorthSideDockingWithGap extends TestDocking {

    @Override
    protected IDocking createDocking() {
        return new NorthSideDocking();
    }

    @Override
    protected int getPanelGap() {
        return 5;
    }

    @Test
    public void applyDocking_p1() {
        docking.applyDocking( dokzContainer, p1 );
        assertEquals( new Rectangle( 0, 0, 105, 50 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 55, 105, 22 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 82, 50, 23 ), p3.getBounds() );
        assertEquals( new Rectangle( 55, 82, 50, 23 ), p4.getBounds() );
    }

    @Test
    public void applyDocking_p2() {
        docking.applyDocking( dokzContainer, p2 );
        assertEquals( new Rectangle( 0, 55, 105, 22 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 0, 105, 50 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 82, 50, 23 ), p3.getBounds() );
        assertEquals( new Rectangle( 55, 82, 50, 23 ), p4.getBounds() );
    }

    @Test
    public void applyDocking_p3() {
        docking.applyDocking( dokzContainer, p3 );
        assertEquals( new Rectangle( 0, 55, 50, 22 ), p1.getBounds() );
        assertEquals( new Rectangle( 55, 55, 50, 22 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 0, 105, 50 ), p3.getBounds() );
        assertEquals( new Rectangle( 0, 82, 105, 23 ), p4.getBounds() );
    }

    @Test
    public void applyDocking_p4() {
        docking.applyDocking( dokzContainer, p4 );
        assertEquals( new Rectangle( 0, 55, 50, 22 ), p1.getBounds() );
        assertEquals( new Rectangle( 55, 55, 50, 22 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 82, 105, 23 ), p3.getBounds() );
        assertEquals( new Rectangle( 0, 0, 105, 50 ), p4.getBounds() );
    }

}
