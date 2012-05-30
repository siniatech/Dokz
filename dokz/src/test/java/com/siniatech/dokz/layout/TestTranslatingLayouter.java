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

import static junit.framework.Assert.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Arrays;

import javax.swing.JPanel;

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

    @Test
    public void shouldFailWithoutLayoutContext() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        try {
            layouter.doLayout( Arrays.<Component> asList( c1 ), new Dimension( 200, 200 ), 0, 0 );
            fail();
        } catch ( Exception e ) {
        }
    }

    @Test
    public void shouldFailWithNullLayoutContext() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        try {
            layouter.doLayout( Arrays.<Component> asList( c1 ), new Dimension( 200, 200 ), 0, 0, null );
            fail();
        } catch ( Exception e ) {
        }
    }

    @Test
    public void shouldFailIfBreaksBorderWest() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        try {
            layouter.doLayout( Arrays.<Component> asList( c1 ), new Dimension( 200, 200 ), 0, 0, context( -50, 0 ) );
            fail();
        } catch ( Exception e ) {
        }
    }

    @Test
    public void shouldFailIfBreaksBorderEast() {
        Component c1 = new JPanel();
        c1.setBounds( 160, 0, 50, 50 );
        try {
            layouter.doLayout( Arrays.<Component> asList( c1 ), new Dimension( 200, 200 ), 0, 0, context( 50, 0 ) );
            fail();
        } catch ( Exception e ) {
        }
    }

    @Test
    public void shouldFailIfBreaksBorderNorth() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        try {
            layouter.doLayout( Arrays.<Component> asList( c1 ), new Dimension( 200, 200 ), 0, 0, context( 0, -50 ) );
            fail();
        } catch ( Exception e ) {
        }
    }

    @Test
    public void shouldFailIfBreaksBorderSouth() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 160, 50, 50 );
        try {
            layouter.doLayout( Arrays.<Component> asList( c1 ), new Dimension( 200, 200 ), 0, 0, context( 0, 50 ) );
            fail();
        } catch ( Exception e ) {
        }
    }

    @Test
    public void shouldTranslateSingleCompEast() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        layouter.doLayout( Arrays.<Component> asList( c1 ), new Dimension( 200, 200 ), 0, 0, context( 50, 0 ) );
        assertEquals( new Rectangle( 50, 0, 50, 50 ), c1.getBounds() );
    }

    @Test
    public void shouldTranslateSingleCompWest() {
        Component c1 = new JPanel();
        c1.setBounds( 50, 0, 50, 50 );
        layouter.doLayout( Arrays.<Component> asList( c1 ), new Dimension( 200, 200 ), 0, 0, context( -50, 0 ) );
        assertEquals( new Rectangle( 0, 0, 50, 50 ), c1.getBounds() );
    }

    @Test
    public void shouldTranslateSingleCompNorth() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 50, 50, 50 );
        layouter.doLayout( Arrays.<Component> asList( c1 ), new Dimension( 200, 200 ), 0, 0, context( 0, -50 ) );
        assertEquals( new Rectangle( 0, 0, 50, 50 ), c1.getBounds() );
    }

    @Test
    public void shouldTranslateSingleCompSouth() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        layouter.doLayout( Arrays.<Component> asList( c1 ), new Dimension( 200, 200 ), 0, 0, context( 0, 50 ) );
        assertEquals( new Rectangle( 0, 50, 50, 50 ), c1.getBounds() );
    }

    @Test
    public void shouldTranslateSingleCompNorthEast() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 50, 50, 50 );
        layouter.doLayout( Arrays.<Component> asList( c1 ), new Dimension( 200, 200 ), 0, 0, context( 50, -50 ) );
        assertEquals( new Rectangle( 50, 0, 50, 50 ), c1.getBounds() );
    }

    @Test
    public void shouldTranslateSingleCompNorthWest() {
        Component c1 = new JPanel();
        c1.setBounds( 50, 50, 50, 50 );
        layouter.doLayout( Arrays.<Component> asList( c1 ), new Dimension( 200, 200 ), 0, 0, context( -50, -50 ) );
        assertEquals( new Rectangle( 0, 0, 50, 50 ), c1.getBounds() );
    }

    @Test
    public void shouldTranslateSingleCompSouthEast() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        layouter.doLayout( Arrays.<Component> asList( c1 ), new Dimension( 200, 200 ), 0, 0, context( 50, 50 ) );
        assertEquals( new Rectangle( 50, 50, 50, 50 ), c1.getBounds() );
    }

    @Test
    public void shouldTranslateSingleCompSouthWest() {
        Component c1 = new JPanel();
        c1.setBounds( 50, 0, 50, 50 );
        layouter.doLayout( Arrays.<Component> asList( c1 ), new Dimension( 200, 200 ), 0, 0, context( -50, 50 ) );
        assertEquals( new Rectangle( 0, 50, 50, 50 ), c1.getBounds() );
    }

    @Test
    public void shouldTranslateMultipleCompEast() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 0, 50, 50, 50 );
        layouter.doLayout( Arrays.<Component> asList( c1, c2 ), new Dimension( 200, 200 ), 0, 0, context( 50, 0 ) );
        assertEquals( new Rectangle( 50, 0, 50, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 50, 50, 50, 50 ), c2.getBounds() );
    }

    @Test
    public void shouldTranslateMultipleCompWest() {
        Component c1 = new JPanel();
        c1.setBounds( 50, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 50, 50, 50, 50 );
        layouter.doLayout( Arrays.<Component> asList( c1, c2 ), new Dimension( 200, 200 ), 0, 0, context( -50, 0 ) );
        assertEquals( new Rectangle( 0, 0, 50, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 0, 50, 50, 50 ), c2.getBounds() );
    }

    @Test
    public void shouldTranslateMultipleCompNorth() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 50, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 50, 50, 50, 50 );
        layouter.doLayout( Arrays.<Component> asList( c1, c2 ), new Dimension( 200, 200 ), 0, 0, context( 0, -50 ) );
        assertEquals( new Rectangle( 0, 0, 50, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 50, 0, 50, 50 ), c2.getBounds() );
    }

    @Test
    public void shouldTranslateMultipleCompSouth() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 50, 0, 50, 50 );
        layouter.doLayout( Arrays.<Component> asList( c1, c2 ), new Dimension( 200, 200 ), 0, 0, context( 0, 50 ) );
        assertEquals( new Rectangle( 0, 50, 50, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 50, 50, 50, 50 ), c2.getBounds() );
    }

    @Test
    public void shouldTranslateMultipleCompNorthEast() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 50, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 50, 50, 50, 50 );
        layouter.doLayout( Arrays.<Component> asList( c1, c2 ), new Dimension( 200, 200 ), 0, 0, context( 50, -50 ) );
        assertEquals( new Rectangle( 50, 0, 50, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 100, 0, 50, 50 ), c2.getBounds() );
    }

    @Test
    public void shouldTranslateMultipleCompNorthWest() {
        Component c1 = new JPanel();
        c1.setBounds( 50, 50, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 100, 50, 50, 50 );
        layouter.doLayout( Arrays.<Component> asList( c1, c2 ), new Dimension( 200, 200 ), 0, 0, context( -50, -50 ) );
        assertEquals( new Rectangle( 0, 0, 50, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 50, 0, 50, 50 ), c2.getBounds() );
    }

    @Test
    public void shouldTranslateMultipleCompSouthEast() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 50, 0, 50, 50 );
        layouter.doLayout( Arrays.<Component> asList( c1, c2 ), new Dimension( 200, 200 ), 0, 0, context( 50, 50 ) );
        assertEquals( new Rectangle( 50, 50, 50, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 100, 50, 50, 50 ), c2.getBounds() );
    }

    @Test
    public void shouldTranslateMultipleCompSouthWest() {
        Component c1 = new JPanel();
        c1.setBounds( 50, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 100, 0, 50, 50 );
        layouter.doLayout( Arrays.<Component> asList( c1, c2 ), new Dimension( 200, 200 ), 0, 0, context( -50, 50 ) );
        assertEquals( new Rectangle( 0, 50, 50, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 50, 50, 50, 50 ), c2.getBounds() );
    }

}
