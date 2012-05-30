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

public class TestSouthSideDocking extends TestDocking {

    @Override
    protected IDocking createDocking() {
        return new SouthSideDocking();
    }

    @Test
    public void applyDocking_p1() {
        docking.applyDocking( dokzContainer, p1 );
        assertEquals( new Rectangle( 0, 50, 100, 50 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 0, 100, 25 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 25, 50, 25 ), p3.getBounds() );
        assertEquals( new Rectangle( 50, 25, 50, 25 ), p4.getBounds() );
    }

    @Test
    public void applyDocking_p2() {
        docking.applyDocking( dokzContainer, p2 );
        assertEquals( new Rectangle( 0, 0, 100, 25 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 50, 100, 50 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 25, 50, 25 ), p3.getBounds() );
        assertEquals( new Rectangle( 50, 25, 50, 25 ), p4.getBounds() );
    }

    @Test
    public void applyDocking_p3() {
        docking.applyDocking( dokzContainer, p3 );
        assertEquals( new Rectangle( 0, 0, 50, 25 ), p1.getBounds() );
        assertEquals( new Rectangle( 50, 0, 50, 25 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 50, 100, 50 ), p3.getBounds() );
        assertEquals( new Rectangle( 0, 25, 100, 25 ), p4.getBounds() );
    }

    @Test
    public void applyDocking_p4() {
        docking.applyDocking( dokzContainer, p4 );
        assertEquals( new Rectangle( 0, 0, 50, 25 ), p1.getBounds() );
        assertEquals( new Rectangle( 50, 0, 50, 25 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 25, 100, 25 ), p3.getBounds() );
        assertEquals( new Rectangle( 0, 50, 100, 50 ), p4.getBounds() );
    }

}
