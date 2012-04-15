package com.siniatech.dokz.layout;

import static junit.framework.Assert.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Arrays;

import javax.swing.JPanel;

import org.junit.Test;

public class TestRemovingLayouter {

    private RemovingLayouter layouter = new RemovingLayouter();

    @Test
    public void shouldntBarfWithEmptyList() {
        layouter.doLayout( Arrays.<Component> asList(), new Dimension( 200, 200 ) );
    }

    @Test
    public void shouldFillEntireWithOneCompE() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        layouter.doLayout( Arrays.asList( c1 ), new Dimension( 100, 50 ) );
        assertEquals( new Rectangle( 0, 0, 100, 50 ), c1.getBounds() );
    }

    @Test
    public void shouldFillEntireWithOneCompS() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        layouter.doLayout( Arrays.asList( c1 ), new Dimension( 50, 100 ) );
        assertEquals( new Rectangle( 0, 0, 50, 100 ), c1.getBounds() );
    }

    @Test
    public void shouldFillEntireWithOneCompW() {
        Component c1 = new JPanel();
        c1.setBounds( 50, 0, 50, 50 );
        layouter.doLayout( Arrays.asList( c1 ), new Dimension( 100, 50 ) );
        assertEquals( new Rectangle( 0, 0, 100, 50 ), c1.getBounds() );
    }

    @Test
    public void shouldFillEntireWithOneCompN() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 50, 50, 50 );
        layouter.doLayout( Arrays.asList( c1 ), new Dimension( 50, 100 ) );
        assertEquals( new Rectangle( 0, 0, 50, 100 ), c1.getBounds() );
    }

    @Test
    public void shouldFillEntireWithOneCompEWithGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        layouter.doLayout( Arrays.asList( c1 ), new Dimension( 105, 50 ), 5, 5 );
        assertEquals( new Rectangle( 0, 0, 105, 50 ), c1.getBounds() );
    }

    @Test
    public void shouldFillEntireWithOneCompSWithGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        layouter.doLayout( Arrays.asList( c1 ), new Dimension( 50, 105 ), 5, 5 );
        assertEquals( new Rectangle( 0, 0, 50, 105 ), c1.getBounds() );
    }

    @Test
    public void shouldFillEntireWithOneCompWWithGap() {
        Component c1 = new JPanel();
        c1.setBounds( 55, 0, 50, 50 );
        layouter.doLayout( Arrays.asList( c1 ), new Dimension( 105, 50 ), 5, 5 );
        assertEquals( new Rectangle( 0, 0, 105, 50 ), c1.getBounds() );
    }

    @Test
    public void shouldFillEntireWithOneCompNWithGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 55, 50, 50 );
        layouter.doLayout( Arrays.asList( c1 ), new Dimension( 50, 105 ), 5, 5 );
        assertEquals( new Rectangle( 0, 0, 50, 105 ), c1.getBounds() );
    }

    @Test
    public void shouldFillSquareP1() {
        Component c1 = new JPanel();
        c1.setBounds( 50, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 0, 50, 50, 50 );
        Component c3 = new JPanel();
        c3.setBounds( 50, 50, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2, c3 ), new Dimension( 100, 100 ) );
        assertEquals( new Rectangle( 0, 0, 100, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 0, 50, 50, 50 ), c2.getBounds() );
        assertEquals( new Rectangle( 50, 50, 50, 50 ), c3.getBounds() );
    }

    @Test
    public void shouldFillSquareP2() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 0, 50, 50, 50 );
        Component c3 = new JPanel();
        c3.setBounds( 50, 50, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2, c3 ), new Dimension( 100, 100 ) );
        assertEquals( new Rectangle( 0, 0, 100, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 0, 50, 50, 50 ), c2.getBounds() );
        assertEquals( new Rectangle( 50, 50, 50, 50 ), c3.getBounds() );
    }

    @Test
    public void shouldFillSquareP3() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 50, 0, 50, 50 );
        Component c3 = new JPanel();
        c3.setBounds( 50, 50, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2, c3 ), new Dimension( 100, 100 ) );
        assertEquals( new Rectangle( 0, 0, 50, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 50, 0, 50, 50 ), c2.getBounds() );
        assertEquals( new Rectangle( 0, 50, 100, 50 ), c3.getBounds() );
    }

    @Test
    public void shouldFillSquareP4() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 50, 0, 50, 50 );
        Component c3 = new JPanel();
        c3.setBounds( 0, 50, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2, c3 ), new Dimension( 100, 100 ) );
        assertEquals( new Rectangle( 0, 0, 50, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 50, 0, 50, 50 ), c2.getBounds() );
        assertEquals( new Rectangle( 0, 50, 100, 50 ), c3.getBounds() );
    }

    @Test
    public void shouldFillSquareP1WithGap() {
        Component c1 = new JPanel();
        c1.setBounds( 55, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 0, 55, 50, 50 );
        Component c3 = new JPanel();
        c3.setBounds( 55, 55, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2, c3 ), new Dimension( 105, 105 ), 5, 5 );
        assertEquals( new Rectangle( 0, 0, 105, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 0, 55, 50, 50 ), c2.getBounds() );
        assertEquals( new Rectangle( 55, 55, 50, 50 ), c3.getBounds() );
    }

    @Test
    public void shouldFillSquareP2WithGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 0, 55, 50, 50 );
        Component c3 = new JPanel();
        c3.setBounds( 55, 55, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2, c3 ), new Dimension( 105, 105 ), 5, 5 );
        assertEquals( new Rectangle( 0, 0, 105, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 0, 55, 50, 50 ), c2.getBounds() );
        assertEquals( new Rectangle( 55, 55, 50, 50 ), c3.getBounds() );
    }

    @Test
    public void shouldFillSquareP3WithGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 55, 0, 50, 50 );
        Component c3 = new JPanel();
        c3.setBounds( 55, 55, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2, c3 ), new Dimension( 105, 105 ), 5, 5 );
        assertEquals( new Rectangle( 0, 0, 50, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 55, 0, 50, 50 ), c2.getBounds() );
        assertEquals( new Rectangle( 0, 55, 105, 50 ), c3.getBounds() );
    }

    @Test
    public void shouldFillSquareP4WithGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 55, 0, 50, 50 );
        Component c3 = new JPanel();
        c3.setBounds( 0, 55, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2, c3 ), new Dimension( 105, 105 ), 5, 5 );
        assertEquals( new Rectangle( 0, 0, 50, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 55, 0, 50, 50 ), c2.getBounds() );
        assertEquals( new Rectangle( 0, 55, 105, 50 ), c3.getBounds() );
    }

    @Test
    public void shouldFillFullHGapAtTop() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 50, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 50, 50, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2 ), new Dimension( 100, 100 ) );
        assertEquals( new Rectangle( 0, 0, 50, 100 ), c1.getBounds() );
        assertEquals( new Rectangle( 50, 0, 50, 100 ), c2.getBounds() );
    }

    @Test
    public void shouldFillFullHGapAtBottom() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 50, 0, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2 ), new Dimension( 100, 100 ) );
        assertEquals( new Rectangle( 0, 0, 50, 100 ), c1.getBounds() );
        assertEquals( new Rectangle( 50, 0, 50, 100 ), c2.getBounds() );
    }

    @Test
    public void shouldFillFullHGapInMiddle() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 50, 0, 50, 50 );
        Component c3 = new JPanel();
        c3.setBounds( 0, 100, 50, 50 );
        Component c4 = new JPanel();
        c4.setBounds( 50, 100, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2, c3, c4 ), new Dimension( 100, 150 ) );
        assertEquals( new Rectangle( 0, 0, 50, 100 ), c1.getBounds() );
        assertEquals( new Rectangle( 50, 0, 50, 100 ), c2.getBounds() );
        assertEquals( new Rectangle( 0, 100, 50, 50 ), c3.getBounds() );
        assertEquals( new Rectangle( 50, 100, 50, 50 ), c4.getBounds() );
    }

    @Test
    public void shouldFillFullHGapAtTopWithGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 55, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 55, 55, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2 ), new Dimension( 105, 105 ), 5, 5 );
        assertEquals( new Rectangle( 0, 0, 50, 105 ), c1.getBounds() );
        assertEquals( new Rectangle( 55, 0, 50, 105 ), c2.getBounds() );
    }

    @Test
    public void shouldFillFullHGapAtBottomWithGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 55, 0, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2 ), new Dimension( 105, 105 ), 5, 5 );
        assertEquals( new Rectangle( 0, 0, 50, 105 ), c1.getBounds() );
        assertEquals( new Rectangle( 55, 0, 50, 105 ), c2.getBounds() );
    }

    @Test
    public void shouldFillFullHGapInMiddleWithGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 55, 0, 50, 50 );
        Component c3 = new JPanel();
        c3.setBounds( 0, 110, 50, 50 );
        Component c4 = new JPanel();
        c4.setBounds( 55, 110, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2, c3, c4 ), new Dimension( 105, 160 ), 5, 5 );
        assertEquals( new Rectangle( 0, 0, 50, 105 ), c1.getBounds() );
        assertEquals( new Rectangle( 55, 0, 50, 105 ), c2.getBounds() );
        assertEquals( new Rectangle( 0, 110, 50, 50 ), c3.getBounds() );
        assertEquals( new Rectangle( 55, 110, 50, 50 ), c4.getBounds() );
    }

    @Test
    public void shouldFillFullVGapAtLeft() {
        Component c1 = new JPanel();
        c1.setBounds( 50, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 50, 50, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2 ), new Dimension( 100, 100 ) );
        assertEquals( new Rectangle( 0, 0, 100, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 0, 50, 100, 50 ), c2.getBounds() );
    }

    @Test
    public void shouldFillFullVGapAtRight() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 0, 50, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2 ), new Dimension( 100, 100 ) );
        assertEquals( new Rectangle( 0, 0, 100, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 0, 50, 100, 50 ), c2.getBounds() );
    }

    @Test
    public void shouldFillFullVGapInMiddle() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 0, 50, 50, 50 );
        Component c3 = new JPanel();
        c3.setBounds( 100, 0, 50, 50 );
        Component c4 = new JPanel();
        c4.setBounds( 100, 50, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2, c3, c4 ), new Dimension( 150, 100 ) );
        assertEquals( new Rectangle( 0, 0, 100, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 0, 50, 100, 50 ), c2.getBounds() );
        assertEquals( new Rectangle( 100, 0, 50, 50 ), c3.getBounds() );
        assertEquals( new Rectangle( 100, 50, 50, 50 ), c4.getBounds() );
    }

    @Test
    public void shouldFillFullVGapAtLeftWithGap() {
        Component c1 = new JPanel();
        c1.setBounds( 55, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 55, 55, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2 ), new Dimension( 105, 105 ), 5, 5 );
        assertEquals( new Rectangle( 0, 0, 105, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 0, 55, 105, 50 ), c2.getBounds() );
    }

    @Test
    public void shouldFillFullVGapAtRightWithGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 0, 55, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2 ), new Dimension( 105, 105 ), 5, 5 );
        assertEquals( new Rectangle( 0, 0, 105, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 0, 55, 105, 50 ), c2.getBounds() );
    }

    @Test
    public void shouldFillFullVGapInMiddleWithGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 0, 55, 50, 50 );
        Component c3 = new JPanel();
        c3.setBounds( 110, 0, 50, 50 );
        Component c4 = new JPanel();
        c4.setBounds( 110, 50, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2, c3, c4 ), new Dimension( 160, 105 ), 5, 5 );
        assertEquals( new Rectangle( 0, 0, 105, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 0, 55, 105, 50 ), c2.getBounds() );
        assertEquals( new Rectangle( 110, 0, 50, 50 ), c3.getBounds() );
        assertEquals( new Rectangle( 110, 50, 50, 50 ), c4.getBounds() );
    }
}
