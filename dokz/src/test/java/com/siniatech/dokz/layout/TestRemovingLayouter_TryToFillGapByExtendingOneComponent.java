package com.siniatech.dokz.layout;

import static junit.framework.Assert.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Arrays;

import javax.swing.JPanel;

import org.junit.Test;

public class TestRemovingLayouter_TryToFillGapByExtendingOneComponent {

    private RemovingLayouter layouter = new RemovingLayouter();

    @Test
    public void shouldFillSimpleGapToLeft() {
        Component c1 = new JPanel();
        c1.setBounds( 50, 0, 50, 50 );
        boolean succeeds = layouter.tryToFillGapByExtendingOneComponent( Arrays.asList( c1 ), new Rectangle( 0, 0, 50, 50 ), new Dimension( 100, 50 ), 0, 0 );
        assertTrue( succeeds );
        assertEquals( new Rectangle( 0, 0, 100, 50 ), c1.getBounds() );
    }

    @Test
    public void shouldFillSimpleGapAbove() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 50, 50, 50 );
        boolean succeeds = layouter.tryToFillGapByExtendingOneComponent( Arrays.asList( c1 ), new Rectangle( 0, 0, 50, 50 ), new Dimension( 50, 100 ), 0, 0 );
        assertTrue( succeeds );
        assertEquals( new Rectangle( 0, 0, 50, 100 ), c1.getBounds() );
    }

    @Test
    public void shouldFillSimpleGapToRight() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        boolean succeeds = layouter.tryToFillGapByExtendingOneComponent( Arrays.asList( c1 ), new Rectangle( 50, 0, 50, 50 ), new Dimension( 100, 50 ), 0, 0 );
        assertTrue( succeeds );
        assertEquals( new Rectangle( 0, 0, 100, 50 ), c1.getBounds() );
    }

    @Test
    public void shouldFillSimpleGapBelow() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        boolean succeeds = layouter.tryToFillGapByExtendingOneComponent( Arrays.asList( c1 ), new Rectangle( 0, 50, 50, 50 ), new Dimension( 50, 100 ), 0, 0 );
        assertTrue( succeeds );
        assertEquals( new Rectangle( 0, 0, 50, 100 ), c1.getBounds() );
    }

    @Test
    public void shouldFillSimpleGapToLeftWithGap() {
        Component c1 = new JPanel();
        c1.setBounds( 55, 0, 50, 50 );
        boolean succeeds = layouter.tryToFillGapByExtendingOneComponent( Arrays.asList( c1 ), new Rectangle( 0, 0, 50, 50 ), new Dimension( 105, 50 ), 5, 0 );
        assertTrue( succeeds );
        assertEquals( new Rectangle( 0, 0, 105, 50 ), c1.getBounds() );
    }

    @Test
    public void shouldFillSimpleGapAboveWithGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 55, 50, 50 );
        boolean succeeds = layouter.tryToFillGapByExtendingOneComponent( Arrays.asList( c1 ), new Rectangle( 0, 0, 50, 50 ), new Dimension( 50, 105 ), 0, 5 );
        assertTrue( succeeds );
        assertEquals( new Rectangle( 0, 0, 50, 105 ), c1.getBounds() );
    }

    @Test
    public void shouldFillSimpleGapToRightWithGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        boolean succeeds = layouter.tryToFillGapByExtendingOneComponent( Arrays.asList( c1 ), new Rectangle( 55, 0, 50, 50 ), new Dimension( 105, 50 ), 5, 0 );
        assertTrue( succeeds );
        assertEquals( new Rectangle( 0, 0, 105, 50 ), c1.getBounds() );
    }

    @Test
    public void shouldFillSimpleGapBelowWithGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        boolean succeeds = layouter.tryToFillGapByExtendingOneComponent( Arrays.asList( c1 ), new Rectangle( 0, 55, 50, 50 ), new Dimension( 50, 105 ), 0, 5 );
        assertTrue( succeeds );
        assertEquals( new Rectangle( 0, 0, 50, 105 ), c1.getBounds() );
    }

    @Test
    public void shouldFillSimpleGapInSquare() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 0, 55, 50, 50 );
        Component c3 = new JPanel();
        c3.setBounds( 55, 55, 50, 50 );
        boolean succeeds = layouter.tryToFillGapByExtendingOneComponent( Arrays.asList( c1, c2, c3 ), new Rectangle( 55, 0, 50, 50 ), new Dimension( 105, 105 ), 5, 5 );
        assertTrue( succeeds );
        assertEquals( new Rectangle( 0, 0, 105, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 0, 55, 50, 50 ), c2.getBounds() );
        assertEquals( new Rectangle( 55, 55, 50, 50 ), c3.getBounds() );
    }
}
