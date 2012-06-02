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

public class TestDkzB0001 extends TestDocking {

    @Override
    protected IDocking createDocking() {
        return new EastSideDocking();
    }

    @Override
    protected int getPanelGap() {
        return 5;
    }

    @Test
    public void applyDockingAndRelayout_p1() {
        docking.applyDocking( dokzContainer, p1 );
        checkDock_p1();
        dokzContainer.getLayout().layoutContainer( dokzContainer );
        checkDock_p1();
    }

    @Test
    public void applyDockingAndResize_p1() {
        docking.applyDocking( dokzContainer, p1 );
        checkDock_p1();
        dokzContainer.setSize( 210, 210 );
        dokzContainer.getLayout().layoutContainer( dokzContainer );
        assertEquals( new Rectangle( 110, 0, 100, 210 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 0, 105, 105 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 110, 49, 100 ), p3.getBounds() );
        assertEquals( new Rectangle( 54, 110, 51, 100 ), p4.getBounds() );
    }

    private void checkDock_p1() {
        assertEquals( new Rectangle( 55, 0, 50, 105 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 0, 50, 50 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 55, 22, 50 ), p3.getBounds() );
        assertEquals( new Rectangle( 27, 55, 23, 50 ), p4.getBounds() );
    }

    @Test
    public void applyDockingAndRelayout_p2() {
        docking.applyDocking( dokzContainer, p2 );
        checkDock_p2();
        dokzContainer.getLayout().layoutContainer( dokzContainer );
        checkDock_p2();
    }

    @Test
    public void applyDockingAndResize_p2() {
        docking.applyDocking( dokzContainer, p2 );
        checkDock_p2();
        dokzContainer.setSize( 210, 210 );
        dokzContainer.getLayout().layoutContainer( dokzContainer );
        assertEquals( new Rectangle( 0, 0, 105, 105 ), p1.getBounds() );
        assertEquals( new Rectangle( 110, 0, 100, 210 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 110, 49, 100 ), p3.getBounds() );
        assertEquals( new Rectangle( 54, 110, 51, 100 ), p4.getBounds() );
    }

    private void checkDock_p2() {
        assertEquals( new Rectangle( 0, 0, 50, 50 ), p1.getBounds() );
        assertEquals( new Rectangle( 55, 0, 50, 105 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 55, 22, 50 ), p3.getBounds() );
        assertEquals( new Rectangle( 27, 55, 23, 50 ), p4.getBounds() );
    }

    @Test
    public void applyDockingAndRelayout_p3() {
        docking.applyDocking( dokzContainer, p3 );
        checkDock_p3();
        dokzContainer.getLayout().layoutContainer( dokzContainer );
        checkDock_p3();
    }

    @Test
    public void applyDockingAndResize_p3() {
        docking.applyDocking( dokzContainer, p3 );
        checkDock_p3();
        dokzContainer.setSize( 210, 210 );
        dokzContainer.getLayout().layoutContainer( dokzContainer );
        assertEquals( new Rectangle( 0, 0, 49, 105 ), p1.getBounds() );
        assertEquals( new Rectangle( 54, 0, 51, 105 ), p2.getBounds() );
        assertEquals( new Rectangle( 110, 0, 100, 210 ), p3.getBounds() );
        assertEquals( new Rectangle( 0, 110, 105, 100 ), p4.getBounds() );
    }

    private void checkDock_p3() {
        assertEquals( new Rectangle( 0, 0, 22, 50 ), p1.getBounds() );
        assertEquals( new Rectangle( 27, 0, 23, 50 ), p2.getBounds() );
        assertEquals( new Rectangle( 55, 0, 50, 105 ), p3.getBounds() );
        assertEquals( new Rectangle( 0, 55, 50, 50 ), p4.getBounds() );
    }

    @Test
    public void applyDockingAndRelayout_p4() {
        docking.applyDocking( dokzContainer, p4 );
        checkDock_p4();
        dokzContainer.getLayout().layoutContainer( dokzContainer );
        checkDock_p4();
    }

    @Test
    public void applyDockingAndResize_p4() {
        docking.applyDocking( dokzContainer, p4 );
        checkDock_p4();
        dokzContainer.setSize( 210, 210 );
        dokzContainer.getLayout().layoutContainer( dokzContainer );
        assertEquals( new Rectangle( 0, 0, 49, 105 ), p1.getBounds() );
        assertEquals( new Rectangle( 54, 0, 51, 105 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 110, 105, 100 ), p3.getBounds() );
        assertEquals( new Rectangle( 110, 0, 100, 210 ), p4.getBounds() );
    }

    private void checkDock_p4() {
        assertEquals( new Rectangle( 0, 0, 22, 50 ), p1.getBounds() );
        assertEquals( new Rectangle( 27, 0, 23, 50 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 55, 50, 50 ), p3.getBounds() );
        assertEquals( new Rectangle( 55, 0, 50, 105 ), p4.getBounds() );
    }

}
