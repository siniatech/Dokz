package com.siniatech.dokz.layout;

import static junit.framework.Assert.*;

import java.awt.Component;
import java.awt.Rectangle;
import java.util.Arrays;

import javax.swing.JPanel;

import org.junit.Test;

public class TestTileLayouter {

    private TileLayouter layouter = new TileLayouter();

    @Test
    public void shouldLayoutEmptyContainer() {
        layouter.doLayout( Arrays.<Component> asList(), 100, 100 );
    }

    @Test
    public void shouldLayoutSingleComponent() {
        Component c1 = new JPanel();
        layouter.doLayout( Arrays.asList( c1 ), 100, 100 );
        assertEquals( new Rectangle( 0, 0, 100, 100 ), c1.getBounds() );
    }

    @Test
    public void shouldLayoutSingleRow() {
        Component c1 = new JPanel();
        Component c2 = new JPanel();
        layouter.doLayout( Arrays.asList( c1, c2 ), 100, 100 );
        assertEquals( new Rectangle( 0, 0, 50, 100 ), c1.getBounds() );
        assertEquals( new Rectangle( 50, 0, 50, 100 ), c2.getBounds() );
    }

    @Test
    public void shouldLayoutSmallSquare() {
        Component c1 = new JPanel();
        Component c2 = new JPanel();
        Component c3 = new JPanel();
        Component c4 = new JPanel();
        layouter.doLayout( Arrays.asList( c1, c2, c3, c4 ), 100, 100 );
        assertEquals( new Rectangle( 0, 0, 50, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 50, 0, 50, 50 ), c2.getBounds() );
        assertEquals( new Rectangle( 0, 50, 50, 50 ), c3.getBounds() );
        assertEquals( new Rectangle( 50, 50, 50, 50 ), c4.getBounds() );
    }

    @Test
    public void shouldLayoutLargeSquare() {
        Component c1 = new JPanel();
        Component c2 = new JPanel();
        Component c3 = new JPanel();
        Component c4 = new JPanel();
        Component c5 = new JPanel();
        Component c6 = new JPanel();
        Component c7 = new JPanel();
        Component c8 = new JPanel();
        Component c9 = new JPanel();
        layouter.doLayout( Arrays.asList( c1, c2, c3, c4, c5, c6, c7, c8, c9 ), 120, 120 );
        assertEquals( new Rectangle( 0, 0, 40, 40 ), c1.getBounds() );
        assertEquals( new Rectangle( 40, 0, 40, 40 ), c2.getBounds() );
        assertEquals( new Rectangle( 80, 0, 40, 40 ), c3.getBounds() );
        assertEquals( new Rectangle( 0, 40, 40, 40 ), c4.getBounds() );
        assertEquals( new Rectangle( 40, 40, 40, 40 ), c5.getBounds() );
        assertEquals( new Rectangle( 80, 40, 40, 40 ), c6.getBounds() );
        assertEquals( new Rectangle( 0, 80, 40, 40 ), c7.getBounds() );
        assertEquals( new Rectangle( 40, 80, 40, 40 ), c8.getBounds() );
        assertEquals( new Rectangle( 80, 80, 40, 40 ), c9.getBounds() );
    }

    @Test
    public void shouldLayoutLargeRectangle() {
        Component c1 = new JPanel();
        Component c2 = new JPanel();
        Component c3 = new JPanel();
        Component c4 = new JPanel();
        Component c5 = new JPanel();
        Component c6 = new JPanel();
        layouter.doLayout( Arrays.asList( c1, c2, c3, c4, c5, c6 ), 120, 120 );
        assertEquals( new Rectangle( 0, 0, 40, 60 ), c1.getBounds() );
        assertEquals( new Rectangle( 40, 0, 40, 60 ), c2.getBounds() );
        assertEquals( new Rectangle( 80, 0, 40, 60 ), c3.getBounds() );
        assertEquals( new Rectangle( 0, 60, 40, 60 ), c4.getBounds() );
        assertEquals( new Rectangle( 40, 60, 40, 60 ), c5.getBounds() );
        assertEquals( new Rectangle( 80, 60, 40, 60 ), c6.getBounds() );
    }

    @Test
    public void shouldFillIncompleteSmallSquare() {
        Component c1 = new JPanel();
        Component c2 = new JPanel();
        Component c3 = new JPanel();
        layouter.doLayout( Arrays.asList( c1, c2, c3 ), 100, 100 );
        assertEquals( new Rectangle( 0, 0, 50, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 50, 0, 50, 50 ), c2.getBounds() );
        assertEquals( new Rectangle( 0, 50, 100, 50 ), c3.getBounds() );
    }

    @Test
    public void shouldFillIncompleteLargeSquareWhenMissingOne() {
        Component c1 = new JPanel();
        Component c2 = new JPanel();
        Component c3 = new JPanel();
        Component c4 = new JPanel();
        Component c5 = new JPanel();
        layouter.doLayout( Arrays.asList( c1, c2, c3, c4, c5 ), 120, 120 );
        assertEquals( new Rectangle( 0, 0, 40, 60 ), c1.getBounds() );
        assertEquals( new Rectangle( 40, 0, 40, 60 ), c2.getBounds() );
        assertEquals( new Rectangle( 80, 0, 40, 60 ), c3.getBounds() );
        assertEquals( new Rectangle( 0, 60, 40, 60 ), c4.getBounds() );
        assertEquals( new Rectangle( 40, 60, 80, 60 ), c5.getBounds() );
    }

    @Test
    public void shouldFillIncompleteLargeSquareWhenMissingTwo() {
        Component c1 = new JPanel();
        Component c2 = new JPanel();
        Component c3 = new JPanel();
        Component c4 = new JPanel();
        Component c5 = new JPanel();
        Component c6 = new JPanel();
        Component c7 = new JPanel();
        layouter.doLayout( Arrays.asList( c1, c2, c3, c4, c5, c6, c7 ), 120, 120 );
        assertEquals( new Rectangle( 0, 0, 40, 40 ), c1.getBounds() );
        assertEquals( new Rectangle( 40, 0, 40, 40 ), c2.getBounds() );
        assertEquals( new Rectangle( 80, 0, 40, 40 ), c3.getBounds() );
        assertEquals( new Rectangle( 0, 40, 40, 40 ), c4.getBounds() );
        assertEquals( new Rectangle( 40, 40, 40, 40 ), c5.getBounds() );
        assertEquals( new Rectangle( 80, 40, 40, 40 ), c6.getBounds() );
        assertEquals( new Rectangle( 0, 80, 120, 40 ), c7.getBounds() );
    }

    @Test
    public void shouldRoundCorrectlyInSingleRow() {
        Component c1 = new JPanel();
        Component c2 = new JPanel();
        layouter.doLayout( Arrays.asList( c1, c2 ), 101, 100 );
        assertEquals( new Rectangle( 0, 0, 50, 100 ), c1.getBounds() );
        assertEquals( new Rectangle( 50, 0, 51, 100 ), c2.getBounds() );
    }

    @Test
    public void shouldRoundCorrectlyInSquare() {
        Component c1 = new JPanel();
        Component c2 = new JPanel();
        Component c3 = new JPanel();
        Component c4 = new JPanel();
        layouter.doLayout( Arrays.asList( c1, c2, c3, c4 ), 101, 101 );
        assertEquals( new Rectangle( 0, 0, 50, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 50, 0, 51, 50 ), c2.getBounds() );
        assertEquals( new Rectangle( 0, 50, 50, 51 ), c3.getBounds() );
        assertEquals( new Rectangle( 50, 50, 51, 51 ), c4.getBounds() );
    }

    @Test
    public void shouldRoundCorrectlyInLargeRectangle() {
        Component c1 = new JPanel();
        Component c2 = new JPanel();
        Component c3 = new JPanel();
        Component c4 = new JPanel();
        Component c5 = new JPanel();
        Component c6 = new JPanel();
        layouter.doLayout( Arrays.asList( c1, c2, c3, c4, c5, c6 ), 122, 121 );
        assertEquals( new Rectangle( 0, 0, 40, 60 ), c1.getBounds() );
        assertEquals( new Rectangle( 40, 0, 40, 60 ), c2.getBounds() );
        assertEquals( new Rectangle( 80, 0, 42, 60 ), c3.getBounds() );
        assertEquals( new Rectangle( 0, 60, 40, 61 ), c4.getBounds() );
        assertEquals( new Rectangle( 40, 60, 40, 61 ), c5.getBounds() );
        assertEquals( new Rectangle( 80, 60, 42, 61 ), c6.getBounds() );
    }

    @Test
    public void shouldAddHGapToSmallSquare() {
        Component c1 = new JPanel();
        Component c2 = new JPanel();
        Component c3 = new JPanel();
        Component c4 = new JPanel();
        layouter.doLayout( Arrays.asList( c1, c2, c3, c4 ), 102, 100, 2, 0 );
        assertEquals( new Rectangle( 0, 0, 50, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 52, 0, 50, 50 ), c2.getBounds() );
        assertEquals( new Rectangle( 0, 50, 50, 50 ), c3.getBounds() );
        assertEquals( new Rectangle( 52, 50, 50, 50 ), c4.getBounds() );
    }

    @Test
    public void shouldAddVGapToSmallSquare() {
        Component c1 = new JPanel();
        Component c2 = new JPanel();
        Component c3 = new JPanel();
        Component c4 = new JPanel();
        layouter.doLayout( Arrays.asList( c1, c2, c3, c4 ), 100, 104, 0, 4 );
        assertEquals( new Rectangle( 0, 0, 50, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 50, 0, 50, 50 ), c2.getBounds() );
        assertEquals( new Rectangle( 0, 54, 50, 50 ), c3.getBounds() );
        assertEquals( new Rectangle( 50, 54, 50, 50 ), c4.getBounds() );
    }

    @Test
    public void shouldAddGapsToLargeSquare() {
        Component c1 = new JPanel();
        Component c2 = new JPanel();
        Component c3 = new JPanel();
        Component c4 = new JPanel();
        Component c5 = new JPanel();
        Component c6 = new JPanel();
        Component c7 = new JPanel();
        Component c8 = new JPanel();
        Component c9 = new JPanel();
        layouter.doLayout( Arrays.asList( c1, c2, c3, c4, c5, c6, c7, c8, c9 ), 124, 126, 2, 3 );
        assertEquals( new Rectangle( 0, 0, 40, 40 ), c1.getBounds() );
        assertEquals( new Rectangle( 42, 0, 40, 40 ), c2.getBounds() );
        assertEquals( new Rectangle( 84, 0, 40, 40 ), c3.getBounds() );
        assertEquals( new Rectangle( 0, 43, 40, 40 ), c4.getBounds() );
        assertEquals( new Rectangle( 42, 43, 40, 40 ), c5.getBounds() );
        assertEquals( new Rectangle( 84, 43, 40, 40 ), c6.getBounds() );
        assertEquals( new Rectangle( 0, 86, 40, 40 ), c7.getBounds() );
        assertEquals( new Rectangle( 42, 86, 40, 40 ), c8.getBounds() );
        assertEquals( new Rectangle( 84, 86, 40, 40 ), c9.getBounds() );
    }

    @Test
    public void shouldIgnoreGapsInSingleComponent() {
        Component c1 = new JPanel();
        layouter.doLayout( Arrays.asList( c1 ), 100, 100, 10, 10 );
        assertEquals( new Rectangle( 0, 0, 100, 100 ), c1.getBounds() );
    }
}
